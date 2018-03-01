package stockanalyzer.controller;

import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.view.StockView;

public class StockController {

	private static final String VANTAGE_API_KEY = "XVXEHHDH9BOTXCBQ";
	
	StockView stockView;
	StockModel stockModel;
	
	public StockController()
	{
		//Main entrypoint of application
		
		stockModel = new StockModel(null); // Create empty StockModel
		stockView = new StockView(this);
		
	}
		
	//@TODO methods to alter model and update view
	
	private String getAPIData(APICallParams params)
	{
		return "";
	}
	
}
