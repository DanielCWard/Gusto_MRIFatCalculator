/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatfractioncalculator;

import java.util.ArrayList;
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
    
    /**
     * Initialise the class with the file templates
     * @require All arguments are not null
     * @param subDirectoryTemplate
     * @param studyDirectoryTemplate
     * @param imageTypeTemplate
     * @param segmentationFileTemplate 
     */
    public MaskMriMatcher(List<String> subDirectoryTemplate, 
            List<String> studyDirectoryTemplate, 
            List<String> imageTypeTemplate, 
            List<String> segmentationFileTemplate) {
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
     * @param subDirectoryTemplate
     * @param studyDirectoryTemplate
     * @param imageTypeTemplate
     * @param segmentationFileTemplate 
     */
    public MaskMriMatcher(String[] subDirectoryTemplate, 
            String[] studyDirectoryTemplate, 
            String[] imageTypeTemplate, 
            String[] segmentationFileTemplate) {
        this.subDirectoryTemplate = subDirectoryTemplate;
        this.studyDirectoryTemplate = studyDirectoryTemplate;
        this.imageTypeTemplate = imageTypeTemplate;
        this.segmentationFileTemplate = segmentationFileTemplate;
    }
    
    /**
     * Initialise the class with the file templates
     * Takes the string lists of templates separated by ", "
     * @require All arguments are not null
     * @param subDirectoryTemplates
     * @param studyDirectoryTemplates
     * @param imageTypeTemplates
     * @param segmentationFileTemplates
     */
    public MaskMriMatcher(String subDirectoryTemplates, 
            String studyDirectoryTemplates, 
            String imageTypeTemplates, 
            String segmentationFileTemplates) {
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
    
}
