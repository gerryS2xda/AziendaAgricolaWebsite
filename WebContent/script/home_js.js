function initHome(){
	initCart();
	showSlideJQuery();
}

function initCart(){
	//verifica se il carrello e' presente nella sessione, se non e' presente ne crea uno nuovo
	$.post("cart-contr", {"action": "init_cart"}, function(resp, stat, xhr){
		if(xhr.readyState != 4 || stat != "success"){
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}

/* funzioni riservate per l'effetto slideshow */
var k = 0; //SlideIndex

function showSlideJQuery(){
	var slide = $(".mySlides");
	for(var i = 0; i < slide.length; i++) {
		slide.eq(i).css("display", "none");
	}
	k++;
	if (k > slide.length) { k = 1; }
	slide.eq(k-1).css("display", "block");
	setTimeout(showSlideJQuery, 3000);
}

/* funzioni per il menu icon */
function showVerticalNavBar(){
	$("#right_sidebar").animate({width: 'toggle'}, "slow", function(){
		//gestione contenuto centrale in base all'apertura o chiusura della barra
		var x = $(".text");
		var y = $("#right_sidebar").css("display");
		if(y == "block"){
			x.css("margin-left", "5%");
			$(".menu_icon").hide();
			$(".close_menu").show();
		}else{
			x.css("margin-left", "15%");
			$(".menu_icon").show();
			$(".close_menu").hide();
		}
	});
}

/* funzioni per la vertical navigation bar */
$("#access_link").click(function(){
	$("#loginarea").load("LoginForm.jsp");
});

$("#logout").click(function(){
	$.post("user-controller", {"action": "logout"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp);
			if(o.done){
				window.location.href = "./HomePage.jsp"; 
			}else{
				window.location.href = "./error.jsp"; //pagina errore 404
			}
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
});