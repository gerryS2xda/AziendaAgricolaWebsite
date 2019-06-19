$(document).ready(function(){
	initContatti();
});

function initContatti(){
	$.post("user-controller", {"action": "get_admin"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			if(o.utente == "No result"){
				str+= "Attualmente i dati non sono disponibili";
			}else{
				for(var i = 0; i < size; i++){
					var k = o["utente"+i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS 
					str+= "<span>" + k.nome + " " + k.cognome + "</span> <br><span>Mobile: " + k.telefono + "</span> <br><span>E­mail: " + k.email+ "</span> <br><br>";
					setDestinationField(k, i);
				}
			}
			$(".admin_info").html(str);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function setDestinationField(obj, i){
	var x = $("#admin_selector option");
	i++; //il primo <option> deve essere senza valore;
	x.eq(i).val(obj.email);
	x.eq(i).html(obj.nome + " " + obj.cognome);
}

$("#send").click(function(){ //pulsante 'Invia' nel popup di scrivi
	if(formContattiValidation()){
		var m = $("#mail_mittente").val();
		var x = $("#admin_selector").val();
		var y = $("#mail_object").val();
		var z = $("#new_mail_content").val();
		$.post("posta-contr", {"action": "scrivi", "mail_mittente": m, "mail_dest" : x, "mail_object": y, "new_mail_content":z }, function(resp, status, xhr){
			if(xhr.readyState == 4 && status == "success"){
				alert("Mail inviata correttamente!! Appena possibile le verra' recapitato il messaggio nella propria casella di posta");
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		});
	}
});

function styleForErrorTextInput(item){
	item.css("border","1px solid red");
}

function formContattiValidation(){
	var flag = false;
	if(validateEmail($("#mail_mittente"), 50, $(".new_mail_txt_err").eq(0))){
		if(validateSelectDestinationEmail($("#admin_selector"), $(".new_mail_txt_err").eq(1))){
			flag = true;
		}
	}
	if(flag){
		return true;
	}else{
		alert("Ci stanno uno o piu' campi che presentano degli errori!! Non si puo' inviare la mail");
		return false;
	}
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


/* funzioni di utilita' */
/* calcola il numero di proprieta' presenti in un oggetto */
function sizeObject(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key)) size++;
	}
	return size;
};