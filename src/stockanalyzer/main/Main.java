package stockanalyzer.main;

import java.util.ArrayList;

import stockanalyzer.controller.StockAPI;
import stockanalyzer.controller.StockController;
import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.APICallParams.DataType;
import stockanalyzer.model.APICallParams.Interval;
import stockanalyzer.model.APICallParams.OutputSize;
import stockanalyzer.model.APICallParams.TimeSeries;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockValue;


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
		JSONObject myjson = StockAPI.getRequest(new APICallParams(TimeSeries.TIME_SERIES_INTRADAY,
																	Interval.OneMin,
																	"MSFT",
																	DataType.JSON,
																	OutputSize.FULL,
																	StockController.VANTAGE_API_KEY));
		
		StockModel model;
		
		try {
			model = new StockModel(myjson, true);
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
		//Main m = new Main(); // @Testing
		new StockController();
        //StockView tester = new StockView(null);
        //tester.test();
	}

}
