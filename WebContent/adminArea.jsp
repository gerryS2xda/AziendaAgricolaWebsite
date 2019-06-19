<%@ page language="java" import="com.project.bean.UtenteBean" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
		<title>Admin Area</title>
		<link type="text/css" rel="stylesheet" href="./css/admin_area_style.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<% 
		UtenteBean ub = (UtenteBean) session.getAttribute("user");
		String userType = (String) session.getAttribute("userType");
		if(ub == null || userType == null) {
			response.sendRedirect("./error.jsp"); //pagina errore 404
		}else if(userType.equals("admin")){	
	%>
	<body onLoad="hideAllContentExceptDashboard();">
		<!--  popup che saranno mostrati al momento opportuno -->
		<div id="scrivi_mail">
			<form name="new_mail">
				<h1>Nuovo messaggio</h1>
				<center><span class="new_mail_txt_err"></span></center> <br>
				A: <input type="text" id="mail_dest" name="mail_dest" value="" onblur="validateEmail($(this), 50, $('.new_mail_txt_err'))"> <br>
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
		<div id="news_content_popup">
			<div id="news_content_popup_area">
				<span class="span_hide"></span>
				<h1></h1>
				<hr>
				<textarea id="news_content_edit" rows="8" value=""></textarea> <br>
				<div class="content_pop_button_area">
					<button id="save_news_edit" type="button">Salva</button>
					<button id="back_content_news" type="button">Indietro</button>
				</div>
			</div>	
		</div>
		<!--  header della pagina, inizia tutto da qui -->
		<header id="header_part">
			<span id="txt_logo"><b>Admin Area</b></span>
			<span id="welcome_txt">Welcome, <%= ub.getNome() %></span>
			<a id="logout" href="javascript:void(0);"><img src="./images/icon-logout.png">Logout</a>
		</header>
		<!--  contenuto a sinistra di quello principale, barra di navigazione laterale -->
		<section id="side_back"> 
			<div id="sidebar">
				<ul>
					<li id="li1" class="active"><a id="a1" href="#dashboard_content">Dashboard</a></li>
					<li id="li2"> <a id="a2" href="#manage_account_section">Gestione account</a></li>
					<li id="li4"> <a id="a4" href="#manage_website">Gestione sito</a></li>
					<li id="li5"> <a id="a5" href="#mailbox">Casella di posta</a></li>
					<li> <a href="HomePage.jsp">Vai alla Homepage </a></li>
				</ul>
			</div>
		</section>
		<!--  contenuto principale -->
		<section id="content_page">
			<div id="dashboard_content">
				<div class="row_block_info">
					<ul class="list_block">
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Utenti registrati </span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Incasso del mese </span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Ordini in uscita </span> </li>
						<li> <span class="info_dash_text"></span> <span class="block_txt"> Fatture eseguite </span> </li>
					</ul>
				</div>
				<table class="row_table_data">
					<tr>
						<th> </th>
						<th> </th>
					</tr>
					<tr>
						<td id="personal_data">
							<div class="widget-box">
								<div class="widget-title">
									<span id="ic1" class="icon"><img class="icon_arrow" src="./images/arrow_btm.png"></span>
									<span class="category_text">Dati personali</span>
								</div>
								<div id="content1" class="widget-content" >
									<span class="text_content">Nome: <%= ub.getNome() %></span><br>
									<span class="text_content">Cognome: <%= ub.getCognome() %></span><br>
									<span class="text_content">Data di nascita: <%= ub.getDataNascita() %></span><br>
									<span class="text_content">Luogo di nascita: <%= ub.getLuogoNascita() %></span><br>
									<span class="text_content">Sesso: <%= ub.getSesso() %></span><br>
									<span class="text_content">Codice fiscale: <%= ub.getCodiceFiscale() %></span><br>
									<span class="text_content">Indirizzo: <%= ub.getAddress() %></span><br>
									<span class="text_content">Telefono: <%= ub.getTelefono() %></span><br>
									<span class="text_content">Email: <%= ub.getEmail() %></span><br>
									<span class="text_content">Username: <%= ub.getUsername() %></span><br>
									<span class="text_content">Password: <%= ub.getPassword() %></span><br>
								</div>
							</div>
						</td>
						<td id="email_list">
							<div class="widget-box_email">
								<div class="widget-title">
									<span id="ic2" class="icon"><img class="icon_arrow" src="./images/arrow_btm.png"></span>
									<span class="category_text">Email arrivate</span>
								</div>
								<div id="content2" class="widget_content_mail" >
									<ul class="mail_arrivate"></ul>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="manage_account_section" class="section">
				<div id="admin_account_area">
					<div class="category_style">
						<h1 class="title_content_page">Miei dati</h1>
						<hr class="hr_line_border">
						<p class="p_content_page">In questa sezione, potete modificare alcuni dei vostri dati personali ed eventualmente nominare un nuovo amministratore</p>
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
							<button type="button" id="add_admin" value="new_admin">Add new admin</button>
						</div>
					</form>	
				</div>	
				<div id="user_account_area">
					<div class="category_style">
						<h1 class="title_content_page">User account</h1>
						<hr class="hr_line_border">
						<p class="p_content_page">In questa parte, l'amministratore puo' gestire l'account di un utente e in particolare puo' anche nominarlo come amministratore del sito
							se lo desidera. Per iniziare inserire l'username dell'utente che si vuole manipolare...</p>
					</div>
					<form id="search_area">
						<input type="text" class="input_search_bar" value="" placeholder="Search..">
						<div id="search_user" class="div_icon_search"><img class="icon_search" src="./images/searchicon.png"></div>
					</form>
					<div id="result_search_area">
						<span></span>
						<div class="table_scroll">
							<table class="table_data">
								<thead>
									<tr>
										<th>Codice Fiscale</th>
										<th>Cognome</th>
										<th>Nome</th>
										<th>Data di nascita</th>
										<th>Username</th>
										<th>Stato</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="manage_website" class="section">
				<div class="category_style">
					<h1 class="title_content_page" >Prodotti</h1>
					<hr class="hr_line_border">
					<p class="p_content_page">In questa sezione, potete aggiungere, rimuovere e modificare i prodotti presenti nel catalogo</p>
				</div>
				<div id="product_area">
					<form id="prod_form" name="prod_form">
						<label>Codice: <input type="text" name="cod_prod" value="" onblur="validateCodice($(this), 6, $('.prod_txt_err').eq(0))"> </label> <span class="prod_txt_err"></span><br>
						<label>Data scadenza: <input type="date" name="data_scad" value="" onblur="validateDate($(this), 1)"> </label> <span class="prod_txt_err"></span><br>
						<label>Data confezionamento: <input type="date" name="data_conf" value="" onblur="validateDate($(this), 2)"></label> <span class="prod_txt_err"></span><br>
						<label>Prezzo: <input type="text" class="price_input" name="price" value="" onblur="validatePrezzo($(this), $('.prod_txt_err').eq(3))"> €</label> 
						<label>Sconto: <input type="number" name="sconto_prod" value="0" min="0" max="100"> % </label>
						<label>IVA: <input type="number" name="iva_prod" value="22" min="0" max="100"> % </label> <span class="prod_txt_err"></span> <br>
						<label>Nome prodotto: <input type="text" name="name_prod" value="" onblur="validateNomeProdotto($(this), 20, $('.prod_txt_err').eq(4))"> </label>
						<label>Numero uova:  <input type="number" id="num_uova" name="num_uova" value="0" min="0" max="100" onchange="checkConfezioneUova($('#name_prod'), $('.prod_txt_err').eq(4))"> </label> <span class="prod_txt_err"></span> <br>
						<label>Link immagine: <input type="text" name="image_link" value="" onblur="validateLinkImmagine($(this), 100, $('.prod_txt_err').eq(5))"> </label> <span class="prod_txt_err"></span> <br>
						<textarea name="desc_prod" placeholder="Descrizione prodotto" rows="5"></textarea> <br>
						<div class="button_area">
							<button type="button" id="add_prod">Aggiungi prodotto</button>
							<button type="button" id="show_prod">Mostra prodotti</button>
							<button type="button" id="hide_prod">Nascondi prodotti</button>
						</div>
					</form>
					<div id="tb_prod" class="table_scroll">
						<table class="table_data">
							<thead>
								<tr>
									<th> Codice </th>
									<th> Nome </th>
									<th> Prezzo </th>
									<th> Sconto </th>
									<th> IVA </th>
									<th> Immagine </th>
									<th> </th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				<div id="news_section">
					<div class="category_style">
						<h1 class="title_content_page">News</h1>
						<hr class="hr_line_border">
						<p class="p_content_page">In questa sezione, potete aggiungere o rimuovere le notizie presenti nella sezione "News" del sito</p>
					</div>
					<div id="news_area">
						<form id="news_form" name="news_form">
							<label>Tipo di notizia: 
								<select name="type_news">	
									<option value="-">-</option>
									<option value="generale">Generale</option>
									<option value="promozionale">Promozionale</option>
								</select>
							</label> <br>
							<label>Link immagine: <input type="text" name="image_news_link" value="" onblur="validateLinkImmagine($(this), 100, $('.news_txt_err').eq(0))"> </label> <span class="news_txt_err"></span><br>
							<label>Titolo: <input type="text" name="title_news" value=""> </label><br>
							<textarea name="news_content" placeholder="Contenuto notizia" rows="5"></textarea> <br>
							<div class="button_area">
								<button type="button" id="add_news" onclick="">Aggiungi notizia</button>
								<button type="button" id="show_news" onclick="">Mostra notizie</button>
								<button type="button" id="hide_news">Nascondi notizie</button>
							</div>
						</form>
						<div id="tb_news" class="table_scroll">
							<table class="table_data">
								<thead>
									<tr>
										<th> Titolo </th>
										<th> Contenuto </th>
										<th> Ultima modifica </th>
										<th> Tipo </th>
										<th> Immagine </th>
										<th> </th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
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
				<div id="posta_arrivata"></div> <!--  contenuto inserito in base alla presenza o meno di email -->
				<div id="posta_inviata"></div> <!--  contenuto inserito in base alla presenza o meno di email -->	
			</div>
		</section>
		<%
			}else{
				response.sendRedirect("./error.jsp"); //pagina errore 404
			}%>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/admin_area.js"></script>
	</body>
</html>
