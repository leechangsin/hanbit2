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
	
	public boolean addMember(Member member) {
		conn = DBManager.getConnection();
		String sql = "insert into member(name, uid, passwd, email, date) values(?,?,?,?,now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getUid());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Code : {}", e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;
	}// end addMember(Member member)
	
	public boolean login(String uid, String passwd) {
		conn = DBManager.getConnection();
		String sql = "select uid, passwd from member where uid = ?";
		ResultSet rs;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			rs.next();
			if (!rs.getString("passwd").equals(passwd))
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Code : {}", e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;
	}
	
	public ArrayList<String> getNewMembers() {
		conn = DBManager.getConnection();
		ArrayList<String> nmembers = new ArrayList<String>();
		// 회원목록은 7개 까지만 가져옴
		String sql = "select * from member order by date desc limit 0,7";

		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				nmembers.add(rs.getString("id"));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Code : {}", e.getErrorCode());
		} finally {
			DBManager.closeConnection(pstmt, conn);
		} // end try

		return nmembers;
	}// end getNewMembers()
}
