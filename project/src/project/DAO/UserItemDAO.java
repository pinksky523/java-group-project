package project.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import project.DBconn.DBconn;
import project.VO.MemberVO;
import project.VO.UserItemVO;

public class UserItemDAO {

	private ResultSet rs;
	private String query;
	private PreparedStatement pstmt;
	private Statement stmt;
	private boolean result;
	private UserItemVO uivo;

	// 보유 아이템 목록 불러오기
	public List<UserItemVO> getList() {
		List<UserItemVO> list = new ArrayList<UserItemVO>();
		try {
			query = "SELECT item_type, item_name, item_price FROM t_item";
			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				UserItemVO uivo = new UserItemVO();
				uivo.setItemType(rs.getString("item_type"));
				uivo.setItemName(rs.getString("item_name"));
				uivo.setItemPrice(rs.getInt("item_price"));
				list.add(uivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt, rs);
		}
		return list;
	}

	// 아이템 상점의 아이템 구매하기
	public boolean buyItem(UserItemVO uivo) {
		MemberDAO mdao = new MemberDAO();
		query = "INSERT INTO t_useritem VALUES(?, t_useritem_seq.NEXTVAL, ?, ?, ?)";
		try {
			pstmt = DBconn.getConnection().prepareStatement(query);
			pstmt.setString(1, uivo.getMemId());
			pstmt.setString(2, uivo.getItemType());
			pstmt.setString(3, uivo.getItemName());
			pstmt.setInt(4, uivo.getItemPrice());

			if (pstmt.executeUpdate() == 1) {
				result = true;
				mdao.memLikeUpdate(uivo.getMemId(), uivo.getItemPrice());
			} else {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 보유아이템목록 출력 메서드
	public List<UserItemVO> getUserItem(String loginId) {
		// query = "SELECT * FROM t_item WHERE item_no IN (SELECT item_no FROM
		// t_useritem WHERE mem_id = '" + loginId + "')";
		query = "SELECT * FROM t_useritem WHERE mem_id = '" + loginId + "'";
		List<UserItemVO> myItemlist = new ArrayList<UserItemVO>();

		try {

			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				UserItemVO uivo = new UserItemVO();
				uivo.setMemId("mem_id");
				uivo.setItemNo(rs.getInt("item_no"));
				uivo.setItemType(rs.getString("item_type"));
				uivo.setItemName(rs.getString("item_name"));
				uivo.setItemPrice(rs.getInt("item_price"));
				myItemlist.add(uivo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt, rs);
		}
		return myItemlist;
	}

	// 보유아이템목록 출력 메서드
	public String getItemType(int itemNo) {
		// query = "SELECT * FROM t_item WHERE item_no IN (SELECT item_no FROM
		// t_userItem WHERE item_no = '" + itemNo + "')";
		query = "SELECT * FROM t_useritem WHERE item_no = '" + itemNo + "'";
		uivo = new UserItemVO();
		List<UserItemVO> myItemlist = new ArrayList<UserItemVO>();

		try {

			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {

				uivo.setItemNo(rs.getInt("item_no"));
				uivo.setItemType(rs.getString("item_type"));
				uivo.setItemName(rs.getString("item_name"));
				uivo.setItemPrice(rs.getInt("item_price"));
				myItemlist.add(uivo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt, rs);
		}
		return uivo.getItemType();
	}

	// 보유아이템목록 출력 메서드
	public String getItemName(int itemNo) {
		// query = "SELECT * FROM t_item WHERE item_no IN (SELECT item_no FROM
		// t_userItem WHERE item_no = '" + itemNo + "')";
		query = "SELECT * FROM t_useritem WHERE item_no = '" + itemNo + "'";
		UserItemVO uivo = new UserItemVO();
		List<UserItemVO> myItemlist = new ArrayList<UserItemVO>();

		try {
			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {

				uivo.setItemNo(rs.getInt("item_no"));
				uivo.setItemType(rs.getString("item_type"));
				uivo.setItemName(rs.getString("item_name"));
				uivo.setItemPrice(rs.getInt("item_price"));
				myItemlist.add(uivo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt, rs);
		}
		return uivo.getItemName();
	}

	public static void main(String[] args) {

	}

	public boolean isExist(String loginId, int itemNo) {
		query = "SELECT item_no FROM t_item WHERE '" + itemNo + "'IN (SELECT item_no FROM t_userItem WHERE mem_id = '"
				+ loginId + "')";

		try {
			stmt = DBconn.getConnection().createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) { // 존재하면 true 리턴
				result = true;
			} else { // 없으면 false 리턴
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(stmt);
		}

		return result;
	}// end isExist

}
