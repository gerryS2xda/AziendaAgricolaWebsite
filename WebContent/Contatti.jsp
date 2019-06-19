<%@ page language="java" contentType="text/html; charset=utf-8" info="Contatti" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Contatti</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/contatti_stile.css"/>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body onLoad="initContatti()">
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar">
			
		</section>
		<section id="main_content">
			<div>
				<h1 class="title title_border">Contatti</h1>
				<hr>
				<br>
			</div>
			<div class="google-maps">
			<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3021.695944147317!2d14.789829215059472!3d40.76871177932582!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133bc5c7456b88bd%3A0x80bab96149d2993d!2sUniversity+of+Salerno!5e0!3m2!1sen!2sit!4v1531406581151" frameborder="0" style="border:0" allowfullscreen></iframe>
			<div class="info">
				<h2 id="t2">Azienda Agricola Ovomont</h2>
				Via Provinciale, 29
				<br>Campagna (SA) 84022<br>P.IVA 05177690657<br>
				Per info contattare: <br>
				<div class="admin_info"></div>
			</div>
			</div>
			<div class="modulo">
				<form action="">
				<div class="posta"><img src="images/posta_icon.png"><span>Contatti</span></div>
				<hr>
				<div class="all">
					<div class="input_elements">
						<span>E-mail:  </span><br><input id="mail_mittente"type="text" name="e-mail" onblur="validateEmail($(this), 50, $('.new_mail_txt_err').eq(0))"> <span class="new_mail_txt_err"></span><br>
						<span>Oggetto: </span><br><input id="mail_object" type="text" name="oggetto"><br>
						<span>Destinatario: </span><select id="admin_selector" name="admin_selector" onblur="validateSelectDestinationEmail($(this), $('.new_mail_txt_err').eq(1))">
								<option value=""> </option>
								<option value=""></option>
								<option value=""></option>
								<option value=""></option>
								</select>	<span class="new_mail_txt_err"></span> <br>
					</div>
					<div class="messaggio">
						<textarea id="new_mail_content" name="messaggio" rows="10" placeholder="Scrivi qui il tuo messaggio: "></textarea><br>
					</div>
				</div>
				<div class="submit"><button id="send" type="button">Invia</button></div>
				</form>
			</div>
		</section>
		<section id="right_sidebar">
		</section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/contatti_js.js"></script>
	</body>
</html>