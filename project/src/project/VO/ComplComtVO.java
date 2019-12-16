package project.VO;

public class ComplComtVO {
	private int complNo; // fk
	private int complComtNo;// pk
	private String memId; // fk
	private String complComtContent;
	private String complComtDate;
	private int complComtLike;
	public synchronized int getComplNo() {
		return complNo;
	}
	public synchronized void setComplNo(int complNo) {
		this.complNo = complNo;
	}
	public synchronized int getComplComtNo() {
		return complComtNo;
	}
	public synchronized void setComplComtNo(int complComtNo) {
		this.complComtNo = complComtNo;
	}
	public synchronized String getMemId() {
		return memId;
	}
	public synchronized void setMemId(String memId) {
		this.memId = memId;
	}
	public synchronized String getComplComtContent() {
		return complComtContent;
	}
	public synchronized void setComplComtContent(String complComtContent) {
		this.complComtContent = complComtContent;
	}
	public synchronized String getComplComtDate() {
		return complComtDate;
	}
	public synchronized void setComplComtDate(String complComtDate) {
		this.complComtDate = complComtDate;
	}
	public synchronized int getComplComtLike() {
		return complComtLike;
	}
	public synchronized void setComplComtLike(int complComtLike) {
		this.complComtLike = complComtLike;
	}
	
	

}
