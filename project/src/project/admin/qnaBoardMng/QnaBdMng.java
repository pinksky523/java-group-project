package project.admin.qnaBoardMng;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import project.DAO.ComplBoardDAO;
import project.DAO.QnaBoardDAO;
import project.VO.ComplBoardVO;
import project.VO.MemberVO;
import project.VO.QnaBoardVO;
import project.admin.complBoardMng.ComplPost;
import project.publicMain.PublicMain;
import project.user.qnaBoard.UserQnaPost;

public class QnaBdMng implements MouseListener{
	QnaBoardDAO qbdao;
	List<QnaBoardVO> qbVoList;
	int pageCnt=0;
	JPanel contentPane;
	JButton searchBtn,writeBtn; //검색버튼 //쓰기버튼
	JTextField searchTextField; //검색창
	 
	 JTable boardTable; //목록
	 JComboBox<String> searchCombox;
	 JPanel pagePanel;
	 JLabel prev,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,next;
	 Object []columnName = {"글번호","제목","아이디","좋아요","조회 수","일시"};

     DefaultTableModel  model;
     private int nowPageState=0; // 0:전체 목록 1:내용 검색 목록 2:아이디 검색 목록     	  
     
	 public QnaBdMng() {
		
		contentPane=new JPanel();
		contentPane.setBounds(0,0,1394, 794);
		contentPane.setLayout(null);
		//////////////////////////////컴포넌트추가 준비 끝
		
		searchBtn=new JButton("검색");
		writeBtn=new JButton("공지사항 작성");
		searchTextField=new JTextField(30);
		searchCombox=new JComboBox<String>();
		searchCombox=new JComboBox<String>();
		searchCombox.addItem("내용");
		searchCombox.addItem("아이디");
		pagePanel=new JPanel();
		pagePanel.setLayout(new FlowLayout());
	
		
		//콤보박스
		searchCombox.setSize(73,30);
		searchCombox.setLocation(30,39);
		searchCombox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked") //e의 타입체크 안해도된다고 컴파일러에게 알려주는 어노테이션
				JComboBox<String> cb=(JComboBox<String>)e.getSource();
				if(cb==searchCombox) {
					System.out.println(cb.getSelectedItem());
				}
				
			}
		});
		
		//검색박스
		searchTextField.setSize(320, 31);
		searchTextField.setLocation(115,39);
		
		//검색버튼
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
		});;
		
		//글쓰기버튼
		writeBtn.setSize(132,30);
		writeBtn.setLocation(1238, 50);
		writeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scanner scn;
				String title,content;
				
				while(true) 
				{	System.out.println("글 제목을 입력하세요:");
					scn=new Scanner(System.in);
					title=scn.next();
				    if(title.length()>50||title.equals("")) {
				    	System.out.println("1자 이상 제목은 50자 이내로 입력하세요");
				    	continue;
				    }
				    else
				    	break;
				}
			
				while(true) {
					System.out.println("글 내용을 입력하세요:");
					scn=new Scanner(System.in);
					content=scn.next();
					if(content.length()>2000) {
						System.out.println("2000자 이내로 입력하세요");
				    	continue;
					}
					else
						break;
				}
				
				System.out.println("글을 작성 확인> 확인:1 취소:2");
				int re=scn.nextInt();
				if(re==1) {
					if(qbdao.writePostByAdmin(title, content))
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
		});
		
		
		//테이블초기화 모델 생성,테이블에 추가
		model =new DefaultTableModel(columnName,15){ 
		private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) { 
    	        return false; 
    	       } 
    	   }; ;
		boardTable=new JTable(model);
		
		
		//첫화면 1페이지로 초기화
		drawPageByState(1);
		

		//테이블 컬럼 너비 설정
		boardTable.getColumn("글번호").setPreferredWidth(5);
		boardTable.getColumn("제목").setPreferredWidth(250);
		boardTable.getColumn("아이디").setPreferredWidth(5);
		boardTable.getColumn("좋아요").setPreferredWidth(3);
		boardTable.getColumn("조회 수").setPreferredWidth(3);
		boardTable.getColumn("일시").setPreferredWidth(5);
		
		
		
		//테이블의 세로 줄 삭제
		boardTable.setShowVerticalLines(false); 
		//테이블 셀높이설정
		boardTable.setRowHeight(40);

//		boardTable.setEnabled(false); 클릭 오류 이놈이 범인..............
		boardTable.setFocusable(false); 
//		boardTable.setRowSelectionAllowed(false); 
		
		//테이블리스너
		boardTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowIndex=boardTable.getSelectedRow();
				Object o;

				o = boardTable.getValueAt(rowIndex, 0);
				int no = Integer.parseInt(o.toString());
				o = boardTable.getValueAt(rowIndex, 1);
				String title = o.toString();
				o = boardTable.getValueAt(rowIndex, 2);
				String id = o.toString();
				o = boardTable.getValueAt(rowIndex, 3);
				int like = Integer.parseInt(o.toString());
				o = boardTable.getValueAt(rowIndex, 4);
				int viewCnt = Integer.parseInt(o.toString());
				o = boardTable.getValueAt(rowIndex, 5);
				String date = o.toString();
				QnaPost QnaPost = new QnaPost(no, title, id, like, viewCnt, date);
				PublicMain.am.changePanel(QnaPost.getJPanel());
		
				
			}
		});
		
		//글목록(테이블)설정 
		JScrollPane jScollPane = new JScrollPane(boardTable);
		jScollPane.setSize(1340,624);
		jScollPane.setLocation(30,90);
		
		//페이지 라벨 객체 생성
		prev=new JLabel("<");
		p1=new JLabel("1");
		p2=new JLabel("2");
		p3=new JLabel("3");
		p4=new JLabel("4");
		p5=new JLabel("5");
		p6=new JLabel("6");
		p7=new JLabel("7");
		p8=new JLabel("8");
		p9=new JLabel("9");
		p10=new JLabel("10");
		next=new JLabel(">");
		
		//페이지목록 리스너 추가
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
		//페이지 목록 설정,jlabel객체담기
		pagePanel.setSize(1111,47);
		pagePanel.setLocation(138,726);
		
		//페이지목록 설정
		
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
		
		
		
		
		
		//페이지목록 폰트크기,위치설정
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
		
		
		//패널에 붙히기
		contentPane.add(pagePanel);
		contentPane.add(searchCombox);
		contentPane.add(searchTextField);
		contentPane.add(searchBtn);
		contentPane.add(writeBtn);
		contentPane.add(jScollPane);
		
		JButton newBtn = new JButton("새로고침");
		newBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowPageState=0;
				drawPageByState(1);
				searchTextField.setText("");
			}
		});
		newBtn.setBounds(1078, 50, 132, 30);
		contentPane.add(newBtn);
		
	}
	
	
	public JPanel getJPanel() {
		return contentPane;
	}


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
			
		} else if(lb == p6) {//6페initializeJTable();
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


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
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
	
	public void drawPageByState(int pageNo) {
		qbdao=new QnaBoardDAO();
		qbVoList = new ArrayList<QnaBoardVO>();
		if(nowPageState==0) {//전체목록
			qbVoList=qbdao.getPageOfQnaPostsInfo(pageNo);
		}
		if(nowPageState==1) {//내용으로검색
			qbVoList=qbdao.getPageUsingContentsByAdmin(pageNo,searchTextField.getText());
		}
			
		if(nowPageState==2) {//아이디로 검색
			qbVoList=qbdao.getPageUsingMemIdByAdmin(pageNo, searchTextField.getText());
		}
		insertPageOnJTable();
	}
	
	public void initializeJTable() {
		for(int i=0;i<15;i++) {	//우선 테이블 초기화 하고
			model.setValueAt("", i, 0);
			model.setValueAt("", i, 1);
			model.setValueAt("", i, 2);
			model.setValueAt("", i, 3);
			model.setValueAt("", i, 4);
			model.setValueAt("", i, 5);
		}
	}
	
	public void insertPageOnJTable(){
		initializeJTable();
		for(int i=0;i<qbVoList.size();i++) {
			model.setValueAt(qbVoList.get(i).getQnaNo(),i,0);
			if(qbVoList.get(i).getQnaType()==1) //공지사항이라면
				model.setValueAt("[공지사항]"+qbVoList.get(i).getQnaTitle(), i,1);
			else model.setValueAt(qbVoList.get(i).getQnaTitle(), i,1);
				model.setValueAt(qbVoList.get(i).getMemId(), i,2);
				model.setValueAt(qbVoList.get(i).getQnaLike(),i, 3);
				model.setValueAt(qbVoList.get(i).getQnaViewcnt(),i, 4);
				model.setValueAt(qbVoList.get(i).getQnaDate(),i, 5);
		}
	}
	
	
}