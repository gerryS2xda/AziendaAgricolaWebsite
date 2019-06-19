package com.project.bean;

import java.time.LocalDate;

public class UtenteBean {

	//instance field
	private String cf;
	private String nome;
	private String cognome;
	private String dataN;
	private String luogoN;
	private String sex;
	private String address;
	private String email;
	private String telefono;
	private String username;
	private String password;
	private String type; //utente o amministratore
	
	//Constructor
	public UtenteBean(){
		cf = "00000000";
		nome = "";
		cognome = "";
		dataN = LocalDate.now().toString();
		luogoN = "";
		sex = "";
		address = "";
		email = "";
		telefono = "0";
		username = "";
		password = "";
		type = "";
	}
	
	//public methods get
	public String getCodiceFiscale(){
		return cf;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getCognome(){
		return cognome;
	}
	
	public String getDataNascita(){
		return dataN;
	}
	
	public String getLuogoNascita(){
		return luogoN;
	}
	
	public String getSesso(){
		return sex;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getTelefono(){
		return telefono;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getUserType(){
		return type;
	}
	
	//public methods set
	public void setCodiceFiscale(String s){
		cf= s;
	}
		
	public void setNome(String s){
		nome = s;
	}
		
	public void setCognome(String c){
		cognome = c;
	}
		
	public void setDataNascita(String d){
		dataN = d;
	}
		
	public void setLuogoNascita(String l){
		luogoN = l;
	}
		
	public void setSesso(String s){
		sex = s;
	}
		
	public void setAddress(String a){
		address = a;
	}
		
	public void setEmail(String e){
		email = e;
	}
		
	public void setTelefono(String t){
		telefono = t;
	}	
	
	public void setUsername(String us){
		username = us;
	}
	
	public void setPassword(String pwd){
		password = pwd;
	}
	
	public void setUserType(String t){
		type = t;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"cf\": \"" + cf + "\", \"nome\": \"" + nome + "\", \"cognome\": \"" + cognome + "\", \"dataN\": \"" + dataN + "\", \"luogoN\":\"" + luogoN + "\", "
				+ "\"sex\": \"" + sex + "\", \"address\": \"" + address + "\", \"email\": \"" + email + "\", \"telefono\": \"" + telefono + "\", \"username\": \"" + username + "\", "
						+ "\"password\": \"" + password + "\", \"type\": \"" + type + "\"}";
		return str;
	}
}
