package stockanalyzer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public StockController() {
		//Main entrypoint of application
		
		stockModel = new StockModel(null); // Create empty StockModel
		stockView = new StockView(this);
		
		setupView();
	}
	
	private void setupView() {
		stockView.addQueryListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			doQuery();
		}});
	}
	
	/**
	 * Updates the view with the new query gotten from the UI
	 */
	private void doQuery() {	
		TimeSeries ts = stockView.getTimeSeries();
		Interval interval = stockView.getInterval();
		String symbol = stockView.getSymbol();
		OutputSize os = stockView.getOutputSize();
		
		APICallParams params = new APICallParams(ts, interval, symbol, DataType.JSON, os, VANTAGE_API_KEY);
		
		doAPIRequest(params);
		
		stockView.setModelData(stockModel);
	}
	
	/**
	 * Calls the API that does the call and receives the data
	 */
	public void doAPIRequest(APICallParams params) {
		JSONObject myjson = StockAPI.getRequest(params);

		try {
			// Second parameter is either sorted/unsorted
			stockModel = new StockModel(myjson, true);
		} catch (Exception e) {
			e.printStackTrace();
			// @TODO this would be nice, not too important
			// view.showError("Internal Error parsing data");
			return;
		}
		
	}
	
}
