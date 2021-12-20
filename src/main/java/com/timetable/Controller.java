package com.timetable;

import com.database.Database;
import com.database.PersistenceHandler;
import com.database.TimeTableFile;
import com.exceptions.IncorrectCredentialsException;
import com.exceptions.IncorrectOptionException;
import com.exceptions.InvalidDataException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {

    @FXML
    private Text sNotification;

    @FXML
    private TextField email;

    @FXML
    private RadioButton isAdmin;

    @FXML
    private RadioButton isStudent;

    @FXML
    private RadioButton isTeacher;

    @FXML
    private TextField password;

    @FXML
    private Label loginMsg;

    private PersistenceHandler db;
    
    public Controller() throws SQLException, ClassNotFoundException {
        if(Application.dbOption == 1) {
            db = Database.getInstance();
        } else if(Application.dbOption == 2) {
            db = TimeTableFile.getInstance();
        }
    }

    @FXML
    void adminPressed(ActionEvent event) {
        if(isAdmin.selectedProperty().asObject().getValue()) {
            isStudent.selectedProperty().asObject().setValue(false);
            isTeacher.selectedProperty().asObject().setValue(false);
        }
    }

    @FXML
    void studentPressed(ActionEvent event) {
        if(isStudent.selectedProperty().asObject().getValue()) {
            isAdmin.selectedProperty().asObject().setValue(false);
            isTeacher.selectedProperty().asObject().setValue(false);
        }
    }

    @FXML
    void teacherPressed(ActionEvent event) {
        if(isTeacher.selectedProperty().asObject().getValue()) {
            isStudent.selectedProperty().asObject().setValue(false);
            isAdmin.selectedProperty().asObject().setValue(false);
        }
    }

    @FXML
    void manageClassrooms(ActionEvent event) throws IOException {
        Application.changeScene("manage-classrooms.fxml", "Manage Classrooms", 773, 423);
    }

    @FXML
    void manageCourses(ActionEvent event) throws IOException {
        Application.changeScene("manage-courses.fxml", "Manage Courses", 773, 423);
    }

    @FXML
    void manageTimeTable(ActionEvent event) throws IOException {
        Application.changeScene("manage-timetable.fxml", "Manage TimeTable", 1000, 640);
    }

    @FXML
    void manageTeachers(ActionEvent event) throws IOException {
        Application.changeScene("manage-teachers.fxml", "Manage Teachers", 900, 500);
    }

    @FXML
    void manageStudents(ActionEvent event) throws IOException {
        Application.changeScene("manage-students.fxml", "Manage Students", 850, 470);
    }

    @FXML
    void viewTimeTable(ActionEvent event) throws IOException {
        if(Application.getCurrentStudent() != null) {
            Application.changeScene("student-timetable.fxml", "Time Table", 1280, 720);
        } else if(Application.getCurrentTeacher() != null) {
            Application.changeScene("teacher-timetable.fxml", "Time Table", 1280, 720);
        } else {
            Application.changeScene("timetable.fxml", "Time Table", 1280, 720);
        }
    }

    @FXML
    void announceQuiz(ActionEvent event) throws IOException {
        Application.changeScene("announce-quiz.fxml", "Announce Quiz", 773, 423);
    }

    @FXML
    void cancelClass(ActionEvent event) throws IOException {
        Application.changeScene("teacher-cancel-class.fxml", "Class Cancel & Reschedule", 1000, 640);
    }

    @FXML
    void viewQuizes(ActionEvent event) throws IOException {
        Application.changeScene("view-quiz.fxml", "Quiz", 800, 610);
    }

    @FXML
    void showNotification(ActionEvent event) {
        sNotification.setText(Application.getCurrentStudent().getNotification());
    }

    @FXML
    void loginPressed(ActionEvent event) throws IOException {
        try {
            if(isAdmin.selectedProperty().asObject().getValue()) {
                if(db.loginAdmin(email.getText(), password.getText())) {
                    loginMsg.setStyle("-fx-text-fill: green");
                    loginMsg.setText("Login Successful...");
                    Application.changeScene("admin-page.fxml", "Admin Panel", 900, 540);
                } else {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Admin Doesn't Exists or pss incorrect...");
                    throw new IncorrectCredentialsException("Admin Doesn't Exists or pss incorrect");
                }
            } else if(isStudent.selectedProperty().asObject().getValue()) {
                if(db.loginStudent(email.getText(), password.getText())) {
                    loginMsg.setStyle("-fx-text-fill: green");
                    loginMsg.setText("Login Successful...");
                    Application.changeScene("student-page.fxml", "Admin Panel", 773, 423);
                } else {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Student Doesn't Exists or pss incorrect...");
                    throw new IncorrectCredentialsException("Student Doesn't Exists or pss incorrect");
                }
            } else if(isTeacher.selectedProperty().asObject().getValue()) {
                if(db.loginTeacher(email.getText(), password.getText())) {
                    loginMsg.setStyle("-fx-text-fill: green");
                    loginMsg.setText("Login Successful...");
                    Application.changeScene("teacher-page.fxml", "Admin Panel", 773, 423);
                } else {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Teacher Doesn't Exists or pss incorrect...");
                    throw new IncorrectCredentialsException("Teacher Doesn't Exists or pss incorrect");
                }
            } else {
                loginMsg.setStyle("-fx-text-fill: red");
                loginMsg.setText("Choose (Admin/Student/Teacher)");
                throw new IncorrectOptionException("Choose (Admin/Student/Teacher)");
            }
        } catch (IncorrectCredentialsException | IncorrectOptionException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void signupPressed(ActionEvent event) {
        if(isAdmin.selectedProperty().asObject().getValue()) {
            try {
                int id;
                if (Application.getAdmins().size() == 0) {
                    id = 0;
                } else {
                    id = Integer.parseInt(Application.getAdmins().get(Application.getAdmins().size() - 1).getId()) + 1;
                }
                if (db.loginAdmin(email.getText(), password.getText())) {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Account Already Exists...");
                    throw new InvalidDataException("Account Already Exists");
                } else if (db.addAdmin(email.getText(), password.getText(), "" + id)) {
                    loginMsg.setStyle("-fx-text-fill: green");
                    loginMsg.setText("Admin Account Created...");
                } else {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Account Creation Unsuccessful...");
                }
                if (isTeacher.selectedProperty().asObject().getValue()) {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Only Admins can add Teachers...");
                    throw new IncorrectOptionException("Only Admins can add Teachers");
                }
                if (isStudent.selectedProperty().asObject().getValue()) {
                    loginMsg.setStyle("-fx-text-fill: red");
                    loginMsg.setText("Only Admins can add Students...");
                    throw new IncorrectOptionException("Only Admins can add Studentss");
                }
            } catch (IncorrectOptionException | InvalidDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void logoutPressed(ActionEvent event) throws IOException {
        Application.setCurrentStudent(null);
        Application.setCurrentTeacher(null);
        Application.changeScene("login-page.fxml", "Main Page", 773, 423);
    }

}