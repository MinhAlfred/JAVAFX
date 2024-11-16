package org.example.hsf301.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.IBookingKoiService;

@RequiredArgsConstructor
public class BookingKoiController implements Initializable {

    private final IBookingKoiService bookingKoiService;

    @FXML
    private GridPane bookingGrid;

    private static final int COLUMNS = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayBookings(LoginController.account);
    }

    private void displayBookings(Account account) {
        // Retrieve the list of bookings by account ID
        List<Bookings> bookings = bookingKoiService.getAllKoiBookings(account.getUsername());
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
            if(booking.getPaymentStatus().equals(PaymentStatus.SHIPPING)) {
                Button purchaseButton = new Button("View Delivery History!");
                purchaseButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
                purchaseButton.setOnAction((event) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/DeliveryHistory.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    DeliveryHistoryController bookingKoiDetailController = loader.getController();
                    bookingKoiDetailController.setBookingId(booking.getId());
                    bookingKoiDetailController.loadData();
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                });
                paymentBox.getChildren().addAll(purchaseButton);
            }
               else if(booking.getPaymentStatus().equals(PaymentStatus.COMPLETE)||booking.getPaymentStatus().equals(PaymentStatus.CANCELLED)) {
                Button cancelButton = new Button("View Delivery");
                cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                cancelButton.setOnAction((event) -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/deliverycheckout.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    DeliveryCheckout bookingKoiDetailController = loader.getController();
                    bookingKoiDetailController.setBookings(booking);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                });
                paymentBox.getChildren().addAll(cancelButton);
            }
//                    paymentBox.getChildren().addAll(purchaseButton, cancelButton);



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
