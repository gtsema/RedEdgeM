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

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static controllers.MainController.mediator;

public class StatusController implements Initializable {

    private static Logger log = Logger.getLogger(StatusController.class.getName());

    //-----------------------------------------------------------------------------------------------------------------
    final static int STOPPED = 0;
    final static int RUNNING = 1;
    final static int PAUSED = 2;

    private final AtomicInteger state = new AtomicInteger(STOPPED);

    private boolean IPIsSaved = false;
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

        Properties properties = new Properties();
        File propertiesFile = new File("ip.properties");
        String defaultIP = "192.168.1.83";
        try {
            properties.load(new FileReader(propertiesFile));
            String IP = properties.getProperty("IP");
            IPField.setText(IpAddressValidator.isValid(IP) ? IP : defaultIP);
        } catch (IOException e) {
            log.warning(e.getMessage());
            IPField.setText(defaultIP);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    void setTextConnectButton(String text) {
        connectBtn.setText(text);
    }

    void setIPFieldDisable(boolean state) {
        IPField.setDisable(state);
    }

    void setGUIDefaultState() {
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
    void pause() {
        state.set(PAUSED);
    }

    void resume() {
        state.set(RUNNING);
        startJob();
    }

    int getState() {
        return state.intValue();
    }

    String getIP() throws NumberFormatException {
        String IP = IPField.getText();
        if(IpAddressValidator.isValid(IP)) return IP;
        else throw new NumberFormatException("Ошибочный IP-адрес");
    }

    private void connect() {
        IPIsSaved = false;
        mediator.setGUIWaiting();
        startJob();
    }

    private void disconnect() {
        stopJob();
        mediator.setGUIDisconnect();
    }

    private void saveIP() {
        Properties properties = new Properties();
        File propertiesFile = new File("ip.properties");
        try {
            String IP = getIP();
            properties.setProperty("IP", IP);
            properties.store(new FileWriter(propertiesFile), "last working IP");
            IPIsSaved = true;
        } catch (NumberFormatException | IOException e) {
            log.warning(e.getMessage());
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    private void startJob() {
        String IP;
        try {
            IP = getIP();
        } catch (NumberFormatException e) {
            mediator.error("Введите правильный IP-адрес");
            return;
        }

        Thread th = new Thread("ЖОПА") {
            public void run() {
                DBService dbService = new DBService(IP);

                state.set(RUNNING);

                while(state.get() == RUNNING) {
                    try {
                        Status status = dbService.getStatus();
                        NetworkStatus networkStatus = dbService.getNetworkStatus();

                        if(state.get() == STOPPED) break;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(!IPIsSaved) saveIP();
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
                        });

                        Thread.sleep(1000);

                    } catch (InterruptedException | DBException e) {
                        if(state.get() == STOPPED) break;
                        Platform.runLater(() -> {
                            mediator.error(e.getMessage());
                        });
                    }
                }
            }
        };
        th.setDaemon(true);
        th.start();

    }

    void stopJob() {
        state.set(STOPPED);
    }
}
