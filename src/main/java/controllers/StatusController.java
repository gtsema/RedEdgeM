package controllers;

import dbService.DBException;
import dbService.DBService;
import dbService.entity.NetworkStatus;
import dbService.entity.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.IpAddressValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatusController implements Initializable {
    //-----------------------------------------------------------------------------------------------------------------

    private final AtomicBoolean running = new AtomicBoolean(false);

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private Button connectBtn;

    @FXML
    private TextField IPField;

    @FXML
    private Label cameraLbl;

    @FXML
    private Label DLSLbl;

    @FXML
    private Label busVoltsLbl;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label sdFreeLbl;

    @FXML
    private Label sdTotalLbl;

    @FXML
    private Label sdStatusLbl;

    @FXML
    ImageView imageView;

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private void connectBtnPressed(ActionEvent event) {
        if (!running.get()) {
            connect();
        } else {
            disconnect();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            imageView.setImage(new Image(new FileInputStream("./src/main/resources/img/rededgem.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void setTextConnectButton(String text) {
        connectBtn.setText(text);
    }

    public void setIPFieldDisable(boolean state) {
        IPField.setDisable(state);
    }

    public void setGUIDefaultState() {
        cameraLbl.setText("Нет связи");
        DLSLbl.setText("Нет связи");
        busVoltsLbl.setText("-");

        sdFreeLbl.setText("-");
        sdTotalLbl.setText("-");
        sdStatusLbl.setText("-");

        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressBar.setStyle("-fx-accent: rgb(0, 142, 190)");
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void connect() {
        ControllerMediator.getInstance().setGUIWaiting();
        startJob();
    }

    public void disconnect() {
        stopJob();
        ControllerMediator.getInstance().setGUIDisconnect();
    }

    public String getIP() {
        String IP = IPField.getText();
        return new IpAddressValidator().isValid(IP) ? IP : null;
    }

    //-----------------------------------------------------------------------------------------------------------------

    private void startJob() {

        String IP = getIP();
        if(IP == null) { ControllerMediator.getInstance().setGUIError("Введите валидный IP-адрес"); return; }

        Thread th = new Thread("ЖОПА") {
            public void run() {
                running.set(true);
                DBService dbService = new DBService(IP);
                while(running.get()) {
                    try {
                        Status status = dbService.getStatus().getOb();
                        NetworkStatus networkStatus = dbService.getNetworkStatus().getOb();
                        int statusCode = dbService.getStatus().getCode();
                        int networkCode = dbService.getNetworkStatus().getCode();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (statusCode == 200 && networkCode == 200) {
                                    ControllerMediator.getInstance().setGUIConnect();

                                    cameraLbl.setText(networkStatus.getCameraStatus() ? "Подключено" : "Нет связи");
                                    DLSLbl.setText(networkStatus.getDLSStatus() ? "Подключено" : "Нет связи");
                                    busVoltsLbl.setText(status.getBus_volts().substring(0, 4) + " В.");

                                    sdFreeLbl.setText(status.getSd_gb_free().substring(0, 4));
                                    sdTotalLbl.setText(status.getSd_gb_total().substring(0, 4));
                                    sdStatusLbl.setText(status.getSd_status().replace("\"", ""));

                                    Double sd_total = Double.parseDouble(status.getSd_gb_total());
                                    Double sd_free = Double.parseDouble(status.getSd_gb_free());
                                    progressBar.setProgress((sd_total - sd_free) / sd_total);

                                    if (status.getSd_warn().equals("true")) progressBar.setStyle("-fx-accent: red");
                                    else progressBar.setStyle("-fx-accent: rgb(0, 142, 190)");
                                } else {
                                    ControllerMediator.getInstance().setGUIWaiting();
                                }
                            }
                        });

                        Thread.sleep(1000);

                    } catch (InterruptedException | DBException e) {
                        if(!running.get()) break;
                        Platform.runLater(() -> {
                            stopJob();
                            ControllerMediator.getInstance().setGUIError(e.getMessage());
                        });
                    }
                }
            }
        };
        th.setDaemon(true);
        th.start();

    }

    private void stopJob() {
        running.set(false);
    }
}
