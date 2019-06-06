package controllers;

import dbService.DBException;
import dbService.DBService;
import dbService.entity.NetworkStatus;
import dbService.entity.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class CaptureController {
    private File manDir;
    private File autoDir;
    final DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    private Node root;

    @FXML
    private CheckBox manLoadChBox;

    @FXML
    private TextField manPathTField;

    @FXML
    private Button manOpenBtn;

    @FXML
    private CheckBox autoLoadChBox;

    @FXML
    private TextField autoPathTField;

    @FXML
    private Button autoOpenBtn;

    @FXML
    RadioButton overlapRBtn;

    @FXML
    RadioButton timerRBtn;

    @FXML
    RadioButton triggerRBtn;

    @FXML
    private GridPane overlapGPane;

    @FXML
    private GridPane timerGPane;

    @FXML
    private GridPane triggerGPane;

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

    //----------------------------------------------------------

//    public void CaptureInit() {
//
//        Thread captureInitThread = new Thread("ПИЗДЕЦ") {
//            public void run() {
//                try {
//                    //DBService dbService = new DBService(IP);
//
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            ///ПЫЩЬ///
//                        }
//                    });
//
//                } catch (DBException e) {
//                    Platform.runLater(() -> {
//                        /////////////
//                    });
//                }
//            }
//        };
//        captureInitThread.setDaemon(true);
//        captureInitThread.start();
//    }
}
