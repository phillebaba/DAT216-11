package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.*;

import java.io.IOException;

public class CartViewCell extends ListCell<ShoppingItem> {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label productNameLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button incrementButton;
    @FXML
    private Button decimateButton;
    @FXML
    private TextField amountField;

    private FXMLLoader mLLoader;

    private ShoppingItem cartItem;

    @Override
    protected void updateItem(ShoppingItem cartItem, boolean empty) {
        super.updateItem(cartItem, empty);

        if (empty || cartItem == null) {
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cartCell.fxml"));
            this.cartItem = cartItem;
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setListeners();
            setProductName(cartItem.getProduct().getName());
            setAmountField(cartItem.getAmount() + "");
            setGraphic(this.rootPane);
        }
    }

    private void setListeners() {
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cancelProduct();
            }
        });
        incrementButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                incrementProductAmount();
            }
        });
        decimateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                decimateProductAmount();
            }
        });
        amountField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               setProductAmount();
            }
        });
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private void setProductName(String string) {
        productNameLabel.setText(string);
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, true); // todo need to check if increase
    }

    private void setAmountField(String string) {
        amountField.setText(string);
    }

    private void incrementProductAmount() {
        cartItem.setAmount(cartItem.getAmount() + 1);
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, true);
    }
    private void setProductAmount() {
        if (isNumeric(amountField.getText())) {
            cartItem.setAmount(Integer.parseInt(amountField.getText()));
        }
    }

    private void decimateProductAmount() {
        cartItem.setAmount(cartItem.getAmount() - 1);
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, false);
    }

    private void cancelProduct() {
        IMatDataHandler.getInstance().getShoppingCart().removeItem(cartItem);
    }
}