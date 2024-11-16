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
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.enums.ERole;
import org.example.hsf301.dtos.request.DeliveryHistoryRequest;
import org.example.hsf301.pojos.DeliveryHistory;
import org.example.hsf301.services.DeliveryHistoryService;
import org.example.hsf301.services.IDeliveryHistoryService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
@Setter
@Getter
public class DeliveryHistoryController implements Initializable {
    @FXML
    private TableView<DeliveryHistory> tbData;
    @FXML
    private TableColumn<DeliveryHistory, Long> id;
    @FXML
    private TableColumn<DeliveryHistory, String> route;
    @FXML
    private TableColumn<DeliveryHistory, String> koiHealth;
    @FXML
    private TableColumn<DeliveryHistory, LocalDate> time;
    @FXML
    private TableColumn<DeliveryHistory, String> staff;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnExit;
    private IDeliveryHistoryService deliveryHistoryService;
    private ObservableList<DeliveryHistory> tableModel;
    private Long bookingId;
    public DeliveryHistoryController(){
        deliveryHistoryService = new DeliveryHistoryService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList();
    }
    @FXML
    private TextField txtRoute;
    @FXML
    private TextArea txtDescription;

    @FXML
    public void btnAddAction() throws Exception {
        DeliveryHistoryRequest deliveryHistoryRequest = new DeliveryHistoryRequest();
        deliveryHistoryRequest.setRoute(txtRoute.getText());
        deliveryHistoryRequest.setHealthKoiDescription(txtDescription.getText());
        deliveryHistoryService.addDeliveryHistory(deliveryHistoryRequest,bookingId,LoginController.account);
        refreshTable();
    }
    @FXML
    public void btnUpdateAction() throws Exception {
        DeliveryHistory bookingKoiDetail = tbData.getSelectionModel().getSelectedItem();
        DeliveryHistoryRequest deliveryHistoryRequest = new DeliveryHistoryRequest();
        deliveryHistoryRequest.setRoute(txtRoute.getText());
        deliveryHistoryRequest.setHealthKoiDescription(txtDescription.getText());
        deliveryHistoryService.updateDeliveryHistory(bookingKoiDetail.getId(),deliveryHistoryRequest);
        refreshTable();
    }
    @FXML
    public void btnDeleteAction(){
        DeliveryHistory bookingKoiDetail = tbData.getSelectionModel().getSelectedItem();
        deliveryHistoryService.deleteDeliveryHistory(bookingKoiDetail.getId());
        refreshTable();
    }
    @FXML
    public void btnExitAction(){
        Stage stage = (Stage)  btnExit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        route.setCellValueFactory(new PropertyValueFactory<>("route"));
        koiHealth.setCellValueFactory(new PropertyValueFactory<>("healthKoiDescription"));
        time.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        staff.setCellValueFactory(cellData -> cellData.getValue().getDeliveryStaff() != null
                ? new SimpleStringProperty(cellData.getValue().getDeliveryStaff().getFirstName() + " " + cellData.getValue().getDeliveryStaff().getLastName())
                : new SimpleStringProperty("N/A"));
        if(tableModel!=null) {
            tbData.setItems(tableModel);
        }
        tbData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object index) {
                if (tbData.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = tbData.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object deliveryId = tablePosition.getTableColumn().getCellData(index);
                    try {
                        DeliveryHistory deliveryHistory = deliveryHistoryService.getDeliveryHistoryById(Long.valueOf(deliveryId.toString()));
                        show(deliveryHistory);
                    } catch (Exception ex) {
                        showAlert("Infomation Board!", "Please choose the First Cell !");
                    }
                }

            }
        });
    }
        private void show(DeliveryHistory deliveryHistory) {
        this.txtRoute.setText(deliveryHistory.getRoute());
        this.txtDescription.setText(deliveryHistory.getHealthKoiDescription());
    }
    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void refreshTable() {
        tableModel =FXCollections.observableArrayList(deliveryHistoryService.getDeliveryHistory(bookingId));
        tbData.setItems(tableModel);
    }
    public void loadData() {
        if (bookingId != null){
            tableModel=FXCollections.observableArrayList(deliveryHistoryService.getDeliveryHistory(bookingId));
        tbData.setItems(tableModel);
        if(LoginController.account.getRole()!= ERole.DELIVERY_STAFF) {
            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }
    }
}
