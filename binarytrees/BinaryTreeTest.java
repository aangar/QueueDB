package binarytrees;

import java.util.Scanner;

/**
 * Class for testing Binary Trees
 * 
 * @author aangar, 2022
 */
public class BinaryTreeTest {
    public static void runTestCases() {
        Scanner s = new Scanner(System.in);
        System.out.print("Max generation value: ");
        int maxGeneration = s.nextInt();
        s.nextLine();
        System.out.println("\nGenerating BST up to " + maxGeneration);
        TreeNode root = new TreeNode(1);
        root.generateNodes(root, maxGeneration);
        System.out.println("BST Generated!");
        System.out.print("Value to verify existence: ");
        String input = s.nextLine();
        Integer validateValue = Integer.parseInt(input);
        System.out.println("Does " + validateValue + " exist: " +
                root.getDoesValueExist(root, validateValue));

        s.close();
    }
}
