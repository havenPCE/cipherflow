package controller;

import bean.CipherBean;
import bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.SavedFileList;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;
import util.GeneratorClass;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField cpassword;

    @FXML
    private Label emailWarning;
    @FXML
    private Label usernameWarning;
    @FXML
    private Label firstnameWarning;
    @FXML
    private Label lastnameWarning;
    @FXML
    private Label passwordWarning;


    private StageManager stageManager;
    private User user;
    private UserPreferences userPreferences;
    private ListPreferences listPreferences;
    private UserBean userBean;
    private CipherBean cipherbean;
    private UserService userService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        userBean = UserBean.INSTANCE;
        cipherbean = CipherBean.INSTANCE;
        user = new User();
        userService = new UserServiceImplimentation();

    }

    @FXML
    public void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void min(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    public void back_to_login(MouseEvent event) throws IOException {
        stageManager.switchScene(FXMLView.LOGIN);
    }

    private boolean patternValidation(String text, String pattern) {
        return !(text.matches(pattern));
    }


    public void username_keyreleased(KeyEvent keyEvent) {
        if (patternValidation(username.getText(), "[A-z a-z 0-9]+"))
            usernameWarning.setText(" letters & numbers are allowed");
        else
            usernameWarning.setText(null);
    }

    public void lastname_keyreleased(KeyEvent keyEvent) {
        if (patternValidation(lastname.getText(), "[A-Z a-z]+"))
            lastnameWarning.setText("only letters are allowed");
        else
            lastnameWarning.setText(null);
    }

    public void firstname_keyreleased(KeyEvent keyEvent) {
        if (patternValidation(firstname.getText(), "[A-Z a-z]+"))
            firstnameWarning.setText("only letters are allowed");
        else
            firstnameWarning.setText(null);
    }

    public void email_keyreleased(KeyEvent keyEvent) {
        if (patternValidation(email.getText(), "^[a-z A-Z 0-9]{0,30}[@][a-z A-Z 0-9]{0,10}[.][a-z A-Z]{2,5}$"))
            emailWarning.setText("Incorrect Email");
        else
            emailWarning.setText(null);
    }


    public void password_pressed(MouseEvent event) {
        if (patternValidation(password.getText(), "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!^&@#$%]).{8,20})")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain atleast one(Digit,Lowercase,Uppercase,and special character)and length should be atleast 8 characters");
            alert.showAndWait();
        }
    }

    public void password_keyreleased(KeyEvent keyEvent) {
        if (patternValidation(password.getText(), "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!^&@#$%]).{8,20})"))
            passwordWarning.setText("Incorrect Password");
        else
            passwordWarning.setText(null);
    }

    private boolean inputValidation() {

        if ((usernameWarning.getText() != null) ||
                (firstnameWarning.getText() != null) ||
                (lastnameWarning.getText() != null) ||
                (emailWarning.getText() != null) ||
                (passwordWarning.getText() != null)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incorrect Details");
            alert.setHeaderText(null);
            alert.setContentText("Fill Correct Details");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private boolean emptyValidation() {
        String Password = password.getText();
        String CPassword = cpassword.getText();
        String Email = email.getText();
        String Uname = username.getText();
        String Lname = lastname.getText();
        String Fname = firstname.getText();
        if (Uname.equals("") || Email.equals("") || Fname.equals("") || Lname.equals("") || Password.equals("") || CPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Above fields can't be left blank ");
            return true;
        }

        return false;
    }

    public void Reset(MouseEvent event) {
        username.clear();
        firstname.clear();
        lastname.clear();
        password.clear();
        cpassword.clear();
        email.clear();

    }

    private boolean validateInputs() {
        boolean result;
        if (emptyValidation()) {
            return false;
        } else if (inputValidation()) {
            return false;
        } else {
            if (!cpassword.getText().equals(password.getText())) {
                JOptionPane.showMessageDialog(null, " Password not matched ");
                return false;
            } else
                result = true;
        }

        return result;
    }

    private void setUserInformation() throws NoSuchPaddingException, NoSuchAlgorithmException {
        user.setUserId(username.getText());
        user.setFirstName(firstname.getText());
        user.setLastName(lastname.getText());
        user.setEmail(email.getText());
        user.setSecretKey(GeneratorClass.generate());
        user.setIvKey(GeneratorClass.generate());
        user.setSalt(GeneratorClass.generate());
        cipherbean.setParameters(user.getSecretKey(), user.getIvKey(), user.getSalt());
        user.setPassword(cipherbean.getSecurePassword(password.getText()));
    }

    private void setLocalData() {
        userBean.setUserID(user.getUserId());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setEmail(user.getEmail());
        userPreferences.setUserID(user.getUserId());
        SavedFileList eFileList = listPreferences.getList();
        if (eFileList == null) {
            userBean.setFileList(new SavedFileList());
        } else
            userBean.setFileList(eFileList);

    }

    public void SignUp(MouseEvent event) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        if (validateInputs()) {
            if (userService.getUser(username.getText()) == null) {
                setUserInformation();
                userService.createUser(user);
                userPreferences.setUserID(user.getUserId());
                setLocalData();
                System.out.println(user.toString());
                stageManager.switchScene(FXMLView.LOGIN);
            } else {
                usernameWarning.setText("username already exists");
            }
        }
    }


}

















