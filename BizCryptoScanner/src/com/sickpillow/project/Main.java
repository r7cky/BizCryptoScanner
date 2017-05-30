package com.sickpillow.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sickpillow.models.CoinBean;
import com.sickpillow.models.ReplyBean;
import com.sickpillow.web.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner.getCoinMarketResultsWithFourChanBoardCatalog("https://boards.4chan.org/biz/catalog");
	}
}