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
        repo.put(SettingsController.class, settingsController);

        barController.startStateCtrl();

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(FIRST);

        selectionModel.selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            switch (oldTab.getId()) {
                case "statusTab":
                    if(getController(StatusController.class).getState() == RUNNING)
                        getController(StatusController.class).pause();
                    break;
                case "captureTab":
                    break;
                case "settingsTab":
                    break;
            }

            switch (newTab.getId()) {
                case "statusTab":
                    if(getController(StatusController.class).getState() == PAUSED)
                        getController(StatusController.class).resume();
                    break;
                case "captureTab":
                    //init
                    break;
                case "settingsTab":
                    break;
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void setGUIConnect() {
        barController.setGreenLamp();
        barController.setTextConInfoLbl("Подключено");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    public void setGUIDisconnect() {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Отключено");
        statusController.setTextConnectButton("Подключить");
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }

    public void setGUIWaiting() {
        barController.setYellowLamp();
        barController.setTextConInfoLbl("Соединение");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    public void setGUIError(String message) {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Ошибка");
        statusController.setTextConnectButton("Подключить");
        barController.setTextInfoLbl(message);
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }
}
