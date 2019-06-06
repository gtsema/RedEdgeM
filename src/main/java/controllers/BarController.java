package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Circle;

public class BarController {

    private boolean isDoing = false;

    private Thread blink;

    @FXML
    private Label conInfoLbl;

    @FXML
    private Label infoLbl;

    @FXML
    private Circle lamp;

    public void setGreenLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.4);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0.5);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    public void setRedLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    public void setYellowLamp() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.4);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0.36);
        colorAdjust.setSaturation(0);
        lamp.setEffect(colorAdjust);
    }

    public void setTextInfoLbl(String text) {
        infoLbl.setText(text);
    }

    public void setTextConInfoLbl(String text) {
        conInfoLbl.setText(text);
    }
}
