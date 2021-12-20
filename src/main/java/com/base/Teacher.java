package com.base;

import java.util.Objects;
import java.util.Vector;

public class Teacher {
    String name;
    String tid;
    String email;
    String pss;
    Vector<String> courses = new Vector<>();
    Vector<String> sections = new Vector<>();

    @Override
    public String toString() {
        String data = name + "," + email + "," + pss + "," + tid + ",";
        String toReturn = "";
        for(int i = 0; i < courses.size(); i++) {
            toReturn += data + courses.get(i) + "," + sections.get(i);
            toReturn += "~";
        }
        return toReturn + "\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public Vector<String> getCourses() {
        return courses;
    }

    public void setCourses(Vector<String> courses) {
        this.courses = courses;
    }

    public Vector<String> getSections() {
        return sections;
    }

    public void setSections(Vector<String> sections) {
        this.sections = sections;
    }

    public boolean exists(String course, String section) {
        for(int i = 0; i < courses.size(); i++) {
            if(Objects.equals(courses.get(i), course) && Objects.equals(sections.get(i), section)) {
                return true;
            }
        }
        return false;
    }
}
