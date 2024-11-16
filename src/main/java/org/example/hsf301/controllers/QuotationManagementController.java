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
import org.example.hsf301.navigate.Navigable;
import org.example.hsf301.pojos.Quotations;
import org.example.hsf301.services.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author TourManagementController
 */
public class QuotationManagementController implements Initializable , Navigable {
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
    private ComboBox<ApproveStatus> txtApproveStatus;

    @FXML
    private ComboBox<Quotations> txtQuotationID;
    private final IQuotationService quotationService;
    private final ObservableList<Quotations> tableModel;
    private final IBookingService bookingService;
    private final IAccountService accountService;
    private final IBookingTourService bookingTourService;
    public QuotationManagementController()
    {
        accountService = new AccountService(ResourcePaths.HIBERNATE_CONFIG);
        quotationService = new QuotationService(ResourcePaths.HIBERNATE_CONFIG);
        bookingService = new BookingService(ResourcePaths.HIBERNATE_CONFIG);
        bookingTourService = new BookingTourService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel= FXCollections.observableArrayList(quotationService.getAllQuotation());

    }

    @FXML
    public void btnCancelOnAction()
    {
        Platform.exit();

    }
    @FXML
    public void btnConfirmOnAction()
    {
        Quotations selectedQuotation = txtQuotationID.getSelectionModel().getSelectedItem();
        ApproveStatus selectedStatus = txtApproveStatus.getSelectionModel().getSelectedItem();

        if (selectedQuotation != null && selectedStatus != null) {
            try {

                    selectedQuotation = quotationService.adminUpdateStatusQuotations(selectedQuotation.getId(), selectedStatus);

                // Refresh the table data after the update
                tableModel.setAll(quotationService.getAllQuotation());
                tbData.refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Update Successful");
                alert.setContentText("Quotation updated successfully.");
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace(); // Handle errors (e.g., display an alert for errors)

                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("An error occurred while updating the quotation.");
                alert.showAndWait();
            }
        } else {
            // Display a warning if no selection is made
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Required");
            alert.setHeaderText("No Selection Made");
            alert.setContentText("Please select a quotation and a status to update.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookingID.setCellValueFactory(new PropertyValueFactory<>("booking"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
         staffName.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        staffName.setCellValueFactory(cellData -> {
            LoginController.account  = cellData.getValue().getCreatedBy();
            return new SimpleStringProperty(LoginController.account != null ? LoginController.account.getUsername() : "");
        });
        approveStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        approveTime.setCellValueFactory(new PropertyValueFactory<>("approveTime"));
        tbData.setItems(tableModel);
        // Filter Quotations with status PROCESSING
        List<Quotations> processingQuotations = quotationService.getAllQuotation()
                .stream()
                .filter(quotation -> quotation.getStatus() == ApproveStatus.PROCESSING)
                .toList();

        ObservableList<Quotations> filteredQuotations = FXCollections.observableArrayList(processingQuotations);
        txtQuotationID.setItems(filteredQuotations);

        txtQuotationID.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Quotations quotation, boolean empty) {
                super.updateItem(quotation, empty);
                if (quotation == null || empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(quotation.getId()));
                }
            }
        });

        txtQuotationID.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Quotations quotation, boolean empty) {
                super.updateItem(quotation, empty);
                if (quotation == null || empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(quotation.getId()));
                }
            }
        });

        txtApproveStatus.setItems(FXCollections.observableArrayList(ApproveStatus.FINISH, ApproveStatus.REJECTED));
        txtApproveStatus.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ApproveStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (status == null || empty) {
                    setText(null);
                } else {
                    setText(status.name());
                }
            }
        });
        txtApproveStatus.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(ApproveStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (status == null || empty) {
                    setText(null);
                } else {
                    setText(status.name());
                }
            }
        });

    }

    private void refreshTable() {
        // Refresh the data from the service and update the table model
        List<Quotations> updatedQuotations = quotationService.getAllQuotation();
        tableModel.setAll(updatedQuotations);
        tbData.refresh(); // Refresh the TableView to display updated data
    }
}
