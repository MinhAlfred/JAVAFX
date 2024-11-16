    package org.example.hsf301.controllers;

    import javafx.application.Platform;
    import javafx.beans.property.SimpleStringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import org.example.hsf301.constants.ResourcePaths;
    import org.example.hsf301.enums.ApproveStatus;
    import org.example.hsf301.enums.PaymentStatus;
    import org.example.hsf301.dtos.request.QuotationRequest;
    import org.example.hsf301.navigate.Navigable;
    import org.example.hsf301.pojos.Bookings;
    import org.example.hsf301.pojos.Quotations;
    import org.example.hsf301.services.*;

    import java.net.URL;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.ResourceBundle;

    /**
     * @author QuotationController
     */
    public class QuotationController implements Initializable, Navigable {
        @FXML
        private TableView<Quotations> tbData;

        @FXML
        private TableColumn<Quotations, Long> id;
        @FXML
        private TableColumn<Quotations, Long> bookingID;
        @FXML
        private TableColumn<Quotations, LocalDate> approveTime;
        @FXML
        private TableColumn<Quotations, Long> description;
        @FXML
        private TableColumn<Quotations, String> staffName;
        @FXML
        private TableColumn<Quotations, ApproveStatus> approveStatus;
        @FXML
        private TableColumn<Quotations,Float> amount;

        @FXML
        private ComboBox<Bookings> txtBookingID;
        @FXML
        private TextField txtId;
        @FXML
        private TextField txtStaffName;
        @FXML
        private TextField txtDescription;
        @FXML
        private ComboBox<ApproveStatus> txtApproveStatus;
        @FXML
        private TextField txtAmount;
        @FXML
        private TextField txtApproveTime;

        private final IQuotationService quotationService;
        private ObservableList<Quotations> tableModel;
        private final IBookingService bookingService;
        private final IAccountService accountService;
        private final IBookingTourService bookingTourService;

        public QuotationController (){
            accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
            quotationService = new QuotationService(ResourcePaths.HIBERNATE_CONFIG);
            bookingService = new BookingService(ResourcePaths.HIBERNATE_CONFIG);
            bookingTourService = new BookingTourService(ResourcePaths.HIBERNATE_CONFIG);
            tableModel= FXCollections.observableArrayList(quotationService.getAllQuotation());



        }

        public void initialize(URL location, ResourceBundle resources) {
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            bookingID.setCellValueFactory(new PropertyValueFactory<>("booking"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
          //  staffName.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            staffName.setCellValueFactory(cellData -> {
                LoginController.account  = cellData.getValue().getCreatedBy();
                return new SimpleStringProperty(LoginController.account != null ? LoginController.account.getUsername() : "");
            });
            approveStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            approveTime.setCellValueFactory(new PropertyValueFactory<>("approveTime"));
            tbData.setItems(tableModel);
            List<Bookings> list = bookingTourService.getAllTourBookings();
            List<Bookings> result = new ArrayList<>();
            for(Bookings booking : list){
                if(booking.getPaymentStatus().equals(PaymentStatus.PENDING)){
                    result.add(booking);
                }
            }
            ObservableList<Bookings> bookingsList = FXCollections.observableArrayList(result);
            txtBookingID.setItems(bookingsList);

            // Set cell factory to show only booking ID in the ComboBox
            txtBookingID.setCellFactory(comboBox -> new ListCell<Bookings>() {
                @Override
                protected void updateItem(Bookings booking, boolean empty) {
                    super.updateItem(booking, empty);
                    if (empty || booking == null) {
                        setText(null);
                    } else {
                        setText(String.valueOf( booking.getId())); // Display booking ID
                    }
                }
            });

            // Set a converter for displaying the selected item
            txtBookingID.setButtonCell(new ListCell<Bookings>() {
                @Override
                protected void updateItem(Bookings booking, boolean empty) {
                    super.updateItem(booking, empty);
                    if (empty || booking == null) {
                        setText(null);
                    } else {
                        setText(String.valueOf( booking.getId())); // Display booking ID
                    }
                }
            });


        }
    @FXML
        public  void btnAddOnAction (){
                QuotationRequest quotationRequest = new QuotationRequest();
            quotationRequest.setBookingId(txtBookingID.getSelectionModel().getSelectedItem().getId());
            quotationRequest.setDescription(txtDescription.getText());
            quotationRequest.setAmount(Float.valueOf(txtAmount.getText()));

            try {
             Quotations quotations =  quotationService.createQuotations(quotationRequest,LoginController.account);
                tableModel.add(quotations);
                tbData.refresh();
                showAlert("Success", "Quotation added successfully!");

            } catch (Exception e) {
                showAlert("Error", "Failed to add quotation: " + e.getMessage());
            }
           // refreshTable();
        }

        public void btnView()
        {}
        private void refreshTable() {
            tableModel = FXCollections.observableArrayList(quotationService.getAllQuotation());
            tbData.setItems(tableModel);

        }
        public void showAlert(String header, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
        @FXML
        public  void btnCancelOnAction()
        {
            Platform.exit();
        }
    }
