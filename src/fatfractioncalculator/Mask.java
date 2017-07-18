package fatfractioncalculator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import niftijio.NiftiHeader;
import niftijio.NiftiVolume;

/**
 * Handles the loading and reading of a fat mask file
 * Fat mask files are generated in ITKSnap and saved as nii.gz files.
 * @author Daniel Ward
 */
public class Mask{
    
    /* Class Variables*/
    private final NiftiVolume volume; // nifti volume mask is loaded into
    private final NiftiHeader header; // the header of the niftiVolue
    private final int height; // Height of the volume
    private final int width; // Width of the volume
    private final int depth; // Depth of the volume
    private final int present; // value of a mask voxel indicating presence
    private final int absent; // value of a mask voxel indicating absence
    private final String path; // Path to where this mask was loaded from
    
    /**
     * Initialises the class by loading the given file.
     * @param filePath : The path to the fatMask nii.gz file
     * @throws IOException : if the file is invalid or not found.
     */
    public Mask(String filePath) throws IOException{
        this.path = filePath;
        this.absent = 0;
        this.present = 1;
        volume = NiftiVolume.read(filePath); // Load the volume
        header = NiftiHeader.read(filePath);
        height = volume.data.sizeY();
        width = volume.data.sizeX();
        depth = volume.data.sizeZ();
    }
    
    public Map<String, String> getHeader() {
        return header.info();
    }
    
    /**
     * 
     * @return String of the Path to this Mask File
     */
    public String getPath() {
        return path;
    }
    
    /**
     * 
     * @return Filename of the mask
     */
    public String getFilename() {
        return Paths.get(path).getFileName().toString();
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
     * @return a list of the masked coordinates masked by value 1
     */
    public ArrayList<Coordinate> getVoxels(){
        return getVoxels(1);
    }
    
    /**
     * @param maskValue The value a voxel must be to be masked
     * @return a list of the masked coordinates having mask value voxelValue
     */
    public ArrayList<Coordinate> getVoxels(int maskValue){
        /* Create array list of masked pixels*/
        ArrayList<Coordinate> maskedVoxels = new ArrayList<>();
        int voxelValue;
        
        /* Iterate over all contents and get all the masked pixels */
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++){
                for (int k = 0; k < depth; k++) {
                    // Get the voxel value: 4th arg 0 seems to work
                    voxelValue = (int) volume.data.get(i, j, k, 0);
                    if (voxelValue == maskValue){
                        Coordinate voxelLocation = new Coordinate(i, j, k);
                        maskedVoxels.add(voxelLocation);
                    }
                }
            }
        }
        return maskedVoxels;
    }
}
