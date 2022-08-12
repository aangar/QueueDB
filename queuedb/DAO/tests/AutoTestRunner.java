package queuedb.DAO.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h3>Automatic Test Runner Class.</h3>
 * <br>
 * </br>
 * 
 * This is a class for an Automatic Test Runner. This will perform tests from a
 * test class, and run them accordingly. All classes must be public and void,
 * following the logging and cleanup conventions.
 * 
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
        for (Method m : methods)
            try {
                m.invoke(obj);
                bt.TEST_COLLECTION_DIR = this.testingPath;
                bt.clearTestCollection();
            } catch (InvocationTargetException nsm) {

            } catch (IllegalAccessException iae) {

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
