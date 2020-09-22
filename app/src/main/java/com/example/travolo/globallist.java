package com.example.travolo;

import java.util.ArrayList;
import java.util.Map;

public class globallist {
    private ArrayList<preference> list;
    private String id;

    public ArrayList<preference> getList() {
        return list;
    }

    public void setId(String id){
        this.id  = id;
    }

    public String getId(){
        return id;
    }

    public void setList(ArrayList<preference> list) {
        this.list = list;
    }

    public void addList(ArrayList<preference> list){
        for(int i =0; i< list.size(); i++){
            this.list.add(list.get(i));
        }
    }
    public int getsize(){
        return this.list.size();
    }
    public String getItem(int i){
        return this.list.get(i).getTid();
    }
    public void removeList(int position){
        this.list.remove(position);
    }
    public void logout(){this.id = null;}
    public void deleteList() {
        this.list.clear();
    }
    private static globallist globallist = null;
    public static synchronized globallist getInstance(){
        if(globallist==null){
            globallist = new globallist();
        }
        return globallist;
    }
}
