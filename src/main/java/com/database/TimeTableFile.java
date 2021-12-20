package com.database;

import com.base.*;
import com.timetable.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

class HandleFile extends Thread {
    File file;
    public static String[] lines;
    public HandleFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                FileReader fr=new FileReader(file);
                BufferedReader br=new BufferedReader(fr);
                StringBuffer sb=new StringBuffer();
                String line;
                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                    sb.append("\n");
                }
                fr.close();
                lines = sb.toString().split("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TimeTableFile implements PersistenceHandler {

    public static TimeTableFile instance;

    private TimeTableFile() { }

    public static TimeTableFile getInstance() {
        if (instance == null) {
            synchronized (TimeTableFile.class) {
                if(instance == null) {
                    instance = new TimeTableFile();
                }
            }
        }
        return instance;
    }

    public Vector<String> getSlots(String type) {
        Vector<String> slots = new Vector<>();
        try {
            File file=new File("Slots.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    if(s.contains(type)) {
                        String[] token = s.split(",");
                        slots.add(token[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return slots;
    }

    private String[] getLines(File file) throws IOException {
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);
        StringBuffer sb=new StringBuffer();
        String line;
        while((line=br.readLine())!=null)
        {
            sb.append(line);
            sb.append("\n");
        }
        fr.close();
        String[] lines = sb.toString().split("\n");
        return lines;
    }

    public Vector<Lecture> getLectures() {
        Vector<Lecture> lectures = new Vector<>();
        try {
            File file=new File("Lecture.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split(",");
                    Lecture l = new Lecture();
                    l.setCourseId(token[5]);
                    l.setLectureId(token[0]);
                    l.setDay(token[2]);
                    l.setSection(token[4]);
                    l.setClassroomId(token[1]);
                    l.setSlot(token[3]);
                    l.setQuizId(Integer.parseInt(token[6]));
                    lectures.add(l);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lectures;
    }

    @Override
    public boolean addStudent(String text, String text1, String s, String s1, String text2, String text3) {
        return false;
    }

    @Override
    public boolean addAdmin(String text, String text1, String s) {
        return false;
    }

    public Vector<Classroom> getClassrooms() {
        Vector<Classroom> classrooms = new Vector<>();
        try {
            File file=new File("Classroom.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split(",");
                    Classroom c = new Classroom();
                    c.setClassroomId(token[0]);
                    c.setType(token[1]);
                    classrooms.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    public Vector<Admin> getAdmins() {
        Vector<Admin> admins = new Vector<>();
        try {
            File file=new File("Admin.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split(",");
                    Admin a = new Admin();
                    a.setId(token[0]);
                    a.setName(token[1]);
                    a.setPss(token[2]);
                    a.setEmail(token[3]);
                    admins.add(a);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public Vector<Course> getCourses() {
        Vector<Course> courses = new Vector<>();
        try {
            File file=new File("Course.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split("~");
                    Course c = new Course();
                    for(String t : token) {
                        if(!Objects.equals(t, "")) {
                            String[] tokens = t.split(",");
                            c.setCourseId(tokens[0]);
                            c.setCourseName(tokens[1]);
                            c.setCreditHrs(Integer.parseInt(tokens[2]));
                            c.getSections().add(tokens[3]);
                        }
                    }
                    courses.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Vector<Student> getStudents() {
        Vector<Student> students = new Vector<>();
        try {
            File file=new File("Student.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split("~");
                    Student st = new Student();
                    for(String t : token) {
                        if(!Objects.equals(t, "")) {
                            String[] tokens = t.split(",");
                            st.setName(tokens[0]);
                            st.setEmail(tokens[1]);
                            st.setPss(tokens[2]);
                            st.setSection(tokens[3]);
                            st.setNotification(tokens[4]);
                            st.setId(tokens[5]);
                            st.getCourseId().add(tokens[6]);
                        }
                    }
                    students.add(st);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Vector<Quiz> getQuizzes() {
        Vector<Quiz> quizzes = new Vector<>();
        try {
            File file=new File("Quiz.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split(",");
                    Quiz q = new Quiz();
                    q.setTopic(token[0]);
                    q.setCourseId(token[1]);
                    q.setSection(token[2]);
                    q.setQuizId(Integer.parseInt(token[3]));
                    quizzes.add(q);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public Vector<Teacher> getTeachers() {
        Vector<Teacher> teachers = new Vector<>();
        try {
            File file=new File("Teacher.txt");
            String[] lines = getLines(file);
            for(String s : lines){
                if(!Objects.equals(s, "")) {
                    String[] token = s.split("~");
                    Teacher st = new Teacher();
                    for(String t : token) {
                        if(!Objects.equals(t, "")) {
                            String[] tokens = t.split(",");
                            st.setName(tokens[0]);
                            st.setEmail(tokens[1]);
                            st.setPss(tokens[2]);
                            st.setTid(tokens[3]);
                            st.getCourses().add(tokens[4]);
                            st.getSections().add(tokens[5]);
                        }
                    }
                    teachers.add(st);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public boolean removeLecture(String lectureId) {
        return false;
    }

    @Override
    public boolean addLecture(String LectureId, String Day, String Slot, String ClassroomId, String Section, String CourseId) {
        Lecture l = new Lecture();
        l.setLectureId(LectureId);
        l.setSection(Section);
        l.setCourseId(CourseId);
        l.setDay(Day);
        l.setSlot(Slot);
        l.setClassroomId(ClassroomId);
        Application.getLectures().add(l);
        return true;
    }

    @Override
    public boolean addClassroom(String classroomId, String Type) {
        Classroom c = new Classroom();
        c.setClassroomId(classroomId);
        c.setType(Type);
        Application.getClassrooms().add(c);
        return true;
    }

    @Override
    public boolean addCourse(String cid, String cName, int credit, String sections) {
        Course c = new Course();
        c.setCourseName(cName);
        c.setCourseId(cid);
        c.setCreditHrs(credit);
        String[] section = sections.split(",");
        for(String s : section) {
            c.getSections().add(s);
        }
        Application.getCourses().add(c);
        return true;
    }

    @Override
    public boolean removeCourse(String courseId) {
        return false;
    }

    @Override
    public boolean removeClassroom(String classroomId) {
        return false;
    }

    @Override
    public boolean addQuiz(int quizId, String courseId, String section, String topic) {
        Vector<Quiz> quizzes = Application.getQuizzes();
        for(Quiz q : quizzes) {
            if(q.getQuizId() == quizId) {
                q.setTopic(topic);
                Application.setQuizzes(quizzes);
                return true;
            }
        }
        Quiz q = new Quiz();
        q.setTopic(topic);
        q.setQuizId(quizId);
        q.setSection(section);
        q.setCourseId(courseId);
        Application.getQuizzes().add(q);
        Vector<Lecture> lectures = Application.getLectures();
        for(int i = 0; i < lectures.size(); i++){
            if(Objects.equals(lectures.get(i).getCourseId(), courseId) && Objects.equals(lectures.get(i).getSection(), section)) {
                lectures.get(i).setQuizId(quizId);
                Application.setLectures(lectures);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean addTeacher(String tid, String name, String email, String pss, String cid, String section) {
        Vector<Teacher> teachers = Application.getTeachers();
            for(int i = 0; i < teachers.size(); i++) {
                if(Objects.equals(teachers.get(i).getEmail(), email)) {
                    teachers.get(i).getCourses().add(cid);
                    teachers.get(i).getSections().add(section);
                    Application.setTeachers(teachers);
                    return true;
                }
            }
            Teacher t = new Teacher();
            t.setPss(pss);
            t.setEmail(email);
            t.setTid(tid);
            t.setName(name);
            t.getSections().add(section);
            t.getCourses().add(cid);
            Application.getTeachers().add(t);
            return true;
    }

    @Override
    public boolean removeTeacher(String email) {
        return false;
    }
}
