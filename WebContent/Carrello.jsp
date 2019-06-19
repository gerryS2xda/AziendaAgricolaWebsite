<%@ page language="java" import="java.util.ArrayList, com.project.bean.*, com.project.model.*, java.math.*"  
	contentType="text/html; charset=utf-8" info="Contenuto del carrello" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>

<head>
	<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Carrello</title>
	<link type="text/css" rel="stylesheet" href="./css/cart_style.css">
</head>
<%	ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart"); //prendi il carrello dalla sessione
	double imptot = 0;	//importo totale
%>
<body onLoad="initCart()">
	<header>
		<%@ include file="NavBar.jsp" %>
	</header>
	<section id="left_sidebar"></section>
	<section id="main_content">
		<h2>Carrello</h2>
		<div id="cart_content">
			<% if(cart.getNumberOfElement() == 0){ %>
				<p id="empty_cart">Il carrello e' vuoto</p>
			<%}else{ %>
			<table id= "table_prod">
				<tr>
					<th class="column_hide"> </th>
					<th> </th>		<!--  nome prodotto e breve descrizione -->
					<th>Prezzo </th>
					<th>Quantità</th>
					<th> </th>
				</tr>
				<%	for(int i=0; i< cart.getNumberOfElement(); i++){ 
						ProdottoBean p = cart.getListaProdotti().get(i).getProdotto();
						ComposizioneBean cb = cart.getListaProdotti().get(i);
						int q = cart.getListaProdotti().get(i).getQuantita();
				%>
					<tr>
						<td class="column_hide">
							<img id ="img_prod" src="<%= p.getImageURL() %>" alt="Immagine prodotto non disponibile">
						</td>
						<%
							if(p.getType().contains("Confezione")){
								String str= p.getType() +  " da " + p.getNumUovaConfezione() + " uova";
						%>
							<td><p><%= str %></p></td>
						<%}else{ %>
							<td><p><%= p.getType() %></p></td>
						<%} %>
						<td class="price_txt"><%= cb.getPrezzoAcquisto() %> €</td>
						<% imptot += cb.getPrezzoAcquisto(); %>
						<td>
							<center>
								<form name="q_form">
									<input type="number" name="quant" value="<%= q %>" min="1" max="100" 
									onblur="updateTotalPrice($(this), '<%= p.getCodice() %>');"> &nbsp;
								</form>
							</center>
						</td>
						<td>
							<form>
								<button type="button" onclick="removeElement($(this), '<%= p.getCodice() %>');">Rimuovi</button>
							</form>
						</td>
					</tr>
				<%}%>
				<tr id="totale">
					<td class="column_hide"> </td>
					<td>Totale: </td>
					<td><b><span id="tot_txt"><%= BigDecimal.valueOf(imptot).setScale(2, RoundingMode.HALF_UP).doubleValue() %></span> €</b></td>
					<td> </td>
					<td><button type="button">Procedi con il pagamento</button></td>
				</tr>		
			</table>
		</div>
		
		<% } %>
		<div id="back_to_catalogo">
			<center><button type="button">Torna al catalogo</button></center>
		</div>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/cart_js.js"></script>
	</section>
	<section id="right_sidebar"></section>
	<footer>
		<%@ include file="footer_simple.jsp" %>
	</footer>
</body>
</html>