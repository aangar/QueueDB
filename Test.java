import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        generateNodes(root, 100);
        System.out.println(
                root.getRightNode().getLeftNode().getLeftNode().getRightNode().getLeftNode().getLeftNodeValue());
        System.out.println(
                root.getRightNode().getLeftNode().getLeftNode().getRightNode().getLeftNode().getRightNodeValue());
    }

    public static void generateNodes(TreeNode node, int max) {
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
}

/**
 * A class for a node of a binary tree.
 * Written entirely from scratch.
 * Thanks, Derek.
 * 
 * @author aangar
 */
class TreeNode {
    private Integer val;
    private TreeNode left;
    private TreeNode right;

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
     */
    public void generateChildren() {
        this.left = new TreeNode(this.val * 2);
        this.right = new TreeNode((this.val * 2) + 1);
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
     * sets the value for the target node
     * 
     * @param value the set value of the node.
     */
    public void setValue(int value) {
        this.val = value;
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
}