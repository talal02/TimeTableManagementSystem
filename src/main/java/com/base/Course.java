package com.base;

import java.util.Vector;

public class Course {
    String courseId;
    String courseName;
    int creditHrs;
    Vector<String> sections = new Vector<>();

    @Override
    public String toString() {
        String data = courseId + "," + courseName + "," + creditHrs + ",";
        String toReturn = "";
        for(String sec : sections) {
            toReturn += data + sec;
            toReturn += "~";
        }
        return toReturn + "\n";
    }

    public Vector<String> getSections() {
        return sections;
    }

    public void setSections(Vector<String> sections) {
        this.sections = sections;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHrs() {
        return creditHrs;
    }

    public void setCreditHrs(int creditHrs) {
        this.creditHrs = creditHrs;
    }
}
