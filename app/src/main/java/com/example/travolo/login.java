package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;


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


        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String areadata = textView.getText().toString();//선택한 지역을 전송
                if(list.contains(areadata)) {//리스트에 지역이 있는경우
                    Intent intent = new Intent(login.this, period.class);//기간선택 화면으로 이동
                    intent.putExtra("area", areadata);
                    startActivity(intent);
                }else{//지역이 없는 경우
                    Toast.makeText(getApplicationContext(),"등록되지않은 지명 입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.hamburgermenu_120234); //햄버거 버튼 이미지 지정
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

                else if(id == R.id.plan){//생성된 여행일정 페이지
                    Intent intent2 = new Intent(login.this, historylist.class);
                    intent2.putExtra("id",id);
                    startActivity(intent2);
                }
                else if(id == R.id.event){
                    Intent intent1 = new Intent(login.this, eventlist.class);
                    intent1.putExtra("id",id);
                    startActivity(intent1);
                }
                else if(id == R.id.notice){//공지사항 페이지
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
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
}