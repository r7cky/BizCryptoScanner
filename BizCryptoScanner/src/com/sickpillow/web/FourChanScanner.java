package com.sickpillow.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sickpillow.models.ReplyBean;

public class FourChanScanner {
	public static List<String> getThreadPostNumsFromBoardCatalog(String fourChanBoardCatalogUrl) throws IOException {
		List<String> postNums = new ArrayList<>();

		//matcher and pattern for getting board abrv
		Pattern boardNamePattern = Pattern.compile("4chan.org/(.+?)/catalog");
		Matcher boardNameMatcher = boardNamePattern.matcher(fourChanBoardCatalogUrl);
		boardNameMatcher.find();
		
		String boardAbrv = boardNameMatcher.group(1);
		//gets board catalog api link
		String link = "https://a.4cdn.org/" + boardAbrv + "/catalog.json";
		System.out.println(String.format("Fetching threads from /%s/...", boardAbrv));
		
		// Connect to the URL using java's native library
		URL url = new URL(link);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); //from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
		JsonArray rootobj = root.getAsJsonArray(); //May be an array, may be an object.

		for (int pageCount = 0; pageCount < rootobj.size(); pageCount++) {
			JsonElement pageElement = rootobj.get(pageCount);
			JsonObject pageObject = pageElement.getAsJsonObject();
			JsonArray threadsArray = pageObject.get("threads").getAsJsonArray();

			for (int threadCount = 0; threadCount < threadsArray.size(); threadCount++) {
				JsonElement threadElement = threadsArray.get(threadCount);
				JsonObject threadObject = threadElement.getAsJsonObject();
				String postNum = threadObject.get("no").toString();

				postNums.add(postNum);
			}
		}

		return postNums;
	}

	public static List<ReplyBean> getReplyBeansFromThreadNumList(List<String> opPostsList, String boardAbrv) throws IOException {
		List<ReplyBean> replyBeans = new ArrayList<>();

		for (String opPostNum : opPostsList) {
			String threadLink = String.format("https://a.4cdn.org/%s/thread/%s.json", boardAbrv, opPostNum);
			String threadPrintLink = String.format("https://boards.4chan.org/%s/thread/%s", boardAbrv, opPostNum);
			System.out.println("Fetching replies from thread: "+threadPrintLink);
			
			// Connect to the URL using java's native library
			URL url = new URL(threadLink);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();

			// Convert to a JSON object to print data
			JsonParser jp = new JsonParser(); //from gson
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
			JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
			JsonArray postsArray = rootobj.get("posts").getAsJsonArray();

			for (int postCount = 0; postCount < postsArray.size(); postCount++) {
				JsonElement postElement = postsArray.get(postCount);
				JsonObject postObject = postElement.getAsJsonObject();

				String replyNum = postObject.get("no").toString();
				String replyContent = null;

				//try to access the post content //if there's no post content (image post) then skip over this iteration
				try {
					replyContent = postObject.get("com").toString();
				}catch (NullPointerException e) {
					continue;
				}

				ReplyBean replyBean = new ReplyBean(boardAbrv, opPostNum, replyNum, replyContent);
				replyBeans.add(replyBean);
			}
		}

		return replyBeans;
	}
	
	//this is never used, but good for testing
	public static List<ReplyBean> getReplyBeansFromThread(String opPostNum, String boardAbrv) throws IOException {
		List<ReplyBean> replyBeans = new ArrayList<>();
		String threadLink = String.format("https://a.4cdn.org/%s/thread/%s.json", boardAbrv, opPostNum);

		// Connect to the URL using java's native library
		URL url = new URL(threadLink);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser(); //from gson
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
		JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
		JsonArray postsArray = rootobj.get("posts").getAsJsonArray();

		for (int postCount = 0; postCount < postsArray.size(); postCount++) {
			JsonElement postElement = postsArray.get(postCount);
			JsonObject postObject = postElement.getAsJsonObject();

			String replyNum = postObject.get("no").toString();
			String replyContent = postObject.get("com").toString();

			ReplyBean replyBean = new ReplyBean(boardAbrv, opPostNum, replyNum, replyContent);
			replyBeans.add(replyBean);
		}

		return replyBeans;
	}


}
