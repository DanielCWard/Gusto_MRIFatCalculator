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
    // The contents of the header file
    private HashMap<String, String> header;
    private final String path; //Path the MRI was loaded from
    
    
    public Image(String filePath) throws IOException{
        this.path = filePath;
        Path[] sliceFiles = getSlicePaths(filePath);
        DICOM dicomFile;
        
        // Initialise imageList
        image = new ArrayList<>();
        // Load each path into the image list
        for (Path p : sliceFiles) {
            dicomFile = new DICOM();
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
    public int get(int x, int y, int z) throws IndexOutOfBoundsException {
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
     *         null if invalid headerKey
     */
    public String getHeader(String headerKey) {
        return header.get(headerKey);
    }
    
    /**
     * Returns the dimensions for each MRI voxel
     * @return {x, y, z} dimensions
     *          null if inconsistent header contents
     */
    public Shape getVoxelDimensions() {
        // Get voxel depth
        String result = getHeader("Slice Thickness");
        
        // If non exisitent return null
        if (result == null) {
            return null;
        }
        
        double depthDimension = Double.parseDouble(result);
        
        // get voxel x and y 
        result = getHeader("Pixel Spacing");
        
        // If non exisitent return null
        if (result == null) {
            return null;
        }
        String[] spacing = result.split("\\\\");
        Shape shape = new Shape(Double.parseDouble(spacing[0]), 
            Double.parseDouble(spacing[1]), depthDimension);
        
        return shape;
    }
    
    /**
     * Returns the voxel volume in mm^2 (assuming that is header file units)
     * @return voxel volume
     *         -1 if inconsistent header file contents
     */
    public double getVoxelVolume() {
        // Get the dimensions
        Shape voxelDimens = getVoxelDimensions();
        if (voxelDimens == null){
            return -1;
        }
        
        return voxelDimens.getVolume();
    }
    
    /**
     * 
     * @return string of path to this MRI file
     */
    public String getPath() {
        return path;
    }
    
    /**
     * 
     * @return the patient ID from the header file
     *         null if inconsistent header contents
     */
    public String getPatientID() {
        return getHeader("Patient ID");
    }
    
    /**
     * 
     * @return the patient height from the header file
     *         null if inconsistent header contents
     */
    public String getPatientHeight() {
        return getHeader("Patient's Size");
    }
    
    /**
     * 
     * @return the patient weight from the header file
     *         null if inconsistent header contents
     */
    public String getPatientWeight() {
        return getHeader("Patient's Weight");
    }
    
    /**
     * 
     * @return the patient sex from the header file
     *         null if inconsistent header contents
     */
    public String getPatientSex() {
        return getHeader("Patient's Sex");
    }
    
    /**
     * 
     * @return the patient age from the header file
     *         null if inconsistent header contents
     */
    public String getPatientAge() {
        return getHeader("Patient's Age");
    }
    
    /**
     * 
     * @return the patient birthday from the header file
     *         null if inconsistent header contents
     */
    public String getPatientBirthday() {
        return getHeader("Patient's Birth Date");
    }
    
    /**
     * 
     * @return the study instance UID from the header file
     *         null if inconsistent header contents
     */
    public String getStudyUID() {
        return getHeader("Study Instance UID");
    }
    
    /**
     * 
     * @return the study date from the header file
     *         null if inconsistent header contents
     */
    public String getStudyDate() {
        return getHeader("Study Date");
    }
    
    /**
     * 
     * @return the MRI strength from the header file
     *         null if inconsistent header contents
     */
    public String getMRIStrength() {
        return getHeader("Magnetic Field Strength");
    }
    
    /**
     * Collects and returns the statistics about masked voxels.
     * @require voxelMask.getShape == getShape()
     * @param voxelMask mask for the image
     * @param bounds
     * @return FatVolume statistics
     */
    public FatVolume getMaskedVoxelStatistics(Mask voxelMask, Bounds bounds) {
        // Create FatVolume to populate statistics
        FatVolume statistics = new FatVolume();
        
        // Add each masked pixel to the statistics
        for (Coordinate coordinate : voxelMask.getVoxels()) {
            if (bounds.inBounds(get(coordinate))) {
                statistics.addPixel(coordinate, get(coordinate));      
            }
        }
        return statistics;
    }
    
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
            
            if (kv.length < 2 || kv[0].equals("---")) {
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
    
    /**
     * 
     * @return shape of image
     */
    public Shape getShape() {
        return new Shape(width, height, depth);
    }
}
