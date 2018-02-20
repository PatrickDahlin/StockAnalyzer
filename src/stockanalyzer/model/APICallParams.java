package stockanalyzer.model;

public class APICallParams {
	private String url;
	private String apiKey;
	private TimeSeries series; // works as function in call
	private String symbol;
	private DataType datatype; // should always be JSON though
	private OutputSize size;
	
	public APICallParams(String URL, TimeSeries timeSeries, String symbol, DataType type, OutputSize output, String APIKey) {
		
	}
	
	public enum DataType { JSON, CSV }
	public enum OutputSize { COMPACT, FULL }
	public enum TimeSeries {
		TIME_SERIES_INTRADAY, TIME_SERIES_DAILY,
		TIME_SERIES_DAILY_ADJUSTED, TIME_SERIES_WEEKLY, TIME_SERIES_WEEKLY_ADJUSTED,
		TIME_SERIES_MONTHLY, TIME_SERIES_MONTHLY_ADJUSTED
	}
	
	// TIME_SERIES_INTRADAY, timeseries/function(req), symbol(req), interval(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_DAILY, 			function(req), symbol(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_DAILY_ADJUSTED, 	function(req), symbol(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_WEEKLY, 			function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_WEEKLY_ADJUSTED, function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_MONTHLY, 		function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_MONTHLY_ADJUSTED,function(req), symbol(req), datatype(opt), apikey(req)
	
}
