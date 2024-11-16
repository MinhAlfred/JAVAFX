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
import org.example.hsf301.enums.CCSTATUS;
import org.example.hsf301.enums.PaymentStatus;
import org.example.hsf301.dtos.request.DeliveryRequest;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.pojos.Delivery;
import org.example.hsf301.services.BookingKoiService;
import org.example.hsf301.services.DeliveryService;
import org.example.hsf301.services.IBookingKoiService;
import org.example.hsf301.services.IDeliveryService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {
    @FXML
    private TableView<Delivery> tbData;
    @FXML
    private TableColumn<Delivery, Long> id;
    @FXML
    private TableColumn<Delivery, String> customerName;
    @FXML
    private TableColumn<Delivery, LocalDate> receiveDate;
    @FXML
    private TableColumn<Delivery, String> healthKoiDescription;
    @FXML
    private TableColumn<Delivery, String> address;
    @FXML
    private TableColumn<Delivery, CCSTATUS> status;
    @FXML
    private TableColumn<Delivery, String> reason;
    @FXML
    private TableColumn<Delivery, Float> amount;
    @FXML
    private TableColumn<Delivery, String> bookingId;
    @FXML
    private TableColumn<Delivery, String> staff;

    @FXML
    private ComboBox<Bookings> txtBookingId;
    @FXML
    private DatePicker txtReceiveDate;
    @FXML
    private ComboBox<CCSTATUS> txtStatus;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextArea txtHealthDescription;
    @FXML
    private TextArea txtReason;
    private final IDeliveryService deliveryService;
    private ObservableList<Delivery> tableModel;
    private final IBookingKoiService bookingKoiService;
    public DeliveryController(){
        deliveryService = new DeliveryService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(deliveryService.getAllDeliveries());
        bookingKoiService = new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG);
    }

    @FXML
    public void btnAddAction(){
        Long bookingId = txtBookingId.getSelectionModel().getSelectedItem().getId();
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setCustomerName(txtCustomerName.getText());
        deliveryRequest.setReceiveDate(txtReceiveDate.getValue());
        deliveryRequest.setHealthKoiDescription(txtHealthDescription.getText());
        deliveryRequest.setAddress(txtAddress.getText());
        deliveryRequest.setStatus(txtStatus.getValue());
        deliveryRequest.setReason(txtReason.getText());
        deliveryService.addDelivery(deliveryRequest,bookingId,LoginController.account);
        refreshTable();
    }
    @FXML
    public void btnUpdateAction() throws Exception {
        Long bookingId = tbData.getSelectionModel().getSelectedItem().getId();
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setCustomerName(txtCustomerName.getText());
        deliveryRequest.setReceiveDate(txtReceiveDate.getValue());
        deliveryRequest.setHealthKoiDescription(txtHealthDescription.getText());
        deliveryRequest.setAddress(txtAddress.getText());
        deliveryRequest.setStatus(txtStatus.getValue());
        deliveryRequest.setReason(txtReason.getText());
        deliveryService.updateDeliveryHistory(bookingId,deliveryRequest);
        refreshTable();
    }
    @FXML
    public void btnDeleteAction(){
        Long bookingId = tbData.getSelectionModel().getSelectedItem().getId();
        deliveryService.deleteDelivery(bookingId);
        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        receiveDate.setCellValueFactory(new PropertyValueFactory<>("receiveDate"));
        healthKoiDescription.setCellValueFactory(new PropertyValueFactory<>("healthKoiDescription"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        amount.setCellValueFactory(new PropertyValueFactory<>("remainAmount"));
        bookingId.setCellValueFactory(cellData -> cellData.getValue().getBooking() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getBooking().getId()))
                : new SimpleStringProperty("N/A"));
        staff.setCellValueFactory(cellData -> cellData.getValue().getDeliveryStaff() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getDeliveryStaff().getUsername()))
                : new SimpleStringProperty("N/A"));
        tbData.setItems(tableModel);
        tbData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object index) {
                if (tbData.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = tbData.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object id = tablePosition.getTableColumn().getCellData(index);
                    try {
                        Delivery delivery = deliveryService.getDelivery(Long.valueOf(id.toString()));
                        show(delivery);
                    } catch (Exception ex) {
                        showAlert("Infomation Board!", "Please choose the First Cell !");
                    }
                }

            }
        });
        txtStatus.setItems(FXCollections.observableArrayList(CCSTATUS.values()));
        List<Bookings> list = bookingKoiService.getAllKoiBookingStatus(PaymentStatus.SHIPPING);
        txtBookingId.setItems(FXCollections.observableArrayList(list));
    }
    private void show(Delivery delivery) {
//        this.txtBookingId.setValue(delivery.getBooking());
        this.txtReceiveDate.setValue(delivery.getReceiveDate());
        this.txtCustomerName.setText(delivery.getCustomerName());
        this.txtAddress.setText(delivery.getAddress());
        this.txtHealthDescription.setText(delivery.getHealthKoiDescription());
        this.txtReason.setText(delivery.getReason());
        this.txtStatus.setValue(delivery.getStatus());
    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void refreshTable() {
        tableModel =FXCollections.observableArrayList(deliveryService.getAllDeliveries());
        tbData.setItems(tableModel);
    }
}
