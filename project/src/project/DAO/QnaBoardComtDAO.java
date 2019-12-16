package project.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import project.DBconn.DBconn;
import project.VO.ComplComtVO;
import project.VO.QnaBoardComtVO;
import project.VO.QnaBoardVO;

public class QnaBoardComtDAO {
	private String query;
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement pstmt;
	private boolean result;	
	
	// 댓글작성
	public boolean writeQnaBoardComt(String id, String content) {
		query = "INSERT INTO t_qnaBoardComt (qna_no, qna_comt_no, mem_id, qna_comt_content, qna_comt_date, qna_comt_like) VALUES(?, ?, ?, ?, ?, SYSDATE, 0)";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);	
			
			pstmt.setString(1,id);
			pstmt.setString(2, content);
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
		
	
	// 댓글삭제
	public boolean deleteQnaBoardComt(int qnaNo, int qnaComtNo) {
		query = "DELETE T_QNABOARD_COMT WHERE qna_no = '"+ qnaNo + "' && qna_comt_no = '"+ qnaComtNo + "'";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			if (pstmt.executeUpdate(query) == 1) {
				result = true;
				} else {
					result = false;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { DBconn.close(stmt);}
		
		return result;

	}

	// 해당 게시글에 달린 댓글 가져오기
	public List<QnaBoardComtVO> getAllBoardComtInfo(int qnaNo) {
		ArrayList list = new ArrayList<QnaBoardComtVO>();
		query = "SELECT qna_no, qna_title, qna_id, qna_like, qna_viewCnt, qna_date FROM t_qnaboard_comt WHERE qna_no= '" + qnaNo +"'";
	try {
		pstmt = DBconn.getConnection().prepareStatement(query);
//		pstmt.setInt(1, page-1);
//		pstmt.setInt(2, page);
		rs = pstmt.executeQuery();		
		while(rs.next() ) {
			QnaBoardVO vo = new QnaBoardVO();
			vo.setQnaNo(rs.getInt("qna_no"));
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


	// 좋아요 버튼눌렀을때
	public boolean clickQnaBoardComtLike(int qnaNo, int qnaComtNo) {
		return result;

	}
	
	//////////해당게시물의 댓글 수 가져오기/////////////////////////////////////////////////////////////////
	public int getSizeOfComtSize(int qnaNo) {
	int lastNo = -1;
	query = "select count(qna_comt_no) as comt_size from t_qnaboard_comt where qna_no=?";
	try {
		pstmt = DBconn.getConnection().prepareStatement(query);
		pstmt.setInt(1, qnaNo);
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
	
	//해당 게시글에 댓글 달기
		public boolean writeQnaComt(int qnaNo,int qnaComtNo,String id,String content) {

			query = "insert into t_qnaboard_comt values(?,?,?,?,SYSDATE,0)";

			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1, qnaNo);
				pstmt.setInt(2, qnaComtNo);
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
		
		//해당 게시글의 해당페이지 댓글 목록 가져오기
		public List<QnaBoardComtVO> getQnaComtListPage(int qnaNo,int page){
				List<QnaBoardComtVO> qnaComtVOList=new ArrayList<QnaBoardComtVO>();  
				query ="select n,qna_no,qna_comt_no,mem_id,qna_comt_content,qna_comt_date,qna_comt_like  from( \r\n" + 
						"									select rownum n,qna_no,qna_comt_no,mem_id,qna_comt_content,qna_comt_date,qna_comt_like\r\n" + 
						"									from(select rownum num,qna_no,qna_comt_no,mem_id,qna_comt_content,qna_comt_date,qna_comt_like\r\n" + 
						"									     from t_qnaboard_comt\r\n" + 
						"                                        where qna_no=?\r\n" + 
						"									     order by qna_comt_date desc\r\n" + 
						"									    ))\r\n" + 
						"									where n/10>? and n/10<=?";
			try {
				pstmt = DBconn.getConnection().prepareStatement(query);
				pstmt.setInt(1, qnaNo);
				pstmt.setInt(2, page-1);
				pstmt.setInt(3, page);
				rs=pstmt.executeQuery();
				while(rs.next()) { 
					QnaBoardComtVO qcVO=new QnaBoardComtVO(); 
					qcVO.setQnaNo(rs.getInt("qna_no"));
					qcVO.setQnaComtNo(rs.getInt("qna_comt_no"));
					qcVO.setMemId(rs.getString("mem_id"));
					qcVO.setQnaComtContent(rs.getString("qna_comt_content"));
					qcVO.setQnaComtDate(rs.getString("qna_comt_date"));
					qcVO.setQnaComtLike(rs.getInt("qna_comt_like"));
					qnaComtVOList.add(qcVO);				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { DBconn.close(pstmt);}
			
			return qnaComtVOList;
		}//메소드 엔드
		
		//좋아요누른 댓글인지 확인
				public int isThereQnaComt(String id,int qnaNo,int comtNo) {
					int re=-1;
					query = "select count(*) as isthere from t_likeboard where mem_id=? and \r\n" + 
							"board_type=0 and board_no=? and comt_no=?";
					try {
						pstmt = DBconn.getConnection().prepareStatement(query);
						pstmt.setString(1, id);
						pstmt.setInt(2, qnaNo);
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
				
				//댓글 좋아요
				public boolean updateLikeCnt(int qnaNo,int comtNo) {
					query = "update t_qnaboard_comt set qna_comt_like=qna_comt_like+1 where qna_no=? and qna_comt_no=?";
					
					try {
						pstmt = DBconn.getConnection().prepareStatement(query);
						pstmt.setInt(1,qnaNo);
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
				public boolean insertLikeBoardNo(String id,int qnaNo,int comtNo) {
					query = "INSERT INTO t_likeboard VALUES(?,0,?,?)";
					
					try {
						pstmt = DBconn.getConnection().prepareStatement(query);
						pstmt.setString(1, id);
						pstmt.setInt(2, qnaNo);
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
				
				//해당 댓글의 댓글의 좋아요 수 가져오기
				public int getComtCnt(int qnaNo,int comtNo) {
					int re=-1;
					query = "select qna_comt_like from t_qnaboard_comt where qna_no=? and qna_comt_no=?";
					try {
						pstmt = DBconn.getConnection().prepareStatement(query);
						pstmt.setInt(1, qnaNo);
						pstmt.setInt(2, comtNo);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							re=rs.getInt("qna_comt_like");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						DBconn.close(pstmt);
					}
					return re;
				}
				
				//댓글삭제
				public boolean deleteQnaComt(int qnaNo,int qnaComtNo) {
					query = "delete t_qnaboard_comt where qna_no=? and qna_comt_no=?";

					try {
						pstmt = DBconn.getConnection().prepareStatement(query);
						pstmt.setInt(1, qnaNo);
						pstmt.setInt(2, qnaComtNo);
						

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

}
