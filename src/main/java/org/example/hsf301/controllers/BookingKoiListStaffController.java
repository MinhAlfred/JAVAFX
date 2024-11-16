package org.example.hsf301.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.enums.ERole;
import org.example.hsf301.enums.PaymentMethod;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.BookingUpdate;
import org.example.hsf301.navigate.Navigable;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.services.BookingKoiService;
import org.example.hsf301.services.IBookingKoiService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingKoiListStaffController implements Initializable , Navigable {
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
    private TableColumn<Bookings, String> account;

    @FXML
    private ComboBox<PaymentMethod> txtPaymentMethod;
    @FXML
    private ComboBox<PaymentStatus> txtStatus;
    @FXML
    private TextField txtDiscountAmount;
    @FXML
    private TextField txtVAT;
    @FXML
    private DatePicker txtBookingDate;

    @FXML
    Button btnViewDeliveryHistory;
    @FXML
    Button btnUpdate;
    @FXML
    Button btnViewDetail;
    @FXML
    Button btnCancel;

    private final IBookingKoiService bookingKoiService;
    private ObservableList<Bookings> tableModel;
    public BookingKoiListStaffController(){
        bookingKoiService = new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(bookingKoiService.getAllKoiBookings());
    }

    @FXML
    public void btnViewDeliveryHistoryAction() throws IOException {
        Bookings selectedBooking = tbData.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert("Error", "Please select a booking to view details.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/DeliveryHistory.fxml"));
        Parent root = loader.load();
        DeliveryHistoryController bookingKoiDetailController = loader.getController();
        bookingKoiDetailController.setBookingId(selectedBooking.getId());
        bookingKoiDetailController.loadData();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    public void btnCreateBookingKoiAction(){

    }
    @FXML
    public void btnViewDetailAction() throws IOException {
        Bookings selectedBooking = tbData.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert("Error", "Please select a booking to view details.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/BookingKoiDetailStaff.fxml"));
        Parent root = loader.load();
        BookingKoiDetailController bookingKoiDetailController = loader.getController();
        bookingKoiDetailController.setBookingId(selectedBooking.getId());
        bookingKoiDetailController.loadData();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    public void btnUpdateAction(){
        Bookings selectedItem = tbData.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a booking to update.");
            return;
        }
        BookingUpdate bookingUpdate = new BookingUpdate();
        bookingUpdate.setVat(Float.parseFloat(txtVAT.getText()));
        bookingUpdate.setDiscountAmount(Float.parseFloat(txtDiscountAmount.getText()));
        bookingUpdate.setPaymentMethod(txtPaymentMethod.getValue());
        bookingKoiService.updateKoiBooking(bookingUpdate,selectedItem.getId(),LoginController.account);
        tbData.refresh();
    }
    @FXML
    public void btnCancelAction(){
        tableModel = FXCollections.observableArrayList(bookingKoiService.getAllKoiBookingStatus(txtStatus.getValue()));
        tbData.setItems(tableModel);
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
        txtPaymentMethod.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
        account.setCellValueFactory(cellData -> cellData.getValue().getAccount() != null
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
                        show(booking);
                    } catch (Exception ex) {
                        showAlert("Infomation Board!", "Please choose the First Cell !");
                    }
                }

            }
        });
        txtStatus.setItems(FXCollections.observableArrayList(PaymentStatus.values()));
        if(LoginController.account.getRole()== ERole.DELIVERY_STAFF){
            btnUpdate.setDisable(true);
            btnViewDetail.setDisable(true);
        } else {
            btnViewDeliveryHistory.setDisable(true);
        }
    }
    private void show(Bookings booking) {
        this.txtPaymentMethod.setValue(booking.getPaymentMethod());
        this.txtDiscountAmount.setText(String.valueOf(booking.getDiscountAmount()));
        this.txtVAT.setText(String.valueOf(booking.getVat()));
        this.txtBookingDate.setValue(booking.getBookingDate());

    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
