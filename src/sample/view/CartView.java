package sample.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * Created by philiplaine on 2017-03-06.
 */
public class CartView extends VBox {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public ListView<ShoppingItem> cartList;

    @FXML
    public Button buyButton;

    @FXML
    void initialize() {
        assert cartList != null : "fx:id=\"cartList\" was not injected: check your FXML file 'CartView.fxml'.";
        assert buyButton != null : "fx:id=\"buyButton\" was not injected: check your FXML file 'CartView.fxml'.";

        // Set custom cell factory
        cartList.setCellFactory(cartListView -> new CartCell());
    }

    public CartView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/CartView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
