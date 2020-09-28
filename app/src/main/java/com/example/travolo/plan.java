package com.example.travolo;

public class plan {
    private String name;
    private String image;
    private String grade;

    public plan(String name, String image, String grade) {
        this.name = name;
        this.image = image;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
