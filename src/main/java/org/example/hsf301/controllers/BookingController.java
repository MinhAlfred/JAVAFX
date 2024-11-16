package org.example.hsf301.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.enums.BookingType;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.IBookingService;

@RequiredArgsConstructor
public class BookingController implements Initializable {

    private final IBookingService bookingService;

    @FXML
    private GridPane bookingGrid;

    private static final int COLUMNS = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayBookings(LoginController.account);
    }

    private void displayBookings(Account account) {
        // Retrieve the list of bookings by account ID
        List<Bookings> list = bookingService.findByAccountID(account.getUsername());
        List<Bookings> bookings = new ArrayList<>();
        for (Bookings booking : list) {
            if(booking.getBookingType()== BookingType.TourBooking)
                bookings.add(booking);
        }

        if (bookings.isEmpty()) {
            Label emptyLabel = new Label("No bookings found");
            emptyLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-weight: bold;");
            bookingGrid.add(emptyLabel, 0, 0);
            return;
        }

        int row = 0;
        int col = 0;

        for (Bookings booking : bookings) {
            VBox bookingCard = createBookingCard(booking);
            bookingGrid.add(bookingCard, col, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createBookingCard(Bookings booking) {
        VBox card = new VBox(10);
        card.setMaxWidth(350);
        card.setPrefWidth(350);
        card.setStyle("-fx-background-color: white; " +
                          "-fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 8; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Booking Type
        try{
            HBox titleBox = new HBox(15);
            Label typeLabel = new Label("Booking Type: " + booking.getBookingType());
            typeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
            typeLabel.setWrapText(true);

            titleBox.setStyle("-fx-background-color: #34495e");
            titleBox.setAlignment(Pos.CENTER);
            titleBox.getChildren().addAll(typeLabel);

            // Booking Date
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Label dateLabel = new Label("Date: " + booking.getBookingDate().format(dateFormatter));
            dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

            // Payment Information
            VBox paymentInfoBox = new VBox(15);
            paymentInfoBox.setAlignment(Pos.CENTER_LEFT);

            Label paymentStatusLabel = new Label("Payment Status: " + booking.getPaymentStatus());
            paymentStatusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

            Label amountLabel = new Label("Total: $" + booking.getTotalAmountWithVAT());
            amountLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

            // Payment Method
            Label paymentMethodLabel = new Label("Payment Method: " + booking.getPaymentMethod());

            HBox paymentBox = new HBox(15);
            paymentBox.setAlignment(Pos.CENTER);
            if(booking.getPaymentStatus().equals(PaymentStatus.PROCESSING)){
                Button purchaseButton = new Button("Purchase Now!");
                purchaseButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
                purchaseButton.setOnAction((event) -> {
                    bookingService.updateBookingStatus(booking.getId(), PaymentStatus.COMPLETE);
                });

                Button cancelButton = new Button("Cancel");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                cancelButton.setOnAction((event) -> {
                    bookingService.updateBookingStatus(booking.getId(), PaymentStatus.CANCELLED);
                });

                paymentBox.getChildren().addAll(purchaseButton, cancelButton);
            }

            paymentMethodLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

            paymentInfoBox.getChildren().addAll(paymentStatusLabel, amountLabel, paymentMethodLabel);

            // Add all elements to card
            card.getChildren().addAll(
                titleBox,
                dateLabel,
                paymentInfoBox,
                paymentBox
            );

            return card;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return card;
    }
}
