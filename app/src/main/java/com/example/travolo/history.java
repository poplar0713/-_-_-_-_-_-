package com.example.travolo;

public class history {
    private String name;
    private String num;
    private String historynum;
    private int date;

    public history(String name, String num, String historynum, int date) {
        this.name = name;
        this.num = num;
        this.historynum  = historynum;
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHistorynum() {
        return historynum;
    }

    public void setHistorynum(String historynum) {
        this.historynum = historynum;
    }

    public String getName() {
        return name;
    }

    public String getNum() {
        return num;
    }
}
