package com.zj.demo.entity;

public class TeacherTime {

    private String id;
    private String teacherId;
    private String timeNum;
    private String state;

    @Override
    public String toString() {
        return "TeacherTime{" +
                "id='" + id + '\'' +
                ", teacherId='" + teacherId + '\'' +
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public TeacherTime(String id, String teacherId, String timeNum, String state) {
        this.id = id;
        this.teacherId = teacherId;
        this.timeNum = timeNum;
        this.state = state;
    }
}
