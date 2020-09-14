package com.example.travolo;

import java.io.Serializable;

public class preference implements Serializable {
    private String name;
    private String image;
    private String tid;

    public preference(){

    }

    public preference(String name, String image,String tid) {
        this.name = name;
        this.image = image;
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
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
}
