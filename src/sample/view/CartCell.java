package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sample.Support;
import se.chalmers.ait.dat215.project.*;

import java.io.IOException;
import java.util.Locale;

public class CartCell extends ListCell<ShoppingItem> {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button incrementButton;
    @FXML
    private Button decimateButton;
    @FXML
    private TextField amountField;
    @FXML
    private HBox infoPane;
    @FXML
    private HBox cartViewHBox;


    private FXMLLoader mLLoader;

    private ShoppingItem cartItem;

    @Override
    protected void updateItem(ShoppingItem cartItem, boolean empty) {
        super.updateItem(cartItem, empty);

        if (empty || cartItem == null) {
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/CartCell.fxml"));
            this.cartItem = cartItem;
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setListeners();
            setProductName(cartItem.getProduct().getName());
            setAmountField(cartItem.getAmount());
            setProductPrice();
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

    }

    private void setAmountField(double d) {
        if (!Support.isDivisible(cartItem.getProduct())) {
            amountField.setText((int) d + "");
        } else {
            amountField.setText(String.valueOf(Support.round(d)));

        }

    }

    private void incrementProductAmount() {
        setProductAmount();
        if(Support.isDivisible(cartItem.getProduct()) && cartItem.getAmount()<1){
            cartItem.setAmount(Support.round(cartItem.getAmount() + 0.1));
        }else{
            cartItem.setAmount(cartItem.getAmount() + 1);
        }

        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, true);
    }

    private void setProductAmount() {
        if (isNumeric(amountField.getText())) {
            if(Support.isDivisible(cartItem.getProduct())){
                cartItem.setAmount(Double.valueOf(amountField.getText()));
            }else{
                cartItem.setAmount(Integer.parseInt(amountField.getText()));
            }

            IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, false); // todo need to check if increase
        }
    }

    private void setProductPrice() {
        priceLabel.setText(Support.round(cartItem.getTotal())+ " Kr");
    }

    private void decimateProductAmount() {
        setProductAmount();
        if(Support.isDivisible(cartItem.getProduct()) && cartItem.getAmount()<=1 && cartItem.getAmount()>0.1){
            cartItem.setAmount(Support.round(cartItem.getAmount() - 0.1));
            //cartItem.setAmount(Double.parseDouble(String.format(Locale.ENGLISH,"%1$,.1f", cartItem.getAmount() - 0.1)));
        }else if(cartItem.getAmount()>1){
            cartItem.setAmount(cartItem.getAmount() - 1);
        }

        // Force update of the cart cell
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(cartItem, false);
    }

    private void cancelProduct() {
        IMatDataHandler.getInstance().getShoppingCart().removeItem(cartItem);
    }
}