$(document).ready(function(){
	initNewsContent();
});


function initNewsContent(){
	$.post("news-controller", {"action":"init"}, function(resp, stat, xhr){
		if(xhr.readyState == 4 && stat == "success"){
			var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			var count = 0;	//usato per sistemare i prodotti nella tabella
			for(var i = 0; i < size; i++){
				var k = o["news"+i];
				if(count % 2 == 0){
						str += "<br>";
				}
				str+= "<div class=\"news\"><a href=\"news-controller?action=view_news&newsBean=" + k.id + "\" title=\"Clicca per visualizzare la notizia\">" +
						"<div class=\"images\"><img src=\"" + k.image + "\" alt=\"No preview image available\"></div></a>" +
						"<h2 class=\"title_img\"><a href=\"news-controller?action=view_news&newsBean=" + k.id + "\">" + k.title + "</a></h2></div>";
				count++;
			}
			$("#news_area").html(str);
			$("#main_content").show();
			$("footer").show();
		}else{
			window.location.href = "./error.jsp"; //pagina errore 404
		}
	});
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