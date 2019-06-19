package com.project.controller;

import javax.servlet.http.*;
import com.project.bean.UtenteBean;
import com.project.model.UtenteModel;

import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.*;

@WebServlet("/reg-form-controller")
public class RegistrationController extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		
			UtenteModel um = new UtenteModel();
			String action = request.getParameter("action");	//action puo' assumere user or admin
			try{
				if(action.equals("user") || action.equals("admin")){
			
					//crea un utente prendendo i dati dalla richiesta
					UtenteBean user = createNewUser(request);
			
					//verifica che l'utente non sia gia' presente nel DB
				
					if(um.isUserPresent(user)){
						session.setAttribute("userstate", "present");
						response.sendRedirect("./LoginForm.jsp");
					}else{
			
						//salva dati del nuovo utente nel DB
						um.doSave(user);
						
						session.removeAttribute("userstate");
						
						/*dopo che i dati del nuovo admin sono stati salvati, posso rimuovere
						l'attr. 'UTProtect' aggiunto per la sicurezza nei confronti della registrazione di un nuovo admin */
						String secure = (String) session.getAttribute("UTProtect");	//protezione
						if(secure != null){
							session.removeAttribute("UTProtect");
						}
						
						//invia l'utente nella login
						response.sendRedirect("./LoginForm.jsp");
					}
				}else{
					response.setStatus(404);
					response.sendRedirect("./error.jsp"); //pagina errore 404
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
	}

	public UtenteBean createNewUser(HttpServletRequest request){
		UtenteBean ub = new UtenteBean();
		String address = "";
	
		ub.setCodiceFiscale(request.getParameter("u_cod_fisc"));
		ub.setNome(request.getParameter("u_name"));
		ub.setCognome(request.getParameter("u_surname"));
		ub.setDataNascita(LocalDate.parse(request.getParameter("u_dataN")).toString());
		ub.setLuogoNascita(request.getParameter("u_luogoN"));
		ub.setSesso(request.getParameter("sex"));
		if(!request.getParameter("u_tel").equals("")){
			ub.setTelefono(request.getParameter("u_tel"));
		}
		
		address += request.getParameter("u_via") + ", " + request.getParameter("u_ncivico") + " " + request.getParameter("u_citta");
		ub.setAddress(address);
		ub.setEmail(request.getParameter("u_email"));
		ub.setUsername(request.getParameter("username"));
		ub.setPassword(request.getParameter("pwd"));
		ub.setUserType(request.getParameter("action"));
		return ub;
	}
}