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
    private ListView<String> cartListView;

    @FXML
    private Label totalLabel;

    @FXML
    private ListView<String> confirmationPaymentInfoList;

    @FXML
    public Button confirmButton;

    @FXML
    public Button backButton;

    @FXML
    void initialize() {


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
        // Clear the current text
        /*confirmationCartPriceLabel.setText("");
        confirmationCartProductLabel.setText("");


        // Set the new values
        for (ShoppingItem shoppingItem : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            confirmationCartPriceLabel.setText(confirmationCartPriceLabel.getText() + shoppingItem.getTotal() + " SEK\n");
            confirmationCartProductLabel.setText(confirmationCartProductLabel.getText() + shoppingItem.getProduct().getName() + "\n");
        }*/


        totalLabel.setText("Totalbelopp: " + String.format("%.2f", shoppingCart.getTotal()) + " SEK");

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add(IMatDataHandler.getInstance().getCustomer().getFirstName() + " " + IMatDataHandler.getInstance().getCustomer().getLastName());
        observableList.add(IMatDataHandler.getInstance().getCreditCard().getCardType() + " " + IMatDataHandler.getInstance().getCreditCard().getCardNumber());
        observableList.add(IMatDataHandler.getInstance().getCustomer().getAddress());
        confirmationPaymentInfoList.setItems(observableList);
    }

}
