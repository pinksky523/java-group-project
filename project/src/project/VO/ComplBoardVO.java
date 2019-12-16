package project.VO;


import java.util.Date;





public class ComplBoardVO {
	int complNo;
	int complType;
	String complTitle;
	String complContent;
	String complId;
	String complDate;
	int complViewCnt;
	int complLike;
	public synchronized int getComplLike() {
		return complLike;
	}
	public synchronized void setComplLike(int complLike) {
		this.complLike = complLike;
	}
	int complIsSecret;
	public synchronized int getComplNo() {
		return complNo;
	}
	public synchronized void setComplNo(int complNo) {
		this.complNo = complNo;
	}
	public synchronized int getComplType() {
		return complType;
	}
	public synchronized void setComplType(int complType) {
		this.complType = complType;
	}
	public synchronized String getComplTitle() {
		return complTitle;
	}
	public synchronized void setComplTitle(String complTitle) {
		this.complTitle = complTitle;
	}
	public synchronized String getComplContent() {
		return complContent;
	}
	public synchronized void setComplContent(String complContent) {
		this.complContent = complContent;
	}
	public synchronized String getComplId() {
		return complId;
	}
	public synchronized void setComplId(String complId) {
		this.complId = complId;
	}
	public synchronized String getComplDate() {
		return complDate;
	}
	public synchronized void setComplDate(String complDate) {
		this.complDate = complDate;
	}
	public synchronized int getComplViewCnt() {
		return complViewCnt;
	}
	public synchronized void setComplViewCnt(int complViewCnt) {
		this.complViewCnt = complViewCnt;
	}
	public synchronized int getComplIsSecret() {
		return complIsSecret;
	}
	public synchronized void setComplIsSecret(int complIsSecret) {
		this.complIsSecret = complIsSecret;
	}
	
	
}
