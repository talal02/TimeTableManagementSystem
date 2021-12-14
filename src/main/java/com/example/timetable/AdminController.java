package com.example.timetable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class AdminController implements Initializable {

    @FXML
    private TextField cId;

    @FXML
    private TextField cName;

    @FXML
    private TextField cSection;

    @FXML
    private TextField creditHrs;

    @FXML
    private ChoiceBox<String> showCourses;

    @FXML
    private Label courseValidator;

    @FXML
    private Label teacherValidator;

    @FXML
    private ChoiceBox<String> tCourse;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tName;

    @FXML
    private TextField tPss;

    @FXML
    private ChoiceBox<String> tSection;

    @FXML
    private ChoiceBox<String> showTeachers;

    @FXML
    private TextField classroomId;

    @FXML
    private Label classroomValidator;

    @FXML
    private ChoiceBox<String> showClassrooms;

    @FXML
    private RadioButton isLab;

    @FXML
    private RadioButton isClass;

    @FXML
    private Label timetableValidator;

    @FXML
    private ChoiceBox<String> showDays;

    @FXML
    private ChoiceBox<String> showDays1;

    @FXML
    private ChoiceBox<String> showSlots;

    @FXML
    private ChoiceBox<String> showSlots1;

    private Database db;

    private static int option = 0;

    public AdminController() throws SQLException, ClassNotFoundException {
        db = new Database();
    }

    @FXML
    void classPressed(ActionEvent event) {
        if(isClass.selectedProperty().asObject().getValue()) {
            isLab.selectedProperty().asObject().setValue(false);
        }
    }

    @FXML
    void labPressed(ActionEvent event) {
        if(isLab.selectedProperty().asObject().getValue()) {
            isClass.selectedProperty().asObject().setValue(false);
        }
    }

    @FXML
    void addClassroom(ActionEvent event) {
        if(!Objects.equals(classroomId.getText(), "")) {
            Vector<Classroom> classrooms = Application.getClassrooms();
            for(Classroom c : classrooms) {
                if(Objects.equals(c.getClassroomId(), classroomId.getText())) {
                    classroomValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                    classroomValidator.setText("Lab/Classroom Already Exists...");
                    return;
                }
            }
            if(isClass.selectedProperty().asObject().getValue()) {
                db.addClassroom(classroomId.getText(), "Class", 1);
                classroomValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                classroomValidator.setText("Classroom Added...");
                initialize(null, null);
            } else if(isLab.selectedProperty().asObject().getValue()) {
                db.addClassroom(classroomId.getText(), "Lab", 1);
                classroomValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                classroomValidator.setText("Lab Added...");
                initialize(null, null);
            } else {
                classroomValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                classroomValidator.setText("Kindly Select Lab or Class...");
            }
        } else {
            classroomValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
            classroomValidator.setText("Classroom Id Can't Be Empty...");
        }
    }

    @FXML
    void addCourse(ActionEvent event) {
        if(!Objects.equals(cName.getText(), "") && !Objects.equals(cSection.getText(), "") && !Objects.equals(creditHrs.getText(), "") && !Objects.equals(cId.getText(), "")) {
            Vector<Course> courses = Application.getCourses();
            for(Course c : courses) {
                if(Objects.equals(c.getCourseId(), cId.getText())){
                    courseValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                    courseValidator.setText("Course Already Exists");
                    return;
                }
            }
            if(db.addCourse(cId.getText(), cName.getText(), Integer.parseInt(creditHrs.getText()), cSection.getText())) {
                courseValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                courseValidator.setText("Course Added");
                initialize(null, null);
            } else {
                courseValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                courseValidator.setText("Course Addition Failed...");
            }
        } else {
            courseValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
            courseValidator.setText("Input Fields Can't be Empty...");
        }
    }

    @FXML
    void backAdmin(ActionEvent event) throws IOException {
        Application.changeScene("admin-page.fxml", "Admin Panel", 773, 423);
    }

    @FXML
    void removeCourse(ActionEvent event) {
        if(!Objects.equals(showCourses.getValue(), "")) {
            Vector<Course> courses = Application.getCourses();
            String id = "";
            for (int i = 0; i < courses.size(); i++) {
                if (Objects.equals(courses.get(i).courseId, cId.getText())) {
                    id = courses.get(i).courseId;
                }
            }
            if (db.removeCourse(id)) {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Course Removed");
                initialize(null,null);
            } else {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Course Removal Failed...");
            }
        } else {
            teacherValidator.setStyle("-fx-text-fill: red");
            teacherValidator.setText("No Course selected...");
        }
    }

    @FXML
    void removeClassroom(ActionEvent event) {
        if(!Objects.equals(showClassrooms.getValue(), "")) {
            Vector<Classroom> classrooms = Application.getClassrooms();
            String id = "";
            for (int i = 0; i < classrooms.size(); i++) {
                if (Objects.equals(classrooms.get(i).toString(), showClassrooms.getValue())) {
                    id = classrooms.get(i).classroomId;
                }
            }
            if (db.removeClassroom(String.valueOf(id))) {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Classroom Removed");
                initialize(null,null);
            } else {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Classroom Removal Failed...");
            }
        } else {
            teacherValidator.setStyle("-fx-text-fill: red");
            teacherValidator.setText("No Classroom selected...");
        }
    }

    @FXML
    void addLecture(ActionEvent event) {

    }


    @FXML
    void removeLecture(ActionEvent event) {

    }


    @FXML
    void addTeacher(ActionEvent event) {
        if(!Objects.equals(tName.getText(), "") && !Objects.equals(tEmail.getText(), "") && !Objects.equals(tPss.getText(), "") && !Objects.equals(tCourse.getValue(), "") && !Objects.equals(tSection.getValue(), "")) {
            Vector<Teacher> teachers = Application.getTeachers();
            for(int i = 0; i < teachers.size(); i++) {
                if(Objects.equals(teachers.get(i).email, tEmail.getText())) {
                    if(teachers.get(i).exists(tCourse.getValue(), tSection.getValue())) {
                        teacherValidator.setStyle("-fx-text-fill: red");
                        teacherValidator.setText("Teacher Already Exists...");
                        return;
                    }
                }
            }
            int id = 0;
            if(Application.getTeachers().size() > 0) {
                id = Application.getTeachers().size();
            }
            if(db.addTeacher(""+id, tName.getText(), tEmail.getText(), tPss.getText(), tCourse.getValue(), tSection.getValue())) {
                teacherValidator.setStyle("-fx-text-fill: green");
                teacherValidator.setText("Teacher Added");
                initialize(null, null);
            } else {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Teacher Addition Failed...");
            }
        }
    }

    @FXML
    void removeTeacher(ActionEvent event) {
        if(!Objects.equals(showTeachers.getValue(), "")) {
            Vector<Teacher> teachers = Application.getTeachers();
            int id = 0;
            for (int i = 0; i < teachers.size(); i++) {
                if (Objects.equals(teachers.get(i).email, tEmail.getText())) {
                    id = i;
                }
            }
            if (db.removeTeacher(String.valueOf(id))) {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Teacher Removed");
                initialize(null,null);
            } else {
                teacherValidator.setStyle("-fx-text-fill: red");
                teacherValidator.setText("Teacher Removal Failed...");
            }
        } else {
            teacherValidator.setStyle("-fx-text-fill: red");
            teacherValidator.setText("No Teacher selected...");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Vector<Course> courses = Application.getCourses();
        if(showCourses != null) {
            showCourses.getItems().clear();
            for(int i = 0; i < courses.size(); i++) {
                showCourses.getItems().addAll(courses.get(i).getCourseName());
            }
        }
        if(tCourse != null) {
            tSection.disableProperty().asObject().setValue(true);
            tCourse.getItems().clear();
            for(int i = 0; i < courses.size(); i++) {
                tCourse.getItems().addAll(courses.get(i).getCourseId());
            }
            tCourse.valueProperty().addListener((ov, t, t1) -> {
                if(!Objects.equals(t1, "")) {
                    option = 1;
                    tSection.disableProperty().asObject().setValue(false);
                    tSection.getItems().clear();
                    for(int i = 0; i < courses.size(); i++) {
                        if(Objects.equals(courses.get(i).getCourseId(), tCourse.getValue())) {
                            Vector<String> sections = courses.get(i).getSections();
                            tSection.getItems().addAll(sections);
                        }
                    }
                }
            });
        }
        if(showTeachers != null) {
            showTeachers.getItems().clear();
            Vector<Teacher> teachers = Application.getTeachers();
            for(Teacher t : teachers) {
                showTeachers.getItems().addAll(t.getName());
            }
        }
        if(showClassrooms != null) {
            showClassrooms.getItems().clear();
            Vector<Classroom> classrooms = Application.getClassrooms();
            for(Classroom c : classrooms) {
                showClassrooms.getItems().addAll(c.classroomId + " | " + c.type);
            }
        }
    }
}
