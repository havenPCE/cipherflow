
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.StageManager;
import view.FXMLView;

import javax.swing.JOptionPane;


public class RegistrationController implements Initializable {
    
    
    @FXML
    private AnchorPane pane ;
    @FXML
     private AnchorPane content_area;
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
    private Label emaill;
   @FXML
    private Label pass;
  @FXML
    private Label conf;

  private StageManager stageManager;
 
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        stageManager = StageManager.INSTANCE;
        
    }   
    public void open_login(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("UI.fxml"));
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
      @FXML
     public void reset_page(MouseEvent event) throws IOException{
        stageManager.switchScene(FXMLView.REGISTRATION);
    }
       @FXML
     public void back_to_login(MouseEvent event)throws IOException{
        stageManager.switchScene(FXMLView.LOGIN);
     }
    
     

   
  public void email_keyReleased(){
      String PATTERN="^[a-z A-Z 0-9]{0,30}[@][a-z A-Z 0-9]{0,10}[.][a-z A-Z]{2,5}$";
      Pattern patt=Pattern.compile(PATTERN);
      Matcher match=patt.matcher(email.getText());
  
      if(!match.matches()){
          emaill.setText("incorrect");
      }
      else{
          emaill.setText(null);
      }}

  
     public void open_home(MouseEvent event)throws IOException{
         // public void pname(MouseEvent event)throws IOException{
        String Password=password.getText();
        password.setText(Password);
         String Password1=cpassword.getText();
         cpassword.setText(Password1);
         String Email=email.getText(); 
        String Uname=username.getText();
          String Lname=lastname.getText();
        String Fname=firstname.getText();
        Pattern p=Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!^&@#$%]).{8,20})");
      Matcher m=p.matcher(password.getText());
    if(!Uname.equals("")&&!Email.equals("")&&!Fname.equals("")&&!Lname.equals("")&&!Password.equals("")&&!Password1.equals("")&&m.matches()&&Password.equals(Password1)){
      Parent fxml = FXMLLoader.load(getClass().getResource("List.fxml"));
             
             content_area.getChildren().removeAll();
             content_area.getChildren().setAll(fxml);}
     else if(!m.matches()){
          Alert alert= new Alert(AlertType.WARNING);
          alert.setTitle("Validate Password");
          alert.setHeaderText(null);
          alert.setContentText("Password must contain atleast one(Digit,Lowercase,Uppercase,and special character)and length should be atleast 8 characters");
          alert.showAndWait();
      
      }
      else if(!Password.equals(Password1)){
        JOptionPane.showMessageDialog(null," Password not matched ");     
        } 
   
    
    else if(Uname.equals("")||Email.equals("")||Fname.equals("")||Lname.equals("")||Password.equals("")||Password1.equals("")){
              JOptionPane.showMessageDialog(null,"Above fields can't be left blank "); 
         }
     
     
    
    
          
     }   
         
         
     
}



   
    
     
     
    