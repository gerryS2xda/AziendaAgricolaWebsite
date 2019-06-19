package com.project.controller;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.project.bean.ProdottoBean;
import com.project.bean.UtenteBean;
import com.project.model.ComposizioneModel;
import com.project.model.ShoppingCart;
import com.project.model.UtenteModel;
import com.project.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;

@WebServlet("/user-controller")
public class UtenteController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		Gson json = new Gson();
		
		/* Set action string object per smistare le azioni da eseguire */
		String action = request.getParameter("action");
		
		/* Admin and User settings */
		try {
			if(action != null){
				UtenteModel um = new UtenteModel();
				if(action.equals("count")){	//conta gli utenti registrati
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					int nUser = um.getNumberofUser("user");
					response.getWriter().write(json.toJson("{\"nUser\":" + nUser + "}"));
				}else if(action.equals("edit_account")){
					UtenteBean ub2 = json.fromJson(request.getParameter("utente"), UtenteBean.class);
					if(ub2 != null){ //modifica i dati dell'utente
						//l'operazione di modifica viene svolta: 
						String code = ub2.getCodiceFiscale();
						um.doDelete(code); //cancellando i vecchi dati dal DB
						um.doSave(ub2); //inserendo i nuovi dati
						
						//modifica i dati dell'utente anche nell'oggetto presente nella sessione
						session.removeAttribute("user");
						session.setAttribute("user", ub2);
					}else{
						response.setStatus(404);
						response.sendRedirect("./error.jsp"); //pagina errore 404
					}
				}else if(action.equals("search")){
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					ArrayList<UtenteBean> utenti= um.doRetrieveByCond("Username = '" + request.getParameter("key") + "' and Tipo = 'user'");
					String str="{\"utente\":";
					if(utenti.size() != 0){ 
						str += utenti.get(0).toString() + ",";
					    str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					}else{
						str += "\"No result\"}";
					}
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("delete")){
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					boolean done = false;
					if(um.doDelete(request.getParameter("cfkey"))){ done = true; }
					
					response.getWriter().write(json.toJson("{\"done\":" + done + "}"));
				}else if(action.equals("admin")){
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					boolean done = false;
					
					if(um.updateUserData(request.getParameter("cfkey"), "Tipo", "admin")) done = true;
					
					response.getWriter().write(json.toJson("{\"done\":" + done + "}"));
				}else if(action.equals("new_admin")){
					//aggiungo questo attributo nella sessione per la sicurezza
					session.setAttribute("UTProtect", "1");
				}else if(action.equals("logout")){
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					boolean done = false;
					UtenteBean ub = (UtenteBean) session.getAttribute("user");
					if(ub != null){
						//rimuovi gli utenti dalla sessione
						session.removeAttribute("user");
						session.removeAttribute("userType");
						
						//rimuovi l'attr. 'userstate' se in precedenza e' stato inserito da 'Registration Controller'
						String usrState = (String) session.getAttribute("userstate");
						if(usrState != null){
							session.removeAttribute("userstate");
						}
						
						
						//oltre a rimuovere gi utenti, svuota il carrello se presente nella sessione
						ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); //prendi il carrello dalla sessione e svuotalo
						if(cart != null){
							//svuota il carrello quando si effettua il logout
							session.removeAttribute("cart");
							ComposizioneModel cm = new ComposizioneModel();
							cart = new ShoppingCart(cm.getMaxNumFattura()); //prendi l'ultimo id della fattura dal DB e assegnalo al nuovo carrello
							session.setAttribute("cart", cart);
						}
						done = true;
					}
					response.getWriter().write(json.toJson("{\"done\":" + done + "}"));
				}else if(action.equals("get_admin")){
					/* Imposta la response di tipo stringa JSON*/
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					ArrayList<UtenteBean> utenti= um.doRetrieveByCond("Tipo = 'admin'");
					String str="{";
					if(utenti.size() == 0){ 
						str += "\"utente\":\"No result\"}";
					}else{
						int i = 0;
						for(UtenteBean u : utenti){
							str+= "\"utente"+i +"\":" + u.toString() + ",";
							i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					}
					response.getWriter().write(json.toJson(str));
				}else{
					response.setStatus(404);
					response.sendRedirect("./error.jsp"); //pagina errore 404
				}
			}else{
				response.setStatus(404);
				response.sendRedirect("./error.jsp"); //pagina errore 404
			}
		}catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
	}
}

