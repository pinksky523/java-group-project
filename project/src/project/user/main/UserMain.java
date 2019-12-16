package project.user.main;

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
import project.publicMain.PublicMain;
import project.server.MultiServer;
import project.user.chat.MultiClient;
import project.user.complBoard.UserComplBoard;
import project.user.itemshop.ItemShop;
import project.user.mypage.Mypage;
import project.user.qnaBoard.UserQnaBoard;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;


//위계 : 프레임(창)-최상위패널(contentPane:버튼과 아이디, 로고가있음)-기능(게시판 등)패널
public class UserMain extends JFrame {
	

	public static MemberVO mvo;
	
	public  JPanel functionPanel;
	JPanel contentPane; // 최상위 패널
	JPanel complBoardPane;// 건의 게시판 관리 JPanel
	JPanel ItemPane; // 아이템관리 JPanel
	JPanel memberInfoPane; // 계정관리 JPanel
	JPanel qnaBoardPane; // 질답게시판 관리 JPanel;
	// 버튼에 따라 바뀌는부분
	boolean isRunning=false;
	
	public UserMain(MemberVO mvoinput) { 
		//로그인하면 채팅서버 돌리기
		MultiServer ms=new MultiServer();
		ms.start();
		mvo = mvoinput;
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
		//사용자 질답으로 바꾸기
		UserQnaBoard uqb=new UserQnaBoard();
		functionPanel.removeAll();
		functionPanel.add(uqb.getJPanel());
		functionPanel.revalidate();
		functionPanel.repaint();
		
		
		
		JLabel userNameLabel = new JLabel(mvo.getMemId()+"님!");
		userNameLabel.setFont(new Font("굴림", Font.PLAIN, 26));
		userNameLabel.setBounds(41, 105, 181, 42);
		contentPane.add(userNameLabel);
		
		JButton myPageBtn = new JButton("마이페이지");
		myPageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mypage mp =new Mypage(mvo);
				functionPanel.removeAll();
				functionPanel.add(mp.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
		myPageBtn.setBounds(604, 94, 147, 53);
		contentPane.add(myPageBtn);
		
		JButton chattingBtn= new JButton("채팅");
		chattingBtn.addMouseListener(new MouseListener() {
			
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
				if(mvo.getMemCtPntTf()==1){
					String date=new String(new SimpleDateFormat("yyMMdd").format(mvo.getMemCtPntEnddate()));
					JOptionPane.showMessageDialog(null,"채팅사용 제한상태입니다.\n"
							+ "제한은 "+date+"에 종료됩니다.");
				} else{
					Runnable r = new MultiClient();
					Thread t = new Thread(r);
					t.start();
				}
				
				
			}
		});
		chattingBtn.setBounds(775, 94, 147, 53);
		contentPane.add(chattingBtn);
		
		JButton qnaBoardBtn = new JButton("질답게시판");
		qnaBoardBtn.setBounds(939, 94, 147, 53);
		contentPane.add(qnaBoardBtn);
		qnaBoardBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserQnaBoard uqb=new UserQnaBoard();
				functionPanel.removeAll();
				functionPanel.add(uqb.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
	
		
		JButton complBoardBtn = new JButton("건의게시판");
		complBoardBtn.setBounds(1100, 94, 147, 53);
		contentPane.add(complBoardBtn);
		complBoardBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserComplBoard ucb=new UserComplBoard();
				functionPanel.removeAll();
				functionPanel.add(ucb.getJPanel());
				functionPanel.revalidate();
				functionPanel.repaint();
			}
		});
		
		JButton itemStoreBtn = new JButton("아이템상점");
		itemStoreBtn.addMouseListener(new MouseListener() {
			
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
				ItemShop is = new ItemShop();
				PublicMain.um.changePanel(is.getJPanel());
			}
		});
		itemStoreBtn.setBounds(1261, 94, 147, 53);
		contentPane.add(itemStoreBtn);
		setVisible(true);
	}
	
	
	public void changePanel(JPanel jp){
		functionPanel.removeAll();
		functionPanel.add(jp);
		functionPanel.revalidate();
		functionPanel.repaint();
		
	}
	


}
