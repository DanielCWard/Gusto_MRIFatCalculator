package fatfractioncalculator;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Daniel Ward
 */
public class CsvWriter {
    /* Class variables*/
    private final FileWriter fileWriter; // The file to write to
    private final Path filePath;
    private final Mutex mutex; // Mutex so that writing can only happen at the one time
    private final String delimiter = ", ";
    
    /**
     * Initalises a CSV Writer. Creates and opens the file.
     * @param filePath path to save the file at
     * @throws IOException filePath does not exist
     */
    public CsvWriter(Path filePath) throws IOException {
        this.filePath = filePath;        
        //Open the file
        fileWriter = new FileWriter(this.filePath.toString());
        
        mutex = new Mutex();
    }
    
    /**
     * Initalises a CSV Writer. Creates and opens the file.
     * @param filePath path to save the file at
     * @param firstRow The first / title row of the file
     * @param <T> any type with toString() method
     * @throws IOException filePath does not exist
     */
    public <T> CsvWriter(Path filePath, ArrayList<T> firstRow) 
            throws IOException {
        this.filePath = filePath;        
        //Open the file
        fileWriter = new FileWriter(this.filePath.toString());
        
        mutex = new Mutex();
        
        writeRow(firstRow);
    }
    
    /**
     * Initalises a CSV Writer. Creates and opens the file.
     * @param filePath path to save the file at
     * @throws IOException filePath does not exist
     */
    public CsvWriter(String filePath) throws IOException {
        this.filePath = Paths.get(filePath);
        //Open the file
        fileWriter = new FileWriter(this.filePath.toString());
        
        mutex = new Mutex();
    }
    
    /**
     * Initalises a CSV Writer. Creates and opens the file.
     * @param filePath path to save the file at
     * @param firstRow The first / title row of the file
     * @param <T> any type with toString() method
     * @throws IOException filePath does not exist
     */
    public <T> CsvWriter(String filePath, ArrayList<T> firstRow) 
            throws IOException {
        this.filePath = Paths.get(filePath);        
        //Open the file
        fileWriter = new FileWriter(this.filePath.toString());
        
        mutex = new Mutex();
        
        writeRow(firstRow);
    }
    
    /**
     * Writes a row to the file. Each item's toString representation
     * will be written to the file and separated by a delimiter.
     * @param <T>
     * @param row 
     */
    public final <T> void writeRow(ArrayList<T> row) {
        while (true) {
            try {
                mutex.acquire();
                break;
            } catch (InterruptedException ex) {
            }
        }
        
        try {
            for (T rowItem : row) {
                fileWriter.append(rowItem.toString());
                fileWriter.append(delimiter);
            }
            fileWriter.append("\n");
        } catch (IOException ex) {
            System.err.println("cannot write to csv");
        } finally {
            mutex.release();
        }
    }
    
    /**
     * Closes the csvWriter
     */
    public void close() {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.err.println("cannot close csv");
        }
    }
}