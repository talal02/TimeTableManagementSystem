package com.database;

import com.base.*;
import com.timetable.Application;

import java.util.Objects;
import java.util.Vector;

public interface PersistenceHandler {
    public default boolean loginTeacher(String Email, String Pss) {
        Vector<Teacher> teachers = Application.getTeachers();
        for(Teacher t : teachers) {
            if(Objects.equals(t.getEmail(), Email) && Objects.equals(t.getPss(), Pss)) {
                Application.setCurrentTeacher(t);
                return true;
            }
        }
        return false;
    }

    public default boolean loginStudent(String Email, String Pss) {
        Vector<Student> students = Application.getStudents();
        for(Student s: students) {
            if(Objects.equals(s.getEmail(), Email) && Objects.equals(s.getPss(), Pss)) {
                Application.setCurrentStudent(s);
                return true;
            }
        }
        return false;
    }

    public default boolean loginAdmin(String Email, String Pss) {
        Vector<Admin> admins = Application.getAdmins();
        for(Admin a: admins) {
            if(Objects.equals(a.getEmail(), Email) && Objects.equals(a.getPss(), Pss)) {
                return true;
            }
        }
        return false;
    }

    boolean removeLecture(String lectureId);

    boolean addLecture(String idOfLecture, String value, String value1, String value2, String teacherKaSection, String teacherKaCourse);

    boolean addClassroom(String text, String aClass);

    boolean addCourse(String text, String text1, int parseInt, String text2);

    boolean removeCourse(String courseId);

    boolean removeClassroom(String classroomId);

    boolean addQuiz(int quizId, String value, String value1, String text);

    boolean addTeacher(String s, String text, String text1, String text2, String value, String value1);

    boolean removeTeacher(String email);

    Vector<String> getSlots(String s);

    Vector<Lecture> getLectures();

    boolean addStudent(String text, String text1, String s, String s1, String text2, String text3);

    boolean addAdmin(String text, String text1, String s);

    Vector<Classroom> getClassrooms();

    Vector<Admin> getAdmins();

    Vector<Student> getStudents();

    Vector<Course> getCourses();

    Vector<Teacher> getTeachers();

    Vector<Quiz> getQuizzes();
}
