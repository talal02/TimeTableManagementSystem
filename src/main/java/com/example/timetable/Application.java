package com.example.timetable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

public class Application extends javafx.application.Application {
    private static Stage s;
    private static Vector<Admin> admins;
    private static Vector<Student> students;
    private static Vector<Course> courses;
    private static Vector<Teacher> teachers;
    private static Vector<Classroom> classrooms;

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        s = stage;
        initialize();
        s.resizableProperty().asObject().setValue(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 773, 423);
        stage.setTitle("Time Table Management System!");
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        Database db = new Database();
        admins = db.getAdmins();
        students = db.getStudents();
        courses = db.getCourses();
        teachers = db.getTeachers();
        classrooms = db.getClassrooms();

        for(Admin a : admins) {
            System.out.println(a.getEmail());
        }

        for(Student s: students) {
            System.out.println(s.getEmail());
        }

        for(Course c: courses) {
            System.out.println(c.getCourseId());
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String fxml, String Title, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        s.setTitle(Title);
        s.setScene(new Scene(fxmlLoader.load(), width, height));
        s.show();
    }

    public static Vector<Admin> getAdmins() {
        return admins;
    }

    public static void setAdmins(Vector<Admin> admins) {
        Application.admins = admins;
    }

    public static Vector<Student> getStudents() {
        return students;
    }

    public static void setStudents(Vector<Student> students) {
        Application.students = students;
    }

    public static Vector<Course> getCourses() {
        return courses;
    }

    public static void setCourses(Vector<Course> courses) {
        Application.courses = courses;
    }

    public static Vector<Teacher> getTeachers() {
        return teachers;
    }

    public static void setTeachers(Vector<Teacher> teachers) {
        Application.teachers = teachers;
    }

    public static Vector<Classroom> getClassrooms() {
        return classrooms;
    }

    public static void setClassrooms(Vector<Classroom> classrooms) {
        Application.classrooms = classrooms;
    }
}