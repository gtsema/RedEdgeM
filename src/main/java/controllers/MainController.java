package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final int FIRST = 0;

    @FXML
    BarController barController;

    @FXML
    StatusController statusController;

    @FXML
    SettingsController settingsController;

    @FXML
    TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerBarController(barController);
        ControllerMediator.getInstance().registerStatusController(statusController);
        ControllerMediator.getInstance().registerSettingsController(settingsController);

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(FIRST);

        selectionModel.selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            switch (oldTab.getId()) {
                case "statusTab":
                    break;
                case "captureTab":
                    break;
                case "settingsTab":
                    break;
            }

            switch (newTab.getId()) {
                case "statusTab":
                    break;
                case "captureTab":
                    //ControllerMediator.getInstance().CaptureInit();
                    break;
                case "settingsTab":
                    break;
            }
        });
    }
}
