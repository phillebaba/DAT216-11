package sample;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import se.chalmers.ait.dat215.project.*;


public class Controller implements ShoppingCartListener {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button fruitButton;

    @FXML
    private Button dairyButton;

    @FXML
    private Button drinksButton;

    @FXML
    private Button sweetButton;

    @FXML
    private Button meatButton;

    @FXML
    private Button pantryButton;

    @FXML
    private Button breadButton;

    @FXML
    private Button vegetableButton;

    @FXML
    private TilePane productPane;

    @FXML
    private StackPane rightPane;

    @FXML
    private VBox cartViewHBox;

    @FXML
    private ListView<ShoppingItem> cartView;

    @FXML
    private Button buyButton;

    @FXML
    private AnchorPane infoView;

    @FXML
    private TextField customerFirstNameField;

    @FXML
    private TextField customerLastNameField;

    @FXML
    private TextField customerPostCodeField;

    @FXML
    private TextField customerAdressField;

    @FXML
    private TextField customerEmailField;

    @FXML
    private TextField customerPhonenumberField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardCVCField;

    @FXML
    private TextField cardMonthField;

    @FXML
    private TextField cardYearField;

    @FXML
    private Button backToCartButton;

    @FXML
    private Button toConfirmationViewButton;

    @FXML
    private Button saveButton;
    @FXML
    private Button backToInfoButton;
    @FXML
    private Button confirmationButton;
    @FXML
    private AnchorPane confirmationView;
    @FXML
    private RadioButton cardMastercardRadioButton;
    @FXML
    private ListView cartConfirmationListView;
    @FXML
    private ListView cartPriceConfirmationListView;
    @FXML
    private ListView<String> paymentConfirmationListView;
    @FXML
    private RadioButton cardVisaRadioButton;
    @FXML
    private ProgressBar shoppingProgressBar;
    @FXML
    private Label cartTotalPriceConfirmationLabel;

    @FXML
    void cardMastercardRadioButtonToggled() {
        cardVisaRadioButton.setSelected(false);
    }

    @FXML
    void cardVisaRadioButtonToggled() {
        cardMastercardRadioButton.setSelected(false);
    }

    @FXML
    void saveButtonPressed(ActionEvent evt) {
        //Save customer
        IMatDataHandler.getInstance().getCustomer().setFirstName(customerFirstNameField.getText());
        IMatDataHandler.getInstance().getCustomer().setLastName(customerLastNameField.getText());
        IMatDataHandler.getInstance().getCustomer().setEmail(customerEmailField.getText());
        IMatDataHandler.getInstance().getCustomer().setAddress(customerAdressField.getText());
        IMatDataHandler.getInstance().getCustomer().setPhoneNumber(customerPhonenumberField.getText());
        IMatDataHandler.getInstance().getCustomer().setPostCode(customerPostCodeField.getText());
        //Save billing info
        IMatDataHandler.getInstance().getCreditCard().setHoldersName(customerFirstNameField.getText() + " " + customerLastNameField.getText());
        IMatDataHandler.getInstance().getCreditCard().setCardNumber(cardNumberField.getText());
        IMatDataHandler.getInstance().getCreditCard().setVerificationCode(Integer.parseInt(cardCVCField.getText()));
        IMatDataHandler.getInstance().getCreditCard().setValidMonth(Integer.parseInt(cardMonthField.getText()));
        IMatDataHandler.getInstance().getCreditCard().setValidYear(Integer.parseInt(cardYearField.getText()));
        if (cardVisaRadioButton.isSelected()) {
            IMatDataHandler.getInstance().getCreditCard().setCardType(cardVisaRadioButton.getText());
        } else if (cardMastercardRadioButton.isSelected()) {
            IMatDataHandler.getInstance().getCreditCard().setCardType(cardMastercardRadioButton.getText());
        }
        //todo need to check if numbers before reading some fields OR maybe combobox
    }

    @FXML
    void buyPushed(ActionEvent event) {
        cartViewHBox.setVisible(false);
        infoView.setVisible(true);
        shoppingProgressBar.setProgress(0.33);
    }

    @FXML
    void toConfirmationViewButtonPushed(ActionEvent event) {
        infoView.setVisible(false);
        confirmationView.setVisible(true);
        setPaymentConfirmationListView();
        setCartConfirmationListView();
        shoppingProgressBar.setProgress(0.66);
        cartTotalPriceConfirmationLabel.setText(IMatDataHandler.getInstance().getShoppingCart().getTotal() + " Kr");
    }

    @FXML
    void confirmationButtonPushed(ActionEvent event) {
        shoppingProgressBar.setProgress(1);
    }

    @FXML
    void backToInfoButtonPushed(ActionEvent event) {
        infoView.setVisible(true);
        confirmationView.setVisible(false);
        shoppingProgressBar.setProgress(0.33);
    }

    @FXML
    void backToCartButtonPushed(ActionEvent event) {
        cartViewHBox.setVisible(true);
        infoView.setVisible(false);
        shoppingProgressBar.setProgress(0);
    }

    @FXML
    void categoryButtonPushed(ActionEvent event) {
        List<ProductCategory> selectedCategories = productMap.get(event.getSource());
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
        if (searchField.getText().length() == 0) {
            updateProducts(new ArrayList<>());
        } else {
            // Show products with names containing the search field text
            List<Product> products = IMatDataHandler.getInstance().getProducts();
            Pattern pattern = Pattern.compile("^(?i)" + searchField.getText() + "\\w*");
            Predicate<Product> predicate = p -> pattern.matcher(p.getName()).matches();
            List<Product> filter = products.stream().filter(predicate).collect(Collectors.toList());
            updateProducts((ArrayList<Product>) filter);
        }

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

        // Setup product map translation from button to categories
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

        productMap = new HashMap<>();
        productMap.put(fruitButton, fruitList);
        productMap.put(drinksButton, drinkList);
        productMap.put(meatButton, meatList);
        productMap.put(pantryButton, pantryList);
        productMap.put(dairyButton, dairyList);
        productMap.put(sweetButton, sweetList);
        productMap.put(breadButton, breadList);
        productMap.put(vegetableButton, vegetableList);

        // Add self as observer of the shopping cart
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);

        // Set custom cell factory
        cartView.setCellFactory(cartListView -> new CartViewCell());
    }

    private ObservableList<ShoppingItem> cartItemObservableList;
    private HashMap<Button, List<ProductCategory>> productMap;

    public void shoppingCartChanged(CartEvent cartEvent) {
        // Read all items from the shopping cart and add to observable list
        cartItemObservableList = FXCollections.observableArrayList();
        cartItemObservableList.addAll(IMatDataHandler.getInstance().getShoppingCart().getItems());

        // Add all items to shopping cart list
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

        for (Product product : productList) {
            ProductViewCell cell = new ProductViewCell();
            cell.updateItem(product);
            productPane.getChildren().add(cell);
        }
    }

    private void setCartConfirmationListView() {
        ObservableList<String> oLNames = FXCollections.observableArrayList();
        ObservableList<String> oLPrices = FXCollections.observableArrayList();

        for (ShoppingItem sI : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
    oLNames.add(sI.getProduct().getName());
    oLPrices.add(sI.getTotal() + " Kr");
        }
        cartConfirmationListView.setItems(oLNames);
        cartPriceConfirmationListView.setItems(oLPrices);
    }

    private void setPaymentConfirmationListView() {
        ObservableList<String> oL = FXCollections.observableArrayList();
        oL.add(customerFirstNameField.getText() + " " + customerLastNameField.getText());
        if (cardVisaRadioButton.isSelected()) {
            oL.add(cardVisaRadioButton.getText() + " " + cardNumberField.getText());
        } else if (cardMastercardRadioButton.isSelected()) {
            oL.add(cardVisaRadioButton.getText() + " " + cardNumberField.getText());
        }
        oL.add(customerAdressField.getText());
        paymentConfirmationListView.setItems(oL);
        // paymentConfirmationListView.refresh();
    }
}