package components;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Observable;
import java.util.function.Function;

public class SharedController {
    @FXML private Pane treePane;
    @FXML private Pane sliderPane;

    private ArrayList<Line> tree;
    private Slider slider;

    private SharedModel model = new SharedModel();

    // TODO get variables from input
    private int inputN = 10; // 0 - 10
    private double inputBranchWitherRatio = 0.9; // 0 - 1
    private double inputBranchLength = 30; // 1 - 30
    private double inputBranchShrinkRatio = 0.9; // 0.5 - 1
    private double inputBranchAngle = Math.toRadians(15); // 1 - 40
    private double inputTrunkAngle = Math.toRadians(-90); // -180 - 0

    @FXML
    private void renderTree() {
        unmountTree();
        buildTree(inputN, treePane.getWidth() / 2, treePane.getHeight(), inputTrunkAngle, inputBranchLength);
        mountTree();
        System.out.println(inputBranchWitherRatio);
    }

    @FXML
    private void renderBranchWitherSlider() {
        renderSlider(0, 1, inputBranchWitherRatio, (e) -> {
            inputBranchWitherRatio = slider.getValue();
        });
    }

    private void renderSlider(double min, double max, double value, EventHandler eventHandler) {
        unmountSlider();
        buildSlider(min, max, value);
        slider.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
        mountSlider();
    }

    private void unmountTree() {
        if (tree != null) {
            treePane.getChildren().clear();
        }
    }

    private void buildTree(int n, double x, double y, double a, double branchLength) {
        tree = new ArrayList<>();
        populateTree(n, x, y, a, branchLength);
    }

    private void populateTree(int n, double x, double y, double a, double branchLength) {
        // Fields
        final double cx = x + (Math.cos(a) * branchLength);
        final double cy = y + (Math.sin(a) * branchLength);
        final double branchWitherRatio = curveBranchWitherRatio(n);

        // Draw Branch
        tree.add(new Line(x, y, cx, cy));

        // Checks
        if (n == 0) {
            return;
        }
        if (branchLength < 2) {
            return;
        }

        // Recursion
        if (Math.random() < branchWitherRatio) {
            populateTree(n - 1, cx, cy, a - inputBranchAngle, branchLength * inputBranchShrinkRatio);
        }
        if (Math.random() < branchWitherRatio) {
            populateTree(n - 1, cx, cy, a + inputBranchAngle, branchLength * inputBranchShrinkRatio);
        }
    }

    private double curveBranchWitherRatio(int n){
        return Math.pow(((double)n / (double)inputN), inputBranchWitherRatio);
    }

    private void mountTree() {
        if (tree != null) {
            treePane.getChildren().addAll(tree);
        }
    }

    private void unmountSlider() {
        if (slider != null) {
            sliderPane.getChildren().remove(slider);
        }
    }

    private void buildSlider(double min, double max, double value) {
        slider = new Slider(min, max, value);
    }

    private void mountSlider() {
        if (slider != null) {
            sliderPane.getChildren().add(slider);
        }
    }
}
