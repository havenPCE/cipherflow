package view;

/*
 * This class contains references to fxml resources
 * when a new fxml file is created, it must be referenced here
 * */

public enum FXMLView {
    FIRST {
        public String getTitle() {
            return "Welcome to the app!";
        }

        public String getFxmlFile() {
            return "fxml/first.fxml";
        }
    },

    SECOND {
        public String getTitle() {
            return "Second Screen";
        }

        public String getFxmlFile() {
            return "fxml/second.fxml";
        }
    };

    //returns the title to display on screen
    public abstract String getTitle();

    //return the file path to use for fxml loader
    public abstract String getFxmlFile();
}

