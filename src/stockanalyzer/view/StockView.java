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

		BuildWindow();
	}


	private void BuildWindow() {
	    //Creates frame
        JFrame stockView = new JFrame("StockView");
        stockView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stockView.setResizable(false); // removes ability to resize the window

        //Sets layout for JFrame
        stockView.setLayout(new GridLayout(2,2));

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
        JComboBox<String> datSerBox = new JComboBox<String>();
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

        //adds temp label to 2nd grid where graph is supposed to be
        stockView.add(new JLabel("This is where the graph is supposed to be")); //Patrick, få int herpes, fillern e bara temporär tills vi lagar grafen :DDDD
        //@TODO Add the actual graph and remove temp FILLER

        //Adds JPanel & components to 3rd grid
        JPanel textPanel = new JPanel();
        textPanel.setBorder(new EmptyBorder(0,15,15,15));
        textPanel.setLayout(new GridLayout(1,1));
        stockView.add(textPanel);
        JTextArea textField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textField);
        textPanel.add(scrollPane);

        //Displays window
        stockView.pack();
        stockView.setVisible(true);
        stockView.setLocationRelativeTo(null);
	}
}
