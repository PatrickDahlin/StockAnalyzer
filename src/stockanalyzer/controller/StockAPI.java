package stockanalyzer.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockEntry;

public class StockAPI {
	
	public static JSONObject getRequest(APICallParams params)
	{
		// "function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=15min&outputsize=full&apikey=XVXEHHDH9BOTXCBQ"
		StringBuilder url_str = new StringBuilder();
		url_str.append("https://www.alphavantage.co/query?");
		
		switch(params.getTimeSeries())
		{
		case TIME_SERIES_INTRADAY:
			url_str.append("function=TIME_SERIES_INTRADAY");
			url_str.append("&symbol=");
			url_str.append(params.getSymbol());
			url_str.append("&interval=");
			url_str.append(params.getInterval());
			url_str.append("&outputsize=");
			url_str.append(params.getOutputSize());
			url_str.append("&apikey=");
			url_str.append(params.getAPIKey());
			url_str.append("&datatype=");
			url_str.append(params.getDataType());
			break;
		case TIME_SERIES_DAILY_ADJUSTED:
		case TIME_SERIES_DAILY:
			url_str.append("function=");
			url_str.append(params.getTimeSeries().toString());
			url_str.append("&symbol=");
			url_str.append(params.getSymbol());
			url_str.append("&outputsize=");
			url_str.append(params.getOutputSize());
			url_str.append("&apikey=");
			url_str.append(params.getAPIKey());
			url_str.append("&datatype=");
			url_str.append(params.getDataType());
			break;	
		default:
			url_str.append("function=");
			url_str.append(params.getTimeSeries().toString());
			url_str.append("&symbol=");
			url_str.append(params.getSymbol());
			url_str.append("&apikey=");
			url_str.append(params.getAPIKey());
			url_str.append("&datatype=");
			url_str.append(params.getDataType());
			break;
		}
		
		//System.out.println("final request url:");
		//System.out.println(url_str.toString());
		
		String response = "";
		try {
			URL url = new URL(url_str.toString());
		
			URLConnection con = (URLConnection)url.openConnection();
			
			BufferedReader br =
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));

			String input;

			while ((input = br.readLine()) != null)
				response += input + "\n";
			
			br.close();
			
		} catch (Exception e) {
			System.out.println("API Request failed!");
			e.printStackTrace();
			return new JSONObject();
		}
		
		return new JSONObject(response);
	}
/*

 String response = "";
		try {
			URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=15min&outputsize=full&apikey=XVXEHHDH9BOTXCBQ");
		
			URLConnection con = (URLConnection)url.openConnection();
			
			BufferedReader br =
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));

			String input;

			while ((input = br.readLine()) != null)
				response += input + "\n";
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject myjson = new JSONObject(response);
		
		StockModel model;
		
		try {
			model = new StockModel(myjson);
		} catch (Exception e) {
			e.printStackTrace(); return;
		}

		ArrayList<StockValue> v = model.getData();
		for(StockValue tmp : v)
		{
			System.out.println(tmp.time + " : " + tmp.high);
		}
 
*/
}
