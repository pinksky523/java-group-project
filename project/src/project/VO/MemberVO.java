package project.VO;

import java.util.Date;

public class MemberVO {
	private String memId;
	private String memPw;
	private String memName;
	private Date memBirthday;
	private String memEmail;
	private int memLike;
	private int memPoint;
	private int memPntCnt;
	private int memBdPntTf;	//0이면 일반 1이면 제한
	private int memCtPntTf; //0이면 일반 1이면 제한
	private Date memBdPntEnddate;
	private Date memCtPntEnddate;
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public Date getMemBirthday() {
		return memBirthday;
	}
	public void setMemBirthday(Date memBirthday) {
		this.memBirthday = memBirthday;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public int getMemLike() {
		return memLike;
	}
	public void setMemLike(int memLike) {
		this.memLike = memLike;
	}
	public int getMemPoint() {
		return memPoint;
	}
	public void setMemPoint(int memPoint) {
		this.memPoint = memPoint;
	}
	public int getMemPntCnt() {
		return memPntCnt;
	}
	public void setMemPntCnt(int memPntCnt) {
		this.memPntCnt = memPntCnt;
	}
	public int getMemBdPntTf() {
		return memBdPntTf;
	}
	public void setMemBdPntTf(int memBdPntTf) {
		this.memBdPntTf = memBdPntTf;
	}
	public int getMemCtPntTf() {
		return memCtPntTf;
	}
	public void setMemCtPntTf(int memCtPntTf) {
		this.memCtPntTf = memCtPntTf;
	}
	public Date getMemBdPntEnddate() {
		return memBdPntEnddate;
	}
	public void setMemBdPntEnddate(Date memBdPntEnddate) {
		this.memBdPntEnddate = memBdPntEnddate;
	}
	public Date getMemCtPntEnddate() {
		return memCtPntEnddate;
	}
	public void setMemCtPntEnddate(Date memCtPntEnddate) {
		this.memCtPntEnddate = memCtPntEnddate;
	}
	
	
	
	

}
