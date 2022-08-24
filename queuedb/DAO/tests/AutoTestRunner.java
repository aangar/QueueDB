package queuedb.DAO.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h3>Automatic Test Runner Class.</h3>
 * <br>
 * </br>
 * This class automatically runs tests marked in the QueueDBTest.java file.
 * Each DAO Test class contains a method called <code>runTests()</code>, which will
 * load and run all written tests <b>if configured properly</b>.
 * <br></br>
 * <b><i>Requirements:</i></b>
 * <ul>
 * <li>Method is void</li>
 * <li><code>this.logTestResult</code> is called. This will not break the test, but
 * the tests' status will remain unknown.</li>
 * </ul>
 * <br></br>
 * @author aangar, 2022.
 */
public class AutoTestRunner<T> {
    private String testingPath;
    private String className;

    /**
     * Generates an instance of the test class targeted. This will allow the methods
     * to be invoked in the <code>runTestMethods()</code> method.
     * 
     * @return an instance of the target / test class.
     */
    private T generateInstance() {
        T inst = null;
        try {
            Class<?> c = Class.forName(this.className);
            Constructor<?> s[] = c.getDeclaredConstructors();
            inst = (T) s[0].newInstance(this.testingPath);
        } catch (InvocationTargetException nsm) {
            System.out.println(nsm);
        } catch (InstantiationException ie) {
            System.out.println(ie);
        } catch (IllegalAccessException iae) {
            System.out.println(iae);
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }
        return inst;
    }

    /**
     * Runs the test methods found in the Target Test Class.
     */
    private void runTestMethods() {
        T obj = generateInstance();
        BaseTest bt = new BaseTest();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            try {
                m.invoke(obj);
                bt.TEST_COLLECTION_DIR = this.testingPath;
                bt.clearTestCollection();
            } catch (InvocationTargetException nsm) {
                System.out.println();
                nsm.printStackTrace();
            } catch (IllegalAccessException iae) {
                System.out.println(iae);
            }
        }
    }

    /**
     * Default Constructor for the AutoTestRunner. Instantiating this will run the
     * subsequent tests found within the target Test Class.
     * 
     * @param clazz       the target Test class.
     * @param testingPath the path to the testing directory.
     */
    public AutoTestRunner(Class<T> clazz, String testingPath) {
        this.className = clazz.getName();
        this.testingPath = testingPath;
        runTestMethods();
    }
}
