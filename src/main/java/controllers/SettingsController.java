package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class SettingsController {

    @FXML
    private CheckBox ecsposition;

    @FXML
    private GridPane ecspositionTbl;

    @FXML
    private void check(ActionEvent event) {
        if(ecsposition.isSelected()) ecspositionTbl.setDisable(false);
        else ecspositionTbl.setDisable(true);
    }
}
