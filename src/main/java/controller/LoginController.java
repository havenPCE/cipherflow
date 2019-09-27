package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {

   
    @FXML
    
    private AnchorPane content_area;
   
    @FXML
    private TextField username;
    
   @FXML
    private PasswordField password;
 
     @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    public void open_registration(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("RegistrationUI.fxml"));
        
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }
    
    
         @FXML
     public void close_app(MouseEvent event){
         System.exit(0);
    }
      @FXML
     public void min(MouseEvent event){
       Stage s =(Stage)((Node)event.getSource()).getScene().getWindow();
       s.setIconified(true);
    }
    // public void uname()throws IOException{
      
        
    // String Uname=username.getText();
    // if(Uname.equals("")){
     //  JOptionPane.showMessageDialog(null,"user name can't be left blank"); 
    // }
   
    
    // }
     @FXML
  public void open_home(MouseEvent event)throws IOException{
    
    String UNAME=username.getText();
     String Password=password.getText();
        password.setText(Password);
       Pattern p=Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!^&@#$%]).{8,20})");
      Matcher m=p.matcher(password.getText());
      if(!UNAME.equals("")&&!Password.equals("")&&m.matches()){
       Parent fxml = FXMLLoader.load(getClass().getResource("List.fxml"));
        
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
   
      }
      else if(!m.matches()){
          Alert alert= new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Validate Password");
          alert.setHeaderText(null);
          alert.setContentText("Password must contain atleast one(Digit,Lowercase,Uppercase,and special character)and length should be atleast 8 characters");
          alert.showAndWait();
      
      }
      
       else if(UNAME.equals("")||Password.equals("")){
              JOptionPane.showMessageDialog(null,"Username can't be left blank "); 
      
  
  
  }
  
  
  }
}
