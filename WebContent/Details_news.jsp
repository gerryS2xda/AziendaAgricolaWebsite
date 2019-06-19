<%@ page language="java" import="java.time.LocalDate, java.time.format.DateTimeFormatter, com.project.bean.NotiziaBean" contentType="text/html; charset=utf-8" info="Home" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>News</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/details_news_style.css"/>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body>
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar">
			
		</section>
		<section id="main_content">
			<% NotiziaBean nb = (NotiziaBean) session.getAttribute("newsBean"); 
				if(nb == null){/*go to error page */}
			%>
			<h1 id="titolo"><%=nb.getTitle() %></h1> <br>
			<img class="news_img" src="<%= nb.getURLImage() %>" alt="No preview image available">
			<div class="text">
				<p><%= nb.getNewsContent() %></p>
			</div>
			<div class="news_info">
				<p>Notizia pubblicata il <%= LocalDate.parse(nb.getDataInserimento()).format(DateTimeFormatter.ofPattern("d MMM uuuu")) %>. L'ultima modifica risale il <%= LocalDate.parse(nb.getDateofLastModified()).format(DateTimeFormatter.ofPattern("d MMM uuuu")) %> </p>
			</div>
		</section>
		<section id="right_sidebar">
		
		</section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
	</body>
</html>