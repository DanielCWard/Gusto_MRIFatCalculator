package fatfractioncalculator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Daniel Ward
 */
public class FatVolume {
    
    /* Class variables */
    private ArrayList<Integer> pixelValues; // Values of pixels in fat volume
    // Map of minimum values for each imageSlice
    private HashMap<Integer, Integer> minValues;
    // Map of maximum values for each imageSlice
    private HashMap<Integer, Integer> maxValues;
    
    /**
     * Initalise the class by instansiating empty variables
     */
    public FatVolume() {
        // Init class variables
        pixelValues = new ArrayList<>();
        minValues = new HashMap<>();
        maxValues = new HashMap<>();        
    }
    
    /**
     * Adds the pixel to the volume and updates the slice bounds accordingly
     * @param coordinate The location of the pixel
     * @param value The value of the pixel
     */
    public void addPixel(Coordinate coordinate, int value) {
        pixelValues.add(value);
        //Update min and max's
        updateMin(coordinate, value);
        updateMax(coordinate, value);
    }
    
    /**
     * Updates the minimum values for each slice
     * @param coordinate The location of the voxel
     * @param value The value of the voxel
     */
    public void updateMin(Coordinate coordinate, int value) {
        Object currentValue = minValues.get(coordinate.getZ());
        int currentMin;
        // TODO: Update java and use getOrDefault here
        //       Then set default to intMAX so value will be less for sure
        
        if (currentValue == null) {
            currentMin = Integer.MAX_VALUE;
        } else {
            currentMin = (int)currentValue;
        }
        
        //Update value if required
        if (value < currentMin) {
            minValues.put(coordinate.getZ(), value);
        }
    }
    
    /**
     * Updates the maximum values for each slice
     * @param coordinate The location of the voxel
     * @param value The value of the voxel
     */
    public void updateMax(Coordinate coordinate, int value) {
        Object currentValue = maxValues.get(coordinate.getZ());
        int currentMax;
        // TODO: Update java and use getOrDefault here
        //       Then set default to intMin so value will be less for sure
        
        if (currentValue == null) {
            currentMax = Integer.MIN_VALUE;
        } else {
            currentMax = (int)currentValue;
        }
        
        //Update value if required
        if (value > currentMax) {
            maxValues.put(coordinate.getZ(), value);
        }
    }
    
    /**
     * Gets the number of pixels in the volume
     * @return the number of pixels in the volume
     */
    public double getVoxelCount() {
        return pixelValues.size();
    }
    
    /**
     * 
     * @param voxelVolume volume of a single pixel in mm^3
     * @return volume of the voxels in the volume in cm^3
     */
    public double getVolume(double voxelVolume) {
        return getVoxelCount() * voxelVolume * 0.001;
    }
    
    /**
     * 
     * @return the absolute minimum value across all slices
     */
    public int getAbsoluteMin() {
        int absoluteMin = Integer.MAX_VALUE;
        for (int i : minValues.values()) {
            if (i < absoluteMin) {
                absoluteMin = i;
            }
        }
        // Value divided by 10 for some reason from previous version
        return absoluteMin / 10;
    }
    
    /**
     * 
     * @return the absolute maximum value across all slices
     */
    public int getAbsoluteMax() {
        int absoluteMax = Integer.MIN_VALUE;
        for (int i : maxValues.values()) {
            if (i > absoluteMax) {
                absoluteMax = i;
            }
        }
        // Value divided by 10 for some reason from previous version
        return absoluteMax / 10;
    }
    
    /**
     * 
     * @return mean minimum value across MRI slices
     */
    public double getMeanMin() {
        double sum = 0;
        for (int i : minValues.values()) {
            sum += i;
        }
        // Value divided by 10 for some reason from previous version
        return (sum / minValues.size()) / 10;
    }
    
    /**
     * 
     * @return mean maximum value across MRI slices
     */
    public double getMeanMax() {
        double sum = 0;
        for (int i : maxValues.values()) {
            sum += i;
        }
        // Value divided by 10 for some reason from previous version
        return (sum / maxValues.size()) / 10;
    }
    
    /**
     * 
     * @return string representation of volume statistics
     */
    @Override
    public String toString() {
        String string = "Voxel count: " + getVoxelCount() + "\n";
        string += "Absolute minimum: " + getAbsoluteMin() + "\n";
        string += "Mean minimum across slices: " + getMeanMin() + "\n";
        string += "Absolute maximum: " + getAbsoluteMax() + "\n";
        string += "Mean maximum across slices: " + getMeanMax() + "\n";
        
        return string;
    }
}
