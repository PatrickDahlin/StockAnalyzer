package stockanalyzer.controller;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
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
		
		// Give StockView the model data
		// I'd give the view a String of the data for the data-textbox
		// Aswell as a float[] for the graph
	}
	
}
