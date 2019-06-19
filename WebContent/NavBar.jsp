<%@ page language="java" import="com.project.bean.UtenteBean, com.project.model.ShoppingCart" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Navigation Bar</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="css/nav_bar_style.css">
	</head>
<body onLoad="initNavBar()">
	<div class="div_blur"></div>
	<%  
		UtenteBean ub = (UtenteBean) session.getAttribute("user");
		ShoppingCart cart1 = (ShoppingCart) request.getSession().getAttribute("cart"); //prendi il carrello dalla sessione
		int np = 0; //numero di prodotti presenti nel carrello
		if(cart1 != null){
			  np = cart1.getListaProdotti().size();
		}
	%>
	<div id="loginarea" class="logArea"></div>
	<section id="nav_bar_main_content">
		<div class="nav">
			<a href="./HomePage.jsp"><img class="logo" src="images/uovo_logo.png" alt="Ovomont"></a>
			<% if(ub == null){ %>
				<ul class="nav_ul">
					<li><a class="nav_text no_user_txt" href="./Chi_siamo.jsp">Chi siamo</a></li>
					<li><a class="nav_text no_user_txt" href="./Azienda.jsp">Azienda</a></li>
					<li><a class="nav_text no_user_txt" href="./Catalogo.jsp">Prodotti</a></li>
					<li><a class="nav_text no_user_txt" href="./News.jsp">News</a></li>
					<li><a class="nav_text no_user_txt" href="./Contatti.jsp">Contatti</a></li>
					<li><a class="accedi nav_text no_user_txt" href="javascript:void(0);">Accedi</a></li>
					<li><a class="nav_text no_user_txt cart_style cart" href="./Carrello.jsp"><div class="cart_container"><img class="cart" src="images/cart.svg" alt="Carrello"><span class="contatore"><%=np %></span></div></a></li>
				</ul>	
			<%}else{ %>
				<ul class="nav_ul_user">
					<li><a class="nav_text" href="./Chi_siamo.jsp">Chi siamo</a></li>
					<li><a class="nav_text" href="./Azienda.jsp">Azienda</a></li>
					<li><a class="nav_text" href="./Catalogo.jsp">Prodotti</a></li>
					<li><a class="nav_text" href="./News.jsp">News</a></li>
					<li><a class="nav_text" href="./Contatti.jsp">Contatti</a></li>
					<li class="dropdown">
						<a class="nav_text dropdown_a" href="javascript:void(0);">Benvenuto, <%=ub.getNome() %></a>
						<div class="dropdown-content">
      						<% if(ub.getUserType().equals("admin")){ %>
      							<a  href="./adminArea.jsp">Admin area</a>
      						<%}else if(ub.getUserType().equals("user")){ %>
      							<a  href="./areaUtente.jsp">User area</a>
      						<%} %>
      						<a class="logout" href="javascript:void(0);">Logout</a>
    					</div>
					</li>
					<li><a class="nav_text cart_style cart_user_logged" href="./Carrello.jsp"><div><img class="cart_user_logged" src="images/cart.svg" alt="Carrello"><span class="contatore"><%=np %></span></div></a></li>
				</ul>
			<% } %>	
		</div>
	</section>
	<section id="right_nav_bar">
		<div class="arrow_label">
			<img src="images/arrow_circle_white_left.png" alt="Image not found">
		</div>
		<div class="vertical-menu">
			<a href="./HomePage.jsp">Home</a>
			<a href="./Chi_siamo.jsp">Chi siamo</a>
			<a href="./Azienda.jsp">Azienda</a>
			<a href="./Catalogo.jsp">Prodotti</a>
			<a href="./News.jsp">News</a>
			<a href="./Contatti.jsp">Contatti</a>
			<% if(ub == null){ %>
				<a class="accedi" href="javascript:void(0);">Accedi</a>
			<%}else{
					if(ub.getUserType().equals("admin")){ %>
						<a  href="./adminArea.jsp">Benvenuto, <%=ub.getNome() %></a>
				<% }else if(ub.getUserType().equals("user")){ %>
					<a  href="./areaUtente.jsp">Benvenuto, <%=ub.getNome() %></a>
				<%} %>
				<a class="logout" href="javascript:void(0);">Logout</a>
			<%} %>
			<a href="./Carrello.jsp"><div class="cart_container"><img class="cart_vert" src="images/cart.svg" alt="Carrello"><span class="contatore"><%=np %></span></div> Carrello</a>
		</div>
	</section>
	<!--  spazio bianco per separare la barra di navigazione dal contenuto della pagina  -->
	<div id="compact"></div>
	<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="script/navBar.js"></script>
</body>
</html>