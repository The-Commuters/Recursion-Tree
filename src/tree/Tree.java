package tree;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Tree {
    @FXML
    private Pane treePane;

    // TODO get variables from input
    private int inputN = 10; // 0 - 10
    private double inputBranchWitherRatio = 0.9; // 0 - 1
    private double inputBranchLength = 30; // 1 - 30
    private double inputBranchShrinkRatio = 0.9; // 0.5 - 1
    private double inputBranchAngle = Math.toRadians(15); // 1 - 40
    private double inputTrunkAngle = Math.toRadians(-90); // -180 - 0

    @FXML
    private void renderTree() {
        deleteTree();
        tree(inputN, treePane.getWidth() / 2, treePane.getHeight(), inputTrunkAngle, inputBranchLength);
    }

    private void tree(int n, double x, double y, double a, double branchLength) {
        // Fields
        final double cx = x + (Math.cos(a) * branchLength);
        final double cy = y + (Math.sin(a) * branchLength);
        final double branchWitherRatio = curveBranchWitherRatio(n);

        // Draw Branch
        treePane.getChildren().add(new Line(x, y, cx, cy));

        // Checks
        if (n == 0) {
            return;
        }
        if (branchLength < 2) {
            return;
        }

        // Recursion
        if (Math.random() < branchWitherRatio) {
            tree(n - 1, cx, cy, a - inputBranchAngle, branchLength * inputBranchShrinkRatio);
        }
        if (Math.random() < branchWitherRatio) {
            tree(n - 1, cx, cy, a + inputBranchAngle, branchLength * inputBranchShrinkRatio);
        }
    }

    private double curveBranchWitherRatio(int n){
        return Math.pow(((double)n / (double)inputN), inputBranchWitherRatio);
    }

    private void deleteTree() {
        treePane.getChildren().clear();
    }


}
