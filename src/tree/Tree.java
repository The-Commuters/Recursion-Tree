package tree;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Tree {
    @FXML
    private Pane treePane;

    int inputN = 10;
    double inputWitherRatio = 0.5;

    @FXML
    private void renderTree() {
        deleteTree();

        // TODO get variables from input
        tree(inputN, treePane.getWidth() / 2, treePane.getHeight(), -45, 10);
    }

    // TODO get variables from input
    private void tree(int n, double x, double y, double a, double branchLength) {
        // Fields
        final double branchShrinkRatio = 1;
        final double branchAngle = Math.toRadians(15);
        double cx = x + (Math.cos(a) * branchLength);
        double cy = y + (Math.sin(a) * branchLength);
        double branchWitherRatio = calculateWitherRatio(n);
        /*
        if (inputN == n){
            cx =
        }
         */

        // Draw Branch
        treePane.getChildren().add(new Line(x, y, cx, cy));

        // Checks
        if (n == 0) {
            return;
        }
        if (branchLength < 2) {
            return;
        }

        System.out.println("sucsess chance: " + branchWitherRatio + " at stepp " + n);
        // Recursion
        if (Math.random() < branchWitherRatio) {
            tree(n - 1, cx, cy, a - branchAngle, branchLength * branchShrinkRatio);
        }
        if (Math.random() < branchWitherRatio) {
            tree(n - 1, cx, cy, a + branchAngle, branchLength * branchShrinkRatio);
        }
    }

    private double calculateWitherRatio(int n){
        return Math.pow(((double)n / (double)inputN), inputWitherRatio);
    }

    private void deleteTree() {
        treePane.getChildren().removeAll();
    }


}
