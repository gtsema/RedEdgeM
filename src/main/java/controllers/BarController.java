package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Circle;
import java.util.concurrent.atomic.AtomicBoolean;

import static controllers.MainController.mediator;
import static controllers.StatusController.*;

public class BarController {

    private final AtomicBoolean wdt = new AtomicBoolean(false);

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private Label conInfoLbl;

    @FXML
    private Label infoLbl;

    @FXML
    private Circle lamp;

    //-----------------------------------------------------------------------------------------------------------------

    void setGreenLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.4);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0.5);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    void setRedLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    void setYellowLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.4);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0.36);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    void setTextInfoLbl(String text) {
        infoLbl.setText(text);
    }

    void setTextConInfoLbl(String text) {
        conInfoLbl.setText(text);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void startStateCtrl() {
        Thread stateCtrl = new Thread("STATE") {
            @Override
            public void run() {
                try {
                    while (true) {

                        if(mediator.getController(StatusController.class).getState() == RUNNING) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if(wdt.get()) {
                                        mediator.setGUIWaiting();
                                    } else {
                                        wdt.set(true);
                                    }
                                }
                            });
                        } else if(mediator.getController(StatusController.class).getState() == PAUSED) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if(wdt.get()) {
                                        mediator.setGUIConnect();
                                    } else {
                                        wdt.set(true);
                                    }
                                }
                            });
                        }

                        Thread.sleep(1000);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        stateCtrl.setDaemon(true);
        stateCtrl.start();
    }

    public void resetWDT() {
        wdt.set(false);
    }
}
