package com.project.model;

import java.sql.*;
import java.util.ArrayList;
import com.project.bean.*;
import com.project.utils.Utils;

public class ComposizioneModel {

	private static final String TABLE_NAME= "composizione";
	private static final String[] COLUMN_NAME = {"Fattura", "Prodotto", "Sconto", "PrezzoAcquisto", "IVA", "Quantita"};
	
	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param p, oggetto di tipo ComposizioneBean
	 * @throws SQLException
	 */
	public synchronized void doSave(ComposizioneBean cb) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setInt(1, cb.getFattura().getNumero());
			ps.setString(2, cb.getProdotto().getCodice());
			ps.setInt(3, cb.getSconto());
			ps.setDouble(4, cb.getPrezzoAcquisto());
			ps.setInt(5, cb.getIVA());
			ps.setInt(6, cb.getQuantita());
			
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
	 * Recupera i dati del bean dal DB tramite una chiave e li salva nell'oggetto ComposizioneBean che viene restituito
	 * @param code1, intero che indica il numero di fattura
	 * @param code2, stringa che indica il codice del prodotto
	 * @return bean, oggetto di tipo ComposizioneBean
	 * @throws SQLException
	 */
	public synchronized ComposizioneBean doRetrieveByKey(int code1, String code2) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		FatturaModel fm = new FatturaModel();
		ProdottoModel pm = new ProdottoModel();
		ComposizioneBean cb = new ComposizioneBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Fattura = ? AND Prodotto = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code1);
			ps.setString(2, code2);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				FatturaBean fb = fm.doRetrieveByKey(rs.getInt(1));
				cb.setFattura(fb);
				ProdottoBean p = pm.doRetrieveByKey(rs.getString(2));
				cb.setProdotto(p);
				cb.setSconto(rs.getInt(3));
				cb.setPrezzoAcquisto(rs.getDouble(4));
				cb.setIVA(rs.getInt(5));
				cb.setQuantita(rs.getInt(6));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return cb;
	}
	
	/**
	 * Cancella i dati dalla tabella Prodotto in base al valore di una chiave e rest. l'esito dell'operazione
	 * @param code, intero che rappresenta la chiave
	 * @return boolean, esito dell'operazione
	 * @throws SQLException
	 */
	public synchronized boolean doDelete(int code1, String code2) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String delSQL = "DELETE FROM " + TABLE_NAME + " WHERE Fattura = ? AND Prodotto = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(delSQL);
			ps.setInt(1, code1);
			ps.setString(2, code2);
			
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
	 * @return lista di oggetti di tipo ComposizioneBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<ComposizioneBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		FatturaModel fm = new FatturaModel();
		ProdottoModel pm = new ProdottoModel();
		
		ArrayList<ComposizioneBean> acquisti = new ArrayList<ComposizioneBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ComposizioneBean cb = new ComposizioneBean();
				FatturaBean fb = fm.doRetrieveByKey(rs.getInt(1));
				cb.setFattura(fb);
				ProdottoBean p = pm.doRetrieveByKey(rs.getString(2));
				cb.setProdotto(p);
				cb.setSconto(rs.getInt(3));
				cb.setPrezzoAcquisto(rs.getDouble(4));
				cb.setIVA(rs.getInt(5));
				cb.setQuantita(rs.getInt(6));
				acquisti.add(cb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return acquisti;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo ComposizioneBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<ComposizioneBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		FatturaModel fm = new FatturaModel();
		ProdottoModel pm = new ProdottoModel();
		
		ArrayList<ComposizioneBean> acquisti = new ArrayList<ComposizioneBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ComposizioneBean cb = new ComposizioneBean();
				FatturaBean fb = fm.doRetrieveByKey(rs.getInt(1));
				cb.setFattura(fb);
				ProdottoBean p = pm.doRetrieveByKey(rs.getString(2));
				cb.setProdotto(p);
				cb.setSconto(rs.getInt(3));
				cb.setPrezzoAcquisto(rs.getDouble(4));
				cb.setIVA(rs.getInt(5));
				cb.setQuantita(rs.getInt(6));
				acquisti.add(cb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return acquisti;
	}
	
	public synchronized int getMaxNumFattura() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		int maxf = 0;
		
		String query = "SELECT MAX(Fattura) AS MaxNumFattura FROM " + TABLE_NAME + " WHERE Fattura IS NOT NULL";
		
		try{
			conn = DMConnectionPool.getConnection();
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				if(rs.wasNull()){
					maxf = 0;
				}else{
					maxf = rs.getInt("MaxNumFattura");
				}
			}
		}finally{
			if(stmt != null){
				stmt.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return maxf;
	}
}
