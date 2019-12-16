package project.VO;

public class QnaBoardComtVO {
	private int qnaNo; // fk
	private int qnaComtNo;
	private String memId; // fk
	private String qnaComtContent;
	private String qnaComtDate;
	private int qnaComtLike;
	public int getQnaNo() {
		return qnaNo;
	}
	public void setQnaNo(int qnaNo) {
		this.qnaNo = qnaNo;
	}
	public int getQnaComtNo() {
		return qnaComtNo;
	}
	public void setQnaComtNo(int qnaComtNo) {
		this.qnaComtNo = qnaComtNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getQnaComtContent() {
		return qnaComtContent;
	}
	public void setQnaComtContent(String qnaComtContent) {
		this.qnaComtContent = qnaComtContent;
	}
	public String getQnaComtDate() {
		return qnaComtDate;
	}
	public void setQnaComtDate(String qnaComtDate) {
		this.qnaComtDate = qnaComtDate;
	}
	public int getQnaComtLike() {
		return qnaComtLike;
	}
	public void setQnaComtLike(int qnaComtLike) {
		this.qnaComtLike = qnaComtLike;
	}
	
	

}