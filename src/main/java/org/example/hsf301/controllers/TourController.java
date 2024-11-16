package org.example.hsf301.controllers;

import java.time.LocalDate;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.enums.PaymentMethod;
import org.example.hsf301.dtos.request.BookingTourDetailRequest;
import org.example.hsf301.dtos.request.BookingTourRequest;
import org.example.hsf301.pojos.Tours;
import org.example.hsf301.services.IBookingTourService;
import org.example.hsf301.services.TourService;
import org.example.hsf301.utils.AppAlert;

@RequiredArgsConstructor
public class TourController implements Initializable {

    @FXML
    private GridPane tourGrid;

    private final TourService tourService;
    private final IBookingTourService bookingTourService;
    private static final int COLUMNS = 4;
    private static final SimpleDateFormat DATE_FORMATTER =
        new SimpleDateFormat("MMM dd, yyyy HH:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayTours();
    }

    private void displayTours() {
        List<Tours> tours = tourService.findAll();
        int row = 0;
        int col = 0;

        for (Tours tour : tours) {
            if ("active".equalsIgnoreCase(tour.getStatus())) {
                VBox tourCard = createTourCard(tour);
                tourGrid.add(tourCard, col, row);

                col++;
                if (col == COLUMNS) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    private VBox createTourCard(Tours tour) {
        VBox card = new VBox(10);
        card.setMaxWidth(300);
        card.setPrefWidth(300);
        card.setStyle("-fx-background-color: white; " +
                          "-fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 8; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Image
        ImageView imageView = new ImageView();
        try {
            String imagePath = tour.getTourImg(); // This is the relative path from your project resources
            Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(imagePath))); // Load the image via the class loader
            imageView.setImage(image);
            imageView.setFitWidth(320);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Could not load image: " + tour.getTourImg());
        }

        // Tour Name
        Label nameLabel = new Label(tour.getTourName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        nameLabel.setWrapText(true);

        // Description
        Label descriptionLabel = new Label(tour.getDescription());
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setWrapText(true);

        // Date/Time
        Label dateTimeLabel = new Label(String.format("From: %s\nTo: %s",
                tour.getStartTime(),
                tour.getEndTime()));
        dateTimeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        // Price and Availability HBox
        HBox priceBox = new HBox(10);
        priceBox.setAlignment(Pos.CENTER_LEFT);

        Label priceLabel = new Label(String.format("$%.2f", tour.getUnitPrice()));
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");

        Label availabilityLabel = new Label(
            String.format("Available spots: %d/%d", tour.getRemaining(), tour.getMaxParticipants()));
        availabilityLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #95a5a6;");

        priceBox.getChildren().addAll(priceLabel, availabilityLabel);

        // Book Button
        Button bookButton = new Button("Book Now");
        bookButton.setMaxWidth(Double.MAX_VALUE);
        bookButton.setStyle("-fx-background-color: #2ecc71; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-padding: 10 20; " +
                                "-fx-cursor: hand; " +
                                "-fx-background-radius: 5;");

        if (tour.getRemaining() == 0) {
            bookButton.setText("Fully Booked");
            bookButton.setStyle(bookButton.getStyle().replace("#2ecc71", "#95a5a6"));
            bookButton.setDisable(true);
        }

        bookButton.setOnAction(event -> handleBooking(tour));

        // Add all elements to card
        card.getChildren().addAll(
            imageView,
            nameLabel,
            descriptionLabel,
            dateTimeLabel,
            priceBox,
            bookButton
        );

        return card;
    }

    private void handleBooking(Tours tour) {
        if (tour.getRemaining() > 0) {
            // TODO: Implement booking logic
            System.out.println("Booking tour: " + tour.getTourName());

            List<BookingTourDetailRequest> bookingTourDetailRequestList = List.of(
                BookingTourDetailRequest.builder()
//                    .bookingID(1L)
                    .tourID(tour.getId())
                    .participant(1)
                    .build()
            );

            BookingTourRequest request = BookingTourRequest.builder()
                .paymentMethod(PaymentMethod.BANKING)
                .details(bookingTourDetailRequestList)
                .vat(0.2F)
                .bookingDate(LocalDate.now())
                .discountAmount(20F)
                .build();

            // Add booking logic here
            bookingTourService.createTourBooking(request, LoginController.account);
            AppAlert.showAlert("Success", "Tour booked successfully");
        }
    }
}