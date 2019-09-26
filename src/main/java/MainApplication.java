import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.ListPreferences;
import util.StageManager;
import util.UserPreferences;
import view.FXMLView;
import util.ListPreferences;

import java.io.IOException;

public class MainApplication extends Application {

    private StageManager stageManager;
    private ListPreferences listPreferences;
    private UserPreferences userPreferences;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageManager.setPrimaryStage(primaryStage);
        stageManager.setLoader(new FXMLLoader());
        showInitialScene();
    }

    private void showInitialScene() throws IOException {
        if (userPreferences.getUserID() != null) {
            stageManager.switchScene(FXMLView.FIRST);
        } else stageManager.switchScene(FXMLView.SECOND);
    }

    //Here is all the beans initialized
    public void init() {
        stageManager = StageManager.INSTANCE;
        userPreferences = UserPreferences.INSTANCE;
        listPreferences = ListPreferences.INSTANCE;
        listPreferences.setListPreferences();
        userPreferences.setUserPreferences();
    }
}
