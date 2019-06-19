package com.project.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class PostaBean {

	//instance field
	private int id; //identifica una email ricevuta o inviata
	private String mittente; //il mittente della email
	private String destination; //destinatario della email
	private String object; // oggetto della mail 
	private String content; //contenuto testuale della email
	private String typeEmail; //tipo di email: ricevuta o inviata
	private String data; //data arrivo o di invio di una email
	private String ora; //ora di arrivo o di invio di una email
	private boolean read; //stato della email (letta oppure no)
	private String typeUser; //tipo utente della casella di posta
	
	//Constructor
	public PostaBean(){
		id = 0;
		mittente = "No mittente";
		destination = "No destinatario";
		object = "No title";
		content = "No content of email";
		typeEmail = "";
		data = LocalDate.now().toString();
		ora = LocalTime.now().toString();
		read = false;
		typeUser = "";
	}
	
	//public get methods
	public int getEmailID(){
		return id;
	}
	
	public String getMittente(){
		return mittente;
	}
	
	public String getDestinatario(){
		return destination;
	}
	
	public String getObjectofEmail(){
		return object;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getTypeofEmail(){
		return typeEmail;
	}
	
	public String getDataofEmail(){
		return data;
	}
	
	public String getHour(){
		return ora;
	}
	
	public boolean isEmailRead(){
		return read;
	}
	
	public String getUserTypeofMailbox(){
		return typeUser;
	}
	
	//public set methods
	public void setEmailID(int i){
		id = i;
	}
	
	public void setMittente(String m){
		mittente = m;
	}
	
	public void setDestinatario(String d){
		destination = d;
	}
	
	public void setObjectofEmail(String o){
		object = o;
	}
	
	public void setContent(String c){
		content = c;
	}
	
	public void setTypeofEmail(String t){
		typeEmail = t;
	}
	
	public void setDataofEmail(String d){
		data = d;
	}
	
	public void setHour(String t){
		ora = t;
	}
	
	public void setEmailReadState(boolean b){
		read = b;
	}
	
	public void setUserTypeofMailbox(String ut){
		typeUser = ut;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"id\":" + id + ", \"mittente\": \"" + mittente + "\", \"destination\": \"" + destination + "\", \"object\": \"" + object + "\", \"content\":\"" + content + "\", "
				+ "\"typeEmail\": \"" + typeEmail + "\", \"data\": \"" + data + "\", \"ora\": \"" + ora + "\", \"read\":" + read + ", \"typeUser\": \"" + typeUser + "\"}";
		return str;
	}
}
