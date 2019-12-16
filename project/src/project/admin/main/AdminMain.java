package project.admin.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.org.apache.xpath.internal.functions.FuncNamespace;

import project.VO.MemberVO;
import project.admin.complBoardMng.ComplBdMng;
import project.admin.complBoardMng.ComplPost;
import project.admin.itemManager.ItemManager;
import project.admin.memberInfo.MemberInfo;
import project.admin.qnaBoardMng.QnaBdMng;
import project.publicMain.PublicMain;
import project.user.complBoard.UserComplBoard;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;


//위계 : 프레임(창)-최상위패널(contentPane:버튼과 아이디, 로고가있음)-기능(게시판 등)패널
public class AdminMain extends JFrame {
	

	public static MemberVO mvo;
	
	public  JPanel functionPanel;
	JPanel contentPane; // 최상위 패널
	JPanel complBoardPane;// 건의 게시판 관리 JPanel
	JPanel ItemPane; // 아이템관리 JPanel
	JPanel memberInfoPane; // 계정관리 JPanel
	JPanel qnaBoardPane; // 질답게시판 관리 JPanel;
	// 버튼에 따라 바뀌는부분

	
	public AdminMain() { 
		mvo=new MemberVO();
		mvo.setMemId("admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//창 크기 조절을 위한 화면 해상도 정보 얻기
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		  System.out.println("해상도 : " + res.width + " x " + res.height);
		
		//프레임에 최상위패널 추가.(java잡아 로고와 버튼들이 출력될 패널임),레이아웃 null(사용자가 자유롭게 사용가능)
		contentPane = new JPanel();
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		
		//창은 화면 해상도 기준. 최상위패널도 같음. 
		setBounds(res.width/8,0,(res.width*3)/4,(res.height*15)/16); //프레임(창)크기
		//최상위패널 크기조절
		getContentPane().setBounds(res.width/8,0,(res.width*3)/4,(res.height*15)/16);
		
		//자바잡아 로고
		JLabel programNameLabel = new JLabel("Java 잡아 !");
		programNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		programNameLabel.setBounds(594,12,352,86);
		contentPane.add(programNameLabel);
		
		
		functionPanel=new JPanel();
		functionPanel.setBounds(14, 159, 1394, 794);
		functionPanel.setLayout(null);
		contentPane.add(functionPanel);
		
		//##############버튼에 따라 바뀔 부분##################
		//관리자 질답으로 바꾸기
		QnaBdMng qbm=new QnaBdMng();
		functionPanel.removeAll();
		functionPanel.add(qbm.getJPanel());
		functionPanel.revalidate();
		functionPanel.repaint();
		
		
		
		JLabel userNameLabel = new JLabel(mvo.getMemId()+"님!");
		userNameLabel.setFont(new Font("굴림", Font.PLAIN, 26));
		userNameLabel.setBounds(41, 105, 181, 42);
		contentPane.add(userNameLabel);
		
		JButton userAdminBtn = new JButton("계정관리");
		userAdminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberInfo mi=new MemberInfo();
				functionPanel.removeAll();
				functionPanel.add(mi.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
		userAdminBtn.setBounds(604, 94, 147, 53);
		contentPane.add(userAdminBtn);
		
		JButton chattingAdminBtn= new JButton("채팅관리");
		chattingAdminBtn.setBounds(775, 94, 147, 53);
		contentPane.add(chattingAdminBtn);
		
		JButton qnaBoardAdminBtn = new JButton("질답게시판관리");
		qnaBoardAdminBtn.setBounds(939, 94, 147, 53);
		contentPane.add(qnaBoardAdminBtn);
		qnaBoardAdminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QnaBdMng qbm=new QnaBdMng();
				functionPanel.removeAll();
				functionPanel.add(qbm.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
		
	
		
		JButton complBoardAdmin = new JButton("건의게시판관리");
		complBoardAdmin.setBounds(1100, 94, 147, 53);
		contentPane.add(complBoardAdmin);
		complBoardAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComplBdMng cbm=new ComplBdMng();
				functionPanel.removeAll();
				functionPanel.add(cbm.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
		
		JButton itemStoreAdminBtn = new JButton("아이템상점관리");
		itemStoreAdminBtn.addMouseListener(new MouseListener() {
			
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
				ItemManager im = new ItemManager();
				PublicMain.am.changePanel(im.getJPanel());
			}
		});
		itemStoreAdminBtn.setBounds(1261, 94, 147, 53);
		contentPane.add(itemStoreAdminBtn);
		setVisible(true);
	}
	
	
	public void changePanel(JPanel jp){
		functionPanel.removeAll();
		functionPanel.add(jp);
		functionPanel.revalidate();
		functionPanel.repaint();
		
	}
	

	public void drawMain() {
		
	}
	public void setUserId(String id) {//MemberVO로 바꾸기
		
	}
	
//	public static void main(String[] args) {
//		new UserMain();
//	}
	
}
