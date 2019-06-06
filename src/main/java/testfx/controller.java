package testfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.shape.Circle;

public class controller {

    private boolean isBlink = false;

    @FXML
    Circle circle;

    @FXML
    Button btn;

    @FXML
    public void press(ActionEvent event) {
        isBlink = Boolean.logicalXor(isBlink, true);

        new Thread(() -> {
            ColorAdjust colorAdjust = new ColorAdjust();

            while (true) {
                try {
                    colorAdjust.setBrightness(0);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            circle.setEffect(colorAdjust);
                        }
                    });

                    Thread.sleep(500);

                    colorAdjust.setBrightness(0.6);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            circle.setEffect(colorAdjust);
                        }
                    });

                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }
}
