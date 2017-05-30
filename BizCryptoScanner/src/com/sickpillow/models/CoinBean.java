package com.sickpillow.models;

import java.util.List;

public class CoinBean implements java.lang.Comparable<CoinBean> {
	private String coinNameFull;
	private String coinNameAbrv;
	private int occorunces;
	private ReplyBean latestReply;
	
	public CoinBean() {}
	
	public CoinBean(String coinNameFull, String coinNameAbrv, int occorunces) {
		super();
		this.coinNameFull = coinNameFull;
		this.coinNameAbrv = coinNameAbrv;
		this.occorunces = occorunces;
	}
	
	public CoinBean(String coinNameFull, String coinNameAbrv, int occorunces, ReplyBean latestReply) {
		super();
		this.coinNameFull = coinNameFull;
		this.coinNameAbrv = coinNameAbrv;
		this.occorunces = occorunces;
		this.latestReply = latestReply;
	}
	
	public String getCoinNameFull() {
		return coinNameFull;
	}
	
	public void setCoinNameFull(String coinNameFull) {
		this.coinNameFull = coinNameFull;
	}
	
	public String getCoinNameAbrv() {
		return coinNameAbrv;
	}
	
	public void setCoinNameAbrv(String coinNameAbrv) {
		this.coinNameAbrv = coinNameAbrv;
	}
	
	public int getOccorunces() {
		return occorunces;
	}
	
	public void setOccorunces(int occorunces) {
		this.occorunces = occorunces;
	}
	
	public ReplyBean getLatestReply() {
		return latestReply;
	}
	
	public void setLatestReply(ReplyBean latestReply) {
		this.latestReply = latestReply;
	}
	
	public void print() {
		System.out.println("Coin name: "+this.getCoinNameFull());
		System.out.println("Coin abrv: "+this.getCoinNameAbrv());
		System.out.println("Coin occorunces: "+this.getOccorunces());
	}
	
	public void printOccorunces(boolean printIfNull) {
		if (this.getOccorunces() > 0 && !printIfNull) {
			System.out.println(String.format("%s (%s): %s", this.getCoinNameFull(), this.getCoinNameAbrv(), this.getOccorunces()));
		}
	}
	
	public void printOccoruncesWithLatestReply(boolean printIfNull) {
		if (this.getOccorunces() > 0 && !printIfNull) {
			System.out.println(String.format("%s (%s): %s           [Latest post: %s]", this.getCoinNameFull(), this.getCoinNameAbrv(), this.getOccorunces(), this.getLatestReply().getLatestReplyLink()));
		}
	}
	
	public void setOccoruncesWithStringList(List<String> stringList) {
		//adds spaces so it only searches when the coin name is used in word form
		String coinNameWord  = " "+this.getCoinNameFull()+" ";
		String coinNameAbrvWord  = " "+this.getCoinNameAbrv()+" ";
		
		for (String rawString : stringList) {
			if (rawString.toLowerCase().contains(coinNameWord.toLowerCase())) {
				occorunces++;
			} else if (rawString.toLowerCase().contains(coinNameAbrvWord.toLowerCase())) {
				//time etc, and game are often high up because they are regularly used words, this fixes it by avoiding it
				if (!coinNameAbrv.toLowerCase().equals("time") && !coinNameAbrv.toLowerCase().equals("etc") && !coinNameAbrv.toLowerCase().equals("game") && !coinNameAbrv.toLowerCase().equals("moon")) {
					occorunces++;
				}
			}
		}
	}
	
	public void setOccoruncesWithReplyBeanList(List<ReplyBean> replyBeans) {
		//adds spaces so it only searches when the coin name is used in word form
		String coinNameWord  = " "+this.getCoinNameFull()+" ";
		String coinNameAbrvWord  = " "+this.getCoinNameAbrv()+" ";
		
		for (ReplyBean replyBean : replyBeans) {
			String replyContent = replyBean.getReplyContent();
			
			if (replyContent.toLowerCase().contains(coinNameWord.toLowerCase())) {
				occorunces++;
				this.setLatestReply(replyBean);
			} else if (replyContent.toLowerCase().contains(coinNameAbrvWord.toLowerCase())) {
				if (!coinNameAbrv.toLowerCase().equals("time") && !coinNameAbrv.toLowerCase().equals("etc") && !coinNameAbrv.toLowerCase().equals("game") && !coinNameAbrv.toLowerCase().equals("moon")) {
					occorunces++;
					this.setLatestReply(replyBean);
				}
			}
		}
	}
	
	@Override
	public int compareTo(CoinBean bean) {
		return bean.getOccorunces()-this.getOccorunces();
	}
}
