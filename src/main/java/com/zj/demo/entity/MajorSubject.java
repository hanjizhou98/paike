package com.zj.demo.entity;

public class MajorSubject {

    private String id;
    private String majorID;
    private String subjectID;

    @Override
    public String toString() {
        return "MajorSubject{" +
                "id='" + id + '\'' +
                ", majorID='" + majorID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorID() {
        return majorID;
    }

    public void setMajorID(String majorID) {
        this.majorID = majorID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public MajorSubject(String id, String majorID, String subjectID) {
        this.id = id;
        this.majorID = majorID;
        this.subjectID = subjectID;
    }
}
