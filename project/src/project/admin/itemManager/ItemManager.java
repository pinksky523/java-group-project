package project.admin.itemManager;

import java.awt.*;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.DefaultTableModel;

import project.DAO.ItemDAO;

import project.DAO.MessageDAO;

import project.VO.ItemVO;

import javax.swing.table.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;

import java.util.Scanner;

public class ItemManager  implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, pagePanel;
	private JTable itemListTable;
	private JButton delBtn, addBtn;
//   private JButton itemListBtn;
	private JLabel prev, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, next;
	private JCheckBox chkBox;
	private ItemDAO idao;
	private ItemVO ivo;
	private List<ItemVO> itemList;
	private DefaultTableModel model;
	private int pageCnt = 0;

	public JPanel getJPanel() {
		return mainPanel;
	}
	
	
	// jtable 열 제목, 내용 설정
	Object columnName[] = { "체크박스", "번호", "아이템 종류", "아이템 이름", "가격" };

	// 메인함수
	public static void main(String[] args) {
		new ItemManager();
	}// end main

	// 생성자
	public ItemManager() {
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

		// 아이템 추가 button
		addBtn = new JButton("추가");
		addBtn.setBounds(967, 246, 68, 23);
		mainPanel.add(addBtn);
		addBtn.addActionListener(this);

		// 아이템 삭제 button
		delBtn = new JButton("삭제");
		delBtn.setBounds(1047, 246, 68, 23);
		mainPanel.add(delBtn);
		delBtn.addActionListener(this);

		// 테이블 컬럼 너비 설정
		itemListTable.getColumn("체크박스").setPreferredWidth(50);
		itemListTable.getColumn("번호").setPreferredWidth(50);
		itemListTable.getColumn("아이템 종류").setPreferredWidth(150);
		itemListTable.getColumn("아이템 이름").setPreferredWidth(80);
		itemListTable.getColumn("가격").setPreferredWidth(100);

//      itemListTable.getTableHeader().setReorderingAllowed(false); // 컬럼 순서 이동 불가
//      itemListTable.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		// 테이블의 체크박스 에디터, 렌더러 설정
		itemListTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		itemListTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1,
					int i, int j) {
				JCheckBox checkBox = new JCheckBox();
				checkBox.setHorizontalAlignment(JLabel.CENTER);

				if (obj instanceof Boolean) {
					checkBox.setSelected(((Boolean) obj).booleanValue());
				} else {
					checkBox.setSelected(Boolean.FALSE);
				}
				return (Component) checkBox;
			}
		});

		// 테이블 셀높이설정
		itemListTable.setRowHeight(30);

		// 1페이지화면으로 화면초기화
		listPaging(1);

		// 페이지 번호label
		pagePanel = new JPanel();
		pagePanel.setBounds(346, 615, 686, 49);
		mainPanel.add(pagePanel);
		pagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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

		//setVisible(true);
	}// end 생성자

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

	public void delete() {

		List<Integer> delList = new ArrayList<Integer>();

		for (int i = 0; i < itemList.size(); i++) {

			if ((boolean) model.getValueAt(i, 0) == true) {

				delList.add((int) model.getValueAt(i, 1));

			}

		}
		
		for (int i = 0; i < delList.size(); i++) {

			
				boolean result = idao.delete(delList.get(i));

				if (result == true) {

					System.out.println("아이템번호 : " + delList.get(i) + " >> 삭제 완료");

				} else {

					System.out.println("아이템번호 : " + delList.get(i) + " >> 삭제 실패");

				}
		}	
		
	}// end delete 메서드

	// 아이템 추가 메서드

	public void add() {

		Scanner scan = new Scanner(System.in);

		int inputType, inputName, inputPrice;
		int flag = 0;

		ivo = new ItemVO();

		System.out.println("-------------------[ 아이템 추가 ] -------------------");

		EXIT1:

		while (true) { // 아이템종류 while 시작

			System.out.println("※ 추가할 아이템의 종류 번호를 입력하세요.");

			System.out.println("1.채팅 내용 색상\t2.채팅 내용 굵게\t3.채팅창 테마 색상\t4.취소");

			System.out.print("> ");

			inputType = scan.nextInt();

			switch (inputType) {

			case 1:
				ivo.setItemType("채팅 내용 색상");
				break;

			case 2:
				ivo.setItemType("채팅 내용 굵게");
				break;

			case 3:
				ivo.setItemType("채팅창 테마 색상");
				break;

			case 4:
				flag = 1;
				break EXIT1;

			}

			if (inputType == 1) {

				System.out.println("※ 색깔을 선택하세요.");

				System.out.println("1.Red 2.Orange 3.Yellow 4.Green 5.Blue 6.Pink 7.Cyan");

				System.out.print("> ");

				inputName = scan.nextInt();

				switch (inputName) {

				case 1:
					ivo.setItemName("Red");
					break EXIT1;

				case 2:
					ivo.setItemName("Orange");
					break EXIT1;

				case 3:
					ivo.setItemName("Yellow");
					break EXIT1;

				case 4:
					ivo.setItemName("Green");
					break EXIT1;

				case 5:
					ivo.setItemName("Blue");
					break EXIT1;

				case 6:
					ivo.setItemName("Pink");
					break EXIT1;

				case 7:
					ivo.setItemName("Cyan");
					break EXIT1;

				default:
					System.out.println("다시 입력해주세요");
					break;

				}
			}

			else if (inputType == 2) {

				System.out.println();
				System.out.println("※ 굵기를 선택하세요.");
				while (true) {

					System.out.println("1.Bold");

					System.out.print("> ");

					inputName = scan.nextInt();

					if (inputName == 1) { // 아이템이름 맞게 입력할경우

						ivo.setItemName("Bold");

						break EXIT1; // 아이템이름 while문 빠져나감

					}

					else { // 아이템이름 잘못입력할경우

						System.out.println("다시 입력해주세요");

						continue; // 아이템이름 다시입력

					}

				}
			} else if (inputType == 3) {

				System.out.println();

				System.out.println("※ 채팅창 테마 색상을 선택하세요.");

				while (true) {
					System.out.println("1.Black 2.White 3.Pink 4.Green 5.Blue 6.Red");

					System.out.print("> ");

					inputName = scan.nextInt();

					switch (inputName) {

					case 1:
						ivo.setItemName("Black");
						break EXIT1;

					case 2:
						ivo.setItemName("White");
						break EXIT1;

					case 3:
						ivo.setItemName("Pink");
						break EXIT1;

					case 4:
						ivo.setItemName("Green");
						break EXIT1;

					case 5:
						ivo.setItemName("Blue");
						break EXIT1;

					case 6:
						ivo.setItemName("Red");
						break EXIT1;

					default:
						System.out.println("다시 입력해주세요");
						continue;

					}
				}
			}

			else { // 아이템종류 잘못 입력할경우

				System.out.println("다시 입력해주세요");

				continue;

			}

		} // end 아이템이름 while

		// 아이템 가격
		ivo.setItemPrice(3);

//         while(true) {
//            System.out.println();
//            System.out.println("※ 추가할 아이템의 가격을 입력하세요 (1~100)");
//            System.out.print("> ");
//            inputPrice = scan.nextInt();
//            if(inputPrice >= 1 && inputPrice <=100) {   //맞게 입력하면
//               ivo.setItemPrice(inputPrice);
//               break;
//            }
//            else {      //잘못 입력하면
//               System.out.println("다시 입력해주세요");
//               continue;
//            }
//         }   //end 아이템가격 while

		System.out.println();
		if (flag == 0)
			System.out.println("아이템 추가가 완료되었습니다");
		else
			System.out.println("아이템 추가를 취소합니다");

		idao.add(ivo);

	} // end add 메서드

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

			model.setValueAt(false, i, 0);

			model.setValueAt(itemList.get(i).getItemNo(), i, 1);

			model.setValueAt(itemList.get(i).getItemType(), i, 2);

			model.setValueAt(itemList.get(i).getItemName(), i, 3);

			model.setValueAt(itemList.get(i).getItemPrice(), i, 4);

		}

	}

	@Override

	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();

		if (btn == addBtn) {

			add();
			listPaging(1);
		}

		else if (btn == delBtn) {
			delete();
			listPaging(1);
		}

	}

	@Override

	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void mouseEntered(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override

	public void mouseExited(MouseEvent e) {

		// TODO Auto-generated method stub

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
