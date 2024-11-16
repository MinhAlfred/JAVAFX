package org.example.hsf301.layouts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.navigate.Navigable;
import org.example.hsf301.utils.NavigateUtil;

public class StaffMainController implements Navigable, Initializable {

    @FXML
    private StackPane contentArea;

    @FXML
    private void navigateLogout() throws IOException {
        navigateLogout(contentArea);
    }

    public void btnTourListAction(ActionEvent actionEvent) throws IOException {
        navigateBookingTourListSaleStaff(contentArea);
    }

    public void btnQuotationAction(ActionEvent actionEvent)  throws IOException{
        navigateQuotationSaleStaff(contentArea);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
