<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
	<head>
		<title>L'azienda</title>
		<link type="text/css" rel="stylesheet" href="css/azienda_stile.css">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body>
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar"></section>
		<section id="main_content">
			<img src="images/azienda_1.jpg" alt="Stabilimento image not found" id="imgStabilimento">
			<h1 id="pres">L'AZIENDA</h1>
			<div id="container">
				<hr class="preshr">
				<div class="azInfo">
					L'azienda "Ovomont", operativa dal mese di luglio 2018,
					nasce dalla passione per la produzione zootecnica-avicola ed è l'unica azienda biologica, 
					per la produzione di uova, presente ad oggi nella Regione Campania e nel Sud Italia 
					che ha come obiettivo principale la fornitura di un prodotto chiaramente "biologico" e di altissima qualità.
				</div>
				<h2 class="azProd">Il nostro processo produttivo</h2>
				<hr class="preshr">
				<div class="images_div">
					<img src="images/azienda_2.jpg" alt="Image not found" class="images" id="media">
					<img src="images/azienda_3.jpg" alt="Image not found" class="images">
				</div>
				<div class="azInfo">
					Come produttori di uova riteniamo che fornire un prodotto biologico di qualità e prendersi cura del benessere animale, 
					siano due facce della stessa medaglia. Ovomont è un'azienda certificata così come le pratiche di allevamento, 
					conformi alle rigorose tecniche di riferimento e progettate per fornire elevati standard di benessere per tutti i nostri animali.
					Lavoriamo esclusivamente con i prodotti messi a disposizione dalla natura. Una filosofia che mira ad un sistema di gestione dell'azienda 
					agricola basato sull'interazione tra le migliori pratiche ambientali, la salvaguardia e la tutela delle risorse naturali, 
					l'applicazione di rigorosi criteri in materia di benessere animale ed una produzione che risponde ad un mercato specifico di consumatori.
					Il rispetto verso l'ambiente in cui viviamo ci conduce quotidianamente a fare affidamento sulle risorse rinnovabili, 
					evitando l'inquinamento dell'ambiente ed in particolare delle risorse preziose come la terra e l'acqua.
				</div>
				<h2 class="azProd">Le nostre galline</h2>
				<hr class="preshr">
				<div class="images_div">
					<img src="images/azienda_4.jpg" alt="Image not found" class="images" id="media2">
					<img src="images/azienda_5.jpg" alt="Image not found" class="images">
				</div>
				<div class="azInfo">
					La metodologia di allevamento e le tecniche utilizzate sono fondamentali per salvaguardare il benessere animale 
					e condurre un'avicoltura biologica e sostenibile.
					Le nostre galline vivono in spazi molto vasti, con una densità molto bassa e che consente loro di muoversi in assoluta libertà, 
					svolazzare da una parte all'altra, razzolare, prendersi cura del loro piumaggio stirandosi le ali, 
					fare bagni di sabbia e andare alla scoperta dell'ambiente circostante banchettando.
					Nei nostri allevamenti, le galline hanno accesso all'esterno con oltre 4 mq di spazio per gallina a disposizione 
					e all'interno del capannone la densita' non raggiunge le 6 galline per mq.
					Le nostre galline sono nutrite con mangimi ottenuti da agricoltura biologica per soddisfare il fabbisogno nutrizionale dell'animale 
					nei vari stadi di sviluppo. Per garantire la fornitura di mangimi di altissima qualità, l'azienda ha scelto come fornitore ufficiale 
					la società cooperativa agricola PROGEO, leader in Italia nella produzione di mangimi da agricoltura biologica certificati.
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