package org.example.hsf301.utils;

import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

public class AppAlert {

    private static Alert alert;

    private static void setTypeAlert(Alert.AlertType type) {
        alert = new Alert(type);
    }

    public static void IS_EMPTY_FIELD(String title, String content, String moreContent) {
        setTypeAlert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.setContentText(moreContent);

        // Display the dialog
        alert.showAndWait();
    }

    public static void IS_NOT_MATCHING(String title, String content, String moreContent) {
        setTypeAlert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.setContentText(moreContent);

        // Display the dialog
        alert.showAndWait();
    }

    public static void IS_LOGIN_FAILED(String title, String content, String moreContent) {
        setTypeAlert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.setContentText(moreContent);

        // Display the dialog
        alert.showAndWait();
    }

    public static void IS_LOGIN_SUCCESS(String title, String content, String moreContent) {
        setTypeAlert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.setContentText(moreContent);

        // Display the dialog
        alert.showAndWait();
    }

    public static void handleSuccess() {
        AppAlert.IS_LOGIN_SUCCESS("Login Success", null, null);
    }

    public static void handleFail() {
        AppAlert.IS_LOGIN_FAILED("Wrong username or password", null, null);
    }

    public static void handleEmptyFields() {
        AppAlert.IS_EMPTY_FIELD("Empty Fields", "Please fill in all fields", null);
    }

    public static void IS_NOT_SUPPORT() {
        JOptionPane.showMessageDialog(null, "This function is not supported", "Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public static void IS_LOGIN_SUCCESS() {
        JOptionPane.showMessageDialog(null, "Login success", "Success",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    public static int IS_CONFIRM_EXIT() {
        return JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit Confirmation",
                                             JOptionPane.YES_NO_OPTION);
    }

    public static void IS_ABOUT_ME() {
        JOptionPane.showMessageDialog(null, "This is a Java Swing project by lcaohoanq", "About me",
                                      JOptionPane.INFORMATION_MESSAGE);
    }


    public static void showAlert(String title, String content) {
        Alert alert = new Alert(title.toLowerCase().contains("error") ? Alert.AlertType.ERROR
                                    : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
