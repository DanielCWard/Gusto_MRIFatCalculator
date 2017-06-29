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
    public Shape getShape() {
        Shape shape = new Shape(width, height, depth);
        return shape;
    }
    
    /**
     * Compares two shapes
     * @param shape shape to compare
     * @return True iff height, width, depth of shape 
     *         are equal to those of this instance
     */
    public boolean checkShape(Shape shape) {
        return shape == getShape();
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
        return (int) volume.data.get(x, y, z, 0);
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
     * @return a list of the masked coordinates
     */
    public ArrayList<Coordinate> getVoxels(){
        /* Create array list of masked pixels*/
        ArrayList<Coordinate> maskedVoxels = new ArrayList<>();
        int voxelValue;
        /* Iterate over all contents and get all the masked pixels */
        int c = 0;
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i < width; i++){
//                for (int k = 0; k < depth; k++) {
//                    // Get the voxel value: 4th arg 0 seems to work
//                    voxelValue = (int) volume.data.get(i, j, k, 0);
//                    if (voxelValue != absent){
//                        Coordinate voxelLocation = new Coordinate(i, j, k);
////                        c++;
////                        if (c < 10) {
////                            System.err.println("masked Coord: " + voxelLocation);
////                            System.err.flush();
////                        }
//                        maskedVoxels.add(voxelLocation);
//                    }
//                }
//            }
//        }

        for (int k = 0; k < depth; k++) {
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++){
                    // Get the voxel value: 4th arg 0 seems to work
                    voxelValue = (int) volume.data.get(i, j, k, 0);
                    if (voxelValue != absent){
                        Coordinate voxelLocation = new Coordinate(i, j, k);
//                        c++;
//                        if (c < 10) {
//                            System.err.println("masked Coord: " + voxelLocation);
//                            System.err.flush();
//                        }
                        maskedVoxels.add(voxelLocation);
                    }
                }
            }
        }
        return maskedVoxels;
    }
}
