package project.admin.memberInfo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.List;
import project.DAO.MemberDAO;
import project.VO.MemberVO;
import project.admin.main.AdminMain;
import project.admin.qnaBoardMng.QnaPost;
import project.publicMain.PublicMain;

import javax.swing.table.DefaultTableModel;

public class MemberInfo implements ActionListener, MouseListener{
	
	JComboBox pntOrNot;
	JButton selectBtn, searchBtn;
	JTextField searchTf;
	JTable idListTb;
	JLabel prev,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,next;
	MemberDAO mdao;
	JPanel memInfoPane, pagePanel;
	Object []columnName = {"No.", "User ID"};
	String []userCategory = {"일반 사용자", "제한 사용자"};
	List<MemberVO> userList;
	DefaultTableModel model;
	int pageCnt = 0;	//페이지 10개 단위로 카운트
	int userPnt = 0;	//0이면 일반 사용자, 1이면 제한 사용자
	String searchWord = "";
	
	public MemberInfo(){
		mdao = new MemberDAO();
		memInfoPane = new JPanel();
		//패널 사이즈, 위치 설정
		memInfoPane.setBounds(44, 34, 1394, 794);
		memInfoPane.setLayout(null);
		
		//컴포넌트 객체생성
		pntOrNot = new JComboBox<String>(userCategory);
		selectBtn = new JButton("확인");
		searchBtn = new JButton("검색");
		searchTf = new JTextField();
		idListTb = new JTable();
		JScrollPane scrollPane = new JScrollPane(idListTb);
		pagePanel=new JPanel(new FlowLayout());
		
		//콤보박스
		pntOrNot.setToolTipText("");
		pntOrNot.setBounds(42, 35, 134, 28);
		memInfoPane.add(pntOrNot);
		
		//버튼 위치 설정
		//확인버튼
		selectBtn.setBounds(188, 35, 78, 28);
		//검색버튼
		searchBtn.setBounds(714, 163, 68, 28);
		//버튼에 리스너 달기
		selectBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		
		//패널에 버튼 붙이기
		memInfoPane.add(selectBtn);
		memInfoPane.add(searchBtn);
		
		//텍스트필드 위치, 길이 설정
		searchTf.setBounds(549, 164, 153, 28);
		searchTf.setColumns(10);
		//패널에 붙이기
		memInfoPane.add(searchTf);
		
		
		
		//테이블초기화 모델 생성, 테이블에 추가
		model =new DefaultTableModel(columnName, 10);
		idListTb.setModel(model);
		
		//테이블 데이터를 일반 사용자로 초기화
		userList = new ArrayList<MemberVO>();
		userList = mdao.getNormalMem(1);

		for(int i=0;i<userList.size();i++) {
			model.setValueAt(i+1, i, 0);
			model.setValueAt(userList.get(i).getMemId(), i, 1);
		}
		
		//테이블 컬럼 너비 설정
		idListTb.getColumn("No.").setPreferredWidth(310);
		idListTb.getColumn("User ID").setPreferredWidth(310);
		//테이블 셀높이 설정
		idListTb.setRowHeight(30);
		//테이블 위치 설정
		idListTb.setBounds(223, 198, 740, 115);
		
		//테이블리스너
		idListTb.addMouseListener(new MouseListener() {
			
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
				int rowIndex=idListTb.getSelectedRow();
				Object o;

				o = idListTb.getValueAt(rowIndex, 1);
				String userId = o.toString();
				MemberInfoManager mim = new MemberInfoManager(userId);
				PublicMain.am.changePanel(mim.getJPanel());
		
				
			}
		});
		
		//스크롤패널 설정
		scrollPane.setBounds(301, 278, 786, 325);
		//패널에 스크롤패널(테이블) 붙이기
		memInfoPane.add(scrollPane);
		
		//페이지목록 객체 생성
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
		
		//페이지목록 폰트크기,위치설정
		prev.setFont(prev.getFont().deriveFont(25.0f));
		p1.setFont(prev.getFont().deriveFont(25.0f));
		p2.setFont(prev.getFont().deriveFont(25.0f));
		p3.setFont(prev.getFont().deriveFont(25.0f));
		p4.setFont(prev.getFont().deriveFont(25.0f));
		p5.setFont(prev.getFont().deriveFont(25.0f));
		p6.setFont(prev.getFont().deriveFont(25.0f));
		p7.setFont(prev.getFont().deriveFont(25.0f));
		p8.setFont(prev.getFont().deriveFont(25.0f));
		p9.setFont(prev.getFont().deriveFont(25.0f));
		p10.setFont(prev.getFont().deriveFont(25.0f));
		next.setFont(prev.getFont().deriveFont(25.0f));
		
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
		
		//페이지 목록 설정,jlabel객체담기
		pagePanel.setSize(389,50);
		pagePanel.setLocation(491,693);
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
		
		//메인패널에 페이지패널 붙이기
		memInfoPane.add(pagePanel);
	}
	
	//페이지 넘버를 받아서 유저 리스트를 초기화해줌
	void userListPaging (int pageNo) {	
		for(int i=0;i<10;i++) {	//우선 테이블 초기화 하고
			model.setValueAt("", i, 0);
			model.setValueAt("", i, 1);
		}
		if(userPnt == 0) {
			//일반 사용자 리스트 테이블에 초기화
			userList = mdao.getNormalMem(pageNo+(10*pageCnt));
			for(int i=0;i<userList.size();i++) {
				model.setValueAt(i+1+(10*(pageNo-1))+(100*pageCnt), i, 0);
				model.setValueAt(userList.get(i).getMemId(), i, 1);
			}
		} else if(userPnt == 1){
			//제한 사용자 리스트 테이블에 초기화
			userList = mdao.getPntMem(pageNo+(10*pageCnt));
			for(int i=0;i<userList.size();i++) {
				model.setValueAt(i+1+(10*(pageNo-1))+(100*pageCnt), i, 0);
				model.setValueAt(userList.get(i).getMemId(), i, 1);
			}
		} else if(userPnt == 2) {
			//일반 사용자 검색 리스트 테이블에 초기화
			userList = mdao.getNormalMem(searchWord, pageNo+(10*pageCnt));
			for(int i=0;i<userList.size();i++) {
				model.setValueAt(i+1+(10*(pageNo-1))+(100*pageCnt), i, 0);
				model.setValueAt(userList.get(i).getMemId(), i, 1);
			}
		} else if(userPnt == 3) {
			//제한 사용자 검색 리스트 테이블에 초기화
			userList = mdao.getPntMem(searchWord, pageNo+(10*pageCnt));
			for(int i=0;i<userList.size();i++) {
				model.setValueAt(i+1+(10*(pageNo-1))+(100*pageCnt), i, 0);
				model.setValueAt(userList.get(i).getMemId(), i, 1);
			}
		}
	}
	
	
	//페이지 라벨들을 다음 또는 이전 페이지를 누를때 pageCnt를 증감시켜서 이에 맞춰 페이지 라벨을 초기화해줌
	void pageNoSet () {
		p1.setText("" + (1+10*pageCnt));
		p2.setText("" + (2+10*pageCnt));
		p3.setText("" + (3+10*pageCnt));
		p4.setText("" + (4+10*pageCnt));
		p5.setText("" + (5+10*pageCnt));
		p6.setText("" + (6+10*pageCnt));
		p7.setText("" + (7+10*pageCnt));
		p8.setText("" + (8+10*pageCnt));
		p9.setText("" + (9+10*pageCnt));
		p10.setText("" + (10+10*pageCnt));
	}
	
	
	
	
	public static void main(String[] args) {
		new MemberInfo();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn == selectBtn) {	//카테고리 선택 후 확인 버튼 눌렀을 때
			if(pntOrNot.getSelectedItem().equals("일반 사용자")) {
				userPnt = 0; //일반 사용자로 설정
				pageCnt = 0; //페이지카운트 0으로 초기화
				pageNoSet(); //페이지 라벨 초기화
				userListPaging(1);//유저 리스트 1페이지로 초기화
				
			} else if(pntOrNot.getSelectedItem().equals("제한 사용자")) {
				userPnt = 1;
				pageCnt = 0; //페이지카운트 0으로 초기화
				pageNoSet(); //페이지 라벨 초기화
				userListPaging(1);//유저 리스트 1페이지로 초기화
			}
			
			
		} else if(btn == searchBtn) {	//검색 버튼 눌렀을 때
			if(!searchTf.getText().equals("")) {	//검색어가 null값이 아니면
				if(pntOrNot.getSelectedItem().equals("일반 사용자")) {	//일반 사용자 카테고리 선택상태면 일반 사용자 검색
					userPnt = 2;
					searchWord = searchTf.getText();
					pageCnt = 0; //페이지카운트 0으로 초기화
					pageNoSet(); //페이지 라벨 초기화
					userListPaging(1);//유저 리스트 1페이지로 초기화
				} else if(pntOrNot.getSelectedItem().equals("제한 사용자")){	//제한 사용자 카테고리 선택상태면 제한 사용자 검색
					userPnt = 3;
					searchWord = searchTf.getText();
					pageCnt = 0; //페이지카운트 0으로 초기화
					pageNoSet(); //페이지 라벨 초기화
					userListPaging(1);//유저 리스트 1페이지로 초기화
				}
			}
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
		if(lb == p1) {//1페이지 눌렀을 때
			userListPaging (1);
			
		} else if(lb == p2) {//2페이지 눌렀을 때
			userListPaging (2);
			
		} else if(lb == p3) {//3페이지 눌렀을 때
			userListPaging (3);
			
		} else if(lb == p4) {//4페이지 눌렀을 때
			userListPaging (4);
			
		} else if(lb == p5) {//5페이지 눌렀을 때
			userListPaging (5);
			
		} else if(lb == p6) {//6페이지 눌렀을 때
			userListPaging (6);
			
		} else if(lb == p7) {//7페이지 눌렀을 때
			userListPaging (7);
			
		} else if(lb == p8) {//8페이지 눌렀을 때
			userListPaging (8);
			
		} else if(lb == p9) {//9페이지 눌렀을 때
			userListPaging (9);
			
		} else if(lb == p10) {//10페이지 눌렀을 때
			userListPaging (10);
			
		} else if(lb == next) {//다음페이지 눌렀을 때
			pageCnt++;
			//페이지 번호 변경
			pageNoSet ();
			//유저리스트 초기화해줌
			userListPaging (1);
			
		} else if(lb == prev) {//이전페이지 눌렀을 때
			if(pageCnt>0) {	//1~10페이지에서는 이전페이지 없음
				pageCnt--;
				//페이지 번호 변경
				pageNoSet ();
				//유저리스트 초기화해줌
				userListPaging (1);
			}
			
			
		}
	}
	
	public JPanel getJPanel() {
		return memInfoPane;
	}
	
}
