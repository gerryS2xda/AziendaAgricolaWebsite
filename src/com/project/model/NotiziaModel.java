package com.project.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.project.bean.NotiziaBean;
import com.project.utils.Utils;

public class NotiziaModel {
	
	//static field
	private static final String TABLE_NAME = "notizia";
	private static final String[] COLUMN_NAME = {"ID", "Titolo", "ContenutoTesto", "DataInserimento", "DataUltimaModifica", "Tipo", "Immagine"};
	
	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param p, oggetto di tipo NotiziaBean
	 * @throws SQLException
	 */
	
	public synchronized void doSave(NotiziaBean n) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setInt(1, n.getNewsID());
			ps.setString(2, n.getTitle());
			ps.setString(3, n.getNewsContent());
			ps.setDate(4, Date.valueOf(n.getDataInserimento()));
			ps.setDate(5, Date.valueOf(n.getDateofLastModified()));
			ps.setString(6, n.getNewsType());
			ps.setString(7, n.getURLImage());
			
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
	 * Recupera i dati del bean dal DB tramite una chiave e li salva nell'oggetto NotiziaBean che viene restituito
	 * @param code, stringa usata come chiave per la selectSQL
	 * @return bean, oggetto di tipo NotiziaBean
	 * @throws SQLException
	 */
	public synchronized NotiziaBean doRetrieveByKey(int code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		NotiziaBean nb = new NotiziaBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				nb.setNewsID(rs.getInt(1));
				nb.setTitle(rs.getString(2));
				nb.setNewsContent(rs.getString(3));
				nb.setDataInserimento(rs.getDate(4).toLocalDate().toString());
				nb.setDateofLastModified(rs.getDate(5).toLocalDate().toString());
				nb.setNewsType(rs.getString(6));
				nb.setURLImage(rs.getString(7));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		System.out.println(nb.getNewsID());
		return nb;
	}
	
	/**
	 * Cancella i dati dalla tabella Notizia in base al valore di una chiave e rest. l'esito dell'operazione
	 * @param code, stringa che rappresenta la chiave
	 * @return boolean, esito dell'operazione
	 * @throws SQLException
	 */
	public synchronized boolean doDelete(int code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String delSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
		
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
	 * @return lista di oggetti di tipo NotiziaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<NotiziaBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<NotiziaBean> notizie = new ArrayList<NotiziaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				NotiziaBean nb = new NotiziaBean();
				nb.setNewsID(rs.getInt(1));
				nb.setTitle(rs.getString(2));
				nb.setNewsContent(rs.getString(3));
				nb.setDataInserimento(rs.getDate(4).toLocalDate().toString());
				nb.setDateofLastModified(rs.getDate(5).toLocalDate().toString());
				nb.setNewsType(rs.getString(6));
				nb.setURLImage(rs.getString(7));
				notizie.add(nb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return notizie;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo NotiziaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<NotiziaBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<NotiziaBean> notizie = new ArrayList<NotiziaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				NotiziaBean nb = new NotiziaBean();
				nb.setNewsID(rs.getInt(1));
				nb.setTitle(rs.getString(2));
				nb.setNewsContent(rs.getString(3));
				nb.setDataInserimento(rs.getDate(4).toLocalDate().toString());
				nb.setDateofLastModified(rs.getDate(5).toLocalDate().toString());
				nb.setNewsType(rs.getString(6));
				nb.setURLImage(rs.getString(7));
				notizie.add(nb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return notizie;
	}
	
	//altri metodi
	public synchronized int getMaxNewsID() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		int max = 0;
		
		String query = "SELECT MAX(ID) AS Max_News_ID FROM " + TABLE_NAME;
		
		try{
			conn = DMConnectionPool.getConnection();
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				if(rs.wasNull()){
					max = 0;
				}else{
					max = rs.getInt("Max_News_ID");
				}
			}
		}finally{
			if(stmt != null){
				stmt.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return max;
	}
}

