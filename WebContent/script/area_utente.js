//funzioni per la vertical side bar
$("#a1").click(function(){	//Link 'Account' della navBar
	removeSelectedClass();
	hideAll();
	$("#l1").addClass("active");
	$("#dashboard_content").show();
});
			
$("#a2").click(function(){ //Link 'Ordini in uscita' della navBar
	removeSelectedClass();
	hideAll();
	$("#l2").addClass("active");
	$("#current_order").show();
	showCurrentOrder();
});
			
$("#a3").click(function(){ //Link 'Casella di posta' della navBar
	removeSelectedClass();
	hideAll();
	$("#l3").addClass("active");
	$("#a6").click();  //simula il click sul link 'Posta arrivata'
	$("#mailbox").show();
});
			
$("#a4").click(function(){ //Link 'I miei ordini' della navBar
	removeSelectedClass();
	hideAll();
	$("#l4").addClass("active");
	showAllOrders();
	$("#my_orders").show();
});
			
$("#a5").click(function(){ //Link 'Statistiche' della navBar
	removeSelectedClass();
	hideAll();
	$("#l5").addClass("active");
	setStatisticInformation();
	$("#stats").show();
});

//pulsante 'Logout' presente in header
$("#logout").click(function(){
	$.post("user-controller", {"action": "logout"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			window.location.href = "HomePage.jsp";
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

			
function removeSelectedClass(){
	var i;
	for(i=1;i<6;i++)
		$("#l"+i).removeClass("active");
}
			
function hideAll(){
	$("#dashboard_content").hide();
	$("#current_order").hide();
	$("#mailbox").hide();
	$("#my_orders").hide();
	$("#stats").hide();
}

//funzione eseguita dopo il caricamento della pagina (onLoad <body>)
function hideAllbutDashboard(){
	$("#current_order").hide();
	$("#mailbox").hide();
	$("#my_orders").hide();
	$("#stats").hide();
}

//funzioni per la sezione 'Account'
$("#edit_admin").click(function(){
	var u = $("#user_data input");
	var s = $("#user_data span");
	var a = new Object();
	a.cf = s.eq(5).text();
	a.nome = s.eq(0).text();
	a.cognome = s.eq(1).text();
	a.dataN = s.eq(2).text();
	a.luogoN = s.eq(3).text();
	a.sex = s.eq(4).text();
	a.address = u.eq(0).val();
	a.telefono = u.eq(1).val();
	a.email = u.eq(2).val();
	a.username = u.eq(3).val();
	a.password = u.eq(4).val();
	a.type = "user";
	if(formMieiDatiValidation()){
		$.post("user-controller", {"action": $(this).val(), "utente": JSON.stringify(a)}, function(resp, statusTxt, xhr){
			if(statusTxt == "success" && xhr.readyState == 4){
				alert("Modifica dati eseguita correttamente");
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
});

//funzioni per la sezione 'Ordini in uscita'
function showCurrentOrder(){
	var cf = $("#user_data span").eq(5).text(); //dammi il CF del cliente per la ricerca nel DB
	$.post("fattura-contr", {"action": "current_order", "cf": cf}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
        	var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
        	var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
        	if(o.fattura == "NoFattura"){
				str += "<div class=\"no_mail_txt\">Nessun dato da mostrare</div>";
				$(".div_content").eq(0).html(str);
			}else{
				for(var i = 0; i < size; i++){
	        		var k = o["fattura"+i];	//prendi l'oggetto JS associato alla proprieta' 'news' dell'oggetto JS appena convertito 
	        		str+= "<tr><td>" + k.numero + "</td><td>"+ k.partenza +"</td>" +
	        			"<td>"+ k.arrivo +"</td><td>" + k.dataPartenza + "</td><td>"+ k.dataArrivo + "</td>"+
	        			"<td>" + k.importo + "€</td></tr>";
					
	        	}
	        	$("#tb_current tbody").html(str);
			}
        }else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});				
}

//funzioni per la sezione 'I miei ordini'
function showAllOrders(){
	$.post("fattura-contr", {"action": "all_order"}, function(resp,stat, xhr){
        if(xhr.readyState==4 && stat=="success"){
        	var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
        	var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
        	var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
        	if(o.fattura == "NoFattura"){
				str += "<div class=\"no_mail_txt\">Nessun dato da mostrare</div>";
				$(".div_content").eq(1).html(str);
			}else{
	        	for(var i = 0; i < size; i++){
	        		var k = o["fattura"+i];	//prendi l'oggetto JS associato alla proprieta' 'news' dell'oggetto JS appena convertito 
	        		str+= "<tr><td onclick=\"showContentPopupFattura($(this))\">" + k.numero + "</td><td>"+ k.partenza +"</td>" +
	        			"<td>"+ k.arrivo +"</td><td>" + k.dataPartenza + "</td><td>"+ k.dataArrivo + "</td>"+
	        			"<td>" + k.importo + "€</td></tr>";
					
	        	}
	        	$("#tb_all_order tbody").html(str);
			}
        }else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

//funzioni per la casella di posta
$("#a6").click(function(){	//Link 'Posta arrivata'
	$("#posta_inviata").hide();
	$("#posta_arrivata").show();
	showMailArrivate();
});

$("#a7").click(function(){ //Link 'Posta inviata'
	$("#posta_arrivata").hide();
	$("#posta_inviata").show();
	showMailInviate();
});

$("#scrivi").focus(showPopupScrivi);	//Link 'Scrivi'

function showPopupScrivi(){
	$("#scrivi_mail").addClass("popup_body");
	setDestinationField(); //riempi il campo di destinazione delle mail con un menu di selezione degli admin
	$("#scrivi_mail").show();
}

function removePopupScrivi(){
	document.new_mail.reset(); //resetta i campi del form per scrivere la nuova mail
	$("#mail_dest").css("border", "1px solid black"); //resetta i colori del bordo dell'input text
	$(".new_mail_txt_err").hide();
	$("#scrivi_mail").removeClass("popup_body");
	$("#scrivi_mail").hide();
}

$(".send_button").click(function(){ //pulsante 'Invia' nel popup di scrivi
	if(validateSelectDestinationEmail($("#mail_dest"), $('.new_mail_txt_err'))){
		var m = $("#user_data input").eq(2).val(); 	//mail del mittente
		var x = $("#mail_dest").val();
		var y = $("#mail_object").val();
		var z = $("#new_mail_content").val();
		$.post("posta-contr", {"action": "scrivi", "mail_mittente" : m, "mail_dest" : x, "mail_object": y, "new_mail_content":z }, function(resp, status, xhr){
			if(xhr.readyState == 4 && status == "success"){
				removePopupScrivi();
				alert("Mail inviata correttamente!!");
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
});

function setDestinationField(){
	$.post("user-controller", {"action": "get_admin"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var x = $("#mail_dest option"); 
			var y = 1; //il primo <option> deve essere senza valore;
			for(var i = 0; i < size; i++){
				var k = o["utente"+i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS 
				x.eq(y).val(k.email);
				x.eq(y).html(k.nome + " " + k.cognome);
				y++;
			}
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function validateSelectDestinationEmail(item, err){
	var x = item.val();
	var val = false;
	if(x == ""){ //errore campo vuoto
		err.show();
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else{ 
		err.hide();
		item.css("border","1px solid green");
		val = true;
	}
	return val;
}

function showMailInviate(){
	$.post("posta-contr", {"action": "show_posta_inv"}, function(resp, status, xhr){
		if(xhr.readyState == 4 & status=="success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto
			if(o.email == "NoEmail"){
				str += "<div class=\"no_mail_txt\">Nessuna email da mostrare</div>";
			}else{
				var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
				//costruzione intestazione della tabella
				str+= "<div class=\"table_content_posta\"><table id=\"tb_mail_inviata\" class=\"table_data\"><thead>" +
						"<tr><th class=\"th_empty\"> </th><th class=\"th_short\"> Destinatario </th><th> Oggetto </th>" + 
							"<th class=\"th_short\"> Data e ora </th></tr></thead><tbody>";
				//costruzione del contenuto della tabella
				for(var i = 0; i < size; i++){
					var k = o["email"+i];	//prendi l'oggetto JS associato alla proprieta' 'email' dell'oggetto JS appena convertito 
					if(k.read == false){
						str += "<tr class=\"tr_bold\">";
					}else{str += "<tr>";}
					str+= "<td><input type=\"checkbox\" onchange=\"deleteEmail($(this))\"><span class=\"email_id\">" + k.id +"</span></td>" +
						"<td>" + k.destination + "</td><td class=\"td_mail_object\" onclick=\"showContentPopup($(this))\">" + k.object + "</td>" +
						"<td>" + k.data + "  " + k.ora + "</td> "+ 
						"<td class=\"hide_td\">" + k.content + "</td><td class=\"hide_td\">" + k.read + "</td></tr>";	
				}
				str += "</tbody></table></div>";
			}
			$("#posta_inviata").html(str);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function showMailArrivate(){
	$.post("posta-contr", {"action": "show_posta_arr"}, function(resp, status, xhr){
		if(xhr.readyState == 4 & status=="success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto
			if(o.email == "NoEmail"){
				str += "<div class=\"no_mail_txt\">Nessuna email da mostrare</div>";
			}else{
				var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
				//costruzione intestazione della tabella
				str+= "<div class=\"table_content_posta\"><table id=\"tb_mail_arrivata\" class=\"table_data\">" +
						"<thead><tr><th class=\"th_empty\"> </th><th class=\"th_short\"> Mittente </th><th> Oggetto </th>" +
							"<th class=\"th_short\"> Data e ora </th></tr></thead><tbody>";
				//costruzione del contenuto della tabella
				for(var i = 0; i < size; i++){
					var k = o["email"+i];	//prendi l'oggetto JS associato alla proprieta' 'email' dell'oggetto JS appena convertito 
					if(k.read == false){
						str += "<tr class=\"tr_bold\">";
					}else{str += "<tr>";}
					str+= "<td><input type=\"checkbox\" onchange=\"deleteEmail($(this))\"><span class=\"email_id\">" + k.id +"</span></td>" +
						"<td>" + k.mittente + "</td><td class=\"td_mail_object\" onclick=\"showContentPopup($(this))\">" + k.object + "</td>" +
						"<td>" + k.data + "  " + k.ora + "</td> "+ 
						"<td class=\"hide_td\">" + k.content + "</td><td class=\"hide_td\">" + k.read + "</td></tr>";	
				}
				str+= "</tbody></table></div>";
			}
			$("#posta_arrivata").html(str);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function deleteEmail(item){	//usata da checkbox per cancellare le email
	if(item.is(':checked')){
		var id = item.next().text(); //dammi l'ID della mail da cancellare
		$.post("posta-contr", {"action": "delete_mail", "id_email" : id}, function(resp, stat, xhr){
			if(xhr.readyState == 4 && stat == "success"){
				item.parents("tr").remove();
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			} 
		});
	}
}

//funzioni per il popup 'Contenuto email'
function showContentPopup(item){
	riempiPopup(item);
	$("#content_popup").show();
	$("#content_popup").addClass("popup_body");
}

function riempiPopup(item){
	var row = item.parents("tr"); //dammi la riga <tr> su cui eseguire le azioni  
	var td = row.find("td"); //dammi tutti gli <td> che sono discendenti di <tr> selezionato prima
	updateLetturaAttribute(td.eq(0).find("span").text(), row); //aggiorna il valore dell'attr. lettura presente nel DB
	row.removeClass("tr_bold");	//se l'attributo lettura e' stato modificato, rimuovi il grassetto dalla riga <tr>
	var x = $(".th_short").text(); //mittente o destinatario
	if(x.includes("Mittente")){
		$("#content_a_txt").hide();
		$("#content_da_txt").show();
		$("#content_da_txt span").html(td.eq(1).text());
		$("#risp_mail").show();
		$("#write_m").hide();
	}else if(x.includes("Destinatario")){
		$("#content_da_txt").hide();
		$("#content_a_txt").show();
		$("#content_a_txt span").html(td.eq(1).text());
		$("#risp_mail").hide();
		$("#write_m").show();
	}
	$("#content_object_txt span").html(td.eq(2).text());
	$("#content_popup_area p").html(td.eq(4).text());
}

function updateLetturaAttribute(id, tr){
	var l = tr.find("td").eq(5).text();
	if(l == "false"){
		$.post("posta-contr", {"action": "update_lett", "id_email" : id}, function(resp, stat, xhr){
			if(xhr.readyState != 4 || stat != "success"){
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
}

$("#risp_mail").click(function(){
	removeContentPopup();
	showPopupScrivi();
	var d = $("#content_da_txt span").text();
	$("#mail_dest").val(d);
	$("#mail_object").val("Re: " + $("#content_object_txt span").text());
});

$("#write_m").click(function(){
	removeContentPopup();
	showPopupScrivi();
});

$("#back_content").click(removeContentPopup);

function removeContentPopup(){
	$("#content_popup").removeClass("popup_body");
	$("#content_popup").hide();
}

//funzioni per il popup 'Fattura'
function showContentPopupFattura(item){
	riempiPopupFattura(item);
	$("#popup_fattura").show();
}

function riempiPopupFattura(item){
	var id = parseInt(item.text());
	var f = $(".fatt_txt span"); //per riempire gli <span> per il numero di fattura e data emissione
	$.post("fattura-contr", {"action": "show_fattura", "key_fattura": id}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var fattura = o["acquisto0"].fat;	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito 
			f.eq(0).html(fattura.numero);
			f.eq(1).html(fattura.dataEmissione);
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			for(var i = 0; i < size; i++){
				var k = o["acquisto"+i];
				var prod = k.prod; //dammi l'oggetto JS che contiene i prodotti associati alla fattura
				str+= createTableRowProduct(prod, k);
			}
			$("#tb_fattura tbody").html(str);
			$("#tot_txt span").eq(0).html(fattura.importo);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

//prod e' l'oggetto JS dei prodotti JS associati alla fattura, obj e' l'oggetto JS associato ai dati di acquisto del prodotto
function createTableRowProduct(prod, obj){
	var str = "";
	var str2 = prod.tipo;	
	if(prod.numUovaConf > 0){ str2 += " da " + prod.numUovaConf + " uova"; }
	str+="<tr><td>"+ prod.codice + "</td><td>" + str2 + "</td><td>" + obj.quantita + "</td><td>"+ prod.prezzo + "€</td>" +
		"<td>" + obj.sconto + "%</td><td>" + obj.iva + "%</td><td>" + obj.prezzo + "€</td></tr>";
	return str;
} 

$("#hide_fatt_button").eq(0).click(removeContentPopupFattura);

function removeContentPopupFattura(){
	$("#popup_fattura").hide();
}

//funzioni per la sez. 'Statistica'
function setStatisticInformation(){
	var cf = $("#user_data span").eq(5).text(); //dammi il CF del cliente per la ricerca nel DB
	var x = $(".info_dash_text");
	$.post("fattura-contr", {"action": "stats_user", "cliente": cf}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			x.eq(0).html(o.thismonth + "€");
			x.eq(1).html(o.lastmonth + "€");
			x.eq(2).html(o.thisyear + "€");
			x.eq(3).html(o.lastyear + "€");
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

//funzioni di validazione
function styleForErrorTextInput(item){
	item.css("border","1px solid red");
}

//validazione form della sezione 'Account'
function validateAddress(item, maxlength){
	var x = item.val();
	var err = $(".account_text_err").eq(0);
	var re = /^[A-Za-z0-9, ]+(([\(A-Z\)]{4,4})|(\([A-Za-z]+)\))$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length > maxlength){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Valore troppo lungo!! (max " + maxlength + " caratteri)");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.empty();
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Valore inserito non valido!! Es. Nome via, 2 (Num. civico) Citta' (Prov)");
	}
	return val;
}

function validateTelephone(item, maxlength){
	var x = item.val();
	var err = $(".account_text_err").eq(1);
	var re = /^\d{10}$/;
	var val = false;
	if(x == ""){ //campo vuoto
		item.css("border","1px solid grey");
		err.html("");
		val = true; //il campo non e' obbligatorio
	}else if(x.length > maxlength){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Numero di telefono non valido!!!! (max " + maxlength + " numeri)");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.empty();
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Numero di telefono non valido!!");
	}
	return val;
}

function validateEmail(item, maxlength, err){
	var x = item.val();
	var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		err.show();
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length > maxlength){ //codice errore per stringa troppo lunga
		err.show();
		styleForErrorTextInput(item);
		err.html("Valore troppo lungo!! (max " + maxlength + " caratteri)");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.hide();
		item.css("border","1px solid green");
		val = true;
	}else{
		err.show();
		styleForErrorTextInput(item);
		err.html("Email non valida!!");
	}
	return val;
}

function validateUsername(item){
	var x = item.val();
	var err = $(".account_text_err").eq(3);
	var re = /^\w{6,12}$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length < 6 || x.length > 12){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Username deve essere compreso tra 6 e 12 caratteri");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.empty();
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Input non valido!! Può contenere solo lettere e numeri");
	}
	return val;
}

function validatePassword(item){
	var x = item.val();
	var err = $(".account_text_err").eq(4);
	var re = /^[A-Za-z0-9]{6,20}$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length < 6 || x.length > 20){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Password deve essere compreso tra 6 e 20 caratteri");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.empty();
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Input non valido!! Può contenere solo lettere e numeri");
	}
	return val;
}

function formMieiDatiValidation(){
	var flag = false;
	var f = $("#user_data");
	var input = f.find("input");
	if(validateAddress(input.eq(0), 50)){
		if(validateTelephone(input.eq(1), 10)){
			if(validateEmail(input.eq(2), 50,  $('.account_text_err').eq(2))){
				if(validateUsername(input.eq(3))){
					if(validatePassword(input.eq(4))){
						flag = true;
					}
				}
			}
		}
	}
	if(flag){
		return true;
	}else{
		alert("Ci stanno uno o piu' campi che presentano degli errori!! Non si puo' proseguire");
		return false;
	}
}

/* funzioni per la parte responsive */

$("#side_back").mouseenter(function(){
	var x = parseInt($(this).css("width"));
	if(x == 20){
		openSideNavBar();
	}		
});

$("#side_back").mouseleave(function(){
	var x = parseInt($(this).css("width"));
	if(x == 220){
		closeSideNavBar();
	}		
});

function openSideNavBar(){
	$("#side_back").css("width", "220px");
	$("#sidebar").show();
	$("#content_page").css("margin-left", "220px");
}

function closeSideNavBar(){
	$("#side_back").css("width", "20px");
	$("#sidebar").hide();
	$("#content_page").css("margin-left", "20px");
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