package stockanalyzer.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockValue;
import stockanalyzer.view.StockView;


//
//	Stock Analyzer
//
//  Patrick Dahlin (41117)
//  Linus Wiberg   (42101)
//
// JSON Parser from : https://github.com/stleary/JSON-java

///
///	Moment 1: View & ability to fetch data from HTTPS API
/// Moment 2: Sort input data by Date
/// Moment 3: Make time-series dynamic, aka no hardcoded urls
/// Moment 4: Make input data depend on time-series chosen, some values should be optional or not avaliable
/// Moment 5: Build a graph out of selected Symbol stock values next to inputs
///

public class Main {

	public Main()
	{
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
	}
	
	public static void main(String[] args) {
		Main m = new Main(); // @Testing
		//new StockController();
		StockView.test();
	}

}
