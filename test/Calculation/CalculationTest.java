package Calculation;

import fatfractioncalculator.Bounds;
import fatfractioncalculator.FatVolume;
import fatfractioncalculator.Image;
import fatfractioncalculator.Mask;
import fatfractioncalculator.gui.FatFractionCalculatorModel;
import gusto_mrifatcalculator.test.Calculation.FFcalcFileReader;
import gusto_mrifatcalculator.test.Calculation.RowAsDict;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Need to do proper unit tests but this is just temporary.
 *
 * @author Daniel Ward
 */
public class CalculationTest {

    FFcalcFileReader testFileReader;

    public static void main(String[] args) {
        CalculationTest calcTest = new CalculationTest();
        calcTest.testCalculationAndData();
    }

    public CalculationTest() {
        testFileReader = new FFcalcFileReader();
    }

    /**
     * Test the calculation: With these files: expected output is:
     *
     * TIAF (0 - 100): 0.568743804956033 TIAF Volume (cm^3): 19.5702938362956
     * TIAF Abs min: 0 TIAF Mean min: 19.1191489361702 TIAF Abs max: 100 TIAF
     * Mean min: 91.6617021276595
     */
    public void testCalculation() {
        try {
            Image image = new Image("C:\\gusto\\TestFiles\\MRI 4.5\\GUSTO_010-04020\\MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000\\AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058");
            Mask mask = new Mask("C:\\gusto\\TestFiles\\Segmentation files_BAT\\010-04020_BAT.nii.gz");
            Bounds bounds = new Bounds(0, 100);
            FatVolume stats = image.getMaskedVoxelStatistics(mask, bounds);
            System.err.println(stats);
            System.err.println("" + stats.getVolume(image.getVoxelVolume()));
        } catch (IOException ex) {
            System.err.println("Error!!!");
        }
    }

    /**
     * Tests the output of the program.
     *
     * Checks that both the values and the file data (for example patient
     * weight) are correct.
     *
     * Prints the error to a text file and to console.
     *
     * Takes an input file with two patient data and saves this as a dictionary
     * of each user.
     *
     * We then run the program for each user and compare their output files to
     * the correct row in the input test file.
     *
     */
    public void testCalculationAndData() {
        // Read in the input file as a reference dictionary so we can determine
        // whether the 
        String inputFilePath = "C:\\gusto\\allPatients.csv";//"/home/ariane/Documents/gusto/TestFiles/unitTestInputs/all_patients.csv";
        HashMap<String, RowAsDict> expectedValueDict = testFileReader.readInputFile(inputFilePath);

        String baseDir = "C:\\gusto";///home/ariane/Documents/gusto/TestFiles/";
        List sureshsOutput  = Arrays.asList("TIAF (%) (0-100)", "TIAF Vol (cm^3)", " BAT (%) (20-60)" ,"BAT Vol (cm^3)", "WAT (%) (80-90)","WAT Vol (cm^3)");
        /**
         * Set up the two test cases.
         */
        String segmentationFile1 = baseDir + "Segmentation files_BAT/010-04035_BAT.nii.gz";
        String imageDirectory1 = baseDir + "MRI 4.5/GUSTO_010-04035/MRI_RESEARCH_GUSTO_RESEARCH_20140625_184729_416000/AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0052";
        String ouputPath1 = baseDir + "unitTestOutputs/010-04035_BAT.csv";
        HashMap<String, RowAsDict> test1 = writeTestFile(segmentationFile1, imageDirectory1, ouputPath1);

        String segmentationFile2 = baseDir + "Segmentation files_BAT/010-04020_BAT.nii.gz";
        String imageDirectory2 = baseDir + "MRI 4.5/GUSTO_010-04020/MRI_RESEARCH_GUSTO_RESEARCH_20140730_171940_526000/AX_VIBE_6ECHOES_SCAPULA_OUTPUT_FP_0058";
        String ouputPath2 = baseDir + "unitTestOutputs/010-04020_BAT.csv";
        HashMap<String, RowAsDict> test2 = writeTestFile(segmentationFile2, imageDirectory2, ouputPath2);

        ArrayList<HashMap<String, RowAsDict>> tests = new ArrayList<>();
        tests.add(test2);
        tests.add(test1);

        for (int j = 0; j < tests.size(); j++) {
            HashMap<String, RowAsDict> currTest = tests.get(j);

            // Compare the output of test1 to the inputTest file for that user PSCID
            for (String pscid : currTest.keySet()) {
                int testsRun = 0;
                int testsFailed = 0;
                int testsPassed = 0;
                System.err.println("=======================================================");
                System.err.println("========================= Start test ==================");
                System.err.println("Testing: " + pscid);

                RowAsDict actualValues = currTest.get(pscid);
                // Try to see if we have that user in the input test file
                RowAsDict expectedValues = expectedValueDict.get(pscid);

                if (expectedValues != null) {
                    for (String key : actualValues.getKeys()) {
                        testsRun += 1;
                        String valueForKey = actualValues.getValueForKey(key);
                        String successMessage = expectedValues.isEntryCorrect(key, valueForKey);
                        if (successMessage != "Value correct") {
                            testsFailed += 1;
                            System.err.println();
                            System.err.println("-------------------- Start Error ---------------------");
                            System.err.println("Error:" + successMessage);
                            if (sureshsOutput.contains(key)) {
                                System.err.println("Suresh's Value: Key ( " + key + " ) Value ( " + expectedValues.getValueForKey(key) + ")");
                                System.err.println("Actual: Key ( " + key + " ) Value ( " + actualValues.getValueForKey(key) + ")");    
                            } else if (successMessage == "Value incorrect") {
                                System.err.println("Expected: Key ( " + key + " ) Value ( " + expectedValues.getValueForKey(key) + ")");
                                System.err.println("Actual: Key ( " + key + " ) Value ( " + actualValues.getValueForKey(key) + ")");
                            } else {
                                // Key wasn't in the expected values
                                System.err.println("Key Error: ( " + key + " ) Allowed keys: ( " + expectedValues.getKeys() + " )");
                            }

                            System.err.println("--------------------- End Error ----------------------");
                            System.err.println();
                        } else {
                            testsPassed += 1;
                        }

                    }
                }
                System.err.println("Finished testing: " + pscid);
                System.err.println("Tests run: " + testsRun);
                System.err.println("Tests passed: " + testsPassed);
                System.err.println("Tests failed: " + testsFailed);
                System.err.println("========================= End test ====================");
                System.err.println();
            }
        }
    }

    /**
     * Sets up the environment for running the test program.
     *
     * @param segmentationFile
     * @param imageDirectory
     * @param ouputPath
     */
    public HashMap<String, RowAsDict> writeTestFile(String segmentationFile, String imageDirectory, String ouputPath) {
        // Run the program on several files and test against the original 
        // file.
        // Add the image directory and the segmentation Paths to model
        // so it is ready to run after setting CSV file
        FatFractionCalculatorModel model = new FatFractionCalculatorModel();
        // Need to set the bounds to be the old bounds
        Bounds BATBounds = new Bounds(20, 60);
        Bounds WATBounds = new Bounds(80, 90);
        Bounds TIAFBounds = new Bounds(0, 100);
        model.setBATBounds(BATBounds);
        model.setWATBounds(WATBounds);
        model.setTIAFBounds(TIAFBounds);
        
        ArrayList<String> segmentationPaths = new ArrayList<>();
        segmentationPaths.add(segmentationFile);
        model.setSegmentationFilePaths(segmentationPaths);
        model.setImagePath(imageDirectory);
        model.setCsvPath(ouputPath);

        // Run the calculation
        model.runCalculation();

        // Read in the output and return that as a dictionary
        HashMap<String, RowAsDict> ouputFile = testFileReader.readInputFile(ouputPath);
        return ouputFile;
    }
}
