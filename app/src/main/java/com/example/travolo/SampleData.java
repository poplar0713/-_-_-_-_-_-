package com.example.travolo;

import java.util.ArrayList;

public class SampleData {
    ArrayList<event> items = new ArrayList<>();

    public ArrayList<event> getItems(){
        event event1 = new event("진해", "군항제", "벚꽃축제");

        event event2 = new event("진안고원", "수박축제", "수박축제");

        event event3 = new event("서귀포", "문화재야행", "문화제 축제");

        items.add(event1);
        items.add(event2);
        items.add(event3);

        items.add(event1);
        items.add(event2);
        items.add(event3);

        items.add(event1);
        items.add(event2);
        items.add(event3);

        return items;
    }
}
