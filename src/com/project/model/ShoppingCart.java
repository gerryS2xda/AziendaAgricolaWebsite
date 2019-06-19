package com.project.model;

import java.sql.SQLException;
import java.util.ArrayList;
import com.project.bean.*;
import com.project.utils.Utils;

//parte restante della fattura da completare

public class ShoppingCart {
	
	//instance field
	private ArrayList<ComposizioneBean> prodotti; 	//lista dei prodotti aggiunti al carrello dall'utente
	private int idCart;
	private FatturaBean f;
	
	//Constructor
	public ShoppingCart(int id){
		prodotti = new ArrayList<ComposizioneBean>();
		idCart = id;	//e' il massimo numero della fattura stampata dal DB
		idCart++;
		f = new FatturaBean(idCart);
	}
	
	//private methods
	private ComposizioneBean makeNew(ProdottoBean p, int q){
		ComposizioneBean cb = new ComposizioneBean();
		cb.setFattura(f);
		cb.setProdotto(p);
		cb.setPrezzoAcquisto(Utils.calculatePrezzoAcquisto(p.getPrezzo(), p.getSconto(), p.getIVA(), q));
		cb.setSconto(p.getSconto());
		cb.setIVA(p.getIVA());
		cb.setQuantita(q);
		return cb;
	}
	
	private void incrementQuantity(int i, int p){
		int q = prodotti.get(i).getQuantita(); 	//quantita' attuale del prodotto da aggiornare
		int q1 = prodotti.get(p).getQuantita(); //quantità del prodotto duplicato
		prodotti.get(i).setQuantita(q+q1);	//aggiorna quantita' aggiungendo quella duplicato che verrà rimosso
	}
	
	private void updatePrezzoAcquisto(int i){
		ProdottoBean p = prodotti.get(i).getProdotto();
		int q = prodotti.get(i).getQuantita();
		prodotti.get(i).setPrezzoAcquisto(Utils.calculatePrezzoAcquisto(p.getPrezzo(), prodotti.get(i).getSconto(), p.getIVA(), q));
	}
	
	//public methods
	public void addProduct(ProdottoBean p, int q){
		prodotti.add(makeNew(p, q));
		removeDuplicateProduct();	//verifica la presenza di duplicati 
	}
	
	public void deleteProduct(ProdottoBean p){
		for(ComposizioneBean cb : prodotti){
			if(cb.getFattura().getNumero() == f.getNumero()){
				if(cb.getProdotto().getCodice().equals(p.getCodice())){
					prodotti.remove(cb);
					break;
				}
			}
		}
	}
	
	public ArrayList<ComposizioneBean> getListaProdotti(){
		return prodotti;
	}
	
	
	public void removeDuplicateProduct(){
		for(int i = 0; i < prodotti.size(); i++){
			if((i+1) == prodotti.size()) break;
			int p = Utils.getPosEqualElement(prodotti, i); //cerca la posizione in cui è presente un'altro elemento uguale a quello in posizione i
			if(p > 0){
				incrementQuantity(i, p);	//aggiorna quantita'
				updatePrezzoAcquisto(i);	//aggiorna prezzo acquisto relativo alla nuova quantita'
				prodotti.remove(p);	//rimuovi duplicato dalla lista
				break;
			}
		}
	}
	
	public int getNumberOfElement(){
		return prodotti.size();
	}
	
	public void removeProduct(String code){
		for(int i = 0; i< prodotti.size(); i++){
			ProdottoBean p = prodotti.get(i).getProdotto();
			if(p.getCodice().equals(code)){
				prodotti.remove(i);
				break;
			}
		}
	}
	
	public int findProduct(String code){
		int r = 0;
		for(int i = 0; i< prodotti.size(); i++){
			ProdottoBean p = prodotti.get(i).getProdotto();
			if(p.getCodice().equals(code)){
				r = i;
				break;
			}
		}
		return r;
	}
	
	public int getIDCart(){
		return idCart;
	}
	
	public void setIDCart(int i){
		idCart = i;
	}
}
