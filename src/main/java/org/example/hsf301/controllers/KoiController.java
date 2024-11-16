package org.example.hsf301.controllers;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import org.example.hsf301.pojos.Koi;
import org.example.hsf301.services.IKoiService;

@RequiredArgsConstructor
public class KoiController implements Initializable {

    private final IKoiService koiService;

    @FXML
    private GridPane koiGrid;

    private static final int COLUMNS = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayKois();
    }

    private void displayKois() {
        List<Koi> kois = koiService.findAll();
        int row = 0;
        int col = 0;

        for (Koi koi : kois) {
            if (koi.isActive()) {
                VBox koiCard = createKoiCard(koi);
                koiGrid.add(koiCard, col, row);

                col++;
                if (col == COLUMNS) {
                    col = 0;
                    row++;
                }
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

        // View Details Button
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setMaxWidth(Double.MAX_VALUE);
        viewDetailsButton.setStyle("-fx-background-color: #2ecc71; " +
                                       "-fx-text-fill: white; " +
                                       "-fx-font-size: 14px; " +
                                       "-fx-padding: 10 20; " +
                                       "-fx-cursor: hand; " +
                                       "-fx-background-radius: 5;");

        viewDetailsButton.setOnAction(event -> handleViewDetails(koi));

        // Add all elements to card
        card.getChildren().addAll(
            nameLabel,
            detailsBox,
            descriptionLabel,
            viewDetailsButton
        );

        return card;
    }

    private void handleViewDetails(Koi koi) {
        // TODO: Implement view details logic
        System.out.println("Viewing details for koi: " + koi.getKoiName());
        // Add navigation logic to detailed view
    }
}