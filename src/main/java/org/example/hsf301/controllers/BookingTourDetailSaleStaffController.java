package org.example.hsf301.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Data;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.pojos.BookingTourDetail;
import org.example.hsf301.services.*;

import java.net.URL;
import java.util.ResourceBundle;


@Data

public class BookingTourDetailSaleStaffController implements Initializable {
    private Long bookingId;
    @FXML
    private TableView<BookingTourDetail> tblBookingTour;
    @FXML
    private TableColumn<BookingTourDetail, Long> id;
    @FXML
    private TableColumn<BookingTourDetail, Long> participant;
    @FXML
    private TableColumn<BookingTourDetail, Float> totalamount;
    @FXML
    private TableColumn<BookingTourDetail, String> bookingid;
    @FXML
    private TableColumn<BookingTourDetail, String> tourid;
    @FXML
    private Button btnExit;
    private ObservableList<BookingTourDetail> tableModel;
    private IBookingTourDetailService bookingTourDetailService;

    public void btnExitAction() {
        // Close only the window containing this button
        Stage stage = (Stage)  btnExit.getScene().getWindow();
        stage.close();
    }
    public BookingTourDetailSaleStaffController(){
         bookingTourDetailService =new BookingTourDetailService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(bookingTourDetailService.bookingTourDetails(bookingId));

    }
    public void loadData() {
        if (bookingId != null) {
            tableModel.setAll(bookingTourDetailService.bookingTourDetails(bookingId));
            tblBookingTour.setItems(tableModel);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        participant.setCellValueFactory(new PropertyValueFactory<>("participant"));
        totalamount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        bookingid.setCellValueFactory(cellData -> cellData.getValue().getBooking() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getBooking().getId()))
                : new SimpleStringProperty("N/A"));
        tourid.setCellValueFactory(cellData -> cellData.getValue().getTour() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getTour().getId()))
                : new SimpleStringProperty("N/A"));
        tblBookingTour.setItems(tableModel);


    }
}
