package sample.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * Created by philiplaine on 2017-03-06.
 */
public class HistoryView extends VBox {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> shoppingHistoryList;

    @FXML
    void initialize() {
        assert shoppingHistoryList != null : "fx:id=\"shoppingHistoryList\" was not injected: check your FXML file 'HistoryView.fxml'.";
    }

    public void configureView(List<Order> orderList) {
        ObservableList<String> orders = FXCollections.observableArrayList();

        // List previous orders as date + price
        for (Order order : orderList) {
            double orderTotal = 0;
            // Get total sum of all products
            for (ShoppingItem shoppingItem : order.getItems()) {
                orderTotal = orderTotal + shoppingItem.getTotal();
            }

            orders.add(order.getDate() + "  " + orderTotal + " SEK");
        }

        shoppingHistoryList.setItems(orders);
    }

    public HistoryView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/HistoryView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
