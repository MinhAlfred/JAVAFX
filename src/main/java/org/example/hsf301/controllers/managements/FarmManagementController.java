package org.example.hsf301.controllers.managements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
import org.example.hsf301.controllers.FarmDetailController;
import org.example.hsf301.dtos.request.KoiFarmRequest;
import org.example.hsf301.pojos.KoiFarms;
import org.example.hsf301.services.IKoiFarmService;
import org.example.hsf301.utils.AppAlert;

@RequiredArgsConstructor
public class FarmManagementController implements Initializable {
    private static final String IMAGE_PATTERN = "^/org/example/hsf301/assets/img/[^/]+\\.(jpg|png)$";
    @FXML
    private GridPane farmGrid;
    @FXML
    private Button addBtnOnAction;
    private final IKoiFarmService koiFarmService;
    private static final int COLUMNS = 4;
    private static final SimpleDateFormat DATE_FORMATTER =
        new SimpleDateFormat("MMM dd, yyyy HH:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayTours();
        addBtnOnAction.setOnAction(event -> handleAdd());
    }

    private void displayTours() {
        List<KoiFarms> farms = koiFarmService.findAll();
        int row = 0;
        int col = 0;

        for (KoiFarms farm : farms) {
            VBox tourCard = createFarmCard(farm);
            farmGrid.add(tourCard, col, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createFarmCard(KoiFarms farm) {
        VBox card = new VBox(10);
        card.setMaxWidth(350);
        card.setPrefWidth(350);
        card.setStyle("-fx-background-color: white; " +
                          "-fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 8; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Farm Image
        if (farm.getImage() != null && !farm.getImage().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(farm.getImage()));
                imageView.setFitWidth(320);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);
                card.getChildren().add(imageView);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        // Farm Name
        Label nameLabel = new Label(farm.getFarmName());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        nameLabel.setWrapText(true);

        // Contact Information
        VBox contactBox = new VBox(5);
        contactBox.setStyle("-fx-padding: 10 0;");

        // Phone and Email
        HBox contactDetailsBox = new HBox(15);
        contactDetailsBox.setAlignment(Pos.CENTER_LEFT);

        Label phoneLabel = new Label("📞 " + farm.getFarmPhoneNumber());
        phoneLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Label emailLabel = new Label("✉ " + farm.getFarmEmail());
        emailLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        contactDetailsBox.getChildren().addAll(phoneLabel, emailLabel);

        // Address
        Label addressLabel = new Label("📍 " + farm.getFarmAddress());
        addressLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");
        addressLabel.setWrapText(true);

        // Website
        Hyperlink websiteLink = new Hyperlink(farm.getWebsite());
        websiteLink.setStyle("-fx-font-size: 13px; -fx-text-fill: #3498db;");
        websiteLink.setOnAction(e -> {
            // TODO: Implement website opening logic
            System.out.println("Opening website: " + farm.getWebsite());
        });

        contactBox.getChildren().addAll(contactDetailsBox, addressLabel, websiteLink);

        // Description
        Label descriptionLabel = new Label(farm.getDescription());
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setWrapText(true);

        // View Details Button
        Button viewDetailsButton = new Button("View Farm Details");
        viewDetailsButton.setMaxWidth(Double.MAX_VALUE);
        viewDetailsButton.setStyle("-fx-background-color: #2ecc71; " +
                                       "-fx-text-fill: white; " +
                                       "-fx-font-size: 14px; " +
                                       "-fx-padding: 10 20; " +
                                       "-fx-cursor: hand; " +
                                       "-fx-background-radius: 5;");

        // CRUD Buttons
        HBox crudButtons = new HBox(5);
        crudButtons.setAlignment(Pos.CENTER);

        Button editButton = createStyledButton("Edit", "#f39c12");
        Button deleteButton = createStyledButton("Delete", "#e74c3c");
        Button viewButton = createStyledButton("View", "#3498db");

        addBtnOnAction.setOnAction(event -> handleAdd());
        editButton.setOnAction(event -> handleEdit(farm));
        deleteButton.setOnAction(event -> handleDelete(farm));
        viewButton.setOnAction(event -> handleView(farm));

        crudButtons.getChildren().addAll(viewButton, editButton, deleteButton);

        viewDetailsButton.setOnAction(event -> {
            try {
                handleViewDetails(farm);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Add all elements to card
        card.getChildren().addAll(
            nameLabel,
            contactBox,
            descriptionLabel,
            viewDetailsButton,
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

    private void handleBooking(KoiFarms koiFarms) {
        System.out.println("Booking tour: " + koiFarms.getFarmName());
    }

    private void handleEdit(KoiFarms koiFarms) {
        KoiFarmRequest updatedKoiFarm = new KoiFarmRequest();
        updatedKoiFarm.setFarmName(koiFarms.getFarmName());
        updatedKoiFarm.setFarmEmail(koiFarms.getFarmEmail());
        updatedKoiFarm.setFarmAddress(koiFarms.getFarmAddress());
        updatedKoiFarm.setDescription(koiFarms.getDescription());
        updatedKoiFarm.setWebsite(koiFarms.getWebsite());
        updatedKoiFarm.setFarmPhoneNumber(koiFarms.getFarmPhoneNumber());
        updatedKoiFarm.setImages(koiFarms.getImage());
        showKoiDialog(updatedKoiFarm, "Edit KoiFarm");
        koiFarmService.updateKoiFarm(koiFarms.getId(), updatedKoiFarm);
        displayTours(); // Refresh the grid
    }

    private void handleAdd() {
        KoiFarmRequest newFarm = new KoiFarmRequest();
        showKoiDialog(newFarm, "Create Farm");

        if (newFarm.getFarmName() != null && !newFarm.getFarmName().isEmpty()
                && newFarm.getFarmEmail() != null && !newFarm.getFarmEmail().isEmpty()
                && newFarm.getDescription() != null && !newFarm.getDescription().isEmpty()
                && newFarm.getImages() != null && !newFarm.getImages().isEmpty()
                && newFarm.getWebsite() != null && !newFarm.getWebsite().isEmpty()
                && newFarm.getFarmPhoneNumber() != null && !newFarm.getFarmPhoneNumber().isEmpty()
                && newFarm.getFarmAddress() != null && !newFarm.getFarmAddress().isEmpty()

        ) {
            koiFarmService.addKoiFarm(newFarm);
            displayTours();
        } else {
            AppAlert.showAlert("Error", "Koi details are not valid");
        }
    }

    private void handleDelete(KoiFarms koiFarms) {
        koiFarmService.delete(koiFarms.getId());
        AppAlert.showAlert("Success", "Tour deleted successfully");
    }

    private void handleView(KoiFarms koiFarms) {
        System.out.println("Viewing koiFarms: " + koiFarms.getFarmName());
        // TODO: Implement view logic
    }

    private void handleViewDetails(KoiFarms farm) throws Exception {
        // TODO: Implement view details logic
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hsf301/fxml/koi_of_farm_detail.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FarmDetailController farmDetailController = loader.getController();
        farmDetailController.setKoiFarms(farm);
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        // Add navigation logic to detailed view
    }
    private void showKoiDialog(Object koi, String title) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(title);

        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);

        TextField farmName = new TextField();
        TextField farmPhoneNumber = new TextField();
        TextField farmEmail = new TextField();
        TextField farmAddress = new TextField();
        TextField website = new TextField();
        TextField description = new TextField();
        TextField images = new TextField();

        if (koi instanceof KoiFarms existingKoiFarm) {
            farmName.setText(existingKoiFarm.getFarmName());
            farmPhoneNumber.setText(existingKoiFarm.getFarmPhoneNumber());
            farmEmail.setText(existingKoiFarm.getFarmEmail());
            farmAddress.setText(existingKoiFarm.getFarmAddress());
            website.setText(existingKoiFarm.getWebsite());
            description.setText(existingKoiFarm.getDescription());
            images.setText(existingKoiFarm.getImage());
        } else if (koi instanceof KoiFarmRequest newKoiFarm) {
            farmName.setText(newKoiFarm.getFarmName());
            farmPhoneNumber.setText(newKoiFarm.getFarmPhoneNumber());
            farmEmail.setText(newKoiFarm.getFarmEmail());
            farmAddress.setText(newKoiFarm.getFarmAddress());
            website.setText(newKoiFarm.getWebsite());
            description.setText(newKoiFarm.getDescription());
            images.setText(newKoiFarm.getImages());
        }

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #2ecc71; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10 20; " +
                "-fx-cursor: hand; " +
                "-fx-background-radius: 5;");
        saveButton.setOnAction(event -> {
            String imageValue = images.getText();
            if (validateImageFormat(imageValue)) {
                if (koi instanceof KoiFarms existingKoiFarm) {
                    existingKoiFarm.setFarmName(farmName.getText());
                    existingKoiFarm.setFarmAddress(farmAddress.getText());
                    existingKoiFarm.setDescription(description.getText());
                    existingKoiFarm.setImage(imageValue);
                    existingKoiFarm.setFarmEmail(farmEmail.getText());
                    existingKoiFarm.setFarmPhoneNumber(farmPhoneNumber.getText());
                            existingKoiFarm.setWebsite(website.getText());
                } else if (koi instanceof KoiFarmRequest newKoiFarm) {
                    newKoiFarm.setFarmName(farmName.getText());
                    newKoiFarm.setFarmAddress(farmAddress.getText());
                    newKoiFarm.setDescription(description.getText());
                    newKoiFarm.setImages(imageValue);
                    newKoiFarm.setFarmEmail(farmEmail.getText());
                    newKoiFarm.setFarmPhoneNumber(farmPhoneNumber.getText());
                    newKoiFarm.setWebsite(website.getText());
                }
                dialogStage.close();
            } else {
                AppAlert.showAlert("Error",
                        "The image field must match the format /org/example/hsf301/assets/img/[filename].[jpg|png]");
            }
        });

        dialogContent.getChildren().addAll(
                new Label("Farm Name:"), farmName,
                new Label("Phone:"), farmPhoneNumber,
                new Label("Description:"), description,
                new Label("Email:"), farmEmail,
                new Label("Address:"), farmAddress,
                new Label("Image:"), images,
                new Label("Website:"), website,
                saveButton
        );

        Scene dialogScene = new Scene(dialogContent, 400, 800);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }
    private boolean validateImageFormat(String imageValue) {
        Pattern pattern = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = pattern.matcher(imageValue);
        return matcher.matches();
    }
}