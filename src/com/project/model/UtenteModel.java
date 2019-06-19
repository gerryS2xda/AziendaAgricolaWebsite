package com.project.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Statement;
import com.project.bean.UtenteBean;
import com.project.utils.Utils;

public class UtenteModel {

	//static field
	private static final String TABLE_NAME= "utente";
	private static final String[] COLUMN_NAME = {"CF", "Nome", "Cognome", "DataNascita", "LuogoNascita", "Sesso", "Indirizzo", 
			"Email", "Telefono", "Username", "Pwd", "Tipo"};

	//public methods
	/**
	 * Memorizza i dati del bean passato come input nel DB
	 * @param fb, oggetto di tipo UtenteBean
	 * @throws SQLException
	 */
	public synchronized void doSave(UtenteBean ub) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String insertSQL = "INSERT INTO " + Utils.getRelazioneTable(TABLE_NAME, COLUMN_NAME) 
							+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try{
			conn = DMConnectionPool.getConnection();	//prendi una connessione disponibile
			ps = conn.prepareStatement(insertSQL);
			
			//imposta i valori da salvare nel DB
			ps.setString(1, ub.getCodiceFiscale());
			ps.setString(2, ub.getNome());
			ps.setString(3, ub.getCognome());
			ps.setDate(4, Date.valueOf(ub.getDataNascita()));
			ps.setString(5, ub.getLuogoNascita());
			ps.setString(6, ub.getSesso());
			ps.setString(7, ub.getAddress());
			ps.setString(8, ub.getEmail());
			ps.setString(9, ub.getTelefono());
			ps.setString(10, ub.getUsername());
			ps.setString(11, ub.getPassword());
			ps.setString(12, ub.getUserType());
			
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
	 * @return bean, oggetto di tipo UtenteBean
	 * @throws SQLException
	 */
	public synchronized UtenteBean doRetrieveByKey(String code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		UtenteBean ub = new UtenteBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE CF = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, code);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ub.setCodiceFiscale(rs.getString(1));
				ub.setNome(rs.getString(2));
				ub.setCognome(rs.getString(3));
				ub.setDataNascita(rs.getDate(4).toLocalDate().toString());
				ub.setLuogoNascita(rs.getString(5));
				ub.setSesso(rs.getString(6));
				ub.setAddress(rs.getString(7));
				ub.setEmail(rs.getString(8));
				ub.setTelefono(rs.getString(9));
				ub.setUsername(rs.getString(10));
				ub.setPassword(rs.getString(11));
				ub.setUserType(rs.getString(12));
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return ub;
	}
	
	/**
	 * Cancella i dati dalla tabella Prodotto in base al valore di una chiave e rest. l'esito dell'operazione
	 * @param code, intero che rappresenta la chiave
	 * @return boolean, esito dell'operazione
	 * @throws SQLException
	 */
	public synchronized boolean doDelete(String code) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String delSQL = "DELETE FROM " + TABLE_NAME + " WHERE CF = ?";
		
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
	 * @return lista di oggetti di tipo UtenteBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<UtenteBean> doRetrieveAll(String order)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<UtenteBean> utenti = new ArrayList<UtenteBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(order != null && (!order.equals(""))){
			querySQL+= " ORDER BY " + order;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				UtenteBean ub = new UtenteBean();
				ub.setCodiceFiscale(rs.getString(1));
				ub.setNome(rs.getString(2));
				ub.setCognome(rs.getString(3));
				ub.setDataNascita(rs.getDate(4).toLocalDate().toString());
				ub.setLuogoNascita(rs.getString(5));
				ub.setSesso(rs.getString(6));
				ub.setAddress(rs.getString(7));
				ub.setEmail(rs.getString(8));
				ub.setTelefono(rs.getString(9));
				ub.setUsername(rs.getString(10));
				ub.setPassword(rs.getString(11));
				ub.setUserType(rs.getString(12));
				utenti.add(ub);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return utenti;
	}
	
	/**
	 * Fornisce una collezione di oggetti che rispettano una certa condizione
	 * @param condition, specifica la condizione
	 * @return lista di oggetti di tipo UtenteBean
	 * @throws SQLException
	 */
	public synchronized ArrayList<UtenteBean> doRetrieveByCond(String condition)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		ArrayList<UtenteBean> utenti = new ArrayList<UtenteBean>();
		
		String querySQL = "SELECT * FROM " + TABLE_NAME;
		if(condition != null && (!condition.equals(""))){
			querySQL+= " WHERE " + condition;
		}
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(querySQL);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				UtenteBean ub = new UtenteBean();
				ub.setCodiceFiscale(rs.getString(1));
				ub.setNome(rs.getString(2));
				ub.setCognome(rs.getString(3));
				ub.setDataNascita(rs.getDate(4).toLocalDate().toString());
				ub.setLuogoNascita(rs.getString(5));
				ub.setSesso(rs.getString(6));
				ub.setAddress(rs.getString(7));
				ub.setEmail(rs.getString(8));
				ub.setTelefono(rs.getString(9));
				ub.setUsername(rs.getString(10));
				ub.setPassword(rs.getString(11));
				ub.setUserType(rs.getString(12));
				utenti.add(ub);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return utenti;
	}
	
	
	public synchronized boolean isUserPresent(UtenteBean utbean) throws SQLException{
		boolean val = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "SELECT CF, Email, Username FROM " + TABLE_NAME + " WHERE CF = ? OR Email = ? OR Username = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, utbean.getCodiceFiscale());
			ps.setString(2, utbean.getEmail());
			ps.setString(3, utbean.getUsername());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getString(1).equals(utbean.getCodiceFiscale()) || rs.getString(2).equals(utbean.getEmail()) || 
						rs.getString(3).equals(utbean.getUsername())){
					val = true;
				}
			}
			
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return val;
	}
	
	public synchronized UtenteBean getUserForLogin(String usr, String pwd) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		UtenteBean ub = new UtenteBean();
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE Username = ? AND Pwd = ?";
		
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, usr);
			ps.setString(2, pwd);
			
			ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()){
				ub.setCodiceFiscale(rs.getString(1));
				ub.setNome(rs.getString(2));
				ub.setCognome(rs.getString(3));
				ub.setDataNascita(rs.getDate(4).toLocalDate().toString());
				ub.setLuogoNascita(rs.getString(5));
				ub.setSesso(rs.getString(6));
				ub.setAddress(rs.getString(7));
				ub.setEmail(rs.getString(8));
				ub.setTelefono(rs.getString(9));
				ub.setUsername(rs.getString(10));
				ub.setPassword(rs.getString(11));
				ub.setUserType(rs.getString(12));
			}
			
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return ub;
	}
	
	public synchronized int getNumberofUser(String usrType)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		int n = 0;
		
		String query = "select count(*) as NumUtenti from utente where Tipo = ?";
		try{
			conn = DMConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, usrType);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				n = rs.getInt(1);
			}
		}finally{
			if(ps != null){
				ps.close();
			}
			DMConnectionPool.releaseConnection(conn);	//rilascia la connessione e la rende nuovamente disponibile
		}
		return n;
	}
	
	/* Modifica le informazioni presenti in una tupla della tabella utente individuata dal CF */
	public synchronized boolean updateUserData(String code, String attr, Object value)throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		boolean val = false;
		
		String str = ""+value;
		if(value instanceof String){
			str = "'" + value + "'";
		}
		
		String query = "update " + TABLE_NAME + " set " + attr + " = " + str + " where CF = '" + code +"';" ;
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

