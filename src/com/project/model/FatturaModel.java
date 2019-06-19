package com.project.model;

import java.sql.*;
import java.util.ArrayList;
import com.project.bean.*;
import com.project.utils.Utils;

public class FatturaModel {

	private static final String TABLE_NAME= "fattura";
	private static final String[] COLUMN_NAME = {"NumeroFattura", "DataEmissione", "Importo", "Cliente", "Partenza", "Arrivo", "DataPartenza", "DataArrivo", "SpeseSpedizione"};
	
	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param fb, oggetto di tipo FatturaBean
	 * @throws SQLException
	 */
	public synchronized void doSave(FatturaBean fb) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setInt(1, fb.getNumero());
			ps.setDate(2, Date.valueOf(fb.getDataEmissione()));
			ps.setDouble(3, fb.getImporto());
			ps.setString(4, fb.getCliente().getCodiceFiscale());
			ps.setString(5, fb.getPartenza());
			ps.setString(6, fb.getArrivo());
			ps.setDate(7, Date.valueOf(fb.getDataPartenza()));
			ps.setDate(8, Date.valueOf(fb.getDataArrivo()));
			ps.setDouble(9, fb.getSpeseSpedizione());
			
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
	 * Recupera i dati del bean dal DB tramite una chiave e li salva nell'oggetto FatturaBean che viene restituito
	 * @param code, intero usata come chiave per la selectSQL
	 * @return bean, oggetto di tipo FatturaBean
	 * @throws SQLException
	 */
	public synchronized FatturaBean doRetrieveByKey(int code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		FatturaBean fb = new FatturaBean();
		UtenteModel um = new UtenteModel();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE NumeroFattura = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				fb.setNumero(rs.getInt(1));
				fb.setDataEmissione(rs.getDate(2).toLocalDate().toString());
				fb.setImporto(rs.getDouble(3));
				fb.setCliente(um.doRetrieveByKey(rs.getString(4)));
				fb.setPartenza(rs.getString(5));
				fb.setArrivo(rs.getString(6));
				fb.setDataPartenza(rs.getDate(7).toLocalDate().toString());
				fb.setDataArrivo(rs.getDate(8).toLocalDate().toString());
				fb.setSpeseSpedizione(rs.getDouble(9));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return fb;
	}
	
	/**
	 * Cancella i dati dalla tabella Prodotto in base al valore di una chiave e rest. l'esito dell'operazione
	 * @param code, intero che rappresenta la chiave
	 * @return boolean, esito dell'operazione
	 * @throws SQLException
	 */
	public synchronized boolean doDelete(int code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String delSQL = "DELETE FROM " + TABLE_NAME + " WHERE NumeroFattura = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(delSQL);
			ps.setInt(1, code);
			
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
	 * @return lista di oggetti di tipo FatturaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<FatturaBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		UtenteModel um = new UtenteModel();
		
		ArrayList<FatturaBean> fatture = new ArrayList<FatturaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				FatturaBean fb = new FatturaBean();
				fb.setNumero(rs.getInt(1));
				fb.setDataEmissione(rs.getDate(2).toLocalDate().toString());
				fb.setImporto(rs.getDouble(3));
				fb.setCliente(um.doRetrieveByKey(rs.getString(4)));
				fb.setPartenza(rs.getString(5));
				fb.setArrivo(rs.getString(6));
				fb.setDataPartenza(rs.getDate(7).toLocalDate().toString());
				fb.setDataArrivo(rs.getDate(8).toLocalDate().toString());
				fb.setSpeseSpedizione(rs.getDouble(9));
				fatture.add(fb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return fatture;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo FatturaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<FatturaBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		UtenteModel um = new UtenteModel();
		
		ArrayList<FatturaBean> fatture = new ArrayList<FatturaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				FatturaBean fb = new FatturaBean();
				fb.setNumero(rs.getInt(1));
				fb.setDataEmissione(rs.getDate(2).toLocalDate().toString());
				fb.setImporto(rs.getDouble(3));
				fb.setCliente(um.doRetrieveByKey(rs.getString(4)));
				fb.setPartenza(rs.getString(5));
				fb.setArrivo(rs.getString(6));
				fb.setDataPartenza(rs.getDate(7).toLocalDate().toString());
				fb.setDataArrivo(rs.getDate(8).toLocalDate().toString());
				fb.setSpeseSpedizione(rs.getDouble(9));
				fatture.add(fb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return fatture;
	}
	
	//altri metodi
	public synchronized int getMaxNumFattura() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		int maxf = 0;
		
		String query = "SELECT MAX(NumeroFattura) AS MaxNumFattura FROM " + TABLE_NAME;
		
		try{
			conn = DMConnectionPool.getConnection();
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				if(!rs.wasNull()){
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
	
	public synchronized double getIncassoOfMonth(int mese) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		double tot = 0;
		
		String querySQL = "SELECT SUM(Importo) AS IncassoMese FROM " + TABLE_NAME + " WHERE MONTH(DataEmissione) = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setInt(1, mese);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					tot = rs.getDouble("IncassoMese");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return tot;
	}
	
	public synchronized int getNumerOrdiniUscita(String date) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		int n = 0;
		
		String querySQL = "SELECT COUNT(NumeroFattura) AS OrdiniUscita FROM " + TABLE_NAME + " WHERE DataArrivo > ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setString(1, date);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					n = rs.getInt("OrdiniUscita");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return n;
	}
	
	public synchronized double getIncassoOfMonthofClient(String cliente, int mese) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		double tot = 0;
		
		String querySQL = "SELECT SUM(Importo) AS IncassoMese FROM " + TABLE_NAME + " WHERE Cliente = ? AND MONTH(DataEmissione) = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setString(1, cliente);
			ps.setInt(2, mese);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					tot = rs.getDouble("IncassoMese");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return tot;
	}
	
	public synchronized double getIncassoOfLastMonthofClient(String cliente) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		double tot = 0;
		
		String querySQL = "SELECT SUM(Importo) AS IncassoMese FROM " + TABLE_NAME + " WHERE Cliente = ? AND MONTH(DataEmissione) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setString(1, cliente);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					tot = rs.getDouble("IncassoMese");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return tot;
	}
	
	public synchronized double getIncassoOfYearofClient(String cliente, int year) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		double tot = 0;
		
		String querySQL = "SELECT SUM(Importo) AS IncassoAnno FROM " + TABLE_NAME + " WHERE Cliente = ? AND YEAR(DataEmissione) = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setString(1, cliente);
			ps.setInt(2, year);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					tot = rs.getDouble("IncassoAnno");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return tot;
	}
	
	public synchronized double getIncassoOfLastYearofClient(String cliente) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		double tot = 0;
		
		String querySQL = "SELECT SUM(Importo) AS IncassoAnno FROM " + TABLE_NAME + " WHERE Cliente = ? AND YEAR(DataEmissione) = YEAR(CURRENT_DATE - INTERVAL 1 YEAR)";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			ps.setString(1, cliente);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(!rs.wasNull()){
					tot = rs.getDouble("IncassoAnno");
				}
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return tot;
	}
}
