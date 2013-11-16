package com.foodie_union.container;

public class ChatMsgEntity {
	private String username;
	private String sendtime;
	private String message;
	private boolean isComMsg = true; // 是否为收到的消息

	public ChatMsgEntity() {

	}

	ChatMsgEntity(String username, String sendtime, String message,
			boolean isComMsg) {
		super();
		this.username = username;
		this.sendtime = sendtime;
		this.message = message;
		this.isComMsg = isComMsg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getMsgType() {
		// TODO Auto-generated method stub
		return isComMsg;
	}

	public void setMsgType(boolean isComMsg) {
		this.isComMsg = isComMsg;
	}

}
