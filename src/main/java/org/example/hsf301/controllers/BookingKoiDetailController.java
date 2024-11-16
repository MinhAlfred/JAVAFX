package org.example.hsf301.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.dtos.request.BookingKoiDetailRequest;
import org.example.hsf301.pojos.*;
import org.example.hsf301.services.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Setter
@Getter
public class BookingKoiDetailController implements Initializable {
    @FXML
    private TableView<BookingKoiDetail> tbData;
    @FXML
    private TableColumn<BookingKoiDetail, Long> id;
    @FXML
    private TableColumn<BookingKoiDetail, String> koiFarmName;
    @FXML
    private TableColumn<BookingKoiDetail, String> koiName;
    @FXML
    private TableColumn<BookingKoiDetail, String> koiDescription;
    @FXML
    private TableColumn<BookingKoiDetail, String> koiOrigin;
    @FXML
    private TableColumn<BookingKoiDetail, Integer> quantity;
    @FXML
    private TableColumn<BookingKoiDetail, Float> unitPrice;
    @FXML
    private TableColumn<BookingKoiDetail, Float> totalAmount;

    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private ComboBox<KoiFarms> txtFarm;
    @FXML
    private ComboBox<Koi> txtKois;

    @FXML
    private Button btnExit;
    public void btnAddAction(){
        BookingKoiDetailRequest request = new BookingKoiDetailRequest();
        request.setKoiId(txtKois.getSelectionModel().getSelectedItem().getId());
        request.setFarmId(txtFarm.getSelectionModel().getSelectedItem().getId());
        request.setQuantity(Integer.parseInt(txtQuantity.getText()));
        request.setUnitPrice(Float.parseFloat(txtUnitPrice.getText()));
        bookingKoiDetailService.createKoiDetail(request,bookingId);
        refreshTable();
    }
    @FXML
    public void btnUpdateAction(){
        BookingKoiDetail bookingKoiDetail = tbData.getSelectionModel().getSelectedItem();
        BookingKoiDetailRequest request = new BookingKoiDetailRequest();
        request.setQuantity(Integer.parseInt(txtQuantity.getText()));
        request.setUnitPrice(Float.parseFloat(txtUnitPrice.getText()));
        bookingKoiDetailService.updateBookingKoiDetail(bookingKoiDetail.getId(),request,LoginController.account);
        refreshTable();
    }
    @FXML
    public void btnDeleteAction(){
        BookingKoiDetail bookingKoiDetail = tbData.getSelectionModel().getSelectedItem();
        bookingKoiDetailService.deletebyBookingKoiDetail(bookingKoiDetail.getId(),LoginController.account);
        refreshTable();
    }
    @FXML
    public void btnExitAction() {
        // Close only the window containing this button
        Stage stage = (Stage)  btnExit.getScene().getWindow();
        stage.close();
    }

    private ObservableList<BookingKoiDetail> tableModel;
    private IBookingKoiDetailService bookingKoiDetailService;
    private IKoiFarmService koiFarmService;
    private IKoiOfFarmService koiOfFarmService;

    private Long bookingId;
    public BookingKoiDetailController(){
        bookingKoiDetailService = new BookingKoiDetailService(ResourcePaths.HIBERNATE_CONFIG);
        koiFarmService = new KoiFarmService(ResourcePaths.HIBERNATE_CONFIG);
        koiOfFarmService = new KoiOfFarmService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList(bookingKoiDetailService.bookingKoiDetails(bookingId));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        koiFarmName.setCellValueFactory(cellData -> cellData.getValue().getKoiFarms() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getKoiFarms().getFarmName()))
                : new SimpleStringProperty("N/A"));
        koiName.setCellValueFactory(cellData -> cellData.getValue().getKoi() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getKoi().getKoiName()))
                : new SimpleStringProperty("N/A"));
        koiDescription.setCellValueFactory(cellData -> cellData.getValue().getKoi() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getKoi().getDescription()))
                : new SimpleStringProperty("N/A"));
        koiOrigin.setCellValueFactory(cellData -> cellData.getValue().getKoi() != null
                ? new SimpleStringProperty(String.valueOf(cellData.getValue().getKoi().getOrigin()))
                : new SimpleStringProperty("N/A"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        tbData.setItems(tableModel);
        List<KoiFarms> list = koiFarmService.findActiveFarms();
        txtFarm.setItems(FXCollections.observableArrayList(list));
        txtFarm.getSelectionModel().selectedItemProperty().addListener((obs, oldFarm, selectedFarm) -> {
            try {
                if (selectedFarm != null) {
                    // Fetch and transform the data
                    List<KoiOfFarm> koiOfFarmList = koiOfFarmService.findByFarmId(selectedFarm.getId());
                    List<Koi> koiList = koiOfFarmList.stream()
                            .map(KoiOfFarm::getKoi)
                            .collect(Collectors.toList());

                    // Update the UI
                    txtKois.setItems(FXCollections.observableArrayList(koiList));
                } else {
                    txtKois.getItems().clear(); // More efficient than creating new empty list
                }
            } catch (Exception e) {
                // Show user-friendly error message
                showAlert("Failed to load koi list", "Please try again or contact support.");
            }
        });
    }
    private void refreshTable() {
        tableModel =FXCollections.observableArrayList(bookingKoiDetailService.bookingKoiDetails(bookingId));
        tbData.setItems(tableModel);
    }
//    private void show(Bookings booking) {
//        this.txtPaymentMethod.setValue(booking.getPaymentMethod());
//        this.txtDiscountAmount.setText(String.valueOf(booking.getDiscountAmount()));
//        this.txtVAT.setText(String.valueOf(booking.getVat()));
//        this.txtBookingDate.setValue(booking.getBookingDate());
//
//    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        loadData();  // Load the data after setting the bookingId
    }
    public void loadData() {
        if (bookingId != null) {
            tableModel.setAll(bookingKoiDetailService.bookingKoiDetails(bookingId));
            tbData.setItems(tableModel);
        }
    }
}
