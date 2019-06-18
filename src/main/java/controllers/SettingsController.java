package controllers;

import dbService.DBException;
import dbService.DBService;
import dbService.entity.Config;
import dbService.entity.Exposure;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.logging.Logger;

import static controllers.MainController.mediator;

public class SettingsController {

    private static Logger log = Logger.getLogger(SettingsController.class.getName());

    @FXML
    private CheckBox expositionCheckBox;

    @FXML
    private GridPane expositionTbl;

    @FXML
    private TextField expBlue;

    @FXML
    private TextField expGreen;

    @FXML
    private TextField expRed;

    @FXML
    private TextField expNIR;

    @FXML
    private TextField expRedEdge;

    @FXML
    private ChoiceBox expGainBlue;

    @FXML
    private ChoiceBox expGainGreen;

    @FXML
    private ChoiceBox expGainRed;

    @FXML
    private ChoiceBox expGainNIR;

    @FXML
    private ChoiceBox expGainRedEdge;

    @FXML
    private TextField gainTime;

    @FXML
    private CheckBox jpg;

    @FXML
    private CheckBox tiff;

    @FXML
    private TextField ip;

    @FXML
    private void expose(ActionEvent event) {
        if(expositionCheckBox.isSelected()) expositionTbl.setDisable(false);
        else expositionTbl.setDisable(true);
    }

    @FXML
    private void jpgTiff(ActionEvent event) {
        if(!jpg.isSelected() && !tiff.isSelected()) { jpg.setSelected(true); }
    }

    public void fillFields() {
        String IP = mediator.getController(StatusController.class).getIP();
        if(IP == null) {
            mediator.error("Ошибочный IP-адрес. Но такого не может быть...");
            log.warning("IP-адрес == null, хоть всё подключилось и работало.");
            return;
        }

        Thread thread = new Thread("SettingsFill") {
            @Override
            public void run() {
                DBService dbService = new DBService(IP);

                try {
                    Exposure exposure = dbService.getExposure();
                    Config config = dbService.getConfig();

                    Platform.runLater(() -> {
                        if(exposure.isEnable_man_exposure()) expositionCheckBox.setSelected(true);
                        else expositionCheckBox.setSelected(false);

                        expBlue.setText(exposure.getExposure1());
                        expGreen.setText(exposure.getExposure2());
                        expRed.setText(exposure.getExposure3());
                        expNIR.setText(exposure.getExposure4());
                        expRedEdge.setText(exposure.getExposure5());

                        SingleSelectionModel<String> selectionModeBlue = expGainBlue.getSelectionModel();
                        selectionModeBlue.select(exposure.getGain1());

                        SingleSelectionModel<String> selectionModeGreen = expGainGreen.getSelectionModel();
                        selectionModeGreen.select(exposure.getGain2());

                        SingleSelectionModel<String> selectionModeRed = expGainRed.getSelectionModel();
                        selectionModeRed.select(exposure.getGain3());

                        SingleSelectionModel<String> selectionModeNIR = expGainNIR.getSelectionModel();
                        selectionModeNIR.select(exposure.getGain4());

                        SingleSelectionModel<String> selectionModeRedEdge = expGainRedEdge.getSelectionModel();
                        selectionModeRedEdge.select(exposure.getGain5());

                        gainTime.setText(config.getGain_exposure_crossover());

                        if(config.getEnabled_bands_jpeg().equals("0")) jpg.setSelected(false);
                        else jpg.setSelected(true);

                        if(config.getEnabled_bands_raw().equals("0")) tiff.setSelected(false);
                        else tiff.setSelected(false);

                        ip.setText(IP);
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
}
