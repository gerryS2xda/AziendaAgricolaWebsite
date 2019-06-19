<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Chi siamo</title>
		<link type="text/css" rel="stylesheet" href="css/chi_siamo_style.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body>
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar"></section>
		<section id="main_content">
			<div id="title_container">
				<img src="images/chi_siamo_1.png" alt="Image not found" id="imggalline">
				<h1 id="pres">CHI SIAMO</h1>
			</div>
			<div id="container">
				<hr class="preshr">
				<div class="presInfo">
					"C'era una volta un contadino che aveva un bel pollaio con galli e tante galline e ogni anno, 
					in primavera, nascevano tante belle nidiate di pulcini pigolanti, 
					covati con cura dalle chiocce. Ma una gallina era speciale perchè il suo uovo era tutto d'oro 
					e ogni mattina il contadino lo trovava nella cova, bello, grosso e brillante. 
					Lo raccoglieva tutto gongolante e si fregava le mani dalla contentezza. 
					Ma era un ingrato e non rivolgeva mai un ringraziamento alla prodigiosa gallina. 
					Una mattina trovò un uovo più bello e grosso del solito e pensò: "Se la gallina depone ogni giorno un uovo d'oro, 
					chissà  quanto oro ha dentro la sua pancia, lo voglio tutto e subito!" 
					Detto e fatto, prese un coltello e squartò la gallina! Ma le viscere della povera bestia erano normali, 
					come quelle di tutte le altre galline. E così il contadino restò a bocca asciutta, 
					la gallina non aveva dentro il suo corpo un tesoro ed ora era morta e non avrebbe più fatto uova d'oro!
					...le favole di Esopo
				</div>
				<br>
				<h2 class="presProd">I nostri obiettivi</h2>
				<hr class="preshr">
				<img src="images/chi_siamo_2.png" alt="Image not found" id="imguova">
				<br>
				<br>
				<div class="presInfo">
					La mission aziendale è quella di trasmettere la passione, il coraggio e la volontà  di costruire qualcosa di solido e valido 
					per se e per tutti quelli che credono nel progetto, non con l'avidità  e l'insaziabilità  del contadino 
					che racconta Esopo ma facendo in modo che la gallina dalle uova d'oro possa vivere a lungo, forte e prosperosa.
				</div>
			</div>
		</section>
		<section id="right_sidebar">
		</section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
	</body>
</html>