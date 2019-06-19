<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Registrazione</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="./css/reg_form_style.css">
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body>
	<header>
		<%@ include file="NavBar.jsp" %>
	</header>
	<section id="left_sidebar"></section>
	<section id="main_content">
	<% String userType = request.getParameter("userType"); 
	   if(userType == null || userType == ""){
		  	response.sendRedirect("./error.jsp"); //pagina errore 404
	   }else if(userType.equals("admin")){
	   			String secure = (String) session.getAttribute("UTProtect");	//protezione per la registrazione di un amministratore
	   			if(secure == null || !secure.equals("1")){
					response.sendRedirect("./error.jsp"); //pagina errore 404
		   		}
		   	}
	%>
		<h1 id="title">Modulo di registrazione</h1>
		<div id="box_form">
			<form name="reg_form" action="reg-form-controller" method="post">
				<fieldset>
					<legend>Dati personali</legend>
					<div class="input_field">	
						<label id="lbl" for="first_name" name="l_name" class="">Nome</label>
						<input id="first_name" type="text" name="u_name" value="" onfocus="addActive('lbl')" onblur="validateOnlyLetter('first_name', 'lbl', 'nome_txt_error', 20);">
						&nbsp;&nbsp; 
					</div> 
					<p id="nome_txt_error" class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl1" for="sur_name" class="">Cognome</label>
						<input id="sur_name" type="text" name="u_surname" value="" onfocus="addActive('lbl1')" onblur="validateOnlyLetter('sur_name', 'lbl1', 'cognome_txt_error', 20)">
						&nbsp;&nbsp;																					
					</div>
					<p id="cognome_txt_error" class="txt_error"> </p> <br>
					Data di nascita: <input type="date" id="dataNasc" name="u_dataN" value="" onblur="validateDate($(this), $('.txt_error').eq(2))"> &nbsp;&nbsp; <p class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl2" for="lg_nasc" class="">Luogo di nascita</label>
						<input id="lg_nasc" type="text" name="u_luogoN" value="" onfocus="addActive('lbl2')" onblur="validateCityProv('lg_nasc', 'lbl2', 'lgN_error', 20)">
						&nbsp;&nbsp;
					</div>																
					<p id="lgN_error" class="txt_error"> </p> <br>
					Sesso: <input type="radio" name="sex" value="M" onblur="validateRadioButton()"> M &nbsp; <input type="radio" name="sex" value="F" onblur="validateRadioButton()"> F
					<p class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl3" for="cod_fisc" class="">Codice Fiscale</label>
						<input id="cod_fisc" type="text" name="u_cod_fisc" value="" onfocus="addActive('lbl3')" onblur="validateCodiceFiscale('cod_fisc', 'lbl3', 'cf_err')">
						&nbsp;&nbsp;
					</div>
					<p id="cf_err" class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl9" for="telef" class="">Telefono</label>
						<input id="telef" type="tel" name="u_tel" value="" onfocus="addActive('lbl9')" onblur="validateTelephone('telef', 'lbl9', 'tel_err')">
						&nbsp;&nbsp;
					</div>
					<p id="tel_err" class="txt_error"> </p> <br>
				</fieldset>
				<fieldset>
					<legend>Indirizzo di residenza</legend>
					<div class="input_field">	
						<label id="lbl4" for="via_in" class="">Via</label>
						<input id="via_in" type="text" name="u_via" value="" onfocus="addActive('lbl4')" onblur="validateOnlyLetter('via_in', 'lbl4', 'via_err', 25)">
						&nbsp;&nbsp; 
					</div>  &nbsp;&nbsp;
					N.ro Civico: <input type="number" name="u_ncivico" value="0" min="0" max="500">
					<p id="via_err" class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl6" for="citta" class="">Citta' e provincia</label>
						<input id="citta" type="text" name="u_citta" value="" onfocus="addActive('lbl6')" onblur="validateCityProv('citta', 'lbl6', 'city_pv_err', 50)">
						&nbsp;&nbsp;
					</div> &nbsp;&nbsp;
					<p id="city_pv_err" class="txt_error"> </p> <br>
				</fieldset>
				<fieldset>
					<legend>Dati login</legend>
					<div class="input_field">	
						<label id="lbl8" for="email" class="">Email</label>
						<input id="email" type="email" name="u_email" value="" onfocus="addActive('lbl8')" onblur="validateEmail('email', 'lbl8', 'mail_err')">
						&nbsp;&nbsp;
					</div>
					<p id="mail_err" class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl10" for="username" class="">Username</label>
						<input id="username" type="text" name="username" value="" onfocus="addActive('lbl10')" onblur="validateUsername('username', 'lbl10', 'usr_err')">
						&nbsp;&nbsp;
					</div>
					<p id="usr_err" class="txt_error"> </p> <br>
					<div class="input_field">	
						<label id="lbl11" for="pwd" class="">Password</label>
						<input id="pwd" type="text" name="pwd" value="" onfocus="addActive('lbl11')" onblur="validatePassword('pwd', 'lbl11', 'pwd_err')">
						&nbsp;&nbsp;
					</div>
					<p id="pwd_err" class="txt_error"> </p> <br>
				</fieldset>
				<input type="hidden" name="action" value="<%= userType %>">
				<div id="but_submit">
					<button type="submit" name="u_submit_but" onclick="formValidation();">Procedi</button>
				</div>
			</form>
		</div>
		</section>
		<section id="right_sidebar"></section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="./script/reg_form_js.js"></script>
	</body>
</html>