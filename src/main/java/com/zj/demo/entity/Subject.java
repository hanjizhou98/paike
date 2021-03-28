package com.zj.demo.entity;

public class Subject {
    private String id;
    private String name;
    private String type;
    private String numOfWeek;

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", numOfWeek='" + numOfWeek + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumOfWeek() {
        return numOfWeek;
    }

    public void setNumOfWeek(String numOfWeek) {
        this.numOfWeek = numOfWeek;
    }

    public Subject(String id, String name, String type, String numOfWeek) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.numOfWeek = numOfWeek;
    }
}
