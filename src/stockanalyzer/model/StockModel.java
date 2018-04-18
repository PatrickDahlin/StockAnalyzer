package stockanalyzer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import stockanalyzer.json.JSONObject;

/*
 * Immutable class containing all the stock data
*/
public class StockModel {
	
	// One value in the list of values returned by api
	public class TimedValue
	{
		public String title;
		public float value;
		public TimedValue(String str, float val)
		{
			title = str;
			value = val;
		}
	}
	
	// This represents a stock value at a given time, it has multiple values (high,low,...)
	public class StockEntry {
		public String time = "";
		public ArrayList<TimedValue> values = new ArrayList<TimedValue>();
	}
	
	
	// The actual model content is kept in this array
	private ArrayList<StockEntry> data;
	// ---
	
	// Create a unsorted model
	public StockModel(JSONObject root) { this(root, false); }
	
	// Create a model with sorted? data
	public StockModel(JSONObject root, boolean sorted)
	{
		data = new ArrayList<StockEntry>();
	
		if(root == null) return; // We just want an empty model
		
		try {
			parseJSON(root);
		} catch (Exception e) {
			System.err.println("StockModel was constructed with invalid JSON data!");
			e.printStackTrace();
		}
		
		if(!sorted) return;
		
		Collections.sort(data, new Comparator<StockEntry>() {
	        @Override
	        public int compare(StockEntry entry1, StockEntry entry2)
	        {

	            return  entry2.time.compareTo(entry1.time);
	        }
	    });
	}
	
	public ArrayList<StockEntry> getData()
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
		
		Iterator<String> i = root.keys();
		if(!i.hasNext()) {
			System.out.println("No data");
			return;
		}
		String data_key = i.next(); // Gets the Data String
		// In some cases the metadata is in the beginning......
		while(i.hasNext() && data_key.equals("Meta Data")) {
			data_key = i.next();
		}

		
		if(!root.has(data_key)) {
			System.out.println("Parsing error: "+data_key+" couldn't be found!");
			return;
		}
		JSONObject tmp = root.getJSONObject(data_key);
		if(tmp == null) throw new Exception("Malformed JSON Object");
		// Now we have the JSON-Object for the Data container
		
		
		for(String time : tmp.keySet())
		{
			// For each Timestamp
			JSONObject child = tmp.getJSONObject(time);
			
			if(child == null) break;
			
			//System.out.println("Time: "+time);
			
			// Make a stockvalue
			StockEntry stockVal = new StockEntry();
			stockVal.time = time;

			// And loop it's children to fill in it's values
			for(String values : child.keySet())
			{
				System.out.println("\t" + values + " : " + child.get(values));
				String value = child.getString(values);
				stockVal.values.add(new TimedValue(values, Float.parseFloat(value)));
			}
			data.add(stockVal);
		}
		
	}

}
