package com.project.controller;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.bean.ProdottoBean;
import com.project.model.ProdottoModel;
import com.project.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;

@WebServlet("/product-contr")
public class ProductController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		Gson json = new Gson();
		ProdottoModel pm = new ProdottoModel();
		try{
			String action = request.getParameter("action");
			if(action != null){
				if(action.equals("add")){
					ProdottoBean pb2 = json.fromJson(request.getParameter("new_prod"), ProdottoBean.class);
					if(pb2 != null){
						pm.doSave(pb2);		
					}else{
						response.setStatus(404);
						response.sendRedirect("./error.jsp"); //pagina errore 404
					}
				}else if(action.equals("show_prod")){
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					String str = "{";
					ArrayList<ProdottoBean> prodotti = pm.doRetrieveAll("DataScadenza");
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
				}else if(action.equals("delete")){
					pm.doDelete(request.getParameter("pkey"));
				}else if(action.equals("edit_save")){
					//prendi i dati da modificare nel DB dalla request
					ProdottoBean epb = json.fromJson(request.getParameter("edit_prod"), ProdottoBean.class);
					//prendi prodotto dal DB, modifica i suoi dati e poi rimetti tale prodotto nel DB
			
					ProdottoBean pb = pm.doRetrieveByKey(epb.getCodice());
					pb.setPrezzo(epb.getPrezzo());
					pb.setSconto(epb.getSconto());
					pb.setIVA(epb.getIVA());
					pb.setImageURL(epb.getImageURL());
					//rimuovi vecchio prodotto da DB e inserisci il nuovo
					pm.doDelete(epb.getCodice()); 
					pm.doSave(pb);
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
