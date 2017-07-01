/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Matches an MRI mask file with the MRI based on some input
 * @author Daniel Ward
 */
public class MaskMriMatcher {
    
    /**
     * Planning:
     * Args:
     *  mri parent directory
     *  filter specifications
     * funcs:
     *  located MRI dir 
     *      takes NIGzfile and returns MRI path or IMage instance
     *      takes PSCID/image information and returns MRI path or IMage instance
     */
    
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
    
    public String getImageFromMask(Mask mask) {
        String subDir = getMriSubDirectory(getPatientNumberFromMask(mask));
        return getStudyDirectory(Paths.get(mriParentDirectory, subDir).toString());
        
        //TODO: Return the image instance
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
                    System.out.println("Found study Folder: " + subFile);
                    return subFile;
                }
            }
        }
        return ""; // No matches
        
    }
    
    private String getImageDirectory(String studyDirectoryPath) {
        return "";
        // TODO
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
