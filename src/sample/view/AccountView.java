package sample.view;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

/**
 * Created by philiplaine on 2017-03-06.
 */
public class AccountView extends VBox {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField customerFirstNameField;

    @FXML
    public TextField customerLastNameField;

    @FXML
    public TextField customerPostCodeField;

    @FXML
    public TextField customerAddressField;

    @FXML
    public TextField customerEmailField;

    @FXML
    public TextField customerPhonenumberField;

    @FXML
    public TextField cardNumberField;

    @FXML
    public TextField cardCVCField;

    @FXML
    public TextField cardMonthField;

    @FXML
    public TextField cardYearField;

    @FXML
    public TextField customerPostAddressField;

    @FXML
    public Button backButton;

    @FXML
    public Button nextButton;

    @FXML
    public Button saveButton;

    @FXML
    void initialize() {
        assert customerFirstNameField != null : "fx:id=\"customerFirstNameField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerLastNameField != null : "fx:id=\"customerLastNameField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerPostCodeField != null : "fx:id=\"customerPostCodeField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerAddressField != null : "fx:id=\"customerAddressField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerEmailField != null : "fx:id=\"customerEmailField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerPhonenumberField != null : "fx:id=\"customerPhonenumberField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert cardNumberField != null : "fx:id=\"cardNumberField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert cardCVCField != null : "fx:id=\"cardCVCField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert cardMonthField != null : "fx:id=\"cardMonthField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert cardYearField != null : "fx:id=\"cardYearField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert customerPostAddressField != null : "fx:id=\"customerPostAddressField\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'AccountView.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'AccountView.fxml'.";

        checkComplete();
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                checkComplete();
            }
        };

        customerFirstNameField.textProperty().addListener(changeListener);
        customerLastNameField.textProperty().addListener(changeListener);
        customerPostCodeField.textProperty().addListener(changeListener);
        customerAddressField.textProperty().addListener(changeListener);
        customerEmailField.textProperty().addListener(changeListener);
        customerPhonenumberField.textProperty().addListener(changeListener);
        cardNumberField.textProperty().addListener(changeListener);
        cardCVCField.textProperty().addListener(changeListener);
        cardMonthField.textProperty().addListener(changeListener);
        cardYearField.textProperty().addListener(changeListener);
        customerPostAddressField.textProperty().addListener(changeListener);
    }

    private void checkComplete() {
        Boolean complete = customerFirstNameField.getText().length() > 0 && customerLastNameField.getText().length() > 0 &&  customerPostCodeField.getText().length() > 0 && customerAddressField.getText().length() > 0 && customerEmailField.getText().length() > 0 && customerPhonenumberField.getText().length() > 0 && cardNumberField.getText().length() > 0 && cardCVCField.getText().length() > 0 && cardMonthField.getText().length() > 0 && cardYearField.getText().length() > 0 && customerPostAddressField.getText().length() > 0;
        nextButton.setDisable(!complete);
    }


    public AccountView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/AccountView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void configureView(Customer customer, CreditCard creditCard) {
        customerFirstNameField.setText(customer.getFirstName());
        customerLastNameField.setText(customer.getLastName());
        customerAddressField.setText(customer.getAddress());
        customerEmailField.setText(customer.getEmail());
        customerPhonenumberField.setText(customer.getPhoneNumber());
        customerPostCodeField.setText(customer.getPostCode());
        customerPostAddressField.setText(customer.getPostAddress());
        cardNumberField.setText(creditCard.getCardNumber());
        cardCVCField.setText(String.valueOf(creditCard.getVerificationCode()));
        cardYearField.setText(String.valueOf(creditCard.getValidYear()));
        cardMonthField.setText(String.valueOf(creditCard.getValidMonth()));
    }

    private Boolean isEditing = false;

    public Boolean getEditing() {
        return isEditing;
    }

    public void setIsEditing(Boolean isEditing) {
        this.isEditing = isEditing;

        customerFirstNameField.setDisable(!isEditing);
        customerLastNameField.setDisable(!isEditing);
        customerPostCodeField.setDisable(!isEditing);
        customerAddressField.setDisable(!isEditing);
        customerEmailField.setDisable(!isEditing);
        customerPhonenumberField.setDisable(!isEditing);
        cardNumberField.setDisable(!isEditing);
        cardCVCField.setDisable(!isEditing);
        cardMonthField.setDisable(!isEditing);
        cardYearField.setDisable(!isEditing);
        customerPostAddressField.setDisable(!isEditing);

        saveButton.setText(isEditing ? "Spara" : "Ändra");
    }

    public void setIsCustomerComplete(Boolean isCustomerComplete) {
        nextButton.setDisable(!isCustomerComplete);
    }



}
