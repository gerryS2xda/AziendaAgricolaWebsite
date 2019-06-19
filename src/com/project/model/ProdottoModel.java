package com.project.model;

import java.sql.*;
import java.util.ArrayList;
import com.project.bean.ProdottoBean;
import com.project.utils.Utils;

public class ProdottoModel {
	
	private static final String TABLE_NAME= "prodotto";
	private static final String[] COLUMN_NAME = {"Codice", "Descrizione", "DataScadenza", "DataConfezionamento", "Prezzo", "Sconto", "IVA", "Tipo", "UovaConfezione", "Immagine"};
	
	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param p, oggetto di tipo ProdottoBean
	 * @throws SQLException
	 */
	
	public synchronized void doSave(ProdottoBean p) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setString(1, p.getCodice());
			ps.setString(2, p.getDescription());
			ps.setDate(3, Date.valueOf(p.getDataScadenza()));
			ps.setDate(4, Date.valueOf(p.getDataConfezionamento()));
			ps.setDouble(5, p.getPrezzo());
			ps.setInt(6, p.getSconto());
			ps.setInt(7, p.getIVA());
			ps.setString(8, p.getType());
			ps.setInt(9, p.getNumUovaConfezione());
			ps.setString(10, p.getImageURL());
			
			//esegui il comando SQL
			ps.executeUpdate();
			
			//dato che autoCommit(false), gli statement SQL non saranno impegnati finche' non verrà eseguito commit()
			conn.commit();
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
	}
	
	/**
	 * Recupera i dati del bean dal DB tramite una chiave e li salva nell'oggetto ProdottoBean che viene restituito
	 * @param code, stringa usata come chiave per la selectSQL
	 * @return bean, oggetto di tipo ProdottoBean
	 * @throws SQLException
	 */
	public synchronized ProdottoBean doRetrieveByKey(String code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ProdottoBean p = new ProdottoBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Codice = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				p.setCodice(rs.getString(1));
				p.setDescription(rs.getString(2));
				p.setDataScadenza(rs.getDate(3).toLocalDate().toString());
				p.setDataConfezionamento((rs.getDate(4).toLocalDate().toString()));
				p.setPrezzo(rs.getDouble(5));
				p.setSconto(rs.getInt(6));
				p.setIVA(rs.getInt(7));
				p.setType(rs.getString(8));
				p.setNumUovaConfezione(rs.getInt(9));
				p.setImageURL(rs.getString(10));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return p;
	}
	
	/**
	 * Cancella i dati dalla tabella Prodotto in base al valore di una chiave e rest. l'esito dell'operazione
	 * @param code, stringa che rappresenta la chiave
	 * @return boolean, esito dell'operazione
	 * @throws SQLException
	 */
	public synchronized boolean doDelete(String code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String delSQL = "DELETE FROM " + TABLE_NAME + " WHERE Codice = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(delSQL);
			ps.setString(1, code);
			
			if(ps.executeUpdate() != 0) val = true;
			conn.commit();
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return val;
	}
	
	/**
	 * Fornisce una collezione di oggetti ordinati in un certo modo relativi al bean
	 * @param order, specifica metodo di ordinamento
	 * @return lista di oggetti di tipo ProdottoBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<ProdottoBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ProdottoBean p = new ProdottoBean();
				p.setCodice(rs.getString(1));
				p.setDescription(rs.getString(2));
				p.setDataScadenza(rs.getDate(3).toLocalDate().toString());
				p.setDataConfezionamento((rs.getDate(4).toLocalDate().toString()));
				p.setPrezzo(rs.getDouble(5));
				p.setSconto(rs.getInt(6));
				p.setIVA(rs.getInt(7));
				p.setType(rs.getString(8));
				p.setNumUovaConfezione(rs.getInt(9));
				p.setImageURL(rs.getString(10));
				prodotti.add(p);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return prodotti;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo ProdottoBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<ProdottoBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ProdottoBean p = new ProdottoBean();
				p.setCodice(rs.getString(1));
				p.setDescription(rs.getString(2));
				p.setDataScadenza(rs.getDate(3).toLocalDate().toString());
				p.setDataConfezionamento((rs.getDate(4).toLocalDate().toString()));
				p.setPrezzo(rs.getDouble(5));
				p.setSconto(rs.getInt(6));
				p.setIVA(rs.getInt(7));
				p.setType(rs.getString(8));
				p.setNumUovaConfezione(rs.getInt(9));
				p.setImageURL(rs.getString(10));
				prodotti.add(p);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return prodotti;
	}
}
