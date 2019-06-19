package controllers;

import dbService.DBException;
import dbService.DBService;
import dbService.entity.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.logging.Logger;

import static controllers.MainController.mediator;

public class CaptureController {

    private static Logger log = Logger.getLogger(CaptureController.class.getName());

    private File manDir;
    private File autoDir;
    final DirectoryChooser directoryChooser = new DirectoryChooser();

    private String IP;

    @FXML
    private Node root;

    @FXML
    private CheckBox manBlue;

    @FXML
    private CheckBox manGreen;

    @FXML
    private CheckBox manRed;

    @FXML
    private CheckBox manNIR;

    @FXML
    private CheckBox manRedEdge;

    @FXML
    private CheckBox antisaturation;

    @FXML
    private CheckBox manSaveToSD;

    @FXML
    private CheckBox manLoadChBox;

    @FXML
    private TextField manPathTField;

    @FXML
    private Button manOpenBtn;

    @FXML
    private Button manStartBtn;

    @FXML
    private CheckBox autoBlue;

    @FXML
    private CheckBox autoGreen;

    @FXML
    private CheckBox autoRed;

    @FXML
    private CheckBox autoNIR;

    @FXML
    private CheckBox autoRedEdge;

    @FXML
    private TextField alt;

    @FXML
    private TextField vOverlap;

    @FXML
    private TextField hOverlap;

    @FXML
    private TextField timer;

    @FXML
    private ChoiceBox triggerType;

    @FXML
    private ToggleGroup mode;

    @FXML
    private TextField triggerThreshold;

    @FXML
    private CheckBox autoLoadChBox;

    @FXML
    private TextField autoPathTField;

    @FXML
    private Button autoOpenBtn;

    @FXML
    private RadioButton overlapRBtn;

    @FXML
    private RadioButton timerRBtn;

    @FXML
    private RadioButton triggerRBtn;

    @FXML
    private GridPane overlapGPane;

    @FXML
    private GridPane timerGPane;

    @FXML
    private GridPane triggerGPane;

    @FXML
    private Button autoStartBtn;

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private void checkManLoad(ActionEvent event) {
        if(manLoadChBox.isSelected()) {
            manPathTField.setDisable(false);
            manOpenBtn.setDisable(false);
        } else {
            manPathTField.setDisable(true);
            manOpenBtn.setDisable(true);
        }
    }

    @FXML
    private void manOpen(ActionEvent event) {
        configuringDirectoryChooser(directoryChooser);
        manDir = directoryChooser.showDialog(root.getScene().getWindow());
        manPathTField.setText(manDir.getPath());
    }

    @FXML
    private void CheckAutoLoad(ActionEvent event) {
        if(autoLoadChBox.isSelected()) {
            autoPathTField.setDisable(false);
            autoOpenBtn.setDisable(false);
        } else {
            autoPathTField.setDisable(true);
            autoOpenBtn.setDisable(true);
        }
    }

    @FXML
    private void autoOpen(ActionEvent event) {
        configuringDirectoryChooser(directoryChooser);
        autoDir = directoryChooser.showDialog(root.getScene().getWindow());
        autoPathTField.setText(autoDir.getPath());
    }

    @FXML
    private void selectMode(ActionEvent event) {
        if(overlapRBtn.isSelected()) {
            setOverlapMode();
        } else if(timerRBtn.isSelected()) {
            setTimerMode();
        } else setTriggerMode();
    }

    @FXML
    private void manStart() {}

    @FXML
    private void autoStart() {}

    //-----------------------------------------------------------------------------------------------------------------

    void lockStartBtn() {
        manStartBtn.setDisable(true);
        autoStartBtn.setDisable(true);
    }

    void unlockStartBtn() {
        manStartBtn.setDisable(false);
        autoStartBtn.setDisable(false);
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите папку");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private void setOverlapMode() {
        overlapGPane.setDisable(false);
        timerGPane.setDisable(true);
        triggerGPane.setDisable(true);
    }

    private void setTimerMode() {
        overlapGPane.setDisable(true);
        timerGPane.setDisable(false);
        triggerGPane.setDisable(true);
    }

    private void setTriggerMode() {
        overlapGPane.setDisable(true);
        timerGPane.setDisable(true);
        triggerGPane.setDisable(false);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void initTab() {
        try {
            IP = mediator.getController(StatusController.class).getIP();
        } catch (NumberFormatException e) {
            mediator.error("Ошибочный IP-адрес. Но такого не может быть...");
            log.warning("IP-адрес == null, хоть всё подключилось и работало.");
            return;
        }

        Thread thread = new Thread("CaptureFill") {
            @Override
            public void run() {
                DBService dbService = new DBService(IP);

                try {
                    Config config = dbService.getConfig();

                    Platform.runLater(() -> {
                        String channels;
                        if(Integer.parseInt(config.getEnabled_bands_jpeg()) >=
                           Integer.parseInt(config.getEnabled_bands_raw())) {
                            channels = Integer.toBinaryString(Integer.parseInt(config.getEnabled_bands_jpeg()));
                        } else channels = Integer.toBinaryString(Integer.parseInt(config.getEnabled_bands_raw()));

                        if(channels.charAt(0) == '0') { manBlue.setSelected(false); autoBlue.setSelected(false); }
                        else if(channels.charAt(0) == '1') { manBlue.setSelected(true); autoBlue.setSelected(true); }

                        if(channels.charAt(1) == '0') { manGreen.setSelected(false); autoGreen.setSelected(false); }
                        else if(channels.charAt(1) == '1') { manGreen.setSelected(true); autoGreen.setSelected(true); }

                        if(channels.charAt(2) == '0') { manRed.setSelected(false); autoRed.setSelected(false); }
                        else if(channels.charAt(2) == '1') { manRed.setSelected(true); autoRed.setSelected(true); }

                        if(channels.charAt(3) == '0') { manNIR.setSelected(false); autoNIR.setSelected(false); }
                        else if(channels.charAt(3) == '1') { manNIR.setSelected(true); autoNIR.setSelected(true); }

                        if(channels.charAt(4) == '0') { manRedEdge.setSelected(false); autoRedEdge.setSelected(false); }
                        else if(channels.charAt(4) == '1') { manRedEdge.setSelected(true); autoRedEdge.setSelected(true); }

                        switch (config.getAuto_cap_mode()) {
                            case "overlap":
                                mode.selectToggle(overlapRBtn);
                                setOverlapMode();
                                break;
                            case "timer":
                                mode.selectToggle(timerRBtn);
                                setTimerMode();
                                break;
                            case "ext":
                                mode.selectToggle(triggerRBtn);
                                setTriggerMode();
                                break;
                        }

                        alt.setText(config.getOperating_alt());
                        vOverlap.setText(config.getOverlap_along_track());
                        hOverlap.setText(config.getOverlap_cross_track());
                        timer.setText(config.getTimer_period());

                        SingleSelectionModel<String> singleSelectionModel = triggerType.getSelectionModel();
                        switch (config.getExt_trigger_mode()) {
                            case "rising":
                                singleSelectionModel.select("восходящий");
                                break;
                            case "falling":
                                singleSelectionModel.select("нисходящий");
                                break;
                            case "longpwm":
                                singleSelectionModel.select("длинный шим");
                                break;
                            case "shortpwm":
                                singleSelectionModel.select("короткий шим");
                                break;
                        }

                        triggerThreshold.setText(config.getPwm_trigger_threshold());
                    });
                } catch (DBException e) {
                    Platform.runLater(() -> {
                        mediator.error(e.getMessage());
                    });
                }
            }
        };
        thread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
}
