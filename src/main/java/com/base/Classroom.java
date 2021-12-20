package com.base;

public class Classroom {
    String classroomId;
    String type;

    @Override
    public String toString() {
        return classroomId + "," + type + "\n";
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
