package com.project.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.project.bean.ComposizioneBean;
import com.project.bean.ProdottoBean;


public class Utils {
	
	public static String getRelazioneTable(String tn, String[] attr){
		String str = tn + "(";
		int n = attr.length - 1;
		for(int i = 0; i< n; i++){
			str += attr[i] + ", ";
		}
		str += attr[n] + ")";
		return str;
	}
	
	public static double getPercentage(double n, int n2){
		double r = (double) (n * n2) /100;
		return (BigDecimal.valueOf(r).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	//calcola il prezzo di acquisto del prodotto
	public static double calculatePrezzoAcquisto(double pz, int sc, int iv, int q){
		double tot = (pz + (getPercentage(pz, iv)) - getPercentage(pz, sc));
		return (BigDecimal.valueOf(tot * q).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	public static double calculatePrezzoScontato(double pz, int sc){
		double tot = (pz - getPercentage(pz, sc));
		return (BigDecimal.valueOf(tot).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}
	
	public static int getPosEqualElement(ArrayList<ComposizioneBean> cart, int pos){
		int p = 0;
		ProdottoBean pb = cart.get(pos).getProdotto();
		
		for(int i = pos+1; i < cart.size(); i++){
			ProdottoBean pb1 = cart.get(i).getProdotto();
			if(pb.getCodice().equals(pb1.getCodice())){
				p = i;
				break;
			}
		}
		return p;
	}
}
