package com.zj.demo.entity;

public class StudentMajor {

    private String id;
    private String studentID;
    private String majorID;

    @Override
    public String toString() {
        return "StudentMajor{" +
                "id='" + id + '\'' +
                ", studentID='" + studentID + '\'' +
                ", majorID='" + majorID + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMajorID() {
        return majorID;
    }

    public void setMajorID(String majorID) {
        this.majorID = majorID;
    }

    public StudentMajor(String id, String studentID, String majorID) {
        this.id = id;
        this.studentID = studentID;
        this.majorID = majorID;
    }
}
