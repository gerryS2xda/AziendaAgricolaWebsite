<%@ page language="java" import="com.project.bean.UtenteBean" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
		<title>User Area</title>
		<link type="text/css" rel="stylesheet" href="./css/area_utente_stile.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<% 
		UtenteBean ub = (UtenteBean) session.getAttribute("user");
		String userType = (String) session.getAttribute("userType");
		if(ub == null || userType == null) {
			response.sendRedirect("./error.jsp"); //pagina errore 404
		}else if(userType.equals("user")){	
	%>
	<body onload="hideAllbutDashboard()">
		<!--  popup che saranno mostrati al momento opportuno -->
		<div id="scrivi_mail">
			<form name="new_mail">
				<h1>Nuovo messaggio</h1>
				<center><span class="new_mail_txt_err"></span></center> <br>
				A:	<select id="mail_dest" name="mail_dest" onchange="validateSelectDestinationEmail($(this), $('.new_mail_txt_err'))"><option value=""> 
					</option><option value=""></option><option value=""></option><option value=""></option></select> <br>
				Oggetto: <input type="text" id="mail_object" name="mail_object" value=""> <br>
				<textarea id="new_mail_content" placeholder="Scrivi" rows="8"></textarea> <br>
				<div>
					<button class="send_button" type="button">Invia</button>
					<button type="button" onclick="removePopupScrivi()">Indietro</button>
				</div>
			</form>
		</div>
		<div id="content_popup">
			<div id="content_popup_area">
				<h1>Contenuto email</h1>
				<div id="content_da_txt">Da: <span></span></div>
				<div id="content_a_txt">A: <span></span> </div>
				<div id="content_object_txt">Oggetto: <span></span> </div>
				<hr>
				<p></p>
				<div class="content_pop_button_area">
					<button id="risp_mail" type="button">Rispondi</button>
					<button id="write_m" type="button">Scrivi</button>
					<button id="back_content" type="button">Indietro</button>
				</div>
			</div>	
		</div>
		<!--  header della pagina, inizia tutto da qui -->
		<header id="header_part">
			<span id="txt_logo">User Area</span>
			<span id="welcome_txt">Benvenuto, <%=ub.getNome() %></span>
			<span id="logout"><img src="./images/icon-logout.png">Logout</span>
		</header>
		<!--  contenuto a sinistra di quello principale, barra di navigazione laterale -->
		<section id="side_back"> 
			<div id="sidebar">
				<ul>
					<li id="l1" class="active"><a id="a1" href="#dashboard_content">Account</a></li>
					<li id="l2"><a id="a2" href="#current_order">Ordini in uscita</a></li>
					<li id="l3"><a id="a3" href="#mailbox">Casella di posta</a></li>
					<li id="l4"><a id="a4" href="#my_orders">I miei ordini</a></li>
					<li id="l5"><a id="a5" href="#stats">Statistiche</a></li>
					<li> <a href="HomePage.jsp">Vai alla Homepage </a></li>
				</ul>
			</div>
		</section>
		<!--  contenuto principale -->
		<section id="content_page">
			<div id="dashboard_content" class="section">
					<div class="category_style">
						<h1 class="title_content_page">Miei dati</h1>
						<hr class="hr_line_border">
						<p class="p_content_page">In questa sezione, potete modificare alcuni dei vostri dati personali</p>
					</div>
					<form id="user_data">
						Nome: <span class="text_user_data"><%= ub.getNome() %></span><br>
						Cognome: <span class="text_user_data"><%= ub.getCognome() %></span><br>
						Data di nascita: <span class="text_user_data"><%= ub.getDataNascita().toString() %></span><br>
						Luogo di nascita: <span class="text_user_data"><%= ub.getLuogoNascita() %></span><br>
						Sesso: <span class="text_user_data"><%= ub.getSesso() %></span><br>
						Codice fiscale: <span class="text_user_data"><%= ub.getCodiceFiscale() %></span><br>
						Indirizzo: <input type="text" name="address" class="text_user_data" value="<%= ub.getAddress() %>" onblur="validateAddress($(this), 50)"> <span class="account_text_err"></span><br>
						Telefono: <input type="tel" name="telefono" class="text_user_data" value="<%= ub.getTelefono() %>" onblur="validateTelephone($(this), 10)"> <span class="account_text_err"></span><br>
						Email: <input type="email" name="mail" class="text_user_data" value="<%= ub.getEmail() %>" onblur="validateEmail($(this), 50, $('.account_text_err').eq(2))"> <span class="account_text_err"></span><br>
						Username: <input type="text" name="usrname" class="text_user_data" value="<%= ub.getUsername() %>" onblur="validateUsername($(this))"> <span class="account_text_err"></span><br>
						Password: <input type="text" name="pwd" class="text_user_data" value="<%= ub.getPassword() %>" onblur="validatePassword($(this))"> <span class="account_text_err"></span><br>
						<div class="button_area_user">
							<button type="button" id="edit_admin" value="edit_account">Modifica dati</button>
						</div>
					</form>	
				</div>	
			<div id="current_order" class="section">
				<div class="category_style">
					<h1 class="title_content_page">Il tuo ordine corrente</h1>
					<hr class="hr_line_border">
					<p class="p_content_page">In questa sezione, vengono mostrati gli ordini che avete fatto recentemente e che stanno per arrivare a destinazione</p>
				</div>
				<br><br>
				<div class="div_content">
					<div class="table_scroll">
						<table id="tb_current" class="table_data">
								<thead>
									<tr>
										<th> Codice fatt. </th>
										<th> Luogo spedizione </th>
										<th> Luogo consegna </th>
										<th> Data spedizione </th>
										<th> Data arrivo </th>
										<th> Importo </th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
					</div>
				</div>
		    </div>
			<div id="mailbox" class="section">
				<nav id="mailbox_nav">
					<a id="a6" href="javascript:void(0);">Posta arrivata</a>
					<a id="a7" href="javascript:void(0);">Posta inviata</a>
					<a id="scrivi" href="javascript:void(0);">Scrivi</a>
				</nav>
				<hr class="hr_line_border_mailbox">
				<p>Cliccare sul contenuto della colonna 'Oggetto' per visualizzare il contenuto. Per cancellare una mail, mettere la spunta sulla checkbox</p>
				<div id="posta_arrivata"></div>  <!--  contenuto inserito in base alla presenza o meno di email -->
				<div id="posta_inviata"></div> <!--  contenuto inserito in base alla presenza o meno di email -->
			</div>
			<div id="my_orders" class="section">
				<div class="category_style">
					<h1 class="title_content_page">Fatture ed ordini</h1>
					<hr class="hr_line_border">
					<p class="p_content_page">In questa sezione, vengono mostrate tutte le fatture che sono state pagate da voi e che sono associate agli ordini.
						Per vedere la fattura, cliccare sul numero presente nella colonna 'Codice fatt.'.
					</p>
				</div>
				<br>
				<div class="div_content">
					<div class="table_scroll">
						<table id="tb_all_order" class="table_data">
								<thead>
									<tr>
										<th> Codice fatt. </th>
										<th> Luogo spedizione </th>
										<th> Luogo consegna </th>
										<th> Data spedizione </th>
										<th> Data arrivo </th>
										<th> Prezzo </th>
									</tr>
								</thead>
								<tbody></tbody>
						</table>
					</div>
				</div>
				<div id="popup_fattura">
					<div id="popup_fattura_content">	
						<div class="fatt_txt">Fattura Nr. <span></span> Data emissione: <span></span> </div>
						<div class="table_scroll fatt_tb">	
							<table id="tb_fattura" class="table_data">
								<thead>
									<tr>
										<th>Cod.Articolo</th><th>Descrizione</th><th>Q.ta</th><th>Prezzo uni.</th><th>Sconto</th><th>IVA</th><th>Importo</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
						<div id="tot_txt">Totale fattura: <span></span>€</div>
					</div>
					<div class="button_area_fattura">
						<button id="hide_fatt_button" type="button">Nascondi fattura</button>
					</div>
				</div>
			</div>
			<div id="stats"class="section">
				<div class="category_style">
					<h1 class="title_content_page">Statistiche</h1>
					<hr class="hr_line_border">
					<p class="p_content_page">In questa sezione, vengono mostrate alcune statistiche relative alle spese fatte questo mese, annuali e sulle spese fatte gli anni precedenti</p>
				</div>
				<div class="row_block_info">
					<ul class="list_block">
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Spesa corrente mese </span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Spesa scorso mese</span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Spesa corrente anno </span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Spesa scorso anno </span> </li>
					</ul>
				</div>
			</div>
		</section>
		<% } %>
		<script src="./script/jquery-3.3.1.min.js"></script>
	    <script src="./script/area_utente.js"></script>
	</body>
</html>