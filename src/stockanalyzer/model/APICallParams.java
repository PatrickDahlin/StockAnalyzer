package stockanalyzer.model;

public class APICallParams {

	private String apiKey;
	private TimeSeries series; // works as function in call
	private Interval interval;
	private String symbol;
	private DataType datatype; // should always be JSON though
	private OutputSize size;
	
	public APICallParams(TimeSeries timeSeries, Interval interval, String symbol, DataType type, OutputSize output, String APIKey) {
		apiKey = APIKey;
		series = timeSeries;
		this.interval = interval;
		this.symbol = symbol;
		datatype = type;
		size = output;
	}
	
	public TimeSeries getTimeSeries() { return series; }
	public String getSymbol() { return symbol; }
	public DataType getDataType() { return datatype; }
	public OutputSize getOutputSize() { return size; }
	public String getAPIKey() { return apiKey; }
	public Interval getInterval() { return interval; }
	
	public enum DataType { JSON, CSV }
	public enum OutputSize { COMPACT, FULL }
	public enum TimeSeries {
		TIME_SERIES_INTRADAY, TIME_SERIES_DAILY,
		TIME_SERIES_DAILY_ADJUSTED, TIME_SERIES_WEEKLY, TIME_SERIES_WEEKLY_ADJUSTED,
		TIME_SERIES_MONTHLY, TIME_SERIES_MONTHLY_ADJUSTED
	}
	public enum Interval {
		OneMin("1min"), FiveMin("5min"), FifteenMin("15min"), ThirtyMin("30min"), SixtyMin("60min");
	
		String value;
		Interval(String v) { this.value = v; }
		@Override
		public String toString() { return value; }
	}
	
	// TIME_SERIES_INTRADAY, timeseries/function(req), symbol(req), interval(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_DAILY, 			function(req), symbol(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_DAILY_ADJUSTED, 	function(req), symbol(req), outputsize(opt), datatype(opt), apikey(req)
	// TIME_SERIES_WEEKLY, 			function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_WEEKLY_ADJUSTED, function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_MONTHLY, 		function(req), symbol(req), datatype(opt), apikey(req)
	// TIME_SERIES_MONTHLY_ADJUSTED,function(req), symbol(req), datatype(opt), apikey(req)
	
}
