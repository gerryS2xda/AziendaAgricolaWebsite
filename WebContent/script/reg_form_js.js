
/* funzioni per la parte grafica */
function addActive(label){
	var d = document.getElementById(label);
	if(d.className == ""){
		d.className += "active";
	}
}

function removeActive(id_input, label){
	var x = document.getElementById(id_input);
	var d = document.getElementById(label);
	if(x.value == ""){
		d.className = "";
	}
}

//funzione generale per check possibilita' di submit
function formValidation(){
	var flag = false;
	if(validateOnlyLetter('first_name', 'lbl', 'nome_txt_error', 20)){
		if(validateOnlyLetter('sur_name', 'lbl1', 'cognome_txt_error', 20)){
			if(validateCityProv('lg_nasc', 'lbl2', 'lgN_error', 20)){
				if(validateCodiceFiscale('cod_fisc', 'lbl3', 'cf_err')){
					if(validateTelephone('telef', 'lbl9', 'tel_err')){
						if(validateOnlyLetter('via_in', 'lbl4', 'via_err', 25)){
							if(validateCityProv('citta', 'lbl6', 'city_pv_err', 50)){
								if(validateEmail('email', 'lbl8', 'mail_err')){
									if(validateUsername('username', 'lbl10', 'usr_err')){
										if(validatePassword('pwd', 'lbl11', 'pwd_err')){
											if(validateDate($("#dataNasc"), $('.txt_error').eq(2))){
												if(validateRadioButton()){
													flag = true;
												}
											}
										}
									}
								}
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
		event.preventDefault();	//utilizzo per bloccare invio submit anche se ritorna false
		return false;
	}
}

//funzioni per validare form

/*  Funzione che valuta i campi di un form che contengono solo caratteri letterali
 *  field e' la variabile che e' stata associata all'elemento 'input' del form
 *  I campi testati sono: nome, cognome, luogoNascita, 
*/ 
function validateOnlyLetter(field, label, id_err, maxlenght){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^[A-Za-z ]+$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length > maxlenght){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Valore troppo lungo!! (max " + maxlenght + " caratteri)";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Valore inserito non valido!! Sono ammesse solo lettere dell'alfabeto";
	}
	return val;
}

function validateCityProv(field, label, id_err, maxlenght){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^[A-Za-z ]+(([\(A-Z\)]{4,4})|(\([A-Za-z]+)\))$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length > maxlenght){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Valore troppo lungo!! (max " + maxlenght + " caratteri)";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Valore inserito non valido!! Es. Salerno (SA)";
	}
	return val;
}

function validateUsername(field, label, id_err){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^\w{6,12}$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length < 6 || x.value.lenght > 12 ){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Username deve essere compreso tra 6 e 12 caratteri";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Input non valido!! Può contenere solo lettere e numeri";
	}
	return val;
}

function validatePassword(field, label, id_err){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^[A-Za-z0-9]{6,20}$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length < 6 || x.value.lenght > 20 ){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Password deve essere compreso tra 6 e 20 caratteri";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Input non valido!! Può contenere solo lettere e numeri";
	}
	return val;
}

function validateEmail(field, label, id_err){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length > 50 ){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Email non valida!! Troppo lunga (max 50 caratteri)";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Email non valida!! Es. email.test@example.com";
	}
	return val;
}

function validateTelephone(field, label, id_err){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^\d{10}$/;
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		err.innerHTML = "";
		restoreStyleField(x, d);
		val = true;	//se il campo non e' obbligatorio si salta il check validita' e si ripristina lo stile
	}else if(x.value.length != 10 ){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Numero di telefono non valido!! (max 10 numeri)";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Numero di telefono non valido!!";
	}
	return val;
}

function validateCodiceFiscale(field, label, id_err){
	var x = document.getElementById(field);
	var d = document.getElementById(label);
	var err = document.getElementById(id_err);
	var re = /^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/
	var val = false;
	if(x.value == ""){ //errore campo vuoto
		d.className = "";
		styleForErrorTextInput(x, d);
		err.innerHTML = "Campo obbligatorio";
	}else if(x.value.length != 16){ //codice errore per stringa troppo lunga
		styleForErrorTextInput(x, d);
		err.innerHTML = "Codice Fiscale deve essere di 16 caratteri alfanumerici";
	}else if(x.value.match(re)){ //la stringa è conforme all'espressione regolare
		err.innerHTML = "";
		styleValidTextInput(x, d);
		val = true;
	}else{
		styleForErrorTextInput(x, d);
		err.innerHTML = "Codice Fiscale non valido!!";
	}
	return val;
}

function validateDate(item, err){
	var x = item.val();
	var val = false;
	if(!Date.parse(x)){
		//errore campo vuoto
		item.css("border","1px solid red");
		err.html("Campo obbligatorio");
	}else{
		item.css("border","1px solid black");
		err.html("");
		val = true;
	}
	return val;
}

function validateRadioButton(){
	var val = false;
	var err = $(".txt_error").eq(4);
	var x = $("#box_form form :radio"); //seleziona tutti gli <input type="radio" ...>
	if(x.eq(0).is(":checked") || x.eq(1).is(":checked")){ //se almeno un radio button e' checked 
		err.html("");
		val = true;
	}else{
		err.html("Campo obbligatorio");
	}
	return val;
}

function styleForErrorTextInput(field, label){
	label.style.color = "#F44336";
	field.style.boxShadow = "0 1px 0 0 #F44336";
	field.style.borderBottomColor = "#F44336";
}

function styleValidTextInput(field, label){
	label.style.color = "#00FF00";
	field.style.boxShadow = "0 1px 0 0 #00FF00";
	field.style.borderBottomColor = "#00FF00";
}

function restoreStyleField(field, label){
	label.style.color = "#9e9e9e";
	field.style.boxShadow = "0 1px 0 0 #9e9e9e";
	field.style.borderBottomColor = "#9e9e9e";
}
