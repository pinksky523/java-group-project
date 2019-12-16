package project.VO;

public class MessageVO {

	private int messageNo;	//기본키
	private String memReceiveId;	
	private String memSendId;	
	private String messageContent;
	private int messageCheckTf;
	private String messageSendDate;
	
	public int getMessageNo() {
		return messageNo;
	}
	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}
	public String getMemReceiveId() {
		return memReceiveId;
	}
	public void setMemReceiveId(String memReceiveId) {
		this.memReceiveId = memReceiveId;
	}
	public String getMemSendId() {
		return memSendId;
	}
	public void setMemSendId(String memSendId) {
		this.memSendId = memSendId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public int getMessageCheckTf() {
		return messageCheckTf;
	}
	public void setMessageCheckTf(int messageCheckTf) {
		this.messageCheckTf = messageCheckTf;
	}
	public String getMessageSendDate() {
		return messageSendDate;
	}
	public void setMessageSendDate(String messageSendDate) {
		this.messageSendDate = messageSendDate;
	}	
	
}
