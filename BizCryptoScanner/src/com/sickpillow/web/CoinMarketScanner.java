package com.sickpillow.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sickpillow.models.CoinBean;

public class CoinMarketScanner {
	public static List<CoinBean> getCoinMarketBeanList(String coinMarketUrl) throws IOException {
		System.out.println("Fetching coins from: "+coinMarketUrl);
		
		Document coinMarketDocRaw = Jsoup.connect(coinMarketUrl).userAgent(Scanner.USERAGENT).referrer(Scanner.REFERRER).timeout(30*1000).get();
		Element coinMarketTbodyElement = coinMarketDocRaw.select("tbody").first();
		
		Elements allIdElements = coinMarketTbodyElement.select("tr");
		List<CoinBean> coinBeans = new ArrayList<>();

		for (Element idElement : allIdElements) {
			try {
				String coinName = idElement.select("a").first().text();

				Element coinAbrvElement = idElement.select("a[target=_blank]").first();
				String coinAbrv = coinAbrvElement.select("a").first().text();
				coinAbrv = coinAbrv.replaceAll("[^a-zA-Z]+", "");

				CoinBean coinBean = new CoinBean(coinName, coinAbrv, 0);
				coinBeans.add(coinBean);
			} catch (NullPointerException e) {
				//if the coin doesn't have a link, then it crashes. Fix this later
			}
		}

		return coinBeans;
	}
}
