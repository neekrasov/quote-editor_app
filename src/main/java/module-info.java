module com.example.db_course_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.app to javafx.fxml;
    exports com.app;
//    exports com.app.controllers;
//    opens  com.app.controllers to javafx.fxml;
    opens com.app.database.models to javafx.base;
    exports com.app.controllers.Profile;
    opens com.app.controllers.Profile to javafx.fxml;
    exports com.app.controllers.Auth;
    opens com.app.controllers.Auth to javafx.fxml;
    exports com.app.controllers.EditQuotes;
    opens com.app.controllers.EditQuotes to javafx.fxml;
}