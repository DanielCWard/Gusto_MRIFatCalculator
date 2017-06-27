/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.io.IOException;
import java.util.ArrayList;
import niftijio.NiftiVolume;

/**
 * Handles the loading and reading of a fat mask file
 * Fat mask files are generated in ITKSnap and saved as nii.gz files.
 * @author Daniel Ward
 */
public class Mask{
    
    /* Class Variables*/
    private final NiftiVolume volume; // nifti volume mask is loaded into
    private final int height; // Height of the volume
    private final int width; // Width of the volume
    private final int depth; // Depth of the volume
    // Value of a pixel if it is present in the mask
    private final int present = 1;
    // Value of a pixel if it is absent in the mask
    private final int absent = 0;
    
    /**
     * Initialises the class by loading the given file.
     * @param filePath : The path to the fatMask nii.gz file
     * @throws IOException : if the file is invalid or not found.
     */
    public Mask(String filePath) throws IOException{
        volume = NiftiVolume.read(filePath); // Load the volume
        height = volume.data.sizeY();
        width = volume.data.sizeX();
        depth = volume.data.sizeZ();
    }
    
    /**
     * Returns the height of the fatmask volume
     * @return height of the fatmask volume
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the width of the fatmask volume
     * @return width of the fatmask volume
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the depth of the fatmask volume
     * @return depth of the fatmask volume
     */
    public int getDepth() {
        return depth;
    }
    
    /**
     * Returns the shape of the fatmask volume as rows, columns, depth
     * @return shape of the fatmask volume
     */
    public int[] getShape() {
        int[] shape = {height, width, depth};
        return shape;
    }
    
    /**
     * Compares two shapes
     * @param shape, int[3] of height, width, depth
     * @return True iff height, width, depth of shape 
     *         are equal to those of this instance
     */
    public boolean checkShape(int[] shape) {
        return shape.length == 3 && 
                shape[0] == height && 
                shape[1] == width && 
                shape[2] == depth;
    }
    
    /**
     * @return a list of the masked coordinates
     */
    public ArrayList<Coordinate> getVoxels(){
        /* Create array list of masked pixels*/
        ArrayList<Coordinate> maskedVoxels = new ArrayList<>();
        int voxelValue;
        /* Iterate over all contents and get all the masked pixels */
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++){
                for (int k = 0; k < depth; k++) {
                    // Get the voxel value: 4th arg 0 seems to work
                    voxelValue = (int) volume.data.get(i, j, k, 0);
                    if (voxelValue != absent){
                        Coordinate voxelLocation = new Coordinate(i, j, k);
                        maskedVoxels.add(voxelLocation);
                    }
                }
            }
        }
        return maskedVoxels;
    }
}
