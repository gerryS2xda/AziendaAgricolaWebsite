package com.project.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.project.bean.*;
import com.project.model.*;
import com.project.utils.Utils;
import com.google.gson.*;

@WebServlet("/cart-contr")
public class CartController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		Gson json = new Gson();
		
		String action = request.getParameter("action");
		
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if(cart == null){ //se non esiste il carrello nella sessione, ne crea uno nuovo 
			action = "init_cart";
		}
		
		
		if(action!= null){
			if(action.equals("init_cart")){ 
				if(cart == null){	//se non esiste il carrello nella sessione, ne crea uno nuovo 
					try {
						ComposizioneModel cm = new ComposizioneModel();
						cart = new ShoppingCart(cm.getMaxNumFattura()); //prendi l'ultimo id della fattura dal DB
					} catch (SQLException e) {
						e.printStackTrace();
						response.setStatus(500);
					}
					session.setAttribute("cart", cart);
				}
			}else if(action.equals("remove")){
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				double tot = 0;
				if(cart.getNumberOfElement() != 0){	//inserita come protezione da sovrapposizione di richieste e risposte
					String code = request.getParameter("IDprod_rm");
					
					//modifica il valore del totale e mandalo tramite la response
					tot = Double.parseDouble(request.getParameter("total"));
					int p = cart.findProduct(code);
				
					tot -= cart.getListaProdotti().get(p).getPrezzoAcquisto();
					tot = BigDecimal.valueOf(tot).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
					//rimuovi l'elemento dal carrello
					cart.removeProduct(code);
				
					//salva il carrello nella sessione e ripassa il controllo alla jsp
					session.setAttribute("cart", cart);
				}
				//formulazione della stringa JSON per la response
				response.getWriter().write(json.toJson("{\"total\":" + tot + ", \"prodotti\":" +  cart.getNumberOfElement() + "}"));
			}else if(action.equals("edit_quantity")){
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				double tot = Double.parseDouble(request.getParameter("total")); //totale corrente prima della modifica
				int p = cart.findProduct(request.getParameter("idprod")); //id del prodotto a cui modificare la quantita'
				int q = Integer.parseInt(request.getParameter("quant")); //valore della nuova quanita' da impostare
				
				//modifiche per il carrello nella sessione
				ComposizioneBean cb = cart.getListaProdotti().get(p);
				ProdottoBean pb = cb.getProdotto();
				
				int qOld = cb.getQuantita(); //valore quantita' prima della modifica
				
				//calcolo del nuovo prezzo di acquisto del prodotto in base alla nuova quantita'
				cb.setQuantita(q); 
				double pz = Utils.calculatePrezzoAcquisto(pb.getPrezzo(), cb.getSconto(), pb.getIVA(), cb.getQuantita());
				cb.setPrezzoAcquisto(pz);
				
				//modifica del prezzo totale con l'aggiunta della nuova quantita'
				tot -= Utils.calculatePrezzoAcquisto(pb.getPrezzo(), cb.getSconto(), pb.getIVA(), qOld);
				tot += cart.getListaProdotti().get(p).getPrezzoAcquisto();
				
					
				//salva il carrello nella sessione e ripassa il controllo alla jsp
				cart.getListaProdotti().set(p, cb);
				session.setAttribute("cart", cart);
				
				//formulazione response per la funzione di callback di $.post
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				tot = BigDecimal.valueOf(tot).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				response.getWriter().write(json.toJson("{\"total\":" + tot + ",\"prezzo\":" + pz + "}"));
			}else{
				response.setStatus(404);
				response.sendRedirect("./error.jsp"); //pagina errore 404
			}
		}else{
			response.setStatus(404);
			response.sendRedirect("./error.jsp"); //pagina errore 404
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
