public class Test {
    public static void main(String[] args) {
        int totalRows = 4;
        int whiteSpaceMax = totalRows + 1;
        int[][] items = new int[whiteSpaceMax + totalRows][whiteSpaceMax + totalRows];
        int numeric = 1;

        for (int row = 0; row < totalRows; row++) {
            for (int index = 1; index < whiteSpaceMax; index += 2 ) {
                items[row][index] = numeric;
                numeric++;
            }
        }

        for (int row = 0; row < totalRows; row++) {
            for (int index = 0; index < whiteSpaceMax; index++ ) {
                System.out.print(String.format("[%d] ", items[row][index]));
            }
            System.out.println(" ");
        }

    }
}

class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}