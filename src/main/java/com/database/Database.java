package com.database;

import com.base.*;
import com.timetable.*;

import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class Database implements PersistenceHandler {
    public Connection connection;

    public static Database instance;
    
    private Database() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "1234");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            synchronized (Database.class) {
                if(instance==null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Vector<String> getSlots(String type) {
        Vector<String> slots = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM SLOT where type='" + type +"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                slots.add(rs.getString("SLOT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    public Vector<Lecture> getLectures() {
        Vector<Lecture> lectures = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM LECTURE ORDER BY CLASSROOMID";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Lecture l = new Lecture();
                l.setCourseId(rs.getString("COURSEID"));
                l.setSection(rs.getString("SECTION"));
                l.setDay(rs.getString("DAY"));
                l.setLectureId(rs.getString("LECTUREID"));
                l.setClassroomId(rs.getString("CLASSROOMID"));
                l.setSlot(rs.getString("SLOT"));
                l.setQuizId(rs.getInt("QUIZID"));
                lectures.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lectures;
    }

    public Vector<Classroom> getClassrooms() {
        Vector<Classroom> classrooms = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM CLASSROOM";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Classroom c = new Classroom();
                c.setClassroomId(rs.getString("CLASSROOMID"));
                c.setType(rs.getString("TYPE"));
                classrooms.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    public Vector<Admin> getAdmins() {
        Vector<Admin> admins = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM ADMIN";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Admin a = new Admin();
                a.setEmail(rs.getString("EMAIL"));
                a.setId(rs.getString("ADMINID"));
                a.setPss(rs.getString("PSS"));
                a.setName(rs.getString("NAME"));
                admins.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public Vector<Course> getCourses() {
        Vector<Course> courses = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM COURSE ORDER BY COURSEID";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                boolean found = false;
                for(Course c : courses) {
                    if(Objects.equals(c.getCourseId(), rs.getString("COURSEID"))) {
                        c.getSections().add(rs.getString("SECTION"));
                        found = true;
                    }
                }
                if(!found) {
                    Course c = new Course();
                    c.setCourseId(rs.getString("COURSEID"));
                    c.setCourseName(rs.getString("COURSENAME"));
                    c.setCreditHrs(rs.getInt("CREDITHRS"));
                    c.getSections().add(rs.getString("SECTION"));
                    courses.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Vector<Student> getStudents() {
        Vector<Student> students = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM STUDENT ORDER BY STUDENTID";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                boolean found = false;
                for(Student s : students) {
                    if(Objects.equals(s.getId(), rs.getString("STUDENTID"))) {
                        s.getCourseId().add(rs.getString("COURSEID"));
                        if(!Objects.equals(rs.getString("NOTIFICATION"), "")) {
                            s.setNotification(rs.getString("NOTIFICATION"));
                        }
                        found = true;
                    }
                }
                if(!found) {
                    Student s = new Student();
                    s.setEmail(rs.getString("EMAIL"));
                    s.setId(rs.getString("STUDENTID"));
                    s.setPss(rs.getString("PSS"));
                    s.setName(rs.getString("NAME"));
                    s.setSection(rs.getString("SECTION"));
                    s.getCourseId().add(rs.getString("COURSEID"));
                    if(!Objects.equals(rs.getString("NOTIFICATION"), "")) {
                        s.setNotification(rs.getString("NOTIFICATION"));
                    }
                    students.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Vector<Quiz> getQuizzes() {
        Vector<Quiz> quizzes = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM QUIZ";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                boolean found = false;
                Quiz q = new Quiz();
                q.setCourseId(rs.getString("COURSEID"));
                q.setQuizId(rs.getInt("QUIZID"));
                q.setTopic(rs.getString("TOPIC"));
                q.setSection(rs.getString("SECTION"));
                quizzes.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public Vector<Teacher> getTeachers() {
        Vector<Teacher> teachers = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM TEACHER ORDER BY TEACHERID";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                boolean found = false;
                for(Teacher t : teachers) {
                    if(Objects.equals(t.getEmail(), rs.getString("EMAIL"))) {
                        t.getCourses().add(rs.getString("COURSEID"));
                        t.getSections().add(rs.getString("SECTION"));
                        found = true;
                    }
                }
                if(!found) {
                    Teacher t = new Teacher();
                    t.setEmail(rs.getString("EMAIL"));
                    t.setTid(rs.getString("TEACHERID"));
                    t.setPss(rs.getString("PSS"));
                    t.setName(rs.getString("NAME"));
                    t.getSections().add(rs.getString("SECTION"));
                    t.getCourses().add(rs.getString("COURSEID"));
                    teachers.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public boolean loginTeacher(String Email, String Pss) {
        Vector<Teacher> teachers = Application.getTeachers();
        for(Teacher t : teachers) {
            if(Objects.equals(t.getEmail(), Email) && Objects.equals(t.getPss(), Pss)) {
                Application.setCurrentTeacher(t);
                return true;
            }
        }
        return false;
    }

    public boolean loginStudent(String Email, String Pss) {
        Vector<Student> students = Application.getStudents();
        for(Student s: students) {
            if(Objects.equals(s.getEmail(), Email) && Objects.equals(s.getPss(), Pss)) {
                Application.setCurrentStudent(s);
                return true;
            }
        }
        return false;
    }

    public boolean loginAdmin(String Email, String Pss) {
        Vector<Admin> admins = Application.getAdmins();
        for(Admin a: admins) {
            if(Objects.equals(a.getEmail(), Email) && Objects.equals(a.getPss(), Pss)) {
                return true;
            }
        }
        return false;
    }

    public boolean addQuiz(int quizId, String courseId, String section, String topic) {
        Vector<Quiz> quizzes = Application.getQuizzes();
        try {
            Statement st = connection.createStatement();
            for(Quiz q : quizzes) {
                if(q.getQuizId() == quizId) {
                    String sql = "UPDATE QUIZ SET TOPIC = '" + topic + "' WHERE QUIZID = " + quizId;
                    st.executeUpdate(sql);
                    q.setTopic(topic);
                    Application.setQuizzes(quizzes);
                    return true;
                }
            }
            String sql = "INSERT INTO QUIZ VALUES ('"+ topic +"', "+ quizId +", '"+ courseId +"', '"+ section +"')";
            st.executeUpdate(sql);
            Quiz q = new Quiz();
            q.setTopic(topic);
            q.setQuizId(quizId);
            q.setSection(section);
            q.setCourseId(courseId);
            Application.getQuizzes().add(q);
            sql = "UPDATE LECTURE SET QUIZID = " + quizId + " WHERE COURSEID = '" + courseId + "' AND SECTION = '" + section +"'";
            st.executeUpdate(sql);
            String notifi = "Quiz Announced of Course (" + courseId + "), Topic (" + topic + ")";
            sql = "UPDATE STUDENT SET NOTIFICATION = '" + notifi + "' WHERE COURSEID = '" + courseId + "' AND SECTION = '" + section +"'";
            st.executeUpdate(sql);
            Vector<Lecture> lectures = Application.getLectures();
            for(int i = 0; i < lectures.size(); i++){
                if(Objects.equals(lectures.get(i).getCourseId(), courseId) && Objects.equals(lectures.get(i).getSection(), section)) {
                    lectures.get(i).setQuizId(quizId);
                    Application.setLectures(lectures);
                    break;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addLecture(String LectureId, String Day, String Slot, String ClassroomId, String Section, String CourseId) {
        try {
            Statement st = connection.createStatement();
            Lecture l = new Lecture();
            l.setLectureId(LectureId);
            l.setSection(Section);
            l.setCourseId(CourseId);
            l.setDay(Day);
            l.setSlot(Slot);
            l.setClassroomId(ClassroomId);
            String sql = "INSERT INTO LECTURE (LECTUREID, DAY, SLOT, COURSEID, SECTION, CLASSROOMID) VALUES ('"+ LectureId +"', '"+ Day +"', '"+ Slot +"', '"+ CourseId +"', '"+ Section+"', '"+ ClassroomId + "')";
            st.executeUpdate(sql);
            Application.getLectures().add(l);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addClassroom(String classroomId, String Type) {
        Vector<Classroom> classrooms = this.getClassrooms();
        for(Classroom c : classrooms) {
            if(Objects.equals(c.getClassroomId(), classroomId)) {
                return false;
            }
        }
        try {
            Statement st = connection.createStatement();
            Classroom c = new Classroom();
            c.setClassroomId(classroomId);
            c.setType(Type);
            String sql = "INSERT INTO CLASSROOM VALUES ('"+ classroomId +"', '"+ Type +"')";
            st.executeUpdate(sql);
            if(Application.getClassrooms() != null)
                Application.getClassrooms().add(c);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addCourse(String cid, String cName, int credit, String sections) {
        Vector<Course> courses = this.getCourses();
        for(Course c : courses) {
            if(Objects.equals(c.getCourseId(), cid)){
                System.out.println("True");
                return false;
            }
        }
        String[] section = sections.split(",");
        try {
            Statement st = connection.createStatement();
            Course c = new Course();
            c.setCourseName(cName);
            c.setCourseId(cid);
            c.setCreditHrs(credit);
            for(String s : section) {
                String sql = "INSERT INTO COURSE VALUES ('"+ cid +"', '"+ cName +"', "+ credit +", '"+ s + "')";
                c.getSections().add(s);
                st.executeUpdate(sql);
            }
            if(Application.getCourses() != null)
                Application.getCourses().add(c);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeCourse(String cid) {
        boolean found = false;
        Vector<Course> courses = this.getCourses();
        for(Course c : courses) {
            if(Objects.equals(c.getCourseId(), cid)) {
                found = true;
                break;
            }
        }
        if(!found) {
            return false;
        }
        try {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM STUDENT WHERE COURSEID = '"+ cid +"'";
            st.executeUpdate(sql);
            sql = "DELETE FROM LECTURE WHERE COURSEID = '"+ cid +"'";
            st.executeUpdate(sql);
            sql = "DELETE FROM QUIZ WHERE COURSEID = '"+ cid +"'";
            st.execute(sql);
            sql = "DELETE FROM TEACHER WHERE COURSEID = '"+ cid +"'";
            st.executeUpdate(sql);
            sql = "DELETE FROM COURSE WHERE COURSEID = '"+ cid +"'";
            st.executeUpdate(sql);
            Application.initialize();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean removeClassroom(String cid) {
        boolean found = false;
        Vector<Classroom> classrooms = this.getClassrooms();
        for(Classroom c : classrooms) {
            if(Objects.equals(c.getClassroomId(), cid)) {
                found = true;
                break;
            }
        }
        if(!found) {
            return false;
        }
        try {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM LECTURE WHERE CLASSROOMID = '"+ cid +"'";
            st.executeUpdate(sql);
            sql = "DELETE FROM CLASSROOM WHERE CLASSROOMID = '"+ cid +"'";
            st.executeUpdate(sql);
            Application.initialize();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeLecture(String lid) {
        try {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM LECTURE WHERE LECTUREID = '"+lid+"'";
            st.executeUpdate(sql);
            Application.initialize();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeTeacher(String email) {
        boolean found = false;
        Vector<Teacher> teachers = this.getTeachers();
        for(Teacher t : teachers) {
            if(Objects.equals(t.getEmail(), email)) {
                found = true;
                break;
            }
        }
        if(!found) {
            return false;
        }
        try {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM TEACHER WHERE EMAIL = '"+email+"'";
            st.executeUpdate(sql);
            Application.initialize();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTeacher(String tid, String name, String email, String pss, String cid, String section) {
        Vector<Teacher> teachers = this.getTeachers();
        for(int i = 0; i < teachers.size(); i++) {
            if(Objects.equals(teachers.get(i).getEmail(), email)) {
                if(teachers.get(i).exists(cid, section)) {
                    return false;
                }
            }
        }
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO TEACHER VALUES ('"+ name +"', '"+ email +"', '"+ tid +"', '"+ pss +"', '"+ cid +"', '"+ section +"')";
            st.executeUpdate(sql);
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
            if(Application.getTeachers() != null)
                Application.getTeachers().add(t);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAdmin(String email, String pss, String id) {
        Vector<Admin> admins = this.getAdmins();
        for(Admin a: admins) {
            if(Objects.equals(a.getEmail(), email)) {
                return false;
            }
        }
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO ADMIN (EMAIL, PSS, ADMINID) VALUES ('"+ email +"', '"+ pss +"', '"+ id +"')";
            st.executeUpdate(sql);
            Admin a = new Admin();
            a.setPss(pss);
            a.setEmail(email);
            a.setId(id);
            if(Application.getAdmins() != null)
                Application.getAdmins().add(a);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addStudent(String Email, String Pss, String id, String Course, String Section, String Name) {
        Vector<Student> students = this.getStudents();
        for(Student s : students) {
            if(Objects.equals(s.getEmail(), Email)) {
                return false;
            }
        }
        try {
            boolean found = false;
            students = this.getStudents();
            for(Student s : students) {
                if(Objects.equals(s.getEmail(), Email)) {
                    s.getCourseId().add(Course);
                    found = true;
                    break;
                }
            }
            Statement st = connection.createStatement();
            String sql = "INSERT INTO STUDENT VALUES ('"+ Name +"', '"+ Email +"', '"+ id +"', '"+ Pss +"', '"+ Course +"', '"+ Section +"', '')";
            st.executeUpdate(sql);
            if(!found) {
                Student s = new Student();
                s.setPss(Pss);
                s.setName(Name);
                s.getCourseId().add(Course);
                s.setSection(Section);
                s.setEmail(Email);
                s.setId(id);
                s.setNotification("");
                if(Application.getStudents() != null)
                    Application.getStudents().add(s);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
