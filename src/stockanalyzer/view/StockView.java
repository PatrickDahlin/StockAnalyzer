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

	public void test(){
	    //Creates frame
        JFrame stockView = new JFrame("StockView");
        stockView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Sets

        //Created and adds JPanel to frame
        JPanel compPanel = new JPanel();
        stockView.add(compPanel);
        compPanel.setBorder(new EmptyBorder(15,15,15,15));

        //Layout Settings
        GridBagLayout stockLayout = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        compPanel.setLayout(stockLayout);
        cons.fill = GridBagConstraints.BOTH;

        //Default Constraints
        cons.weighty = 1.0;
        cons.ipadx = 30;

        //Create border to add spacing between components
        EmptyBorder spaceBorder = new EmptyBorder(7,0,7,0);

        //Adds components

        //1st Row
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipadx = 10;
        JLabel datSerLbl = new JLabel("Data Series:");
        datSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerLbl, cons);
        compPanel.add(datSerLbl);

        cons.gridx = 1;
        cons.gridy = 0;
        cons.ipadx = 300;
        JComboBox datSerBox = new JComboBox();
        datSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerBox, cons);
        compPanel.add(datSerBox);
        cons.ipadx = 30;

        //2st Row
        cons.gridx = 0;
        cons.gridy = 1;
        JLabel timSerLbl = new JLabel("Time Series:");
        timSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerLbl, cons);
        compPanel.add(timSerLbl);

        cons.gridx = 1;
        cons.gridy = 1;
        JComboBox timSerBox = new JComboBox();
        timSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerBox, cons);
        compPanel.add(timSerBox);

        //3st Row
        cons.gridx = 0;
        cons.gridy = 2;
        JLabel smblLbl = new JLabel("Symbol:");
        smblLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(smblLbl, cons);
        compPanel.add(smblLbl);

        cons.gridx = 1;
        cons.gridy = 2;
        JComboBox smblBox = new JComboBox();
        smblBox.setBorder(spaceBorder);
        stockLayout.setConstraints(smblBox, cons);
        compPanel.add(smblBox);

        //4st Row
        cons.gridx = 0;
        cons.gridy = 3;
        JLabel timIntLbl = new JLabel("Time Series:");
        timIntLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntLbl, cons);
        compPanel.add(timIntLbl);

        cons.gridx = 1;
        cons.gridy = 3;
        JComboBox timIntBox = new JComboBox();
        timIntBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntBox, cons);
        compPanel.add(timIntBox);

        //5st Row
        cons.gridx = 0;
        cons.gridy = 4;
        JLabel outSizLbl = new JLabel("Time Series:");
        outSizLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizLbl, cons);
        compPanel.add(outSizLbl);

        cons.gridx = 1;
        cons.gridy = 4;
        JComboBox outSizBox = new JComboBox();
        outSizBox.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizBox, cons);
        compPanel.add(outSizBox);

        //Displays window
        stockView.pack();
        stockView.setVisible(true);
        stockView.setLocationRelativeTo(null);
	} //PLACEHOLDER UNTIL I KNOW HOW THE VIEW WILL RUN. I JUST RUN FROM MAIN NOW. HUMAN BENIS
}
