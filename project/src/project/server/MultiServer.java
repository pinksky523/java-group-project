package project.server;

import java.io.*;

import java.net.*;

import java.text.SimpleDateFormat;

import java.util.*;

public class MultiServer extends Thread {

	private static final int PORT = 5000;

	private Socket socket;

	private Map<String, PrintWriter> clientMap;

	
	
	/*public Map<String, PrintWriter> getClientMap() {

		return clientMap;

	}*/

	@Override
	public void run() {
		System.out.println("*** SERVER is running... ***");

		// 1. 서버 소켓 생성

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {

			// 2. clientMap 객체 생성 및 동기화 처리

			clientMap = Collections.synchronizedMap(new HashMap<String, PrintWriter>());

			// 3. 클라이언트 접속 무한 대기

			// 3.1 클라이언트 접속 승인

			while (true) {

				System.out.println("> 클라이언트 접속 대기 중...");

				socket = serverSocket.accept();
			
				System.out.println("> 클라이언트 접속 완료");

				// 4. 접속한 클라이언트 스레드로 처리 시작

				new ChatServerThread().start();

			}

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		
		
		
	}
	public void MultiServerMethod() {
		//run()안에 있는거 원래 여기
	}// END MultiServer()

	// 내부 클래스 ChatServerThread로 Thread를 상속받고

	

	class ChatServerThread extends Thread {

		private BufferedReader br; // 각 스레드에서 사용할 스트림 객체들 선언

		private PrintWriter pw;

		private String nickname, msg;

		public ChatServerThread() { // 기본 생성자에서 위에서 선언한 스트림 객체들 생성

			try {

				pw = new PrintWriter(socket.getOutputStream(), true);

				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String[] input = br.readLine().split("/");

				nickname = input[0];

				msg = input[1];

				pw = new PrintWriter(socket.getOutputStream(), true);

				pw.println("*** 서버에 접속되었습니다. ***");
				

			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}// END ChatServerThread()

		@Override

		public void run() { // 스레드로 처리할 내용

			// 클라이언트의 nickname(키)과 연결된 출력스트림(값)을 clientMap에 저장

			clientMap.put(nickname, pw);

			// 모든 클라이언트에게 브로드 캐스팅하는 메서드 호출

			// 최초 입장 시에는 환영 메시지 전달

			broadCast(nickname + "님이 입장하셨습니다");

			try {

				while (br != null) {

					msg = br.readLine(); // 클라이언트에서 보낸 메세지를 읽고

					if (msg.equals("-1")) { // 클라이언트 창 종료 시에는 퇴장 메시지 전달

						clientMap.remove(nickname);
						
						
					} else {

						broadCast(nickname + "> " + msg); // 이후에는 반복으로 메시지만 전달

					}

				}

			} catch (SocketException e) {

				broadCast(nickname + "님이 나가셨습니다.");

			}

			catch (IOException e) {

				e.printStackTrace();

			}

		}// END run()

	}// END ChatServerThread class (내부클래스)

	public void broadCast(String msg) {

		// 모든 사용자에게 메시지를 전송하는 broadCast 메서드 작성

		// 매개변수 : 전송할 메세지

		// 기능 : clientMap에서 출력 스트림을 받아서 모두에게 메시지 전송

		// 메시지 전송

		Iterator<String> iterator = clientMap.keySet().iterator();

		while (iterator.hasNext()) {

			PrintWriter pw = (PrintWriter) clientMap.get(iterator.next());

			pw.println(msg);

		}
		
		
//		for(int i = 0 ; i<userlist.size() ; i++) {
//			PrintWriter pw2 = (PrintWriter) userlist.get(i);
//			pw2.println();
//			
//		}

	}// end broadCast

	/*public static void main(String[] args) {

		new MultiServer().MultiServerMethod();

	}*/// END main()

}// END MultiServer class