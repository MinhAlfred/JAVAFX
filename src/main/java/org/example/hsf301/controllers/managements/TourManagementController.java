package org.example.hsf301.controllers.managements;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
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
import lombok.extern.slf4j.Slf4j;
import org.example.hsf301.dtos.request.TourRequest;
import org.example.hsf301.pojos.Tours;
import org.example.hsf301.services.TourService;
import org.example.hsf301.utils.AppAlert;

@Slf4j
@RequiredArgsConstructor
public class TourManagementController implements Initializable {
    private static final String IMAGE_PATTERN = "^/org/example/hsf301/assets/img/[^/]+\\.(jpg|png)$";
    @FXML
    private GridPane tourGrid;
    @FXML
    private Button addBtnOnAction;
    private final TourService tourService;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
    private static final int COLUMNS = 4;
    private static final SimpleDateFormat DATE_FORMATTER =
        new SimpleDateFormat("MMM dd, yyyy HH:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addBtnOnAction.setOnAction(event -> handleAdd());
        displayTours();
    }

    private void displayTours() {
        List<Tours> tours = tourService.findAll();
        int row = 0;
        int col = 0;

        for (Tours tour : tours) {
            VBox tourCard = createTourCard(tour);
            tourGrid.add(tourCard, col, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createTourCard(Tours tour) {
        VBox card = new VBox(10);
        card.setMaxWidth(300);
        card.setPrefWidth(300);
        card.setStyle("-fx-background-color: #FFFFFF; " +
                          "-fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 8; " +
                          "-fx-background-radius: 8; " +
                          "-fx-padding: 15; " +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Image
        ImageView imageView = new ImageView();
        try {
            String imagePath = tour.getTourImg();
            Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            imageView.setImage(image);
            imageView.setFitWidth(320);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Could not load image: " + tour.getTourImg());
        }

        // Tour Name
        Label nameLabel = new Label(tour.getTourName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        nameLabel.setWrapText(true);

        // Description
        Label descriptionLabel = new Label(tour.getDescription());
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setWrapText(true);

        // Date/Time
        Label dateTimeLabel = new Label(String.format("From: %s\nTo: %s",
                tour.getStartTime(),
                tour.getEndTime()));

        // Price and Availability HBox
        HBox priceBox = new HBox(10);
        priceBox.setAlignment(Pos.CENTER_LEFT);

        Label priceLabel = new Label(String.format("$%.2f", tour.getUnitPrice()));
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");

        Label availabilityLabel = new Label(
            String.format("Available spots: %d/%d", tour.getRemaining(),
                          tour.getMaxParticipants()));
        availabilityLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #95a5a6;");

        priceBox.getChildren().addAll(priceLabel, availabilityLabel);

        // CRUD Buttons

        Button editButton = createStyledButton("Edit", "#f39c12");
        Button deleteButton = createStyledButton("Delete", "#e74c3c");
        Button viewButton = createStyledButton("View", "#3498db");

        editButton.setOnAction(event -> {
            try {
                handleEdit(tour);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        deleteButton.setOnAction(event -> handleDelete(tour));
        viewButton.setOnAction(event -> handleView(tour));

        HBox crudButtons = new HBox(5, viewButton, editButton, deleteButton);
        crudButtons.setAlignment(Pos.CENTER);

        // Book Button
        Button bookButton = new Button("Book Now");
        bookButton.setMaxWidth(Double.MAX_VALUE);
        bookButton.setStyle("-fx-background-color: #2ecc71; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-padding: 10 20; " +
                                "-fx-cursor: hand; " +
                                "-fx-background-radius: 5;");

        if (tour.getRemaining() == 0) {
            bookButton.setText("Fully Booked");
            bookButton.setStyle(bookButton.getStyle().replace("#2ecc71", "#95a5a6"));
            bookButton.setDisable(true);
        }

        addBtnOnAction.setOnAction(event -> handleAdd());

        // Add all elements to card
        card.getChildren().addAll(
            imageView,
            nameLabel,
            descriptionLabel,
            dateTimeLabel,
            priceBox,
            crudButtons

        );

        return card;
    }

    public void handleAdd() {
        TourRequest newTour = new TourRequest();
        showKoiDialog(newTour, "Create Tour");

        if (newTour.getDescription() != null && !newTour.getDescription().isEmpty()
                && newTour.getTourName() != null && !newTour.getTourName().isEmpty()
                && newTour.getTourImg() != null && !newTour.getTourImg().isEmpty()
                && newTour.getEndTime() != null
                && newTour.getStartTime() != null
                && newTour.getMaxParticipants() > 0
                && newTour.getUnitPrice() > 0

        ) {
            tourService.addTour(newTour);
            displayTours();
        } else {
            AppAlert.showAlert("Error", "Koi details are not valid");
        }
    }

    public void handleEdit(Tours tour) throws Exception {
        TourRequest tourRequest = new TourRequest();
        tourRequest.setDescription(tour.getDescription());
        tourRequest.setTourName(tour.getTourName());
        tourRequest.setTourImg(tour.getTourImg());
        tourRequest.setEndTime(tour.getEndTime());
        tourRequest.setStartTime(tour.getStartTime());
        tourRequest.setMaxParticipants(tour.getMaxParticipants());
        tourRequest.setUnitPrice(tour.getUnitPrice());

        showKoiDialog(tourRequest, "Edit Tour");
        tourService.updateTour(tour.getId(), tourRequest);
        displayTours(); // Refresh the grid
    }

    public void handleDelete(Tours tour) {
       try{
           tourService.deleteTour(tour.getId());
           AppAlert.showAlert("Success", "Tour deleted successfully");
       } catch (Exception e) {
           log.error("Error deleting tour", e);
           AppAlert.showAlert("Error", "An error occurred while deleting the tour");
       }
    }

    public void handleView(Tours tour) {
        System.out.println("Viewing tour: " + tour.getTourName());
        // TODO: Implement view logic
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
    private void showKoiDialog(Object koi, String title) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(title);

        VBox dialogContent = new VBox(20);
        dialogContent.setAlignment(Pos.CENTER);

        TextField tourName = new TextField();
        TextField unitPrice = new TextField();
        TextField maxParticipants = new TextField();
        DatePicker startTime = new DatePicker();
        DatePicker endTime = new DatePicker();
        TextField tourImg = new TextField();
        TextField description = new TextField();


        if (koi instanceof Tours tours) {
            tourName.setText(tours.getTourName());
            unitPrice.setText(String.valueOf(tours.getUnitPrice()));
            maxParticipants.setText(String.valueOf(tours.getMaxParticipants()));
            startTime.setValue(tours.getStartTime());
            endTime.setValue(tours.getEndTime());
            tourImg.setText(tours.getTourImg());
            description.setText(tours.getDescription());
        } else if (koi instanceof TourRequest newTour) {
            tourName.setText(newTour.getTourName());
            unitPrice.setText(String.valueOf(newTour.getUnitPrice()));
            maxParticipants.setText(String.valueOf(newTour.getMaxParticipants()));
            startTime.setValue(newTour.getStartTime());
            endTime.setValue(newTour.getEndTime());
            tourImg.setText(newTour.getTourImg());
            description.setText(newTour.getDescription());
        }

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #2ecc71; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10 20; " +
                "-fx-cursor: hand; " +
                "-fx-background-radius: 5;");
        saveButton.setOnAction(event -> {
            String imageValue = tourImg.getText();
            if (validateImageFormat(imageValue)) {
                if (koi instanceof Tours existingKoiFarm) {
                    existingKoiFarm.setDescription(description.getText());
                    existingKoiFarm.setEndTime(endTime.getValue());
                    existingKoiFarm.setStartTime(startTime.getValue());
                    existingKoiFarm.setMaxParticipants(Integer.parseInt(maxParticipants.getText()));
                    existingKoiFarm.setTourImg(tourImg.getText());
                    existingKoiFarm.setTourName(tourName.getText());
                    existingKoiFarm.setUnitPrice(Float.parseFloat(unitPrice.getText()));
                } else if (koi instanceof TourRequest newKoiFarm) {
                    newKoiFarm.setDescription(description.getText());
                    newKoiFarm.setEndTime(endTime.getValue());
                    newKoiFarm.setStartTime(startTime.getValue());
                    newKoiFarm.setMaxParticipants(Integer.parseInt(maxParticipants.getText()));
                    newKoiFarm.setTourImg(tourImg.getText());
                    newKoiFarm.setTourName(tourName.getText());
                    newKoiFarm.setUnitPrice(Float.parseFloat(unitPrice.getText()));
                }
                dialogStage.close();
            } else {
                AppAlert.showAlert("Error",
                        "The image field must match the format /org/example/hsf301/assets/img/[filename].[jpg|png]");
            }
        });

        dialogContent.getChildren().addAll(
                new Label("Tour Name:"), tourName,
                new Label("Price:"), unitPrice,
                new Label("Description:"), description,
                new Label("Max Participants:"), maxParticipants,
                new Label("Start:"), startTime,
                new Label("End:"), endTime,
                new Label("Image:"), tourImg,

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
