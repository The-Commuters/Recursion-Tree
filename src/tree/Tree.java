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
    private void tree(int teller, double x, double y, double branchAngle, double branchLength) {

// Fields
        final double branchShrinkRatio = 1;
        final double branchAngle = Math.toRadians(15);
        final double cx = x + (Math.cos(branchAngle) * branchLength);
        final double cy = y + (Math.sin(branchAngle) * branchLength);

        // Draw Branch
        treePane.getChildren().add(new Line(x, y, cx, cy));

        // Checks
        if (teller == 0) {
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
        tree(teller - 1, cx, cy, branchAngle - branchAngle, branchLength * branchShrinkRatio);
        tree(teller - 1, cx, cy, branchAngle + branchAngle, branchLength * branchShrinkRatio);
    }

    private void deleteTree() {
        treePane.getChildren().removeAll();
    }


}
