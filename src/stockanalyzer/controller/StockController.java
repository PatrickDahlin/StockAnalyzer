package stockanalyzer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import stockanalyzer.json.JSONObject;
import stockanalyzer.model.APICallParams;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockEntry;
import stockanalyzer.view.StockView;

public class StockController {

	
	
	//TODO make private
	public static String VANTAGE_API_KEY = "XVXEHHDH9BOTXCBQ";
	
	StockView stockView;
	StockModel stockModel;
	StockModel secondSymbolStockModel;

	public StockController() {
		//Main entrypoint of application

        //Commented so they default to null
		//stockModel = new StockModel(); // Create empty StockModel
		//secondSymbolStockModel = new StockModel();
		stockView = new StockView(this);
		
		setupView();
		loadIni();
	}
	
	private void loadIni() {
		Hashtable<String, String[]> ini = new Hashtable<String, String[]>();
		
		// Read in the ini
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("StockAnalyzer.ini"));
			
			// Build ini hashtable out of Properties object
			for(Object a : props.keySet())
			{
				String s = (String)props.get(a);
				ArrayList<String> val = new ArrayList<String>();
				String[] values = s.split(",");
				
				for(int i=0; i < values.length; i++)
					values[i] = values[i].trim();
					
				ini.put((String)a, values);
			}
			System.out.println("Loaded ini file successfully!");
		} catch (Exception e) {
			System.out.println("Reading INI file failed! Using defaults instead.");
			e.printStackTrace();
			// Something went wrong
		}
		
		VANTAGE_API_KEY = ini.getOrDefault("API_KEY", new String[] {VANTAGE_API_KEY})[0];
		String[] time_intervals = ini.getOrDefault("TIME_INTERVAL", new String[] {"1min","5min","15min","30min","60min"});
		String[] output_size = ini.getOrDefault("OUTPUT_SIZE", new String[] {"compact","full"});
		String[] time_series = ini.getOrDefault("TIME_SERIES", new String[] {"TIME_SERIES_INTRADAY",
																				"TIME_SERIES_DAILY",
																				"TIME_SERIES_DAILY_ADJUSTED",
																				"TIME_SERIES_WEEKLY",
																				"TIME_SERIES_WEEKLY_ADJUSTED",
																				"TIME_SERIES_MONTHLY",
																				"TIME_SERIES_MONTHLY_ADJUSTED"});
		String[] symbols = ini.getOrDefault("SYMBOL", new String[] {"MSFT","GOOG"});


        stockView.setInterval(time_intervals);
        stockView.setOutput(output_size);
        stockView.setSymbols(symbols);
        stockView.setSeries(time_series);
        stockView.setApiKeyField(VANTAGE_API_KEY);

	}

	private void setupView() {
		stockView.addQueryListener(e -> doQuery());

		stockView.startDateFocusLoss(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Dun du nutin
            }

            @Override
            public void focusLost(FocusEvent e) {

                if(validateDate(stockView.getStartDateField())){
                    //stockView.setStartDate(gDate);
                    stockView.startDateCorr(true);
                } else {
                    stockView.startDateCorr(false);
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
                    //stockView.setEndDate(gDate);
                    stockView.endDateCorr(true);
                } else {
                    stockView.endDateCorr(false);
                }

            }
        });
		
		stockView.addPearsonCalcListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stockView.setPearsonCorrelation(CorrelationCalc(stockModel.getData(), secondSymbolStockModel.getData(), stockView.getDataSeries()));
			}
		});
	}

	//Checks if Date is a valid date
	private boolean validateDate(String date){

	    //Assuming date format is DD.MM.YYYY
	    date = date.trim();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");       
        dateFormat.setLenient(false);
        
	    try {
            Date dateParse = dateFormat.parse(date);

            //System.out.println("Parsing of date {"+date+"} turned into {"+dateParse+"}");
        } catch (ParseException e){
	        return false;
        }

	    return true;
    }

	// Pearson Correlation
	public double CorrelationCalc(ArrayList<StockEntry> list1, ArrayList<StockEntry> list2, String dataSeries) {
	    double sumX= 0.0, sumY = 0.0, sumXX = 0.0, sumYY = 0.0, sumXY = 0.0;

	    int length = Math.min(list1.size(), list2.size());
	    
	    if(list1.size() != list2.size())
	    	System.out.println("Warning! The two lists used for Pearsons Correlation aren't the same size!");

	    for (int i=0; i<length; i++) {
	    	
	    	// Get the Stock value for the given dataseries
	        float x = list1.get(i).getValueFromSeries(dataSeries).value;
	        float y = list2.get(i).getValueFromSeries(dataSeries).value;

	        sumX+=x;
	        sumY+=y;
	        sumXX+=x*x;
	        sumYY+=y*y;
	        sumXY+=x*y;
	    }

        double cov = sumXY / length - sumX*sumY / length / length;

        double sigmaX = Math.sqrt(sumXX / length - sumX*sumX / length / length);
        double sigmaY = Math.sqrt(sumYY / length - sumY*sumY / length / length);

        return cov/sigmaX/sigmaY;
	}
	
	/**
	 * Updates the view with the new query gotten from the UI
	 */
	private void doQuery() {	
		String ts = stockView.getTimeSeries();
		String interval = stockView.getInterval();
		String symbols[] = stockView.getSymbol();
		String os = stockView.getOutputSize();
		VANTAGE_API_KEY = stockView.getKey();

		boolean symbolAempty = symbols[0].trim().equals("");
		boolean symbolBempty = symbols[1].trim().equals("");
		boolean doubleQuery = !symbolAempty && !symbolBempty;
		
		APICallParams params1 = new APICallParams(ts, interval, symbols[0], "JSON", os, VANTAGE_API_KEY);
        APICallParams params2 = new APICallParams(ts, interval, symbols[1], "JSON", os, VANTAGE_API_KEY);
		
		if (symbolAempty && symbolBempty){
		    System.out.println("No Symbols chosen!");
		    return;
        }

        //Set to null
        stockModel = null;
        secondSymbolStockModel = null;

		if(doubleQuery)
		{
			stockModel = doAPIRequest(params1);
			try{ Thread.sleep(1000); }catch(Exception e) {}
			secondSymbolStockModel = doAPIRequest(params2);
		}
		else
		{
			if(symbolAempty)
				stockModel = doAPIRequest(params2);
			else
				stockModel = doAPIRequest(params1);
		}

		// TODO Calculate Pearsons Correlation if we have both symbols loaded, On buttonpress of Pearsons correlation button

		
		if(validateDate(stockView.getStartDateField()) && validateDate(stockView.getEndDateField()))
		{
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


		    try {
	            Date startDate = dateFormat.parse(stockView.getStartDateField());
	            Date endDate = dateFormat.parse(stockView.getEndDateField());

				StockModel m = filterModelByTimeInterval(stockModel, startDate, endDate);
				StockModel m2 = null;

				if (secondSymbolStockModel != null) {
				    m2 = filterModelByTimeInterval(secondSymbolStockModel, startDate, endDate);
                }

				stockView.setModelData(m, m2);

		    } catch (ParseException e){}
			
		}
		else
			stockView.setModelData(stockModel, secondSymbolStockModel);
	}
	
	/**
	 * Calls the API that does the call and receives the data
	 */
	public StockModel doAPIRequest(APICallParams params) {
		JSONObject myjson = StockAPI.getRequest(params);

		try {
			// Second parameter is either sorted/unsorted
			return new StockModel(myjson, true);
		} catch (Exception e) {
			e.printStackTrace();
			// @TODO this would be nice, not too important
			// view.showError("Internal Error parsing data");
			return null;
		}
		
	}

	private StockModel filterModelByTimeInterval(StockModel model, Date start, Date end)
	{
		
		ArrayList<StockEntry> tempData = model.getData();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0; i < tempData.size(); i++)
		{
			StockEntry entry = tempData.get(i);
			Date entryDate;
			
			
			// We got 2 different timeformats possible, try them both
			
			try {
				Date d = df.parse(entry.time);
				
				// Make sure date is ok
				if(!d.after(start) || !d.before(end))
				{
					//System.out.println("Time: "+d+" is not within the range "+start+" and "+ end);
					// Remove this entry, it's outside timespan
					tempData.remove(entry);
					i--;
				}
				
			} catch (ParseException e) {
				try {
					Date d = df2.parse(entry.time);
					
					// Make sure date is ok
					if(!d.after(start) || !d.before(end))
					{
						//System.out.println("Time: "+d+" is not within the range "+start+" and "+ end);
						// Remove this entry, it's outside timespan
						tempData.remove(entry);
						i--;
					}
				
				} catch(ParseException e2) {
					//e2.printStackTrace();
				}
				//e.printStackTrace();
			}
			
		}
		
		return new StockModel(tempData,model.getSymbol());
	}
	
}
