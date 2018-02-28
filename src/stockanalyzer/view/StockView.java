package stockanalyzer.view;

import stockanalyzer.controller.StockController;

public class StockView {
	
	private StockController controller;
	
	public StockView(StockController contrlr)
	{
		controller = contrlr;
		//@TODO initialize window (Swing? FX?)
	}

	public static void test(){
		System.out.println("Twat");
	} //PLACEHOLDER UNTIL I KNOW HOW THE VIEW WILL RUN. I JUST RUN FROM MAIN NOW. HUMAN BENIS
}
