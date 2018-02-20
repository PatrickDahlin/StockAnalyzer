package stockanalyzer.main;

import stockanalyzer.controller.StockController;


//
//	Stock Analyzer
//
//  Patrick Dahlin (41117)
//  Linus Wiberg   (@TODO)
//

///
///	Moment 1: View & ability to fetch data from HTTPS API
/// Moment 2: Sort input data by Date
/// Moment 3: Make time-series dynamic, aka no hardcoded urls
/// Moment 4: Make input data depend on time-series chosen, some values should be optional or not avaliable
/// Moment 5: Build a graph out of selected Symbol stock values next to inputs
///

public class Main {

	public static void main(String[] args) {
		new StockController();
	}

}
