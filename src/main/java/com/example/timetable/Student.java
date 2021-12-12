package com.example.timetable;

import java.util.Vector;

public class Student {
    String name;
    String email;
    String pss;
    String section;
    Vector<String> courseId = new Vector<>();
    String Id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPss() {
        return pss;
    }

    public void setPss(String pss) {
        this.pss = pss;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Vector<String> getCourseId() {
        return courseId;
    }

    public void setCourseId(Vector<String> courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
