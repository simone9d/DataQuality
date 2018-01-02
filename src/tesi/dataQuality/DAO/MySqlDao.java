package tesi.dataQuality.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class MySqlDao {
	private static MySqlDao dao=new MySqlDao();

	private final static String host="localhost";
	public static String db=new String();
	public static String user=new String();
	public static String table=new String(); 
	private static String connessione="jdbc:mysql://" + host + ":3306";	//"/ + db;
	public static String pw=new String();
	
	private static Connection conn = null;
	
	public static boolean connected() {
		if(conn!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	public MySqlDao() {
	}

	public static MySqlDao getIstance(){
		return dao;
	}
	
	public static ResultSet getSentences() {
		
		try {
			chiudiConnessione();
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+db+";");
			ResultSet rs=stmt.executeQuery("Select * from " + table + ";");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static ResultSet getTable() {
		
		try {
			chiudiConnessione();
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+db+";");
			return stmt.executeQuery("show tables;");
		} catch (SQLException e) {}
		return null;
		
	}
	
	public static ResultSetMetaData getColumn() {
		
		try {
			chiudiConnessione();
			ResultSetMetaData rsmd = null;
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+db+";");
			ResultSet rs;
			rs = stmt.executeQuery("select * from "+table+ ";");
			rsmd = rs.getMetaData();
			return rsmd;
		} catch (SQLException e) {}
		return null;
		
	}
	
	public static String getPKcol() {
		
		try {
			chiudiConnessione();
			ResultSetMetaData rsmd = null;
			Statement stmt = MySqlDao.connetti().createStatement();
			stmt.executeQuery("Use "+db+";");
			ResultSet rs;
			rs = stmt.executeQuery("select * from "+table+ ";");
			rsmd = rs.getMetaData();
			return rsmd.getColumnLabel(1);
		} catch (SQLException e) {}
		return null;
		
	}

	private static Connection connetti() throws SQLException{
		getIstance();
		conn=DriverManager.getConnection(connessione, user, pw);
		return conn;
	}

	private static void chiudiConnessione() throws SQLException{
		if(conn!=null){
			if(!conn.isClosed()){
				conn.close();
			}
		}
	}
}