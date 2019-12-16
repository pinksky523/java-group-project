package project.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import project.DBconn.DBconn;
import project.VO.ComplBoardVO;
import project.VO.ComplComtVO;

public class ComplComtDAO {
	private String query;
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement pstmt;
	private boolean result;
	
	//해당 게시글의 해당페이지 댓글 목록 가져오기
	public List<ComplComtVO> getComplComtListPage(int complNo,int page){
			List<ComplComtVO> complComtVOList=new ArrayList<ComplComtVO>();  
			query ="select n,compl_no,compl_comt_no,mem_id,compl_comt_content,compl_comt_date,compl_comt_like  from( \r\n" + 
					"									select rownum n,compl_no,compl_comt_no,mem_id,compl_comt_content,compl_comt_date,compl_comt_like\r\n" + 
					"									from(select rownum num,compl_no,compl_comt_no,mem_id,compl_comt_content,compl_comt_date,compl_comt_like\r\n" + 
					"									     from t_complboard_comt\r\n" + 
					"                                        where compl_no=?\r\n" + 
					"									     order by compl_comt_date desc\r\n" + 
					"									    ))\r\n" + 
					"									where n/10>? and n/10<=?";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, complNo);
			pstmt.setInt(2, page-1);
			pstmt.setInt(3, page);
			rs=pstmt.executeQuery();
			while(rs.next()) { 
				ComplComtVO ccVO=new ComplComtVO(); 
				ccVO.setComplNo(rs.getInt("compl_no"));
				ccVO.setComplComtNo(rs.getInt("compl_comt_no"));
				ccVO.setMemId(rs.getString("mem_id"));
				ccVO.setComplComtContent(rs.getString("compl_comt_content"));
				ccVO.setComplComtDate(rs.getString("compl_comt_date"));
				ccVO.setComplComtLike(rs.getInt("compl_comt_like"));
				complComtVOList.add(ccVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return complComtVOList;
	}//메소드 엔드
	
	
	//해당 게시글에 댓글 달기
	public boolean writeComplComt(int complNo,int complComtNo,String id,String content) {

		query = "insert into t_complboard_comt values(?,?,?,?,SYSDATE,0)";

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, complNo);
			pstmt.setInt(2, complComtNo);
			pstmt.setString(3,id);
			pstmt.setString(4, content);

			if (pstmt.executeUpdate() == 1) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(pstmt);
		}

		return result;

	}// 메서드엔드
	
	//////////해당게시물의 댓글 수 가져오기/////////////////////////////////////////////////////////////////
	public int getSizeOfComtSize(int complNo) {
		int lastNo = -1;
		query = "select count(compl_comt_no) as comt_size from t_complboard_comt where compl_no=?";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, complNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				lastNo=rs.getInt("comt_size");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(pstmt);
		}

		return lastNo;
	}
	//댓글삭제
	public boolean deleteComplComt(int complNo,int complComtNo) {
		query = "delete t_complboard_comt where compl_no=? and compl_comt_no=?";

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, complNo);
			pstmt.setInt(2, complComtNo);
			

			if (pstmt.executeUpdate() == 1) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(pstmt);
		}

		return result;
	}
	
	//댓글 좋아요
		public boolean updateLikeCnt(int complNo,int comtNo) {
			query = "update t_complboard_comt set compl_comt_like=compl_comt_like+1 where compl_no=? and compl_comt_no=?";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1,complNo);
				pstmt.setInt(2,comtNo);
				
				if (pstmt.executeUpdate() == 1) {
					result = true;
					} else {
					result = false;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(pstmt);}
			
			return result;
		}
		
		//작성자 라이크 수 올리기
		public boolean updateMemLike(String memId) { 
			query = "update t_member set mem_like=mem_like+1 where mem_id=?";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setString(1,memId);
		
				if (pstmt.executeUpdate() == 1) {
					result = true;
					} else {
					result = false;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(pstmt);}
			
			return result;
		}
		
		//좋아요 누른 댓글 추가하기
		public boolean insertLikeBoardNo(String id,int complNo,int comtNo) {
			query = "INSERT INTO t_likeboard VALUES(?,1,?,?)";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setInt(2, complNo);
				pstmt.setInt(3, comtNo);
		
				if (pstmt.executeUpdate() == 1) {
					result = true;
					} else {
					result = false;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(pstmt);}
			
			return result;
		}
		
		//좋아요누른 댓글인지 확인
		public int isThereComplComt(String id,int complNo,int comtNo) {
			int re=-1;
			query = "select count(*) as isthere from t_likeboard where mem_id=? and \r\n" + 
					"board_type=1 and board_no=? and comt_no=?";
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setInt(2, complNo);
				pstmt.setInt(3, comtNo);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					re=rs.getInt("isthere");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBconn.close(pstmt);
			}
			return re;
		}

	//해당 댓글의 댓글의 좋아요 수 가져오기
		public int getComtCnt(int complNo,int comtNo) {
			int re=-1;
			query = "select compl_comt_like from t_complboard_comt where compl_no=? and compl_comt_no=?";
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1, complNo);
				pstmt.setInt(2, comtNo);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					re=rs.getInt("compl_comt_like");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBconn.close(pstmt);
			}
			return re;
		}
}
