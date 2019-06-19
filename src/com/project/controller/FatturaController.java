package com.project.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.project.bean.*;
import com.project.model.*;
import com.project.utils.Utils;
import com.google.gson.*;

@WebServlet("/fattura-contr")
public class FatturaController extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		ComposizioneModel cm = new ComposizioneModel();
		Gson json = new Gson();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		UtenteBean ub = (UtenteBean) session.getAttribute("user");
		if(ub == null){
			response.getWriter().write(json.toJson("{\"status\": \"NoUser\"}"));
			return;
		}
		
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if(cart == null){
			response.getWriter().write(json.toJson("{\"status\": \"NoCart\"}"));
			return;
		}
		
		FatturaModel fm = new FatturaModel();
		String action = request.getParameter("action");
		try{
			if(action != null){	
				if(action.equals("buy")){
					//prendi il numero della fattura che serve in seguito
					int n = cart.getListaProdotti().get(0).getFattura().getNumero();
					
					//salva i dati sull'acquisto nella tabella 'fattura' del DB
					saveFatturaToDB(cart, request, ub);
					//salva il contenuto del carrello nel DB
					saveContentofCartToDB(cart);
					
					//costruzione dei bean per essere usati come prodotti nella fattura
					createObjectForShowFattura(request, n);
					
					//svuota il contenuto del carrello
					session.removeAttribute("cart");
					cart = new ShoppingCart(cm.getMaxNumFattura()); //prendi l'ultimo id della fattura dal DB e assegnalo al nuovo carrello
					session.setAttribute("cart", cart);
					
					//l'utente sara' spostato nella pagina relativa alla visualizzazione della fattura tramite la funzione JS del pulsante
					response.getWriter().write(json.toJson("{\"status\": \"ShowFattura\"}"));
				}else if(action.equals("show_fattura")){
					
					int key = Integer.parseInt(request.getParameter("key_fattura"));
					String str = "{";
					//ottieni lista prodotti associati alla fattura e salva nella sessione
					ArrayList<ComposizioneBean> acquisti = cm.doRetrieveByCond(" Fattura = "+key);
					if(acquisti.size() == 0){
						str += "\"acquisto\": \"NoAcquisto\"}";
					}else{
						int i = 0;
					    for(ComposizioneBean c : acquisti) { 
					    	str += "\"acquisto"+i +"\":" + c.toString() + ",";
						    i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					}
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("dashboard_info_fatture")){ //informazioni relative ai riquari nella dashboard dell'admin area
					int n = fm.getMaxNumFattura();	//ottieni il numero max di fatture effettuate dal DB
					double incasso = fm.getIncassoOfMonth(LocalDate.now().getMonthValue()); //ottieni l'incasso effettuato nel mese corrente
					int m = fm.getNumerOrdiniUscita(LocalDate.now().toString()); //ottieni il numero di ordini (pacchi) che non sono ancora arrivati
					response.getWriter().write(json.toJson("{\"nFatture\": " + n + ", \"incasso\": " + incasso + ", \"ordini\": " + m + "}"));
				}else if(action.equals("all_order")){
					String str = "{";
					ArrayList<FatturaBean> fatture = fm.doRetrieveAll("NumeroFattura");
					if(fatture.size() == 0){
						str += "\"fattura\": \"NoFattura\"}";
					}else{
						int i = 0;
						for(FatturaBean f : fatture) { 
							str += "\"fattura"+i +"\":" + f.toString() + ",";
							i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					}
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("current_order")){
					String cl = request.getParameter("cf");
					String str = "{";
					ArrayList<FatturaBean> fatture = fm.doRetrieveAll("Cliente = '"+ cl +"' and DataArrivo > '" + LocalDate.now().toString() +"'");
					if(fatture.size() == 0){
						str += "\"fattura\": \"NoFattura\"}";
					}else{
						int i = 0;
						for(FatturaBean f : fatture) { 
							str += "\"fattura"+i +"\":" + f.toString() + ",";
							i++;
						}
						str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					}
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("stats_user")){ //informazioni relative ai riquari nella sez. statistiche
					String cl = request.getParameter("cliente");
					double a = fm.getIncassoOfLastYearofClient(cl); //ottieni l'incasso effettuato lo scorso anno
					double b = fm.getIncassoOfYearofClient(cl, LocalDate.now().getYear()); //ottieni l'incasso effettuato nell'anno corrente
					double c = fm.getIncassoOfLastMonthofClient(cl); //ottieni l'incasso effettuato nel mese scorso
					double d = fm.getIncassoOfMonthofClient(cl, LocalDate.now().getMonthValue());  //ottieni l'incasso effettuato nel mese corrente
					
					response.getWriter().write(json.toJson("{\"lastyear\": " + a + ", \"thisyear\": " + b + ", \"lastmonth\": " + c + ", \"thismonth\": " + d + "}"));
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
	
	//private methods
	private void saveFatturaToDB(ShoppingCart cart, HttpServletRequest request, UtenteBean ub) throws SQLException{
		FatturaModel fm = new FatturaModel();
		FatturaBean fb = cart.getListaProdotti().get(0).getFattura();	//prendi il bean fattura dal primo elemento del carrello
		
		//setta il bean inserendo informazioni provenienti dal carrello.jsp
		fb.setDataEmissione(LocalDate.now().toString());
		fb.setImporto(Double.parseDouble(request.getParameter("tot_import")));
		fb.setCliente(ub);
		fb.setArrivo(ub.getAddress());
		fb.setSpeseSpedizione(5);	//valore fisso impostato a 5
		fb.setDataPartenza(LocalDate.now().toString());
		fb.setDataArrivo(LocalDate.now().plusDays(5).toString());
		fm.doSave(fb); //salva le informazioni nella tabella 'fattura' del DB
	}
	
	private void saveContentofCartToDB(ShoppingCart cart) throws SQLException{
		ComposizioneModel cm = new ComposizioneModel();
		for(int i = 0; i< cart.getListaProdotti().size(); i++){
			cm.doSave(cart.getListaProdotti().get(i));
		}
	}
	
	private void createObjectForShowFattura(HttpServletRequest request, int key)throws SQLException{
		HttpSession session = request.getSession();
		ComposizioneModel cm = new ComposizioneModel();
		FatturaModel fm = new FatturaModel();
		
		//ottieni fattura dal DB e salva nella sessione
		FatturaBean fb = fm.doRetrieveByKey(key);
		session.setAttribute("fattura", fb);
		
		//ottieni lista prodotti associati alla fattura e salva nella sessione
		ArrayList<ComposizioneBean> prodotti = cm.doRetrieveByCond(" Fattura = "+key);
		session.setAttribute("prod_fattura", prodotti);
	}
}
