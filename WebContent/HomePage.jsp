<%@ page language="java" import="com.project.bean.UtenteBean, com.project.model.ShoppingCart" contentType="text/html; charset=utf-8" info="Home" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
		<title>Ovomont srl</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/home.css"/>
	</head>
	<body onLoad="initHome()">
		<div id="loginarea" class="logArea"></div>
		<header>
			<div class="logo">
				<img src="images/uovo_home.png" alt="Home logo image not found">
			</div>
			<img class="menu_icon" src="images/menu_icon_white.png" alt="Vertical Navigation bar" onclick="showVerticalNavBar()">
			<div class="close_menu" onclick="showVerticalNavBar()"> 
				<img src="images/x_white.png" alt="Vertical Navigation bar">
				<a href="javascript:void(0);">Chiudi menu</a>
			</div>
		</header>
		<section id="left_sidebar">
			
		</section>
		<section id="main_content">
			<div class="text">
				"Nessuna geometria ha ricavato la formula dell’uovo.
				Per il cerchio, la sfera c’è il pigreco,
				ma per la figura perfetta della vita non c’è quadratura".
			</div>
			<div class="entra"><a href="./Chi_siamo.jsp"><span>Entra</span> <img src="images/arrow_circle_down.png"></a></div>
			<div class="slideshow-container">				
							
				<div class="mySlides fade">
				   <img src="images/home_image1.jpg">
				</div>
				
				<div class="mySlides fade">
				  <img src="images/home_image2.jpg">
				</div>
				
				<div class="mySlides fade">
				  <img src="images/home_image3.jpg">
				</div>
				
			</div>
		</section>
		<section id="right_sidebar">
			<%  UtenteBean ub = (UtenteBean) session.getAttribute("user"); 
				ShoppingCart cart1 = (ShoppingCart) request.getSession().getAttribute("cart"); //prendi il carrello dalla sessione
				int np = 0; //numero di prodotti presenti nel carrello
				if(cart1 != null){
					np = cart1.getListaProdotti().size();
				}
			%>
			<div class="vertical-menu">
				<a href="./Chi_siamo.jsp">Chi siamo</a>
				<a href="./Azienda.jsp">Azienda</a>
				<a href="./Catalogo.jsp">Prodotti</a>
				<a href="./News.jsp">News</a>
				<a href="./Contatti.jsp">Contatti</a>
				<% if(ub == null){ %>
					<a id="access_link" href="javascript:void(0);">Accedi</a>
				<%}else{
						if(ub.getUserType().equals("admin")){ %>
							<a  href="./adminArea.jsp">Benvenuto, <%=ub.getNome() %></a>
					<% }else if(ub.getUserType().equals("user")){ %>
						<a  href="./areaUtente.jsp">Benvenuto, <%=ub.getNome() %></a>
					<%} %>
					<a id="logout" href="#">Logout</a>
				<%} %>
				<a href="./Carrello.jsp"><div class="cart_container"><img class="cart cart_vert" src="images/cart_white.png" alt="Carrello"><span class="contatore count_vert"><%=np %></span></div>Carrello</a>
			</div>
		</section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/home_js.js"></script>
	</body>
</html>