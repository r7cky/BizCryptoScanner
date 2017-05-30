package com.sickpillow.models;

public class ReplyBean{
	private String boardAbrv;
	private String opPostNum;
	private String replyPostNum;
	private String replyContent;
	
	public ReplyBean() {}
	
	public ReplyBean(String boardAbrv, String opPostNum, String replyPostNum, String replyContent) {
		super();
		this.boardAbrv = boardAbrv;
		this.opPostNum = opPostNum;
		this.replyPostNum = replyPostNum;
		this.replyContent = replyContent;
	}
	
	public String getBoardAbrv() {
		return boardAbrv;
	}

	public void setBoardAbrv(String boardAbrv) {
		this.boardAbrv = boardAbrv;
	}

	public String getOpPostNum() {
		return opPostNum;
	}

	public void setOpPostNum(String opPostNum) {
		this.opPostNum = opPostNum;
	}

	public String getReplyPostNum() {
		return replyPostNum;
	}

	public void setReplyPostNum(String replyPostNum) {
		this.replyPostNum = replyPostNum;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	public String getLatestReplyLink() {
		ReplyBean replyBean = this;
		return String.format("https://boards.4chan.org/%s/thread/%s#p%s", replyBean.getBoardAbrv(), replyBean.getOpPostNum(), replyBean.getReplyPostNum());
	}

	public void print() {
		System.out.println("OP num: "+opPostNum);
		System.out.println("Reply num: "+replyPostNum);
		System.out.println("Reply content: "+replyContent);
	}
}
