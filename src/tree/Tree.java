package tree;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Tree {
    @FXML
    private Pane treePane;

    @FXML
    private void renderTree() {
        deleteTree();

        // TODO get variables from input
        tree(10, treePane.getWidth() / 2, treePane.getHeight() / 2, -45, 10);
    }

    // TODO get variables from input
    private void tree(int n, double x, double y, double a, double branchLength) {
        // Fields
        final double branchShrinkRatio = 1;
        final double branchAngle = Math.toRadians(15);
        final double cx = x + (Math.cos(a) * branchLength);
        final double cy = y + (Math.sin(a) * branchLength);

        // Draw Branch
        treePane.getChildren().add(new Line(x, y, cx, cy));

        // Checks
        if (n == 0) {
            return;
        }
        if (branchLength < 2) {
            return;
        }
        if (Math.random() == 0) {
            System.out.println("How Random");
            // return;
        }

        // Recursion
        tree(n - 1, cx, cy, a - branchAngle, branchLength * branchShrinkRatio);
        tree(n - 1, cx, cy, a + branchAngle, branchLength * branchShrinkRatio);
    }

    private void deleteTree() {
        treePane.getChildren().removeAll();
    }


}
