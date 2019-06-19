package com.project.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.project.bean.*;
import com.project.model.*;
import com.project.utils.Utils;

@WebServlet("/catalogo-contr")
public class CatalogoController extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		ProdottoModel model = new ProdottoModel();
		Gson json = new Gson();
		
		//controlla lo stato del carrello, se non e' presente nella sessione ne viene creato uno nuovo
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if(cart == null){
			try {
				ComposizioneModel cm = new ComposizioneModel();
				cart = new ShoppingCart(cm.getMaxNumFattura()); //prendi l'ultimo id della fattura dal DB
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
			session.setAttribute("cart", cart);
		}
		
		String action = request.getParameter("action");
			try {
				if(action!= null){
					if(action.equals("init")){
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						
						String str = "{";
						ArrayList<ProdottoBean> prodotti = model.doRetrieveAll("Codice");
					    int i = 0;
					    for(ProdottoBean p : prodotti) { 
					    	str += "\"prod"+i +"\":" + p.toString();
					    	if(p.getSconto() != 0){
					    		str += ", \"prezzoScontato\":" + Utils.calculatePrezzoScontato(p.getPrezzo(), p.getSconto()) + "},";
					    	}else{str+= "},";}
						    i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
						response.getWriter().write(json.toJson(str));
					}else if(action.equals("addtocart")){
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						
						ProdottoBean p = model.doRetrieveByKey(request.getParameter("prodID"));
						int q = Integer.parseInt(request.getParameter("quantID"));
						cart.addProduct(p, q);
					
						//salva il carrello nella sessione e ripassa il controllo alla jsp
						session.setAttribute("cart", cart);
					
						response.getWriter().write(json.toJson("{\"prodotti\":" +  cart.getNumberOfElement() + "}"));
					}else if(action.equals("order")){
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						
						String str = "{";
						ArrayList<ProdottoBean> prodotti = model.doRetrieveAll(request.getParameter("order_by")); //modifica ordine in cui verranno mostrati gli elementi
					    int i = 0;
					    for(ProdottoBean p : prodotti) { 
					    	str += "\"prod"+i +"\":" + p.toString();
					    	if(p.getSconto() != 0){
					    		str += ", \"prezzoScontato\":" + Utils.calculatePrezzoScontato(p.getPrezzo(), p.getSconto()) + "},";
					    	}else{str+= "},";}
						    i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
						response.getWriter().write(json.toJson(str));
					}else if(action.equals("view_det")){
						String prodID = request.getParameter("prodBean");
						ProdottoBean pb = model.doRetrieveByKey(prodID);
						session.setAttribute("prodDetails", pb);
						response.sendRedirect("./Details.jsp");
					}else{
						response.setStatus(404);
						response.sendRedirect("./error.jsp"); //pagina errore 404
					}
				}else{
					response.setStatus(404);
					response.sendRedirect("./error.jsp"); //pagina errore 404
				}
			}catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
				response.setStatus(500);
			}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
