package sample.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.Support;
import se.chalmers.ait.dat215.project.*;

/**
 * Created by philiplaine on 2017-03-06.
 */
public class ConfirmationView extends VBox {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ListView<ShoppingItem> receiptList;
    @FXML
    private Label totalLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Label cardLabel;
    @FXML
    public Button confirmButton;
    @FXML
    public Button backButton;

    @FXML
    void initialize() {
        receiptList.setCellFactory(cartListView -> new ReceiptCell());
    }

    public ConfirmationView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/ConfirmationView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void configureView(ShoppingCart shoppingCart, Customer customer, CreditCard creditCard) {
        // Add all items to shopping cart list
        ObservableList<ShoppingItem> observableShoppingItems = FXCollections.observableArrayList();
        observableShoppingItems.addAll(shoppingCart.getItems());
        receiptList.setItems(observableShoppingItems);
        receiptList.refresh();

        totalLabel.setText("Totalbelopp: " + String.format("%.2f", shoppingCart.getTotal()) + " SEK");

        nameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        adressLabel.setText(customer.getAddress());
        cardLabel.setText(creditCard.getCardType() + " " + creditCard.getCardNumber());
    }

}
