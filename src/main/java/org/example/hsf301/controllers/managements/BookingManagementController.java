package org.example.hsf301.controllers.managements;

import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.IBookingService;
import org.example.hsf301.utils.AppAlert;

@RequiredArgsConstructor
public class BookingManagementController implements Initializable {

    private final IBookingService bookingService;

    @FXML
    private GridPane bookingGrid;

    private static final int COLUMNS = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayBookings();
    }

    private void displayBookings() {
        // Retrieve the list of bookings by account ID
        List<Bookings> bookings = bookingService.findAll();
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
                Button cancelButton = new Button("Cancel");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                paymentBox.getChildren().addAll(purchaseButton, cancelButton);
            }

            paymentMethodLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

            paymentInfoBox.getChildren().addAll(paymentStatusLabel, amountLabel, paymentMethodLabel);

            // CRUD Buttons
            HBox crudButtons = new HBox(5);
            crudButtons.setAlignment(Pos.CENTER);

            Button editButton = createStyledButton("Edit", "#f39c12");
            Button deleteButton = createStyledButton("Delete", "#e74c3c");
            Button viewButton = createStyledButton("View", "#3498db");

            editButton.setOnAction(event -> handleEdit(booking));
            deleteButton.setOnAction(event -> handleDelete(booking));
            viewButton.setOnAction(event -> handleView(booking));

            crudButtons.getChildren().addAll(viewButton, editButton, deleteButton);

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

    private void handleEdit(Bookings bookings) {
        System.out.println("Editing booking: " + bookings.getId());
        // TODO: Implement edit logic
    }

    private void handleDelete(Bookings bookings) {
        bookingService.delete(bookings.getId());
        AppAlert.showAlert("Success", "Tour deleted successfully");
    }

    private void handleView(Bookings bookings) {
        System.out.println("Viewing bookings: " + bookings.getId());
        // TODO: Implement view logic
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            String.format("-fx-background-color: %s; " +
                              "-fx-text-fill: white; " +
                              "-fx-font-size: 12px; " +
                              "-fx-padding: 5 10; " +
                              "-fx-cursor: hand; " +
                              "-fx-background-radius: 3;", color)
        );
        return button;
    }
}
