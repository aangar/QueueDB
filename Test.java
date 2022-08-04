import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Test {
    public List<String> path = new ArrayList<>();

    public static void main(String[] args) {
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
        System.out.println("Does " + validateValue + " exist: " + root.getDoesValueExist(root, validateValue));

        s.close();
    }
}

/**
 * A class for a node of a binary tree.
 * Written entirely from scratch.
 * Thanks, Derek.
 * 
 * @author AJ Angarita, 2022
 * 
 */
class TreeNode {
    // base TreeNode fields
    private Integer val;
    private TreeNode left;
    private TreeNode right;
    /**
     * These fields are in tandem to one antoher.
     * generatedPathToValue is set in the findPath method.
     * generatedPathValue is set in the followGeneratedPath method
     */
    private List<Boolean> generatedPathToValue;
    private Integer generatedPathValue;

    // this field is for checking if a value exists in a generated tree.
    private boolean doesValueExist = false;

    /**
     * default empty constructor
     */
    public TreeNode() {
    }

    /**
     * Constructor with a passed value. This will NOT generate anything for left /
     * right nodes.
     * 
     * @param value int value for this node.
     */
    public TreeNode(int value) {
        this.val = value;
    }

    /**
     * constructor that can determine which node to generate.
     * 
     * @param value          the root value.
     * @param nodeToGenerate the string ( either left or right ) of the node to
     *                       generate.
     */
    public TreeNode(int value, String nodeToGenerate) {
        this.val = value;
        if (nodeToGenerate.toLowerCase().equals("left")) {
            this.left = new TreeNode(this.val * 2);
        }
        if (nodeToGenerate.toLowerCase().equals("right")) {
            this.right = new TreeNode((this.val * 2) + 1);
        }
    }

    /**
     * Generates values for the children nodes,
     * not inlcuding their following nodes.
     * 
     * @author AJ Angarita, 2022
     * 
     */
    public void generateChildren() {
        this.left = new TreeNode(this.val * 2);
        this.right = new TreeNode((this.val * 2) + 1);
    }

    public void generateChildNode(String node) {
        switch (node) {
            case "left":
                this.left = new TreeNode(this.val * 2);
                break;
            case "right":
                this.right = new TreeNode((this.val * 2) + 1);
                break;
            default:
                System.err.println("Node name not recognized!");
        }
    }

    public HashMap<String, Integer> previewValues() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("left", this.val * 2);
        values.put("right", (this.val * 2) + 1);
        return values;
    }

    /**
     * A method to generate the nodes up to a specified value. Intended for use with
     * a Complete Binary Tree.
     * 
     * @param node the base node.
     * @param max  the value that cannot be exceeded.
     * @author AJ Angarita, 2022
     */
    public void generateNodes(TreeNode node, int max) {
        HashMap<String, Integer> preview = node.previewValues();
        if (preview.get("left") <= max && preview.get("right") <= max) {
            node.generateChildren();
        }

        if (preview.get("left") > max || preview.get("right") > max) {
            if (preview.get("left") <= max) {
                node.generateChildNode("left");
            }
            if (preview.get("right") <= max) {
                node.generateChildNode("right");
            }
        }

        if (preview.get("left") > max) {
            return;
        }
        if (preview.get("right") > max) {
            return;
        }

        if (!node.getLeftNodeValue().equals(null)) {
            generateNodes(node.getLeftNode(), max);
        }

        if (!node.getRightNodeValue().equals(null)) {
            generateNodes(node.getRightNode(), max);
        }
    }

    /**
     * Takes the boolean input path, and converts it to start from a root value of
     * 1.
     * 
     * @param input the initial order
     * @return the corrected order
     * @author AJ Angarita, 2022
     */
    private List<Boolean> correctOrder(List<Boolean> input) {
        List<Boolean> corrected = new ArrayList<Boolean>(input.size());
        for (int i = 0; i <= input.size() - 1; i++) {
            int index = input.size() - (1 + i);
            corrected.add(input.get(index));
        }
        return corrected;
    }

    /**
     * Finds the path to the specified value in context of a Complete Binary Tree.
     * Does <b>not</b> depend on node generation.
     * 
     * @param number the current number
     * @param pathTo the list for the path
     */
    public void generatePathToValue(int number, List<Boolean> pathTo) {
        List<Boolean> newPath = pathTo.size() > 0 ? pathTo : new ArrayList<Boolean>();

        if (number % 2 > 0) {
            newPath.add(true);
            int num = (number - 1) / 2;
            if (num <= 1) {
                TreeNode initialNode = new TreeNode(1);
                initialNode.generateChildren();
                List<Boolean> correctedOrder = correctOrder(newPath);
                this.generatedPathToValue = correctedOrder;
                return;
            }
            generatePathToValue((number - 1) / 2, newPath);
        }

        if (number % 2 == 0) {
            newPath.add(false);
            int num = number / 2;
            if (num <= 1) {
                TreeNode initialNode = new TreeNode(1);
                initialNode.generateChildren();
                List<Boolean> correctedOrder = correctOrder(newPath);
                this.generatedPathToValue = correctedOrder;
                return;
            }
            generatePathToValue(number / 2, newPath);
        }
    }

    /**
     * Follows <b>this.generatedPathToValue</b>, and will set
     * <b>this.generatedPathValue</b> as
     * the
     * final result once reaching the end of the steps.
     * 
     * @author AJ Angarita, 2022
     * 
     * @param step the current step.
     * @param node the node it's on.
     * @param path the path to follow.
     */
    private void followGeneratedPath(int step, int value) {
        if (step > this.generatedPathToValue.size() - 1) {
            this.generatedPathValue = value;
            return;
        }

        if (this.generatedPathToValue.get(step)) {
            followGeneratedPath(step + 1, (value * 2) + 1);
        }

        if (!this.generatedPathToValue.get(step)) {
            followGeneratedPath(step + 1, value * 2);
        }
    }

    /**
     * Generates the path to a value based off a Complete Binary Tree. It then
     * follows that path and will return the value. This is essentially to make sure
     * automatic generation is working properly.
     * 
     * @param target the value to validate.
     * @return the value the path generates.
     * @author AJ Angarita, 2022
     */
    public boolean validateAutomaticGeneration(int target) {
        this.generatedPathToValue = null;
        this.generatedPathValue = null;
        generatePathToValue(target, new ArrayList<>());
        followGeneratedPath(0, 1);
        return target == this.generatedPathValue;
    }

    /**
     * A private method that will search a tree for the target value.
     * If the value is found, then this.doesValueExist will be set.
     * If not, then it will remain null or false - initially null, but
     * set to false when calling getDoesValueExist.
     * 
     * @author AJ Angarita, 2022
     *
     * @param node        The current node. Note this usually starts at the root
     *                    node,
     *                    however it can start wherever - just know where that is.
     * @param targetValue The value to search for. This remains consistent
     *                    and is passed from method call to method call.
     */
    private void findIfValueExists(TreeNode node, int targetValue) {
        if (node.getNodeValue() == targetValue) {
            this.doesValueExist = true;
            return;
        }

        if (node.getRightNode() != null && node.getLeftNode() != null) {
            findIfValueExists(node.getLeftNode(), targetValue);
            findIfValueExists(node.getRightNode(), targetValue);
        }

        if (node.getRightNode() != null && node.getLeftNode() == null) {
            findIfValueExists(node.getRightNode(), targetValue);
        }
        if (node.getRightNode() == null && node.getLeftNode() != null) {
            findIfValueExists(node.getLeftNode(), targetValue);
        }
    }

    /**
     * This will check if the specified value exists on the generated tree.
     * 
     * @param node   root node, ideally.
     * @param target value to search for.
     * @return true / false if the value exists.
     */
    public boolean getDoesValueExist(TreeNode node, int target) {
        this.doesValueExist = false;
        findIfValueExists(node, target);
        if (this.doesValueExist) {
            return true;
        }
        return false;
    }

    /**
     * gets the target node value.
     * 
     * @return the value of the node.
     */
    public Integer getNodeValue() {
        return this.val;
    }

    /**
     * gets the value for the node to the right of the current node.
     * 
     * @return the value of the right node.
     */
    public Integer getRightNodeValue() {
        return this.right.getNodeValue();
    }

    /**
     * gets the value for the node to the left of the current node.
     * 
     * @return the value of the left node.
     */
    public Integer getLeftNodeValue() {
        return this.left.getNodeValue();
    }

    /**
     * Gets the complete left node.
     * 
     * @return Left Node.
     */
    public TreeNode getLeftNode() {
        return this.left;
    }

    /**
     * Gets the complete Right Node.
     * 
     * @return Right Node.
     */
    public TreeNode getRightNode() {
        return this.right;
    }

    /**
     * getter for the found value from the followed path.
     * 
     * @return If negative 1, a path was not generated. Otherwise the computed
     *         value.
     */
    public int getGeneratedPathValue() {
        if (this.generatedPathToValue == null || this.generatedPathToValue.size() < 1) {
            System.err.print("No path has been generated. Generate a path now? ( yes / no ): ");
            return -1;
        }
        followGeneratedPath(0, 1);
        return this.generatedPathValue;
    }

    /**
     * getter for the human readable path to the previous specified value
     * 
     * @return list of the steps to follow
     */
    public List<String> getReadablePathToValue() {
        List<String> readable = new ArrayList<String>();
        for (int i = 0; i < this.generatedPathToValue.size(); i++) {
            if (this.generatedPathToValue.get(i)) {
                readable.add("Right");
            }
            if (!this.generatedPathToValue.get(i)) {
                readable.add("Left");
            }
        }
        return readable;
    }

    /**
     * sets the value for the target node
     * 
     * @param value the set value of the node.
     */
    public void setValue(int value) {
        this.val = value;
    }
}