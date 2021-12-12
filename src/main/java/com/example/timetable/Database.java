package com.example.timetable;

import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class Database {
    public Connection connection;

    public Database() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "1234");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                    c.setCreditHrs(Integer.parseInt(rs.getString("CREDITHRS")));
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
                    students.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
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

    public boolean loginStudent(String Email, String Pss) {
        Vector<Student> students = Application.getStudents();
        for(Student s: students) {
            if(Objects.equals(s.getEmail(), Email) && Objects.equals(s.getPss(), Pss)) {
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

    public boolean addCourse(String cid, String cName, int credit, String sections) {
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
            Application.getCourses().add(c);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTeacher(String tid, String name, String email, String pss, String cid, String section) {
        Vector<Teacher> teachers = Application.getTeachers();
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO TEACHER VALUES ('"+ name +"', '"+ email +"', '"+ tid +"', '"+ pss +"', '"+ cid +"', '"+ section +"')";
            st.executeUpdate(sql);
            for(int i = 0; i < teachers.size(); i++) {
                if(Objects.equals(teachers.get(i).email, email)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAdmin(String email, String pss, String id) {
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO ADMIN (EMAIL, PSS, ADMINID) VALUES ('"+ email +"', '"+ pss +"', '"+ id +"')";
            st.executeUpdate(sql);
            Admin a = new Admin();
            a.setPss(pss);
            a.setEmail(email);
            a.setId(id);
            Application.getAdmins().add(a);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addStudent(String Email, String Pss, String id) {
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO STUDENT (EMAIL, PSS, STUDENTID) VALUES ('"+ Email +"', '"+ Pss +"', '"+ id +"')";
            st.executeUpdate(sql);
            Student s = new Student();
            s.setPss(Pss);
            s.setEmail(Email);
            s.setId(id);
            Application.getStudents().add(s);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
