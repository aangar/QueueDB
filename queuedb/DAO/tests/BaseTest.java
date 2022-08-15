package queuedb.DAO.tests;

import java.io.File;

/**
 * The BaseTest class
 */
public class BaseTest {
    /** Color for a successful test */
    public final String SUCCESS_COLOR = "\u001B[32m";
    /** Color for a failing test */
    public final String FAIL_COLOR = "\u001B[31m";
    /** reset color??? */
    public final String ANSI_RESET = "\u001B[0m";
    /** The directory to the testing collection. */
    public String TEST_COLLECTION_DIR;
    /** The test File name. just for output reasons :D */
    public String TestFileName;

    public void runTests(Class<?> clazz) {
        this.clearTestCollection();
        new AutoTestRunner<>(clazz, this.TEST_COLLECTION_DIR);
        this.removeTestCollection();
    }

    /**
     * Logs the result to the console for test status.
     * 
     * @param result   the result of the test. Set by the user, must output to a
     *                 boolean.
     * @param testName the name of the test.
     */
    public void logTestResult(boolean result, String testName) {
        String value = result ? "PASSED" : "FAILED";
        String color = result ? this.SUCCESS_COLOR : this.FAIL_COLOR;
        String f = "%s:%s %s%s%s";
        String formatter = String.format(f, this.TestFileName, testName, color, value, ANSI_RESET);
        System.out.println(formatter);

    }

    /**
     * Clears the test collection. Does not delete.
     */
    public void clearTestCollection() {
        if (this.TEST_COLLECTION_DIR == null || this.TEST_COLLECTION_DIR.isEmpty()) {
            System.err.println("Collection Directory not specified, unable to clear.");
            return;
        }
        File baseDir = new File(this.TEST_COLLECTION_DIR);
        String[] existingFiles = baseDir.list();
        if (existingFiles.length < 1) {
            return;
        }
        if (existingFiles.length >= 1) {
            for (String file : existingFiles) {
                new File(this.TEST_COLLECTION_DIR + file).delete();
            }
        }
    }

    /**
     * Removes the test collection after testing.
     */
    public void removeTestCollection() {
        new File(this.TEST_COLLECTION_DIR).delete();
    }
}