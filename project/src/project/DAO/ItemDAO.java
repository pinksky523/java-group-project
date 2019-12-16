package project.DAO;

import java.sql.*;

import java.util.*;

import project.DBconn.DBconn;
import project.VO.ItemVO;

public class ItemDAO {
	private ResultSet rs;
	private String query;
	private PreparedStatement pstmt;
	private Statement stmt;
	private boolean result;

	// 아이템 상점 관리 - 아이템 삭제 메서드
	public boolean delete(int itemNo) {
		try {

			query = "DELETE FROM t_item where item_no="+itemNo;
			//query = "DELETE t_message WHERE message_no IN (SELECT message_no FROM (SELECT message_no,ROWNUM rm FROM t_message ORDER BY message_send_date DESC) WHERE rm = '"+checkedRecord+"')"
			stmt = DBconn.getConnection().createStatement();
			if (stmt.executeUpdate(query) == 1) {
				result = true; // 아이템 삭제 성공
			} else {
				result = false; // 아이템 삭제 실패
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt);
		}
		return result;
	}

	// 아이템 상점 관리 - 아이템 추가에 사용
	public boolean add(ItemVO ivo) {

		query = "INSERT INTO t_item VALUES(t_item_seq.NEXTVAL, ?, ?, ?)";

		try {

			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, ivo.getItemType());
			pstmt.setString(2, ivo.getItemName());
			pstmt.setInt(3, ivo.getItemPrice());

			if (pstmt.executeUpdate() == 1) {
				result = true; // 아이템 추가 성공
			} else {
				result = false; // 아이템 추가 실패
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}// end add메서드

	// 아이템 상점의 JTable에 뿌리기
	public List<ItemVO> getAll(int page) {
		query = "select n,item_no,item_type,item_name,item_price  \r\n" + 
				"from(select rownum n,item_no,item_type,item_name,item_price \r\n" + 
				"from t_item \r\n" + 
				"order by item_no desc)\r\n" + 
				"where n/10>? and n/10<=?";
		List<ItemVO> list = new ArrayList<ItemVO>();

		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setInt(1, page-1);
			pstmt.setInt(2, page);
			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ItemVO ivo = new ItemVO();
				ivo.setItemNo(rs.getInt("item_no"));
				ivo.setItemType(rs.getString("item_type"));
				ivo.setItemName(rs.getString("item_name"));
				ivo.setItemPrice(rs.getInt("item_price"));
				list.add(ivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt, rs);
		}

		return list;

	}

}