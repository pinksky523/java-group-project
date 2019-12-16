package project.user.message;

import java.awt.*;

import java.sql.*;
import java.sql.Date;

import javax.swing.*;
import javax.swing.table.*;

import oracle.sql.DATE;
import project.DAO.MessageDAO;
import project.VO.MessageVO;
import project.publicMain.PublicMain;
import project.user.main.UserMain;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class MessageBox implements ActionListener, MouseListener {
	 
	 Scanner scan;
	 
	 private MessageDAO mdao;
	 private MessageVO mvo;
	
	 private static final long serialVersionUID = 1L;
	 
	 JButton sendBtn, delBtn;
	 JTable receiveTb, sendTb;
	 JLabel messageBoxLb, receiveLb, sendLb, MessageIconLb, prev,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,next,pprev,pp1,pp2,pp3,pp4,pp5,pp6,pp7,pp8,pp9,pp10,nnext;
	 JPanel messageBoxPane, pagePanel, pagePanel_1;
	 JScrollPane scrollPane, scrollPane_1;
	 JCheckBox chkBox;
	 JCheckBox chkBox2;
	 
	 java.util.List<MessageVO> rmessageList;
	 java.util.List<MessageVO> smessageList;
	 DefaultTableModel model, model_1;
	 Object [] columnName = {"삭제항목체크", "번호", "보낸사람", "내용", "받는사람", "발송일시"};
	 int pageCnt = 0;
	 int pageCnt2 = 0;
	 String memId =UserMain.mvo.getMemId();	//로그인한 아이디

	 public MessageBox() {		 
		 
		 scan = new Scanner(System.in);
		 mdao = new MessageDAO();
		 mvo = new MessageVO();
		 
		 //컴포넌트 객체 생성
		 delBtn = new JButton("삭제");
		 sendBtn = new JButton("쪽지보내기");
		 messageBoxPane = new JPanel(new FlowLayout());
	     pagePanel = new JPanel(new FlowLayout());
	     pagePanel_1 = new JPanel(new FlowLayout());
	   	 chkBox = new JCheckBox();
	   	 chkBox2 = new JCheckBox();
	   	 receiveTb = new JTable();
	   	 sendTb = new JTable();
		 scrollPane = new JScrollPane(receiveTb);
		 scrollPane_1 = new JScrollPane(sendTb);

		
		 
		 //패널 사이즈, 위치, 배경색 설정
		 messageBoxPane.setBounds(0, 0, 1394, 794);
		 messageBoxPane.setLayout(null);
		 
		 //프레임에 패널 붙이기
		 

		 //삭제 버튼
   	 	 delBtn.setSize(90,30);
   	 	 delBtn.setLocation(1130, 129);
   	 	 messageBoxPane.add(delBtn);
   	 	 //버튼에 리스너 달기
   	 	 delBtn.addActionListener(this);

		 //쪽지보내기 버튼
   	 	 sendBtn.setSize(99,30);
   	 	 sendBtn.setLocation(1240, 129);
   	 	 messageBoxPane.add(sendBtn);
   	 	 sendBtn.addActionListener(this);

   	 	 //받은쪽지함 테이블초기화 모델 생성, 테이블에 추가
	     model =new DefaultTableModel(columnName, 6);
	     receiveTb.setModel(model);
	     
	     //보낸쪽지함 테이블초기화 모델 생성, 테이블에 추가
	     model_1 = new DefaultTableModel(columnName, 6);
	     sendTb.setModel(model_1);
			
		 //테이블 데이터 초기화
		 rmessageList = new ArrayList<MessageVO>();
		 smessageList = new ArrayList<MessageVO>();
		 rmessageList = mdao.getReceivedM(1, memId);
		 smessageList = mdao.getSendM(1, memId);

		 receiveMListPaging(1);
		 sendMListPaging(1);
//		 for(int i=0;i<rmessageList.size();i++) {
//			model.setValueAt(false, i, 0);
//			model.setValueAt(i+1, i, 1);
//			model.setValueAt(rmessageList.get(i).getMemSendId(), i, 2);
//			model.setValueAt(rmessageList.get(i).getMessageContent(), i, 3);
//			model.setValueAt(rmessageList.get(i).getMemReceiveId(), i, 4);
//			model.setValueAt(rmessageList.get(i).getMessageSendDate(), i, 5);
//		 }
//		 
////		 System.out.println(model.getValueAt(0, 3));
//		 
//		 for(int i=0;i<smessageList.size();i++) {
//			model_1.setValueAt(false, i, 0);
//			model_1.setValueAt(i+1, i, 1);
//			model_1.setValueAt(smessageList.get(i).getMemSendId(), i, 2);
//			model_1.setValueAt(smessageList.get(i).getMessageContent(), i, 3);
//			model_1.setValueAt(smessageList.get(i).getMemReceiveId(), i, 4);
//			model_1.setValueAt(smessageList.get(i).getMessageSendDate(), i, 5);
//			 }
		 
	     //테이블 컬럼 너비 설정
	     receiveTb.getColumn("삭제항목체크").setPreferredWidth(6);
	     receiveTb.getColumn("번호").setPreferredWidth(2);
	     receiveTb.getColumn("보낸사람").setPreferredWidth(3);
	     receiveTb.getColumn("내용").setPreferredWidth(100);
	     receiveTb.getColumn("받는사람").setPreferredWidth(3);
	     receiveTb.getColumn("발송일시").setPreferredWidth(30);
	     
	     sendTb.getColumn("삭제항목체크").setPreferredWidth(6);
	     sendTb.getColumn("번호").setPreferredWidth(2);
	     sendTb.getColumn("보낸사람").setPreferredWidth(3);
	     sendTb.getColumn("내용").setPreferredWidth(100);
	     sendTb.getColumn("받는사람").setPreferredWidth(3);
	     sendTb.getColumn("발송일시").setPreferredWidth(30);
	
		 //테이블 셀 높이설정
	     receiveTb.setRowHeight(30);
	     sendTb.setRowHeight(30);

	    //받은쪽지 테이블의 체크박스 에디터, 렌더러 설정
	     receiveTb.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
	     
	     receiveTb.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
	     public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1, int i, int j) {
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

	     
	    //보낸쪽지 테이블의 체크박스 에디터, 렌더러 설정
	     sendTb.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
	     
	     sendTb.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
	     public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1, int i, int j) {
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

			
		 //스크롤 설정
	     scrollPane = new JScrollPane(receiveTb);
	     scrollPane.setBounds(272, 167, 820, 204);
	    
	     messageBoxPane.add(scrollPane);
		 
		 scrollPane_1 = new JScrollPane(sendTb);
		 scrollPane_1.setBounds(272, 476, 820, 204);
		 
		 messageBoxPane.add(scrollPane_1);

		  //받은쪽지함 페이지목록 설정
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
		  
		  //보낸쪽지함 페이지목록 설정
	  	  pprev=new JLabel("<");
		  pp1=new JLabel("1");
		  pp2=new JLabel("2");
		  pp3=new JLabel("3");
		  pp4=new JLabel("4");
		  pp5=new JLabel("5");
		  pp6=new JLabel("6");
		  pp7=new JLabel("7");
		  pp8=new JLabel("8");
		  pp9=new JLabel("9");
		  pp10=new JLabel("10");
		  nnext=new JLabel(">");

		  //받은쪽지함 페이지목록 폰트 설정
		  prev.setFont(new Font("굴림", Font.PLAIN, 15));
		  p1.setFont(new Font("굴림", Font.PLAIN, 15));
		  p2.setFont(new Font("굴림", Font.PLAIN, 15));
		  p3.setFont(new Font("굴림", Font.PLAIN, 15));
		  p4.setFont(new Font("굴림", Font.PLAIN, 15));
		  p5.setFont(new Font("굴림", Font.PLAIN, 15));
		  p6.setFont(new Font("굴림", Font.PLAIN, 15));
		  p7.setFont(new Font("굴림", Font.PLAIN, 15));
		  p8.setFont(new Font("굴림", Font.PLAIN, 15));
		  p9.setFont(new Font("굴림", Font.PLAIN, 15));
		  p10.setFont(new Font("굴림", Font.PLAIN, 15));
		  next.setFont(new Font("굴림", Font.PLAIN, 15));
		  
		  //보낸쪽지함 페이지목록 폰트 설정
		  pprev.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp1.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp2.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp3.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp4.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp5.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp6.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp7.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp8.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp9.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp10.setFont(new Font("굴림", Font.PLAIN, 15));
		  nnext.setFont(new Font("굴림", Font.PLAIN, 15));
		  
		  //페이지 버튼 굵게하는 메서드 실행
		  setFont();
		  setFont2();
		  
		  //받은쪽지함 페이지 목록에 리스너달기
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
		  
		  //보낸쪽지함 페이지 목록에 리스너달기
		  pprev.addMouseListener(this);
		  pp1.addMouseListener(this);
		  pp2.addMouseListener(this);
		  pp3.addMouseListener(this);
		  pp4.addMouseListener(this);
		  pp5.addMouseListener(this);
		  pp6.addMouseListener(this);
		  pp7.addMouseListener(this);
		  pp8.addMouseListener(this);
		  pp9.addMouseListener(this);
		  pp10.addMouseListener(this);
		  nnext.addMouseListener(this);

		  //받은쪽지함 페이지 목록 설정, jLabel에 객체담기
		  pagePanel.setSize(500,30);
		  pagePanel.setLocation(439,381);
		  //pagePanel.setBackground(getBackground());
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
		  messageBoxPane.add(pagePanel);
		   
		  //보낸쪽지함 페이지 목록 설정, jLabel에 객체담기
		  pagePanel_1.setSize(500,30);
		  pagePanel_1.setLocation(439,700);
		  //pagePanel_1.setBackground(getBackground());
		  pagePanel_1.add(pprev);
		  pagePanel_1.add(pp1);
		  pagePanel_1.add(pp2);
		  pagePanel_1.add(pp3);
		  pagePanel_1.add(pp4);
		  pagePanel_1.add(pp5);
		  pagePanel_1.add(pp6);
		  pagePanel_1.add(pp7);
		  pagePanel_1.add(pp8);
		  pagePanel_1.add(pp9);
		  pagePanel_1.add(pp10);
		  pagePanel_1.add(nnext);
		  messageBoxPane.add(pagePanel_1);

		  messageBoxLb = new JLabel("쪽지함");
		  messageBoxLb.setFont(new Font("나눔고딕", Font.BOLD, 30));
		  messageBoxLb.setBounds(110, 142, 104, 42);
		  messageBoxPane.add(messageBoxLb);
		  
		  receiveLb = new JLabel("받은쪽지함");
		  receiveLb.setFont(new Font("굴림", Font.BOLD, 15));
		  receiveLb.setBounds(272, 142, 76, 15);
		  messageBoxPane.add(receiveLb);
		  
		  sendLb = new JLabel("보낸쪽지함");
		  sendLb.setFont(new Font("굴림", Font.BOLD, 15));
		  sendLb.setBounds(272, 451, 76, 15);
		  messageBoxPane.add(sendLb);
		  
		  MessageIconLb = new JLabel("");
		  MessageIconLb.setIcon(new ImageIcon("C:\\javawork\\project\\e-mail-envelope (1).png"));
		  MessageIconLb.setBounds(36, 129, 64, 64);
		  messageBoxPane.add(MessageIconLb);
		  
		  //JTable에 마우스 리스너 달기
		  receiveTb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  receiveTb.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				JTable jt = (JTable) arg0.getSource();
				if(jt == receiveTb && jt.getSelectedColumn() != 0 && model.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn()) != null) { 
					int row = receiveTb.getSelectedRow();
					int column = receiveTb.getSelectedColumn();
//					//조회한 쪽지 레코드 폰트 색 변경								//안됨
//					model.getValueAt(row, 1).setForeground(Color.GRAY);
//					model.getValueAt(row, 2).setForeground(Color.GRAY);	
//					model.getValueAt(row, 3).setForeground(Color.GRAY);	
//					model.getValueAt(row, 4).setForeground(Color.GRAY);	
//					model.getValueAt(row, 5).setForeground(Color.GRAY);	
					System.out.println("-------------------- [받은 쪽지 조회] --------------------");
					System.out.println("쪽지번호: " + model.getValueAt(row, 1));
					System.out.println("보낸사람: " + model.getValueAt(row, 2));
					System.out.println("쪽지내용: " + model.getValueAt(row, 3));
					System.out.println("받는사람: " + model.getValueAt(row, 4));
					System.out.println("발송일시: " + model.getValueAt(row, 5));
					receiveMListPaging(1);
				}
			}
			  
		  });
		  
		  sendTb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  sendTb.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg1) {
					JTable jt = (JTable) arg1.getSource();
					if(jt == sendTb && jt.getSelectedColumn() != 0 && model_1.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn()) != null) { 
						int row = sendTb.getSelectedRow();
//						int column = sendTb.getSelectedColumn();
						//조회한 쪽지 레코드 속성들의 폰트 색 변경								//안됨
//						((Component) model_1.getValueAt(row, 1)).setForeground(Color.GRAY);
//						((Component) model_1.getValueAt(row, 2)).setForeground(Color.GRAY);	
//						((Component) model_1.getValueAt(row, 3)).setForeground(Color.GRAY);	
//						((Component) model_1.getValueAt(row, 4)).setForeground(Color.GRAY);	
//						((Component) model_1.getValueAt(row, 5)).setForeground(Color.GRAY);	
						System.out.println("-------------------- [보낸 쪽지 조회] --------------------");
						System.out.println("쪽지번호: " + model_1.getValueAt(row, 1));
						System.out.println("보낸사람: " + model_1.getValueAt(row, 2));
						System.out.println("쪽지내용: " + model_1.getValueAt(row, 3));
						System.out.println("받는사람: " + model_1.getValueAt(row, 4));
						System.out.println("발송일시: " + model_1.getValueAt(row, 5));
						sendMListPaging(1);
					}
				}
				  
			  });
		  
		  

	}
	 
//	 //테이블 초기화 메서드
//	 public void resetTb() {
//		 for(int i=0;i<rmessageList.size();i++) {
//				model.setValueAt(false, i, 0);
//				model.setValueAt(i+1, i, 1);
//				model.setValueAt(rmessageList.get(i).getMemSendId(), i, 2);
//				model.setValueAt(rmessageList.get(i).getMessageContent(), i, 3);
//				model.setValueAt(rmessageList.get(i).getMemReceiveId(), i, 4);
//				model.setValueAt(rmessageList.get(i).getMessageSendDate(), i, 5);
//			 }
//			 
////			 System.out.println(model.getValueAt(0, 3));
//			 
//			 for(int i=0;i<smessageList.size();i++) {
//				model_1.setValueAt(false, i, 0);
//				model_1.setValueAt(i+1, i, 1);
//				model_1.setValueAt(smessageList.get(i).getMemSendId(), i, 2);
//				model_1.setValueAt(smessageList.get(i).getMessageContent(), i, 3);
//				model_1.setValueAt(smessageList.get(i).getMemReceiveId(), i, 4);
//				model_1.setValueAt(smessageList.get(i).getMessageSendDate(), i, 5);
//				 }
//	 }
	
	//받은 쪽지함 페이지 넘버 눌렀을 때 번호가 진하게 변하게 하는 메서드
	public void setFont() {
		  prev.setFont(new Font("굴림", Font.PLAIN, 15));
		  p1.setFont(new Font("굴림", Font.PLAIN, 15));
		  p2.setFont(new Font("굴림", Font.PLAIN, 15));
		  p3.setFont(new Font("굴림", Font.PLAIN, 15));
		  p4.setFont(new Font("굴림", Font.PLAIN, 15));
		  p5.setFont(new Font("굴림", Font.PLAIN, 15));
		  p6.setFont(new Font("굴림", Font.PLAIN, 15));
		  p7.setFont(new Font("굴림", Font.PLAIN, 15));
		  p8.setFont(new Font("굴림", Font.PLAIN, 15));
		  p9.setFont(new Font("굴림", Font.PLAIN, 15));
		  p10.setFont(new Font("굴림", Font.PLAIN, 15));
		  next.setFont(new Font("굴림", Font.PLAIN, 15));
	}
	
	//보낸 쪽지함 페이지 넘버 눌렀을 때 번호가 진하게 변하는 메서드
	public void setFont2() {
		  pprev.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp1.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp2.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp3.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp4.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp5.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp6.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp7.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp8.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp9.setFont(new Font("굴림", Font.PLAIN, 15));
		  pp10.setFont(new Font("굴림", Font.PLAIN, 15));
		  nnext.setFont(new Font("굴림", Font.PLAIN, 15));
	}
	 
	//페이지 넘버를 받아서 받은쪽지 리스트를 초기화해줌
	void receiveMListPaging (int pageNo) {	
		for(int i=0;i<6;i++) {	//우선 테이블 초기화 하고
			model.setValueAt("", i, 0);
			model.setValueAt("", i, 1);
			model.setValueAt("", i, 2);
			model.setValueAt("", i, 3);
			model.setValueAt("", i, 4);
			model.setValueAt("", i, 5);
		}
			
	//받은쪽지 리스트 테이블에 초기화
	rmessageList = mdao.getReceivedM(pageNo+(10*pageCnt), memId);
		for(int i=0;i<rmessageList.size();i++) {
			model.setValueAt(false, i, 0);
			model.setValueAt(rmessageList.get(i).getMessageNo(), i, 1);
			model.setValueAt(rmessageList.get(i).getMemSendId(), i, 2);
			model.setValueAt(rmessageList.get(i).getMessageContent(), i, 3);
			model.setValueAt(rmessageList.get(i).getMemReceiveId(), i, 4);
			model.setValueAt(rmessageList.get(i).getMessageSendDate(), i, 5);
		}
	}
				
		//다음 또는 이전 페이지를 누를때 pageCnt를 증감시켜서 이에 맞춰 페이지 라벨을 초기화해줌
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
		
		//페이지 넘버를 받아서 보낸쪽지 리스트를 초기화해줌
		void sendMListPaging (int pageNo) {	
			for(int i=0;i<6;i++) {	//우선 테이블 초기화 하고
				model_1.setValueAt("", i, 0);
				model_1.setValueAt("", i, 1);
				model_1.setValueAt("", i, 2);
				model_1.setValueAt("", i, 3);
				model_1.setValueAt("", i, 4);
				model_1.setValueAt("", i, 5);
			}
			
				//보낸쪽지 리스트 테이블에 초기화
			smessageList = mdao.getSendM(pageNo+(10*pageCnt2), memId);
				for(int i=0;i<smessageList.size();i++) {
					model_1.setValueAt(false, i, 0);
					model_1.setValueAt(smessageList.get(i).getMessageNo(), i, 1);
					model_1.setValueAt(smessageList.get(i).getMemSendId(), i, 2);
					model_1.setValueAt(smessageList.get(i).getMessageContent(), i, 3);
					model_1.setValueAt(smessageList.get(i).getMemReceiveId(), i, 4);
					model_1.setValueAt(smessageList.get(i).getMessageSendDate(), i, 5);
				}
			
		}
		
		
		//다음 또는 이전 페이지를 누를때 pageCnt를 증감시켜서 이에 맞춰 페이지 라벨을 초기화해줌
		void pageNoSet2 () {
			pp1.setText("" + (1+10*pageCnt2));
			pp2.setText("" + (2+10*pageCnt2));
			pp3.setText("" + (3+10*pageCnt2));
			pp4.setText("" + (4+10*pageCnt2));
			pp5.setText("" + (5+10*pageCnt2));
			pp6.setText("" + (6+10*pageCnt2));
			pp7.setText("" + (7+10*pageCnt2));
			pp8.setText("" + (8+10*pageCnt2));
			pp9.setText("" + (9+10*pageCnt2));
			pp10.setText("" + (10+10*pageCnt2));
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

	public void mouseReleased(MouseEvent e) {

		JLabel lb = (JLabel) e.getSource();
		if(lb == p1) {//1페이지 눌렀을 때
			setFont();
			p1.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (1);
			
		} else if(lb == p2) {//2페이지 눌렀을 때
			setFont();
			p2.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (2);
			
		} else if(lb == p3) {//3페이지 눌렀을 때
			setFont();
			p3.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (3);
			
		} else if(lb == p4) {//4페이지 눌렀을 때
			setFont();
			p4.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (4);
			
		} else if(lb == p5) {//5페이지 눌렀을 때
			setFont();
			p5.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (5);
			
		} else if(lb == p6) {//6페이지 눌렀을 때
			setFont();
			p6.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (6);
			
		} else if(lb == p7) {//7페이지 눌렀을 때
			setFont();
			p7.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (7);
			
		} else if(lb == p8) {//8페이지 눌렀을 때
			setFont();
			p8.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (8);
			
		} else if(lb == p9) {//9페이지 눌렀을 때
			setFont();
			p9.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (9);
			
		} else if(lb == p10) {//10페이지 눌렀을 때
			setFont();
			p10.setFont(new Font("굴림", Font.BOLD, 25));
			receiveMListPaging (10);
			
		} else if(lb == next) {//다음페이지 눌렀을 때
			pageCnt++;
			//페이지 번호 변경
			pageNoSet ();
			//유저리스트 초기화해줌
			receiveMListPaging (1);
			
		} else if(lb == prev) {//이전페이지 눌렀을 때
			if(pageCnt>0) {	//1~10페이지에서는 이전페이지 없음
				pageCnt--;
				//페이지 번호 변경
				pageNoSet ();
				//유저리스트 초기화해줌
				receiveMListPaging (1);
			}

		}
		
		if(lb == pp1) {//1페이지 눌렀을 때
			setFont2();
			pp1.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (1);
			
		} else if(lb == pp2) {//2페이지 눌렀을 때
			setFont2();
			pp2.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (2);
			
		} else if(lb == pp3) {//3페이지 눌렀을 때
			setFont2();
			pp3.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (3);
			
		} else if(lb == pp4) {//4페이지 눌렀을 때
			setFont2();
			pp4.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (4);
			
		} else if(lb == pp5) {//5페이지 눌렀을 때
			setFont2();
			pp5.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (5);
			
		} else if(lb == pp6) {//6페이지 눌렀을 때
			setFont2();
			pp6.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (6);
			
		} else if(lb == pp7) {//7페이지 눌렀을 때
			setFont2();
			pp7.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (7);
			
		} else if(lb == pp8) {//8페이지 눌렀을 때
			setFont2();
			pp8.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (8);
			
		} else if(lb == pp9) {//9페이지 눌렀을 때
			setFont2();
			pp9.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (9);
			
		} else if(lb == pp10) {//10페이지 눌렀을 때
			setFont2();
			pp10.setFont(new Font("굴림", Font.BOLD, 25));
			sendMListPaging (10);
			
		} else if(lb == nnext) {//다음페이지 눌렀을 때
			pageCnt2++;
			//페이지 번호 변경
			pageNoSet2 ();
			//유저리스트 초기화해줌
			sendMListPaging (1);
			
		} else if(lb == pprev) {//이전페이지 눌렀을 때
			if(pageCnt2>0) {	//1~10페이지에서는 이전페이지 없음
				pageCnt2--;
				//페이지 번호 변경
				pageNoSet2 ();
				//유저리스트 초기화해줌
				sendMListPaging (1);
			}

		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn == sendBtn) {
			send();
			sendMListPaging(1);
		    receiveMListPaging(1);
			
		} else if (btn == delBtn) {
			delete();
			delete2();
			sendMListPaging(1);
		    receiveMListPaging(1);
//			resetTb();
		}
	}
	
	//쪽지보내기 메서드
	public void send() {
		mvo = new MessageVO();
		
		System.out.println("-------------------- [쪽지작성] --------------------");
		while (true) {
		System.out.print("  > 받는 사람: ");
		String id = scan.nextLine();
		if (mdao.isExist(id, 1)) { //받는사람이 존재한다면
			mvo.setMemReceiveId(id); //수신자에 아이디 넣기
			if(id.equals(UserMain.mvo.getMemId())) { //내가 나한테 보내는거라면
				System.out.println("자신에게 쪽지를 보냅니다.");
				System.out.print("  > 쪽지 내용: ");
				String cont = scan.nextLine();
				mvo.setMessageContent(cont);
				System.out.println();
				System.out.println("  정말 전송하시겠습니까?");
				System.out.print("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");
				int choice = scan.nextInt();
				scan.nextLine();
				switch (choice) {
				case 1: mvo.setMemSendId(memId);  mvo.setMessageSendDate(null); mvo.setMessageNo(1); mdao.send(mvo);	
				System.out.println();
				System.out.println("  전송이 완료되었습니다.");
				break; 
				case 2: System.out.println("  전송 취소되었습니다.");	 break; 	
				default: System.out.println("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");  scan.nextInt(); break;
				}
			} else { //내 아이디가 아님
			System.out.print("  > 쪽지 내용: ");
			String cont = scan.nextLine();
			mvo.setMessageContent(cont);
			System.out.println();
			System.out.println("  정말 전송하시겠습니까?");
			System.out.print("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");
			int choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 1: mvo.setMemSendId(memId);  mvo.setMessageSendDate(null); mvo.setMessageNo(1); 
			if(mdao.send(mvo)) {	
			System.out.println();
			System.out.println("  전송이 완료되었습니다.");
			}
			else 
				System.out.println("전송을 실패하였습니다.");
			break; 
			case 2: System.out.println("  전송 취소되었습니다.");	break; 	
			default: System.out.println("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");  scan.nextInt(); break;
			}
			}
			break;
		} else { //받는사람이 존재하지 않는다면 
				System.out.println("  존재하지 않는 아이디입니다. 다시 입력해주세요.");
		}
		} 
		

	}
	
	
	//받은 쪽지 삭제하기 메서드
	public void delete() {
	    
		mdao = new MessageDAO();

	      List<Integer> delList = new ArrayList<Integer>();

	         for (int i = 0 ; i<rmessageList.size() ; i++) {
	            if ((boolean)model.getValueAt(i, 0) == true) {
	               delList.add((int)model.getValueAt(i, 1));
	            }
	         }

	      
	      for(int i=0 ; i<delList.size(); i++) {
	    	  boolean result2 = mdao.delete(delList.get(i));
	         if(result2 == true) {
	            System.out.println("쪽지번호: " + delList.get(i) + "   쪽지 삭제가 완료되었습니다.");
	         } else { 
	            System.out.println("쪽지번호: " + delList.get(i) + "   쪽지 삭제를 실패하였습니다.");
	         }
	      }
	      

	   }
	
	
	//보낸 쪽지 삭제하기 메서드
	public void delete2() {
	    
		  mdao = new MessageDAO();	
	      
	      List<Integer> delList = new ArrayList<Integer>();      

	         for (int i = 0 ; i<smessageList.size() ; i++) {
	            if ((boolean)model_1.getValueAt(i, 0) == true) {
	               delList.add((int)model_1.getValueAt(i, 1));
	            }
	         }

	      
	      for(int i=0 ; i<delList.size(); i++) {
	    	  boolean result2 = mdao.delete(delList.get(i));
	         if(result2 == true) {
	            System.out.println("쪽지번호: " + delList.get(i) + "   쪽지 삭제가 완료되었습니다.");
	         } else { 
	            System.out.println("쪽지번호: " + delList.get(i) + "   쪽지 삭제를 실패하였습니다.");
	         }
	      }
	   }
	
	public JPanel getJPanel() {
		return messageBoxPane;
	}
}

