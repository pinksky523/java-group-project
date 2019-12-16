package project.publicMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import project.DAO.MemberDAO;
import project.VO.MemberVO;
import project.admin.main.AdminMain;
import project.user.main.UserMain;


public class PublicMain {
	public static UserMain um;
	public static AdminMain am;
	
	final String REG_EXP_ID = "^[0-9a-zA-Z]{4,14}$";
	final String REG_EXP_BIRTHDAY = "^[0-9]{6}$";
	final String REG_EXP_EMAIL = "^([A-za-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
	
	Scanner scan;
	private String memId;
	private String memPw;
	private String memName;
	private String memBirthday;
	private String memEmail;
	private MemberVO mvo;
	private MemberDAO mdao;
	
	PublicMain(){
		mdao = new MemberDAO();
		scan = new Scanner(System.in);
		menu();
	}
	
	public void menu() {
		System.out.println("------- JAVA 잡아! -------");
		System.out.println("로그인 또는 회원가입을 선택해주세요.");
		System.out.println();
		System.out.println("> 1. 로그인");
		System.out.println("> 2. 회원가입");
		while(true) {
			System.out.print(">> 선택 : ");
			String input = scan.next();
			if(input.equals("1")) {
				//로그인
				login();
				break;
			}else if(input.equals("2")) {
				//회원가입
				join();
				break;
			}else System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}
	}
	
	//login()과 join()은 따로라서 mvo = new MemberVO(); 두번함
	public void login() {
		mvo = new MemberVO();
		System.out.println("> 로그인");
		while(true) {
			System.out.println("> 아이디와 비밀번호를 입력해주세요.");
			System.out.print("> 아이디 : ");
			
			memId = scan.next();
			
				System.out.println(memId);
				System.out.println("> 비밀번호 : ");
				memPw = scan.next();
				mvo = mdao.login(memId, memPw);
				if(mvo == null) {	//반환값이 null이면 존재하지 않는 아이디 또는 비밀번호
					System.out.println("아이디 또는 비밀번호가 잘못되었습니다. 다시 입력해주세요.");
				}else {
					if(memId.equals("admin")) {
						am = new AdminMain();
					} else {
						//um = new UserMain(mvo); 이거 주석 없애고 UserMain 생성자 매개변수도 MemberVO 해줘야함@@@@@@@@@@@@@@@@@@@@@@@@@@@@
						um = new UserMain(mvo);
					}
					break;
				}
		}
	}
	
	public void join() {
		mvo = new MemberVO();
		System.out.println("> 회원가입");
		System.out.println("> 다음 정보들을 입력해 주세요.");
		
		while(true) {
			System.out.print("> 아이디(영문, 숫자만 가능 / 4-14자) : ");
			memId = scan.next();
			if(!Pattern.matches(REG_EXP_ID, memId)) {
				System.out.println("영문, 숫자를 이용하여 4-14자로 맞춰서 다시 입력해주세요.");
			} else {
				if(mdao.isExist(memId, 1)) { //true면 사용 가능
					mvo.setMemId(memId);
					break;
				} else {	//false면 중복
					System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.");
				}
			}
		}
		
		while(true) {
			System.out.print("> 비밀번호(4-14자) : ");
			memPw = scan.next();
			if(!(memPw.length()>=4 && memPw.length()<=14)) {
				System.out.println("4-14자로 맞춰서 다시 입력해주세요.");
			} else {
				mvo.setMemPw(memPw);
				break;
			}
		}
		
		while(true) {
			System.out.print("> 이름(2-5자) : ");
			memName = scan.next();
			if(!(memName.length()>=2 && memName.length()<=5)) {
				System.out.println("2-5자로 맞춰서 다시 입력해주세요.");
			} else {
				mvo.setMemName(memName);
				break;
			}
		}

		while(true) {
			System.out.print("> 생년월일(yymmdd) : ");
			memBirthday = scan.next();
			if(!Pattern.matches(REG_EXP_BIRTHDAY, memBirthday)) {
				System.out.println("숫자와 형식을 맞춰서 다시 입력해주세요.");
			} else {
					try {
						mvo.setMemBirthday(new SimpleDateFormat("yyMMdd").parse(memBirthday));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				break;
			}
		}

		while(true) {
			System.out.print("> 이메일(아이디@도메인) : ");
			memEmail = scan.next();
			if(!Pattern.matches(REG_EXP_EMAIL, memEmail)) {
				System.out.println("이메일 형식이 틀립니다. 다시 입력해주세요.");
			} else {
				if(mdao.isExist(memEmail, 2)) { //true면 사용 가능
					mvo.setMemEmail(memEmail);
					break;
					
				} else {	//false면 중복
					System.out.println("이미 존재하는 이메일입니다. 다시 입력해주세요.");
				}
			}
		}
		boolean complete = mdao.join(mvo);
		if(complete) {System.out.println("회원가입이 완료되었습니다.");menu();}
		else System.out.println("회원가입이 실패했습니다.");
	}
	

	public static void main(String[] args) {
		new PublicMain();
	}

	
}
