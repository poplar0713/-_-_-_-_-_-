package com.example.travolo;

public class event {
    private String contry;
    private String name;
    private String content;

    public event(String contry, String name, String content) {
        this.contry = contry;
        this.name = name;
        this.content = content;
    }

    public String getContry() {
        return contry;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

}
