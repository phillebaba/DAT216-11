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
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

/**
 * Created by philiplaine on 2017-03-06.
 */
public class ConfirmationView extends AnchorPane {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label confirmationCartProductLabel;

    @FXML
    private Label confirmationCartPriceLabel;

    @FXML
    public Button confirmButton;

    @FXML
    public Button backButton;

    @FXML
    private ListView<String> confirmationPaymentInfoList;

    @FXML
    private Label confirmationCartTotalPriceLabel;

    @FXML
    void initialize() {
        assert confirmationCartProductLabel != null : "fx:id=\"confirmationCartProductLabel\" was not injected: check your FXML file 'ConfirmationView.fxml'.";
        assert confirmationCartPriceLabel != null : "fx:id=\"confirmationCartPriceLabel\" was not injected: check your FXML file 'ConfirmationView.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'ConfirmationView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'ConfirmationView.fxml'.";
        assert confirmationPaymentInfoList != null : "fx:id=\"confirmationPaymentInfoList\" was not injected: check your FXML file 'ConfirmationView.fxml'.";
        assert confirmationCartTotalPriceLabel != null : "fx:id=\"confirmationCartTotalPriceLabel\" was not injected: check your FXML file 'ConfirmationView.fxml'.";

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

    public void configureView(List<ShoppingItem> shoppingItemList, Customer customer, CreditCard creditCard) {
        // Clear the current text
        confirmationCartPriceLabel.setText("");
        confirmationCartProductLabel.setText("");

        // Set the new values
        for (ShoppingItem shoppingItem : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            confirmationCartPriceLabel.setText(confirmationCartPriceLabel.getText() + shoppingItem.getTotal() + " SEK\n");
            confirmationCartProductLabel.setText(confirmationCartProductLabel.getText() + shoppingItem.getProduct().getName() + "\n");
        }

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add(IMatDataHandler.getInstance().getCustomer().getFirstName() + " " + IMatDataHandler.getInstance().getCustomer().getLastName());
        observableList.add(IMatDataHandler.getInstance().getCreditCard().getCardType() + " " + IMatDataHandler.getInstance().getCreditCard().getCardNumber());
        observableList.add(IMatDataHandler.getInstance().getCustomer().getAddress());
        confirmationPaymentInfoList.setItems(observableList);
    }

}
