package project.VO;

import java.util.Date;

public class QnaBoardVO {
	private int qnaNo; // pk
	private int qnaType;
	private String qnaTitle;
	private String qnaContent;
	private String memId; // fk
	private String qnaDate;
	private int qnaViewcnt;
	private int qnaLike;
	public int getQnaNo() {
		return qnaNo;
	}
	public void setQnaNo(int qnaNo) {
		this.qnaNo = qnaNo;
	}
	public int getQnaType() {
		return qnaType;
	}
	public void setQnaType(int qnaType) {
		this.qnaType = qnaType;
	}
	public String getQnaTitle() {
		return qnaTitle;
	}
	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getQnaDate() {
		return qnaDate;
	}
	public void setQnaDate(String qnadate) {
		this.qnaDate = qnadate;
	}
	public int getQnaViewcnt() {
		return qnaViewcnt;
	}
	public void setQnaViewcnt(int qnaViewcnt) {
		this.qnaViewcnt = qnaViewcnt;
	}
	public int getQnaLike() {
		return qnaLike;
	}
	public void setQnaLike(int qnaLike) {
		this.qnaLike = qnaLike;
	}


}
