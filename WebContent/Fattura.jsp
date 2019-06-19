<%@ page language="java" import="com.project.bean.*, java.util.ArrayList"  
	contentType="text/html; charset=utf-8" info="Contenuto della fattura" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Fattura</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link type="text/css" rel="stylesheet" href="./css/fattura_style.css">
		<link rel="icon" href="./images/icon_web_window_32.jpg" type="image/jpg" />
	</head>
	<body>
		<header>
			<%@ include file="NavBar.jsp" %>
		</header>
		<section id="left_sidebar"></section>
		<section id="main_content">
			<%
				UtenteBean cliente = (UtenteBean) session.getAttribute("user");
				if(cliente == null){
					//go to login page
				}
				FatturaBean fb = (FatturaBean) session.getAttribute("fattura");
				ArrayList<ComposizioneBean> prodotti = (ArrayList<ComposizioneBean>) session.getAttribute("prod_fattura");
				if(fb == null || prodotti == null){
					//go to page error
				}
			%>
			<div id="content_popup_area">
				<p id="info_azienda"><span>Ovomont srl</span><br>Via Provinciale 29, Campagna (SA)<br>Tel.: 089 345678/ E-mail: info@ovomont.it<br>P.IVA 05177690657</p>
				<div id="dati_fatt">
					<p id="dati_cliente" class="fatt_txt"><span>Dati cliente</span><br><%=cliente.getCognome() %> <%=cliente.getNome() %><br>
					<%=cliente.getAddress() %><br>Cod. fiscale: <%=cliente.getCodiceFiscale() %> <br></p>
					<p class="fatt_txt">Fattura Nr. <%= fb.getNumero() %><br>Data emissione: <%= fb.getDataEmissione() %> </p>
				</div>
				<div class="table_scroll">	
					<table id="tb_prod" class="table_data">
						<thead>
							<tr>
								<th>Cod.Articolo</th>
								<th>Descrizione</th>
								<th>Q.ta</th>
								<th>Prezzo uni.</th>
								<th>Sconto</th>
								<th>IVA </th>
								<th>Importo</th>
							</tr>
						</thead>
						<tbody>
							<% for(ComposizioneBean cb : prodotti){ 
								ProdottoBean pb = cb.getProdotto();
							%>
							<tr>
								<td><%= pb.getCodice()%></td>
								<%	String str = pb.getType();
									if(pb.getType().contains("Confezione")){
										str += " da " + pb.getNumUovaConfezione() + " uova";
									}
								%>
								<td><%= str %> </td>
								<td><%= cb.getQuantita() %> </td>
								<td><%= pb.getPrezzo() %> €</td>
								<td><%= cb.getSconto() %> %</td>
								<td><%= cb.getIVA() %> %</td>
								<td><%= cb.getPrezzoAcquisto() %> €</td>
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
				<div>
					<span id="tot_txt">Totale fattura: <%= fb.getImporto() %> €</span><br>
				</div>
			</div>
		</section>
		<section id="right_sidebar"></section>
		<footer>
			<%@ include file="footer_simple.jsp" %>
		</footer>
	</body>
</html>

