package com.example.travolo;

public class event {
    private String img;
    private String name;
    private String address;

    public event(String name, String address, String img) {
        this.name = name;
        this.address = address;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.address = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return address;
    }

}
