/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import ij.plugin.DICOM;
import ij.process.ImageProcessor;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Handles the loading and reading of an MRI file
 * MRI files are a directory of .IMA files
 * @author Daniel Ward
 */
public class Image {
    
    /*Class Variables*/
    // A list of slices for the MRI image
    private ArrayList<ImageProcessor> image;
    private final int height; // image height
    private final int width; // image width
    private final int depth; // image depth
    private HashMap<String, String> header;
    
    public Image(String filePath) throws IOException{
        Path[] sliceFiles = getSlicePaths(filePath);
        DICOM dicomFile = new DICOM();
        
        // Initialise imageList
        image = new ArrayList<>();
        
        // Load each path into the image list
        for (Path p : sliceFiles) {
            dicomFile.open(p.toString()); // open the IMA file
            image.add(dicomFile.getProcessor().duplicate());
        }
        
        // If image is empty: directory at filepath is empty
        if (image.isEmpty()) {
            throw new IOException("Error, empty MRI directory: " + filePath);
        }
        
        populateHeader(sliceFiles[0].toString());
        
        depth = image.size();
        height = image.get(0).getHeight();
        width = image.get(0).getWidth();
    }
    
    /**
     * Get the voxel value at x, y, z coordinates
     * @param x
     * @param y
     * @param z
     * @return
     * @throws IndexOutOfBoundsException 
     */
    public int get(int x, int y, int z) throws IndexOutOfBoundsException{
        if (x < 0 || x > width || y < 0 || y > height || z < 0 || z > depth) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds for image");
        }
        
        return image.get(z).get(x, y);
    }
    
    /**
     * Get the voxel value at the coordinate
     * @param coord
     * @return
     * @throws IndexOutOfBoundsException 
     */
    public int get(Coordinate coord) throws IndexOutOfBoundsException{
        return get(coord.getX(), coord.getY(), coord.getZ());
    }
    
    /**
     * @return a list of header titles
     */
    public ArrayList<String> getHeaderTitles() {
        return new ArrayList<>(header.keySet());
    }
    
    /**
     * The contents of the header at the headerKey
     * @param headerKey
     * @return the value from the header
     * @return null if invalid headerKey
     */
    public String getHeader(String headerKey) {
        return header.get(headerKey);
    }
    
    //TODO
    // Common header things and parse to ints e.g. pixel size and stuff we need to use
    // mask mode
    
    
    /**
     * Reads and populates the header informations
     * @param filePath of an IMA file to read header info
     */
    private void populateHeader(String filePath){
        DICOM dicom = new DICOM();
        String rawHeader = dicom.getInfo(filePath); // extract raw header info
        
        header = new HashMap<>(); // Init header map
        
        for (String line : rawHeader.split("\n")) {
            // Drop the first 11 chars for the number things
            line = line.substring(11);
            // Split line into <key>: <value> and add to header hashMap
            String[] kv = line.split(": ");
            if (kv[0].equals("---")) {
                continue; // Skipp useless header contents
            }
            
            header.put(kv[0], kv[1]);
        }
    }
    
    /**
     * Returns a list of paths which are the paths to the files within the
     * given folder. Paths are sorted lexographically.
     * @param filePath directory to get files' paths
     * @return paths to each of the files
     */
    private Path[] getSlicePaths(String filePath){
        // Get a list of files in the given folder
        File file = new File(filePath);
        String[] containedFiles = file.list(
                new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        }
        );
        
        // Add the parent directory to each file name to form a path
        Path[] slicePaths = new Path[containedFiles.length];
        int count = 0;
        for (String f: containedFiles) {
            Path path = Paths.get(filePath, f);
            slicePaths[count++] = path;
        }
        
        Arrays.sort(slicePaths);
        return slicePaths;
    }
}
