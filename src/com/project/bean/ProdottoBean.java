package com.project.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ProdottoBean {

	//instance field
	private String codice;
	private String descrizione;
	private String dataScadenza;
	private String dataConf;
	private double prezzo;
	private int sconto;
	private int iva;
	private String tipo;
	private int numUovaConf;
	private String immagine;
	
	//Constructor
	public ProdottoBean(){
		codice = "XXX-000000";
		descrizione = "No description";
		dataScadenza = LocalDate.now().toString();
		dataConf = LocalDate.now().toString();;
		prezzo = 0;
		sconto = 0;
		iva = 0;
		tipo = "";
		numUovaConf = 0;
		immagine = "Image not found";
	}
	
	//get methods
	public String getCodice(){
		return codice;
	}
	
	public String getDescription(){
		return descrizione;
	}
	
	public String getDataScadenza(){
		return dataScadenza;
	}
	
	public String getDataConfezionamento(){
		return dataConf;
	}
	
	public double getPrezzo(){
		return (BigDecimal.valueOf(prezzo).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	public int getSconto(){
		return sconto;
	}
	
	public int getIVA(){
		return iva;
	}
	
	public String getType(){
		return tipo;
	}
	
	public int getNumUovaConfezione(){
		return numUovaConf;
	}
	
	public String getImageURL(){
		return immagine;
	}
	
	//set methods
	public void setCodice(String c){
		codice = c;
	}
	
	public void setDescription(String d){
		descrizione = d;
	}
	
	public void setDataScadenza(String d){
		dataScadenza = d;
	}
	
	public void setDataConfezionamento(String d){
		dataConf = d;
	}
	
	public void setPrezzo(double p){
		prezzo = p;
	}
	
	public void setSconto(int s){
		sconto = s;
	}
	
	public void setIVA(int i){
		iva = i;
	}
	
	public void setType(String t){
		tipo = t;
	}
	
	public void setNumUovaConfezione(int u){
		numUovaConf = u;
	}
	
	public void setImageURL(String url){
		immagine = url;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"codice\": \"" + codice + "\", \"dataScadenza\": \"" + dataScadenza + "\", \"dataConf\": \"" + dataConf + "\", \"prezzo\":" + prezzo + ", "
				+ "\"sconto\": " + sconto + ", \"iva\": " + iva + ", \"tipo\": \"" + tipo + "\", \"numUovaConf\":" + numUovaConf + ", \"immagine\": \"" + immagine + "\"";
		return str;
	}
}
