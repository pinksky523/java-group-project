package project.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import project.DBconn.DBconn;
import project.VO.ComplBoardVO;
import project.VO.QnaBoardVO;

public class QnaBoardDAO {
	private String query;
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement pstmt;
	private boolean result;	
	private String index;

	// 아이디로 검색 결과 질답게시물 목록에  띄우기 
	public List<QnaBoardVO> searchById(int pageNo, String id) {
		List<QnaBoardVO> list = new ArrayList<QnaBoardVO>();
		query ="select n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date from(\r\n" + 
				"select rownum n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date \r\n" + 
				"from(select rownum num,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date\r\n" + 
				"     from t_qnaboard \r\n where qna_id = '" + id + "'"+ 
				"     order by qna_date desc\r\n" + 
				"    ))\r\n" + 
				"where n/15>? and n/15<=? ";
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query); 	
			pstmt.setInt(1, pageNo-1);
			pstmt.setInt(2, pageNo);
			rs = pstmt.executeQuery();
			while(rs.next() ) {
				QnaBoardVO vo = new QnaBoardVO();
				vo.setQnaNo(rs.getInt("qna_no"));
				vo.setQnaTitle(rs.getString("qna_Title"));
				vo.setMemId(rs.getString("qna_id"));
				vo.setQnaLike(rs.getInt("qna_like"));
				vo.setQnaDate(rs.getString("qna_date"));	
				list.add(vo);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {DBconn.close(stmt, rs);}
			return list;		
	}

	// 내용으로 검색 결과 질답게시물 목록에 띄우기 
	public List<QnaBoardVO> searchByContent(int pageNo, String contents) {
		List<QnaBoardVO> list = new ArrayList<QnaBoardVO>();	
		query ="select n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date from(\r\n" + 
				"select rownum n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date \r\n" + 
				"from(select rownum num,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date\r\n" + 
				"     from t_qnaboard \r\n where qna_content like '%" + contents + "%'"+ 
				"     order by qna_date desc\r\n" + 
				"    ))\r\n" + 
				"where n/15>? and n/15<=?";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, pageNo-1);
			pstmt.setInt(2, pageNo);
			rs = pstmt.executeQuery();
			while(rs.next() ) {
				QnaBoardVO vo = new QnaBoardVO();
				vo.setQnaNo(rs.getInt("qna_no"));
				vo.setQnaTitle(rs.getString("qna_Title"));
				vo.setMemId(rs.getString("qna_id"));
				vo.setQnaLike(rs.getInt("qna_like"));
				vo.setQnaDate(rs.getString("qna_date"));	
				list.add(vo);
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {DBconn.close(stmt, rs);}
			return list;		
	}
	
		// 질답게시판 목록에 출력할 DB 데이터 출력
		public List<QnaBoardVO> getPageOfQnaPostsInfo(int page) {
			List<QnaBoardVO> list = new ArrayList<QnaBoardVO>();		
			query ="select n,qna_no,qna_type,qna_title,qna_id,qna_like,qna_viewcnt,qna_date from(\r\n" + 
					"select rownum n,qna_no,qna_type,qna_title,qna_id,qna_like,qna_viewcnt,qna_date \r\n" + 
					"from(select rownum num,qna_no,qna_type,qna_title,qna_id,qna_like,qna_viewcnt,qna_date\r\n" + 
					"     from t_qnaboard \r\n" + 
					"     order by qna_type desc,qna_date desc,qna_no desc\r\n" + 
					"    ))\r\n" + 
					"where n/15>? and n/15<=?";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1, page-1);
				pstmt.setInt(2, page);
				rs = pstmt.executeQuery();		
				while(rs.next() ) {
					QnaBoardVO vo = new QnaBoardVO();
					vo.setQnaNo(rs.getInt("qna_no"));
					vo.setQnaType(rs.getInt("qna_type"));
					vo.setQnaTitle(rs.getString("qna_title"));
					vo.setMemId(rs.getString("qna_id"));
					vo.setQnaLike(rs.getInt("qna_like"));
					vo.setQnaViewcnt(rs.getInt("qna_viewcnt"));
					vo.setQnaDate(rs.getString("qna_date"));	
					list.add(vo);
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {DBconn.close(stmt, rs);}
				return list;	
		}
	
	// 질답게시판에 게시글 쓰기
	public boolean writeQnaPost(String id,String title,String content) {
		query = "INSERT INTO t_qnaBoard VALUES(qnabd_seq.NEXTVAL,0,?,?,SYSDATE,0,0,?)";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);					
			pstmt.setString(1,title);
			pstmt.setString(2,content);
			pstmt.setString(3,id);
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
		
	// 질답 게시판에 쓴 글 중 한 개 목록에서 클릭해서 띄우기 	
		public QnaBoardVO getOneQnaPostInfo(int qnaNo) {
			QnaBoardVO qvo = new QnaBoardVO();
			query = "select qna_title,qna_content,qna_like from t_qnaboard where qna_no="+qnaNo;

			try {
				stmt = DBconn.getConnection().createStatement();			
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					qvo.setQnaContent(rs.getString("qna_content"));
					qvo.setQnaTitle(rs.getString("qna_title"));
					qvo.setQnaLike(rs.getInt("qna_like"));
					} else {
						System.out.println("해당 질답게시판 조회 글이 없습니다.");
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(stmt);}
			return qvo;
		}
		
		//철현 추가
		//특정 아이디의  qna게시판 작성 글 목록(3페이지)
		public List<QnaBoardVO> getPageOfQnaPostsInfo(String userId, int page) {
			List<QnaBoardVO> list = new ArrayList<QnaBoardVO>();
			String query = "SELECT * FROM(SELECT ROWNUM num, a.* FROM (SELECT * FROM t_qnaboard) a WHERE qna_id = '"+userId+"') WHERE num/3 >"+(page-1)+" AND num/3 <="+page;
			try {
				stmt = DBconn.getConnection().createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					QnaBoardVO qbvo = new QnaBoardVO();
					qbvo.setQnaNo(rs.getInt("qna_no"));
					qbvo.setQnaType(rs.getInt("qna_type"));
					qbvo.setQnaTitle(rs.getString("qna_title"));
					qbvo.setQnaContent(rs.getString("qna_content"));
					qbvo.setMemId(rs.getString("qna_id"));
					qbvo.setQnaDate(rs.getString("qna_date"));
					qbvo.setQnaViewcnt(rs.getInt("qna_viewcnt"));
					qbvo.setQnaLike(rs.getInt("qna_like"));
					list.add(qbvo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {DBconn.close(stmt, rs); }
			return list;
		}
		
		// 질답게시판에서 게시글 한 개 클릭 후 해당 글 수정
		public boolean updateQnaPost(int no, String title, String content) {
			  query = "update t_qnaboard set qna_Title=?, qna_content=? where qna_no=?";
			try {
				pstmt = DBconn.getConnection().prepareStatement(query); 
				
				pstmt.setString(1,title);
				pstmt.setString(2,content);
				pstmt.setInt(3,no);
				
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
		
		// 질답게시판에서 게시글 한 개 클릭 후 해당 글 삭제
	public boolean deleteQnaPost(int no) {
		query = "DELETE t_qnaboard WHERE qna_no = ?";
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
	}
	

	//관리자글쓰기
	public boolean writePostByAdmin(String title,String content) {
		
		query = "INSERT INTO t_qnaboard VALUES(qnabd_seq.NEXTVAL,1,?,?,SYSDATE,0,0,?)";
		
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
	

	//내용으로 관리자 검색 메소드
	public List<QnaBoardVO> getPageUsingContentsByAdmin(int page,String contents){
		List<QnaBoardVO> qnabdVOList=new ArrayList<QnaBoardVO>();  
	
		query="select n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date  from(\r\n" + 
				"				select rownum n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date \r\n" + 
				"				from(select rownum num,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,to_char(qna_date,'YYYY-MM-DD HH24:MI:SS') as qna_date\r\n" + 
				"				     from t_qnaboard \r\n" + 
				"				     where qna_content like '%"+contents+"%' \r\n" + 
				"				     order by qna_date desc\r\n" + 
				"				    ))\r\n" + 
				"				where n/15>? and n/15<=?";
		

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, page-1);
			pstmt.setInt(2, page);
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				QnaBoardVO qnabdVO=new QnaBoardVO();
				qnabdVO.setQnaNo(rs.getInt("qna_no"));
				System.out.println(rs.getString("qna_title"));
				qnabdVO.setMemId(rs.getString("qna_id"));
				qnabdVO.setQnaTitle(rs.getString("qna_title"));
				qnabdVO.setQnaLike(rs.getInt("qna_like"));
				qnabdVO.setQnaViewcnt(rs.getInt("qna_viewcnt"));
				qnabdVO.setQnaDate(rs.getString("qna_date"));
				qnabdVOList.add(qnabdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return qnabdVOList;
	}//메서드 끝
	

	//아이디로 검색 관리자부분
	public List<QnaBoardVO> getPageUsingMemIdByAdmin(int page,String userId){
		List<QnaBoardVO> qnabdVOList=new ArrayList<QnaBoardVO>();  
		
			query="select n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date  \r\n" + 
					"from(\r\n" + 
					"      select rownum n,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,qna_date \r\n" + 
					"              from(select rownum num,qna_no,qna_title,qna_id,qna_like,qna_viewcnt,to_char(qna_date,'YYYY-MM-DD HH24:MI:SS') as qna_date\r\n" + 
					"                   from t_qnaboard \r\n" + 
					"                   where qna_id like ?\r\n" + 
					"                   order by qna_date desc))\r\n" + 
					"where n/15>? and n/15<=?";
	
		
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, page-1);
			pstmt.setInt(3, page);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) { 
				QnaBoardVO qnabdVO=new QnaBoardVO();
				qnabdVO.setQnaNo(rs.getInt("qna_no"));
				System.out.println(rs.getString("qna_title"));
				qnabdVO.setMemId(rs.getString("qna_id"));
				qnabdVO.setQnaTitle(rs.getString("qna_title"));
				qnabdVO.setQnaLike(rs.getInt("qna_like"));
				qnabdVO.setQnaViewcnt(rs.getInt("qna_viewcnt"));
				qnabdVO.setQnaDate(rs.getString("qna_date"));
				qnabdVOList.add(qnabdVO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(pstmt);}
		
		return qnabdVOList;
	}//메서드 끝
	
	//조회수 올리기
		public boolean updateViewCnt(int qnaNo) {
			
			query = "update t_qnaboard set qna_viewcnt=qna_viewcnt+1 where qna_no=?";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1,qnaNo);
		
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
		
		//좋아요누른게시물인지 확인
		public int isThereQnaBoard(String id,int qnaNo) {
			int re=-1;
			query = "select count(*) as isthere from t_likeboard where mem_id=? and \r\n" + 
					"board_type=0 and board_no=? and comt_no=0";
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setInt(2, qnaNo);
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
		
		//게시물 좋아요
		public boolean updateLikeCnt(int qnaNo) {
			query = "update t_qnaboard set qna_like=qna_like+1 where qna_no=?";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1,qnaNo);
		
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
		public boolean insertLikeBoardNo(String id,int qnaNo) {
			query = "INSERT INTO t_likeboard VALUES(?,0,?,0)";
			
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setInt(2, qnaNo);
			
		
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

}