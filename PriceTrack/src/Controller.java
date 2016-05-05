import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Controller {
	
	static JSONObject historicalDataJson(String asin, int days) throws Exception{
		String tracktorSN = getTracktorSN(asin);
		String dataUrl = "https://thetracktor.com/ajax/prices/?id="+tracktorSN+"&days="+days;
		String urlDataString = urlDataString(dataUrl);
		return new JSONObject(urlDataString);
	}
	
	static String getUrlContents(String theUrl) throws IOException
	  {
		URL url = new URL(theUrl);
		Scanner sc = new Scanner(url.openStream(), "UTF-8");
		String urlContent = sc.useDelimiter("\\A").next();
		sc.close();
		return urlContent;
	  }
	
	static String urlDataString(String dataUrl) throws IOException{
		String urlContent = null;
		URL url = new URL(dataUrl);
		URLConnection conn = url.openConnection();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
		   urlContent = reader.lines().collect(Collectors.joining("\n"));
		}
		return urlContent;
	}
		
	static String getTracktorSN(String asin) throws IOException{
		String tracktorQueryResaults = "", tracktorSN = "";
		Document doc = Jsoup.connect("https://thetracktor.com/detail/"+asin+"/").timeout(10000).get();
		Elements scriptElements = doc.getElementsByTag("script");
		Pattern pattern = Pattern.compile("Tracktor\\.trackItem\\((.*?),");
		
		for (Element element : scriptElements ){                
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
	
	static String convertDateFromMilis(long dateInMilis){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(dateInMilis);
		return sdf.format(resultdate);
	}
}