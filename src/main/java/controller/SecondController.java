package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.ListPreferences;
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
    private UserPreferences userPreferences;
    private ListPreferences listPreferences;

    public void goToPast(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.FIRST);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
    }

    public void saveInfo(ActionEvent actionEvent) {
        userPreferences.setUserID(userInput.getText());
    }

    public void deleteInfo(ActionEvent actionEvent) {
        userPreferences.removeUserID();
    }
}
