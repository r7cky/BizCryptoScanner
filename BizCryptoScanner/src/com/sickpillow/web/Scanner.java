package com.sickpillow.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.sickpillow.models.CoinBean;
import com.sickpillow.models.ReplyBean;

public class Scanner {
	final public static String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
	final public static String REFERRER = "http://www.google.com";
	
	public static void getCoinMarketResultsWithFourChanBoardCatalog(String fourChanBoardCatalog) throws IOException {
		List<CoinBean> coinBeans = CoinMarketScanner.getCoinMarketBeanList("https://coinmarketcap.com");
		List<String> opPostNums = FourChanScanner.getThreadPostNumsFromBoardCatalog(fourChanBoardCatalog);
		List<ReplyBean> replyBeans = FourChanScanner.getReplyBeansFromThreadNumList(opPostNums, "biz");
		
		coinBeans = getOccoruncesWithCoinsAndReplyBeans(coinBeans, replyBeans);
		Collections.sort(coinBeans);
		
		System.out.println("\n==RESULTS==\n");
		for (CoinBean coinBean : coinBeans) {
			coinBean.printOccoruncesWithLatestReply(false);
		}
	}
	
	private static List<CoinBean> getOccoruncesWithCoinsAndReplyBeans(List<CoinBean> coinBeans, List<ReplyBean> replyBeans) {
		for (int i = 0; i < coinBeans.size(); i++) {
			CoinBean coinBean = coinBeans.get(i);

			int occoruncesBefore = coinBean.getOccorunces();
			coinBean.setOccoruncesWithReplyBeanList(replyBeans);
			if (occoruncesBefore != coinBean.getOccorunces()) { //if the occorunces changed, then replace current coin in list with new one 
				coinBeans.set(i, coinBean);
			}
		}
		
		return coinBeans;
	}
}
