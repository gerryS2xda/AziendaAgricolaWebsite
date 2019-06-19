<%@ page language="java" import="java.util.ArrayList, com.project.bean.NotiziaBean" contentType="text/html; charset=utf-8" info="Home" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>News</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/news_styles.css"/>
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body onLoad="initNewsContent()">
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar">
			
		</section>
		<section id="main_content">
			<h1 class="title title_border">Notizie</h1> 
			<hr>
			<br>
			<div id="news_area"></div> <!--  usato da Jquery per inserire contenuto preso da DB tramite servlet -->
		</section>
		<section id="right_sidebar">
		
		</section>
		<footer class="news_footer">
			<%@ include file="footer_simple.jsp" %>
		</footer>
		<script type="text/javascript" src="script/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="script/news_js.js"></script>
	</body>
</html>