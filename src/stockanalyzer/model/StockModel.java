package stockanalyzer.model;

import java.util.ArrayList;

import stockanalyzer.json.JSONObject;

/*
 * Immutable class returned from the method parsing the data
*/
public class StockModel {
	//@TODO Put all the data needed for the application
	// eg. List of stock values returned by API
	
	public class StockValue {
		public String time;
		public float high, low, open, close;
		public long volume;
	}
	
	private ArrayList<StockValue> data;
	
	
	public StockModel(ArrayList<StockValue> in)
	{
		data = new ArrayList<StockValue>();
		data = in;
	}
	
	public StockModel(JSONObject root) throws Exception
	{
		data = new ArrayList<StockValue>();
		parseJSON(root);
	}
	
	public ArrayList<StockValue> getData()
	{
		return data;
	}
	
	private void parseJSON(JSONObject root) throws Exception
	{
		//
		// Formatting the input data
		//
		
		// We're expecting the data in the form of
		// Level 0:  Data, MetaData
		// Data: { Time : Values }
		// Values: { "value category", "value" }
		
		// Get the data, we don't need metadata
		if(!root.keySet().iterator().hasNext()) throw new Exception("Malformed JSON Object");
		String data_key = root.keySet().iterator().next();
		
		JSONObject tmp = root.optJSONObject(data_key);
		if(tmp == null) throw new Exception("Malformed JSON Object");
		
		for(String time : tmp.keySet())
		{
			
			JSONObject child = tmp.optJSONObject(time);
			if(child == null || child.keySet() == null) break;
			
			System.out.println("Time: "+time);
			
			StockValue stockVal = new StockValue();
			stockVal.time = time;
			
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
