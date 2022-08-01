public class Test {
    public static void main(String[] args) {
        TreeNode gen = generateNodeBinary(3);
        System.out.println(gen.getValue());
        System.out.println(gen.getLeftValue());
        System.out.println(gen.getRightValue());
    }

    private static TreeNode generateNodeBinary(int value) {
        TreeNode node = new TreeNode(value);
        node.generateNodeAndChildren();
        return node;
    } 
}

class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public void generateNodeAndChildren() {
        this.left = new TreeNode(this.val * 2);
        this.right = new TreeNode((this.val * 2) + 1);
    }

    public void setLeft(int a) {
        this.left = new TreeNode(a);
    }

    public void setRight(int a) {
        this.right = new TreeNode(a);
    }

    public void setValue(int a) {
        this.val = a;
    }

    public int getLeftValue() {
        return this.left.getValue();
    }

    public int getRightValue() {
        return this.right.getValue();
    }

    public TreeNode getRightNode() {
        return this.right;
    }

    public TreeNode getLeftNode() {
        return this.left;
    }

    public int getValue() {
        return this.val;
    }
}