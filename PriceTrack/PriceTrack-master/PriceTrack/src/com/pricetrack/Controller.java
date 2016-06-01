package com.pricetrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Controller {

	public static JSONObject getHistoricalDataJson(String asin, int days) throws Exception
	{
		String tracktorSN = getTracktorSN(asin);
		String dataUrl = "https://thetracktor.com/ajax/prices/?id="+tracktorSN+"&days="+days;
		String urlDataString = getUrlDataString(dataUrl);
		return new JSONObject(urlDataString);
	}

	public static String getUrlContents(String theUrl) throws IOException
	{
		URL url = new URL(theUrl);
		Scanner sc = new Scanner(url.openStream(), "UTF-8");
		String urlContent = sc.useDelimiter("\\A").next();
		sc.close();
		return urlContent;
	}

	public static String convertDateFromMilis(long dateInMilis)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(dateInMilis);
		return sdf.format(resultdate);
	}
	
	public static Date convertDateFromMilisAsDate(double dateInMilis)
	{
		Date resultdate = new Date(((long)dateInMilis));
		return resultdate;
	}

	private static String getUrlDataString(String dataUrl) throws IOException
	{
		String urlContent = null;
		URL url = new URL(dataUrl);
		URLConnection conn = url.openConnection();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
			urlContent = reader.lines().collect(Collectors.joining("\n"));
		}
		return urlContent;
	}

	private static String getTracktorSN(String asin) throws IOException
	{
		String tracktorQueryResaults = "", tracktorSN = "";
		Document doc = Jsoup.connect("https://thetracktor.com/detail/"+asin+"/").timeout(10000).get();
		Elements scriptElements = doc.getElementsByTag("script");
		Pattern pattern = Pattern.compile("Tracktor\\.loadPrices\\( (.*?),");

		for (Element element : scriptElements ) {                
			for (DataNode node : element.dataNodes()) {
				tracktorQueryResaults = node.getWholeData();
				Matcher matcher = pattern.matcher(tracktorQueryResaults);
				while (matcher.find()) {
					tracktorSN = matcher.group(1);
				}
			}
		}
		return tracktorSN;
	}
}
