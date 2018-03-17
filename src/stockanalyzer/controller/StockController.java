package stockanalyzer.controller;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.APICallParams.DataType;
import stockanalyzer.model.APICallParams.Interval;
import stockanalyzer.model.APICallParams.OutputSize;
import stockanalyzer.model.APICallParams.TimeSeries;
import stockanalyzer.view.StockView;

public class StockController {

	//@TODO make private
	public static final String VANTAGE_API_KEY = "XVXEHHDH9BOTXCBQ";
	
	StockView stockView;
	StockModel stockModel;
	
	public StockController()
	{
		//Main entrypoint of application
		
		stockModel = new StockModel(null); // Create empty StockModel
		stockView = new StockView(this);
		
		doAPIRequest(new APICallParams(TimeSeries.TIME_SERIES_INTRADAY,
				Interval.FifteenMin,
				"MSFT",
				DataType.JSON,
				OutputSize.FULL,
				StockController.VANTAGE_API_KEY));
	}
	
	public void doAPIRequest(APICallParams params)
	{
		JSONObject myjson = StockAPI.getRequest(params);

		try {
			// Second parameter is either sorted/unsorted
			stockModel = new StockModel(myjson, true);
		} catch (Exception e) {
			//e.printStackTrace(); 
			// @TODO this would be nice, not too important
			// view.showError("Internal Error parsing data");
			return;
		}
		
		stockView.setModelData(stockModel);
		
	}
	
}
