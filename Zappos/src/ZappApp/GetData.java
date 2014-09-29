package ZappApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONException;
import org.json.simple.*;
import org.json.simple.parser.*;


public class GetData {
	
	public static final String root = "http://api.zappos.com/Search?key=52ddafbe3ee659bad97fcce7c53592916a6bfd73";
	
	
	
//	  Sends a get request to the given URL and returns the result
//	  Code from http://rest.elkstein.org/2008/02/using-rest-in-java.html
//	  @param urlStr The URL to send a get request to
//	  @return The response from the server as a String
	 
	public static String httpGet(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn =
		      (HttpURLConnection) url.openConnection();
	
		if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		}
	
		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(
	      new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
	
		conn.disconnect();
		return sb.toString();
	}
	
	
//	  Parses the search API's JSON response into a JSON object
//	  @param reply The string of the server's JSON response
//	  @return The parsed JSON object
	 
	public static JSONObject parseReply(String reply) throws org.json.simple.parser.ParseException{
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(reply);
		JSONObject object = (JSONObject)obj;
		return object;
	}
	
	
//	  Gets the array out of the JSON object the server returns
//	  @param reply The JSON object form of the server's response
//	  @return The JSONArray of the results portion

	 
	public static JSONArray getResults(JSONObject reply) throws JSONException{
		Object resultObject = reply.get("results");	
		JSONArray resultArray = (JSONArray)resultObject;
		return resultArray;
	}	

}
