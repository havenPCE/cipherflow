package controller;

import bean.CipherBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;
import util.StageManager;
import view.FXMLView;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class ResetController implements Initializable {

    @FXML
    public TextField uidField;
    @FXML
    public AnchorPane content;
    @FXML
    public PasswordField passField;
    @FXML
    public PasswordField cPassField;

    private StageManager stageManager;
    private UserService userService;
    private CipherBean cipherBean;
    private User user;


    public void goToLogin(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        cipherBean = CipherBean.INSTANCE;
        userService = new UserServiceImplimentation();
    }

    public void confirmID(ActionEvent actionEvent) throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (patternValidation(uidField.getText(), "[A-Za-z0-9]+")) {
            showAlert("Invalid UID", "The id may contain only letters and numbers!");
        } else {
            user = userService.getUser(uidField.getText());
            if (user == null) {
                showAlert("User not found", "the uid entered is not registered!");
            } else {
                cipherBean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
                content.setVisible(true);
                uidField.setEditable(false);
            }
        }
    }

    public void resetID(ActionEvent actionEvent) {
        if (content.isVisible()) {
            uidField.setEditable(true);
            uidField.clear();
            content.setVisible(false);
        }
    }

    public void proceed(ActionEvent actionEvent) throws IOException {
        if (checkPasswords(passField.getText(), cPassField.getText())) {
            user.setPassword(cipherBean.getSecurePassword(passField.getText()));
            userService.updateUser(user);
            showAlert("Password set", "new Password has been set");
            stageManager.switchScene(FXMLView.LOGIN);
        }
    }

    private boolean checkPasswords(String password, String confirmPassword) {
        if (patternValidation(password, "[a-zA-z0-9!@#$%^&*]{8,}") | patternValidation(confirmPassword, "[a-zA-z0-9!@#$%^&*]{8,}")) {
            showAlert("Invalid Password", "8 or more characters [A-Za-z!@#$%^&*] and numbers combination");
            return false;
        } else if (!password.equals(confirmPassword)) {
            showAlert("Passwords not matched", "Both password and confirm password should be same!");
            return false;
        } else return true;
    }

    private boolean patternValidation(String text, String pattern) {
        return !text.matches(pattern);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void minimizeWindow(ActionEvent actionEvent) {
        stageManager.minimize();
    }
}
