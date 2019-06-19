package com.project.controller;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.*;
import com.project.bean.UtenteBean;
import com.project.model.PostaModel;
import com.google.gson.Gson;
import com.project.bean.PostaBean;
import com.project.bean.ProdottoBean;

@WebServlet("/posta-contr")
public class PostaController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		Gson json = new Gson();
		
		//prendo i dati dell'utente dalla sessione
		HttpSession session = request.getSession();
		UtenteBean ub = (UtenteBean) session.getAttribute("user");
		
			
			String action = request.getParameter("action");
			if(action == null){
				response.setStatus(404);
				response.sendRedirect("./error.jsp"); //pagina errore 404
			}else{
				PostaModel pm = new PostaModel();
				try {
					if(action.equals("scrivi")){
						int id = pm.getMaxEmailID(); //prendi il max valore corrente di email presente nel DB
						id++; //incrementa di 1 per la nuova email
						
						PostaBean pb = new PostaBean();
						
						pb.setEmailID(id);
						pb.setMittente(request.getParameter("mail_mittente"));
						pb.setDestinatario(request.getParameter("mail_dest"));
						pb.setObjectofEmail(request.getParameter("mail_object"));
						pb.setContent(request.getParameter("new_mail_content"));
						pb.setTypeofEmail("Inviata");
						pb.setDataofEmail(LocalDate.now().toString());
						pb.setHour(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
						if(ub == null){
							pb.setUserTypeofMailbox("guest");
						}else{
							pb.setUserTypeofMailbox(ub.getUserType());
						}
						pm.doSave(pb);	//salva la mail scritta dall'utente o admin del sito nel DB
						
						id++; //incrementa di 1 per la nuova email
						//salva la mail verso la persona a cui e' diretta
						createMailForDestination(pb, id);
				
					}else if(action.equals("show_posta_inv")){
						if(ub == null){
							response.setStatus(404);
							response.sendRedirect("./error.jsp"); //pagina errore 404
							return;
						}
						response.setContentType("application/json");
						response.setCharacterEncoding("utf-8");
						
						ArrayList<PostaBean> inviate = pm.doRetrieveByCond("Mittente = '" + ub.getEmail() + "' and TipoPosta = 'Inviata' order by DataEmail");
						String str = "{";
						if(inviate.size() == 0){
							str += "\"email\": \"NoEmail\"}";
						}else{
							int i = 0;
							for(PostaBean p : inviate) { 
								str += "\"email"+i +"\":" + p.toString() + ",";
								i++;
							}
							str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
						}
						response.getWriter().write(json.toJson(str));
					}else if(action.equals("show_posta_arr") || action.equals("show_mail_dashboard")){
							if(ub == null){
								response.setStatus(404);
								response.sendRedirect("./error.jsp"); //pagina errore 404
								return;
							}
						
							response.setContentType("application/json");
							response.setCharacterEncoding("utf-8");
						
							ArrayList<PostaBean> arrivate = pm.doRetrieveByCond("Destinatario = '" + ub.getEmail() + "' and TipoPosta = 'Arrivata' order by DataEmail");
							String str = "{";
							if(arrivate.size() == 0){
								str += "\"email\": \"NoEmail\"}";
							}else{
								int i = 0;
								for(PostaBean p : arrivate) { 
									str += "\"email"+i +"\":" + p.toString() + ",";
									i++;
								}
								str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
							}
							response.getWriter().write(json.toJson(str));
					}else if(action.equals("delete_mail")){
							int id = Integer.parseInt(request.getParameter("id_email"));
							pm.doDelete(id); //cancellazione della mail selezionata
					}else if(action.equals("update_lett")){
						int id = Integer.parseInt(request.getParameter("id_email"));
						pm.updatePostaData(id, "Lettura", true);
					}else{
						response.setStatus(404);
						response.sendRedirect("./error.jsp"); //pagina errore 404
					}
				}catch (SQLException e) {
					e.printStackTrace();
					response.setStatus(500);
				}	
			}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
	}
	
	//altri metodi
	/* Una volta inviata una mail, si deve fare in modo che il destinatario possa visualizzarla nella propria casella di posta */
	/* Si crea un duplicato della mail precedente con alcune modifiche...*/
	private void createMailForDestination(PostaBean p, int id) throws SQLException{
		PostaModel pm = new PostaModel();
		PostaBean pb = p;
		
		pb.setEmailID(id);
		pb.setTypeofEmail("Arrivata");
		pb.setDataofEmail(LocalDate.now().toString());
		pb.setHour(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		String str = "";
		if(pb.getTypeofEmail().equals("user")){
			str = "admin"; //la mail e' diretta alla casella di posta dell'admin
		}else{
			str = "user"; //la mail e' diretta alla casella di posta user
		}
		pb.setUserTypeofMailbox(str);
		pb.setEmailReadState(false);
		
		pm.doSave(pb);
	}
}
