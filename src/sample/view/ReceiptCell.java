package sample.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.*;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;

public class ReceiptCell extends ListCell<ShoppingItem> {

    @FXML
    private GridPane rootPane;
    @FXML
    private Label productLabel;
    @FXML
    private Label priceLabel;

    @FXML
    void initialize() {

    }

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(ShoppingItem shoppingItem, boolean empty) {
        super.updateItem(shoppingItem, empty);

        if (empty || shoppingItem == null) {
            setGraphic(null);
        } else {
            this.fxmlLoader = new FXMLLoader(getClass().getResource("../resources/ReceiptCell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            productLabel.setText(shoppingItem.getProduct().getName());
            priceLabel.setText(String.valueOf(shoppingItem.getTotal()) + " SEK");
            setGraphic(this.rootPane);
        }
    }

}