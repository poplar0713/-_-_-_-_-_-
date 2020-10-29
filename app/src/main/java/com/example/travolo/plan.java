package com.example.travolo;

public class plan {
    private String name;
    private String image;
    private String info;
    private String tid;

    public plan(String tid, String name, String image, String info) {
        this.name = name;
        this.image = image;
        this.info = info;
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


    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
