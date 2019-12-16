package project.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import project.DBconn.DBconn;
import project.VO.*;

public class MessageDAO {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private boolean result;
	private String query;
	
		
	//쪽지 보낼 때 아이디 존재 유무 확인 메서드
	public boolean isExist(String id, int iOrE) {
		boolean result = true;
			query = "SELECT * FROM t_member WHERE mem_id = '" + id + "'";
			try {
				stmt = DBconn.getConnection().createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {	//아이디 존재하면 true 리턴
					result = true;
					} else {		//아이디 존재하지 않으면 false 리턴
					result = false;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBconn.close(stmt);
				}

			return result;
	}
	
	//쪽지 보내기 메서드
	public boolean send(MessageVO mvo) {
		query = "INSERT INTO t_message(message_no, mem_send_id, message_content, mem_receive_id, message_send_date) VALUES(t_message_seq.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, mvo.getMemSendId());
			pstmt.setString(2, mvo.getMessageContent());
			pstmt.setString(3, mvo.getMemReceiveId());
			
			if(pstmt.executeUpdate() == 1) {
				result = true;		//쪽지보내기 성공
			}else {
				result = false;		//쪽지보내기 실패
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(pstmt);
		}
		return result;
	}
	
	//쪽지 삭제하기 메서드
	public boolean delete(int checkedRecord) {
		try {
			stmt = DBconn.getConnection().createStatement();
			query = "DELETE FROM t_message WHERE message_no="+checkedRecord;
			if (stmt.executeUpdate(query) == 1) {
				DBconn.getConnection().commit();
					result = true;
				} else {
					result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(stmt);}
		
		return result;
		
	}
	
//	//보낸쪽지 삭제하기 메서드
//	public boolean delete2(int checkedRecord) {
//		try {
//			stmt = DBconn.getConnection().createStatement();
//			query = "DELETE FROM t_message WHERE message_no="+checkedRecord;
//			if (stmt.executeUpdate(query) == 1) {
//				DBconn.getConnection().commit();
//					result = true;
//				} else {
//					result = false;
//				}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally { DBconn.close(stmt);}
//		
//		return result;
//		
//	}

	//받은쪽지 가져오는 메서드
	public List<MessageVO> getReceivedM(int page, String memId) {
		List<MessageVO> list = new ArrayList<MessageVO>();
		query = "SELECT num, message_no, mem_send_id, message_content, mem_receive_id, message_send_date FROM(SELECT ROWNUM num, message_no, mem_send_id, message_content, mem_receive_id, message_send_date FROM (SELECT * FROM t_message ORDER BY message_send_date DESC) WHERE mem_receive_id ='" +memId+ "') WHERE num/6 >"+(page-1)+" AND num/6 <="+page;	
		
		try {
			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				MessageVO mvo = new MessageVO();
				mvo.setMessageNo(rs.getInt("message_no"));
				mvo.setMemSendId(rs.getString("mem_send_id"));
				mvo.setMessageContent(rs.getString("message_content"));
				mvo.setMemReceiveId(rs.getString("mem_receive_id"));
				mvo.setMessageSendDate(rs.getString("message_send_date"));
				list.add(mvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {DBconn.close(stmt, rs); }
		return list;
	}
	
	//보낸쪽지 가져오는 메서드
	public List<MessageVO> getSendM(int page, String memId) {
		List<MessageVO> list = new ArrayList<MessageVO>();
		query = "SELECT num, message_no, mem_send_id, message_content, mem_receive_id, message_send_date FROM(SELECT ROWNUM num, message_no, mem_send_id, message_content, mem_receive_id, message_send_date FROM (SELECT * FROM t_message ORDER BY message_send_date DESC) WHERE mem_send_id ='" +memId+ "') WHERE num/6 >"+(page-1)+" AND num/6 <="+page;		
		try {
			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				MessageVO mvo = new MessageVO();
				mvo.setMessageNo(rs.getInt("message_no"));
				mvo.setMemSendId(rs.getString("mem_send_id"));
				mvo.setMessageContent(rs.getString("message_content"));
				mvo.setMemReceiveId(rs.getString("mem_receive_id"));
				mvo.setMessageSendDate(rs.getString("message_send_date"));
				list.add(mvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {DBconn.close(stmt, rs); }
		return list;
	}
	
	

}
