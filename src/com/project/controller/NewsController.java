package com.project.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.project.bean.NotiziaBean;
import com.project.bean.ProdottoBean;
import com.project.model.NotiziaModel;
import com.project.utils.Utils;


@WebServlet("/news-controller")
public class NewsController extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		NotiziaModel nm = new NotiziaModel();
		Gson json = new Gson();
		
		try{
			String action = request.getParameter("action");
			if(action != null){
				if(action.equals("init")){
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					String str = "{";
					ArrayList<NotiziaBean> notizie = nm.doRetrieveAll("ID");
				    int i = 0;
				    for(NotiziaBean n : notizie) { 
				    	str += "\"news"+i +"\":" + n.toString() + ",";
					    i++;
					}
					str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("view_news")){
					int newsID = Integer.parseInt(request.getParameter("newsBean"));
					NotiziaBean nb = nm.doRetrieveByKey(newsID);
					session.setAttribute("newsBean", nb);
					response.sendRedirect("./Details_news.jsp");
				}else if(action.equals("get_news_id")){	//possibile utilizzo in futuro, ora no
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					
					int id = nm.getMaxNewsID();	//prendi il numero max di news presenti nel DB
					id++; //incrementa di 1 per aggiungere una nuova news;
					
					response.getWriter().write(json.toJson("{\"newsID\":" + id + "}"));
				}else if(action.equals("add")){
					NotiziaBean nb2 = json.fromJson(request.getParameter("new_news"), NotiziaBean.class);	
					if(nb2 != null){
						//alcuni dati da settare manualmente prima di salvare nel DB
						int id = nm.getMaxNewsID();	//prendi il numero max di news presenti nel DB
						id++; //incrementa di 1 per aggiungere una nuova news;
						nb2.setNewsID(id);
						nb2.setDataInserimento(LocalDate.now().toString());
						nb2.setDateofLastModified(LocalDate.now().toString());
						
						nm.doSave(nb2);
					}else{
						//go to page error
						response.setStatus(404);
					}
				}else if(action.equals("show_news")){
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					String str = "{";
					ArrayList<NotiziaBean> notizie = nm.doRetrieveAll("ID");
					int i = 0;
					for(NotiziaBean n : notizie) { 
					   	str += "\"news"+i +"\":" + n.toString() + ",";
					    i++;
					}
					str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
					response.getWriter().write(json.toJson(str));
				}else if(action.equals("delete")){
					nm.doDelete(Integer.parseInt(request.getParameter("pkey"))); //cancella la notizia con l'ID = pkey dal DB
				}else if(action.equals("edit_save")){
					//prendi i dati da modificare nel DB dalla request
					NotiziaBean enb = json.fromJson(request.getParameter("edit_news"), NotiziaBean.class);
					//prendi news dal DB, modifica i suoi dati e poi rimetti tale news nel DB
					NotiziaBean nb = nm.doRetrieveByKey(enb.getNewsID());
					nb.setTitle(enb.getTitle());
					if(!enb.getNewsContent().equals("NoContent")){
						nb.setNewsContent(enb.getNewsContent());
					}else{
						nb.setNewsType(enb.getNewsType());
						nb.setURLImage(enb.getURLImage());
					}
					nb.setDateofLastModified(LocalDate.now().toString());
						
					//rimuovi vecchia news da DB e inserisci il nuovo
					nm.doDelete(enb.getNewsID()); 
					nm.doSave(nb);
				}else if(action.equals("dammi_news")){
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					NotiziaBean nb = nm.doRetrieveByKey(Integer.parseInt(request.getParameter("id")));
					response.getWriter().write(json.toJson("{\"notizia\":" + nb.toString() + "}"));
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
