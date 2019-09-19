package tree;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Tree {
    @FXML
    private Pane treePane;

    @FXML
    private void renderTree() {
        treePane.getChildren().add(new Line(0, 0, 10, 10));
    }
}
