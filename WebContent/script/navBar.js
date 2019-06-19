$(document).ready(function(){
	initNavBar();
	
	$(".accedi").click(function(){
		$("#loginarea").load("LoginForm.jsp");
	});
			
	$(".logout").click(function(){
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
	
	/* open and close navBar */
	$(".arrow_label").click(function showVerticalNavBar(){
		$(".vertical-menu").animate({width: 'toggle'}, "slow");
		var y = $(this); //etichetta che attiva l'evento
		if(y.css("width") == "248px"){	//se la barra si sta per chiudure esegui questo
			closeNavBar(y);
		}else{	//se la barra si sta per aprire esegui questo
			openNavBar(y);
		}
	});
	
	function openNavBar(item){
		$("#main_content").css("margin-right", "260px"); //per il main_content di ogni pagina in cui la navBar.jsp e' inclusa
		$(".arrow_label img").attr("src", "images/arrow_circle_white_right.png");
		item.animate({width: '248px'}, "slow");
		$(".div_blur").addClass(" blur_effect");
		$("body").css("overflow-y", "hidden");
	}
	
	function closeNavBar(item){
		$("#main_content").css("margin-right", "0");	//per il main_content di ogni pagina in cui la navBar.jsp e' inclusa
		$(".arrow_label img").attr("src", "images/arrow_circle_white_left.png");
		item.animate({width: '48px'}, "slow");
		$(".div_blur").removeClass(" blur_effect");
		$("body").css("overflow-y", "scroll");
	}
});

function initNavBar(){
	//verifica se il carrello e' presente nella sessione, se non e' presente ne crea uno nuovo
	$.post("cart-contr", {"action": "init_cart"}, function(resp, stat, xhr){
		if(xhr.readyState != 4 || stat != "success"){
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
}
