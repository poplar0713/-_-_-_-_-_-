package com.example.travolo;

public class searchitem {
    private String areaName;
    private String areaaddress;

    public searchitem(String areaName, String areaaddress) {
        this.areaName = areaName;
        this.areaaddress = areaaddress;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAreaaddress() {
        return areaaddress;
    }
}
