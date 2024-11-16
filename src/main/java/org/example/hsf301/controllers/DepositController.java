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
import org.example.hsf301.dtos.request.DepositRequest;
import org.example.hsf301.pojos.Bookings;
import org.example.hsf301.pojos.Deposit;
import org.example.hsf301.services.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    @FXML
    private TableView<Deposit> tbData;
    @FXML
    private TableColumn<Deposit, Long> id;
    @FXML
    private TableColumn<Deposit, LocalDate> depositDate;
    @FXML
    private TableColumn<Deposit, Float> depositAmount;
    @FXML
    private TableColumn<Deposit, CCSTATUS> status;
    @FXML
    private TableColumn<Deposit, Float> remainAmount;
    @FXML
    private TableColumn<Deposit, LocalDate> deliveryDate;
    @FXML
    private TableColumn<Deposit, Float> shippingfee;
    @FXML
    private TableColumn<Deposit, String> shippingAddress;
    @FXML
    private TableColumn<Deposit, String> bookingId;

    @FXML
    private ComboBox<Bookings> txtBookingId;
    @FXML
    private DatePicker txtDeliveryDate;
    @FXML
    private TextField txtdepositPercentage;
    @FXML
    private TextArea txtShippingaddress;
    @FXML
    private TextField txtshippingfee;

    private final IDepositService depositService;
    private ObservableList<Deposit> tableModel;
    private final IBookingKoiService bookingService;
    public DepositController() {
        depositService = new DepositService(ResourcePaths.HIBERNATE_CONFIG);
        bookingService = new BookingKoiService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(depositService.getAllDeposits());

    }
    @FXML
    public void btnAddAction(){
        Long bookingId = txtBookingId.getSelectionModel().getSelectedItem().getId();
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositPercentage(Float.parseFloat(txtdepositPercentage.getText()));
        depositRequest.setShippingAddress(txtShippingaddress.getText());
        depositRequest.setShippingFee(Float.parseFloat(txtshippingfee.getText()));
        depositRequest.setDeliveryExpectedDate(txtDeliveryDate.getValue());
        depositService.createDeposit(depositRequest,bookingId);
        refreshTable();
    }
    @FXML
    public void btnUpdateAction() throws Exception {
        Long depositId = tbData.getSelectionModel().getSelectedItem().getId();
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositPercentage(Float.parseFloat(txtdepositPercentage.getText()));
        depositRequest.setShippingAddress(txtShippingaddress.getText());
        depositRequest.setShippingFee(Float.parseFloat(txtshippingfee.getText()));
        depositRequest.setDeliveryExpectedDate(txtDeliveryDate.getValue());
        depositService.updateDeposit(depositId, depositRequest);
        refreshTable();
    }
    @FXML
    public void btnCancelAction(){
        Long bookingId = tbData.getSelectionModel().getSelectedItem().getId();
        depositService.deleteById(bookingId);
        refreshTable();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        depositDate.setCellValueFactory(new PropertyValueFactory<>("depositDate"));
        depositAmount.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        status.setCellValueFactory(new PropertyValueFactory<>("depositStatus"));
        remainAmount.setCellValueFactory(new PropertyValueFactory<>("remainAmount"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryExpectedDate"));
        shippingfee.setCellValueFactory(new PropertyValueFactory<>("shippingFee"));
        shippingAddress.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));
        bookingId.setCellValueFactory(cellData -> cellData.getValue().getBooking() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getBooking().getId()))
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
                        Deposit deposit = depositService.getDepositById(Long.valueOf(id.toString()));
                        show(deposit);
                    } catch (Exception ex) {
                        showAlert("Infomation Board!", "Please choose the First Cell !");
                    }
                }

            }
        });

        List<Bookings> list = bookingService.getAllKoiBookingStatus(PaymentStatus.PENDING);
        txtBookingId.setItems(FXCollections.observableArrayList(list));
    }
    private void show(Deposit deposit) {
//        this.txtBookingId.setValue(delivery.getBooking());
        this.txtDeliveryDate.setValue(deposit.getDeliveryExpectedDate());
        this.txtdepositPercentage.setText(String.valueOf(deposit.getDepositPercentage()));
        this.txtShippingaddress.setText(deposit.getShippingAddress());
        this.txtshippingfee.setText(String.valueOf(deposit.getShippingFee()));

    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void refreshTable() {
        tableModel =FXCollections.observableArrayList(depositService.getAllDeposits());
        tbData.setItems(tableModel);
    }
}
