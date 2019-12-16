package project.admin.memberInfo;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import project.DAO.*;
import project.VO.ComplBoardVO;
import project.VO.ComplComtVO;
import project.VO.MemberVO;
import project.VO.QnaBoardVO;
import project.admin.complBoardMng.ComplPost;
import project.admin.main.AdminMain;
import project.admin.qnaBoardMng.QnaPost;
import project.publicMain.PublicMain;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

public class MemberInfoManager implements ActionListener, MouseListener{
	JPanel memInfoMngPane, userInfoPane, pntPane, postAndRepl, complPageNoPane, qnaPageNoPane, commentPageNoPane;
	JButton editBtn, deleteBtn, bdPntBtn, ctPntBtn;
	JTable qnaBdTb, complBdTb, commentTb;
	JLabel qnaPgLb, complPgLb, commentPgLb;
	JTextField pwTf, nameTf, birthTf, emailTf;
	JLabel idLb, pwLb, nameLb, birthLb, emailLb, pointLb, likeLb, userIdLb, userPointLb, userLikeLb;
	JLabel complprevLb, complP1Lb, complP2Lb, complP3Lb, complP4Lb, complP5Lb,complNextLb;
	JLabel qnaprevLb, qnaP1Lb, qnaP2Lb, qnaP3Lb, qnaP4Lb, qnaP5Lb, qnaNextLb;
	JLabel commentprevLb, commentP1Lb, commentP2Lb, commentP3Lb, commentP4Lb, commentP5Lb, commentNextLb;
	JLabel userPntCntLb, userBdPntStateLb, userCtPntStateLb, userBdPntEndDateLb, userCtPntEndDateLb;
	
	DefaultTableModel complTbModel, qnaTbModel, commentTbModel;
	Object []columnName = {"번호", "제목", "아이디", "좋아요", "조회 수", "작성일시"};
	Object []commentcolumName = {"게시글번호", "아이디", "댓글 내용", "좋아요", "작성일시"};
	List<ComplBoardVO> complBdList;
	List<QnaBoardVO> qnaBdList;
//	List<ComplComtVO> commentList;
	
	
	int pageCnt = 0;
	int complOrQna;
	
	Scanner scan;
	
	MemberVO mvo;
	MemberDAO mdao;
	ComplBoardDAO cbdao;
	ComplComtDAO ccdao;
	QnaBoardDAO qbdao;
	QnaBoardComtDAO qbcdao;
	
	String userId;	//임의로 사용하는 유저 아이디
	
	
	MemberInfoManager (String Id){
		userId = Id;
		scan = new Scanner(System.in);
		
		mvo = new MemberVO();
		mdao = new MemberDAO();
		cbdao = new ComplBoardDAO();
		ccdao = new ComplComtDAO();
		qbdao = new QnaBoardDAO();
		qbcdao = new QnaBoardComtDAO();

		memInfoMngPane = new JPanel();
		//패널 사이즈, 위치 설정
		memInfoMngPane.setBounds(44, 34, 1394, 794);
		//프레임에 패널 붙이기
		memInfoMngPane.setLayout(null);
		
		//회원정보 패널 선언
		userInfoPane = new JPanel();
		userInfoPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		//패널 사이즈, 위치 설정
		userInfoPane.setBounds(54, 133, 339, 352);
		//메인 패널에 패널 붙이기
		memInfoMngPane.add(userInfoPane);
		userInfoPane.setLayout(null);
		
		//제한 정보 패널 선언
		pntPane = new JPanel();
		pntPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		//패널 사이즈, 위치 설정
		pntPane.setBounds(54, 559, 339, 159);
		//메인 패널에 패널 붙이기
		memInfoMngPane.add(pntPane);
		pntPane.setLayout(null);
		
		
		
		//작성한 글 패널 선언
		postAndRepl = new JPanel();
		postAndRepl.setBorder(new LineBorder(new Color(0, 0, 0)));
		//패널 사이즈, 위치 설정
		postAndRepl.setBounds(463, 133, 874, 605);
		//메인 패널에 패널 붙이기
		memInfoMngPane.add(postAndRepl);
		postAndRepl.setLayout(null);
		
		
		
		
		idLb = new JLabel("아이디");
		idLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		idLb.setBounds(12, 10, 109, 39);
		userInfoPane.add(idLb);
		
		pwLb = new JLabel("비밀번호");
		pwLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		pwLb.setBounds(12, 59, 109, 39);
		userInfoPane.add(pwLb);
		
		nameLb = new JLabel("이름");
		nameLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		nameLb.setBounds(12, 108, 109, 39);
		userInfoPane.add(nameLb);
		
		birthLb = new JLabel("생년월일");
		birthLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		birthLb.setBounds(12, 157, 109, 39);
		userInfoPane.add(birthLb);
		
		emailLb = new JLabel("이메일");
		emailLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		emailLb.setBounds(12, 206, 109, 39);
		userInfoPane.add(emailLb);
		
		pointLb = new JLabel("보유 포인트");
		pointLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		pointLb.setBounds(12, 255, 109, 39);
		userInfoPane.add(pointLb);
		
		likeLb = new JLabel("좋아요 개수");
		likeLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		likeLb.setBounds(12, 304, 109, 39);
		userInfoPane.add(likeLb);
		
		pwTf = new JTextField();
		pwTf.setColumns(10);
		pwTf.setBounds(133, 59, 194, 39);
		userInfoPane.add(pwTf);
		
		nameTf = new JTextField();
		nameTf.setColumns(10);
		nameTf.setBounds(133, 108, 194, 39);
		userInfoPane.add(nameTf);
		
		birthTf = new JTextField();
		birthTf.setColumns(10);
		birthTf.setBounds(133, 157, 194, 39);
		userInfoPane.add(birthTf);
		
		emailTf = new JTextField();
		emailTf.setColumns(10);
		emailTf.setBounds(133, 206, 194, 39);
		userInfoPane.add(emailTf);
		
		//유저의 아이디 표시 라벨
		userIdLb = new JLabel("아이디");
		userIdLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userIdLb.setBounds(133, 10, 194, 39);
		userInfoPane.add(userIdLb);
				
		//유저의 보유 포인트 표시 라벨
		userPointLb = new JLabel("보유 포인트");
		userPointLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userPointLb.setBounds(133, 255, 150, 39);
		userInfoPane.add(userPointLb);
		
		JLabel lblPt = new JLabel("Pt");
		lblPt.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblPt.setBounds(295, 255, 32, 39);
		userInfoPane.add(lblPt);
		
		//유저의 좋아요 개수 표시 라벨
		userLikeLb = new JLabel("좋아요 개수");
		userLikeLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userLikeLb.setBounds(133, 304, 194, 39);
		userInfoPane.add(userLikeLb);
		
		//건의 게시판 스크롤 패널 생성 및 설정
		JScrollPane complScrollPane = new JScrollPane();
		complScrollPane.setBounds(64, 98, 748, 113);
		postAndRepl.add(complScrollPane);
		
		//건의 게시판 테이블 모델 및 테이블 설정
		complTbModel = new DefaultTableModel(columnName,3);
		complBdTb = new JTable();
		complBdTb.setModel(complTbModel);
		complBdTb.getColumnModel().getColumn(0).setPreferredWidth(50);
		complBdTb.getColumnModel().getColumn(1).setPreferredWidth(200);
		complBdTb.getColumnModel().getColumn(2).setPreferredWidth(90);
		complBdTb.getColumnModel().getColumn(3).setPreferredWidth(50);
		complBdTb.getColumnModel().getColumn(4).setPreferredWidth(50);
		complBdTb.getColumnModel().getColumn(5).setPreferredWidth(100);
		complBdTb.setRowHeight(30);
		
		//테이블리스너
		complBdTb.addMouseListener(new MouseListener() {
			
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
				int rowIndex=complBdTb.getSelectedRow();
				if(complBdTb.getValueAt(rowIndex, 0) != "") {
					Object o;
					o = complBdTb.getValueAt(rowIndex, 0);
					int no = Integer.parseInt(o.toString());
					o = complBdTb.getValueAt(rowIndex, 1);
					String title = o.toString();
					o = complBdTb.getValueAt(rowIndex, 2);
					String id = o.toString();
					o = complBdTb.getValueAt(rowIndex, 3);
					int like = Integer.parseInt(o.toString());
					o = complBdTb.getValueAt(rowIndex, 4);
					int viewCnt = Integer.parseInt(o.toString());
					o = complBdTb.getValueAt(rowIndex, 5);
					String date = o.toString();
					ComplPost complPost = new ComplPost(no, title, id, like, viewCnt, date);
					PublicMain.am.changePanel(complPost.getJPanel());
				}
			}
		});
		
		//스크롤 패널에 붙이기
		complScrollPane.setViewportView(complBdTb);
		
		
		//질의응답 게시판 스크롤 패널 생성 및 설정
		JScrollPane qnaScrollPane = new JScrollPane();
		qnaScrollPane.setBounds(64, 360, 748, 113);
		postAndRepl.add(qnaScrollPane);
		
		//질의응답 게시판 테이블 모델 및 테이블 설정		
		qnaTbModel = new DefaultTableModel(columnName,3);
		qnaBdTb = new JTable();
		qnaBdTb.setModel(qnaTbModel);
		qnaBdTb.getColumnModel().getColumn(0).setPreferredWidth(50);
		qnaBdTb.getColumnModel().getColumn(1).setPreferredWidth(200);
		qnaBdTb.getColumnModel().getColumn(2).setPreferredWidth(90);
		qnaBdTb.getColumnModel().getColumn(3).setPreferredWidth(50);
		qnaBdTb.getColumnModel().getColumn(4).setPreferredWidth(50);
		qnaBdTb.getColumnModel().getColumn(5).setPreferredWidth(100);
		qnaBdTb.setRowHeight(30);
		qnaScrollPane.setViewportView(qnaBdTb);
		
		//테이블리스너
		qnaBdTb.addMouseListener(new MouseListener() {
			
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
				int rowIndex=qnaBdTb.getSelectedRow();
				if(qnaBdTb.getValueAt(rowIndex, 0) != "") {
					Object o;
					o = qnaBdTb.getValueAt(rowIndex, 0);
					int no = Integer.parseInt(o.toString());
					o = qnaBdTb.getValueAt(rowIndex, 1);
					String title = o.toString();
					o = qnaBdTb.getValueAt(rowIndex, 2);
					String id = o.toString();
					o = qnaBdTb.getValueAt(rowIndex, 3);
					int like = Integer.parseInt(o.toString());
					o = qnaBdTb.getValueAt(rowIndex, 4);
					int viewCnt = Integer.parseInt(o.toString());
					o = qnaBdTb.getValueAt(rowIndex, 5);
					String date = o.toString();
					QnaPost QnaPost = new QnaPost(no, title, id, like, viewCnt, date);
					PublicMain.am.changePanel(QnaPost.getJPanel());
				}
			}
			
		});
			
		
//		//댓글 스크롤 패널 생성 및 설정
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(64, 455, 748, 83);
//		postAndRepl.add(scrollPane);
//		
//		//댓글 테이블 모델 및 테이블 설정
//		commentTbModel = new DefaultTableModel(commentcolumName,2);
//		commentTb = new JTable();
//		commentTb.setModel(commentTbModel);
//		commentTb.getColumnModel().getColumn(0).setPreferredWidth(40);
//		commentTb.getColumnModel().getColumn(1).setPreferredWidth(90);
//		commentTb.getColumnModel().getColumn(2).setPreferredWidth(270);
//		commentTb.getColumnModel().getColumn(3).setPreferredWidth(30);
//		commentTb.getColumnModel().getColumn(3).setPreferredWidth(50);
//		commentTb.setRowHeight(30);
//		
//		scrollPane.setViewportView(commentTb);
		
		
		
		//내가 쓴 글의 부제 라벨들
		JLabel complBdLb = new JLabel("건의 게시판");
		complBdLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		complBdLb.setBounds(64, 49, 109, 39);
		postAndRepl.add(complBdLb);
		
		JLabel qnaBdLb = new JLabel("질의응답 게시판");
		qnaBdLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		qnaBdLb.setBounds(64, 311, 141, 39);
		postAndRepl.add(qnaBdLb);
		
//		JLabel commentLb = new JLabel("댓글 추후 업데이트 예정..");
//		commentLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
//		commentLb.setBounds(64, 406, 219, 39);
//		postAndRepl.add(commentLb);
		
		
		
		//건의게시판 페이지넘버 패널 선언
		complPageNoPane = new JPanel();
		//패널 사이즈, 위치 설정
		complPageNoPane.setBounds(319, 240, 252, 30);
		//패널 붙이기
		postAndRepl.add(complPageNoPane);
		
		//건의게시판 페이지 넘버 라벨들 선언
		complprevLb = new JLabel("<");
		complprevLb.setFont(new Font("굴림", Font.BOLD, 20));
		complP1Lb = new JLabel("1");
		complP1Lb.setFont(new Font("굴림", Font.BOLD, 20));
		complP2Lb = new JLabel("2");
		complP2Lb.setFont(new Font("굴림", Font.BOLD, 20));
		complP3Lb = new JLabel("3");
		complP3Lb.setFont(new Font("굴림", Font.BOLD, 20));
		complP4Lb = new JLabel("4");
		complP4Lb.setFont(new Font("굴림", Font.BOLD, 20));
		complP5Lb = new JLabel("5");
		complP5Lb.setFont(new Font("굴림", Font.BOLD, 20));
		complNextLb = new JLabel(">");
		complNextLb.setFont(new Font("굴림", Font.BOLD, 20));
		
		//건의게시판 페이지 목록에 리스너달기
		complprevLb.addMouseListener(this);
		complP1Lb.addMouseListener(this);
		complP2Lb.addMouseListener(this);
		complP3Lb.addMouseListener(this);
		complP4Lb.addMouseListener(this);
		complP5Lb.addMouseListener(this);
		complNextLb.addMouseListener(this);
		
		//건의 게시판 페이지 패널에 붙이기
		complPageNoPane.add(complprevLb);
		complPageNoPane.add(complP1Lb);
		complPageNoPane.add(complP2Lb);
		complPageNoPane.add(complP3Lb);
		complPageNoPane.add(complP4Lb);
		complPageNoPane.add(complP5Lb);
		complPageNoPane.add(complNextLb);
		
		
		

		//질의응답게시판 페이지넘버 패널 선언
		qnaPageNoPane = new JPanel();
		qnaPageNoPane.setBounds(319, 504, 252, 30);
		postAndRepl.add(qnaPageNoPane);
		
		
		//질의응답 페이지 넘버 라벨들 선언
		qnaprevLb = new JLabel("<");
		qnaprevLb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaP1Lb = new JLabel("1");
		qnaP1Lb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaP2Lb = new JLabel("2");
		qnaP2Lb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaP3Lb = new JLabel("3");
		qnaP3Lb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaP4Lb = new JLabel("4");
		qnaP4Lb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaP5Lb = new JLabel("5");
		qnaP5Lb.setFont(new Font("굴림", Font.BOLD, 20));
		qnaNextLb = new JLabel(">");
		qnaNextLb.setFont(new Font("굴림", Font.BOLD, 20));
		
		//질의응답게시판 페이지 목록에 리스너달기
		qnaprevLb.addMouseListener(this);
		qnaP1Lb.addMouseListener(this);
		qnaP2Lb.addMouseListener(this);
		qnaP3Lb.addMouseListener(this);
		qnaP4Lb.addMouseListener(this);
		qnaP5Lb.addMouseListener(this);
		qnaNextLb.addMouseListener(this);
		
		//질의응답 페이지 패널에 붙이기
		qnaPageNoPane.add(qnaprevLb);
		qnaPageNoPane.add(qnaP1Lb);
		qnaPageNoPane.add(qnaP2Lb);
		qnaPageNoPane.add(qnaP3Lb);
		qnaPageNoPane.add(qnaP4Lb);
		qnaPageNoPane.add(qnaP5Lb);
		qnaPageNoPane.add(qnaNextLb);
		

		
		
		
		
//		//댓글 페이지넘버 패널 선언
//		commentPageNoPane = new JPanel();
//		commentPageNoPane.setBounds(319, 548, 252, 30);
//		postAndRepl.add(commentPageNoPane);
//		
//		
//		//질의응답 페이지 넘버 라벨들 선언 및 질의응답 페이지 패널에 붙이기
//		commentprevLb = new JLabel("<");
//		commentprevLb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentprevLb);
//		
//		commentP1Lb = new JLabel("1");
//		commentP1Lb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentP1Lb);
//		
//		commentP2Lb = new JLabel("2");
//		commentP2Lb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentP2Lb);
//		
//		commentP3Lb = new JLabel("3");
//		commentP3Lb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentP3Lb);
//		
//		commentP4Lb = new JLabel("4");
//		commentP4Lb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentP4Lb);
//		
//		commentP5Lb = new JLabel("5");
//		commentP5Lb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentP5Lb);
//		
//		commentNextLb = new JLabel(">");
//		commentNextLb.setFont(new Font("굴림", Font.BOLD, 20));
//		commentPageNoPane.add(commentNextLb);
		
		
		
		//수정 완료 버튼
		editBtn = new JButton("수정완료");
		editBtn.setBounds(641, 36, 119, 39);
		memInfoMngPane.add(editBtn);
		//계정 삭제 버튼
		deleteBtn = new JButton("계정 삭제");
		deleteBtn.setBounds(800, 36, 119, 39);
		memInfoMngPane.add(deleteBtn);
		//채팅 이용 제한 버튼
		ctPntBtn = new JButton("채팅 이용 제한");
		ctPntBtn.setBounds(1140, 36, 152, 39);
		memInfoMngPane.add(ctPntBtn);
		//게시판 이용 제한 버튼
		bdPntBtn = new JButton("게시판 이용 제한");
		bdPntBtn.setBounds(955, 36, 152, 39);
		memInfoMngPane.add(bdPntBtn);
		
		//작성한 글, 회원정보 라벨
		JLabel userPostLb= new JLabel("작성한 글");
		userPostLb.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		userPostLb.setBounds(463, 80, 152, 39);
		memInfoMngPane.add(userPostLb);
		JLabel userInfoLb = new JLabel("회원정보");
		userInfoLb.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		userInfoLb.setBounds(54, 80, 152, 39);
		memInfoMngPane.add(userInfoLb);
		
		
		//제한 정보 관련 라벨
		JLabel pntCntLb = new JLabel("제한 횟수");
		pntCntLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		pntCntLb.setBounds(12, 10, 109, 39);
		pntPane.add(pntCntLb);
		
		JLabel pntStateLb = new JLabel("현재 상태");
		pntStateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		pntStateLb.setBounds(12, 59, 109, 39);
		pntPane.add(pntStateLb);
		
		JLabel pntEndDateLb = new JLabel("제한 종료일");
		pntEndDateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		pntEndDateLb.setBounds(12, 108, 109, 39);
		pntPane.add(pntEndDateLb);
		
		
		//유저의 제한 횟수 라벨
		userPntCntLb = new JLabel("제한 횟수");
		userPntCntLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userPntCntLb.setBounds(133, 10, 194, 39);
		pntPane.add(userPntCntLb);
		
		//유저의 현재 게시판 제한 상태 라벨
		userBdPntStateLb = new JLabel("현재 상태");
		userBdPntStateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userBdPntStateLb.setBounds(133, 59, 99, 39);
		pntPane.add(userBdPntStateLb);
		
		//유저의 현재 채팅 제한 상태 라벨
		userCtPntStateLb = new JLabel("현재 상태");
		userCtPntStateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userCtPntStateLb.setBounds(244, 59, 83, 39);
		pntPane.add(userCtPntStateLb);
		
		//유저의 게시판 제한 종료일 라벨
		userBdPntEndDateLb = new JLabel("제한 종료일");
		userBdPntEndDateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userBdPntEndDateLb.setBounds(133, 108, 83, 39);
		pntPane.add(userBdPntEndDateLb);
		
		//유저의 채팅 제한 종료일 라벨
		userCtPntEndDateLb = new JLabel("제한 종료일");
		userCtPntEndDateLb.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		userCtPntEndDateLb.setBounds(244, 108, 83, 39);
		pntPane.add(userCtPntEndDateLb);
		
		//계정 제한 표시 라벨
		JLabel label_15 = new JLabel("계정제한");
		label_15.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label_15.setBounds(54, 510, 152, 39);
		memInfoMngPane.add(label_15);
		
		//회원 정보 초기화
		refreshInfo();
		
		//작성 게시글 초기화
		PostNReplPaging(1);
		
		//버튼에 리스너 달기
		editBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		bdPntBtn.addActionListener(this);
		ctPntBtn.addActionListener(this);
		
		
	}
	
	
	//페이지 넘버를 받아서 작성글 리스트를 초기화해줌
	void PostNReplPaging (int pageNo) {
			//건의게시판 부분
			for(int i=0;i<3;i++) {	//우선 건의 게시판 테이블 초기화 하고
				complTbModel.setValueAt("", i, 0);
				complTbModel.setValueAt("", i, 1);
				complTbModel.setValueAt("", i, 2);
				complTbModel.setValueAt("", i, 3);
				complTbModel.setValueAt("", i, 4);
				complTbModel.setValueAt("", i, 5);
			}
			//건의 게시판 테이블에 현재 아이디가 쓴 건의 게시글 초기화
			complBdList = cbdao.getPageOfComplPostsInfo(userId, pageNo+(5*pageCnt));
			for(int i=0;i<complBdList.size();i++) {
				complTbModel.setValueAt(complBdList.get(i).getComplNo(), i, 0);
				complTbModel.setValueAt(complBdList.get(i).getComplTitle(), i, 1);
				complTbModel.setValueAt(complBdList.get(i).getComplId(), i, 2);
				complTbModel.setValueAt(complBdList.get(i).getComplLike(), i, 3);
				complTbModel.setValueAt(complBdList.get(i).getComplViewCnt(), i, 4);
				complTbModel.setValueAt(complBdList.get(i).getComplDate(), i, 5);
			}
			//질의응답 게시판 부분
			for(int i=0;i<3;i++) {	//질의응답 게시판 테이블 초기화 하고
				qnaTbModel.setValueAt("", i, 0);
				qnaTbModel.setValueAt("", i, 1);
				qnaTbModel.setValueAt("", i, 2);
				qnaTbModel.setValueAt("", i, 3);
				qnaTbModel.setValueAt("", i, 4);
				qnaTbModel.setValueAt("", i, 5);
			}
			//질의응답 게시판 테이블에 현재 아이디가 쓴 질의응답 게시글 초기화
			qnaBdList = qbdao.getPageOfQnaPostsInfo(userId, pageNo+(5*pageCnt));
			for(int i=0;i<qnaBdList.size();i++) {
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaNo(), i, 0);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaTitle(), i, 1);
				qnaTbModel.setValueAt(qnaBdList.get(i).getMemId(), i, 2);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaLike(), i, 3);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaViewcnt(), i, 4);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaDate(), i, 5);
			}
	}
	
	void PostNReplPaging (int pageNo, int complOrQna) {
		if(complOrQna == 0) {	//건의게시판
			//건의게시판 부분
			for(int i=0;i<3;i++) {	//우선 건의 게시판 테이블 초기화 하고
				complTbModel.setValueAt("", i, 0);
				complTbModel.setValueAt("", i, 1);
				complTbModel.setValueAt("", i, 2);
				complTbModel.setValueAt("", i, 3);
				complTbModel.setValueAt("", i, 4);
				complTbModel.setValueAt("", i, 5);
			}
			//건의 게시판 테이블에 현재 아이디가 쓴 건의 게시글 초기화
			complBdList = cbdao.getPageOfComplPostsInfo(userId, pageNo+(5*pageCnt));
			for(int i=0;i<complBdList.size();i++) {
				complTbModel.setValueAt(complBdList.get(i).getComplNo(), i, 0);
				complTbModel.setValueAt(complBdList.get(i).getComplTitle(), i, 1);
				complTbModel.setValueAt(complBdList.get(i).getComplId(), i, 2);
				complTbModel.setValueAt(complBdList.get(i).getComplLike(), i, 3);
				complTbModel.setValueAt(complBdList.get(i).getComplViewCnt(), i, 4);
				complTbModel.setValueAt(complBdList.get(i).getComplDate(), i, 5);
			}
		} else if(complOrQna == 1) {	//질의게시판		
			//질의응답 게시판 부분
			for(int i=0;i<3;i++) {	//질의응답 게시판 테이블 초기화 하고
				qnaTbModel.setValueAt("", i, 0);
				qnaTbModel.setValueAt("", i, 1);
				qnaTbModel.setValueAt("", i, 2);
				qnaTbModel.setValueAt("", i, 3);
				qnaTbModel.setValueAt("", i, 4);
				qnaTbModel.setValueAt("", i, 5);
			}
			//질의응답 게시판 테이블에 현재 아이디가 쓴 질의응답 게시글 초기화
			qnaBdList = qbdao.getPageOfQnaPostsInfo(userId, pageNo+(5*pageCnt));
			for(int i=0;i<qnaBdList.size();i++) {
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaNo(), i, 0);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaTitle(), i, 1);
				qnaTbModel.setValueAt(qnaBdList.get(i).getMemId(), i, 2);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaLike(), i, 3);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaViewcnt(), i, 4);
				qnaTbModel.setValueAt(qnaBdList.get(i).getQnaDate(), i, 5);
			}
		}
	}
	
	
	//페이지 라벨들을 다음 또는 이전 페이지를 누를때 pageCnt를 증감시켜서 이에 맞춰 페이지 라벨을 초기화해줌
	void pageNoSet (int complOrQna) {
		if(complOrQna == 0) {	//건의게시판 페이지 라벨 초기화
			complP1Lb.setText("" + (1+3*pageCnt));
			complP2Lb.setText("" + (2+3*pageCnt));
			complP3Lb.setText("" + (3+3*pageCnt));
			complP4Lb.setText("" + (4+3*pageCnt));
			complP5Lb.setText("" + (5+3*pageCnt));
		} else if(complOrQna == 1) {	//질의게시판 페이지 라벨 초기화
			qnaP1Lb.setText("" + (1+3*pageCnt));
			qnaP2Lb.setText("" + (2+3*pageCnt));
			qnaP3Lb.setText("" + (3+3*pageCnt));
			qnaP4Lb.setText("" + (4+3*pageCnt));
			qnaP5Lb.setText("" + (5+3*pageCnt));
		}
	}
	
	public void refreshInfo() {
		mvo = mdao.getOne(userId);
		userIdLb.setText(mvo.getMemId());
		pwTf.setText(mvo.getMemPw());
		nameTf.setText(mvo.getMemName());
		birthTf.setText(new SimpleDateFormat("yyMMdd").format(mvo.getMemBirthday()));
		emailTf.setText(mvo.getMemEmail());
		userPointLb.setText(""+mvo.getMemPoint());
		userLikeLb.setText(""+mvo.getMemLike());
		userPntCntLb.setText(""+mvo.getMemPntCnt());
		
		if(mvo.getMemBdPntTf() == 1) {
			userBdPntStateLb.setText("게시판제한");
		} else userBdPntStateLb.setText("게시판가능");
		
		if(mvo.getMemCtPntTf() == 1) {
			userCtPntStateLb.setText("채팅제한");
		} else userCtPntStateLb.setText("채팅가능");
		
		if(mvo.getMemBdPntTf() == 1) {
			userBdPntEndDateLb.setText(new SimpleDateFormat("yyMMdd").format(mvo.getMemBdPntEnddate()));
		} else userBdPntEndDateLb.setText(" ");
		
		if(mvo.getMemCtPntTf() == 1) {
			userCtPntEndDateLb.setText(new SimpleDateFormat("yyMMdd").format(mvo.getMemCtPntEnddate()));
		} else userCtPntEndDateLb.setText(" ");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == editBtn) {	//수정 버튼 눌렀을 때
			mvo = new MemberVO();
			mvo.setMemId(userIdLb.getText());
			mvo.setMemPw(pwTf.getText());
			mvo.setMemName(nameTf.getText());
			try {
				mvo.setMemBirthday(new SimpleDateFormat("yyMMdd").parse(birthTf.getText()));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			mvo.setMemEmail(emailTf.getText());
			
			if(mdao.modify(mvo)) {
				System.out.println("수정 완료!");
			} else System.out.println("수정 실패..");
			
		} else if(btn == deleteBtn) {	//삭제 버튼 눌렀을 때
			System.out.print(userIdLb.getText() + "님을 정말 삭제하시겠습니까? (y/n) : ");
			while(true) {
				String yOrN = scan.next();
				if(yOrN.equals("y") || yOrN.equals("Y")) {
					if(mdao.delete(userIdLb.getText())) {
						System.out.println("삭제 완료");
					}
					//삭제시 바로 MemberInfo로 이동@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
					MemberInfo mi = new MemberInfo();
					PublicMain.am.changePanel(mi.getJPanel());
					
					break;
				} else if(yOrN.equals("n") || yOrN.equals("N")) {
					break;
				}else System.out.print("잘못 입력하셨습니다. 다시 입력해주세요. (y/n) : ");
			}
			
		} else if(btn == bdPntBtn) {	//게시판 제한 버튼 눌렀을 때
			
			System.out.print(userIdLb.getText() + "님의 게시판 권한을 제한하시겠습니까? (y/n) : ");
			while(true) {
				String yOrN = scan.next();
				if(yOrN.equals("y") || yOrN.equals("Y")) {
					if(mdao.penalty(userIdLb.getText(), 1)) {
						System.out.println("게시판 제한 완료");
						try {
								//채팅 제한 완료시 정보 리프레시
								refreshInfo();
							} catch (NullPointerException n) {
								//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@멤버인포 화면으로 이동
								System.out.println("멤버인포로 이동!");
							}
						break;
					} else System.out.println("게시판 제한 실패");
					break;
				} else if(yOrN.equals("n") || yOrN.equals("N")) {
					break;
				}else System.out.print("잘못 입력하셨습니다. 다시 입력해주세요. (y/n) : ");
			}
			
		} else if(btn == ctPntBtn) {	//채팅 제한 버튼 눌렀을 때
			System.out.print(userIdLb.getText() + "님의 채팅 권한을 제한하시겠습니까? (y/n) : ");
			while(true) {
				String yOrN = scan.next();
				if(yOrN.equals("y") || yOrN.equals("Y")) {
					if(mdao.penalty(userIdLb.getText(), 2)) {
						System.out.println("채팅 제한 완료");
						try {
							//채팅 제한 완료시 정보 리프레시
							refreshInfo();
						} catch (NullPointerException n) {
							//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@멤버인포 화면으로 이동
							System.out.println("멤버인포로 이동!");
						}
						break;
					} else System.out.println("채팅 제한 실패");
					break;
				} else if(yOrN.equals("n") || yOrN.equals("N")) {
					break;
				}else System.out.print("잘못 입력하셨습니다. 다시 입력해주세요. (y/n) : ");
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
			//질의응답게시판
		if(lb == qnaP1Lb) {//1페이지 눌렀을 때
			PostNReplPaging (1,1);
			
		} else if(lb == qnaP2Lb) {//2페이지 눌렀을 때
			PostNReplPaging (2,1);
			
		} else if(lb == qnaP3Lb) {//3페이지 눌렀을 때
			PostNReplPaging (3,1);
			
		} else if(lb == qnaP4Lb) {//4페이지 눌렀을 때
			PostNReplPaging (4,1);
			
		} else if(lb == qnaP5Lb) {//5페이지 눌렀을 때
			PostNReplPaging (5,1);
			
		}  else if(lb == qnaNextLb) {//다음페이지 눌렀을 때
			pageCnt++;
			//페이지 번호 변경
			pageNoSet (1);
			//유저리스트 초기화해줌
			PostNReplPaging (1,1);
			
		} else if(lb == qnaprevLb) {//이전페이지 눌렀을 때
			if(pageCnt>0) {	//1~10페이지에서는 이전페이지 없음
				pageCnt--;
				//페이지 번호 변경
				pageNoSet (1);
				//유저리스트 초기화해줌
				PostNReplPaging (1,1);
			}
			
			//건의게시판
		} else if(lb == complP1Lb) {//1페이지 눌렀을 때
			PostNReplPaging (1,0);
			
		} else if(lb == complP2Lb) {//2페이지 눌렀을 때
			PostNReplPaging (2,0);
			
		} else if(lb == complP3Lb) {//3페이지 눌렀을 때
			PostNReplPaging (3,0);
			
		} else if(lb == complP4Lb) {//4페이지 눌렀을 때
			PostNReplPaging (4,0);
			
		} else if(lb == complP5Lb) {//5페이지 눌렀을 때
			PostNReplPaging (5,0);
			
		}  else if(lb == complNextLb) {//다음페이지 눌렀을 때
			pageCnt++;
			//페이지 번호 변경
			pageNoSet (0);
			//유저리스트 초기화해줌
			PostNReplPaging (1,0);
			
		} else if(lb == complprevLb) {//이전페이지 눌렀을 때
			if(pageCnt>0) {	//1~10페이지에서는 이전페이지 없음
				pageCnt--;
				//페이지 번호 변경
				pageNoSet (0);
				//유저리스트 초기화해줌
				PostNReplPaging (1,0);
			}
			
			
		}
	}
	
	public JPanel getJPanel() {
		return memInfoMngPane;
	}
	
}
