package com.zj.demo.entity;

public class ClassroomTime {

    private String id;
    private String classroomId;
    private String timeNum;
    private String state;

    @Override
    public String toString() {
        return "ClassroomTime{" +
                "id='" + id + '\'' +
                ", classroomId='" + classroomId + '\'' +
                ", timeNum='" + timeNum + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(String timeNum) {
        this.timeNum = timeNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ClassroomTime(String id, String classroomId, String timeNum, String state) {
        this.id = id;
        this.classroomId = classroomId;
        this.timeNum = timeNum;
        this.state = state;
    }
}
