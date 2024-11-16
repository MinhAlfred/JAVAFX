package org.example.hsf301.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.pojos.KoiFarms;
import org.example.hsf301.services.IKoiFarmService;

@RequiredArgsConstructor
public class FarmController implements Initializable {

    private final IKoiFarmService koiFarmService;

    @FXML
    private GridPane farmGrid;

    private static final int COLUMNS = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayFarms();
    }

    private void displayFarms() {
        List<KoiFarms> farms = koiFarmService.findAll();
        int row = 0;
        int col = 0;

        for (KoiFarms farm : farms) {
            if (farm.isActive()) {
                VBox farmCard = createFarmCard(farm);
                farmGrid.add(farmCard, col, row);

                col++;
                if (col == COLUMNS) {
                    col = 0;
                    row++;
                }
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

        viewDetailsButton.setOnAction(event -> handleViewDetails(farm));

        // Add all elements to card
        card.getChildren().addAll(
            nameLabel,
            contactBox,
            descriptionLabel,
            viewDetailsButton
        );

        return card;
    }

    private void handleViewDetails(KoiFarms farm) {
        // TODO: Implement view details logic
        System.out.println("Viewing details for farm: " + farm.getFarmName());
        // Add navigation logic to detailed view
    }
}