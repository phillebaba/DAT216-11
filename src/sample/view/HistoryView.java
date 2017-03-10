package sample.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.controller.Controller;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;
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
    private List<Order> orderHistory;

    @FXML
    void initialize() {
        assert shoppingHistoryList != null : "fx:id=\"shoppingHistoryList\" was not injected: check your FXML file 'HistoryView.fxml'.";
        shoppingHistoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int i = 0;
                for(String s: orders){
                    if(s.equals(newValue)){
                        c.updateProductsSI(orderHistory.get(i).getItems());
                    }
                    i++;
                }
                System.out.println("Selected item: " + newValue);
            }
        });
    }

    ObservableList<String> orders;
    public void configureView(List<Order> orderList) {
        orders = FXCollections.observableArrayList();
    this.orderHistory = orderList; // temp
        // Run backwards to get the newest first
        List<Order> reversed = orderList.subList(0, orderList.size());
        Collections.reverse(reversed);

        // List previous orders as date + price
        for (Order order : reversed) {
            double orderTotal = 0;
            // Get total sum of all products
            for (ShoppingItem shoppingItem : order.getItems()) {
                orderTotal = orderTotal + shoppingItem.getTotal();
            }
            orders.add(order.getDate() + "  " + orderTotal + " SEK");
        }

        shoppingHistoryList.setItems(orders);
    }
    private Controller c;

    public void setController(Controller c){
        this.c = c;
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
