package com.zj.demo.entity;

public class Paike {

    private String id;
    private String majorId;
    private String subjectId;
    private String classroomId;
    private String teacherId;
    private String timeNum;

    public Paike(String id, String majorId, String subjectId, String classroomId, String teacherId, String timeNum) {
        this.id = id;
        this.majorId = majorId;
        this.subjectId = subjectId;
        this.classroomId = classroomId;
        this.teacherId = teacherId;
        this.timeNum = timeNum;
    }

    @Override
    public String toString() {
        return "Paike{" +
                "id='" + id + '\'' +
                ", majorId='" + majorId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", classroomId='" + classroomId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", timeNum='" + timeNum + '\'' +
                '}';
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(String timeNum) {
        this.timeNum = timeNum;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

}
