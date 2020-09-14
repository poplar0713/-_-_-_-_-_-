package com.example.travolo;

import java.util.ArrayList;

public class historysample {
    ArrayList<history> items = new ArrayList<>();

    public ArrayList<history> getItems(){
        history history1 = new history("1번공지","1");
        history history2 = new history("2번공지","2");
        history history3 = new history("3번공지","3");

        items.add(history1);
        items.add(history2);
        items.add(history3);

        return items;
    }
}
