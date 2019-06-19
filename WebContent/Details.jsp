<%@ page language="java" import="com.project.bean.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Dettagli prodotto</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="./css/details.css">
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body onLoad="initDetailsProd()">
		<header>
		<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar"></section>
		<section id="main_content">
		<% ProdottoBean pb = (ProdottoBean) session.getAttribute("prodDetails"); %>
		<% if(pb.getType().equalsIgnoreCase("Confezione")){ %>
			<center><h1><%=pb.getType() %> da <%=pb.getNumUovaConfezione() %> uova</h1></center>
			<center><img src="<%=pb.getImageURL() %>" alt="Image not found"></center>
			<center>
				<table id="details">
					<tr>
						<th> </th>
						<th> </th>
					</th>
					<tr><td>Codice Articolo: </td><td><%= pb.getCodice() %></td></tr>
					<%= pb.getDescription() %>
					<tr><td>Prezzo: </td><td> <%= pb.getPrezzo() %> EUR</td> </tr>
					<tr><td>Iva: </td><td> <%= pb.getIVA() %> %</td></tr>
				</table>
			</center>
		<%}else{ %>
			<center><h1><%= pb.getType() %></h1></center>
			<center><div class="tb_deriv_content">
				<table id="table_deriv">
					<tr> <th> </th></tr>
					<tr><td class="td_head"><center><b>Descrizione</b></center></td></tr>
					<tr><td><p><%= pb.getDescription() %></p></td></tr>
					<tr><td class="td_head"><center><b>Info sull'acquisto</b></center></td></tr>
					<tr>
						<td>
							<p>
								Codice Articolo: <%= pb.getCodice() %> <br>
								Prezzo: <%= pb.getPrezzo() %> EUR <br>
								Iva: <%= pb.getIVA() %> %
							</p>
						</td>
					</tr>
				</table>
			</div></center>
		<% }%>
		<div id="back_to_catalogo">
			<form action="./Catalogo.jsp" method="post">
				<button type="submit">Torna al catalogo</button>
			</form>
		</div>
		</section>
		<section id="right_sidebar"></section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/details_prod_js.js"></script>
	</body>
</html>