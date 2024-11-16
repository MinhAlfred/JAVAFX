package org.example.hsf301.layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.example.hsf301.navigate.Navigable;

public class MainController implements Initializable, Navigable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Set tours_home_page as the default content when the app starts
            setContent("tours_home_page", contentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateHome() throws IOException {
        navigateHome(contentArea);
    }

    @FXML
    private void navigateMyProfile() throws IOException {
        navigateMyProfile(contentArea);
    }

    @FXML
    private void navigateTour() throws IOException {
        navigateTourHomePage(contentArea);
    }

    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }

    @FXML
    public void navigatePurchasedTour(ActionEvent event) throws IOException {
        navigatePurchasedTour(contentArea);
    }

    @FXML
    public void navigateBookingKoi(ActionEvent event) throws IOException {
        navigateBookingKoi(contentArea);
    }

    @FXML
    public void navigateKois(ActionEvent event) throws IOException {
        navigateKois(contentArea);
    }

    @FXML
    public void navigateFarms(ActionEvent event) throws IOException {
        navigateFarms(contentArea);
    }
}