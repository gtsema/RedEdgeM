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
import java.util.concurrent.atomic.AtomicInteger;

import static controllers.MainController.mediator;

public class StatusController implements Initializable {
    //-----------------------------------------------------------------------------------------------------------------
    final static int STOPPPED = 0;
    final static int RUNNING = 1;
    final static int PAUSED = 3;

    private final AtomicInteger state = new AtomicInteger(STOPPPED);

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
        if (state.get() == RUNNING) {
            disconnect();
        } else {
            connect();
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

    private void connect() {
        mediator.setGUIWaiting();
        startJob();
    }

    private void disconnect() {
        stopJob();
        mediator.setGUIDisconnect();
    }

    private void error(String message) {
        stopJob();
        mediator.setGUIError(message);
    }

    public void pause() {
        state.set(PAUSED);
    }

    public void resume() {
        state.set(RUNNING);
        startJob();
    }

    public String getIP() {
        String IP = IPField.getText();
        return new IpAddressValidator().isValid(IP) ? IP : null;
    }

    public int getState() {
        return state.intValue();
    }

    //-----------------------------------------------------------------------------------------------------------------

    private void startJob() {

        String IP = getIP();
        if(IP == null) { error("Введите валидный IP-адрес"); return; }

        Thread th = new Thread("ЖОПА") {
            public void run() {
                DBService dbService = new DBService(IP);

                state.set(RUNNING);

                while(state.get() == RUNNING) {
                    try {
                        Status status = dbService.getStatus().getOb();
                        NetworkStatus networkStatus = dbService.getNetworkStatus().getOb();
                        int statusCode = dbService.getStatus().getCode();
                        int networkCode = dbService.getNetworkStatus().getCode();

                        if(state.get() == STOPPPED) break;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (statusCode == 200 && networkCode == 200) {
                                    mediator.setGUIConnect();
                                    mediator.getController(BarController.class).resetWDT();

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
                                }
                            }
                        });

                        Thread.sleep(1000);

                    } catch (InterruptedException | DBException e) {
                        if(state.get() == STOPPPED) break;
                        Platform.runLater(() -> {
                            error(e.getMessage());
                        });
                    }
                }
            }
        };
        th.setDaemon(true);
        th.start();

    }

    private void stopJob() {
        state.set(STOPPPED);
    }
}
