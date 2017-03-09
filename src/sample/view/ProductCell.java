package sample.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.layout.HBox;
import sample.Support;
import javafx.scene.layout.VBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductCell extends VBox {

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
    private Button favoriteButton;

    @FXML
    private ImageView favoriteImageView;

    @FXML
    private Label nameLable;

    @FXML
    private Label priceLabel;

    @FXML
    void initialize() {
        /*assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert subtractButton != null : "fx:id=\"subtractButton\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert incrementButton != null : "fx:id=\"incrementButton\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert nameLable != null : "fx:id=\"nameLable\" was not injected: check your FXML file 'ProductCell.fxml'.";
        assert priceLabel != null : "fx:id=\"priceLabel\" was not injected: check your FXML file 'ProductCell.fxml'.";*/
    }

    public ProductCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/ProductCell.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateItem(Product product) {
        imageView.setImage(IMatDataHandler.getInstance().getFXImage(product));
        nameLable.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()) + product.getUnit());
        setFavoriteIcon(IMatDataHandler.getInstance().isFavorite(product));
        if (Support.isDivisible(product)){
            amountField.setText("1.0");
        } else{
            amountField.setText("1");
        }

        subtractButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Support.isDivisible(product)){
                    if(Double.valueOf(amountField.getText())>1){
                        amountField.setText(String.valueOf(Support.round(Double.valueOf(amountField.getText())-1)));
                    }else if(Double.valueOf(amountField.getText())>0.1){
                        amountField.setText(String.valueOf(Support.round(Double.valueOf(amountField.getText())-0.1)));
                    }
                }else{
                    if(Integer.valueOf(amountField.getText()) > 1){
                        amountField.setText(String.valueOf(Integer.valueOf(amountField.getText())-1));
                    }

                }


            }
        });

        incrementButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Support.isDivisible(product)){
                    if(Double.valueOf(amountField.getText())<1){
                        amountField.setText(String.valueOf(Support.round(Double.valueOf(amountField.getText()) + 0.1)));
                    }else{
                        amountField.setText(String.valueOf(Double.valueOf(amountField.getText()) + 1));
                    }
                }else{
                    amountField.setText(String.valueOf(Integer.valueOf(amountField.getText())+1));
                }


            }
        });

        favoriteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!IMatDataHandler.getInstance().isFavorite(product)) {
                    IMatDataHandler.getInstance().addFavorite(product);
                } else {
                    IMatDataHandler.getInstance().removeFavorite(product);
                }

                setFavoriteIcon(IMatDataHandler.getInstance().isFavorite(product));
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Double amount;
                if(Support.isDivisible(product)){
                    amount = Support.round(Double.valueOf(amountField.getText()));
                }else{
                    amount = Support.round3(Double.valueOf(amountField.getText()));
                }

                List<ShoppingItem> currentItems = IMatDataHandler.getInstance().getShoppingCart().getItems();
                List<ShoppingItem> duplicates = currentItems.stream().filter(p -> p.getProduct().equals(product)).collect(Collectors.toList());

                // The product already exists
                if (duplicates.size() > 0) {
                    ShoppingItem duplicate = duplicates.get(0);
                    duplicate.setAmount(duplicate.getAmount() + amount);
                    IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(duplicate, false);
                    if(Support.isDivisible(product)){
                        amountField.setText("1.0");
                    }else{
                        amountField.setText("1");
                    }

                } else {
                    IMatDataHandler.getInstance().getShoppingCart().addProduct(product, amount);
                    if(Support.isDivisible(product)){
                        amountField.setText("1.0");
                    }else{
                        amountField.setText("1");
                    }
                }
            }
        });
    }

    private void setFavoriteIcon(Boolean favorited) {
        String fileName = favorited ? "../resources/favorite_fill.png" : "../resources/favorite_border.png";
        favoriteImageView.setImage(new Image(getClass().getResourceAsStream(fileName)));
    }

}
