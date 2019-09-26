package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.EFile;
import model.EFileList;
import util.ListPreferences;
import util.StageManager;
import view.FXMLView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FirstController implements Initializable {

    @FXML
    public Button firstButton;
    @FXML
    public ListView<EFile> fileListView;
    private StageManager stageManager;
    private ObservableList<EFile> observableList;
    private ListPreferences listPreferences;
    private EFileList retrievedList;

    public void goToFuture(ActionEvent actionEvent) throws IOException {
        stageManager.switchScene(FXMLView.SECOND);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stageManager = StageManager.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        retrievedList = listPreferences.getList();
        assert retrievedList != null;
        observableList = FXCollections.observableArrayList(Objects.requireNonNull(retrievedList.getFiles()));
        fileListView.setItems(observableList);
        fileListView.setCellFactory(param -> new ListCell<EFile>() {
            @Override
            protected void updateItem(EFile item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getFileName() == null) {
                    setText(null);
                } else {
                    setText(item.getFileName());
                }
            }
        });
    }

    private void saveList() {
        observableList.addAll(new EFile("fifth.file", "/path/", new Date(System.currentTimeMillis())));
        List<EFile> files = new ArrayList<>(observableList);
        retrievedList.setFiles(files);
        listPreferences.setList(retrievedList);
    }
}
