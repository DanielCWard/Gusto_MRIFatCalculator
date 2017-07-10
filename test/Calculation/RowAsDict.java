/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Calculation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ariane
 */
public class RowAsDict {
    

    /**
     * Keep the empty dictionary as a hash map.
     * Each row in an input file will be represented as a dictionary.
     */
    HashMap<String, String> rowAsDict;
    
    /**
     * Keep track of the keys stored in the row.
     */
    
    ArrayList<String> keys;
    
    
    public RowAsDict() {
        rowAsDict = new HashMap<>();
        keys = new ArrayList<>();
                
    }
    
    /**
     * For each entry in the input file we want to use the column header as the
     * key and the column, row entry as the value.
     * 
     * @param key
     * @param value 
     */
    public void addEntryToDict(String key, String value) {
        rowAsDict.put(key, value);
        keys.add(key);
    }
    
    
    /**
     * Gets the value for a particular key in the dictionary.
     * 
     * Assumes the key exists in the dictionary. To use when itterating through
     * the key set of the dictionary not for testing.
     * 
     * @param key
     * @return value 
     */
    public String getValueForKey(String key) {
        return rowAsDict.get(key);
    }
   
    /**
     * Returns the created row.
     * 
     * @return row parsed to a dictionary.
     */
    public HashMap<String, String> getRowAsDict() {
        return rowAsDict;
    }
    
    
    /**
     * Tests whether the value for a particular key is correct of not.
     * @param key
     * @param value
     * @return either an error or success message
     */
    public String isEntryCorrect(String key, String value) {
        String savedValue = savedValue = rowAsDict.get(key);
        if (savedValue == null) {
            return "Key Error";
        }
        if (savedValue.equals(value)) {
            return "Value correct";
        } 
        return "Value incorrect";
    }
    
    /**
     * Returns the keys for this dictionary.
     * In the case of testing, this is the header of the file.
     * 
     * @return keys 
     */
    public ArrayList<String> getKeys() {
        return keys;
    }
    
    
}
