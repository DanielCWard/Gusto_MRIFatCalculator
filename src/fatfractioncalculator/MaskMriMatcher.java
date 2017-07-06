package fatfractioncalculator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Matches an MRI mask file with the MRI based on some input
 * @author Daniel Ward
 */
public class MaskMriMatcher {
    
    /*Class Variables*/
    // list of acceptable subdirectories, in order of priority, highest first
    private final String[] subDirectoryTemplate;
    // list of acceptable study directories, in order of priority, highest first
    private final String[] studyDirectoryTemplate;
    // list of acceptable image filenames, in order of priority, highest first
    private final String[] imageTypeTemplate;
    // list of acceptable segmentation file, in order of priority, highest first
    private final String[] segmentationFileTemplate;
    //Mri parent directory
    private final String mriParentDirectory;
    // StringList to set if unsuccessfull in finding an Image
    private ArrayList<String> errorInformation;
    
    /**
     * Initialise the class with the file templates
     * @require All arguments are not null
     * @param MriParentPath
     * @param subDirectoryTemplate
     * @param studyDirectoryTemplate
     * @param imageTypeTemplate
     * @param segmentationFileTemplate 
     */
    public MaskMriMatcher(String MriParentPath, 
            List<String> subDirectoryTemplate, 
            List<String> studyDirectoryTemplate, 
            List<String> imageTypeTemplate, 
            List<String> segmentationFileTemplate) {
        mriParentDirectory = MriParentPath;
        this.subDirectoryTemplate = subDirectoryTemplate.toArray(
                new String[subDirectoryTemplate.size()]);
        this.studyDirectoryTemplate = studyDirectoryTemplate.toArray(
                new String[studyDirectoryTemplate.size()]);
        this.imageTypeTemplate = imageTypeTemplate.toArray(
                new String[imageTypeTemplate.size()]);
        this.segmentationFileTemplate = segmentationFileTemplate.toArray(
                new String[segmentationFileTemplate.size()]);
        
        errorInformation = new ArrayList<>(); // Init empty Error info

    }
    
    /**
     * Initialise the class with the file templates
     * @require All arguments are not null
     * @param MriParentPath
     * @param subDirectoryTemplate
     * @param studyDirectoryTemplate
     * @param imageTypeTemplate
     * @param segmentationFileTemplate 
     */
    public MaskMriMatcher(String MriParentPath, 
            String[] subDirectoryTemplate, 
            String[] studyDirectoryTemplate, 
            String[] imageTypeTemplate, 
            String[] segmentationFileTemplate) {
        mriParentDirectory = MriParentPath;
        this.subDirectoryTemplate = subDirectoryTemplate;
        this.studyDirectoryTemplate = studyDirectoryTemplate;
        this.imageTypeTemplate = imageTypeTemplate;
        this.segmentationFileTemplate = segmentationFileTemplate;
        
        errorInformation = new ArrayList<>(); // Init empty Error info
    }
    
    /**
     * Initialise the class with the file templates
     * Takes the string lists of templates separated by ", "
     * @require All arguments are not null
     * @param MriParentPath
     * @param subDirectoryTemplates
     * @param studyDirectoryTemplates
     * @param imageTypeTemplates
     * @param segmentationFileTemplates
     */
    public MaskMriMatcher(String MriParentPath, 
            String subDirectoryTemplates, 
            String studyDirectoryTemplates, 
            String imageTypeTemplates, 
            String segmentationFileTemplates) {
        mriParentDirectory = MriParentPath;
        this.subDirectoryTemplate = 
                subDirectoryTemplates.replace(" ", "").split(",");
        this.studyDirectoryTemplate = 
                studyDirectoryTemplates.replace(" ", "").split(",");
        this.imageTypeTemplate = 
                imageTypeTemplates.replace(" ", "").split(",");
        this.segmentationFileTemplate = 
                segmentationFileTemplates.replace(" ", "").split(",");
        
        errorInformation = new ArrayList<>(); // Init empty Error info
    }
    
    /**
     * Extracts the patient number from a mask according to the template
     * @param mask
     * @return "" if no matching template otherwise the pateintNumber
     */
    public String getPatientNumberFromMask(Mask mask) {
        String maskFilename = mask.getFilename();
        String patientNumber = "";
        int start = -1; // start index for patient number
        int end = -1; // end index for patient number
        boolean foundMatch = false;
        for (String template : segmentationFileTemplate) {
            if (template.length() != maskFilename.length()) {
                continue;
            }
            
            // Locate the start and then end of the slashes in mask
            for (int i = 0; i < template.length(); i++) {
                if (template.charAt(i) == '/') {
                    if (start == -1) {
                        start = i;
                    } else if (end == -1 && template.charAt(i+1) != '/') {
                        end = i;
                        foundMatch = true;
                        break;
                    }
                }
            }
            
            if (foundMatch) {
                break;
            }
        }
        
        if (start != -1 && end != -1) {
            patientNumber = maskFilename.substring(start, end+1);
        }
        return patientNumber;
    }
    
    /**
     * Returns an instance of Image located from a mask instance.
     * @param mask to find the matching image from.
     * @return the matching Image for mask or null
     */
    public Image getImageFromMask(Mask mask) {
        // Reset error info
        errorInformation = new ArrayList<>();
        
        String patientDirectory = getMriSubDirectory(
                getPatientNumberFromMask(mask));
        String studyDirectory = getStudyDirectory(
                Paths.get(mriParentDirectory, patientDirectory).toString());
        String imageDirectory = getImageDirectory(Paths.get(
                mriParentDirectory, patientDirectory, 
                studyDirectory).toString());
        
        // If possible Open up the image and return it.
        try {
            return new Image(Paths.get(mriParentDirectory, patientDirectory, 
                studyDirectory, imageDirectory).toString());
        } catch (IOException ex) {
            // Set error StringList
            errorInformation.add(getPatientNumberFromMask(mask));
            errorInformation.add(" Error Unable to locate MRI");
            errorInformation.add("mask file: " + mask.getPath());
            errorInformation.add("mri parent directory: " + mriParentDirectory);
            errorInformation.add("patient directory: " + patientDirectory);
            errorInformation.add("study directory: " + studyDirectory);
            errorInformation.add("patient directory: " + imageDirectory);
            return null;
        }
    }
    
    /**
     * 
     * @return Information about paths searched which is set if an image is not 
     * able to be found.
     */
    public ArrayList<String> getErrorInformation() {
        return new ArrayList<>(errorInformation);
    }
    
    /**
     * Finds the Patient MRI directory
     * @param patientNumber
     * @return 
     */
    private String getMriSubDirectory(String patientNumber) {
        String[] mriParentFiles = getDirectoriesIn(mriParentDirectory);
        
        for (String subFile : mriParentFiles) {
            for (String subjectDir : subDirectoryTemplate) {
                if (subFile.contains(patientNumber) && 
                        subFile.contains(subjectDir)) {
                    return subFile;
                }
            }
        }
        return ""; // No matches
    }
    
    /**
     * Finds the correct study directory according to the templates
     * @param patientDirectory
     * @return "" if not matching directory found otherwise the studyDirectory
     */
    private String getStudyDirectory(String patientDirectoryPath) {
        String[] studyDirectories = getDirectoriesIn(patientDirectoryPath);
        
        for (String subFile : studyDirectories) {
            for (String studyDir : studyDirectoryTemplate) {
                if (subFile.contains(studyDir)) {
                    return subFile;
                }
            }
        }
        return ""; // No matches
        
    }
    
    /**
     * Compares the directory names in possibleDirectories to the template, 
     * template sections. A template can be in multiple sections if a 
     * number field is expected in the filename.
     * The match which is lexographically last is returned.
     * I.E if a number field is present, the largest number.
     * @param possibleDirectories a list of names to compare to the template
     * @param templateSections a list of sections of the template
     * @return the lexographically last match from possibleDirectories.
     *         Otherwise "", the empty string is returned.
     */
    private String compareDirectoriesToTemplate(String[] possibleDirectories, 
            String[] templateSections) {
        ArrayList<String> possibleImages = new ArrayList<>();
        boolean validName;
        
        for (String possibleDirectory : possibleDirectories) {
            validName = true;
            // Check all sections of the template
            for (String template : templateSections) {
                if (!possibleDirectory.contains(template)) {
                    validName = false;
                }
            }
            
            if (validName) { // Add it to possibilities
                possibleImages.add(possibleDirectory);
            }
            
        }
        
        if (possibleImages.size() > 0) {
            Collections.sort(possibleImages); // Sort so highest number is used
            return (String)possibleImages.get(possibleImages.size() - 1);
        }
        return "";
    }
    
    /**
     * Gets the first image directory which matches the highest priority 
     * template and returns it.
     * @param studyDirectoryPath the path to directory containing the image 
     *                           directories.
     * @return the image directory
     */
    private String getImageDirectory(String studyDirectoryPath) {
        String[] possibleImageDirectories = getDirectoriesIn(
                studyDirectoryPath);
        
        String imageDirectory = ""; // return imageDirectory
        
        // For each MRI image template
        for (String template : imageTypeTemplate) {
            // Check if template contains a number field, represented by "/"'s
            int numberFieldSize = template.length() - 
                    template.replace("/", "").length();
            String numberField = new String(
                        new char[numberFieldSize]).replace("\0", "/");
            
            String[] templateSections = template.split(numberField);
            
            imageDirectory = compareDirectoriesToTemplate(
                    possibleImageDirectories, templateSections);
            
            if (!imageDirectory.equals("")) { // If match found, return it
                return imageDirectory;
            } // else continue to next, lesser priority template
        }
        return imageDirectory;
    }
    
    /**
     * Returns a list of paths which are the paths to the files
     * within the given folder. Paths are sorted lexographically.
     * @param filePath directory to get files' paths
     * @return paths to each of the files
     */
    private String[] getFilesIn(String filePath){
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
        
        return containedFiles;
    }
    
    /**
     * Returns a list of paths which are the paths to the directories
     * within the given folder. Paths are sorted lexographically.
     * @param filePath directory to get files' paths
     * @return paths to each of the files
     */
    private String[] getDirectoriesIn(String filePath){
        // Get a list of files in the given folder
        File file = new File(filePath);
        String[] containedFiles = file.list(
                new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        }
        );
        
        return containedFiles;
    }
}
