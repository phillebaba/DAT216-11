package sample;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import se.chalmers.ait.dat215.project.*;

import javax.swing.*;


public class Controller implements ShoppingCartListener {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button fruitButton;

    @FXML
    private Button vegetableButton;

    @FXML
    private Button meatButton;

    @FXML
    private Button breadButton;

    @FXML
    private Button pantryButton;

    @FXML
    private Button drinksButton;

    @FXML
    private Button sweetButton;

    @FXML
    private Button dairyButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TilePane productPane;

    @FXML
    private ListView<ShoppingItem> cartView;

    @FXML
    private AnchorPane cartPane;

    @FXML
    void categoryButtonPushed(ActionEvent event) {
        ArrayList<ProductCategory> fruitList = new ArrayList<ProductCategory>();
        fruitList.add(ProductCategory.BERRY);
        fruitList.add(ProductCategory.CITRUS_FRUIT);
        fruitList.add(ProductCategory.EXOTIC_FRUIT);
        fruitList.add(ProductCategory.MELONS);
        fruitList.add(ProductCategory.FRUIT);

        ArrayList<ProductCategory> drinkList = new ArrayList<ProductCategory>();
        drinkList.add(ProductCategory.HOT_DRINKS);
        drinkList.add(ProductCategory.COLD_DRINKS);

        ArrayList<ProductCategory> meatList = new ArrayList<ProductCategory>();
        meatList.add(ProductCategory.FISH);
        meatList.add(ProductCategory.MEAT);

        ArrayList<ProductCategory> pantryList = new ArrayList<ProductCategory>();
        pantryList.add(ProductCategory.POD);
        pantryList.add(ProductCategory.FLOUR_SUGAR_SALT);
        pantryList.add(ProductCategory.NUTS_AND_SEEDS);
        pantryList.add(ProductCategory.PASTA);
        pantryList.add(ProductCategory.POTATO_RICE);
        pantryList.add(ProductCategory.HERB);

        ArrayList<ProductCategory> dairyList = new ArrayList<ProductCategory>();
        dairyList.add(ProductCategory.DAIRIES);

        ArrayList<ProductCategory> sweetList = new ArrayList<ProductCategory>();
        sweetList.add(ProductCategory.SWEET);

        ArrayList<ProductCategory> breadList = new ArrayList<ProductCategory>();
        breadList.add(ProductCategory.BREAD);

        ArrayList<ProductCategory> vegetableList = new ArrayList<ProductCategory>();
        vegetableList.add(ProductCategory.VEGETABLE_FRUIT);
        vegetableList.add(ProductCategory.CABBAGE);
        vegetableList.add(ProductCategory.ROOT_VEGETABLE);

        HashMap<Button, ArrayList<ProductCategory>> hashMap = new HashMap();
        hashMap.put(fruitButton, fruitList);
        hashMap.put(drinksButton, drinkList);
        hashMap.put(meatButton, meatList);
        hashMap.put(pantryButton, pantryList);
        hashMap.put(dairyButton, dairyList);
        hashMap.put(sweetButton, sweetList);
        hashMap.put(breadButton, breadList);
        hashMap.put(vegetableButton, vegetableList);

        ArrayList<ProductCategory> selectedCategories = hashMap.get(event.getSource());
        if (selectedCategories != null) {
            ArrayList<Product> products = new ArrayList<Product>();

            for (ProductCategory productCategory : selectedCategories) {
                products.addAll(IMatDataHandler.getInstance().getProducts(productCategory));
            }

            updateProducts(products);
        }
    }

    @FXML
    void searchButtonPushed(ActionEvent event) {
        // Show products with names containing the search field text
        List<Product> products = IMatDataHandler.getInstance().getProducts();
        Predicate<Product> predicate = p -> p.getName().toLowerCase().contains(searchField.getText().toLowerCase());
        List<Product> filter = products.stream().filter(predicate).collect(Collectors.toList());
        updateProducts((ArrayList<Product>) filter);

    }

    @FXML
    void initialize() {
        assert fruitButton != null : "fx:id=\"fruitButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert vegetableButton != null : "fx:id=\"vegetableButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert meatButton != null : "fx:id=\"produceButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert breadButton != null : "fx:id=\"breadButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert pantryButton != null : "fx:id=\"pantryButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert drinksButton != null : "fx:id=\"drinksButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert sweetButton != null : "fx:id=\"sweetButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert dairyButton != null : "fx:id=\"dairyButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'sample.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert productPane != null : "fx:id=\"productPane\" was not injected: check your FXML file 'sample.fxml'.";
        assert cartView != null : "fx:id=\"cartView\" was not injected: check your FXML file 'sample.fxml'.";

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);

        cartView.setCellFactory(cartListView -> new CartViewCell());
    }

    private ObservableList<ShoppingItem> cartItemObservableList;

    public void shoppingCartChanged(CartEvent cartEvent) {
        cartItemObservableList = FXCollections.observableArrayList();
        // read all items from shoppingcart and add to observablelist
        cartItemObservableList.addAll(IMatDataHandler.getInstance().getShoppingCart().getItems());
        // add all items to shoppingcart list
        cartView.setItems(cartItemObservableList);
        cartView.refresh();
    }

    /**
     * Will clear all of the products in the productPane and then draw
     * out the new views for each of the products in the productList.
     *
     * @param productList List of products to draw
     */
    private void updateProducts(ArrayList<Product> productList) {
        // Empty all of the existing items
        productPane.getChildren().clear();

        // Draw the new product list
        /*for (Product product : productList) {
            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    IMatDataHandler.getInstance().getShoppingCart().addProduct(product);
                }
            });
            button.setText(product.getName());
            productPane.getChildren().add(button);
        }*/
        // Draw the new product list
        for (Product product : productList) {
            //nodes
            Label imageLabel = new Label();
            Label productNameLabel = new Label();
            BorderPane productPaneTile = new BorderPane();
            Button button = new Button();

            //Dimensions
            productPaneTile.setMinHeight(70);
            productPaneTile.setMinWidth(40);
            //imageLabel.setMinHeight(30);
            //imageLabel.setMinWidth(30);
            Dimension productImageDimension = new Dimension(267,200);
            // Allignment
            productPaneTile.setTop(productNameLabel);
            productPaneTile.setBottom(button);
            productPaneTile.setCenter(imageLabel);
            productPaneTile.setAlignment(button, Pos.BOTTOM_CENTER);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    IMatDataHandler.getInstance().getShoppingCart().addProduct(product);
                }
            });
            // Prdoct name at top
            productNameLabel.setText(product.getName());
            button.setText("Add to Cart");
            productPane.getChildren().add(productPaneTile);
            // converting images to fx campatible type, images in backend are for swing :(
            ImageIcon icon = IMatDataHandler.getInstance().getImageIcon(product, productImageDimension);
            ImageView productImage = new ImageView(convertBackendImage(icon));
            imageLabel.setGraphic(productImage);
        }
    }

    private WritableImage convertBackendImage(ImageIcon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return SwingFXUtils.toFXImage(bi, null);
    }


}