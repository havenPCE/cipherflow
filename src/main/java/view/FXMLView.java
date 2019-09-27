package view;

/*
 * This class contains references to fxml resources
 * when a new fxml file is created, it must be referenced here
 * */

public enum FXMLView {

    LOGIN {
        @Override
        public String getTitle(){
            return "Welcome to the login screen";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/Login.fxml";
        }
    };

    //returns the title to display on screen
    public abstract String getTitle();

    //return the file path to use for fxml loader
    public abstract String getFxmlFile();
}

