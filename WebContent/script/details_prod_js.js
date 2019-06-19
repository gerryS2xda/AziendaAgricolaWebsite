function initDetailsProd(){
	setFooterPosition(620);	//in base alla dimensione del <body> viene posizionato il footer nella pagina
}

function setFooterPosition(val){
	var x = parseInt($("body").css("height"));	//prendi altezza del body
	if(x < val){
		$("footer").addClass(" footer_small_body");
	}else{
		$("footer").removeClass(" footer_small_body");
	}
	$("footer").show();
}