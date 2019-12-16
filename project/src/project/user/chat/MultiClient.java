package project.user.chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

import project.DAO.UserItemDAO;
import project.VO.UserItemVO;
import project.user.itemshop.ItemShop;


public class MultiClient extends JFrame implements ActionListener, KeyListener,Runnable {

	private static final long serialVersionUID = 1L;
	private static final String DATE_PATTERN = " yyyy년 MM월 dd일\n";
	private static final String TIME_PATTERN = "[a hh:mm:ss]";
	

	private JLabel titleLbl, nicknameLbl;
	private JTextField chatTxt;
	private JButton clearBtn, itemBtn;
	private JTextArea chatArea;
	private JPanel panel;
	private JScrollPane scrollPane;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
//	private String nickname = "jjw";
	private UserItemDAO uidao;
	private UserItemVO uivo;
	private String nickname;
	
	@Override
	public void run() {
		nickname = JOptionPane.showInputDialog("대화명을 입력해 주세요.");

		// 대화명을 입력하지 않은 경우는 실행 중단

		if (nickname == null) {

			return;

		}

		titleLbl = new JLabel("JAVA CHAT v.1", JLabel.CENTER);
		titleLbl.setBounds(0, 0, 612, 40);
		nicknameLbl = new JLabel(nickname);
		nicknameLbl.setBounds(29, 7, 58, 15);
		nicknameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		chatTxt = new JTextField(10);
		chatTxt.setBounds(97, 6, 173, 21);
		clearBtn = new JButton("CLEAR");
		clearBtn.setBounds(275, 5, 73, 23);
		itemBtn = new JButton("보유 아이템 목록");
		itemBtn.setBounds(353, 5, 140, 23);
		
		JButton exitBtn=new JButton("채팅종료");
		exitBtn.setBounds(500,5, 100,23);
		
		exitBtn.addMouseListener(new MouseListener() {
			
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
				dispose();
				
			}
		});
		chatArea = new JTextArea();

		panel = new JPanel();
		panel.setBounds(0, 415, 612, 33);
		titleLbl.setFont(new Font("Arial Black", Font.BOLD, 15));
		titleLbl.setPreferredSize(new Dimension(350, 40));
		chatArea.setLineWrap(true); // 줄바꿈 설정
		chatArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈 설정
		chatArea.setEditable(false); // 편집 불가 설정


		
		
		scrollPane =

				new JScrollPane(chatArea,

						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,

						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 40, 612, 375);

		chatTxt.addKeyListener(this);
		clearBtn.addActionListener(this);
		itemBtn.addActionListener(this);
		panel.setLayout(null);
		
		panel.add(nicknameLbl);
		panel.add(chatTxt);
		panel.add(clearBtn);
		panel.add(itemBtn);
		panel.add(exitBtn);
		getContentPane().setLayout(null);

		getContentPane().add(scrollPane);
		getContentPane().add(titleLbl);
		getContentPane().add(panel);
		setTitle("JAVA CHAT v.1");

		setSize(632, 520);

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocation(500, 300);

		setVisible(true);

		addWindowListener(new WindowAdapter() {

			@Override

			public void windowOpened(WindowEvent e) {

				try {

					System.out.println();

					socket = new Socket("localhost", 5000);

					pw = new PrintWriter(socket.getOutputStream(), true);

					pw.println(nickname + "/" + "Hello Server");

					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					new Thread(() -> {

						try {

							while (br != null) {

								chatArea.append(br.readLine() + " " + timeStamp(TIME_PATTERN) + "\n");

							}

						} catch (IOException e1) {

							// TODO Auto-generated catch block

							e1.printStackTrace();

						}

					}).start();

					chatTxt.requestFocus();

				} catch (UnknownHostException ee) {

					System.err.println("> 서버 연결 오류 : 지정된 서버(" + ee.getMessage() + ")가 존재하지 않습니다. ");

				} catch (ConnectException ee) {

					System.err.println("> 서버에 연결하지 못했습니다. ");

					System.err.println("> 서버 가동 상태를 확인해 주세요. ");

				} catch (IOException ee) {

					ee.printStackTrace();

				}

			}// END windowOpened()

			@Override

			public void windowClosing(WindowEvent e) {

				System.out.println("WINDOW CLOSING");

				pw.println("-1");

				dispose();

				System.exit(0);

				try {

					if (br != null)
						br.close();

					if (pw != null)
						pw.close();

					if (socket != null)
						socket.close();

				} catch (IOException e1) {

					e1.printStackTrace();

				}

				System.out.println("WINDOW CLOSING");

			}// END windowClosing

		});
		
	}

	public MultiClient() {

		

	}

	// 소켓 및 스트림들 생성

	@Override

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == 10) { // 엔터키가 눌린 경우

			pw.println(chatTxt.getText()); // 닉네임과 대화내용을 서버로 전송하고

			chatTxt.setText("");

			chatTxt.requestFocus();

		}

	}

	// 날짜 및 시간 정보를 반환

	public String timeStamp(String pattern) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);

		return simpleFormat.format(new Date());

	}

	@Override

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == clearBtn) { // 지우기 버튼

			chatArea.setText("");

			chatTxt.requestFocus();

		} else if (e.getSource() == itemBtn) { // 보유아이템 목록 버튼
			ItemShop is = new ItemShop();
			is.getJPanel();
			
			itemList();
		}

	}

	@Override

	public void keyTyped(KeyEvent e) {
	}

	@Override

	public void keyPressed(KeyEvent e) {
	}

	/*public static void main(String[] args) {

		new MultiClient();

	}*///end main
	
	public void itemList() {
		uidao = new UserItemDAO();
		uivo = new UserItemVO();
		Scanner scan = new Scanner(System.in);
		int inputItemNo;

		
		if (uidao.getUserItem(nickname).size() == 0) {
			System.out.println("보유한 아이템이 없습니다");
		}
				
		
		else {
			System.out.println("============== 내 아이템 목록 ==============");
			System.out.println("아이템번호\t\t아이템종류\t\t아이템이름\t\t가격");
			System.out.println("------------------------------------");
			for (int i = 0 ; i<uidao.getUserItem(nickname).size() ; i++) {
					System.out.println(uidao.getUserItem(nickname).get(i).getItemNo() + "\t\t"
										+ uidao.getUserItem(nickname).get(i).getItemType() + "\t\t"
										+ uidao.getUserItem(nickname).get(i).getItemName() + "\t\t"
										+ uidao.getUserItem(nickname).get(i).getItemPrice());
					uivo.setItemNo(uidao.getUserItem(nickname).get(i).getItemNo());
					uivo.setItemType(uidao.getUserItem(nickname).get(i).getItemType());
					uivo.setItemName(uidao.getUserItem(nickname).get(i).getItemName());
					uivo.setItemPrice(uidao.getUserItem(nickname).get(i).getItemPrice());
					
			};
			System.out.println();
			System.out.println("적용하실 아이템 번호를 입력해주세요");
			
			EXIT1:
			while(true) {
				System.out.print("> ");
				inputItemNo = scan.nextInt();
				System.out.println();
				if (uidao.isExist(nickname, inputItemNo) == true) {
					
					if(uidao.getItemType(inputItemNo).equals("채팅 내용 굵게")) {
						chatArea.setFont(new Font("맑은 고딕", Font.BOLD, 20));
						break;
					}
					
					else if(uidao.getItemType(inputItemNo).equals("채팅 내용 색상")) {
						switch(uidao.getItemName(inputItemNo)) {
							case "Red" : chatArea.setForeground(Color.RED); break EXIT1;
							case "Orange" : chatArea.setForeground(Color.ORANGE); break EXIT1;
							case "Yellow" : chatArea.setForeground(Color.YELLOW); break EXIT1;
							case "Green" : chatArea.setForeground(Color.GREEN); break EXIT1;
							case "Blue" : chatArea.setForeground(Color.BLUE); break EXIT1;
							case "Pink" : chatArea.setForeground(Color.PINK); break EXIT1;
							case "Cyan" : chatArea.setForeground(Color.CYAN); break EXIT1;
						}
					}
					
					else if(uidao.getItemType(inputItemNo).equals("채팅창 테마 색상")) {
						switch(uidao.getItemName(inputItemNo)) {
							case "Black" : {
								getContentPane().setBackground(new Color(0xBF8D7A, false));
								titleLbl.setForeground(new Color(0x000000, false));
								chatArea.setBackground(new Color(0x7D716E, false));
								panel.setBackground(new Color(0x7D716E, false));
								nicknameLbl.setForeground(new Color(0x222626, false));
								break EXIT1;
							}
							case "White" : {
								getContentPane().setBackground(new Color(0xA6BFBE, false));
								titleLbl.setForeground(new Color(0x9A9C7E, false));
								chatArea.setBackground(new Color(0xDFD3E8, false));
								panel.setBackground(new Color(0xE9CFC1, false));
								nicknameLbl.setForeground(new Color(0x656659, false));
								break EXIT1;
							}
							case "Pink" : {
								getContentPane().setBackground(new Color(0xE65496, false));
								titleLbl.setForeground(new Color(0x5F4661, false));
								chatArea.setBackground(new Color(0xBD6F78, false));
								panel.setBackground(new Color(0x996763, false));
								nicknameLbl.setForeground(new Color(0xEB6ED0, false));
								break EXIT1;
							}
							case "Red" : {
								getContentPane().setBackground(new Color(0xBD3044, false));
								titleLbl.setForeground(new Color(0x612F2F, false));
								chatArea.setBackground(new Color(0xEB4649, false));
								panel.setBackground(new Color(0x993F52, false));
								nicknameLbl.setForeground(new Color(0xE61E2E, false));
								break EXIT1;
							}
							case "Blue" : {
								getContentPane().setBackground(new Color(0x5CA1E6, false));
								titleLbl.setForeground(new Color(0x313661, false));
								chatArea.setBackground(new Color(0x4B9BBD, false));
								panel.setBackground(new Color(0x519096, false));
								nicknameLbl.setForeground(new Color(0x4B7AEB, false));
								break EXIT1;
							}
							case "Yellow" : {
								getContentPane().setBackground(new Color(0x8A7A07, false));
								titleLbl.setForeground(new Color(0xDEDB00, false));
								chatArea.setBackground(new Color(0xC7ED68, false));
								panel.setBackground(new Color(0xC5D92B, false));
								nicknameLbl.setForeground(new Color(0xB5A900, false));
								break EXIT1;
							}
							case "Green" : {
								getContentPane().setBackground(new Color(0xBCDB74, false));
								titleLbl.setForeground(new Color(0x418769, false));
								chatArea.setBackground(new Color(0xEBC471, false));
								panel.setBackground(new Color(0xD1C564, false));
								nicknameLbl.setForeground(new Color(0x27AB26, false));
	
								break EXIT1;
							}
						}
					}
					
				}
				else {
					System.out.println("보유하고 있지 않는 아이템입니다. 다시 입력해주세요");
					continue;
				}
			}//end while
		}//end else
      }//end itemlist 메서드
}//end class
	
	
	


