package text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {
	public static Connection getConnection(){
		Connection conn;
		
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
			conn = ds.getConnection();
		} catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
		return conn;
	}//end getConnection()
	
	public static void closeConnection(PreparedStatement pstmt, Connection conn ){
		try{
			pstmt.close();
			conn.close();
		} catch(SQLException e){
			System.out.println(e);
			e.printStackTrace();
		}
	}//end closeConnection(PreparedStatement pstmt, Connection conn )

}