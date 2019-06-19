package com.project.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class FatturaBean {
	
	//instance field
	private String partenza;	//uso come dato statico
	private int numero;
	private String dataEmissione;
	private double importo;
	private UtenteBean cliente;
	//attributi relativi all'entita' Pacco presente nel diagramma ER
	private String arrivo;
	private String dataPartenza;
	private String dataArrivo;
	private double speseSped; //spese di spedizione
	
	//Constructor
	public FatturaBean(){
		numero = 0;
		dataEmissione = LocalDate.now().toString();
		importo = 0;
		cliente = new UtenteBean();
		arrivo = "";
		dataPartenza = LocalDate.now().toString();
		dataArrivo = LocalDate.now().toString();
		speseSped = 0;
		partenza = "Via Provinciale 29, Campagna (SA)";
	}
	
	public FatturaBean(int n){
		numero = n;
		dataEmissione = null;
		importo = 0;
		cliente = null;
		arrivo = "";
		dataPartenza = LocalDate.now().toString();
		dataArrivo = LocalDate.now().toString();
		speseSped = 0;
		partenza = "Via Serra Pauzone, Castelvetere Sul Calore, AV";
	}
	
	//get methods
	public int getNumero(){
		return numero;
	}
	
	public String getDataEmissione(){
		return dataEmissione;
	}
	
	public double getImporto(){
		return (BigDecimal.valueOf(importo).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	public String getPartenza(){
		return partenza;
	}
	
	public UtenteBean getCliente(){
		return cliente;
	}
	
	public String getArrivo(){
		return arrivo;
	}
	
	public String getDataPartenza(){
		return dataPartenza;
	}
	
	public String getDataArrivo(){
		return dataArrivo;
	}
	
	public double getSpeseSpedizione(){
		return speseSped;
	}
	
	//set methods
	public void setNumero(int n){
		numero = n;
	}
	
	public void setDataEmissione(String d){
		dataEmissione = d;
	}
	
	public void setImporto(double im){
		importo = im;
	}
	
	public void setCliente(UtenteBean u){
		cliente = u;
	}
	
	public void setPartenza(String p){
		partenza = p;
	}
	
	public void setArrivo(String a){
		arrivo = a;
	}
	
	public void setDataPartenza(String d){
		dataPartenza = d;
	}
	
	public void setDataArrivo(String d){
		dataArrivo = d;
	}
	
	public void setSpeseSpedizione(double s){
		speseSped = s;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"numero\":" + numero + ", \"dataEmissione\": \"" + dataEmissione + "\", \"importo\":" + importo + ", \"cliente\": \"" + cliente.getCodiceFiscale() + "\", "
				+ "\"partenza\": \"" + partenza + "\", \"arrivo\": \"" + arrivo + "\", \"dataPartenza\": \"" + dataPartenza + "\", \"dataArrivo\": \"" + dataArrivo + "\", \"speseSped\":" + speseSped + "}";
		return str;
	}
}
