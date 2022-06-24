module com.example.db_course_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.app to javafx.fxml;
    exports com.app;
    exports com.app.controllers;
    opens  com.app.controllers to javafx.fxml;
    opens com.app.database.models to javafx.base;
}