public class Test {
    public static void main(String[] args) {
        int[] values = {1,2,3,4,5,6,7,8,9,10};
        TreeNode tree = generateTree(values);
        System.out.println(tree.getLeftNode().getRightValue());
    }

    private static TreeNode generateTree(int[] values) {
        TreeNode root = new TreeNode(values[0]);
        root.setLeft(values[1]);
        root.setRight(values[2]);
        return root;
    }
}

class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode() { }

    public TreeNode(int val) {
        this.val = val;
    }

    public void setLeft() { }
    public void setRight() { }

    public void setLeft(int a) {
        this.left = new TreeNode(a);
    }

    public void setRight(int a) {
        this.right = new TreeNode(a);
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