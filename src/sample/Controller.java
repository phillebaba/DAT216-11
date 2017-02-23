package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.*;

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
    private Button produceButton;

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
    private GridPane productPane;

    @FXML
    private TableView<?> cartView;

    @FXML
    void categoryButtonPushed(ActionEvent event) {
        ArrayList<ProductCategory> fruitList = new ArrayList<ProductCategory>();
        fruitList.add(ProductCategory.FRUIT);
        fruitList.add(ProductCategory.BERRY);

        HashMap<Button, ArrayList<ProductCategory>> hashMap = new HashMap();
        hashMap.put(fruitButton, fruitList);

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
    void initialize() {
        assert fruitButton != null : "fx:id=\"fruitButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert vegetableButton != null : "fx:id=\"vegetableButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert produceButton != null : "fx:id=\"produceButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert breadButton != null : "fx:id=\"breadButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert pantryButton != null : "fx:id=\"pantryButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert drinksButton != null : "fx:id=\"drinksButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert sweetButton != null : "fx:id=\"sweetButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert dairyButton != null : "fx:id=\"dairyButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'sample.fxml'.";
        assert productPane != null : "fx:id=\"productPane\" was not injected: check your FXML file 'sample.fxml'.";
        assert cartView != null : "fx:id=\"cartView\" was not injected: check your FXML file 'sample.fxml'.";

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }

    void shoppingCartChanged(CartEvent var1) {
        // Remove all items form cartView
        // IMatDataHandler.getInstance().getShoppingCart().getItems()
        // Add new items
    }

    /**
     * Will clear all of the products in the productPane and then draw
     * out the new views for each of the products in the productList.
     *
     * @param productList List of products to draw
     */
    private void updateProducts(ArrayList<Product> productList) {
        // One-liner to clear the GridPane
        //productPane.getChildren().retainAll(productPane.getChildren().get(0));

        // Draw the new product list
        for (Product product: productList) {
            System.out.println(product.getName());
            //Button button = new Button();
            //productPane.add(button, 0, 0);
        }
    }

}
