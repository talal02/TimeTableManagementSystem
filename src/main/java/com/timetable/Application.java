package com.timetable;

import com.base.*;
import com.database.Database;
import com.database.PersistenceHandler;
import com.database.TimeTableFile;
import com.exceptions.IncorrectOptionException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Application extends javafx.application.Application {
    private static Stage s;
    private static Vector<Admin> admins;
    private static Vector<Student> students;
    private static Vector<Course> courses;
    private static Vector<Teacher> teachers;
    private static Vector<Classroom> classrooms;
    private static Vector<Lecture> lectures;
    private static Vector<Quiz> quizzes;
    private static Student currentStudent;
    private static Teacher currentTeacher;
    public static int dbOption = 0;

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

    public static void saveData() {
        try {
            FileWriter myWriter = new FileWriter("Admin.txt");
        for(Admin a : admins) {
            myWriter.write(a.toString());
        }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Classroom.txt");
            for(Classroom c : classrooms) {
                myWriter.write(c.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Course.txt");
            for(Course c : courses) {
                myWriter.write(c.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Teacher.txt");
            for(Teacher t : teachers) {
                myWriter.write(t.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Student.txt");
            for(Student s : students) {
                myWriter.write(s.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Quiz.txt");
            for(Quiz q : quizzes) {
                myWriter.write(q.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }

        try {
            FileWriter myWriter = new FileWriter("Lecture.txt");
            for(Lecture l : lectures) {
                myWriter.write(l.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }
    }

    public static void initialize() throws SQLException, ClassNotFoundException {
        PersistenceHandler db;
        if(dbOption == 1) {
            db = Database.getInstance();
        } else {
            db = TimeTableFile.getInstance();
        }
        admins = db.getAdmins();
        students = db.getStudents();
        courses = db.getCourses();
        teachers = db.getTeachers();
        classrooms = db.getClassrooms();
        lectures = db.getLectures();
        quizzes = db.getQuizzes();
    }

    public static void main(String[] args) throws IncorrectOptionException {
        int choice = 0;
        System.out.println("Press 1. Oracle\nPress 2. Local File\n");
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        if(choice == 1) {
            dbOption = 1;
        } else if(choice == 2) {
            dbOption = 2;
        } else {
            throw new IncorrectOptionException("Select Correct Option...");
        }
        launch();
        saveData();
    }

    public static void changeScene(String fxml, String Title, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        s.setTitle(Title);
        s.setScene(new Scene(fxmlLoader.load(), width, height));
        s.show();
    }

    public static void setTeachers(Vector<Teacher> teachers) {
        Application.teachers = teachers;
    }

    public static void setLectures(Vector<Lecture> lectures) {
        Application.lectures = lectures;
    }

    public static void setQuizzes(Vector<Quiz> quizzes) {
        Application.quizzes = quizzes;
    }

    public static void setCurrentStudent(Student currentStudent) {
        Application.currentStudent = currentStudent;
    }

    public static void setCurrentTeacher(Teacher currentTeacher) {
        Application.currentTeacher = currentTeacher;
    }

    public static Vector<Admin> getAdmins() {
        return admins;
    }

    public static Vector<Student> getStudents() {
        return students;
    }

    public static Vector<Course> getCourses() {
        return courses;
    }

    public static Vector<Teacher> getTeachers() {
        return teachers;
    }

    public static Vector<Classroom> getClassrooms() {
        return classrooms;
    }

    public static Vector<Lecture> getLectures() {
        return lectures;
    }

    public static Vector<Quiz> getQuizzes() {
        return quizzes;
    }

    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static Teacher getCurrentTeacher() {
        return currentTeacher;
    }
}