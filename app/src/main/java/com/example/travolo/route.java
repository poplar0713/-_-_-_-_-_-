package com.example.travolo;

public class route {
    private double x;
    private double y;
    private int flag;

    public route(double x, double y, int flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
