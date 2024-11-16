package org.example.hsf301.controllers;

import java.net.URL;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hsf301.constants.ResourcePaths;
import org.example.hsf301.enums.ERole;
import org.example.hsf301.exceptions.BadCredentialsException;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.services.AccountService;
import org.example.hsf301.services.IAccountService;
import org.example.hsf301.utils.AppAlert;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupController {

    @FXML
    public TextField usernameTextField;

    @FXML
    public TextField firstNameTextField;

    @FXML
    public TextField lastNameTextField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public PasswordField confirmPasswordField;

    @FXML
    public Button submitButton;


    public static String email = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String confirmPassword = "";

    @FXML
    protected ImageView brandingImageView;
    @FXML
    protected ImageView logoImageView;

    private IAccountService accountService = new AccountService("hibernate.cfg.xml");

    @FXML
    public void initialize() {

//        Image brandingImage = new Image(
//            Objects.requireNonNull(
//                    getClass().getResource("/org/example/hsf301/assets/img/branding.png"))
//                .toExternalForm());
//        brandingImageView.setImage(brandingImage);

        Image logoImage = new Image(
            Objects.requireNonNull(getClass().getResource("/org/example/hsf301/assets/img/koi.png"))
                .toExternalForm());
        logoImageView.setImage(logoImage);

        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter key pressed");
                submitButtonAction(null);  // Trigger login action when Enter key is pressed
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter key pressed");
                submitButtonAction(null);  // Trigger login action when Enter key is pressed
            }
        });
    }


    public void submitButtonAction(ActionEvent event) {
        if ((!usernameTextField.getText().isBlank()) && (!passwordField.getText().isBlank())) {
//            loginMessageLabel.setText("You tried to login");
            validateLogin();
        } else {
            AppAlert.IS_EMPTY_FIELD("Empty Fields", "Please fill in all fields", null);
        }
    }

    public void validateLogin() {
        email = usernameTextField.getText();
        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();

        try{

            if (!password.equals(confirmPassword)){
                throw new BadCredentialsException("Passwords do not match");
            }

            Account newAccount = Account.builder()
                .username(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .role(ERole.CUSTOMER) // default role when signing up
                .build();

            accountService.signup(newAccount);

            AppAlert.showAlert("Success", "Account created successfully");

        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            System.out.println("Error: " + e.getMessage());
            AppAlert.showAlert("Error", e.getMessage());
        }
    }

    public void loginHereAction(ActionEvent actionEvent) {
        try {
            // Load the signup.fxml file
            URL url = getClass().getResource(ResourcePaths.LOGIN_VIEW);

            // Create the controller with the service
            LoginController controller = new LoginController(new AccountService(ResourcePaths.HIBERNATE_CONFIG));

            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(controller);
            Parent root = loader.load();

            // Get the current stage (the window that contains the login button)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Option 1: Replace the current scene with the signup scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Option 2: Open signup.fxml in a new window
            // Stage newStage = new Stage();
            // newStage.setScene(new Scene(root));
            // newStage.show();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}