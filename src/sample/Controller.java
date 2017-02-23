package sample;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.*;

public class Controller {

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

    }


    @FXML
    void categoryButtonPushed(ActionEvent event) {
        ArrayList<Product> products = new ArrayList<>(IMatDataHandler.getInstance().getProducts(ProductCategory.FRUIT));

        ArrayList<ProductCategory> categoryList = new ArrayList<ProductCategory>();
        categoryList.add(ProductCategory.FRUIT);
        categoryList.add(ProductCategory.BERRY);

        HashMap<Object, ProductCategory> hashMap = new HashMap<Object, ProductCategory>();
        hashMap.put(fruitButton, ProductCategory.FRUIT);

        ProductCategory selectedCategory = hashMap.get(event.getSource());
        if (selectedCategory != null) {

        }
    }

    

}
