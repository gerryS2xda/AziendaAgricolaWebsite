//funzioni per la vertical side bar
$("#a1").click(function(){  //Link 'Dashboard' della navBar
	removeActiveClass();
	hideAllContent();
	$("#li1").addClass("active");
	$("#dashboard_content").show();
	showMailInviateDashboard();
});

$("#a2").click(function(){  //Link 'Gestione account' della navBar
	removeActiveClass();
	hideAllContent();
	$("#li2").addClass("active");
	$("#result_search_area").hide();
	$("#manage_account_section").show();
});

$("#a4").click(function(){  //Link 'Gestione sito' della navBar
	removeActiveClass();
	hideAllContent();
	$("#li4").addClass("active");
	loadConfigforManageSiteSection();
	$("#manage_website").show();
});

$("#a5").click(function(){  //Link 'Casella di posta' della navBar
	removeActiveClass();
	hideAllContent();
	$("#li5").addClass("active");
	$("#a6").click();  //simula il click sul link 'Posta arrivata'
	$("#mailbox").show();
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

function removeActiveClass(){
	for(var i = 1; i<=5; i++){
		$("#li"+i).removeClass("active");
	}
}

function hideAllContent(){
	$("#dashboard_content").hide();
	$("#manage_account_section").hide();
	$("#manage_website").hide();
	$("#mailbox").hide();
}

//funzione eseguita dopo il caricamento della pagina
function hideAllContentExceptDashboard(){
	$("#scrivi_mail").removeClass("popup_body");
	$("#manage_account_section").hide();
	$("#manage_website").hide();
	$("#mailbox").hide();
	$("#scrivi_mail").hide();
	loadConfigForDashboard();
}

//funzioni per la Dashboard (i riquadri)
function loadConfigForDashboard(){
	setNumeroUtentiReg();
	setInfoFatturaDashboard();
	showMailInviateDashboard();
}

function setNumeroUtentiReg(){
	$.post("user-controller", {"action": "count"}, function(resp, statTxt, xhr){
		if(xhr.readyState == 4 && statTxt === "success"){
			var o = JSON.parse(resp);
			$(".info_dash_text").eq(0).text(o.nUser);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function setInfoFatturaDashboard(){ //ottieni informazioni relativa alla tab. fattura per i riquadri nella Dashboard
	$.post("fattura-contr", {"action": "dashboard_info_fatture"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp);
			$(".info_dash_text").eq(1).text(o.incasso + " €");
			$(".info_dash_text").eq(2).text(o.ordini);
			$(".info_dash_text").eq(3).text(o.nFatture);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

//funzioni per la dashboard area
$("#ic1").click(function(){	//animazione widget 'Dati personali'
	$("#content1").slideToggle("slow");
});

$("#ic2").click(function(){ //animazione widget 'Email arrivate'
	$("#content2").slideToggle("slow");
});

function showMailInviateDashboard(){
	$.post("posta-contr", {"action": "show_mail_dashboard"}, function(resp, status, xhr){
		if(xhr.readyState == 4 & status=="success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto
			if(o.email == "NoEmail"){
				str += "<li><div class=\"email_txt_div\">" + 
				"<p class=\"email_txt_body_link\">Nessuna mail da mostrare</p></div></li>";
			}else{
				var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
				for(var i = 0; i < size && i<6; i++){ //mostra solo le prime 6 mail arrivate di recente
					var k = o["email"+i];	//prendi l'oggetto JS associato alla proprieta' 'email' dell'oggetto JS appena convertito 
					str+= "<li><div class=\"email_txt_div\"><span class=\"email_txt_body\"> Da: " + k.mittente +
					"/ Il " + k.data + "</span><p class=\"email_txt_body_link\">" + k.object + "</p></div></li>";
				}
			}
			$(".mail_arrivate").html(str);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

//se si preme sul contenuto widget 'Email arrivate', si simula il click del link 'Casella di posta'
$(".mail_arrivate").click(function(){
	$("#a5").click(); //link 'Casella di posta'
});

//funzioni per la sezione 'Gestione account'
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
	a.type = "admin";
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

$("#add_admin").click(function(){
	$.ajax({
		type: "post",
		url: "user-controller",
		data: {"action": "new_admin"},
		success:function(resp, stat, xhr){ 
            if (xhr.readyState == 4 && stat == "success") {
                window.location.href = "RegistrationForm.jsp?userType=admin";
            }else{
            	window.location.href = "./error.jsp"; //pagina errore 404
            }
		},
		error: function (xhr, ajaxOptions, thrownError) {
			window.location.href = "./error.jsp"; //pagina errore 404
	    }
	});
});

$("#search_user").click(function(){
	var x = $("#search_area input").eq(0);
	$.post("user-controller", {"action": "search", "key" : x.val()}, function(resp, statTxt, xhr){
		if(xhr.readyState == 4 && statTxt == "success"){
			$("#result_search_area").show();
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			var k = o.utente;	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito
			if(k != "No result"){ 
				str+= "<tr><td>" + k.cf + "</td><td>" + k.cognome + "</td><td>" + k.nome + "</td><td>" + k.dataN + "</td>" +
					"<td>" + k.username + "</td><td>" + k.type + "</td><td><select id=\"admin_action\" onchange=\"searchAction();\">" +
					"<option value=\"\"></option> <option value=\"delete\">Cancella</option> "+
					"<option value=\"admin\">Nomina Admin</option></select></td></tr>";
				$("#result_search_area span").html("");
				$("#result_search_area table").show();
				$("#result_search_area table tbody").html(str);
			}else{
				$("#result_search_area table").hide();
				$("#result_search_area span").text("Nessun utente trovato!!");
			}
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

function searchAction(){
	var x = $("#admin_action").val(); //azione selezionata
	var y = $("#result_search_area table td").eq(0);
	if(x != ""){
		$.post("user-controller", {"action":x, "cfkey": y.text() }, function(resp, statTxt, xhr){
			if(statTxt == "success" && xhr.readyState == 4){
				var o = JSON.parse(resp);
				if(o.done){
					alert("Azione eseguita correttamente!! Effettuare di nuovo la ricerca per verificare le modifiche ;)");
				}else{
					window.location.href = "./error.jsp"; //pagina errore 404
				}
			}
		});
	}
}

//funzioni per la sezione 'Gestione sito' -> prodotti
function loadConfigforManageSiteSection(){
	//configurazione per la parte 'Prodotti'
	$("#hide_prod").click();
	document.prod_form.reset(); //resetta il form 'Prodotti'
	$("#prod_form input").css("border", "1px solid black"); //resetta i colori dei textfield dei prodotti
	$(".prod_txt_err").html(""); //resetta il text error relativo ai prodotti
	
	//configurazione per la parte 'News'
	document.news_form.reset(); //resetta il form 'News'
	$("#hide_news").click();
	$(".news_txt_err").html(""); //resetta il text error relativo alle news
	$("#news_form input").css("border", "1px solid black"); //resetta i colori dei textfield dei prodotti
}

$("#add_prod").click(function(){ //pulsante 'Aggiungi prodotto'
	var p = $("#prod_form input");	//prende tutti gli elementi <input> che sono nel form con id='add_prod'
	var o = new Object();
	o.codice = p.eq(0).val();
	o.dataScadenza = p.eq(1).val();
	o.dataConf = p.eq(2).val();
	o.prezzo = p.eq(3).val();
	o.sconto = p.eq(4).val();
	o.iva = p.eq(5).val();
	o.tipo = p.eq(6).val();
	o.numUovaConf = p.eq(7).val(); 
	o.immagine = p.eq(8).val();
	o.descrizione = $("#prod_form textarea").eq(0).val();
	if(formProdottoValidation()){ //prima di inviare si verificano i dati inseriti nei campi del form
		$.post("product-contr", {"action":"add", "new_prod": JSON.stringify(o)}, function(resp, stat, xhr){
			if(xhr.readyState == 4 && stat == "success"){
				alert("Prodotto aggiunto correttamente al catalogo!!");
				document.prod_form.reset();
				$("#show_prod").click();
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
});

$("#show_prod").click(function(){ //pulsante 'Mostra prodotti'
	$(this).hide();
	$("#hide_prod").show();
	$.post("product-contr", {"action": "show_prod"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			for(var i = 0; i < size; i++){
				var k = o["prod"+i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito 
				var str2 = k.tipo;	
				if(k.numUovaConf > 0){ str2 += " da " + k.numUovaConf + " uova"; }
				str+= "<tr><td>" + k.codice + "</td><td>" + str2 + "</td>" +
					"<td><input type=\"text\" class=\"price_input\" value=\"" + k.prezzo+ "\" onblur=\"validatePrezzo($(this), $(this).next())\"> € <p class=\"prod_txt_err_table\"></p></td>" +
					"<td><input type=\"number\" min=\"0\" max=\"100\" value=\"" + k.sconto + "\">%</td> "+
					"<td><input type=\"number\" min=\"0\" max=\"100\" value=\"" + k.iva + "\">%</td> "+ 
					"<td><input type=\"text\" value=\"" + k.immagine + "\" onblur=\"validateLinkImmagine($(this), 100, $(this).next())\"> <p class=\"prod_txt_err_table\"></p></td>" +
					"<td><select name=\"prod_action\" onChange=\"manageProduct($(this));\">" +
					"<option value=\"\">-</option><option value=\"edit_save\">Salva</option><option = value=\"delete\">Cancella</option></select></td></tr>";
	
			}
			$("#tb_prod").show();
			$("#tb_prod tbody").html(str);
			$(".prod_txt_err_table").hide();
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

$("#hide_prod").click(function(){ //pulsante 'Nascondi prodotti'
	$(this).hide();
	$("#show_prod").show();
	$("#tb_prod").hide();
});

function manageProduct(item){ //azioni da eseguire su un prodotto (onChange di <select name=\"prod_action\" ...)
	var row = item.parents("tr"); //dammi la riga <tr> su cui eseguire le azioni  
	var td = row.find("td"); //dammi tutti gli <td> che sono discendenti di <tr> selezionato prima
	var act = item.val(); //vai a prendere il valore di <select> scorrendo il DOM
	var key = td.eq(0).text();
	if(act != "-"){
		if(act == "edit_save"){
			var o = new Object();
			o.codice = key;
			o.prezzo = td.eq(2).children().val(); // prendi il valore associato all'attr. value di <input> che e' discendente di <td>
			o.sconto = td.eq(3).children().val();
			o.iva = td.eq(4).children().val();
			o.immagine = td.eq(5).children().val();
			$.post("product-contr", {"action": "edit_save", "edit_prod": JSON.stringify(o)}, function(resp, stat, xhr){
				if(xhr.readyState == 4 && stat == "success"){
					alert("Modifica dati del prodotto eseguita con successo!!");
					item.val("-");
				}else{
					window.location.href = "./error.jsp"; //pagina errore 404
				}
			});
		}else if(act == "delete"){
			$.post("product-contr", {"action": "delete", "pkey" : key}, function(resp, stat, xhr){
				if(xhr.readyState == 4 && stat == "success"){
					row.remove();
				}else{
					window.location.href = "./error.jsp"; //pagina errore 404
				}
			});
		}
	}
}

//funzioni riservate per la parte 'Notizie' nella sezione 'Gestione Sito'
$("#add_news").click(function(){ //pulsante 'Aggiungi Notizia'
	var n = $("#news_form input");	//prende tutti gli elementi <input> che sono nel form con id='news_form 
	var o = new Object();
	o.title = n.eq(1).val();
	o.content = $("#news_form textarea").eq(0).val();
	o.type = $("#news_form select").eq(0).val();
	o.image = n.eq(0).val(); ;
	if(formNewsValidation()){ //prima di inviare si verificano i dati inseriti nei campi del form
		$.post("news-controller", {"action":"add", "new_news": JSON.stringify(o)}, function(resp, stat, xhr){
			if(xhr.readyState == 4 && stat == "success"){
				alert("Notizia aggiunta correttamente!!");
				document.news_form.reset();
				$("#show_news").click();
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
});

$("#show_news").click(function(){ //pulsante 'Mostra prodotti'
	$(this).hide();
	$("#hide_news").show();
	$.post("news-controller", {"action": "show_news"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			for(var i = 0; i < size; i++){
				var k = o["news"+i];	//prendi l'oggetto JS associato alla proprieta' 'news' dell'oggetto JS appena convertito 
				str+= "<tr><td class=\"td_hide\">" + k.id + "</td><td><input type=\"text\" value=\"" + k.title + "\"></td>" +
					"<td onclick=\"showContentNewsPopup($(this))\">Premi qui</td><td>" + k.dataUlMod + "</td><td><input type=\"text\" value=\"" + k.type + "\"></td> "+ 
					"<td><input type=\"text\" value=\"" + k.image + "\" onblur=\"validateLinkImmagine($(this), 100, $(this).next())\"> <p class=\"news_txt_err_table\"></p></td>" +
					"<td><select name=\"news_action\" onChange=\"manageNews($(this));\">" +
					"<option value=\"\">-</option><option value=\"edit_save\">Salva</option><option = value=\"delete\">Cancella</option></select></td></tr>";
	
			}
			$("#tb_news").show();
			$("#tb_news tbody").html(str);
			$(".news_txt_err_table").hide();
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

$("#hide_news").click(function(){ //pulsante 'Nascondi prodotti'
	$(this).hide();
	$("#show_news").show();
	$("#tb_news").hide();
});

function manageNews(item){ //azioni da eseguire su una news (onChange di <select name=\"news_action\" ...)
	var row = item.parents("tr"); //dammi la riga <tr> su cui eseguire le azioni  
	var td = row.find("td"); //dammi tutti gli <td> che sono discendenti di <tr> selezionato prima
	var act = item.val(); //vai a prendere il valore di <select> scorrendo il DOM
	var key = td.eq(0).text();	//dammi l'id della news da modificare
	if(act != "-"){
		if(act == "edit_save"){
			var o = new Object();
			o.id = key;
			o.content = "NoContent";
			o.title = td.eq(1).children().val(); // prendi il valore associato all'attr. value di <input> che e' discendente di <td>
			o.type = td.eq(4).children().val();
			o.image = td.eq(5).children().val();
			$.post("news-controller", {"action": "edit_save", "edit_news": JSON.stringify(o)}, function(resp, stat, xhr){
				if(xhr.readyState == 4 && stat == "success"){
					alert("Modifica dati delle news eseguita con successo!!");
					item.val("-");
				}else{
					window.location.href = "./error.jsp"; //pagina errore 404
				}
			});
		}else if(act == "delete"){
			$.post("news-controller", {"action": "delete", "pkey" : key}, function(resp, stat, xhr){
				if(xhr.readyState == 4 && stat == "success"){
					row.remove();
				}else{
					window.location.href = "./error.jsp"; //pagina errore 404
				}
			});
		}
	}
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
	if(validateEmail($("#mail_dest"), 50, $('.new_mail_txt_err'))){
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
				str+= "<div class=\"table_scroll table_content_posta\"><table id=\"tb_mail_inviata\" class=\"table_data\"><thead>" +
						"<tr><th class=\"th_empty\"> </th><th class=\"th_short\"> Destinatario </th><th> Oggetto </th>" +
							"<th class=\"th_short\"> Data e ora </th></tr></thead><tbody>";
				//costruzione del contenuto della tabella
				for(var i = 0; i < size; i++){
					var k = o["email"+i];	//prendi l'oggetto JS associato alla proprieta' 'email' dell'oggetto JS appena convertito 
					if(k.read == false){
						str += "<tr class=\"tr_bold\">";
					}else{
						str += "<tr>";
					}
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
				str+= "<div class=\"table_scroll table_content_posta\"><table id=\"tb_mail_arrivata\" class=\"table_data\">" +
					 "<thead><tr><th class=\"th_empty\"> </th><th class=\"th_short\"> Mittente </th><th> Oggetto </th>" + 
					 "<th class=\"th_short\"> Data e ora </th></tr></thead><tbody>";
				//costruzione del contenuto della tabella
				for(var i = 0; i < size; i++){
					var k = o["email"+i];	//prendi l'oggetto JS associato alla proprieta' 'email' dell'oggetto JS appena convertito 
					if(k.read == false){
						str += "<tr class=\"tr_bold\">";
					}else{
						str += "<tr>";
					}
					str+= "<td><input type=\"checkbox\" onchange=\"deleteEmail($(this))\"><span class=\"email_id\">" + k.id +"</span></td>" +
						"<td>" + k.mittente + "</td><td class=\"td_mail_object\" onclick=\"showContentPopup($(this))\">" + k.object + "</td>" +
						"<td>" + k.data + "  " + k.ora + "</td> "+ 
						"<td class=\"hide_td\">" + k.content + "</td><td class=\"hide_td\">" + k.read + "</td></tr>";
				}
				str +="</tbody></table></div>";
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

//validazione form per la sezione 'Gestione sito' -> prodotti
function styleForErrorTextInput(item){
	item.css("border","1px solid red");
}

function validateCodice(item, maxlenght, err){
	var x = item.val();
	var re = /^[0-9]{1}-{1}[0-9]{4}$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.empty();
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Valore inserito non valido!! Es. 0-1234");
	}
	return val;
}
		
function validateDate(item, index){
	var x = item.val();
	var err = $(".prod_txt_err").eq(index);
	var val = false;
	if(!Date.parse(x)){
		//errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else{
		item.css("border","1px solid black");
		err.html("");
		val = true;
	}
	return val;
}

function validatePrezzo(item, err){
	var x = item.val();
	var re = /^[0-9]+[\.]{1}[0-9]{1,2}$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		err.show();
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		err.hide();
		item.css("border","1px solid green");
		val = true;
	}else{
		err.show();
		styleForErrorTextInput(item);
		err.html("Valore inserito non valido!! Es. 1.23");
	}
	return val;
}

function validateNomeProdotto(item, maxlenght, err){
	var x = item.val();
	var re = /^[A-Za-z0-9 ]+$/;
	var val = false;
	if(x == ""){ //errore campo vuoto
		styleForErrorTextInput(item);
		err.html("Campo obbligatorio");
	}else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(item);
		err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
	}else if(x.match(re)){ //la stringa è conforme all'espressione regolare
		item.val(filterNamep(x));
		err.html("");
		item.css("border","1px solid green");
		val = true;
	}else{
		styleForErrorTextInput(item);
		err.html("Valore inserito non valido!! Ammessi max " + maxlenght + " caratteri");
	}
	return val;
}

function filterNamep(str){ //per il fatto che si vuole che nel DB sia memorizzato solo 'Confezione' (scelta progettuale)
	var s = "";
	if(str.includes("Confezione") || str.includes("confezione")){
		s += "Confezione";
	}else{s = str;}
	return s;
}

//Si obbliga a inserire il numero di uova se il nome del prodotto e' 'Confezione' 
function checkConfezioneUova(err){
	var k = $("#prod_form input").eq(6); // prendi il campo <input> che riguarda il nome del prodotto
	var flag = true;
	if(k.val() == "Confezione"){
		var x = $("#prod_form input").eq(7); //prendi il campo <input> che riguarda il numero di uova
		if(x.val() == 0){
			err.html("Obbligatorio specificare il numero di uova della confezione");
			styleForErrorTextInput(x);
			flag = false;
		}else{
			err.html("");
			k.css("border","1px solid green");
			x.css("border","1px solid grey");
		}
	}
	return flag;
}

function validateLinkImmagine(item, maxlenght, err){
	var x = item.val();
	var val = false;
	if(x.length > maxlenght){ //codice errore per stringa troppo lunga
		err.show();
		styleForErrorTextInput(item);
		err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
	}else{ //la stringa è conforme all'espressione regolare
		err.hide();
		item.css("border","1px solid grey");
		val = true;
	}
	return val;
}

function formProdottoValidation(){
	var flag = false;
	var f = $("#prod_form");
	var input = f.find("input");
	if(validateCodice(input.eq(0), 6, $('.prod_txt_err').eq(0))){
		if(validateDate(input.eq(1), 1)){
			if(validateDate(input.eq(2), 2)){
				if(validatePrezzo(input.eq(3), $('.prod_txt_err').eq(3))){
					if(validateNomeProdotto(input.eq(5), 20, $('.prod_txt_err').eq(4))){
						if(checkConfezioneUova($('.prod_txt_err').eq(4))){	//verifica se occorre inserire il numero di uova
							if(validateLinkImmagine(input.eq(7), 100, $('.prod_txt_err').eq(5))){
								flag = true;
							}
						}
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

function formNewsValidation(){
	var flag = false;
	var f = $("#news_form");
	var input = f.find("input");
	if(validateLinkImmagine(input.eq(0), 100, $('.news_txt_err').eq(0))){
		flag = true;
	}
	if(flag){
		return true;
	}else{
		alert("Ci stanno uno o piu' campi che presentano degli errori!! Non si puo' proseguire");
		return false;
	}
}

//validazione form della sezione 'Gestione account' -> Miei dati
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

//funzioni per il popup 'Contenuto news'
function showContentNewsPopup(item){
	//resetta gli elementi
	$("#news_content_popup_area h1").html("");
	$("#news_content_edit").val("");
	$("#news_content_popup_area span").eq(0).html("");
	
	//riempi gli elementi e mostra il popup
	riempiNewsPopup(item);
	$("#news_content_popup").show();
	$("#news_content_popup").addClass("popup_body");
}

function riempiNewsPopup(item){
	var row = item.parents("tr"); //dammi la riga <tr> su cui eseguire le azioni  
	var td = row.find("td"); //dammi tutti gli <td> che sono discendenti di <tr> selezionato prima
	var key = td.eq(0).text(); //dammi l'ID della news da prendere
	$.post("news-controller", {"action": "dammi_news", "id": key}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp);
			var k = o.notizia;
			$("#news_content_popup_area h1").text(k.title);
			$("#news_content_edit").val(k.content);
			$("#news_content_popup_area span").eq(0).html(k.id); //id non visualizzato nella pagina, serve in seguito
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

$("#save_news_edit").click(function(){
	var o = new Object();
	o.id = $("#news_content_popup_area span").eq(0).text();
	o.title = $("#news_content_popup_area h1").text();
	o.content = $("#news_content_edit").val();
	$.post("news-controller", {"action": "edit_save", "edit_news": JSON.stringify(o)}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			removeContentNewsPopup();
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

$("#back_content_news").click(removeContentNewsPopup);

function removeContentNewsPopup(){
	$("#news_content_popup").removeClass("popup_body");
	$("#news_content_popup").hide();
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