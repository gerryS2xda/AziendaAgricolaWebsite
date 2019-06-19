<%@ page language="java" import="com.project.model.*,java.util.ArrayList, com.project.bean.*, com.project.utils.Utils" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Prodotti</title>
		<link type="text/css" rel="stylesheet" href="./css/catalogo.css">
	</head>
	<body onLoad="initCatalogo()">
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar"></section>
		<section id="main_content">
			<h1>Confezioni di uova</h1>
			<div id="order">
				<form>
					Ordina per: <select name="order_by" onchange="showProductOrder($(this))">
						<option value="-">-</option>
						<option value="UovaConfezione asc">Numero uova: crescente</option>
						<option value="UovaConfezione desc">Numero uova: descrescente</option>
						<option value="Prezzo Desc">Prezzo elevato</option>
						<option value="Prezzo Asc">Prezzo basso</option>
					</select>
				</form>
			</div>
			<div id="conf_prod_area"></div>
			<h1>Derivati</h1>
			<div id="prod_deriv_area"></div>	
			<div id="view_cart_button">
				<button type="button">Mostra carrello</button>
			</div>
		</section>
		<section id="right_sidebar"></section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/catalogo.js"></script>
		</div>
	</body>
</html>