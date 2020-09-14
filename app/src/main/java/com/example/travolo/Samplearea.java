package com.example.travolo;

import java.util.ArrayList;

public class Samplearea {
    ArrayList<area> items = new ArrayList<>();

    public ArrayList<area> getItems(){
        area area1 = new area("서울");
        area area2 = new area("경기도");
        area area3 = new area("강원도");
        area area4 = new area("충청북도");
        area area5 = new area("충청남도");
        area area6 = new area("대전");
        area area7 = new area("인천");
        area area8 = new area("경상남도");
        area area9 = new area("경상북도");
        area area10 = new area("대구");
        area area11 = new area("부산");
        area area12 = new area("전라남도");
        area area13 = new area("전라북도");
        area area14 = new area("광주");
        area area15 = new area("제주도");
        area area16 = new area("울산");

        items.add(area1);
        items.add(area2);
        items.add(area3);
        items.add(area4);
        items.add(area5);
        items.add(area6);
        items.add(area7);
        items.add(area8);
        items.add(area9);
        items.add(area10);
        items.add(area11);
        items.add(area12);
        items.add(area13);
        items.add(area14);
        items.add(area15);
        items.add(area16);

        return items;
    }
}
