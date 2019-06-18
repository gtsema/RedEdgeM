package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static controllers.StatusController.PAUSED;
import static controllers.StatusController.RUNNING;

public class MainController implements Initializable {

    private static final int FIRST = 0;
    private Map<Class<?>, Object> repo = new HashMap<>();

    public static MainController mediator;

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private BarController barController;

    @FXML
    private StatusController statusController;

    @FXML
    private SettingsController settingsController;

    @FXML
    private CaptureController captureController;

    @FXML
    private TabPane tabPane;

    //-----------------------------------------------------------------------------------------------------------------

    public MainController() {
        mediator = this;
    }

    public <T> T getController(Class<T> controller) {
        return (T) repo.get(controller);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repo.put(BarController.class, barController);
        repo.put(StatusController.class, statusController);
        repo.put(CaptureController.class, captureController);
        repo.put(SettingsController.class, settingsController);

        barController.startStateCtrl();

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(FIRST);

        selectionModel.selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            switch (oldTab.getId()) {
                case "statusTab":
                    if(statusController.getState() == RUNNING) {
                        statusController.pause();
                    }
                    break;
                case "captureTab":
                    break;
                case "settingsTab":
                    break;
            }

            switch (newTab.getId()) {
                case "statusTab":
                    if(statusController.getState() == PAUSED) {
                        statusController.resume();
                    }
                    break;
                case "captureTab":
                    if(statusController.getState() == PAUSED) {
                        captureController.fillFields();
                    }
                    break;
                case "settingsTab":
                    if(statusController.getState() == PAUSED) {
                        settingsController.fillFields();
                    }
                    break;
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------

    void setGUIConnect() {
        barController.setGreenLamp();
        barController.setTextConInfoLbl("Подключено");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    void setGUIDisconnect() {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Отключено");
        statusController.setTextConnectButton("Подключить");
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }

    void setGUIWaiting() {
        barController.setYellowLamp();
        barController.setTextConInfoLbl("Соединение");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    void setGUIError(String message) {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Ошибка");
        statusController.setTextConnectButton("Подключить");
        barController.setTextInfoLbl(message);
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }

    //-----------------------------------------------------------------------------------------------------------------

    void error(String message) {
        statusController.stopJob();
        //captureController.stopJob();
        //settingsController.stopJob();
        setGUIError(message);
    }
}
