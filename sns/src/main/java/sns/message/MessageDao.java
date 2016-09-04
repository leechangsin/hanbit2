package sns.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sns.util.DBManager;

public class MessageDao {
	PreparedStatement pstmt;
	Connection conn;
	//Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean newMsg(Message msg){
		conn = DBManager.getConnection();
		
		String sql = "insert into message(uid, msg, date) values (?,?,now())";
		
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
		}//end try
		
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
		}//end try
		
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
			
			sql = "update message set replycount=replycount+1 where mid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}//end try
		
		return true;
	}//end newReply(Reply reply)
	
	public boolean delReply(int rid){
		conn = DBManager.getConnection();
		
		String sql = "select mid from reply where rid =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rid);
			ResultSet resultSet = pstmt.executeQuery();
			
			resultSet.next();
			int mid = resultSet.getInt("mid");
			sql = "update message set replycount=replycount-1 where mid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
			
			sql = "delete from reply where rid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
			return false;
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}//end try
		
		return true;
	}//end delReply(int rid)
	
	public ArrayList<MessageSet> getAll(int cnt, String suid) {
		conn = DBManager.getConnection();

		ArrayList<MessageSet> datas = new ArrayList<>();
		String sql;

		try {
			// 전체 게시물인경우
			if (suid == null || suid.equals("")) {
				sql = "select * from message order by date desc limit 0,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cnt);
			} // 특정회원 게시물인 경우
			else {
				sql = "select * from message where uid=? order by date desc limit 0,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, suid);
				pstmt.setInt(2, cnt);
			}

			ResultSet messageResultSet = pstmt.executeQuery();
			while (messageResultSet.next()) {
				MessageSet messageSet = new MessageSet();
				Message message = new Message();
				ArrayList<Reply> replys = new ArrayList<Reply>();

				message.setMid(messageResultSet.getInt("mid"));
				message.setUid(messageResultSet.getString("uid"));
				message.setMsg(messageResultSet.getString("msg"));
				message.setFavcount(messageResultSet.getInt("favcount"));
				message.setReplycount(messageResultSet.getInt("replycount"));
				message.setDate(messageResultSet.getDate("date") + "/" + messageResultSet.getTime("date"));
				
				sql = "select *  from reply where mid=? order by date desc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, messageResultSet.getInt("mid"));
				ResultSet replyResultSet = pstmt.executeQuery();
				while (replyResultSet.next()) {
					Reply reply = new Reply();
					reply.setRid(replyResultSet.getInt("rid"));
					reply.setUid(replyResultSet.getString("uid"));
					reply.setRmsg(replyResultSet.getString("rmsg"));
					reply.setDate(replyResultSet.getDate("date") + "/" + replyResultSet.getTime("date"));
					replys.add(reply);
				}//end while (replyResultSet.next())

				messageSet.setMessage(message);
				messageSet.setRlist(replys);
				datas.add(messageSet);
			}//end while (messageResultSet.next())
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}//end try

		return datas;
	}// end getAll(int cnt, String suid)
	
	public void favorite(int mid) {
		conn = DBManager.getConnection();
		
		String sql = "update message set favcount=favcount+1 where mid=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode());
		} finally {
			DBManager.closeConnection(pstmt, conn);
		}//end try
	}//end favorite(int mid)
}