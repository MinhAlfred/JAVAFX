package org.example.hsf301.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.controllers.TourController;
import org.example.hsf301.services.BookingTourService;
import org.example.hsf301.services.IBookingTourService;
import org.example.hsf301.services.TourService;

public class TourView extends Application {
    private static final String FXML_PATH = "/org/example/hsf301/fxml/tours_home_page.fxml";

    @Override
    public void start(Stage stage) {
        try {
            // Initialize the TourService
            TourService tourService = new TourService(ResourcePaths.HIBERNATE_CONFIG);
            IBookingTourService bookingTourService = new BookingTourService(ResourcePaths.HIBERNATE_CONFIG);

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));

            // Create and set the controller with injected dependencies
            TourController controller = new TourController(tourService, bookingTourService);
            loader.setController(controller);

            // Load the view
            Parent root = loader.load();

            // Configure and show the stage
            Scene scene = new Scene(root);
            stage.setTitle("Tour Management");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing TourView: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
