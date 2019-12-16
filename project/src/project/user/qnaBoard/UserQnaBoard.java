package project.user.qnaBoard;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.*;

import project.DAO.QnaBoardDAO;
import project.VO.MemberVO;
import project.VO.QnaBoardVO;
import project.publicMain.PublicMain;
import project.user.main.UserMain;

public class UserQnaBoard implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;	
	JButton searchBtn, writeBtn;
	JTextField searchTextField;
	JTable qnaBoardTable;
	JComboBox<String> searchCombox;
	JPanel userQnaPane, pagePanel;
	JLabel prev, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, next;
	Object[] columnName = { "글번호", "제목", "아이디", "좋아요", "조회 수", "일시" };
	Object[][] rowDatas;
	String[] searchCategory = { "내용", "아이디" };
	//DefaultTableModel model = new DefaultTableModel(rowDatas, columnName);

	QnaBoardDAO qdao;
	QnaBoardVO qvo;
	List<QnaBoardVO> list;
	int pageCnt = 0;	//페이지 10개 단위로 카운트
	private boolean result;
	DefaultTableModel model;	//model 변수 선언
	Scanner scan;
	
	MemberVO mvo=new MemberVO();
	private int nowPageState=0; //맨위에 선언  0:전체 목록 1:내용 검색 목록 2:아이디 검색 목록    
	
	public UserQnaBoard() {	
		mvo.setMemId(UserMain.mvo.getMemId());
		
		userQnaPane = new JPanel();
		userQnaPane.setBounds(0,0,1394,794);
		userQnaPane.setLayout(null);
		// 컴포넌트 객체 생성
		searchBtn = new JButton("검색");
		writeBtn = new JButton("글쓰기");
		searchTextField = new JTextField(30);
		searchCombox = new JComboBox<String>(searchCategory);
		pagePanel = new JPanel(new FlowLayout());
		
		// 콤보박스
		searchCombox.setSize(65, 27);
		searchCombox.setLocation(35, 40);
		searchCombox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked") // e의 타입체크 안해도된다고 컴파일러에게 알려주는 어노테이션
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				if (cb == searchCombox) {
	//				System.out.println(cb.getSelectedItem());	//콤보박스의 글자들이 출력됨
				}
			}
		});

		// 검색창
		searchTextField.setSize(320, 30);
		searchTextField.setLocation(120, 40);

		// 검색버튼
		searchBtn.setSize(78, 35);
		searchBtn.setLocation(447, 37);
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchTextField.getText().length()>50)
					JOptionPane.showMessageDialog(null, "50자이내로 검색하세요");
				else {
					if(searchCombox.getSelectedItem().equals("내용")) {
						nowPageState=1;
						drawPageByState(1);
					}
					if(searchCombox.getSelectedItem().equals("아이디")) {
						//아이디로 검색할때 
						nowPageState=2;
						drawPageByState(1);
					}
				}
			}
		});
		
		userQnaPane.add(searchBtn);
		
		JButton newBtn = new JButton("새로고침");
		newBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowPageState=0;
				drawPageByState(1);
				searchTextField.setText("");
			}
		});
		newBtn.setBounds(1078, 50, 132, 30);
		userQnaPane.add(newBtn);
	
		// 글쓰기버튼
		writeBtn.setSize(132,30);
		writeBtn.setLocation(1238, 50);
		writeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(UserMain.mvo.getMemBdPntTf()==0) {
				Scanner scan;
				String title, content;	//제목, 내용 받을 변수 선언
				scan=new Scanner(System.in);
				qdao = new QnaBoardDAO();
				while(true) {
					System.out.println("※게시글 제목을 입력해주세요 (50자 이내)");
					
					title=scan.next();
					if(title.length()>50||title.equals("")) {
						System.out.println("1자 이상 50자 이내로 입력하세요");
						continue;	//입력 할때까지 반복
					}
					else break;
				}				

				while(true) {
					System.out.println("※게시글 내용을 입력해주세요 (2000자 이내)");
					content=scan.next();					
					if(content.length()>2000) {
						System.out.println("2000자 이내로 입력하세요");
						continue;	//입력 할때까지 반복
					}
					else break;
				}	
				System.out.println("글을 작성 확인> 확인:1 취소:2");
				int re=scan.nextInt();
				if(re==1) {
					
				if(qdao.writeQnaPost(mvo.getMemId(), title, content))
					 System.out.println("성공");
				 else
					 System.out.println("실패");				 
				 nowPageState=0;
				 drawPageByState(1);

				 				 
				}
				else {
					System.out.println("글 작성이 취소되었습니다.");
					
					}
				}
				else {
					String date=new String(new SimpleDateFormat("yyMMdd").format(UserMain.mvo.getMemBdPntEnddate()));
					JOptionPane.showMessageDialog(null,"게시판 글쓰기 제한상태입니다.\n"
							+ "제한은 "+date+"에 종료됩니다.");
				}
			}//클릭드	
		});
		
		userQnaPane.add(writeBtn);

		qdao = new QnaBoardDAO();
		// 테이블초기화 모델 생성,테이블에 추가		
		model = new DefaultTableModel(columnName,15);
		qnaBoardTable = new JTable(model);
		qnaBoardTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		
			@Override
			public void mouseClicked(MouseEvent e) {
//				Object ob = new Object();
				 int rIndex = qnaBoardTable.getSelectedRow();				 
				 Object ob = qnaBoardTable.getValueAt(rIndex, 0);				 
				 int qnaNum = Integer.parseInt(ob.toString());
				 ob = qnaBoardTable.getValueAt(rIndex, 1);
				 String qnaTitle = ob.toString();
				 ob = qnaBoardTable.getValueAt(rIndex, 2);
				 String memId = ob.toString();
				 ob = qnaBoardTable.getValueAt(rIndex, 3);
				 int qnaLike = Integer.parseInt(ob.toString());
				 ob = qnaBoardTable.getValueAt(rIndex, 4);
				 int qnaViewcnt = Integer.parseInt(ob.toString());
				 ob = qnaBoardTable.getValueAt(rIndex, 5);
				 String qnadate = ob.toString();				 
				 UserQnaPost uqp = new UserQnaPost(qnaNum, qnaTitle, memId, qnaLike, qnaViewcnt, qnadate);	
				 PublicMain.um.changePanel(uqp.getJPanel());
			}
		});
		
		list = new ArrayList<QnaBoardVO>();
		list = qdao.getPageOfQnaPostsInfo(1);		
		insertPageOnJTable(list);

		// 테이블 컬럼 너비 설정
		qnaBoardTable.getColumn("글번호").setPreferredWidth(5);
		qnaBoardTable.getColumn("제목").setPreferredWidth(250);
		qnaBoardTable.getColumn("아이디").setPreferredWidth(5);
		qnaBoardTable.getColumn("좋아요").setPreferredWidth(3);
		qnaBoardTable.getColumn("조회 수").setPreferredWidth(3);
		qnaBoardTable.getColumn("일시").setPreferredWidth(50);

		// 테이블의 세로 줄 삭제
		qnaBoardTable.setShowVerticalLines(false);

		// 테이블 셀높이설정
		qnaBoardTable.setRowHeight(40);

		// 글목록(테이블)설정
		JScrollPane jScollPane = new JScrollPane(qnaBoardTable);
		jScollPane.setSize(1340,624);
		jScollPane.setLocation(30, 90);

		
		// 페이지목록 설정
		prev = new JLabel("<");
		p1 = new JLabel("1");
		p2 = new JLabel("2");
		p3 = new JLabel("3");
		p4 = new JLabel("4");
		p5 = new JLabel("5");
		p6 = new JLabel("6");
		p7 = new JLabel("7");
		p8 = new JLabel("8");
		p9 = new JLabel("9");
		p10 = new JLabel("10");
		next = new JLabel(">");

		// 페이지목록 폰트크기,위치설정
		prev.setFont(prev.getFont().deriveFont(32.0f));
		p1.setFont(prev.getFont().deriveFont(32.0f));
		p2.setFont(prev.getFont().deriveFont(32.0f));
		p3.setFont(prev.getFont().deriveFont(32.0f));
		p4.setFont(prev.getFont().deriveFont(32.0f));
		p5.setFont(prev.getFont().deriveFont(32.0f));
		p6.setFont(prev.getFont().deriveFont(32.0f));
		p7.setFont(prev.getFont().deriveFont(32.0f));
		p8.setFont(prev.getFont().deriveFont(32.0f));
		p9.setFont(prev.getFont().deriveFont(32.0f));
		p10.setFont(prev.getFont().deriveFont(32.0f));
		next.setFont(prev.getFont().deriveFont(32.0f));

		//페이지 목록에 리스너달기
		prev.addMouseListener(this);
		p1.addMouseListener(this);
		p2.addMouseListener(this);
		p3.addMouseListener(this);
		p4.addMouseListener(this);
		p5.addMouseListener(this);
		p6.addMouseListener(this);
		p7.addMouseListener(this);
		p8.addMouseListener(this);
		p9.addMouseListener(this);
		p10.addMouseListener(this);
		next.addMouseListener(this);
		
		
		// 페이지 목록 설정,jlabel객체담기
		pagePanel.setSize(1111,47);
		pagePanel.setLocation(138,726);
		pagePanel.add(prev);
		pagePanel.add(p1);
		pagePanel.add(p2);
		pagePanel.add(p3);
		pagePanel.add(p4);
		pagePanel.add(p5);
		pagePanel.add(p6);
		pagePanel.add(p7);
		pagePanel.add(p8);
		pagePanel.add(p9);
		pagePanel.add(p10);
		pagePanel.add(next);

		// 패널에 붙히기
		userQnaPane.add(pagePanel);
		userQnaPane.add(searchCombox);
		userQnaPane.add(searchTextField);
		userQnaPane.add(searchBtn);
		userQnaPane.add(writeBtn);
		userQnaPane.add(jScollPane);
		}
	//}	
	
	public static void main(String[] args) {
		new UserQnaBoard();
	}//END main

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel lb = (JLabel) e.getSource();
		
		if(lb == p1) {//1페이지 눌렀을 때
			
			drawPageByState(1+(10*pageCnt));
			
		} else if(lb == p2) {//2페이지 눌렀을 때
			
			drawPageByState(2+(10*pageCnt));
			
		} else if(lb == p3) {//3페이지 눌렀을 때
		
			drawPageByState(3+(10*pageCnt));
			
		} else if(lb == p4) {//4페이지 눌렀을 때
			
			drawPageByState(4+(10*pageCnt));
			
		} else if(lb == p5) {//5페이지 눌렀을 때
			
			drawPageByState(5+(10*pageCnt));
			
		} else if(lb == p6) {//6페이지 눌렀을 때
			
			drawPageByState(6+(10*pageCnt));
			
		} else if(lb == p7) {//7페이지 눌렀을 때
			
			drawPageByState(7+(10*pageCnt));
			
		} else if(lb == p8) {//8페이지 눌렀을 때
			
			drawPageByState(8+(10*pageCnt));
			
		} else if(lb == p9) {//9페이지 눌렀을 때
			
			drawPageByState(9+(10*pageCnt));
			
		} else if(lb == p10) {//10페이지 눌렀을 때
			
			drawPageByState(10+(10*pageCnt));
			
		} else if(lb == next) {//다음페이지 눌렀을 때
			
			pageCnt++;
			drawPageByState(1+(10*pageCnt));
			
			//페이지 번호 변경
			p1.setText(Integer.toString(1+10*pageCnt));
			p2.setText(Integer.toString(2+10*pageCnt));
			p3.setText(Integer.toString(3+10*pageCnt));
			p4.setText(Integer.toString(4+10*pageCnt));
			p5.setText(Integer.toString(5+10*pageCnt));
			p6.setText(Integer.toString(6+10*pageCnt));
			p7.setText(Integer.toString(7+10*pageCnt));
			p8.setText(Integer.toString(8+10*pageCnt));
			p9.setText(Integer.toString(9+10*pageCnt));
			p10.setText(Integer.toString(10+10*pageCnt));
			
			
		} else if(lb == prev) {//이전페이지 눌렀을 때
			
			pageCnt--;
			if(pageCnt==-1)
				pageCnt++;
			drawPageByState(1+(10*pageCnt));
			
			//페이지 번호 변경
			p1.setText(Integer.toString(1+10*pageCnt));
			p2.setText(Integer.toString(2+10*pageCnt));
			p3.setText(Integer.toString(3+10*pageCnt));
			p4.setText(Integer.toString(4+10*pageCnt));
			p5.setText(Integer.toString(5+10*pageCnt));
			p6.setText(Integer.toString(6+10*pageCnt));
			p7.setText(Integer.toString(7+10*pageCnt));
			p8.setText(Integer.toString(8+10*pageCnt));
			p9.setText(Integer.toString(9+10*pageCnt));
			p10.setText(Integer.toString(10+10*pageCnt));
		}		
	}		

	public void drawPageByState(int pageNo) {
		qdao=new QnaBoardDAO();
		list = new ArrayList<QnaBoardVO>();
		if(nowPageState==0) {//전체목록
			list=qdao.getPageOfQnaPostsInfo(pageNo);
			
		}
		if(nowPageState==1) {//내용으로검색
			list = new ArrayList<QnaBoardVO>();
			list=qdao.searchByContent(pageNo, searchTextField.getText());
			//insertPageOnJTable(list);
		}			
		if(nowPageState==2) {//아이디로 검색
			list = new ArrayList<QnaBoardVO>();
			list=qdao.searchById(pageNo, searchTextField.getText());
			//insertPageOnJTable(list);
		}		
		insertPageOnJTable(list);
	}	

	private void insertPageOnJTable(List<QnaBoardVO> list) {
	   initializeJTable();
	   for(int i=0;i<list.size();i++) {
		   if(list.get(i).getQnaType()==1) {
			   model.setValueAt("[공지사항]"+list.get(i).getQnaTitle(), i, 1);
		   }
		   else {
			   model.setValueAt(list.get(i).getQnaTitle(), i, 1);
		   }
			model.setValueAt(list.get(i).getQnaNo(), i, 0);
			model.setValueAt(list.get(i).getMemId(), i, 2);
			model.setValueAt(list.get(i).getQnaLike(), i, 3);
			model.setValueAt(list.get(i).getQnaViewcnt(), i, 4);
			model.setValueAt(list.get(i).getQnaDate(), i, 5);
	    }
	} 	
	
	public void initializeJTable() {
	  for(int i=0;i<15;i++) { //우선 테이블 초기화 하고   
	   model.setValueAt("", i, 0);
	   model.setValueAt("", i, 1);
	   model.setValueAt("", i, 2);
	   model.setValueAt("", i, 3);
	   model.setValueAt("", i, 4);
	   model.setValueAt("", i, 5);	
	  }	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public JPanel getJPanel() {
		return userQnaPane;
	}

}