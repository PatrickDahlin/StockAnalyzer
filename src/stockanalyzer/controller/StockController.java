package stockanalyzer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.view.StockView;

import javax.swing.*;

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

		stockView.startDateFocusLoss(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Dun du nutin
            }

            @Override
            public void focusLost(FocusEvent e) {

                if(validateDate(stockView.getStartDateField())){
                    System.out.println("True");
                } else {
                    System.out.println("False");
                }

            }
        });

		stockView.endDateFocusLoss(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Dun du nutin
            }

            @Override
            public void focusLost(FocusEvent e) {

                if(validateDate(stockView.getEndDateField())){
                    System.out.println("True");
                } else {
                    System.out.println("False");
                }

            }
        });
	}

	//Checks if Date is a valid date
	private boolean validateDate(String date){

	    //Assuming date format is DD.MM.YYYY

	    date.trim();

	    String[] dateSplit = date.split("[.]");

	    //Check if date is split into 3 parts, DD.MM.YYYY
	    if(dateSplit.length != 3) return false;

	    //Checks if it's in int
        for(int i = 0; i != dateSplit.length; i++){
            try {
                Integer.parseInt(dateSplit[i]);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        //Checks days
        if(Integer.parseInt(dateSplit[0]) <= 0 || Integer.parseInt(dateSplit[0]) >= 32) return false;

        //Checks months
        if(Integer.parseInt(dateSplit[1]) <= 0 || Integer.parseInt(dateSplit[1]) >= 13) return false;

        //Check that year has 4 chars
        if(dateSplit[2].length() != 4) return false;

	    return true;
    }

	/**
	 * Updates the view with the new query gotten from the UI
	 */
	private void doQuery() {	
		String ts = stockView.getTimeSeries();
		String interval = stockView.getInterval();
		String symbol = stockView.getSymbol();
		String os = stockView.getOutputSize();
		
		APICallParams params = new APICallParams(ts, interval, symbol, "JSON", os, VANTAGE_API_KEY);
		
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
