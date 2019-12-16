package project.admin.complBoardMng;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import project.DAO.ComplBoardDAO;
import project.DAO.ComplComtDAO;
import project.DAO.MessageDAO;
import project.VO.ComplBoardVO;
import project.VO.ComplComtVO;
import project.VO.MessageVO;
import project.admin.main.AdminMain;
//import project.pane.CustomCmtPane;
import project.pane.ModifyPostPane;
import project.publicMain.PublicMain;
import project.user.main.UserMain;
import project.user.message.MessageBox;

import java.awt.Font;

public class ComplPost implements MouseListener{
   JPanel contentPane;
   JPanel qnaComtsPane; //질문창 패널
   JPanel pagePanel;
   JLabel prev,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,next;
   ComplBoardDAO cbDAO;
   ComplBoardVO cbVO;
   MessageVO mvo;
   MessageDAO mdao;
   Scanner scan;
   private JLabel lblNewLabel;
   JScrollPane ComtsjScollPane;
   int no=0;//글번호
   int pageCnt=0;
   public ComplPost(int no,String title,String id,int like,int viewCnt,String date)   {
	   scan = new Scanner(System.in);
	   mdao = new MessageDAO();
	   mvo = new MessageVO();
	   ComplBoardDAO cbDAO =new ComplBoardDAO();
      if(cbDAO.updateViewCnt(no)) {} //조회수 올리기
      
      
      this.no=no; //글번호 전달
      //글번호","제목","아이디","좋아요","조회 수","일시";
      contentPane=new JPanel();
      contentPane.setBounds(0,0,1394,794);
      contentPane.setLayout(null);
      /////////////////////////////////////////////////
      
      JLabel qnaNoLabel = new JLabel("[No."+no+"]");
      qnaNoLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      qnaNoLabel.setBounds(49, 56, 105, 28);
      contentPane.add(qnaNoLabel);
      
      
      JLabel qnaTitleLabel = new JLabel(title);
      qnaTitleLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      qnaTitleLabel.setBounds(155, 61, 617, 18);
      contentPane.add(qnaTitleLabel);
      
      JLabel memIdLabel = new JLabel(id);
      memIdLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      memIdLabel.setBounds(774, 61, 177, 18);
      contentPane.add(memIdLabel);
      
      //쪽지보내기
      memIdLabel.addMouseListener(new MouseListener() {
          
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
        	  mvo = new MessageVO();
        		
        		System.out.println("-------------------- [쪽지작성] --------------------");
        		System.out.println("  > 받는 사람: " + id);
        				mvo.setMemReceiveId(id);
        				System.out.print("  > 쪽지 내용: ");
        				String cont = scan.nextLine();
        				mvo.setMessageContent(cont);
        				System.out.println();
        				System.out.println("  정말 전송하시겠습니까?");
        				
        				System.out.print("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");
        				int choice = scan.nextInt();
        				scan.nextLine();
        				switch (choice) {
        				case 1: mvo.setMemSendId(AdminMain.mvo.getMemId());  mvo.setMessageSendDate(null); mvo.setMessageNo(1); 
        				if(mdao.send(mvo)) {	
       								System.out.println();
       								System.out.println("  전송이 완료되었습니다.");}
        				else
        					System.out.println("전송을 실패하였습니다.");
        				break; 
        				case 2: System.out.println("  전송 취소되었습니다.");	 break; 	
        				default: System.out.println("  > 1 또는 2를 입력해주세요. (1: 전송 / 2: 취소)");  scan.nextInt(); break;
        				}
          }//클릭드
       });
      
      JLabel likesLabel = new JLabel(""+like);
      likesLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      likesLabel.setBounds(982, 61, 58, 18);
      contentPane.add(likesLabel);
      
      JLabel viewCntLabel = new JLabel(""+viewCnt);
      viewCntLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      viewCntLabel.setBounds(1054, 61, 67, 18);
      contentPane.add(viewCntLabel);
      
      JLabel postDateLabel = new JLabel(date);
      postDateLabel.setFont(new Font("굴림", Font.PLAIN, 20));
      postDateLabel.setBounds(1135, 61, 230, 18);
      contentPane.add(postDateLabel);
      
   
      
      //내 글일 떄만 수정/삭제 가능
      if(id.equals(AdminMain.mvo.getMemId())) {
         JButton updateSaveBtn = new JButton("글수정");
      updateSaveBtn.setBounds(1016, 22, 105, 30);
      contentPane.add(updateSaveBtn);
      updateSaveBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ComplBoardDAO complbDAO=new ComplBoardDAO();
            ComplBoardVO complVO=complbDAO.getOneComplPostInfo(no);
            ModifyPostPane modiPane=new ModifyPostPane(1,no,complVO.getComplTitle(),cbVO.getComplContent(),true);
            PublicMain.am.changePanel(modiPane.getJPanel());
         }
      });
      
      
      
      }//내글일 떄 가능한 기능들 end
      
      JButton deletePostBtn = new JButton("삭제");
      deletePostBtn.setBounds(897, 22, 105, 30);
      contentPane.add(deletePostBtn);
      deletePostBtn.addMouseListener(new MouseListener() {
         
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
            ComplBoardDAO cbDAO=new ComplBoardDAO();
            if(cbDAO.deleteComplPost(no)) {
               JOptionPane.showMessageDialog(null, "글이 삭제되었습니다.");
               ComplBdMng cbm = new ComplBdMng();
               PublicMain.am.changePanel(cbm.getJPanel());
            }
            else {
               JOptionPane.showMessageDialog(null, "글 삭제 실패");
            }
            
         }//마우스클릭드
      });
      
      
      
      
      
      
      
      
      
      //내글이 아니라면 좋아요버튼 생김
      if(!AdminMain.mvo.getMemId().equals(id)) {
         //내글이 아닌데 좋아요를 안눌렀어야해
         ComplBoardDAO cbd=new ComplBoardDAO();
         if(cbd.isThereComplBoard(AdminMain.mvo.getMemId(),no)==0) {
            JButton likesBtn = new JButton("좋아요");
            likesBtn.setBounds(1260, 22, 105, 30);
            contentPane.add(likesBtn);
            likesBtn.addMouseListener(new MouseListener() {

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

                  ComplBoardDAO cbDAO = new ComplBoardDAO();
                  ComplBoardVO cVO = new ComplBoardVO();
                  // 게시물좋아요 수 높이기
                  if (cbDAO.updateLikeCnt(no)) {

                     // 이제 게시글 작성자 like 높이기
                     if (cbDAO.updateMemLike(id)) {

                        likesBtn.setVisible(false);
                        if (cbDAO.insertLikeBoardNo(AdminMain.mvo.getMemId(), no)) {
                           JOptionPane.showMessageDialog(null, "좋아요누른게시물 추가성공");
                        } else {
                           JOptionPane.showMessageDialog(null, "작성자 좋아요 수 높이기 실패");
                        }

                     } else {

                     }

                     cVO = cbDAO.getOneComplPostInfo(no);
                     likesLabel.setText("" + cVO.getComplLike());
                  } else {
                     JOptionPane.showMessageDialog(null, "게시물좋아요누르기실패");
                  }

               }//마우스클릭드
            });
         } // 내가 좋아요버튼 안눌렀다면
      }//내글이 아니라면 
      
      //디자인 선
      JSeparator separator = new JSeparator();
      separator.setForeground(Color.BLACK);
      separator.setBounds(49, 82, 1316, 2);
      contentPane.add(separator);
      
      JTextArea qnaContentText = new JTextArea();
      qnaContentText.setBackground(Color.WHITE);
      qnaContentText.setBounds(49, 126, 716, 290);
      
      //내용 DB에서 가져오기
      cbDAO=new ComplBoardDAO();
      cbVO=new ComplBoardVO();
      cbVO=cbDAO.getOneComplPostInfo(no);
      qnaContentText.setText(cbVO.getComplContent());
      qnaContentText.setEditable(false);//수정불가
      qnaContentText.setLineWrap(true);//줄저절로바꾸기
      //��ũ�� ���̱� ��ũ���� ���ν�ũ�Ѹ� ���
      JScrollPane jScollPane = new JScrollPane(qnaContentText,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      jScollPane.setBounds(49, 91, 1316, 258);
      contentPane.add(jScollPane);
      
      
      
      
      JButton wirteCmtBtn = new JButton("댓글작성");
      wirteCmtBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ComplComtDAO ccDAO=new ComplComtDAO();
            int lastnum=ccDAO.getSizeOfComtSize(no);
            System.out.println("댓글을 입력하세요:");
            Scanner scn=new Scanner(System.in, "UTF-8");
            String content=scn.nextLine();
            if(ccDAO.writeComplComt(no, lastnum+1,AdminMain.mvo.getMemId(),content)) {
               System.out.println("댓글작성 성공");
               getPageOfComt(1);
               
            }else {
               System.out.println("댓글작성 실패");
               
            }
               
         }
      });
      
      wirteCmtBtn.setBounds(1141, 22, 105, 30);
      contentPane.add(wirteCmtBtn);
      
      
      
      qnaComtsPane=new JPanel();
      qnaComtsPane.setLocation(49, 369);
      qnaComtsPane.setLayout(new BoxLayout(qnaComtsPane,BoxLayout.Y_AXIS));
      //qnaComtsPane.setPreferredSize(new Dimension(0,2000));
      ComtsjScollPane = new JScrollPane(qnaComtsPane,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      ComtsjScollPane.setBounds(49,369,1316,379);
      
      
      getPageOfComt(1);
      
      
      
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
      prev.setFont(prev.getFont().deriveFont(20.0f));
      p1.setFont(prev.getFont().deriveFont(20.0f));
      p2.setFont(prev.getFont().deriveFont(20.0f));
      p3.setFont(prev.getFont().deriveFont(20.0f));
      p4.setFont(prev.getFont().deriveFont(20.0f));
      p5.setFont(prev.getFont().deriveFont(20.0f));
      p6.setFont(prev.getFont().deriveFont(20.0f));
      p7.setFont(prev.getFont().deriveFont(20.0f));
      p8.setFont(prev.getFont().deriveFont(20.0f));
      p9.setFont(prev.getFont().deriveFont(20.0f));
      p10.setFont(prev.getFont().deriveFont(20.0f));
      next.setFont(prev.getFont().deriveFont(20.0f));

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
      pagePanel=new JPanel();
      pagePanel.setSize(680, 30);
      pagePanel.setLocation(360, 754);
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
      
      contentPane.add(pagePanel);
      
      JButton btnNewButton = new JButton("목록으로 돌아가기");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ComplBdMng complbdMng=new ComplBdMng();
            PublicMain.am.changePanel(complbdMng.getJPanel());
         }
      });
      btnNewButton.setBounds(1196, 761, 169, 23);
      contentPane.add(btnNewButton);
      
      lblNewLabel = new JLabel("♥");
      lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 17));
      lblNewLabel.setBounds(959, 62, 17, 18);
      contentPane.add(lblNewLabel);
      
   }
   
   public void getPageOfComt(int page) {
      qnaComtsPane.removeAll();
      
      ComplComtDAO ccDAO=new ComplComtDAO();
      List<ComplComtVO> ccVOList=new ArrayList<ComplComtVO>();
      ccVOList=ccDAO.getComplComtListPage(no,page); //글번호와 페이지 수 넘겨줌
      //qnaComtsPane.setPreferredSize(new Dimension(0,2000));
      if(ccVOList.size()==10) {
         qnaComtsPane.setPreferredSize(new Dimension(0,2000));
      }else if(ccVOList.size()==9) {
         qnaComtsPane.setPreferredSize(new Dimension(0,1800));
      }else if(ccVOList.size()==8) {
         qnaComtsPane.setPreferredSize(new Dimension(0,1600));
      }else if(ccVOList.size()==7) {
         qnaComtsPane.setPreferredSize(new Dimension(0,1400));
      }else if(ccVOList.size()==6) {
         qnaComtsPane.setPreferredSize(new Dimension(0,1200));
      }else if(ccVOList.size()==5) {
         qnaComtsPane.setPreferredSize(new Dimension(0,1000));
      }else if(ccVOList.size()==4) {
         qnaComtsPane.setPreferredSize(new Dimension(0,800));
      }else if(ccVOList.size()==3) {
         qnaComtsPane.setPreferredSize(new Dimension(0,600));
      }else if(ccVOList.size()==2) {
         qnaComtsPane.setPreferredSize(new Dimension(0,400));
      }else if(ccVOList.size()==1) {
         qnaComtsPane.setPreferredSize(new Dimension(0,200));
      }else if(ccVOList.size()==0) {
         qnaComtsPane.setPreferredSize(new Dimension(0,200));
         JLabel noData=new JLabel("댓글없음");
         noData.setFont(new Font("굴림", Font.PLAIN,30));
         noData.setBounds(55,400,500,10);
         qnaComtsPane.add(noData);
      }
      
      for(int i=0;i<ccVOList.size();i++) {
         qnaComtsPane.add(new CustomCmtPane(ccVOList.get(i).getComplNo(),
                                    ccVOList.get(i).getComplComtNo(),
                                    ccVOList.get(i).getMemId(),
                                    ccVOList.get(i).getComplComtContent(),
                                    ccVOList.get(i).getComplComtLike(),
                                    ccVOList.get(i).getComplComtDate()));
      }
      qnaComtsPane.revalidate();
      qnaComtsPane.repaint();
      
      contentPane.add(ComtsjScollPane);
      contentPane.revalidate();
      contentPane.repaint();
   }
   public JPanel getJPanel() {
      return contentPane;
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      JLabel lb = (JLabel) e.getSource();
      if(lb == p1) {//1페이지 눌렀을 때
         getPageOfComt(1+(10*pageCnt));
      } else if(lb == p2) {//2페이지 눌렀을 때
         
         getPageOfComt(2+(10*pageCnt));
         
      } else if(lb == p3) {//3페이지 눌렀을 때
      
         getPageOfComt(3+(10*pageCnt));
         
      } else if(lb == p4) {//4페이지 눌렀을 때
         
         getPageOfComt(4+(10*pageCnt));
         
      } else if(lb == p5) {//5페이지 눌렀을 때
         
         getPageOfComt(5+(10*pageCnt));
         
      } else if(lb == p6) {//6페initializeJTable();
         getPageOfComt(6+(10*pageCnt));
         
      } else if(lb == p7) {//7페이지 눌렀을 때
         getPageOfComt(7+(10*pageCnt));
         
      } else if(lb == p8) {//8페이지 눌렀을 때
         
         getPageOfComt(8+(10*pageCnt));
         
      } else if(lb == p9) {//9페이지 눌렀을 때
         
         getPageOfComt(9+(10*pageCnt));
         
      } else if(lb == p10) {//10페이지 눌렀을 때
         
         getPageOfComt(10+(10*pageCnt));
         
      } else if(lb == next) {//다음페이지 눌렀을 때
         pageCnt++;
         getPageOfComt(1+(10*pageCnt));
         
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
         getPageOfComt(1+(10*pageCnt));
         
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
   class CustomCmtPane extends JPanel {
      JLabel memIdLabel;
      JLabel dateLabel;
      JTextArea comtText;
      JLabel cmtNoLabel;
      JLabel likeLabel;
      private JLabel lblNewLabel;
      JButton likeBtn;
      public CustomCmtPane(int complNo,int comtNo,String memId,String content,int like,String date) {
         setBackground(Color.LIGHT_GRAY);
         setSize(776,179);
         setLayout(null);
         
         memIdLabel = new JLabel(memId);
         memIdLabel.setFont(new Font("굴림", Font.PLAIN, 20));
         
         memIdLabel.setBounds(69, 14, 215, 26);
         add(memIdLabel);
         
         dateLabel = new JLabel(date);
         dateLabel.setFont(new Font("굴림", Font.PLAIN, 19));
         dateLabel.setBounds(443, 158, 215, 18);
         add(dateLabel);
         
         //내 댓글이 아니면 보여야함
         if(!AdminMain.mvo.getMemId().equals(memIdLabel.getText()))
         {   
            ComplComtDAO ccomtDao=new ComplComtDAO();
            //if(내가 좋아요 안누른 댓글만 좋아요보임
            if(ccomtDao.isThereComplComt(AdminMain.mvo.getMemId(), complNo, comtNo)==0) {
               likeBtn = new JButton("좋아요");
               likeBtn.setBounds(482, 13, 92, 27);
               add(likeBtn);
               likeBtn.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     ComplComtDAO ccomtDAO = new ComplComtDAO();
            
                     // 게시물좋아요 수 높이기
                     if (ccomtDAO.updateLikeCnt(complNo,comtNo)) {

                        // 이제 댓글 작성자 like 높이기
                        if (ccomtDAO.updateMemLike(memIdLabel.getText())) {

                           likeBtn.setVisible(false);
                           if (ccomtDAO.insertLikeBoardNo(AdminMain.mvo.getMemId(), complNo, comtNo)) {
                              JOptionPane.showMessageDialog(null, "좋아요누른게시물 추가성공");
                           } else {
                              JOptionPane.showMessageDialog(null, "작성자 좋아요 수 높이기 실패");
                           }

                        } else {

                        }

                        int cnt = ccomtDAO.getComtCnt(complNo, comtNo);
                        likeLabel.setText(""+cnt);
                     } else {
                        JOptionPane.showMessageDialog(null, "게시물좋아요누르기실패");
                     }
                  }
               });
            }//내가 좋아요 눌렀으면 못누름ㅁ
         }//내 댓글이면 좋아요 못누름
         
         
      
         
         JButton deleteBtn = new JButton("삭제");
         deleteBtn.setBounds(372, 13, 92, 27);
         deleteBtn.addMouseListener(new MouseListener() {
            
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
               ComplComtDAO ccDAO = new ComplComtDAO();
            
               if(ccDAO.deleteComplComt(complNo,comtNo)) {
                  JOptionPane.showMessageDialog(null, "댓글 삭제 성공");
                  getPageOfComt(1);
               }else {
                  JOptionPane.showMessageDialog(null, "댓글 삭제 실패");
               }
               
            }
         });
         
         add(deleteBtn);
         
         
         

         comtText = new JTextArea();
         comtText.setFont(new Font("Monospaced", Font.PLAIN, 18));
         comtText.setText(content);
         comtText.setBounds(14, 46, 736, 90);
         comtText.setLineWrap(true);
         JScrollPane jScollPane = new JScrollPane(comtText,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         jScollPane.setBounds(14,46,644,106);
         add(jScollPane);
         
         cmtNoLabel = new JLabel("No."+comtNo);
         cmtNoLabel.setFont(new Font("굴림", Font.PLAIN, 18));
         cmtNoLabel.setBounds(14, 21, 85, 15);
         add(cmtNoLabel);
         
         likeLabel = new JLabel(""+like);
         likeLabel.setFont(new Font("굴림", Font.PLAIN, 18));
         likeLabel.setBounds(609, 18, 54, 18);
         add(likeLabel);
         
         lblNewLabel = new JLabel("♥");
         lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
         lblNewLabel.setBounds(585, 19, 15, 18);
         add(lblNewLabel);
      }
   }

}