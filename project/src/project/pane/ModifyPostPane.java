package project.pane;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import project.DAO.ComplBoardDAO;
import project.DAO.QnaBoardDAO;
import project.admin.complBoardMng.ComplBdMng;
import project.admin.qnaBoardMng.QnaBdMng;
import project.publicMain.PublicMain;
import project.user.complBoard.UserComplBoard;
import project.user.qnaBoard.UserQnaBoard;

import java.awt.Color;




public class ModifyPostPane{
	
	
	private JTextField titleArea;
	private JTextArea contentArea;
	JPanel panel;                             
	public ModifyPostPane(int boardKind,int no,String title,String content,boolean isAdmin) {//boardKind:질답인지/건의인지,글번호
		panel = new JPanel();						//0이 일반 게시판 1이 건의게시판
		panel.setBounds(0,0, 1394, 794);
		panel.setLayout(null);

		
		contentArea = new JTextArea();
		contentArea.setBounds(276, 74, 860, 660);
		contentArea.setText(content);
		
		
		
		panel.add(contentArea);
		contentArea.setColumns(10);
		JScrollPane jScollPane = new JScrollPane(contentArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScollPane.setBounds(276, 74, 860, 660);
		panel.add(jScollPane);
		
		titleArea = new JTextField(30);
		titleArea.setText(title);
		titleArea.setBounds(276, 27, 860, 37);
		panel.add(titleArea);
		

		JButton SaveBtn = new JButton("수정완료");
		SaveBtn.setBounds(582, 744, 105, 27);
		panel.add(SaveBtn);
		SaveBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
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
				if(boardKind==1&&!isAdmin) { //지금수정하는 곳이 건의 게시판이고 관리자아님
					ComplBoardDAO ucbDAO = new ComplBoardDAO();
					if (ucbDAO.modifyComplPost(no, titleArea.getText(), contentArea.getText())) {
						JOptionPane.showMessageDialog(null, "글 수정이 완료");
						UserComplBoard ucb = new UserComplBoard();
						PublicMain.um.changePanel(ucb.getJPanel());
					} else {
						JOptionPane.showMessageDialog(null, "글 수정 실패!");
					}
				}else if(boardKind==1&&isAdmin) { // 지금 수정하는곳이 건의게시판이고 관리자임
					ComplBoardDAO ucbDAO = new ComplBoardDAO();
					if (ucbDAO.modifyComplPost(no, titleArea.getText(), contentArea.getText())) {
						JOptionPane.showMessageDialog(null, "글 수정이 완료");
						ComplBdMng cbd = new ComplBdMng();
						PublicMain.am.changePanel(cbd.getJPanel());
					} else {
						JOptionPane.showMessageDialog(null, "글 수정 실패!");
					}
				}else if(boardKind==0&&!isAdmin) { //지금 수정하는곳이 질답게시판이고 관리자아님
					QnaBoardDAO qbDAO= new QnaBoardDAO();
					if (qbDAO.updateQnaPost(no, titleArea.getText(), contentArea.getText())) {
						JOptionPane.showMessageDialog(null, "글 수정이 완료");
						UserQnaBoard uqb = new UserQnaBoard();
						PublicMain.um.changePanel(uqb.getJPanel());
					} else {
						JOptionPane.showMessageDialog(null, "글 수정 실패!");
					}
				}else if(boardKind==0&&isAdmin) { //지금 수정하는 곳이 질답게시판이고 관리자임
					QnaBoardDAO qbDAO= new QnaBoardDAO();
					if (qbDAO.updateQnaPost(no, titleArea.getText(), contentArea.getText())) {
						JOptionPane.showMessageDialog(null, "글 수정이 완료");
						QnaBdMng qbm = new QnaBdMng();
						PublicMain.am.changePanel(qbm.getJPanel());
					} else {
						JOptionPane.showMessageDialog(null, "글 수정 실패!");
					}
				}
				

			}//mouseClicked end
		});
		
		
		

		JButton cancelBtn = new JButton("취소");
		cancelBtn.setBounds(710, 744, 105, 27);
		panel.add(cancelBtn);
		cancelBtn.addMouseListener(new MouseListener() {
			
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
				 JOptionPane.showMessageDialog(null, "글 수정을 취소합니다.");
				    if(boardKind==1&&isAdmin==true) { //건의게시판수정이었으니까 건의게시판으로 돌아감
				    ComplBdMng cbmng=new ComplBdMng();
				    PublicMain.am.changePanel(cbmng.getJPanel());
				    }else if(boardKind==1&&isAdmin==false){ //건의 사용자
				     UserComplBoard usercomb=new UserComplBoard();
				     PublicMain.um.changePanel(usercomb.getJPanel());
				    }else if(boardKind==0&&isAdmin==true){ //질답 관리자
				     QnaBdMng qnabdmng=new QnaBdMng();
				     PublicMain.am.changePanel(qnabdmng.getJPanel());
				    }else if(boardKind==0&&isAdmin==false) {//질답 사용자
				     UserQnaBoard uqb=new UserQnaBoard();
				     PublicMain.um.changePanel(uqb.getJPanel());
				    } 
			}
		});
	}

	public JPanel getJPanel() {
		return panel;
	}
}
