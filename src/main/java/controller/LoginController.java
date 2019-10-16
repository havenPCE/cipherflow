package controller;

import bean.Cipherbean;
import bean.UserBean;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.EFileList;
import model.User;
import service.UserService;
import service.UserServiceImplimentation;
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

public class LoginController implements Initializable {


    @FXML
    public AnchorPane content_area;
    @FXML
    public TextField username;
    @FXML
    public  PasswordField password;
    @FXML
    private JFXCheckBox rememberBox;

    private Alert alert = new Alert(Alert.AlertType.WARNING);


    private StageManager stageManager;
    private Cipherbean cipherbean;
    private UserBean userBean;
    private User user;
    private UserService userService;
    private ListPreferences listPreferences;
    private UserPreferences userPreferences;

    public void open_registration(MouseEvent event) throws IOException {
        stageManager.switchScene(FXMLView.REGISTRATION);
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stageManager = StageManager.INSTANCE;
        userBean=UserBean.INSTANCE;
        cipherbean=Cipherbean.INSTANCE;
        listPreferences=ListPreferences.INSTANCE;
        userPreferences=UserPreferences.INSTANCE;
        userService=new UserServiceImplimentation();
    }

    public void Login(MouseEvent event) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        String Username = username.getText();
        String Password = password.getText();
        boolean remember=rememberBox.isSelected();
        if(emptyValidation(Username,Password)){
            if(authenticate(Username,Password)){
                setUserInfo(remember);
                stageManager.switchScene(FXMLView.REGISTRATION);
            }
        }

    }

    private boolean emptyValidation(String username, String password)
    {
        if (username.equals("") || password.equals("") ) {
            JOptionPane.showMessageDialog(null, "Above fields can't be left blank ");
            return false;
        }
        return true;
    }
    private boolean authenticate(String username,String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
       boolean result=false;
        User u= userService.getUser(username) ;
       if(u==null){
           alert.setTitle("Login Details");
           alert.setHeaderText(null);
           alert.setContentText("Username is not registered!  Please sign up");
           alert.showAndWait();
       }
       else{
           cipherbean.setParameters(u.getSecretKey(),u.getIvKey(),u.getSalt());
           if(cipherbean.getSecurePassword(password).equals(u.getPassword())){
               user=u;
               result=true;
           }else {
               alert.setTitle("Login Details");
               alert.setHeaderText(null);
               alert.setContentText("Password is incorrect");
               alert.showAndWait();
           }
       }
       return result;
    }
    private  void setUserInfo(boolean isChecked) throws NoSuchPaddingException, NoSuchAlgorithmException {
      userBean.setUserID(user.getUserId());
      userBean.setFirstName(user.getFirstName());
      userBean.setLastName(user.getLastName());
      userBean.setEmail(user.getEmail());
      cipherbean.setParameters(user.getSecretKey(),user.getIvKey(),user.getSalt());
        EFileList eFileList=listPreferences.getList();
        if(eFileList==null) {
            userBean.setFileList(new EFileList());
        }else{
            userBean.setFileList(eFileList);
        }
        if(isChecked){
            userPreferences.setUserID(user.getUserId());
        }
    }

}









