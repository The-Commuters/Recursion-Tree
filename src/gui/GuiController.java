package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GuiController {
    @FXML private Pane treePane;
    @FXML private Pane sliderPane;
    @FXML private Spinner branchLimitSpinner;

    private ArrayList<Line> tree;
    private Slider slider;


    private int inputN = 10; // 0 - 20
    private double inputBranchWitherRatio = 0.9; // 0 - 1
    private double inputBranchLength = 30; // 1 - 100
    private double inputBranchGrowthRatio = 0.9; // 0.5 - 1
    private double inputBranchAngle = 15; // 1 - 30
    private double inputTrunkAngle = -90; // -180 - 0

    @FXML
    private void initialize() {
        initializeBranchLimitSpinner();
    }

    /**
     * Sets upp and initializes the spinner input for branchLimit
     */
    private void initializeBranchLimitSpinner() {
        SpinnerValueFactory<Integer> valueFactory;
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, inputN){
            @Override public void decrement(int steps) {
                final int min = getMin();
                final int currentValue = getValue();
                final int newIndex = currentValue - steps * getAmountToStepBy();
                setValue(Math.max(newIndex, min));
                inputN = newIndex;
                renderTree();
            }

            @Override public void increment(int steps) {
                final int max = getMax();
                final int currentValue = getValue();
                final int newIndex = currentValue + steps * getAmountToStepBy();
                setValue(Math.min(newIndex, max));
                inputN = newIndex;
                renderTree();
            }
        };
        branchLimitSpinner.setValueFactory(valueFactory);
    }

    /**
     * The method used to clear and build a tree
     */
    @FXML
    private void renderTree() {
        unmountTree();
        buildTree(inputN, treePane.getWidth() / 2, treePane.getHeight(), Math.toRadians(inputTrunkAngle), inputBranchLength);
        mountTree();
    }

    /**
     * Renders the slider that controls the inputBranchWitherRatio field
     */
    @FXML
    private void renderBranchWitherSlider() {
        renderSlider(0, 1, inputBranchWitherRatio, (e) -> {
            inputBranchWitherRatio = slider.getValue();
            renderTree();
        });
    }

    /**
     * Renders the slider that controls the inputBranchLength field
     */
    @FXML
    private void renderBranchLengthSlider() {
        renderSlider(1, 100, inputBranchLength, (e) -> {
            inputBranchLength = slider.getValue();
            renderTree();
        });
    }

    /**
     * Renders the slider that controls the inputBranchGrowthRatio field
     */
    @FXML
    private void renderBranchGrowthRatioSlider() {
        renderSlider(0.5, 1, inputBranchGrowthRatio, (e) -> {
            inputBranchGrowthRatio = slider.getValue();
            renderTree();
        });
    }

    /**
     * Renders the slider that controls the inputBranchAngle field
     */
    @FXML
    private void renderBranchAngleSlider() {
        renderSlider(1, 30, inputBranchAngle, (e) -> {
            inputBranchAngle = slider.getValue();
            renderTree();
        });
    }

    /**
     * Renders the slider that controls the inputTrunkAngle field
     */
    @FXML
    private void renderTrunkAngleSlider() {
        renderSlider(-180, 0, inputTrunkAngle, (e) -> {
            inputTrunkAngle = slider.getValue();
            renderTree();
        });
    }

    /**
     * A generic method for rendering sliders with an event on mouse release
     * @param min the minimum value of the slider
     * @param max the maximum value of the slider
     * @param value the default value of the slider
     * @param eventHandler callback
     */
    private void renderSlider(double min, double max, double value, EventHandler eventHandler) {
        unmountSlider();
        buildSlider(min, max, value);
        slider.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
        mountSlider();
    }

    /**
     * Unmounts tree from treePane
     */
    private void unmountTree() {
        if (tree != null) {
            treePane.getChildren().clear();
        }
    }

    /**
     * initializes the tree array and populates it
     * @param n recursion limit
     * @param a initial angle
     */
    private void buildTree(int n, double x, double y, double a, double branchLength) {
        tree = new ArrayList<>();
        populateTree(n, x, y, a, branchLength);
    }

    /**
     * Populates the tree
     */
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
            populateTree(n - 1, cx, cy, a - Math.toRadians(inputBranchAngle), branchLength * inputBranchGrowthRatio);
        }
        if (Math.random() < branchWitherRatio) {
            populateTree(n - 1, cx, cy, a + Math.toRadians(inputBranchAngle), branchLength * inputBranchGrowthRatio);
        }
    }

    /**
     * Generic method for "curving" the branch wither ratio
     * @param n recursion limit
     * @return a curved ratio
     */
    private double curveBranchWitherRatio(int n){
        return Math.pow(((double)n / (double)inputN), inputBranchWitherRatio);
    }

    /**
     * Mounts tree onto treePane
     */
    private void mountTree() {
        if (tree != null) {
            treePane.getChildren().addAll(tree);
        }
    }

    /**
     * Unmounts slider from sliderPane
     */
    private void unmountSlider() {
        if (slider != null) {
            sliderPane.getChildren().remove(slider);
        }
    }

    /**
     * Builds a slider
     * @param value initial value
     */
    private void buildSlider(double min, double max, double value) {
        slider = new Slider(min, max, value);

    }

    /**
     * Mounts slider onto sliderPane
     */
    private void mountSlider() {
        if (slider != null) {
            sliderPane.getChildren().add(slider);
        }
    }
}
