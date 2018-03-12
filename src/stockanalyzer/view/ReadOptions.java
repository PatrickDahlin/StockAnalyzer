package stockanalyzer.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadOptions {

    public ArrayList<String> getOptions(String file) {

        ArrayList<String> options = new ArrayList<String>();

        String csvFile = "res/"+file+".csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                // use comma as separator
                options.add(line.split(csvSplitBy)[0]+", "+line.split(csvSplitBy)[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return options;

    }
}
