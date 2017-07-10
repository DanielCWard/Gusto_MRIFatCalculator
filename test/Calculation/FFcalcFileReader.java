package Calculation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariane
 */
public class FFcalcFileReader {

    public FFcalcFileReader() {
        
    }
    
    public HashMap<String, RowAsDict> readInputFile(String inputFilePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inputFilePath));
            try {                
                // This is the first line so we want to use this as the header
                // list for the dictionary we will create.
                String line = br.readLine();
                // Since it is a csv file we split the line on commas.
                String[] header = line.split(",");
                
                // Keep track of the current line as a list of values in the row
                String[] row;
                
                /**
                 * Keep track of the file as a hash map with the PSCID as the 
                 * key (in position row[0] of the file) and the rest of the
                 * values as the value.
                 */
                HashMap<String, RowAsDict> fileAsDict = new HashMap<>();
                
                // Read the next line as we don't want to add the header as a 
                // row entry
                line = br.readLine();

                while (line != null) {
                    /**
                     * We want to split the line on a comma as this is the 
                     * format for both the output file and input test file.
                     * 
                     * We use the first line to make a header and the rest of the
                     * file is based on this header.
                     */
                     row = line.split(",");
                    /**
                     * For each entry in the row we want to add it to a row
                     * dictionary which will eventually be added to a dictionary
                     * for the file.
                     */
                    RowAsDict rowAsDict = new RowAsDict();
                     for (int i = 0; i < row.length; i ++ ) {
                         rowAsDict.addEntryToDict(header[i].trim(), row[i].trim());
                     }
                    fileAsDict.put(row[0], rowAsDict);
                    
                    line = br.readLine();
                }
                return fileAsDict;
                
            } catch (IOException ex) {
                Logger.getLogger(FFcalcFileReader.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(FFcalcFileReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FFcalcFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FFcalcFileReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}
