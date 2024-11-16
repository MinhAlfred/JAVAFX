package org.example.hsf301.controllers.managements;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.dtos.request.KoiRequest;
import org.example.hsf301.pojos.Koi;
import org.example.hsf301.services.IKoiService;
import org.example.hsf301.utils.AppAlert;

@RequiredArgsConstructor
public class KoiManagementController implements Initializable {

    @FXML
    private GridPane koiGrid;
    private final IKoiService koiService;
    private static final int COLUMNS = 4;
    private static final String IMAGE_PATTERN = "^/org/example/hsf301/assets/img/[^/]+\\.(jpg|png)$";

    @FXML
    private Button addBtnOnAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayTours();
        addBtnOnAction.setOnAction(event -> handleAdd());
    }

    private void displayTours() {
        List<Koi> kois = koiService.findAll();
        int row = 0;
        int col = 0;

        for (Koi koi : kois) {
            VBox tourCard = createKoiCard(koi);
            koiGrid.add(tourCard, col, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createKoiCard(Koi koi) {
        VBox card = new VBox(10);
        card.setMaxWidth(300);
        card.setPrefWidth(300);
        card.setStyle("-fx-background-color: white; " +
                          "-fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 8; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        System.out.println("Koi data: " + koi);

        // Koi Image
        if (koi.getKoiImage() != null && !koi.getKoiImage().isEmpty()) {
            try {
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream(koi.getKoiImage()))));
                imageView.setFitWidth(280);
                imageView.setFitHeight(180);
                imageView.setPreserveRatio(true);
                card.getChildren().add(imageView);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        // Koi Name
        Label nameLabel = new Label(koi.getKoiName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        nameLabel.setWrapText(true);

        // Origin and Color HBox
        HBox detailsBox = new HBox(10);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        Label originLabel = new Label("Origin: " + koi.getOrigin());
        originLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Label colorLabel = new Label("Color: " + koi.getColor());
        colorLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        detailsBox.getChildren().addAll(originLabel, colorLabel);

        // Description
        Label descriptionLabel = new Label(koi.getDescription());
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setWrapText(true);

        // CRUD Buttons
        HBox crudButtons = new HBox(5);
        crudButtons.setAlignment(Pos.CENTER);

        Button editButton = createStyledButton("Edit", "#f39c12");
        Button deleteButton = createStyledButton("Delete", "#e74c3c");
        Button viewButton = createStyledButton("View", "#3498db");

        addBtnOnAction.setOnAction(event -> handleAdd());
        editButton.setOnAction(event -> handleEdit(koi));
        deleteButton.setOnAction(event -> handleDelete(koi));
        viewButton.setOnAction(event -> handleView(koi));

        crudButtons.getChildren().addAll(viewButton, editButton, deleteButton);

        card.getChildren().addAll(
            nameLabel,
            detailsBox,
            descriptionLabel,
            crudButtons
        );

        return card;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            String.format("-fx-background-color: %s; " +
                              "-fx-text-fill: white; " +
                              "-fx-font-size: 12px; " +
                              "-fx-padding: 5 10; " +
                              "-fx-cursor: hand; " +
                              "-fx-background-radius: 3;", color)
        );
        return button;
    }

    private void handleAdd() {
        KoiRequest newKoi = new KoiRequest();
        showKoiDialog(newKoi, "Create Koi");

        if (newKoi.getKoiName() != null && !newKoi.getKoiName().isEmpty()
            && newKoi.getImage() != null && !newKoi.getImage().isEmpty()
            && newKoi.getDescription() != null && !newKoi.getDescription().isEmpty()
            && newKoi.getColor() != null && !newKoi.getColor().isEmpty()
            && newKoi.getOrigin() != null && !newKoi.getOrigin().isEmpty()) {
            koiService.save(newKoi);
            displayTours();
        } else {
            AppAlert.showAlert("Error", "Koi details are not valid");
        }
    }

    private void handleView(Koi koi) {
        showKoiDialog(koi, "View Koi");
    }

    private void handleEdit(Koi koi) {
        KoiRequest updatedKoi = new KoiRequest();
        updatedKoi.setKoiName(koi.getKoiName());
        updatedKoi.setImage(koi.getKoiImage());
        updatedKoi.setDescription(koi.getDescription());
        updatedKoi.setColor(koi.getColor());
        updatedKoi.setOrigin(koi.getOrigin());
        showKoiDialog(updatedKoi, "Edit Koi");
        koiService.updateKoi(koi.getId(), updatedKoi);
        displayTours(); // Refresh the grid
    }

    private void handleDelete(Koi koi) {
        koiService.delete(koi.getId());
        AppAlert.showAlert("Success", "Koi deleted successfully");
        displayTours(); // Refresh the grid
    }

    private void showKoiDialog(Object koi, String title) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(title);

        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        TextField imageField = new TextField();
        TextField descriptionField = new TextField();
        TextField colorField = new TextField();
        TextField originField = new TextField();

        if (koi instanceof Koi existingKoi) {
            nameField.setText(existingKoi.getKoiName());
            imageField.setText(existingKoi.getKoiImage());
            descriptionField.setText(existingKoi.getDescription());
            colorField.setText(existingKoi.getColor());
            originField.setText(existingKoi.getOrigin());
        } else if (koi instanceof KoiRequest newKoi) {
            nameField.setText(newKoi.getKoiName());
            imageField.setText(newKoi.getImage());
            descriptionField.setText(newKoi.getDescription());
            colorField.setText(newKoi.getColor());
            originField.setText(newKoi.getOrigin());
        }

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #2ecc71; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-padding: 10 20; " +
                                "-fx-cursor: hand; " +
                                "-fx-background-radius: 5;");
        saveButton.setOnAction(event -> {
            String imageValue = imageField.getText();
            if (validateImageFormat(imageValue)) {
                if (koi instanceof Koi existingKoi) {
                    existingKoi.setKoiName(nameField.getText());
                    existingKoi.setKoiImage(imageValue);
                    existingKoi.setDescription(descriptionField.getText());
                    existingKoi.setColor(colorField.getText());
                    existingKoi.setOrigin(originField.getText());
                } else if (koi instanceof KoiRequest newKoi) {
                    newKoi.setKoiName(nameField.getText());
                    newKoi.setImage(imageValue);
                    newKoi.setDescription(descriptionField.getText());
                    newKoi.setColor(colorField.getText());
                    newKoi.setOrigin(originField.getText());
                }
                dialogStage.close();
            } else {
                AppAlert.showAlert("Error",
                                   "The image field must match the format /org/example/hsf301/assets/img/[filename].[jpg|png]");
            }
        });

        dialogContent.getChildren().addAll(
            new Label("Koi Name:"), nameField,
            new Label("Image:"), imageField,
            new Label("Description:"), descriptionField,
            new Label("Color:"), colorField,
            new Label("Origin:"), originField,
            saveButton
        );

        Scene dialogScene = new Scene(dialogContent, 400, 500);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private boolean validateImageFormat(String imageValue) {
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(imageValue);
        return matcher.matches();
    }
}