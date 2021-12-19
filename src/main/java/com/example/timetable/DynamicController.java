package com.example.timetable;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class DynamicController implements Initializable {

    @FXML
    private TextField sCourses;

    @FXML
    private TextField sEmail;

    @FXML
    private TextField sName;

    @FXML
    private TextField sPss;

    @FXML
    private TextField sSection;

    @FXML
    private TextField selectSection;

    @FXML
    private ChoiceBox<String> showStudents;

    @FXML
    private Label studentValidator;

    @FXML
    private SVGPath next;

    @FXML
    private SVGPath prev;

    @FXML
    private Text dayLabel;

    @FXML
    private Text isStudent;

    @FXML
    private Text isAdmin;

    @FXML
    private Text isTeacher;

    @FXML
    private Label quizValidator;

    @FXML
    private TableColumn<Course, String> sCourseName;

    @FXML
    private TableView<Course> sCourseView;

    @FXML
    private TableView<Quiz> sQuizTable;

    @FXML
    private TableColumn<Quiz, String> sQuizTopic;

    @FXML
    private TableView<Classroom> t1;

    @FXML
    private TableView<Classroom> t01;

    @FXML
    private TableView<Lecture> t2;

    @FXML
    private TableView<Lecture> t3;

    @FXML
    private TableView<Lecture> t4;

    @FXML
    private TableView<Lecture> t5;

    @FXML
    private TableView<Lecture> t02;

    @FXML
    private TableView<Lecture> t03;

    @FXML
    private TableView<Lecture> t04;

    @FXML
    private TableView<Lecture> t05;

    @FXML
    private TableView<Lecture> t6;

    @FXML
    private TableView<Lecture> t7;

    @FXML
    private TableView<Lecture> t8;

    @FXML
    private TableView<Lecture> t9;

    @FXML
    private TableView<Lecture> labTable;

    @FXML
    private TableColumn<Classroom, String> classrooms;

    @FXML
    private TableColumn<Classroom, String> labs;

    @FXML
    private TableColumn<Lecture, String> slot01;

    @FXML
    private TableColumn<Lecture, String> slot02;

    @FXML
    private TableColumn<Lecture, String> slot03;

    @FXML
    private TableColumn<Lecture, String> slot04;

    @FXML
    private TableColumn<Lecture, String> slot1;

    @FXML
    private TableColumn<Lecture, String> slot2;

    @FXML
    private TableColumn<Lecture, String> slot3;

    @FXML
    private TableColumn<Lecture, String> slot4;

    @FXML
    private TableColumn<Lecture, String> slot5;

    @FXML
    private TableColumn<Lecture, String> slot6;

    @FXML
    private TableColumn<Lecture, String> slot7;

    @FXML
    private TableColumn<Lecture, String> slot8;

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
    private ChoiceBox<String> qCourse;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tName;

    @FXML
    private TextField tPss;

    @FXML
    private ChoiceBox<String> tSection;

    @FXML
    private ChoiceBox<String> qSection;

    @FXML
    private TextArea qTopic;

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

    public DynamicController() throws SQLException, ClassNotFoundException {
        db = new Database();
    }

    @FXML
    private Label teacherClassValidator;

    @FXML
    private ChoiceBox<String> teacherCourse;

    @FXML
    private ChoiceBox<String> teacherDays;

    @FXML
    private ChoiceBox<String> teacherSection;

    @FXML
    private ChoiceBox<String> teacherSlots;

    @FXML
    private ChoiceBox<String> teacherClassroom;

    private static int option2 = 0;
    private static String teacherKaCourse = "";
    private static String teacherKaSection = "";
    private static String teacherKaClassType = "";
    private static String IdOfLecture = "";

    @FXML
    void cancelClass(ActionEvent event) {
        if(!Objects.equals(teacherSection.getValue(), "") && !Objects.equals(teacherCourse.getValue(), "")) {
            option2 = 1;
            teacherKaCourse = teacherCourse.getValue();
            teacherKaSection = teacherSection.getValue();
            Vector<Lecture> lectures = Application.getLectures();
            for(Lecture l : lectures) {
                if(Objects.equals(l.getCourseId(), teacherKaCourse) && Objects.equals(l.getSection(), teacherKaSection)) {
                    db.removeLecture(l.getLectureId());
                    IdOfLecture = l.getLectureId();
                    teacherKaClassType = l.getSlot();
                    teacherClassValidator.setStyle("-fx-text-fill: green");
                    teacherClassValidator.setText("Class Canceled... You may reschedule it");
                    initialize(null, null);
                    return;
                }
                teacherClassValidator.setStyle("-fx-text-fill: red");
                teacherClassValidator.setText("No Course/Section exists...");
            }
        } else {
            teacherClassValidator.setStyle("-fx-text-fill: red");
            teacherClassValidator.setText("No Course/Section selected...");
        }
    }

    @FXML
    void rescheduleClass(ActionEvent event) {
        if(!Objects.equals(teacherSection.getValue(), "") && !Objects.equals(teacherCourse.getValue(), "")) {
            option2 = 2;
            if(db.addLecture(IdOfLecture, teacherDays.getValue(), teacherSlots.getValue(), teacherClassroom.getValue(), teacherKaSection, teacherKaCourse)) {
                teacherClassValidator.setStyle("-fx-text-fill: green");
                teacherClassValidator.setText("Class Rescheduled...");
                Vector<Student> students = Application.getStudents();
                for(Student s : students) {
                    if(Objects.equals(s.getSection(), teacherKaSection) && s.getCourseId().contains(teacherKaCourse)) {
                        s.setNotification("Class of " + teacherKaCourse + " has been rescheduled on " + teacherDays.getValue());
                    }
                }
            } else {
                teacherClassValidator.setStyle("-fx-text-fill: red");
                teacherClassValidator.setText("There was an issue...");
            }
        } else {
            teacherClassValidator.setStyle("-fx-text-fill: red");
            teacherClassValidator.setText("No Day/Class/Slot selected...");
        }
    }

    @FXML
    void classPressed(ActionEvent event) {
        if(isClass.selectedProperty().asObject().getValue()) {
            isLab.selectedProperty().asObject().setValue(false);
            initialize(null, null);
        }
    }

    @FXML
    void changeDay(MouseEvent event) {
        String mouseOn = event.getSource().toString();
        if(mouseOn.contains("next")) {
            option += 1;
            option = option%5;
        } else if(mouseOn.contains("prev")) {
            option -= 1;
            if(option < 0) {
                option = 4;
            }
        }
        initialize(null, null);
    }

    @FXML
    void labPressed(ActionEvent event) {
        if(isLab.selectedProperty().asObject().getValue()) {
            isClass.selectedProperty().asObject().setValue(false);
            initialize(null, null);
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
                if(db.addClassroom(classroomId.getText(), "Class", 1)) {
                    classroomValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                    classroomValidator.setText("Classroom Added...");
                    initialize(null, null);
                }
            } else if(isLab.selectedProperty().asObject().getValue()) {
                if(db.addClassroom(classroomId.getText(), "Lab", 1)){
                    classroomValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                    classroomValidator.setText("Lab Added...");
                    initialize(null, null);
                }
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
        Application.changeScene("admin-page.fxml", "Admin Panel", 900, 540);
    }

    @FXML
    void backStudent(ActionEvent event) throws IOException {
        Application.changeScene("student-page.fxml", "Student Panel", 773, 423);
    }

    @FXML
    void removeCourse(ActionEvent event) {
        if(!Objects.equals(showCourses.getValue(), "")) {
            Vector<Course> courses = Application.getCourses();
            for (Course cours : courses) {
                if (Objects.equals(cours.getCourseName(), showCourses.getValue())) {
                    if(db.removeCourse(cours.getCourseId())) {
                        courseValidator.setStyle("-fx-text-fill: green");
                        courseValidator.setText("Course Removed");
                        initialize(null,null);
                    } else {
                        courseValidator.setStyle("-fx-text-fill: red");
                        courseValidator.setText("Course Removal Failed...");
                    }
                    return;
                }
            }
            courseValidator.setStyle("-fx-text-fill: red");
            courseValidator.setText("Course doesn't exists...");
        } else {
            courseValidator.setStyle("-fx-text-fill: red");
            courseValidator.setText("No Course selected...");
        }
    }

    @FXML
    void removeClassroom(ActionEvent event) {
        if(!Objects.equals(showClassrooms.getValue(), "")) {
            Vector<Classroom> classrooms = Application.getClassrooms();
            for (Classroom c : classrooms) {
                String[] classroomValues = showClassrooms.getValue().split("@");
                System.out.println(classroomValues[0]);
                if (Objects.equals(c.getClassroomId(), classroomValues[0])) {
                    if(db.removeClassroom(c.getClassroomId())) {
                        classroomValidator.setStyle("-fx-text-fill: green");
                        classroomValidator.setText("Classroom Removed");
                        initialize(null,null);
                    } else {
                        classroomValidator.setStyle("-fx-text-fill: red");
                        classroomValidator.setText("Classroom Removal Failed...");
                    }
                    return;
                }
            }
            classroomValidator.setStyle("-fx-text-fill: red");
            classroomValidator.setText("Classroom doesn't exists...");
        } else {
            classroomValidator.setStyle("-fx-text-fill: red");
            classroomValidator.setText("No Classroom selected...");
        }
    }

    @FXML
    void addLecture(ActionEvent event) {
        if(!Objects.equals(showDays.getValue(), "") && !Objects.equals(showSlots.getValue(), "") && !Objects.equals(showClassrooms.getValue(), "") && !Objects.equals(tCourse.getValue(), "") && !Objects.equals(tSection.getValue(), "")) {
            if(isLab.selectedProperty().asObject().getValue() || isClass.selectedProperty().asObject().getValue()) {
                Vector<Lecture> lectures = Application.getLectures();
                int id = 0;
                for(Lecture l : lectures) {
                    if(Objects.equals(l.getClassroomId(), showClassrooms.getValue()) && Objects.equals(l.getDay(), showDays.getValue()) && Objects.equals(l.getSlot(), showSlots.getValue())) {
                        timetableValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                        timetableValidator.setText("Slot isn't empty...");
                        return;
                    }
                    if(Integer.parseInt(l.getLectureId()) > id) {
                        id = Integer.parseInt(l.getLectureId());
                    }
                }
                if(lectures.size() == 0 || (lectures.size() == 1 && id != 1)){
                    id = lectures.size();
                } else {
                    id += 1;
                }
                if(db.addLecture(""+id, showDays.getValue(), showSlots.getValue(), showClassrooms.getValue(), tSection.getValue(), tCourse.getValue())) {
                initialize(null, null);
                showSlots.getItems().clear();
                timetableValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                timetableValidator.setText("Lecture Added...");
                }
            } else {
                timetableValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                timetableValidator.setText("Select Class or Lab...");
            }
        } else {
            timetableValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
            timetableValidator.setText("Input Fields Can't be Empty...");
        }
    }


    @FXML
    void removeLecture(ActionEvent event) {
        if(!Objects.equals(showSlots1.getValue(), "") && !Objects.equals(showDays1.getValue(), "")) {
            Vector<Lecture> lectures = Application.getLectures();
            for (Lecture l : lectures) {
                if (Objects.equals(l.getSlot(), showSlots1.getValue()) && Objects.equals(l.getDay(), showDays1.getValue())) {
                    if(db.removeLecture(l.getLectureId())) {
                        timetableValidator.setStyle("-fx-text-fill: green");
                        timetableValidator.setText("Lecture Removed");
                        initialize(null,null);
                    } else {
                        timetableValidator.setStyle("-fx-text-fill: red");
                        timetableValidator.setText("Lecture Removal Failed...");
                    }
                    return;
                }
            }
            timetableValidator.setStyle("-fx-text-fill: red");
            timetableValidator.setText("Lecture doesn't exists...");
        } else {
            timetableValidator.setStyle("-fx-text-fill: red");
            timetableValidator.setText("No Lecture selected...");
        }
    }

    @FXML
    void announce(ActionEvent event) {
        if(!Objects.equals(qCourse.getValue(), "") && !Objects.equals(qSection.getValue(), "") && !Objects.equals(qTopic.getText(), "")) {
            Vector<Quiz> quizzes = Application.getQuizzes();
            int quizId = 0;
            boolean found = false;
            for(Quiz q : quizzes) {
                if(Objects.equals(q.getCourseId(), qCourse.getValue()) && Objects.equals(q.getSection(), qSection.getValue())) {
                    quizId = q.getQuizId();
                    found = true;
                    break;
                }
            }
            if(!found) {
                if(quizzes.size() == 0 || quizzes.size() == 1) {
                    quizId = quizzes.size();
                } else {
                    quizId += 1;
                }
            }
            if(db.addQuiz(quizId, qCourse.getValue(), qSection.getValue(), qTopic.getText())) {
                quizValidator.setText("Quiz Added");
                Vector<Student> students = Application.getStudents();
                for(Student s : students) {
                    if(Objects.equals(s.getSection(), qSection.getValue()) && s.getCourseId().contains(qCourse.getValue())) {
                        s.setNotification("Quiz Announced of Course (" + qCourse.getValue() + "), Topic (" + qTopic.getText() + ")");
                    }
                }
                quizValidator.setStyle("-fx-background-color: white; -fx-text-fill: green");
                quizValidator.setText("Quiz Added");
            } else {
                quizValidator.setStyle("-fx-background-color: white; -fx-text-fill: red");
                quizValidator.setText("Error can't add quiz");
            }
        } else {
            quizValidator.setStyle("-fx-background-color: white; -fx-text-fill: red");
            quizValidator.setText("Input fields shouldn't be empty...");
        }
    }

    @FXML
    void backTeacher(ActionEvent event) throws IOException {
        if(option2 == 0 || option2 == 2) {
            Application.changeScene("teacher-page.fxml", "Teacher Panel", 773, 423);
        }
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
            for (Teacher t : teachers) {
                if (Objects.equals(t.getName(), showTeachers.getValue())) {
                    if(db.removeTeacher(t.email)) {
                        teacherValidator.setStyle("-fx-text-fill: green");
                        teacherValidator.setText("Teacher Removed");
                        initialize(null,null);
                    } else {
                        teacherValidator.setStyle("-fx-text-fill: red");
                        teacherValidator.setText("Teacher Removal Failed...");
                    }
                    return;
                }
            }
            teacherValidator.setStyle("-fx-text-fill: red");
            teacherValidator.setText("Teacher doesn't exists...");
        } else {
            teacherValidator.setStyle("-fx-text-fill: red");
            teacherValidator.setText("No Teacher selected...");
        }
    }

    private void initialize_slots1(String Day) {
        if(showSlots1 != null) {
            Vector<Lecture> lectures = Application.getLectures();
            showSlots1.getItems().clear();
            for(Lecture l : lectures) {
                if(Objects.equals(l.getDay(), Day)) {
                    showSlots1.getItems().add(l.getSlot());
                }
            }
        }
    }

    private void initialize_slots(Vector<String> nv_slots) {
        if(showSlots != null) {
            if (isLab.selectedProperty().asObject().getValue() || isClass.selectedProperty().asObject().getValue()) {
                if(showClassrooms.getValue() == null || showDays.getValue() == null) {
                    timetableValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                    timetableValidator.setText("Select Classroom & Day First...");
                } else {
                    showSlots.getItems().clear();
                    showSlots1.getItems().clear();
                    Vector<String> slots = db.getSlots(isClass.selectedProperty().asObject().getValue() ? "Class" : "Lab");
                    for(String s : slots) {
                        if(nv_slots.contains(s)) {
                            showSlots1.getItems().addAll(s);
                            continue;
                        }
                        showSlots.getItems().addAll(s);
                        showSlots1.getItems().addAll(s);
                    }
                }
            } else {
                timetableValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                timetableValidator.setText("Select Class or Lab...");
            }
        }
    }

    private void initialize_classrooms() {
        showClassrooms.getItems().clear();
        Vector<Classroom> classrooms = Application.getClassrooms();
        if (showDays != null) {
            for (Classroom c : classrooms) {
                if (isLab.selectedProperty().asObject().getValue() && Objects.equals(c.type, "Lab")) {
                    showClassrooms.getItems().addAll(c.classroomId);
                } else if (isClass.selectedProperty().asObject().getValue() && Objects.equals(c.type, "Class")) {
                    showClassrooms.getItems().addAll(c.classroomId);
                }
            }
            showClassrooms.valueProperty().addListener((ov, t, t1) -> {
                if (!Objects.equals(t1, "")) {
                    Vector<Lecture> lectures = Application.getLectures();
                    Vector<String> nv_slots = new Vector<>();
                    for (Lecture l : lectures) {
                        if (Objects.equals(l.getClassroomId(), showClassrooms.getValue()) && Objects.equals(l.getDay(), showDays.getValue())) {
                            nv_slots.add(l.getSlot());
                        }
                    }
                    initialize_slots(nv_slots);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(option2 == 1) {
            teacherDays.getItems().clear();
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            for(String s : days) {
                teacherDays.getItems().addAll(s);
            }
            teacherDays.valueProperty().addListener((ov, t1, t2) -> {
                if(!Objects.equals(t2, "")) {
                    teacherClassroom.getItems().clear();
                    Vector<Classroom> classrooms = Application.getClassrooms();
                    for(Classroom c : classrooms) {
                        teacherClassroom.getItems().add(c.getClassroomId());
                    }
                }
            });
            if(!Objects.equals(teacherClassroom.getValue(), "")) {
                teacherClassroom.valueProperty().addListener((ov, t1, t2) -> {
                    if(!Objects.equals(t2, "")){
                        teacherSlots.getItems().clear();
                        Vector<Lecture> lectures = Application.getLectures();
                        Vector<String> nv_slots = new Vector<>();
                        for(Lecture l : lectures) {
                            if(Objects.equals(l.getClassroomId(), t2) && Objects.equals(l.getDay(), teacherDays.getValue())){
                                nv_slots.add(l.getSlot());
                            }
                        }
                        Vector<String> slots1 = db.getSlots("Class");
                        if(slots1.contains(teacherKaClassType)) {
                            for(String sl : slots1) {
                                if(!nv_slots.contains(sl)){
                                    teacherSlots.getItems().add(sl);
                                }
                            }
                        } else {
                            slots1 = db.getSlots("Lab");
                            for(String sl : slots1) {
                                if(!nv_slots.contains(sl)){
                                    teacherSlots.getItems().add(sl);
                                }
                            }
                        }
                    }
                });
            }
        }
        if(teacherCourse != null) {
            Teacher t = Application.getCurrentTeacher();
            teacherCourse.getItems().clear();
            for(int i = 0; i < t.getCourses().size(); i++) {
                teacherCourse.getItems().addAll(t.getCourses().get(i));
            }
            teacherCourse.valueProperty().addListener((ov, t1, t2) -> {
                if(!Objects.equals(t2, "")) {
                    teacherSection.getItems().clear();
                    for(int i = 0; i < t.getCourses().size(); i++) {
                        if(Objects.equals(t.getCourses().get(i), t2)) {
                            teacherSection.getItems().add(t.getSections().get(i));
                        }
                    }
                }
            });
        }
        Vector<Course> courses = Application.getCourses();
        if(showCourses != null) {
            showCourses.getItems().clear();
            for(int i = 0; i < courses.size(); i++) {
                showCourses.getItems().addAll(courses.get(i).getCourseName());
            }
        }
        if(qCourse != null) {
            qSection.disableProperty().asObject().setValue(true);
            qCourse.getItems().clear();
            for(int i = 0; i < courses.size(); i++) {
                if(Application.getCurrentTeacher().getCourses().contains(courses.get(i).getCourseId()))
                    qCourse.getItems().addAll(courses.get(i).getCourseId());
            }
            qCourse.valueProperty().addListener((ov, t, t1) -> {
                if(!Objects.equals(t1, "")) {
                    qSection.disableProperty().asObject().setValue(false);
                    qSection.getItems().clear();
                    for(int i = 0; i < courses.size(); i++) {
                        if(Objects.equals(courses.get(i).getCourseId(), qCourse.getValue())) {
                            Vector<String> sections = courses.get(i).getSections();
                            qSection.getItems().addAll(sections.get(i));
                        }
                    }
                }
            });
        }
        if(tCourse != null) {
            tSection.disableProperty().asObject().setValue(true);
            tCourse.getItems().clear();
            for(int i = 0; i < courses.size(); i++) {
                tCourse.getItems().addAll(courses.get(i).getCourseId());
            }
            tCourse.valueProperty().addListener((ov, t, t1) -> {
                if(!Objects.equals(t1, "")) {
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
            if (showDays == null) {
                for(Classroom c : classrooms) {
                    showClassrooms.getItems().addAll(c.classroomId + "@" + c.type);
                }
            }
        }
        if(showDays != null) {
            showDays.getItems().clear();
            showDays1.getItems().clear();
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            for(String s : days) {
                showDays1.getItems().addAll(s);
                showDays.getItems().addAll(s);
            }
            showDays1.valueProperty().addListener((ov, t, t1) -> {
                if(!Objects.equals(t1, "")) {
                    initialize_slots1(t1);
                }
            });
            showDays.valueProperty().addListener((ov, t, t1) -> {
                if(!Objects.equals(t1, "")) {
                    initialize_classrooms();
                }
            });
        }
        if(showStudents != null) {
            showStudents.getItems().clear();
            if(!Objects.equals(selectSection.getText(), "")) {
                Vector<Course> st_courses = Application.getCourses();
                for(Course c : st_courses) {
                    if(c.getSections().contains(selectSection.getText())) {
                        showStudents.getItems().add(c.getCourseId() + "@" + c.getCourseName());
                    }
                }
            }
        }
        if(t1 != null) {
            classrooms.setCellValueFactory(new PropertyValueFactory<>("classroomId"));
            labs.setCellValueFactory(new PropertyValueFactory<>("classroomId"));
            slot1.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot2.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot3.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot4.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot5.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot6.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot7.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot8.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot01.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot02.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot03.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            slot04.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            Vector<Classroom> classrooms = Application.getClassrooms();
            t1.getItems().clear();
            t01.getItems().clear();
            t2.getItems().clear();
            t3.getItems().clear();
            t4.getItems().clear();
            t5.getItems().clear();
            t6.getItems().clear();
            t7.getItems().clear();
            t8.getItems().clear();
            t9.getItems().clear();
            t02.getItems().clear();
            t03.getItems().clear();
            t04.getItems().clear();
            t05.getItems().clear();
            for(Classroom c : classrooms) {
                if(Objects.equals(c.getType(), "Class")) {
                    t1.getItems().add(c);
                } else {
                    t01.getItems().add(c);
                }
            }
            dayLabel.setText(option == 0 ? "Monday" : option == 1 ? "Tuesday" : option == 2 ? "Wednesday" : option == 3 ? "Thursday" : "Friday");
            setTable(dayLabel.getText());
        }
        if(sCourseView != null) {
            sCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            sQuizTopic.setCellValueFactory(new PropertyValueFactory<>("topic"));
            sCourseView.getItems().clear();
            sQuizTable.getItems().clear();
            Vector<Course> Ccourses = Application.getCourses();
            Student s = Application.getCurrentStudent();
            Vector<Quiz> quizzes = Application.getQuizzes();
            for(Course c : Ccourses) {
                if(c.getSections().contains(s.getSection()) && s.getCourseId().contains(c.getCourseId())) {
                    for(Quiz q : quizzes) {
                        if(Objects.equals(q.getCourseId(), c.courseId) && Objects.equals(q.getSection(), s.getSection())) {
                            sCourseView.getItems().add(c);
                            sQuizTable.getItems().add(q);
                            break;
                        }
                    }
                }
            }
        }
    }
    private void setTable(String present_day) {
        ObservableList<Classroom> table1 = t1.getItems();
        Vector<Lecture> lectures = db.getLectures();
        Vector<String> slots_name = db.getSlots("Class");
        boolean[] slots_found = new boolean[8];
        for(Classroom c : table1) {
            Arrays.fill(slots_found, false);
            Lecture found = null;
            for(Lecture l : lectures) {
                if(Objects.equals(l.getClassroomId(), c.getClassroomId()) && Objects.equals(l.getDay(), present_day)) {
                    int idx = -1;
                    if(isStudent != null) {
                        Student s = Application.getCurrentStudent();
                        if(s.getCourseId().contains(l.getCourseId()) && Objects.equals(s.getSection(), l.getSection())) {
                            idx = slots_name.indexOf(l.getSlot());
                            slots_found[idx] = true;
                            found = l;
                        }
                    } else if(isAdmin != null) {
                        idx = slots_name.indexOf(l.getSlot());
                        slots_found[idx] = true;
                        found = l;
                    } else if(isTeacher != null) {
                        Teacher t = Application.getCurrentTeacher();
                        if(t.getCourses().contains(l.getCourseId()) && t.getSections().contains(l.getSection())) {
                            idx = slots_name.indexOf(l.getSlot());
                            slots_found[idx] = true;
                            found = l;
                        }
                    }
                    if(found != null) {
                        Vector<Course> courses = Application.getCourses();
                        for(Course cc : courses) {
                            if(Objects.equals(cc.getCourseId(), l.getCourseId())) {
                                found.setCourseId(cc.getCourseName() + "   |   " + found.getSection());
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if(!slots_found[0]) {
                t2.getItems().add(new Lecture());
            } else {
                t2.getItems().add(found);
            }
            if(!slots_found[1]) {
                t3.getItems().add(new Lecture());
            } else {
                t3.getItems().add(found);
            }
            if(!slots_found[2]) {
                t4.getItems().add(new Lecture());
            } else {
                t4.getItems().add(found);
            }
            if(!slots_found[3]) {
                t5.getItems().add(new Lecture());
            } else {
                t5.getItems().add(found);
            }
            if(!slots_found[4]) {
                t6.getItems().add(new Lecture());
            } else {
                t6.getItems().add(found);
            }
            if(!slots_found[5]) {
                t7.getItems().add(new Lecture());
            } else {
                t7.getItems().add(found);
            }
            if(!slots_found[6]) {
                t8.getItems().add(new Lecture());
            } else {
                t8.getItems().add(found);
            }
            if(!slots_found[7]) {
                t9.getItems().add(new Lecture());
            } else {
                t9.getItems().add(found);
            }
        }
        table1 = t01.getItems();
        slots_name = db.getSlots("Lab");
        slots_found = new boolean[4];
        for(Classroom c : table1) {
            Arrays.fill(slots_found, false);
            Lecture found = null;
            for(Lecture l : lectures) {
                if(Objects.equals(l.getClassroomId(), c.getClassroomId()) && Objects.equals(l.getDay(), present_day)) {
                    int idx = -1;
                    if(isStudent != null) {
                        Student s = Application.getCurrentStudent();
                        if(s.getCourseId().contains(l.getCourseId()) && Objects.equals(s.getSection(), l.getSection())) {
                            idx = slots_name.indexOf(l.getSlot());
                            slots_found[idx] = true;
                            found = l;
                        }
                    } else if(isAdmin != null) {
                        idx = slots_name.indexOf(l.getSlot());
                        slots_found[idx] = true;
                        found = l;
                    } else if(isTeacher != null) {
                        Teacher t = Application.getCurrentTeacher();
                        if(t.getCourses().contains(l.getCourseId()) && t.getSections().contains(l.getSection())) {
                            idx = slots_name.indexOf(l.getSlot());
                            slots_found[idx] = true;
                            found = l;
                        }
                    }
                    if(found != null) {
                        Vector<Course> courses = Application.getCourses();
                        for(Course cc : courses) {
                            if(Objects.equals(cc.getCourseId(), l.getCourseId())) {
                                found.setCourseId(cc.getCourseName() + "   |   " + found.getSection());
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if(!slots_found[0]) {
                t02.getItems().add(new Lecture());
            } else {
                t02.getItems().add(found);
            }
            if(!slots_found[1]) {
                t03.getItems().add(new Lecture());
            } else {
                t03.getItems().add(found);
            }
            if(!slots_found[2]) {
                t04.getItems().add(new Lecture());
            } else {
                t04.getItems().add(found);
            }
            if(!slots_found[3]) {
                t05.getItems().add(new Lecture());
            } else {
                t05.getItems().add(found);
            }
        }
    }

    @FXML
    public void showCourse(ActionEvent actionEvent) {
        if(!Objects.equals(selectSection.getText(), "")) {
            initialize(null, null);
        } else {
            studentValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
            studentValidator.setText("Section field can't be empty....");
        }
    }

    @FXML
    public void addStudent(ActionEvent actionEvent) {
        if(!Objects.equals(sCourses.getText(), "") && !Objects.equals(sEmail.getText(), "") && !Objects.equals(sPss.getText(), "") && !Objects.equals(sSection.getText(), "") && !Objects.equals(sName.getText(), "")) {
            String[] split_courses = sCourses.getText().split(",");
            Vector<Course> courses = Application.getCourses();
            Vector<Student> students = Application.getStudents();
            int id = 0;
            for(Student s : students) {
                if(Integer.parseInt(s.getId()) > id) {
                    id = Integer.parseInt(s.getId());
                }
            }
            if(students.size() == 0 || students.size() == 1) {
                id = students.size();
            } else {
                id += 1;
            }
            for(String s : split_courses) {
                boolean found = true;
                for(Course c : courses) {
                    if(Objects.equals(c.getCourseId(), s) && c.getSections().contains(sSection.getText())) {
                        if(db.addStudent(sEmail.getText(), sPss.getText(), ""+id, s, sSection.getText(), sName.getText())) {
                            studentValidator.setStyle("-fx-text-fill: green; -fx-background-color: white");
                            studentValidator.setText("Student Added...!");
                        } else {
                            studentValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                            studentValidator.setText("Error, can't add");
                        }
                        found = false;
                        break;
                    }
                }
                if(found) {
                    studentValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
                    studentValidator.setText("Course or Section doesn't Exists");
                }
            }

        } else {
            studentValidator.setStyle("-fx-text-fill: red; -fx-background-color: white");
            studentValidator.setText("Input field can't be empty....");
        }
    }
}
