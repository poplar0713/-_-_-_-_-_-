package com.example.travolo;

public class plan {
    private String name;
    private String image;
    private String grade;
    private String tid;

    public plan(String tid, String name, String image, String grade) {
        this.name = name;
        this.image = image;
        this.grade = grade;
        this.tid = tid;
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

    public String getGrade() {
        return grade;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
