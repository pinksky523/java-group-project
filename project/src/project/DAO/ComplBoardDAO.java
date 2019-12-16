package project.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import project.DBconn.DBconn;
import project.VO.ComplBoardVO;
import project.user.main.UserMain;

public class ComplBoardDAO {
	private String query;
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement pstmt;
	private boolean result;

	   
	//철현 추가
	   //특정 아이디의  compl게시판 작성 글 목록(3페이지)
	   public List<ComplBoardVO> getPageOfComplPostsInfo(String userId, int page) {
	      List<ComplBoardVO> list = new ArrayList<ComplBoardVO>();
	      String query = "SELECT * FROM(SELECT ROWNUM num, a.* FROM (SELECT * FROM t_complboard) a WHERE compl_id = '"+userId+"') WHERE num/3 >"+(page-1)+" AND num/3 <="+page;
	      try {
	         stmt = DBconn.getConnection().createStatement();
	         rs = stmt.executeQuery(query);
	         while(rs.next()) {
	            ComplBoardVO cbvo = new ComplBoardVO();
	            cbvo.setComplNo(rs.getInt("compl_no"));
	            cbvo.setComplType(rs.getInt("compl_type"));
	            cbvo.setComplTitle(rs.getString("compl_title"));
	            cbvo.setComplContent(rs.getString("compl_content"));
	            cbvo.setComplId(rs.getString("compl_id"));
	            cbvo.setComplDate((rs.getString("compl_date")));
	            cbvo.setComplViewCnt(rs.getInt("compl_viewcnt"));
	            cbvo.setComplLike(rs.getInt("compl_like"));
	            cbvo.setComplIsSecret(rs.getInt("compl_is_secret"));
	            list.add(cbvo);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {DBconn.close(stmt, rs); }
	      return list;
	   }//메서드 끝
	   
	 ///////////수경부분
	   
	//해당페이지의 건의게시판 글 목록을 가져오는 메서드
	public List<ComplBoardVO> getPageOfComplPostsInfo(int page) {
		
		List<ComplBoardVO> combdVOList=new ArrayList<ComplBoardVO>();  
		
		query ="select n,compl_no,compl_type,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  from(\r\n" + 
				"select rownum n,compl_no,compl_type,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
				"from(select rownum num,compl_no,compl_type,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
				"     from t_complboard \r\n" + 
				"     order by compl_type desc,compl_date desc,compl_no desc\r\n" + 
				"    ))\r\n" + 
				"where n/15>? and n/15<=?";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, page-1);
			pstmt.setInt(2, page);
			rs=pstmt.executeQuery();
			while(rs.next()) { 
				ComplBoardVO complbdVO=new ComplBoardVO();
				complbdVO.setComplNo(rs.getInt("compl_no"));
				complbdVO.setComplType(rs.getInt("compl_type"));
				complbdVO.setComplId(rs.getString("compl_id"));
				complbdVO.setComplTitle(rs.getString("compl_title"));
				complbdVO.setComplLike(rs.getInt("compl_like"));
				complbdVO.setComplViewCnt(rs.getInt("compl_viewcnt"));
				complbdVO.setComplDate(rs.getString("compl_date"));
				complbdVO.setComplIsSecret(rs.getInt("compl_is_secret"));
				combdVOList.add(complbdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return combdVOList;
	}//메서드끝
	
	//내용으로 건의게시판 글 사용자 검색 메소드
	public List<ComplBoardVO> getPageUsingContents(int page,String contents,String userId){
		List<ComplBoardVO> combdVOList=new ArrayList<ComplBoardVO>();  
	
		query= "select n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  from(\r\n" + 
				"select rownum n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
				"from(select rownum num,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
				"     from t_complboard \r\n" + 
				"     where (compl_content like '%"+contents+"%' and compl_is_secret =0) or (compl_content like '%"+contents+"%'\r\n" + 
				"          and compl_is_secret=1 and compl_id like ?)\r\n" + 
				"     order by compl_date desc\r\n" + 
				"    ))\r\n" + 
				"where n/15>? and n/15<=?";
		

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, page-1);
			pstmt.setInt(3, page);
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				ComplBoardVO complbdVO=new ComplBoardVO();
				complbdVO.setComplNo(rs.getInt("compl_no"));
				System.out.println(rs.getString("compl_title"));
				complbdVO.setComplId(rs.getString("compl_id"));
				complbdVO.setComplTitle(rs.getString("compl_title"));
				complbdVO.setComplLike(rs.getInt("compl_like"));
				complbdVO.setComplViewCnt(rs.getInt("compl_viewcnt"));
				complbdVO.setComplDate(rs.getString("compl_date"));
				complbdVO.setComplIsSecret(rs.getInt("compl_is_secret"));
				combdVOList.add(complbdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return combdVOList;
	}//메서드 끝
	
	//내용으로 관리자 검색 메소드
	public List<ComplBoardVO> getPageUsingContentsByAdmin(int page,String contents){
		List<ComplBoardVO> combdVOList=new ArrayList<ComplBoardVO>();  
	
		query="select n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  from(\r\n" + 
				"				select rownum n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
				"				from(select rownum num,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
				"				     from t_complboard \r\n" + 
				"				     where compl_content like '%"+contents+"%' \r\n" + 
				"				     order by compl_date desc\r\n" + 
				"				    ))\r\n" + 
				"				where n/15>? and n/15<=?";
		

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, page-1);
			pstmt.setInt(2, page);
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				ComplBoardVO complbdVO=new ComplBoardVO();
				complbdVO.setComplNo(rs.getInt("compl_no"));
				System.out.println(rs.getString("compl_title"));
				complbdVO.setComplId(rs.getString("compl_id"));
				complbdVO.setComplTitle(rs.getString("compl_title"));
				complbdVO.setComplLike(rs.getInt("compl_like"));
				complbdVO.setComplViewCnt(rs.getInt("compl_viewcnt"));
				complbdVO.setComplDate(rs.getString("compl_date"));
				complbdVO.setComplIsSecret(rs.getInt("compl_is_secret"));
				combdVOList.add(complbdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return combdVOList;
	}//메서드 끝
	
	//아이디로검색 사용자부분
	public List<ComplBoardVO> getPageUsingMemId(int page,String userId){
		List<ComplBoardVO> combdVOList=new ArrayList<ComplBoardVO>();  
		if(userId.equals(UserMain.mvo.getMemId())) //검색 아이디가 내 아이디 일 때 
			query="select n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  \r\n" + 
					"from(\r\n" + 
					"      select rownum n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
					"              from(select rownum num,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
					"                   from t_complboard \r\n" + 
					"                   where compl_id like ?\r\n" + 
					"                   order by compl_date desc))\r\n" + 
					"where n/15>? and n/15<=?";
		else //검색 아이디가 내 아이디가 아닐 때
			query="select n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  \r\n" + 
					"from(\r\n" + 
					"      select rownum n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
					"              from(select rownum num,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
					"                   from t_complboard \r\n" + 
					"                   where compl_id like ? and compl_is_secret =0\r\n" + 
					"                   order by compl_date desc))\r\n" + 
					"where n/15>? and n/15<=?";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, page-1);
			pstmt.setInt(3, page);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				ComplBoardVO complbdVO=new ComplBoardVO();
				complbdVO.setComplNo(rs.getInt("compl_no"));
				complbdVO.setComplId(rs.getString("compl_id"));
				complbdVO.setComplTitle(rs.getString("compl_title"));
				complbdVO.setComplLike(rs.getInt("compl_like"));
				complbdVO.setComplViewCnt(rs.getInt("compl_viewcnt"));
				complbdVO.setComplDate(rs.getString("compl_date"));
				complbdVO.setComplIsSecret(rs.getInt("compl_is_secret"));
				combdVOList.add(complbdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return combdVOList;
	}//메서드 끝
	
	//아이디로 검색 관리자부분
	public List<ComplBoardVO> getPageUsingMemIdByAdmin(int page,String userId){
		List<ComplBoardVO> combdVOList=new ArrayList<ComplBoardVO>();  
		
			query="select n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret  \r\n" + 
					"from(\r\n" + 
					"      select rownum n,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,compl_date,compl_is_secret \r\n" + 
					"              from(select rownum num,compl_no,compl_title,compl_id,compl_like,compl_viewcnt,to_char(compl_date,'YYYY-MM-DD HH24:MI:SS') as compl_date,compl_is_secret\r\n" + 
					"                   from t_complboard \r\n" + 
					"                   where compl_id like ?\r\n" + 
					"                   order by compl_date desc))\r\n" + 
					"where n/15>? and n/15<=?";
	
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, page-1);
			pstmt.setInt(3, page);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				ComplBoardVO complbdVO=new ComplBoardVO();
				complbdVO.setComplNo(rs.getInt("compl_no"));
				complbdVO.setComplId(rs.getString("compl_id"));
				complbdVO.setComplTitle(rs.getString("compl_title"));
				complbdVO.setComplLike(rs.getInt("compl_like"));
				complbdVO.setComplViewCnt(rs.getInt("compl_viewcnt"));
				complbdVO.setComplDate(rs.getString("compl_date"));
				complbdVO.setComplIsSecret(rs.getInt("compl_is_secret"));
				combdVOList.add(complbdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return combdVOList;
	}//메서드 끝
	//사용자 글쓰기
	public boolean writePost(String memId,String title,String content,int isSecret) {
		
			query = "INSERT INTO t_complboard VALUES(complbd_seq.NEXTVAL,0,?,?,?,SYSDATE,\r\n" + 
					"0,0,?)";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				System.out.println(memId);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, memId);
				pstmt.setInt(4, isSecret);
		
				if (pstmt.executeUpdate() == 1) {
					result = true;
					} else {
					result = false;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(pstmt);}
			
			return result;
		
	}//메서드엔드

	//관리자글쓰기
	public boolean writePostByAdmin(String title,String content) {
		
		query = "INSERT INTO t_complboard VALUES(complbd_seq.NEXTVAL,1,?,?,?,SYSDATE,\r\n" + 
				"0,0,0)";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, "admin");
			
	
			if (pstmt.executeUpdate() == 1) {
				result = true;
				} else {
				result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return result;
	
}//메서드엔드
	
	//해당 글 조회하기
	public ComplBoardVO getOneComplPostInfo(int complNo) {	
			ComplBoardVO complbdVO=new ComplBoardVO();
		
				query = "select compl_title,compl_content,compl_like from t_complboard where compl_no="+complNo;
				
				try {
					stmt = DBconn.getConnection().createStatement();
					rs = stmt.executeQuery(query);
					if (rs.next()) {	
						complbdVO.setComplTitle(rs.getString("compl_title"));
						complbdVO.setComplContent(rs.getString("compl_content"));
						complbdVO.setComplLike(rs.getInt("compl_like"));
						}else {
							System.out.println("해당 건의게시판 조회 글이 없습니다.");
						}
							
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {DBconn.close(stmt);}
				
				return complbdVO;

	}
 
	//건의게시판 글 수정
	public boolean modifyComplPost(int no,String title,String content) {
		
		query = "update t_complboard set compl_title=?,compl_content=? where compl_no=?";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, no);
	
			if (pstmt.executeUpdate() == 1) {
				result = true;
				} else {
				result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return result;
	
	}//메서드엔드
	
	//건의게시판 글 삭제
	public boolean deleteComplPost(int no) {
		
		query = "delete t_complboard where compl_no=?";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, no);
	
			if (pstmt.executeUpdate() == 1) {
				result = true;
				} else {
				result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return result;
	
	}//메서드엔드
	
	//조회수 올리기
	public boolean updateViewCnt(int complNo) {
		
		query = "update t_complboard set compl_viewcnt=compl_viewcnt+1 where compl_no=?";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1,complNo);
	
			if (pstmt.executeUpdate() == 1) {
				result = true;
				} else {
				result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return result;
	
	}//메서드엔드
	
	//게시물 좋아요
	public boolean updateLikeCnt(int complNo) {
		query = "update t_complboard set compl_like=compl_like+1 where compl_no=?";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1,complNo);
	
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
	
	//좋아요 누른 게시물 추가하기
	public boolean insertLikeBoardNo(String id,int complNo) {
		query = "INSERT INTO t_likeboard VALUES(?,1,?,0)";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, complNo);
		
	
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
	
	//좋아요누른게시물인지 확인
	public int isThereComplBoard(String id,int complNo) {
		int re=-1;
		query = "select count(*) as isthere from t_likeboard where mem_id=? and \r\n" + 
				"board_type=1 and board_no=? and comt_no=0";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, complNo);
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
	
}//클래스엔드
