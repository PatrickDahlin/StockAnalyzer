package stockanalyzer.model;

import java.util.ArrayList;

import stockanalyzer.json.JSONObject;

/*
 * Immutable class containing all the stock data
*/
public class StockModel {
	//@TODO Put all the data needed for the application
	// eg. List of stock values returned by API
	
	
	// This represents one value at a given time for a stock
	public class StockValue {
		public String time;
		public float high, low, open, close;
		public long volume;
	}
	
	
	// The actual content is kept in this array
	private ArrayList<StockValue> data;
	// ---
	
	
	public StockModel(JSONObject root)
	{
		data = new ArrayList<StockValue>();
	
		if(root == null) return; // We just want an empty model
		
		try {
			parseJSON(root);
		} catch (Exception e) {
			System.err.println("StockModel was constructed with invalid JSON data!");
		}
	}
	
	public ArrayList<StockValue> getData()
	{
		return data;
	}
	
	// Parse JSON Data gotten from API
	private void parseJSON(JSONObject root) throws Exception
	{
		//
		// Formatting the input data
		//
		
		// We're expecting the data in the form of
		// Level 0:  { Data, MetaData }
		// Data: { Time : Values }
		// Values: { "value category", "value" }
		
		//
		//	Data {
		//		Time {
		//			"high" , 99999
		//			"low"  , 00000 ...
		//		}, ...
		//	},
		//	MetaData {
		//		...
		//	}
		//
		
		// Get the data, we don't need metadata
		if(!root.keySet().iterator().hasNext()) throw new Exception("Malformed JSON Object");
		
		String data_key = root.keySet().iterator().next(); // Gets the Data String
		
		JSONObject tmp = root.optJSONObject(data_key);
		if(tmp == null) throw new Exception("Malformed JSON Object");
		// Now we have the JSON-Object for the Data container
		
		for(String time : tmp.keySet())
		{
			// For each Timestamp
			JSONObject child = tmp.optJSONObject(time);
			if(child == null || child.keySet() == null) break;
			
			System.out.println("Time: "+time);
			
			// Make a stockvalue
			StockValue stockVal = new StockValue();
			stockVal.time = time;
			
			// And loop it's children to fill in it's values
			for(String values : child.keySet())
			{
				System.out.println("\t" + values + " : " + child.get(values));
				String value = child.getString(values);
				if(values.contains("high"))  stockVal.high = Float.parseFloat(value);
				if(values.contains("low"))   stockVal.low = Float.parseFloat(value);
				if(values.contains("open"))  stockVal.open = Float.parseFloat(value);
				if(values.contains("close")) stockVal.close = Float.parseFloat(value);
				if(values.contains("volume"))stockVal.volume = Long.parseLong(value);
			}
			data.add(stockVal);
		}
		
	}

}
