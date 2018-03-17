package stockanalyzer.view;

import stockanalyzer.controller.StockController;
import stockanalyzer.model.APICallParams;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class StockView {
	
	private StockController controller;
	private JComboBox<String> datSerBox;
	private JComboBox<String> timSerBox;
	private JComboBox<String> smblBox;
	private JComboBox<String> timIntBox;
	private JComboBox<String> outSizBox;
    private JButton queryButton;
	private JTextArea textField;

	public StockView(StockController contrlr)
	{
		controller = contrlr;

		BuildWindow();

        ArrayList<String> options = new ArrayList<String>();



        options.addAll(Arrays.asList("TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", "TIME_SERIES_DAILY_ADJUSTED",
                "TIME_SERIES_WEEKLY", "TIME_SERIES_WEEKLY_ADJUSTED", "TIME_SERIES_MONTHLY", "TIME_SERIES_MONTHLY_ADJUSTED"));
        itemAdder(timSerBox, options);
        options.clear();

        defaultOptions();

		componentListeners();
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
        EmptyBorder spaceBorder = new EmptyBorder(14,0,0,0);

        //Adds components

        //1st Row
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipadx = 10;
        JLabel datSerLbl = new JLabel("Data Series:");
        stockLayout.setConstraints(datSerLbl, cons);
        compPanel.add(datSerLbl);

        cons.gridx = 1;
        cons.gridy = 0;
        cons.ipadx = 300;
        datSerBox = new JComboBox<String>();
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
        timSerBox = new JComboBox<String>();
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
        smblBox = new JComboBox<String>();
        smblBox.setBorder(spaceBorder);
        stockLayout.setConstraints(smblBox, cons);
        compPanel.add(smblBox);

        //4st Row
        cons.gridx = 0;
        cons.gridy = 3;
        JLabel timIntLbl = new JLabel("Time Interval:");
        timIntLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntLbl, cons);
        compPanel.add(timIntLbl);

        cons.gridx = 1;
        cons.gridy = 3;
        timIntBox = new JComboBox<String>();
        timIntBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntBox, cons);
        compPanel.add(timIntBox);

        //5st Row
        cons.gridx = 0;
        cons.gridy = 4;
        JLabel outSizLbl = new JLabel("Output Size:");
        outSizLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizLbl, cons);
        compPanel.add(outSizLbl);

        cons.gridx = 1;
        cons.gridy = 4;
        outSizBox = new JComboBox<String>();
        outSizBox.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizBox, cons);
        compPanel.add(outSizBox);

        //Adds query button
        cons.gridx = 1;
        cons.gridy = 5;
        cons.weighty = 0.5;
        cons.weightx = 0.5;
        cons.fill = GridBagConstraints.CENTER;
        cons.insets = new Insets(14,0,0,0);
        queryButton = new JButton("--- Do Query ---");
        stockLayout.setConstraints(queryButton, cons);
        compPanel.add(queryButton);

        //adds temp label to 2nd grid where graph is supposed to be
        stockView.add(new JLabel("This is where the graph is supposed to be")); //Patrick, få int herpes, fillern e bara temporär tills vi lagar grafen :DDDD
        //@TODO Add the actual graph and remove temp FILLER

        //Adds JPanel & components to 3rd grid
        JPanel textPanel = new JPanel();
        textPanel.setBorder(new EmptyBorder(0,15,15,15));
        textPanel.setLayout(new GridLayout(1,1));
        stockView.add(textPanel);
        textField = new JTextArea();
        textField.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textField);
        textPanel.add(scrollPane);

        //Displays window
        stockView.pack();
        stockView.setVisible(true);
        stockView.setLocationRelativeTo(null);
	}

    public void textFieldData() {
        //@TODO Do stuff here that makes TextFieldChange change
    }

    public void graphData() {
	    //@TODO Do stuff here that makes non-existing Graph change
    }

    private void defaultOptions() {

        ArrayList<String> options = new ArrayList<String>();

        options.addAll(Arrays.asList("open", "high", "low", "close", "volume"));
        itemAdder(datSerBox, options);
        options.clear();

        options.add("MSFT");
        itemAdder(smblBox, options);
        options.clear();

        options.addAll(Arrays.asList("1min", "5min", "15min", "30min", "60min"));
        itemAdder(timIntBox, options);
        options.clear();

        options.addAll(Arrays.asList("compact", "full"));
        itemAdder(outSizBox, options);
        options.clear();

    }

    private void itemAdder(JComboBox<String> box, ArrayList<String> options){

        //Makes sure ArrayList isn't empty
        if (options.size() == 0){
            return;
        }

        //Makes sure box isn't disabled
        box.setEnabled(true);

        //Clears box
        box.removeAllItems();

        //Method for adding items to JComboBox
        for(int i = 0; i != options.size(); i++){
            box.addItem(options.get(i));
        }
    }

    private void componentListeners() {

        //ActionListener for time series
        timSerBox.addActionListener(evt -> {

            ArrayList<String> options = new ArrayList<String>();

            //Depending on choice
            switch (timSerBox.getSelectedIndex()) {
                case 0:
                    defaultOptions();
                    break;
                case 1:
                    options.addAll(Arrays.asList("daily open", "daily high", "daily low", "daily close", "daily volume"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    options.addAll(Arrays.asList("compact", "full"));
                    itemAdder(outSizBox, options);
                    options.clear();

                    //Disables box
                    disableBox(timIntBox);
                    break;
                case 2:
                    options.addAll(Arrays.asList("daily open", "daily high", "daily low", "daily close", "daily volume", "daily adjusted", "split/dividend events"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    options.addAll(Arrays.asList("compact", "full"));
                    itemAdder(outSizBox, options);
                    options.clear();

                    //Disables box
                    disableBox(timIntBox);
                    break;
                case 3:
                    options.addAll(Arrays.asList("weekly open", "weekly high", "weekly low", "weekly close", "weekly volume"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    //Disables box
                    disableBox(timIntBox);
                    disableBox(outSizBox);
                    break;
                case 4:
                    options.addAll(Arrays.asList("weekly open", "weekly high", "weekly low", "weekly close", "weekly adjusted close", "weekly volume", "weekly volume", "weekly dividend"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    //Disables box
                    disableBox(outSizBox);
                    disableBox(timIntBox);
                    break;
                case 5:
                    options.addAll(Arrays.asList("monthly open", "monthly high", "monthly low", "monthly close", "monthly volume"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    //Disables box
                    disableBox(outSizBox);
                    disableBox(timIntBox);
                    break;
                case 6:
                    options.addAll(Arrays.asList("monthly open", "monthly high", "monthly low", "monthly close", "monthly adjusted close", "monthly volume", "monthly dividend"));
                    itemAdder(datSerBox, options);
                    options.clear();

                    //Disables box
                    disableBox(outSizBox);
                    disableBox(timIntBox);
                    break;
                default:
                    break;
            }

        });

	    //ActionListener for query Button
        queryButton.addActionListener(evt -> {
            //APICallParams(TimeSeries timeSeries, Interval interval, String symbol, DataType type, OutputSize output, String APIKey
            createParameters();
        });

    }

    private void createParameters() {

	    //"TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", "TIME_SERIES_DAILY_ADJUSTED",
        //"TIME_SERIES_WEEKLY", "TIME_SERIES_WEEKLY_ADJUSTED", "TIME_SERIES_MONTHLY", "TIME_SERIES_MONTHLY_ADJUSTED"

        switch (timSerBox.getSelectedItem().toString()) {
            case "TIME_SERIES_INTRADAY":
                break;
            case "TIME_SERIES_DAILY":
                break;
            case "TIME_SERIES_DAILY_ADJUSTED":
                break;
            case "TIME_SERIES_WEEKLY":
                break;
            case "TIME_SERIES_WEEKLY_ADJUSTED":
                break;
            case "TIME_SERIES_MONTHLY":
                break;
            case "TIME_SERIES_MONTHLY_ADJUSTED":
                break;
            default:
                break;
        }


    }

    private void disableBox(JComboBox box){

	    //Method to disable box
	    box.removeAllItems();
	    box.addItem("DISABLED");
        box.setEnabled(false);

    }

}
