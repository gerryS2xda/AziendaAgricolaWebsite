
function initCatalogo(){
	$.post("catalogo-contr", {"action": "init"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			initProdottiConfezioni(o, size);
			initProdottiDerivati(o, size);
			$("#main_content").show();
			$("footer").show();
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function initProdottiConfezioni(o, size){
	var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
	var count = 0;	//usato per sistemare i prodotti nella tabella
	for(var i = 0; i < size; i++){
		var k = o["prod"+i];
		if(k.tipo.includes("Confezione")){
			if(count % 2 == 0){
				str += "<br>";
			}
			str+= "<div class=\"prodotto\"><div class=\"image_prod\"><a href=\"catalogo-contr?action=view_det&prodBean="+k.codice+ "\" title=\"Clicca per visualizzare i dettagli del prodotto\">" +
				"<img src=\"" + k.immagine + "\" alt=\"Image not available\"></a></div>" + 
				"<form class=\"text_prod\"><h3>Confezione da " + k.numUovaConf + " uova</h3>";
			if(k.sconto != 0){
				str+= "Prezzo: <span class=\"price_txt text_barrato\">"+ k.prezzo +" €</span> " + k.prezzoScontato + " €<br>"+
   						"<span class=\"sconto_txt\">Scontato del " + k.sconto + "%</span><br>";
			}else{
				str += "Prezzo: "+ k.prezzo +" €<br>"
			}
			str+= "Quantità: <input type=\"number\" id=\"quantID\" name=\"quantity\" min=\"1\" max=\"100\" value=\"1\"><br>" + 
    				"<br><button type=\"button\" onclick=\"addProd('"+k.codice+ "', quantity.value);\">Aggiungi al carrello</button></form></div>";
			count++;
		}
	}
	$("#conf_prod_area").html(str);
}

function initProdottiDerivati(o, size){
	$("#prod_deriv_area").show();
	var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
	var count = 0;	//usato per sistemare i prodotti nella tabella
	for(var i = 0; i < size; i++){
		var k = o["prod"+i];
		if(!(k.tipo.includes("Confezione"))){
			if(count % 2 == 0){
				str += "<br>";
			}
			str+= "<div class=\"prodotto\"><div class=\"image_prod\"><a href=\"catalogo-contr?action=view_det&prodBean="+k.codice+ "\" title=\"Clicca per visualizzare i dettagli del prodotto\">" +
				"<img src=\"" + k.immagine + "\" alt=\"Image not available\"></a></div>" + 
				"<form class=\"text_prod\"><h3>" + k.tipo + "</h3>";
			if(k.sconto != 0){
				str+= "Prezzo: <span class=\"price_txt text_barrato\">"+ k.prezzo +" €</span> " + k.prezzoScontato + " €</br>"+
   						"<span class=\"sconto_txt\">Scontato del " + k.sconto + "%</span><br>";
			}else{
				str += "Prezzo: "+ k.prezzo +" €<br>"
			}
			str+= "Quantità: <input type=\"number\" id=\"quantID\" name=\"quantDer\" min=\"1\" max=\"100\" value=\"1\"><br>" + 
    				"<br><button type=\"button\" onclick=\"addProd('"+k.codice+ "', quantDer.value);\">Aggiungi al carrello</button></form></div>";
			count++;
		}
	}
	$("#prod_deriv_area").html(str);
}


function addProd(p, q){	//pulsante 'Aggiungi al carrello'	
	$.post("catalogo-contr", {"action": "addtocart", "prodID": p, "quantID": q}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp);
			$(".contatore").text(o.prodotti);	//incrementa il contatore del carrello posto nella NavBar
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}


$("#view_cart_button button").click(function(){ //pulsante 'Mostra carrello'
	window.location.href = "./Carrello.jsp";
});

function showProductOrder(item){
	var x = item.val();
	if(x != "-"){
		$.post("catalogo-contr", {"action": "order", "order_by": x}, function(resp, stat, xhr){
			if(xhr.readyState == 4 && stat == "success"){
				var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
				var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
				initProdottiConfezioni(o, size);
				initProdottiDerivati(o, size);
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
}

/* funzioni di utilita' */
/* calcola il numero di proprieta' presenti in un oggetto */
function sizeObject(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key)) size++;
	}
	return size;
};