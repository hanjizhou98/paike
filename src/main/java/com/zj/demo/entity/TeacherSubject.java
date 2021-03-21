package com.zj.demo.entity;

public class TeacherSubject {

    private String id;
    private String teacherID;
    private String subjectID;

    @Override
    public String toString() {
        return "TeacherSubject{" +
                "id='" + id + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public TeacherSubject(String id, String teacherID, String subjectID) {
        this.id = id;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }
}
