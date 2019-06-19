package com.project.controller;

import javax.servlet.http.*;

import com.google.gson.Gson;
import com.project.bean.UtenteBean;
import com.project.model.ComposizioneModel;
import com.project.model.ShoppingCart;
import com.project.model.UtenteModel;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.*;

@WebServlet("/login-contr")
public class LoginController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		//verifica se ci sono eventuali utente gia' loggati
		HttpSession session = request.getSession();
		Gson json = new Gson();
		
		UtenteBean ub2 = (UtenteBean) session.getAttribute("user");
		if(ub2 == null){
			UtenteModel um = new UtenteModel();
			String usr = request.getParameter("usrname");
			String pwd = request.getParameter("pwd");
		
			try {
				UtenteBean ub = um.getUserForLogin(usr, pwd);
				if(ub.getCodiceFiscale().equals("00000000")){
					//codice che riporta nella login e stampa Username e password non valida
					response.getWriter().write(json.toJson("{\"userstate\": \"errLogin\", \"userType\": \"null\"}"));
				}else{
					//se i dati inseriti nella login sono esatti, si inserisce il carrello nella sessione e poi si aggiunge l'utente alla sessione
					
					//controlla lo stato del carrello, se non e' presente nella sessione ne viene creato uno nuovo
					ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
					if(cart == null){
						ComposizioneModel cm = new ComposizioneModel();
						cart = new ShoppingCart(cm.getMaxNumFattura()); //prendi l'ultimo id della fattura dal DB
						session.setAttribute("cart", cart);
					}
					
					//aggiungi l'utente nella sessione
					session.setAttribute("user", ub);
					session.setAttribute("userType", ub.getUserType());
				
					response.getWriter().write(json.toJson("{\"userstate\": \"ok\", \"userType\":\""+ ub.getUserType()+"\"}"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
		}else{
			response.getWriter().write(json.toJson("{\"userstate\": \"logged\", \"userType\": \"null\"}"));
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
	}
}
