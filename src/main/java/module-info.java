module com.example.timetable {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.timetable to javafx.fxml;
    exports com.timetable;
    exports com.database;
    opens com.database to javafx.fxml;
    exports com.base;
    opens com.base to javafx.fxml;
}