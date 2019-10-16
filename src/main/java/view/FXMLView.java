package view;

/*
 * This class contains references to fxml resources
 * when a new fxml file is created, it must be referenced here
 * */

public enum FXMLView {

    LOGIN {
        public String getTitle() {
            return "Login Screen";
        }

        public String getFxmlFile() {
            return "fxml/login.fxml";
        }
    },

    REGISTRATION {
        @Override
        public String getTitle() {
            return "Registration Screen";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/registration.fxml";
        }
    },

    MAIN {
        @Override
        public String getTitle() {
            return "CipherFlow";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/main.fxml";
        }
    },

    PROFILE {
        @Override
        public String getTitle() {
            return "Profile";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/profile.fxml";
        }
    },

    DASHBOARD {
        @Override
        public String getTitle() {
            return "Dashboard";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/dashboard.fxml";
        }
    },

    ABOUT {
        @Override
        public String getTitle() {
            return "About Us";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/about.fxml";
        }
    },

    LEARN {
        @Override
        public String getTitle() {
            return "Learn the App";
        }

        @Override
        public String getFxmlFile() {
            return "fxml/learn.fxml";
        }
    };

    //returns the title to display on screen
    public abstract String getTitle();

    //return the file path to use for fxml loader
    public abstract String getFxmlFile();
}

