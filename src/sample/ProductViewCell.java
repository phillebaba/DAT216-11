package sample;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductViewCell extends AnchorPane {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField amountField;

    @FXML
    private Button subtractButton;

    @FXML
    private Button incrementButton;

    @FXML
    private Button addButton;

    @FXML
    private Label nameLable;

    @FXML
    private Label priceLabel;

    @FXML
    void initialize() {
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'Product.fxml'.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file 'Product.fxml'.";
        assert subtractButton != null : "fx:id=\"subtractButton\" was not injected: check your FXML file 'Product.fxml'.";
        assert incrementButton != null : "fx:id=\"incrementButton\" was not injected: check your FXML file 'Product.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'Product.fxml'.";
        assert nameLable != null : "fx:id=\"nameLable\" was not injected: check your FXML file 'Product.fxml'.";
        assert priceLabel != null : "fx:id=\"priceLabel\" was not injected: check your FXML file 'Product.fxml'.";

    }

    public ProductViewCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateItem(Product product) {
        Image image = new Image("file:" + System.getProperty("user.home") + "/.dat215/imat/images/" + product.getImageName());
        imageView.setImage(image);
        nameLable.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()) + " KR");
        amountField.setText("1.0");

        subtractButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                amountField.setText(String.valueOf(Double.valueOf(amountField.getText()) - 1));
            }
        });

        incrementButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                amountField.setText(String.valueOf(Double.valueOf(amountField.getText()) + 1));
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Double amount = Double.valueOf(amountField.getText());

                List<ShoppingItem> currentItems = IMatDataHandler.getInstance().getShoppingCart().getItems();
                List<ShoppingItem> duplicates = currentItems.stream().filter(p -> p.getProduct().equals(product)).collect(Collectors.toList());;

                // The product already exists
                if (duplicates.size() > 0) {
                    ShoppingItem duplicate = duplicates.get(0);
                    duplicate.setAmount(duplicate.getAmount() + amount);
                    IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(duplicate, false);
                } else {
                    IMatDataHandler.getInstance().getShoppingCart().addProduct(product, amount);
                    amountField.setText("1.0");
                }
            }
        });
    }
}
