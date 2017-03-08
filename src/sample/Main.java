package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                IMatDataHandler.getInstance().shutDown();
            }
        });

        Parent root = FXMLLoader.load(getClass().getResource("resources/sample.fxml"));
        stage.setTitle("iMat");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
