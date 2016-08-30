package sns.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sns.util.DBManager;

public class MemberDao {
	
	Connection conn;
	PreparedStatement pstmt;
	Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	public ArrayList<String> getNewMembers(){
		conn = DBManager.getConnection();
		ArrayList<String> nmembers = new ArrayList<String>();
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
			DBManager.closeConnection(pstmt, conn);
		}
		
		return nmembers;
	}//end getNewMembers()
}
