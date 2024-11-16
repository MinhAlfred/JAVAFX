package org.example.hsf301.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.enums.PaymentMethod;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.navigate.Navigable;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingTourListSaleStaffController implements Initializable, Navigable {
    @FXML
    private TableView<Bookings> tbData;
    @FXML
    private TableColumn<Bookings, Long> id;
    @FXML
    private TableColumn<Bookings, LocalDate> bookingDate;
    @FXML
    private TableColumn<Bookings, Float> vat;
    @FXML
    private TableColumn<Bookings, Float> discountAmount;
    @FXML
    private TableColumn<Bookings, PaymentMethod> paymentMethod;
    @FXML
    private TableColumn<Bookings, PaymentStatus> status;
    @FXML
    private TableColumn<Bookings, Float> totalAmount;
    @FXML
    private TableColumn<Bookings, Float> totalAmountVAT;
    @FXML
    private TableColumn<Bookings, LocalDate> paymentDate;
    private final IBookingKoiService bookingKoiService;
    private final ObservableList<Bookings> tableModel;
    private final IAccountService accountService;
    private final IBookingTourService bookingTourService;
    public BookingTourListSaleStaffController(){
        accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        bookingKoiService = new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG);
        bookingTourService = new BookingTourService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(bookingTourService.getAllTourBookings());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        discountAmount.setCellValueFactory(new PropertyValueFactory<>("discountAmount"));
        paymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        status.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        totalAmountVAT.setCellValueFactory(new PropertyValueFactory<>("totalAmountWithVAT"));
        paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        tbData.setItems(tableModel);
        
    }

    public void btnViewDetailTourAction(ActionEvent actionEvent) throws IOException {
        Bookings selectedBooking = tbData.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert("Error", "Please select a booking to view details.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/BookingTourDetailSaleStaff.fxml"));
        Parent root = loader.load();
        BookingTourDetailSaleStaffController bookingKoiDetailController = loader.getController();
        bookingKoiDetailController.setBookingId(selectedBooking.getId());
        bookingKoiDetailController.loadData();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void btnSearchAccountAction(ActionEvent actionEvent) {
    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
