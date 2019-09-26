package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondController implements Initializable {

    @FXML
    public Button saveButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button secondButton;
    @FXML
    private TextField userInput;
    private StageManager stageManager;
    private UserPreferences preferences;

    public void goToPast(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.FIRST);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        preferences = UserPreferences.INSTANCE;
    }

    public void saveInfo(ActionEvent actionEvent) {
        preferences.setUserID(userInput.getText());
    }

    public void deleteInfo(ActionEvent actionEvent) {
        preferences.removeUserID();
    }
}
