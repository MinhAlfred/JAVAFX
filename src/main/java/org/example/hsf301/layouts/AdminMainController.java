package org.example.hsf301.layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.example.hsf301.controllers.LoginController;
import org.example.hsf301.navigate.Navigable;

public class AdminMainController implements Initializable, Navigable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Set tours_home_page as the default content when the app starts
            setContent("tours_management", contentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateMyProfile() throws IOException {
        navigateMyProfile(contentArea);
    }

    @FXML
    private void navigateTour() throws IOException {
        navigateTourManagement(contentArea);
    }

    @FXML
    private void navigateBooking() throws IOException {
        navigateBookingManagement(contentArea);
    }

    @FXML
    private void navigateKoi() throws IOException {
        navigateKoiManagement(contentArea);
    }

    @FXML
    private void navigateStaff() throws IOException {
        navigateStaff(contentArea);
    }

    @FXML
    private void navigateSetting() throws IOException {
        navigateSetting(contentArea);
    }

    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }

    @FXML
    private void navigateFarm() throws IOException {
        navigateFarmManagement(contentArea);
    }

}
