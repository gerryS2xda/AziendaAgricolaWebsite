package com.project.bean;

import java.time.LocalDate;

public class NotiziaBean {

	//instance field
	private int id; //numero identificativo 
	private String title; //titolo della notizia
	private String content; //contenuto testuale della notizia
	private String dataIns; //data inserimento
	private String dataUlMod; //data ultima modifica della notizia
	private String type; //notizia di carattere generale o promozionale
	private String image; //url per andare a prendere le immagini da caricare
	
	//Constructor
	public NotiziaBean(){
		id = 0;
		title = "No title";
		content = "No content of news";
		dataIns = LocalDate.now().toString();
		dataUlMod = LocalDate.now().toString();
		type = "";
		image = "";
	}
	
	//public get methods
	public int getNewsID(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getNewsContent(){
		return content;
	}
	
	public String getDataInserimento(){
		return dataIns;
	}
	
	public String getDateofLastModified(){
		return dataUlMod;
	}
	
	public String getNewsType(){
		return type;
	}
	
	public String getURLImage(){
		return image;
	}
	
	
	//public set methods
	public void setNewsID(int i){
		id = i;
	}
	
	public void setTitle(String nt){
		title = nt;
	}
	
	public void setNewsContent(String c){
		content = c;
	}
	
	public void setDataInserimento(String d){
		dataIns = d;
	}
	
	public void setDateofLastModified(String d){
		dataUlMod = d;
	}
	
	public void setNewsType(String t){
		type = t;
	}
	
	public void setURLImage(String url){
		image = url;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"id\":" + id + ", \"title\": \"" + title + "\", \"content\": \"" + content + "\", \"dataIns\":\"" + dataIns + "\", "
				+ "\"dataUlMod\": \"" + dataUlMod + "\", \"type\": \"" + type + "\", \"image\": \"" + image + "\"}";
		return str;
	}
}

