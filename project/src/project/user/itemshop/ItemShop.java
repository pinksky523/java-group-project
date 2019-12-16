package project.user.itemshop;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import project.DAO.ItemDAO;
import project.DAO.MemberDAO;
import project.DAO.UserItemDAO;
import project.VO.ItemVO;
import project.VO.MemberVO;
import project.VO.UserItemVO;
import project.user.main.UserMain;

import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class ItemShop implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, pagePanel;
	private JTable itemListTable;
	private JButton itemListBtn, buyBtn;
	private JLabel prev, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, next;

	private UserItemDAO uidao;
	private UserItemVO uivo;
	private ItemDAO idao;
	private MemberDAO mdao;
	private MemberVO mvo;
	private List<ItemVO> itemList;
	private DefaultTableModel model;
	private int pageCnt = 0;

	// jtable 열 제목, 내용 설정
	Object columnName[] = { "번호", "아이템 종류", "아이템 이름", "가격", "구매" };

	private JPanel panel;
	private JLabel label;
	private JLabel lblNewLabel;
	String memId = UserMain.mvo.getMemId(); // 일단 로그인한 아이디가 이것이라고 가정하였음



	
	public JPanel getJPanel() {
		return mainPanel;
	}
	
	
	
	// 생성자
	public ItemShop() {
		idao = new ItemDAO();
		//setSize(1500, 900);

		// 패널 생성, 설정
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();

		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		mainPanel.setBounds(0, 0, 1394, 794);

		//setContentPane(mainPanel);

		mainPanel.setLayout(null);

		// 테이블초기화 모델 생성,테이블에 추가, 설정
		model = new DefaultTableModel(columnName, 10);
		itemListTable = new JTable(model);

		// jscrollpane 설정
		JScrollPane jscrollPane = new JScrollPane(itemListTable);
		jscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		jscrollPane.setBounds(369, 279, 746, 322);
		mainPanel.add(jscrollPane);

		// 상점아이콘 label
		JLabel imageLabel = new JLabel(new ImageIcon("itemshop.png"));
		imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		imageLabel.setText("상점아이콘");
		imageLabel.setForeground(Color.BLACK);
		imageLabel.setBounds(550, 135, 68, 68);
		mainPanel.add(imageLabel);

		// 제목 label 설정
		JLabel itemShopLabel = new JLabel("아이템 상점");
		itemShopLabel.setForeground(Color.BLACK);
		itemShopLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 50));
		itemShopLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemShopLabel.setBounds(637, 143, 275, 61);
		mainPanel.add(itemShopLabel);

		// 보유 아이템 목록 button
		itemListBtn = new JButton("보유 아이템 목록");
		itemListBtn.setBounds(984, 246, 131, 23);
		mainPanel.add(itemListBtn);

		// 테이블 컬럼 너비 설정
		itemListTable.getColumn("번호").setPreferredWidth(50);
		itemListTable.getColumn("아이템 종류").setPreferredWidth(150);
		itemListTable.getColumn("아이템 이름").setPreferredWidth(80);
		itemListTable.getColumn("가격").setPreferredWidth(100);
		itemListTable.getColumn("구매").setPreferredWidth(80);
//    itemListTable.getTableHeader().setReorderingAllowed(false); // 컬럼 순서 이동 불가
//    itemListTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		// 테이블 셀높이설정
		itemListTable.setRowHeight(30);

		// 1페이지화면으로 화면초기화
		listPaging(1);

		// 페이지 번호label
		pagePanel = new JPanel();
		pagePanel.setBounds(369, 615, 746, 49);
		mainPanel.add(pagePanel);
		pagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// itemListBtn 버튼 리스너
		itemListBtn.addActionListener(this);

		// 페이지 번호 리스너
		prev = new JLabel("<");
		prev.addMouseListener(this);
		pagePanel.add(prev);

		p1 = new JLabel("1");
		p1.addMouseListener(this);
		pagePanel.add(p1);

		p2 = new JLabel("2");
		p2.addMouseListener(this);
		pagePanel.add(p2);

		p3 = new JLabel("3");
		p3.addMouseListener(this);
		pagePanel.add(p3);

		p4 = new JLabel("4");
		p4.addMouseListener(this);
		pagePanel.add(p4);

		p5 = new JLabel("5");
		p5.addMouseListener(this);
		pagePanel.add(p5);

		p6 = new JLabel("6");
		p6.addMouseListener(this);
		pagePanel.add(p6);

		p7 = new JLabel("7");
		p7.addMouseListener(this);
		pagePanel.add(p7);

		p8 = new JLabel("8");
		p8.addMouseListener(this);
		pagePanel.add(p8);

		p9 = new JLabel("9");
		p9.addMouseListener(this);
		pagePanel.add(p9);

		p10 = new JLabel("10");
		p10.addMouseListener(this);
		pagePanel.add(p10);

		next = new JLabel(">");
		next.addMouseListener(this);
		pagePanel.add(next);

		setFont();

		p1.setFont(new Font("맑은 고딕", Font.BOLD, 25));

		panel = new JPanel();
		panel.setBounds(369, 234, 215, 35);
		mainPanel.add(panel);
		panel.setLayout(null);

		label = new JLabel("좋아요 수");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setBounds(12, 5, 92, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);

		lblNewLabel = new JLabel(likeCnt(memId));
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(112, 0, 103, 27);

		panel.add(lblNewLabel);

		itemListTable.getColumnModel().getColumn(4).setCellRenderer(new TableCell());
		itemListTable.getColumnModel().getColumn(4).setCellEditor(new TableCell());

		//setVisible(true);
	}// end 생성자

	// 아이템 구매 버튼
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public TableCell() {
			buyBtn = new JButton("구매");
			buyBtn.addActionListener(e -> {
				buyItem();
			});
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return buyBtn;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return buyBtn;
		}
	} // end class TableCell extends AbstractCellEditor implements TableCellEditor,
		// TableCellRenderer

	// 페이지 라벨들을 다음 또는 이전 페이지를 누를때 pageCnt를 증감시켜서 이에 맞춰 페이지 라벨을 초기화해줌 (메서드)
	public void pageNoSet() {

		p1.setText("" + (1 + 10 * pageCnt));
		p2.setText("" + (2 + 10 * pageCnt));
		p3.setText("" + (3 + 10 * pageCnt));
		p4.setText("" + (4 + 10 * pageCnt));
		p5.setText("" + (5 + 10 * pageCnt));
		p6.setText("" + (6 + 10 * pageCnt));
		p7.setText("" + (7 + 10 * pageCnt));
		p8.setText("" + (8 + 10 * pageCnt));
		p9.setText("" + (9 + 10 * pageCnt));
		p10.setText("" + (10 + 10 * pageCnt));
	}

	// 페이지 번호label 폰트 초기화 메서드
	public void setFont() {
		prev.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p1.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p2.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p3.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p4.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p5.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p6.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p7.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p8.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p9.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		p10.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		next.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
	}

	// t_item DB를 jtable에 뿌리기
	void listPaging(int pageNo) {
		for (int i = 0; i < 10; i++) { // 우선 테이블 초기화 하고
			model.setValueAt("", i, 0);
			model.setValueAt("", i, 1);
			model.setValueAt("", i, 2);
			model.setValueAt("", i, 3);
			model.setValueAt("", i, 4);
		}

		itemList = idao.getAll(pageNo + (10 * pageCnt));
		for (int i = 0; i < itemList.size(); i++) {
			model.setValueAt(itemList.get(i).getItemNo(), i, 0);
			model.setValueAt(itemList.get(i).getItemType(), i, 1);
			model.setValueAt(itemList.get(i).getItemName(), i, 2);
			model.setValueAt(itemList.get(i).getItemPrice(), i, 3);
		}

	}

	// 내 아이템 리스트 가져오는 메서드
	public void showItemList() {
		mvo = new MemberVO();
		mvo.setMemId(memId);
		System.out.println("--------------- [ " + mvo.getMemId() + " 님의 보유 아이템 목록 ] ---------------");
		System.out.println("   번호\t아이템 종류\t\t아이템 이름\t\t가격");
		System.out.println("-------------------------------------------------------");
		uivo = new UserItemVO();
		uidao = new UserItemDAO();
		for (int i = 0; i < uidao.getUserItem(memId).size(); i++) {
			System.out.println(i + 1 + "\t" + uidao.getUserItem(memId).get(i).getItemType() + "\t"
					+ uidao.getUserItem(memId).get(i).getItemName() + "\t\t"
					+ uidao.getUserItem(memId).get(i).getItemPrice() + "\t");
			uivo.setItemNo(uidao.getUserItem(memId).get(i).getItemNo());
			uivo.setItemType(uidao.getUserItem(memId).get(i).getItemType());
			uivo.setItemName(uidao.getUserItem(memId).get(i).getItemName());
			uivo.setItemPrice(uidao.getUserItem(memId).get(i).getItemPrice());

		}
		;
	}

	// 아이템 구매 메서드
	public void buyItem() {
		uivo = new UserItemVO();
		uidao = new UserItemDAO();
		mvo = new MemberVO();
		int row = itemListTable.getSelectedRow();
		if ((int) itemListTable.getValueAt(row, 3) > Integer.parseInt(likeCnt(memId))) {
			System.out.println("보유 포인트가 부족하여 구매 할 수 없습니다.");
		} else {
			if (buyBtn != null) {
				uivo.setMemId(memId);
				uivo.setItemNo((int) itemListTable.getValueAt(row, 0));
				uivo.setItemType((String) itemListTable.getValueAt(row, 1));
				uivo.setItemName((String) itemListTable.getValueAt(row, 2));
				uivo.setItemPrice((int) itemListTable.getValueAt(row, 3));

			}
			if (uidao.buyItem(uivo)) {
				System.out.println("아이템 구매가 완료되었습니다.");
				lblNewLabel.setText(likeCnt(memId));
			} else {
				System.out.println("아이템 구매를 실패하였습니다.");
			}
		}
	}// end 아이템구매 메서드

	
	// 좋아요수 가져오기
	public String likeCnt(String memId) {
		mdao = new MemberDAO();
		return mdao.memLike(memId) + "";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn == itemListBtn) {
			showItemList();
		} else if (btn == buyBtn) {
			buyItem();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		JLabel lb = (JLabel) e.getSource();

		if (lb == p1) {// 1페이지 눌렀을 때
			setFont();
			p1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(1);
		} else if (lb == p2) {// 2페이지 눌렀을 때
			setFont();
			p2.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(2);
		} else if (lb == p3) {// 3페이지 눌렀을 때
			setFont();
			p3.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(3);
		} else if (lb == p4) {// 4페이지 눌렀을 때
			setFont();
			p4.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(4);
		} else if (lb == p5) {// 5페이지 눌렀을 때
			setFont();
			p5.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(5);
		} else if (lb == p6) {// 6페이지 눌렀을 때
			setFont();
			p6.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(6);
		} else if (lb == p7) {// 7페이지 눌렀을 때
			setFont();
			p7.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(7);
		} else if (lb == p8) {// 8페이지 눌렀을 때
			setFont();
			p8.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(8);
		} else if (lb == p9) {// 9페이지 눌렀을 때
			setFont();
			p9.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(9);
		} else if (lb == p10) {// 10페이지 눌렀을 때
			setFont();
			p10.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			listPaging(10);
		} else if (lb == next) {// 다음페이지 눌렀을 때
			pageCnt++;
			pageNoSet(); // 페이지 번호 변경
			listPaging(1);
		} else if (lb == prev) {// 이전페이지 눌렀을 때
			if (pageCnt > 0) { // 1~10페이지에서는 이전페이지 없음
				pageCnt--;
				pageNoSet(); // 페이지 번호 변경
				listPaging(1);
			}
		}
	}// end mouseReleased 메서드

}// end class