package tesi.dataQuality.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe per l'accesso al database
 * @author PC-Simone
 *
 */
public class MySqlDao {
	private static MySqlDao dao=new MySqlDao();

	
	
	private static Connection conn = null;
	
	public static boolean connected() {
		if(conn!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	private MySqlDao() {
	}

	public static MySqlDao getIstance(){
		return dao;
	}
	
	public static ResultSet getSentences() {
		
		try {
			chiudiConnessione();
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+Configurator.getDb()+";");
			ResultSet rs=stmt.executeQuery("Select * from " + Configurator.getTable() + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return null;
		
	}
	
	public static ResultSet getDatabases() {
		try {
			chiudiConnessione();
			Statement stmt = MySqlDao.connetti().createStatement();
			//stmt.executeQuery("Use "+db+";");
			ResultSet rs=stmt.executeQuery("show databases;");
			return rs;
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return null;
		
	}
	
	public static ResultSet getTable() {
		
		try {
			chiudiConnessione();
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+Configurator.getDb()+";");
			return stmt.executeQuery("show tables;");
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return null;
		
	}
	
	public static ResultSetMetaData getColumn() {
		
		try {
			chiudiConnessione();
			ResultSetMetaData rsmd = null;
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+Configurator.getDb()+";");
			ResultSet rs;
			rs = stmt.executeQuery("select * from "+Configurator.getTable()+ ";");
			rsmd = rs.getMetaData();
			return rsmd;
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return null;
		
	}
	
	public static String getPKcol() {
		
		try {
			chiudiConnessione();
			ResultSetMetaData rsmd = null;
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+Configurator.getDb()+";");
			ResultSet rs;
			rs = stmt.executeQuery("select * from "+Configurator.getTable()+ ";");
			rsmd = rs.getMetaData();
			return rsmd.getColumnLabel(1);
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return null;
		
	}

	private static Connection connetti(){
		try {
			getIstance();
			conn=DriverManager.getConnection(Configurator.getConnessione(), Configurator.getUser(), Configurator.getPw());
			return conn;
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
		return conn;
		
	}

	private static void chiudiConnessione() {
		try {
			if(conn!=null){
				if(!conn.isClosed()){
					conn.close();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Error");
		}
	
	}
}