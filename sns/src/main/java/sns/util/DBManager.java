package sns.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		return conn;
	}//end getConnection()
	
	public static void closeConnection(PreparedStatement pstmt, Connection conn ){
		try{
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		}
	}//end closeConnection(PreparedStatement pstmt, Connection conn )
	
	public static void closeConnection(PreparedStatement pstmt, Connection conn, ResultSet... resultSet){
		try{
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				conn.close();
			for(int i = resultSet.length; i > 0; i--)
				resultSet[i-1].close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		}
	}//end closeConnection(PreparedStatement pstmt, Connection conn, ResultSet resultSet)
}