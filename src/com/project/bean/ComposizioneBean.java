package com.project.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComposizioneBean {

	//instance field
	private FatturaBean fat;
	private ProdottoBean prod;
	private double prezzo;
	private int sconto;
	private int iva;
	private int quantita;
	
	//Constructor
	public ComposizioneBean(){
		fat = new FatturaBean();
		prod = new ProdottoBean();
		sconto = 0;
		prezzo = 0;
		iva = 0;
		quantita = 0;
	}
	
	//get methods
	public FatturaBean getFattura(){
		return fat;
	}
	
	public ProdottoBean getProdotto(){
		return prod;
	}
	
	public double getPrezzoAcquisto(){
		return (BigDecimal.valueOf(prezzo).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	public int getSconto(){
		return sconto;
	}
	
	public int getIVA(){
		return iva;
	}
	
	public int getQuantita(){
		return quantita;
	}
	
	//set methods
	public void setFattura(FatturaBean f){
		fat = f;
	}
	
	public void setProdotto(ProdottoBean p){
		prod = p;
	}
	
	public void setPrezzoAcquisto(double p){
		prezzo = p;
	}
	
	public void setSconto(int s){
		sconto = s;
	}
	
	public void setIVA(int i){
		iva = i;
	}
	
	public void setQuantita(int q){
		quantita = q;
	}
	
	//altri metodi
	public String toString(){
		String str = "{\"fat\":" + fat.toString() + ", \"prod\": " + prod.toString() + "}, \"prezzo\":" + prezzo + ", \"sconto\":" + sconto + ", \"iva\":" + iva + ", \"quantita\":" + quantita + "}";
		return str;
	}
}
