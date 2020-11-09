package com.example.travolo;

public class evaluate {
    private String name;
    private String image;
    private String tid;
    private String grade;

    public evaluate(String name, String image, String tid, String grade) {
        this.name = name;
        this.image = image;
        this.tid = tid;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
