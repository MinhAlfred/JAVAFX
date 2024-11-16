package org.example.hsf301.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.dtos.request.KoiOfFarmRequest;
import org.example.hsf301.pojos.Koi;
import org.example.hsf301.pojos.KoiFarms;
import org.example.hsf301.pojos.KoiOfFarm;
import org.example.hsf301.services.IKoiOfFarmService;
import org.example.hsf301.services.IKoiService;
import org.example.hsf301.services.KoiOfFarmService;
import org.example.hsf301.services.KoiService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FarmDetailController implements Initializable {
    @FXML
    private TableView<KoiOfFarm> tbData;
    @FXML
    private TableColumn<KoiOfFarm, Long> id;
    @FXML
    private TableColumn<KoiOfFarm, String> koiName;
    @FXML
    private TableColumn<KoiOfFarm, String> koiDescription;
    @FXML
    private TableColumn<KoiOfFarm, String> koiOrigin;
    @FXML
    private TableColumn<KoiOfFarm, Integer> quantity;

    @FXML
    private ComboBox<Koi> txtKois;
    @FXML
    private TextField txtQuantity;

    private final IKoiOfFarmService koiOfFarmService;
    private final IKoiService koiService;
    private ObservableList<KoiOfFarm> tableModel;
    private KoiFarms koiFarms;

    public FarmDetailController() throws Exception {
        koiOfFarmService = new KoiOfFarmService(ResourcePaths.HIBERNATE_CONFIG);
        koiService = new KoiService(ResourcePaths.HIBERNATE_CONFIG);
        tableModel = FXCollections.observableArrayList();
    }

    @FXML
    public void btnAddAction() throws Exception {
        KoiOfFarmRequest request = new KoiOfFarmRequest();
        request.setFarmId(koiFarms.getId());
        request.setQuantity(Integer.parseInt(txtQuantity.getText()));
        request.setAvailable(true);
        request.setKoiId(txtKois.getSelectionModel().getSelectedItem().getId());
        koiOfFarmService.addKoiOfFarm(request);
        refreshTable();
    }
    @FXML
    public void btnDeleteAction() throws Exception {
        KoiOfFarm koiOfFarm= tbData.getSelectionModel().getSelectedItem();
        koiOfFarmService.delete(koiOfFarm.getId());
        refreshTable();
    }
    @FXML
    public void btnExitAction(){
        Stage stage = (Stage)  txtKois.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        List<Koi> kois = koiService.findAll();
        txtKois.setItems(FXCollections.observableArrayList(kois));
    }
    private void refreshTable() throws Exception {
        tableModel =FXCollections.observableArrayList(koiOfFarmService.findByFarmId(koiFarms.getId()));
        tbData.setItems(tableModel);
    }

    public KoiFarms getKoiFarms() {
        return koiFarms;
    }

    public void setKoiFarms(KoiFarms koiFarms) throws Exception {
        this.koiFarms = koiFarms;
        refreshTable();
    }
}
