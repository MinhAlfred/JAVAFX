package org.example.hsf301.layouts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.hsf301.controllers.LoginController;
import org.example.hsf301.enums.ERole;
import org.example.hsf301.navigate.Navigable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryStaffController implements Navigable, Initializable {
    @FXML
    Button btnTourList;
    @FXML
    Button btnKoiList;
    @FXML
    Button btnDelivery;
    @FXML
    Button btnDeposit;
    public DeliveryStaffController(){

    }

    @FXML
    private StackPane contentArea;
    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }

    @FXML
    private void btnTourListAction() throws IOException {
        navigateBookingTourList(contentArea);
    }

    @FXML
    private void btnDepositAction() throws IOException {
            navigateDeposit(contentArea);
    }

    @FXML
    private void btnDeliveryAction() throws IOException {
        navigateDelivery(contentArea);
    }

    @FXML
    private void btnKoiListAction() throws IOException {
        navigateBookingKoiList(contentArea);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Set tours_home_page as the default content when the app starts
            setContent("BookingKoiListStaff", contentArea);
            if(LoginController.account.getRole()== ERole.DELIVERY_STAFF){
                btnTourList.setDisable(true);
                btnDeposit.setDisable(true);
            } else {
                btnDelivery.setDisable(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
