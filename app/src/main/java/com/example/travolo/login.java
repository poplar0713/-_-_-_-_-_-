package com.example.travolo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class login extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private List<String> list;
    private AutoCompleteTextView textView;
    private RequestQueue queue;
    Button button;
    long first;
    long second;
    private Toast toast;
    private eventAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<event> item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        final String id = globallist.getInstance().getId();
        list = new ArrayList<>();

        checkarea();//자동완성 기능을 위한 지역을 받아옴

        textView = findViewById(R.id.area);
        textView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, list));//여행지 검색 시 자동 완성을 위한 리스트 설정

        textView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });


        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String areadata = textView.getText().toString();//선택한 지역을 전송
                if(list.contains(areadata)) {//리스트에 지역이 있는경우
                    Intent intent = new Intent(login.this, period.class);//기간선택 화면으로 이동
                    globallist.getInstance().setAddress(areadata);
                    intent.putExtra("area", areadata);
                    startActivity(intent);
                }else{//지역이 없는 경우
                    Toast.makeText(getApplicationContext(),"등록되지않은 지명 입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        setRecycler();

        adapter = new eventAdapter(item,this);
        final RecyclerView recyclerView = findViewById(R.id.recycler_best);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while (true) {
                        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {//버튼을 누를시 해당 일차의 여행일정으로 이동
                            @Override
                            protected int getVerticalSnapPreference() {
                                return LinearSmoothScroller.SNAP_TO_START;//해당 일차를 화면의 제일 위로 표시
                            }
                        };
                        smoothScroller.setTargetPosition(num);
                        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);//리스트에 적용
                    if(num < item.size()){
                        num++;
                    }else{
                        num = 0;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.hambergermenu); //햄버거 버튼 이미지 지정
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id==R.id.homepage){//홈화면(여행지 검색 페이지)으로 이동

                }

                else if(id == R.id.plan) {//생성된 여행일정 페이지
                    Intent intent2 = new Intent(login.this, historylist.class);
                    startActivity(intent2);
                }
                else if(id == R.id.notice){//공지사항 페이지
                }
                else if(id == R.id.setting){//환경설정 페이지
                    Intent intent3 = new Intent(login.this, setting.class);
                    startActivity(intent3);
                }

                return true;
            }
        });

        View nav_haeder = navigationView.getHeaderView(0);
        TextView id_text = nav_haeder.findViewById(R.id.nav_id);
        id_text.setText(id);
        Button button = nav_haeder.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//로그아웃 버튼 기능 구현
                        Intent i = new Intent(login.this, MainActivity.class);//로그인화면으로 이동
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();
                        globallist.getInstance().logout();//전역변수에서 아이디 삭제
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);//햄버거 메뉴를 띄움
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {//뒤로가기 버튼을 누를시 동작
        second = System.currentTimeMillis();
        Toast.makeText(login.this,"한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
        if(second - first<2000){
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
        }
        first = System.currentTimeMillis();
    }

    public void checkarea(){

        String URL = "http://211.253.26.214:8080/travolo2/post/search";//통신할 서버 url

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");//여행지들을 리스트에 저장
                        list.add(name);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,null, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//배경화면을 누를경우 키보드가 내려감
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    public void setRecycler(){
        String uid= globallist.getInstance().getId();
        String URL ="http://211.253.26.214:15000/recommandTourList";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                adapter.notifyDataSetChanged();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("label");//여행지의 이름, 사진, tid를 전달받음
                        String img = jsonObject.getString("img");
                        String ad = jsonObject.getString("address");
                        item.add(new event(name,ad,img));
                        adapter.notifyItemInserted(0);
                    }
                }catch (JSONException e){
                    Toast.makeText(login.this," list error", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,"newt error", Toast.LENGTH_LONG).show();
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("user_id",uid);
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}