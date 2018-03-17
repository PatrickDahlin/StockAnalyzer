package stockanalyzer.view;

import stockanalyzer.controller.StockController;
import stockanalyzer.model.APICallParams;
import javax.swing.border.EmptyBorder;
import stockanalyzer.model.StockModel;
import stockanalyzer.model.StockModel.StockEntry;
import stockanalyzer.model.StockModel.TimedValue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.Option;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.OHLCSeries.OHLCSeriesRenderStyle;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class StockView {
	
	private StockController controller;
	private JComboBox<String> datSerBox;
	private JComboBox<String> timSerBox;
	private JComboBox<String> smblBox;
	private JComboBox<String> timIntBox;
	private JComboBox<String> outSizBox;
    private JButton queryButton;
	private JTextArea textField;
	
	private XYChart chart;
	private JPanel chartPanel;

	public StockView(StockController contrlr)
	{
		controller = contrlr;

		BuildWindow();


        ArrayList<String> options = new ArrayList<String>(Arrays.asList("TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", "TIME_SERIES_DAILY_ADJUSTED",
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
        

        GridBagLayout g = new GridBagLayout();
        GridBagConstraints vcons = new GridBagConstraints();
        vcons.fill = GridBagConstraints.BOTH;
        stockView.setLayout(g);
        
        JPanel leftP = new JPanel();
        leftP.setPreferredSize(new Dimension(400,500));
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


        chart = new XYChartBuilder().title("Stock chart").width(600).height(500).xAxisTitle("Time").yAxisTitle("Value").build();

        // Need these to set the types of the series correctly, just empty data until update
        ArrayList<Date> a = new ArrayList<Date>(1); a.add(new Date()); // We need 1 element for chart not to crash :/
        ArrayList<Float> b = new ArrayList<Float>(1); b.add(0f);
        chart.addSeries("Stock value", a, b);
        
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

    public void setModelData(StockModel model) {

    	
    	StringBuilder text = new StringBuilder();
    	
    	ArrayList<StockEntry> v = model.getData();
    	ArrayList<Float> graphData = new ArrayList<Float>();
    	ArrayList<Date> graphData2 = new ArrayList<Date>();
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    	text.append("========== Listing data: ");
    	text.append(datSerBox.getSelectedItem().toString());
    	text.append(" ==========");
    	for(StockEntry entry : v)
    	{
    		for(TimedValue val : entry.values)
    		{
    			// We're checking the first character in the title(the index) against the index in the combobox
    			// API **should** always return same index for same value and our combo has items in the correct order
    			if(Integer.parseInt(val.title.substring(0, 1)) == (datSerBox.getSelectedIndex()+1))
    			{
    				text.append("\n");
    				text.append("Date: ");
    				text.append(entry.time);
    				text.append(": ");
    				text.append(val.value);
    				graphData.add(val.value);
    				
    				try {
    					Date d = df.parse(entry.time);
						graphData2.add(d);
					} catch (ParseException e) {
						try {
							Date d = df2.parse(entry.time);
							graphData2.add(d);
						}catch(ParseException e2)
						{
							e2.printStackTrace();
							System.out.println("Failed to parse date time");
							return;
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
    	
    	chart.removeSeries("Stock value");
    	chart.addSeries("Stock value", graphData2, graphData);
    	
    	// Repaint the chart
    	chartPanel.revalidate();
    	chartPanel.repaint();
    }

    private void defaultOptions() {

        ArrayList<String> options = new ArrayList<String>(Arrays.asList("open", "high", "low", "close", "volume"));
        itemAdder(datSerBox, options);
        options.clear();

        options.addAll(Arrays.asList("MSFT", "TSLA", "GOOGL", "AABA", "ULTA"));
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
                    options.addAll(Arrays.asList("daily open", "daily high", "daily low", "daily close", "daily adjusted close", "daily volume", "daily dividend amount", "daily split coefficient"));
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
                    options.addAll(Arrays.asList("weekly open", "weekly high", "weekly low", "weekly close", "weekly adjusted close", "weekly volume", "weekly dividend"));
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
        queryButton.addActionListener(evt -> createParameters());

    }

    private void createParameters() {

        APICallParams.TimeSeries timeSerie;
        APICallParams.Interval interval;
        APICallParams.OutputSize size;

        switch (timSerBox.getSelectedItem().toString()) {
            case "TIME_SERIES_INTRADAY":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_INTRADAY;
                break;
            case "TIME_SERIES_DAILY":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_DAILY;
                break;
            case "TIME_SERIES_DAILY_ADJUSTED":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_DAILY_ADJUSTED;
                break;
            case "TIME_SERIES_WEEKLY":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_WEEKLY;
                break;
            case "TIME_SERIES_WEEKLY_ADJUSTED":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_WEEKLY_ADJUSTED;
                break;
            case "TIME_SERIES_MONTHLY":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_MONTHLY;
                break;
            case "TIME_SERIES_MONTHLY_ADJUSTED":
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_WEEKLY_ADJUSTED;
                break;
            default:
                timeSerie = APICallParams.TimeSeries.TIME_SERIES_INTRADAY;
                break;
        }

        switch (timIntBox.getSelectedItem().toString()) {
            case "1min":
                interval = APICallParams.Interval.OneMin;
                break;
            case "5min":
                interval = APICallParams.Interval.FiveMin;
                break;
            case "15min":
                interval = APICallParams.Interval.FifteenMin;
                break;
            case "30min":
                interval = APICallParams.Interval.ThirtyMin;
                break;
            case "60min":
                interval = APICallParams.Interval.SixtyMin;
                break;
            default:
                interval = APICallParams.Interval.OneMin;
                break;
        }

        switch (outSizBox.getSelectedItem().toString()){
            case "compact":
                size = APICallParams.OutputSize.COMPACT;
                break;
            case "full":
                size = APICallParams.OutputSize.FULL;
                break;
            default:
                size = null;
                break;
        }

        APICallParams params = new APICallParams(timeSerie, interval, smblBox.getSelectedItem().toString(), APICallParams.DataType.JSON, size, "XVXEHHDH9BOTXCBQ");

        controller.doAPIRequest(params);

    }

    private void disableBox(JComboBox box){

	    //Method to disable box
	    box.removeAllItems();
	    box.addItem("DISABLED");
        box.setEnabled(false);

    }

}
