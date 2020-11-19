package com.example.travolo;

import java.util.ArrayList;
import java.util.Map;

public class globallist {
    private ArrayList<preference> list;//선택한 여행지를 저장하기 위한 리스트
    private ArrayList<Integer> position;
    private ArrayList<plan> plans;
    private ArrayList<plan> recommend;
    private ArrayList<String> tid;
    private String id;//로그인을 위한 아이디 저장
    private String startdate;
    private String enddate;
    private int date;
    private String address;
    private String group_no;

    public String getAddress() {
        return address;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setTid(ArrayList<String> tid){
        this.tid = tid;
    }
    public ArrayList<String> getTid(){
        return tid;
    }
    public String gettidpos(int position){
        return tid.get(position);
    }
    public void removeTid(){
        this.tid.clear();
    }
    public void addtid(String t){
        this.tid.add(t);
    }
    public int gettidsize(){
        if(this.tid == null)
            return 0;
        return this.tid.size();
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setRecommend(ArrayList<plan> recommend){
        this.recommend = recommend;
    }
    public ArrayList<plan> getRecommend(){
        return recommend;
    }
    public void removerecommend(){
        this.recommend.clear();
    }

    public void setPlans(ArrayList<plan> plans){
        this.plans = plans;
    }
    public ArrayList<plan> getPlans(){
        return plans;
    }
    public void addplans (plan p){
        this.plans.add(p);
    }
    public void deleteplans (int position){
        this.plans.remove(position);
    }
    public void removeplans (){
        this.plans.clear();
    }
    public String getGroup_no() {
        return group_no;
    }

    public void setGroup_no(String group_no) {
        this.group_no = group_no;
    }

    public ArrayList<preference> getList() {
        return list;
    }

    public void setId(String id){
        this.id  = id;
    }

    public String getId(){
        return id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setList(ArrayList<preference> list) {
        this.list = list;
    }

    public void setPosition(ArrayList<Integer> pos){
        this.position = pos;
    }

    public int getPosition(int position){
        return this.position.get(position);
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
    }//아이템의 tid 반환

    public void removeList(int position){
        this.list.remove(position);
    }//전역변수의 리스트에 존재하는 아이템하나설정

    public void logout(){this.id = null;}//아이디를 초기화

    public void deleteList() {
        this.list.clear();
    }//전역변수의 리스트를 초기화

    private static globallist globallist = null;

    public static synchronized globallist getInstance(){
        if(globallist==null){
            globallist = new globallist();//전역변수가 없을경우 전역변수 생성
        }
        return globallist;//존재하면 전역변수 반환
    }
}
