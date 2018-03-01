package stockanalyzer.view;

import stockanalyzer.controller.StockController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StockView {
	
	private StockController controller;
	
	public StockView(StockController contrlr)
	{
		controller = contrlr;
		//@TODO initialize window (Swing? FX?)
	}

	public static void test(){
	    //Creates frame
        JFrame stockView = new JFrame("StockView");
        stockView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Created and adds JPanel to frame
        JPanel compPanel = new JPanel();
        stockView.add(compPanel);
        compPanel.setBorder(new EmptyBorder(10,10,10,10));

        //Layout Settings
        GridBagLayout stockLayout = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        compPanel.setLayout(stockLayout);
        cons.fill = GridBagConstraints.HORIZONTAL;

        //Default Constraints
        cons.weighty = 1.0;
        cons.ipadx = 100;

        //Create border to add spacing between components
        EmptyBorder spaceBorder = new EmptyBorder(5,0,5,0);

        //Adds components
        cons.gridx = 0;
        cons.gridy = 0;
        JLabel datSerLbl = new JLabel("Data Series:");
        datSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerLbl, cons);
        compPanel.add(datSerLbl);

        cons.gridx = 2;
        cons.gridy = 0;
        JComboBox datSerBox = new JComboBox();
        datSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerBox, cons);
        compPanel.add(datSerBox);

        cons.gridx = 0;
        cons.gridy = 1;
        JLabel timSerLbl = new JLabel("Time Series:");
        timSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerLbl, cons);
        compPanel.add(timSerLbl);

        cons.gridx = 2;
        cons.gridy = 1;
        JComboBox timSerBox = new JComboBox();
        timSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerBox, cons);
        compPanel.add(timSerBox);


        //Displays window
        stockView.pack();
        stockView.setVisible(true);
        stockView.setLocationRelativeTo(null);
	} //PLACEHOLDER UNTIL I KNOW HOW THE VIEW WILL RUN. I JUST RUN FROM MAIN NOW. HUMAN BENIS
}
