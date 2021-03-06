package stockanalyzer.view;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;
import stockanalyzer.controller.StockController;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockEntry;
import stockanalyzer.model.StockModel.TimedValue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class StockView {
	
	private StockController controller;
	private JComboBox<String> datSerBox;
	private JComboBox<String> timSerBox;
	private JComboBox<String> smblBox;
	private JComboBox<String> smbl2Box;
	private JComboBox<String> timIntBox;
	private JComboBox<String> outSizBox;
	private JTextField startDateField;
	private JTextField endDateField;
    private JButton queryButton;
	private JTextArea textField;
	private JTextField apiKeyField;
	private JTextField pearsonField;
	private JButton pearsonButton;

	private String[] timeSeries;
	private String[] symbols;
	private String[] intervals;
	private String[] outputs;

	private XYChart chart;
	private JPanel chartPanel;

	public StockView(StockController contrlr) {
		controller = contrlr;

		BuildWindow();

		componentListeners();
	}

	private void BuildWindow() {
	    //Creates frame
        JFrame stockView = new JFrame("StockView");
        stockView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stockView.setResizable(false); // removes ability to resize the window

        GridBagLayout g = new GridBagLayout();
        GridBagConstraints vcons = new GridBagConstraints();
        vcons.fill = GridBagConstraints.BOTH;
        stockView.setLayout(g);
        
        JPanel leftP = new JPanel();
        leftP.setPreferredSize(new Dimension(600,800));
        stockView.add(leftP);
        vcons.gridx = 0;
        vcons.gridy = 0;
        vcons.weightx = 1.0f;
        vcons.weighty = 1.0f;
        g.setConstraints(leftP, vcons);
        
        JPanel rightP = new JPanel();
        stockView.add(rightP);
        vcons.weightx = 1.0f;
        vcons.gridx = 1;
        g.setConstraints(rightP, vcons);
        
        //Sets layout for JFrame
        leftP.setLayout(new GridLayout(2,1));

        //Created and adds JPanel to frame
        JPanel compPanel = new JPanel();
        leftP.add(compPanel);
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
        JLabel apiLabel = new JLabel("API Key:");
        stockLayout.setConstraints(apiLabel, cons);
        compPanel.add(apiLabel);

        cons.gridx = 1;
        cons.gridy = 0;
        cons.ipadx = 300;
        apiKeyField = new JTextField();
        stockLayout.setConstraints(apiKeyField, cons);
        compPanel.add(apiKeyField);
        cons.ipadx = 30;

        //2st Row
        cons.gridx = 0;
        cons.gridy = 1;
        cons.ipadx = 10;
        JLabel datSerLbl = new JLabel("Data Series:");
        datSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerLbl, cons);
        compPanel.add(datSerLbl);

        cons.gridx = 1;
        cons.gridy = 1;
        cons.ipadx = 300;
        datSerBox = new JComboBox<String>();
        datSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(datSerBox, cons);
        compPanel.add(datSerBox);
        cons.ipadx = 30;

        //3nd Row
        cons.gridx = 0;
        cons.gridy = 2;
        JLabel timSerLbl = new JLabel("Time Series:");
        timSerLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerLbl, cons);
        compPanel.add(timSerLbl);

        cons.gridx = 1;
        cons.gridy = 2;
        timSerBox = new JComboBox<String>();
        timSerBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timSerBox, cons);
        compPanel.add(timSerBox);

        //4rd Row
        cons.gridx = 0;
        cons.gridy = 3;
        JLabel smblLbl = new JLabel("Symbol 1:");
        smblLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(smblLbl, cons);
        compPanel.add(smblLbl);

        cons.gridx = 1;
        cons.gridy = 3;
        smblBox = new JComboBox<String>();
        smblBox.setBorder(spaceBorder);
        stockLayout.setConstraints(smblBox, cons);
        compPanel.add(smblBox);

        //5th Row
        cons.gridx = 0;
        cons.gridy = 4;
        JLabel smblLbl2 = new JLabel("Symbol 2:");
        smblLbl2.setBorder(spaceBorder);
        stockLayout.setConstraints(smblLbl2, cons);
        compPanel.add(smblLbl2);

        cons.gridx = 1;
        cons.gridy = 4;
        smbl2Box = new JComboBox<String>();
        smbl2Box.setBorder(spaceBorder);
        stockLayout.setConstraints(smbl2Box, cons);
        compPanel.add(smbl2Box);

        //6th Row
        cons.gridx = 0;
        cons.gridy = 5;
        JLabel timIntLbl = new JLabel("Time Interval:");
        timIntLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntLbl, cons);
        compPanel.add(timIntLbl);

        cons.gridx = 1;
        cons.gridy = 5;
        timIntBox = new JComboBox<String>();
        timIntBox.setBorder(spaceBorder);
        stockLayout.setConstraints(timIntBox, cons);
        compPanel.add(timIntBox);

        //7th Row
        cons.gridx = 0;
        cons.gridy = 6;
        JLabel outSizLbl = new JLabel("Output Size:");
        outSizLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizLbl, cons);
        compPanel.add(outSizLbl);

        cons.gridx = 1;
        cons.gridy = 6;
        outSizBox = new JComboBox<String>();
        outSizBox.setBorder(spaceBorder);
        stockLayout.setConstraints(outSizBox, cons);
        compPanel.add(outSizBox);

        //8th Row
        cons.gridx = 0;
        cons.gridy = 7;
        JLabel startDateLbl = new JLabel("Start Date:");
        startDateLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(startDateLbl, cons);
        compPanel.add(startDateLbl);

        cons.gridx = 1;
        cons.gridy = 7;
        cons.insets = new Insets(14,0,0,0);
        startDateField = new JTextField();
        stockLayout.setConstraints(startDateField, cons);
        compPanel.add(startDateField);
        cons.insets = new Insets(0,0,0,0);

        //9th Row
        cons.gridx = 0;
        cons.gridy = 8;
        JLabel endDateLbl = new JLabel("End Date:");
        endDateLbl.setBorder(spaceBorder);
        stockLayout.setConstraints(endDateLbl, cons);
        compPanel.add(endDateLbl);

        cons.gridx = 1;
        cons.gridy = 8;
        cons.insets = new Insets(14,0,0,0);
        endDateField = new JTextField();
        stockLayout.setConstraints(endDateField, cons);
        compPanel.add(endDateField);
        cons.insets = new Insets(0,0,0,0);

        //Adds query button
        cons.gridx = 1;
        cons.gridy = 9;
        cons.weighty = 0.5;
        cons.weightx = 0.5;
        cons.fill = GridBagConstraints.CENTER;
        cons.insets = new Insets(14,0,0,0);
        queryButton = new JButton("--- Do Query ---");
        stockLayout.setConstraints(queryButton, cons);
        compPanel.add(queryButton);

        //Adds Pearson Button & Label
        cons.gridx = 0;
        cons.gridy = 10;
        pearsonButton = new JButton("Pearson Correlation");
        cons.weightx = 0;
        cons.insets = new Insets(14,0,0,10);
        stockLayout.setConstraints(pearsonButton, cons);
        compPanel.add(pearsonButton);

        cons.gridx = 1;
        cons.gridy = 10;
        cons.insets = new Insets(14,0,0,0);
        cons.fill = GridBagConstraints.BOTH;
        pearsonField = new JTextField();
        stockLayout.setConstraints(pearsonField, cons);
        compPanel.add(pearsonField);
        cons.insets = new Insets(0,0,0,0);

        chart = new XYChartBuilder().title("Stock chart").width(600).height(500).xAxisTitle("Time").yAxisTitle("Value").build();

        // Need these to set the types of the series correctly, just empty data until update
        ArrayList<Date> a = new ArrayList<Date>(1); a.add(new Date()); // We need 1 element for chart not to crash :/
        ArrayList<Float> b = new ArrayList<Float>(1); b.add(0f);
        
        chart.getStyler().setLegendPosition(LegendPosition.InsideNE); // Puts legend inside chart for smaller padding
        chart.getStyler().setChartBackgroundColor(new Color(0,0,0,0));
        chart.getStyler().setLegendLayout(LegendLayout.Horizontal);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setDecimalPattern("##########.##");
        chart.getStyler().setDatePattern("yyyy-MM-dd HH:mm:ss");
        chart.getStyler().setXAxisLabelRotation(75);
        
        chartPanel = new XChartPanel<XYChart>(chart);
        rightP.add(chartPanel);
         
        //Adds JPanel & components to 3rd grid
        JPanel textPanel = new JPanel();
        textPanel.setBorder(new EmptyBorder(0,15,15,15));
        textPanel.setLayout(new GridLayout(1,1));
        leftP.add(textPanel);
        textField = new JTextArea();
        textField.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textField);
        textPanel.add(scrollPane);

        //Displays window
        stockView.pack();
        stockView.setVisible(true);
        stockView.setLocationRelativeTo(null);
	}
	

	public void setModelData(StockModel model, StockModel model2) {

    	/*
    	 * Formatting input data to output to UI
    	 */
    	
    	StringBuilder text = new StringBuilder();
    	ArrayList<StockEntry> v = model.getData();
    	ArrayList<Float> graphData = new ArrayList<Float>();
    	ArrayList<Date> graphData2 = new ArrayList<Date>();
        ArrayList<Float> graphData3 = null;
        ArrayList<Date> graphData4 = null;
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    	text.append("========== Listing data: ");
    	text.append(datSerBox.getSelectedItem().toString());
    	text.append(" ==========");
    	
    	if(v.size() == 0)
    	{
    		text.append("\nNo data avaliable");
    	}

    	if(model2 == null){

            for(StockEntry entry : v) {
                for (TimedValue val : entry.values) {
                    // We're checking the first character in the title(the index) against the index in the combobox
                    // API **should** always return same index for same value and our combo has items in the correct order
                    if (Integer.parseInt(val.title.substring(0, 1)) == (datSerBox.getSelectedIndex() + 1)) {
                        text.append("\n");
                        text.append("Date: ");
                        text.append(entry.time);
                        text.append(":\t"+model.getSymbol()+": ");
                        text.append(val.value);
                        graphData.add(val.value);

                        try {
                            Date d = df.parse(entry.time);
                            graphData2.add(d);
                        } catch (ParseException e) {
                            try {
                                Date d = df2.parse(entry.time);
                                graphData2.add(d);
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                                System.out.println("Failed to parse date time");
                                return;
                            }
                        }
                    }
                }
            }

    	} else {
    	    ArrayList<StockEntry> v2 = model2.getData();
            graphData3 = new ArrayList<>();
            graphData4 = new ArrayList<>();

            ArrayList<StockEntry> smallestList = v.size()<v2.size()? v:v2;
            ArrayList<StockEntry> largestList = v.size()>v2.size()? v:v2;

            for(int i = 0; i != largestList.size(); i++) {
                ArrayList<StockEntry> largestValuesSize;

                try{
                    largestValuesSize = v.get(i).values.size()>v2.get(i).values.size()? v:v2;
                } catch (IndexOutOfBoundsException e){
                    largestValuesSize = largestList;
                }

                for (int k = 0; k != largestValuesSize.get(i).values.size(); k++) {
                    // We're checking the first character in the title(the index) against the index in the combobox
                    // API **should** always return same index for same value and our combo has items in the correct order
                    if (Integer.parseInt(largestValuesSize.get(i).values.get(k).title.substring(0, 1)) == (datSerBox.getSelectedIndex() + 1)) {

                        String value1;
                        String value2;

                        try{
                            value1 = Float.toString(v.get(i).values.get(k).value);
                            graphData.add(Float.parseFloat(value1));
                        }catch (IndexOutOfBoundsException e){
                            value1 = "N/A";
                        }

                        try{
                            value2 = Float.toString(v2.get(i).values.get(k).value);
                            graphData3.add(Float.parseFloat(value2));
                        }catch (IndexOutOfBoundsException e){
                            value2 = "N/A";
                        }

                        text.append("\n");
                        text.append("Date: ");
                        try{
                            text.append(v2.get(i).time);
                        } catch(IndexOutOfBoundsException e){
                            text.append(v.get(i).time);
                        }
                        text.append(":\t"+model.getSymbol()+": ");
                        text.append(value1 + "\t"+model2.getSymbol()+": ");
                        text.append(value2+"");

                        try {

                            try {
                                Date d = df.parse(v.get(i).time);
                                graphData2.add(d);
                            } catch(IndexOutOfBoundsException e){

                            }

                            try {
                                Date d2 = df.parse(v2.get(i).time);
                                graphData4.add(d2);
                            } catch(IndexOutOfBoundsException e){

                            }

                        } catch (ParseException e) {
                            try {

                                try {
                                    Date d = df2.parse(v.get(i).time);
                                    graphData2.add(d);
                                } catch(IndexOutOfBoundsException e2){

                                }

                                try {
                                    Date d2 = df2.parse(v2.get(i).time);
                                    graphData4.add(d2);
                                } catch(IndexOutOfBoundsException e2){

                                }

                            } catch (ParseException e2) {
                                e2.printStackTrace();
                                System.out.println("Failed to parse date time");
                                return;
                            }
                        }
                    }
                }
            }
        }

    	textField.setText(text.toString());
    	textField.setCaretPosition(0);
    	
    	// Chart needs to have data to properly render, otherwise it throws an error
    	if(graphData.size() == 0)
    		graphData.add(0f);
    	
    	if(graphData2.size() == 0)
    		graphData2.add(new Date());

    	chart.removeSeries("Symbol 1");
    	chart.removeSeries("Symbol 2");

    	chart.addSeries("Symbol 1", graphData2, graphData);

        if(graphData3 != null && graphData4 != null){
            if(graphData3.size() == 0)
                graphData3.add(0f);

            if(graphData4.size() == 0)
                graphData4.add(new Date());

            chart.addSeries("Symbol 2", graphData4, graphData3);

        }

    	// Repaint the chart
    	chartPanel.revalidate();
    	chartPanel.repaint();
    }

    private void defaultOptions() {

        ArrayList<String> options = new ArrayList<String>(Arrays.asList("open", "high", "low", "close", "volume"));
        setComboBoxOptions(datSerBox, options);
        datSerBox.setEnabled(true);
        options.clear();

        options.addAll(Arrays.asList(symbols));
        options.add(0,""); //Adds empty symbol element
        setComboBoxOptions(smblBox, options);
        smblBox.setEnabled(true);

        setComboBoxOptions(smbl2Box, options);
        smbl2Box.setEnabled(true);
        options.clear();

        options.addAll(Arrays.asList(intervals));
        setComboBoxOptions(timIntBox, options);
        timIntBox.setEnabled(true);
        options.clear();

        options.addAll(Arrays.asList(outputs));
        setComboBoxOptions(outSizBox, options);
        outSizBox.setEnabled(true);
        options.clear();

    }

    private void setComboBoxOptions(JComboBox<String> box, ArrayList<String> options){

        //Makes sure ArrayList isn't empty
        if (options.size() == 0){
            return;
        }

        //Clears box
        box.removeAllItems();

        //Method for adding items to JComboBox
        for(int i = 0; i != options.size(); i++){
            box.addItem(options.get(i));
        }
    }

    private void componentListeners() {

    	// We only set up the dependencies on each inputfield here
    	
    	// The manager of this class handles what is done when a query is made
    	
        //ActionListener for time series
        timSerBox.addActionListener(evt -> {

            ArrayList<String> options = new ArrayList<String>();

            //Depending on choice
            switch (timSerBox.getSelectedItem().toString()) {
                case "TIME_SERIES_INTRADAY":
                    defaultOptions();
                    break;
                case "TIME_SERIES_DAILY":
                    options.addAll(Arrays.asList("daily open", "daily high", "daily low", "daily close", "daily volume"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    options.addAll(Arrays.asList(outputs));
                    setComboBoxOptions(outSizBox, options);
                    outSizBox.setEnabled(true);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    break;
                case "TIME_SERIES_DAILY_ADJUSTED":
                    options.addAll(Arrays.asList("daily open", "daily high", "daily low", "daily close", "daily adjusted close", "daily volume", "daily dividend amount", "daily split coefficient"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    options.addAll(Arrays.asList(outputs));
                    setComboBoxOptions(outSizBox, options);
                    outSizBox.setEnabled(true);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    break;
                case "TIME_SERIES_WEEKLY":
                    options.addAll(Arrays.asList("weekly open", "weekly high", "weekly low", "weekly close", "weekly volume"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    outSizBox.setEnabled(false);
                    outSizBox.removeAllItems();
                    break;
                case "TIME_SERIES_WEEKLY_ADJUSTED":
                    options.addAll(Arrays.asList("weekly open", "weekly high", "weekly low", "weekly close", "weekly adjusted close", "weekly volume", "weekly dividend"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    outSizBox.setEnabled(false);
                    outSizBox.removeAllItems();
                    break;
                case "TIME_SERIES_MONTHLY":
                    options.addAll(Arrays.asList("monthly open", "monthly high", "monthly low", "monthly close", "monthly volume"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    outSizBox.setEnabled(false);
                    outSizBox.removeAllItems();
                    break;
                case "TIME_SERIES_MONTHLY_ADJUSTED":
                    options.addAll(Arrays.asList("monthly open", "monthly high", "monthly low", "monthly close", "monthly adjusted close", "monthly volume", "monthly dividend"));
                    setComboBoxOptions(datSerBox, options);
                    options.clear();

                    //Disables box
                    timIntBox.setEnabled(false);
                    timIntBox.removeAllItems();
                    outSizBox.setEnabled(false);
                    outSizBox.removeAllItems();
                    break;
                default:
                    System.out.println("Unable to match time serie");
                    break;
            }

        });

    }

    //Adds a listener for when JTextField loses focus
    public void startDateFocusLoss(FocusListener listener) {
        startDateField.addFocusListener(listener);
    }

    public void endDateFocusLoss(FocusListener listener) {
        endDateField.addFocusListener(listener);
    }

    /**
     * Add a listener for when a query is made
     */
    public void addQueryListener(ActionListener actionListener) {
    	queryButton.addActionListener(actionListener);
    }
    
    /**
     * Add a listener for Pearsons Correlation calculation button
     */
    public void addPearsonCalcListener(ActionListener actionListener) {
    	pearsonButton.addActionListener(actionListener);
    }

    /**
     * Gets the currently selected TimeSeries
     */
    public String getTimeSeries() {
    	return timSerBox.getSelectedItem().toString().trim();
    }
    
    /**
     * Gets the currently selected Interval
     */
    public String getInterval() {
    	if(timIntBox.getItemCount() == 0) return "1min";
    	
    	return timIntBox.getSelectedItem().toString().trim();
    }
    
    public String getDataSeries() {
    	return String.valueOf(datSerBox.getSelectedIndex()+1);
    }
    
    /**
     * Gets the currently selected Output Size, either FULL or COMPACT
     */
    public String getOutputSize() {
    	if(outSizBox.getItemCount() == 0) return "FULL";
    	
    	return outSizBox.getSelectedItem().toString().trim();
    }
   
    /**
     * Get the selected Symbol
     */
	public String[] getSymbol() {
	    String[] symbols = new String[2];
	    symbols[0] = smblBox.getSelectedItem().toString();
	    symbols[1] = smbl2Box.getSelectedItem().toString();
		return symbols;
	}

    public String getStartDateField() {
        return startDateField.getText().trim();
    }

    public String getEndDateField() {
        return endDateField.getText().trim();
    }

    public void setSymbols(String[] smbl){

	    symbols = smbl;

    }

    public void setInterval(String[] interval){

        intervals = interval;

    }

    public void setOutput(String[] output){

        outputs = output;

    }
    
    public void setPearsonCorrelation(double value) {
    	pearsonField.setText(String.valueOf(value));
    }

    public void setSeries(String[] series){

        timeSeries = series;

        ArrayList<String> options = new ArrayList<String>(Arrays.asList(timeSeries));
        setComboBoxOptions(timSerBox, options);

        defaultOptions();

    }

    public void setApiKeyField(String key) {

	    apiKeyField.setText(key);

    }

    public void setStartDate(String date){
	    startDateField.setText(date);
    }

    public void setEndDate(String date){
	    endDateField.setText(date);
    }

    public void startDateCorr(Boolean pizza) {
	    if(pizza) {
            startDateField.setBorder(BorderFactory.createLineBorder(Color.gray));
        } else {
            startDateField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    public void endDateCorr(Boolean pizza) {
	    if(pizza) {
            endDateField.setBorder(BorderFactory.createLineBorder(Color.gray));
        } else {
            endDateField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    public String getKey(){

	    return apiKeyField.getText().trim();

    }

}
