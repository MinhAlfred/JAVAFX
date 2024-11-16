package org.example.hsf301.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.enums.PaymentMethod;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.BookingKoiRequest;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.AccountService;
import org.example.hsf301.services.BookingKoiService;
import org.example.hsf301.services.IAccountService;
import org.example.hsf301.services.IBookingKoiService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BookingTourListStaffController implements Initializable {
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
    @FXML
    private TableColumn<Bookings, String> accountId;

    @FXML
    private ComboBox<Account> txtAccount;
    @FXML
    private DatePicker txtBookingDate;
    @FXML
    private ComboBox<PaymentMethod> txtPaymentMethod;
    @FXML
    private TextField txtDiscountAmount;
    @FXML
    private TextField txtVAT;
    @FXML
    private ComboBox<PaymentStatus> txtStatus;
    private final IBookingKoiService bookingKoiService;
    private ObservableList<Bookings> tableModel;
    private final IAccountService accountService;
    public BookingTourListStaffController(){
        accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        bookingKoiService = new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(bookingKoiService.getAllTourBookings());
    }
    @FXML
    public void btnSearchAccountAction(){

        tableModel = FXCollections.observableArrayList(bookingKoiService.getAllTourBookingStatus(txtStatus.getValue()));
        tbData.setItems(tableModel);
    }
    @FXML
    public void btnCreateBookingKoiAction(){
        Bookings selectedItem = tbData.getSelectionModel().getSelectedItem();
        BookingKoiRequest bookingKoiRequest = new BookingKoiRequest();
        bookingKoiRequest.setBookingDate(txtBookingDate.getValue());
        bookingKoiRequest.setPaymentMethod(txtPaymentMethod.getValue());
        bookingKoiRequest.setDiscountAmount(Float.parseFloat(txtDiscountAmount.getText()));
        bookingKoiRequest.setVat(Float.parseFloat(txtVAT.getText()));
        bookingKoiService.createKoiBooking(bookingKoiRequest,selectedItem.getId(),LoginController.account);
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
        accountId.setCellValueFactory(cellData -> cellData.getValue().getAccount() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getAccount().getUsername()))
                : new SimpleStringProperty("N/A"));
        tbData.setItems(tableModel);
        tbData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object index) {
                if (tbData.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = tbData.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object bookingId = tablePosition.getTableColumn().getCellData(index);
                    try {
                        Bookings booking = bookingKoiService.getKoiBookings(Long.valueOf(bookingId.toString()));
//                        show(booking);
                    } catch (Exception ex) {
                        showAlert("Infomation Board!", "Please choose the First Cell !");
                    }
                }

            }
        });
        txtStatus.setItems(FXCollections.observableArrayList(PaymentStatus.values()));
        List<Account> customers = accountService.findAllCustomers();
        txtAccount.setItems(FXCollections.observableArrayList(customers));
        txtPaymentMethod.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
    }
    private void show(Bookings booking) {
        this.txtAccount.setValue(booking.getAccount());
        this.txtBookingDate.setValue(booking.getBookingDate());
    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
