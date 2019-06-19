function initCart(){
	setFooterPosition(320);	//in base alla dimensione del <body> viene posizionato il footer nella pagina
	//verifica se il carrello e' presente nella sessione, se non e' presente ne crea uno nuovo
	$.post("cart-contr", {"action": "init_cart"}, function(resp, stat, xhr){
		if(xhr.readyState != 4 || stat != "success"){
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

function updateTotalPrice(item, prod){
	var tr = item.parents("tr");
	var q = item.val();
	var t = $("#tot_txt").text();
	$.post("cart-contr", {"action": "edit_quantity", "idprod": prod, "quant": q, "total": t}, function (resp, statusTxt, xhr){
		if(xhr.readyState == 4 && statusTxt == "success"){
			var o = JSON.parse(resp);
			$("#tot_txt").text(o.total);
			tr.find("td").eq(2).text(o.prezzo + " €");
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}
		
function removeElement(item, prod){
	var x = item.parents("tr");
	var t = $("#tot_txt").text();
	$.post("cart-contr", {"action": "remove", "IDprod_rm": prod, "total": t}, function(resp, status, xhr){
		if(xhr.readyState == 4 && status == "success"){
			var o = JSON.parse(resp);
			if(o.prodotti == 0){
				$("#cart_content").html("<p id=\"empty_cart\">Il carrello e' vuoto</p>");
				setFooterPosition(620);
			}else{
				$("#tot_txt").html(o.total);
				x.remove();
				setFooterPosition(320);
			}
			$(".contatore").text(o.prodotti);
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

$("#back_to_catalogo button").click(function(){
	window.location.href = "./Catalogo.jsp";
});

$("#totale button").click(function(){
	var tot = parseFloat($("#tot_txt").text());
	$.post("fattura-contr", {"action": "buy", "tot_import": tot}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp);
			if(o.status == "NoUser"){
				$("#loginarea").load("LoginForm.jsp");	//viene inserito nel <div> presente nel codice HTML della navBar inclusa in <header>
			}else if(o.status == "NoCart"){
				window.location.href = "./Catalogo.jsp";
			}else if(o.status == "ShowFattura"){
				alert("Pagamento effettuato!! Premi 'Ok' per visualizzare la fattura");
				window.location.href = "./Fattura.jsp";
			}
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});

function setFooterPosition(val){
	var x = parseInt($("body").css("height"));	//prendi altezza del body
	if(x < val){
		$("footer").addClass(" footer_small_body");
	}else{
		$("footer").removeClass(" footer_small_body");
	}
	$("footer").show();
}
