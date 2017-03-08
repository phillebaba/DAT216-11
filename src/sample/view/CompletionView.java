package sample.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Created by philiplaine on 2017-03-06.
 */
public class CompletionView extends VBox {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public Button doneButton;

    @FXML
    void initialize() {
        assert doneButton != null : "fx:id=\"doneButton\" was not injected: check your FXML file 'CompletionView.fxml'.";

    }

    public CompletionView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/CompletionView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
