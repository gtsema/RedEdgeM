package testfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/testFxml/test.fxml"));
        stage.setTitle("RedEdge-M");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 640, 360));
        stage.show();
    }
}
