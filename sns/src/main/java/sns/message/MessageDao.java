package sns.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sns.member.MemberDao;
import sns.util.DBManager;

public class MessageDao {
	PreparedStatement pstmt;
	Connection conn;
	Logger logger = LoggerFactory.getLogger(MemberDao.class);

	public boolean newMsg(Message msg){
		conn = DBManager.getConnection();
		
		String sql = "insert into message(uid, msg, date)" +
				"values (?,?,now())";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getUid());
			pstmt.setString(2, msg.getMsg());
			pstmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;
	}//end newMsg(Message msg)
	
	public boolean delMsg(int mid){
		conn = DBManager.getConnection();
		
		String sql = "delete from message where mid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;	
	}//end delMsg(int mid)
	
	public boolean newReply(Reply reply){
		conn = DBManager.getConnection();
		
		String sql = "insert into reply(mid,uid,rmsg,date) values(?,?,?,now())";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getMid());
			pstmt.setString(2, reply.getUid());
			pstmt.setString(3, reply.getRmsg());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;
	}//end newReply(Reply reply)
	
	public boolean delReply(int rid){
		conn = DBManager.getConnection();
		
		String sql = "delete from reply where rid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rid);;
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		}
		finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return true;
	}//end delReply(int rid)
	
	public ArrayList<MessageSet> getAll(int cnt, String suid) {
		ArrayList<MessageSet> datas = new ArrayList<MessageSet>();
		String sql;

		try {
			// 전체 게시물인경우
			if (suid == null || suid.equals("")) {
				sql = "select * from message order by date desc limit 0,?";
				pstmt.setInt(1, cnt);
			}
			// 특정회원 게시물인 경우
			else {
				sql = "select * from message where uid=? order by date desc limit 0,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, suid);
				pstmt.setInt(2, cnt);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MessageSet ms = new MessageSet();
				Message m = new Message();
				ArrayList<Reply> rlist = new ArrayList<Reply>();

				m.setMid(rs.getInt("mid"));
				m.setMsg(rs.getString("msg"));
				m.setDate(rs.getDate("date") + " / " + rs.getTime("date"));
				m.setFavcount(rs.getInt("favcount"));
				m.setUid(rs.getString("uid"));

				sql = "select *  from reply where mid=? order by date desc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt("mid"));
				ResultSet rrs = pstmt.executeQuery();
				while (rrs.next()) {
					Reply r = new Reply();
					r.setRid(rrs.getInt("rid"));
					r.setUid(rrs.getString("uid"));
					r.setRmsg(rrs.getString("rmsg"));
					r.setDate(rrs.getDate("date") + "/" + rrs.getTime("date"));
					rlist.add(r);
				}
				rrs.last();
				m.setReplycount(rrs.getRow());

				ms.setMessage(m);
				ms.setRlist(rlist);
				datas.add(ms);
				rrs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}
		return datas;
	}//end getAll(int cnt, String suid)
	
	public void favorite(int mid) {
		conn = DBManager.getConnection();
		String sql = "update s_message set favcount=favcount+1 where mid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		}
		finally {
			DBManager.closeConnection(pstmt, conn);
		}
	}//end favorite(int mid)
}