package stockanalyzer.main;

import java.util.ArrayList;

import stockanalyzer.controller.StockAPI;
import stockanalyzer.controller.StockController;
import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockEntry;
import stockanalyzer.model.StockModel.TimedValue;


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
		System.out.println("Running test");
		// @Cleanup this whole constructor will be removed, only for testing output from stockmodel atm
		JSONObject myjson = StockAPI.getRequest(new APICallParams("TIME_SERIES_MONTHLY_ADJUSTED",
																	"1min",
																	"MSFT",
																	"JSON",
																	"FULL",
																	StockController.VANTAGE_API_KEY));
		
		StockModel model;
		
		try {
			model = new StockModel(myjson, true);
		} catch (Exception e) {
			e.printStackTrace(); return;
		}

		ArrayList<StockEntry> v = model.getData();
		for(StockEntry tmp : v)
		{
			System.out.println(tmp.time + " : ");
			for(TimedValue tmp2 : tmp.values)
			{
				System.out.println("\t "+tmp2.title +":"+tmp2.value);
			}
		}
	}

	public static void main(String[] args) {
		//Main m = new Main(); // @Testing
		new StockController();
	}

}
