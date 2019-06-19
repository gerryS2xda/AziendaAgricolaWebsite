package com.project.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import com.project.bean.PostaBean;
import com.project.utils.Utils;

public class PostaModel {

	//static field
	private static final String TABLE_NAME = "posta";
	private static final String[] COLUMN_NAME = {"ID", "Mittente", "Destinatario", "Oggetto", "ContenutoTesto", "TipoPosta", "DataEmail", "Ora", "Lettura", "TipoUtente"};

	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param p, oggetto di tipo PostaBean
	 * @throws SQLException
	 */
	
	public synchronized void doSave(PostaBean e) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setInt(1, e.getEmailID());
			ps.setString(2, e.getMittente());
			ps.setString(3, e.getDestinatario());
			ps.setString(4, e.getObjectofEmail());
			ps.setString(5, e.getContent());
			ps.setString(6, e.getTypeofEmail());
			ps.setDate(7, Date.valueOf(e.getDataofEmail()));
			ps.setTime(8, Time.valueOf(e.getHour()));
			ps.setBoolean(9, e.isEmailRead());
			ps.setString(10, e.getUserTypeofMailbox());
			
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
	 * Recupera i dati del bean dal DB tramite una chiave e li salva nell'oggetto PostaBean che viene restituito
	 * @param code, stringa usata come chiave per la selectSQL
	 * @return bean, oggetto di tipo PostaBean
	 * @throws SQLException
	 */
	public synchronized PostaBean doRetrieveByKey(int code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		PostaBean pb = new PostaBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				pb.setEmailID(rs.getInt(1));
				pb.setMittente(rs.getString(2));
				pb.setDestinatario(rs.getString(3));
				pb.setObjectofEmail(rs.getString(4));
				pb.setContent(rs.getString(5));
				pb.setTypeofEmail(rs.getString(6));
				pb.setDataofEmail(rs.getDate(7).toLocalDate().toString());
				pb.setHour(rs.getTime(8).toLocalTime().toString());
				pb.setEmailReadState(rs.getBoolean(9));
				pb.setUserTypeofMailbox(rs.getString(10));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return pb;
	}
	
	/**
	 * Cancella i dati dalla tabella Posta in base al valore di una chiave e rest. l'esito dell'operazione
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
	 * @return lista di oggetti di tipo PostaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<PostaBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<PostaBean> email = new ArrayList<PostaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				PostaBean pb = new PostaBean();
				pb.setEmailID(rs.getInt(1));
				pb.setMittente(rs.getString(2));
				pb.setDestinatario(rs.getString(3));
				pb.setObjectofEmail(rs.getString(4));
				pb.setContent(rs.getString(5));
				pb.setTypeofEmail(rs.getString(6));
				pb.setDataofEmail(rs.getDate(7).toLocalDate().toString());
				pb.setHour(rs.getTime(8).toLocalTime().toString());
				pb.setEmailReadState(rs.getBoolean(9));
				pb.setUserTypeofMailbox(rs.getString(10));
				email.add(pb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return email;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo PostaBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<PostaBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<PostaBean> email = new ArrayList<PostaBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				PostaBean pb = new PostaBean();
				pb.setEmailID(rs.getInt(1));
				pb.setMittente(rs.getString(2));
				pb.setDestinatario(rs.getString(3));
				pb.setObjectofEmail(rs.getString(4));
				pb.setContent(rs.getString(5));
				pb.setTypeofEmail(rs.getString(6));
				pb.setDataofEmail(rs.getDate(7).toLocalDate().toString());
				pb.setHour(rs.getTime(8).toLocalTime().toString());
				pb.setEmailReadState(rs.getBoolean(9));
				pb.setUserTypeofMailbox(rs.getString(10));
				email.add(pb);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return email;
	}
	
	// dammi il max numero Email ID tra quelli presenti nel DB
	public synchronized int getMaxEmailID() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		int max = 0;
		
		String query = "SELECT MAX(ID) AS MaxEmailID FROM " + TABLE_NAME;
		
		try{
			conn = DMConnectionPool.getConnection();
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				if(!rs.wasNull()){
					max = rs.getInt("MaxEmailID");
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
	
	/* Modifica le informazioni presenti in una tupla della tabella posta individuata da 'email ID' */
	public synchronized boolean updatePostaData(int code, String attr, Object value)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String str = ""+value;
		if(value instanceof String){
			str = "'" + value + "'";
		}
		
		String query = "update " + TABLE_NAME + " set " + attr + " = " + str + " where ID = " + code +";" ;
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			if(ps.executeUpdate(query) != 0) val = true;
			conn.commit();
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return val;
	}
}
