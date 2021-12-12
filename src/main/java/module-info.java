module com.example.timetable {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.example.timetable to javafx.fxml;
    exports com.example.timetable;
}