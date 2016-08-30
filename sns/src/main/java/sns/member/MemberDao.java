package sns.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberDao {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	Logger logger = LoggerFactory.getLogger(MemberDao.class);

	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://127.0.0.1:3306/hanbit1";
	
	// DB연결 메서드
		void connect() {
			try {
				Class.forName(jdbc_driver);
				conn = DriverManager.getConnection(jdbc_url, "developer", "developer");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		void disconnect() {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // end if(pstmt != null)

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} // end if(conn != null)
		}
	
	public ArrayList<String> getNewMembers(){
		ArrayList<String> nmembers = new ArrayList<String>();
		connect();
		//회원목록은 7개 까지만 가져옴
		String sql = "select * from member order by date desc limit 0,7";
		
		try{
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
				nmembers.add(rs.getString("id"));
		} catch(SQLException e){
			e.printStackTrace();
			logger.info("Error Code : {}", e.getErrorCode());
		} finally{
			try{
				pstmt.close();
				conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}//end try
		}//end try
		
		return nmembers;
	}//end getNewMembers()
}
