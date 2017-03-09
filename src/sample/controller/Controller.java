package sample.controller;


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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import sample.view.*;
import se.chalmers.ait.dat215.project.*;

public class Controller implements ShoppingCartListener {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    // Top left view variables
    @FXML
    private TextField searchField;

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

    // Left view variables
    @FXML
    private TilePane productPane;

    @FXML
    private StackPane rightPane;

    @FXML
    private Button accountButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button cartButton;

    @FXML
    private Button favoriteButtonTop;

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
        assert productPane != null : "fx:id=\"productPane\" was not injected: check your FXML file 'sample.fxml'.";

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
        IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(null, false);

        // Set the initial right hand view
        setRightHandView(cartView);

        // Cart View
        cartView.buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(deliveryView);
                cartButton.setOpacity(0.65);
            }
        });

        // Account View
        accountView.setIsEditing(false);
        accountView.backButton.setVisible(false);
        accountView.backButton.setManaged(false);
        accountView.nextButton.setVisible(false);
        accountView.nextButton.setManaged(false);
        accountView.cardCVCField.setVisible(false);
        accountView.saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (accountView.getEditing()) {
                    IMatDataHandler.getInstance().getCustomer().setFirstName(accountView.customerFirstNameField.getText());
                    IMatDataHandler.getInstance().getCustomer().setLastName(accountView.customerLastNameField.getText());
                    IMatDataHandler.getInstance().getCustomer().setEmail(accountView.customerEmailField.getText());
                    IMatDataHandler.getInstance().getCustomer().setAddress(accountView.customerAddressField.getText());
                    IMatDataHandler.getInstance().getCustomer().setPhoneNumber(accountView.customerPhonenumberField.getText());
                    IMatDataHandler.getInstance().getCustomer().setPostCode(accountView.customerPostCodeField.getText());
                    IMatDataHandler.getInstance().getCustomer().setPostAddress(accountView.customerPostAddressField.getText());

                    // Cardholder name is read from customer name fields
                    IMatDataHandler.getInstance().getCreditCard().setHoldersName(accountView.customerFirstNameField.getText() + " " + accountView.customerLastNameField.getText());
                    IMatDataHandler.getInstance().getCreditCard().setCardNumber(accountView.cardNumberField.getText());
                    IMatDataHandler.getInstance().getCreditCard().setVerificationCode(Integer.parseInt(accountView.cardCVCField.getText()));
                    IMatDataHandler.getInstance().getCreditCard().setValidMonth(Integer.parseInt(accountView.cardMonthField.getText()));
                    IMatDataHandler.getInstance().getCreditCard().setValidYear(Integer.parseInt(accountView.cardYearField.getText()));

                    char[] digits = accountView.cardNumberField.getText().toCharArray();
                    if (digits.length > 0 && digits[0] == '4') {
                        IMatDataHandler.getInstance().getCreditCard().setCardType("VISA");
                    } else if (digits.length > 1 && digits[0] == '5' && digits[1] < '6') {
                        IMatDataHandler.getInstance().getCreditCard().setCardType("MasterCard");
                    } else {
                        IMatDataHandler.getInstance().getCreditCard().setCardType("Unknown");
                    }
                }

                accountView.setIsEditing(!accountView.getEditing());
                accountView.setIsCustomerComplete(IMatDataHandler.getInstance().isCustomerComplete());
            }
        });

        // Delivery View Buttons
        deliveryView.setIsEditing(true);
        deliveryView.setIsCustomerComplete(false);
        deliveryView.saveButton.setVisible(false);
        deliveryView.saveButton.setManaged(false);
        deliveryView.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(cartView);
            }
        });

        deliveryView.nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(confirmationView);
            }
        });

        // Confirmation View Buttons
        confirmationView.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(deliveryView);
            }
        });

        confirmationView.confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(completionView);
                IMatDataHandler.getInstance().placeOrder(true);
            }
        });

        // Completion View Buttons
        completionView.doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setRightHandView(cartView);
            }
        });
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
        fruitButton.setOpacity(0.65);
        dairyButton.setOpacity(0.65);
        sweetButton.setOpacity(0.65);
        pantryButton.setOpacity(0.65);
        meatButton.setOpacity(0.65);
        vegetableButton.setOpacity(0.65);
        breadButton.setOpacity(0.65);
        drinksButton.setOpacity(0.65);
        favoriteButtonTop.setOpacity(0.65);

        String category = ((Button) event.getSource()).getText();
        if (category.equals("Frukt")) {
            fruitButton.setOpacity(1.0);
        } else if (category.equals("Mejeri")) {
            dairyButton.setOpacity(1.0);
        } else if (category.equals("Sötsaker")) {
            sweetButton.setOpacity(1.0);
        } else if (category.equals("Skafferi")) {
            pantryButton.setOpacity(1.0);
        } else if (category.equals("Kött och Fisk")) {
            meatButton.setOpacity(1.0);
        } else if (category.equals("Grönt")) {
            vegetableButton.setOpacity(1.0);
        } else if (category.equals("Bröd")) {
            breadButton.setOpacity(1.0);
        } else if (category.equals("Drycker")) {
            drinksButton.setOpacity(1.0);
        }
    }

    @FXML
    void favoriteButtonPushed(ActionEvent event) {
        updateProducts(IMatDataHandler.getInstance().favorites());
        fruitButton.setOpacity(0.65);
        dairyButton.setOpacity(0.65);
        sweetButton.setOpacity(0.65);
        pantryButton.setOpacity(0.65);
        meatButton.setOpacity(0.65);
        vegetableButton.setOpacity(0.65);
         breadButton.setOpacity(0.65);
        drinksButton.setOpacity(0.65);
        favoriteButtonTop.setOpacity(1.0);

    }

    @FXML
    void searchButtonPushed(ActionEvent event) {
        if (searchField.getText().length() > 0) {
            List<Product> products = IMatDataHandler.getInstance().getProducts();
            String regex = searchField.getText().length() < 4 ? "^(?i)" + searchField.getText() + ".*" : ".*(?i)" + searchField.getText() + ".*";
            Pattern pattern = Pattern.compile(regex);
            Predicate<Product> predicate = p -> pattern.matcher(p.getName()).matches();
            List<Product> filter = products.stream().filter(predicate).collect(Collectors.toList());
            updateProducts(filter);
        }
    }

    @FXML
    void cartPushed(ActionEvent event) {
        setRightHandView(cartView);
        historyButton.setOpacity(0.65);
        accountButton.setOpacity(0.65);
        cartButton.setOpacity(1.0);
    }

    @FXML
    void historyPushed(ActionEvent event) {
        setRightHandView(historyView);
        historyButton.setOpacity(1.0);
        accountButton.setOpacity(0.65);
        cartButton.setOpacity(0.65);
    }

    @FXML
    void accountPushed(ActionEvent event) {
        setRightHandView(accountView);
        historyButton.setOpacity(0.65);
        accountButton.setOpacity(1.0);
        cartButton.setOpacity(0.65);
    }


    private ObservableList<ShoppingItem> cartItemObservableList;
    private HashMap<Button, List<ProductCategory>> productMap;
    private HistoryView historyView = new HistoryView();
    private AccountView accountView = new AccountView();
    private AccountView deliveryView = new AccountView();
    private CartView cartView = new CartView();
    private CompletionView completionView = new CompletionView();
    private ConfirmationView confirmationView = new ConfirmationView();


    public void shoppingCartChanged(CartEvent cartEvent) {
        // Read all items from the shopping cart and add to observable list
        cartItemObservableList = FXCollections.observableArrayList();
        cartItemObservableList.addAll(IMatDataHandler.getInstance().getShoppingCart().getItems());

        // Add all items to shopping cart list
        cartView.cartList.setItems(cartItemObservableList);
        cartView.cartList.refresh();

        if (cartEvent.isAddEvent()) {
            setRightHandView(cartView);
            historyButton.setOpacity(0.65);
            accountButton.setOpacity(0.65);
            cartButton.setOpacity(1.0);
        }

        Boolean isCartEmpty = IMatDataHandler.getInstance().getShoppingCart().getItems().isEmpty();
        cartView.buyButton.setDisable(isCartEmpty);
    }

    /**
     * Changes the content in the right pane.
     *
     * @param pane Pane to set the right stack pane to
     */
    private void setRightHandView(Pane pane) {
        IMatDataHandler instance = IMatDataHandler.getInstance();

        if (pane == accountView) {
            accountView.configureView(instance.getCustomer(), instance.getCreditCard());
        } else if (pane == deliveryView) {
            deliveryView.configureView(instance.getCustomer(), instance.getCreditCard());
        } else if (pane == confirmationView) {
            confirmationView.configureView(instance.getShoppingCart(), instance.getCustomer(), instance.getCreditCard());
        } else if (pane == historyView) {
            historyView.configureView(IMatDataHandler.getInstance().getOrders());
        }

        rightPane.getChildren().clear();
        pane.prefHeightProperty().bind(rightPane.heightProperty());
        pane.prefWidthProperty().bind(rightPane.widthProperty());
        rightPane.getChildren().add(0, pane);
    }

    /**
     * Will clear all of the products in the productPane and then draw
     * out the new view for each of the products in the productList.
     *
     * @param productList List of products to draw
     */
    private void updateProducts(List<Product> productList) {
        // Empty all of the existing items
        productPane.getChildren().clear();

        for (Product product : productList) {
            ProductCell cell = new ProductCell();
            cell.updateItem(product);
            productPane.getChildren().add(cell);
        }
    }

}