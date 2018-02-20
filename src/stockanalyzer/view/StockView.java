package stockanalyzer.view;

import stockanalyzer.controller.StockController;

public class StockView {
	
	private StockController controller;
	
	public StockView(StockController contrlr)
	{
		controller = contrlr;
		//@TODO initialize window (Swing? FX?)
		// set up listeners for buttons and connect them to controller
	}
}
