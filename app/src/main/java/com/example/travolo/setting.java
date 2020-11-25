package com.example.travolo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class setting extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    TextView text;
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        final String id = globallist.getInstance().getId();
        text = findViewById(R.id.userid);
        text.setText(id);
        btn1 = findViewById(R.id.changepwd);
        btn2 = findViewById(R.id.deleteid);
        btn3 = findViewById(R.id.sns_link);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, checkpwd.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("회원탈퇴").setMessage("정말 탈퇴 하시겠습니까?").setPositiveButton("회원탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteid();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, sns_link_id.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.hambergermenu); //뒤로가기 버튼 이미지 지정
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                if(id == R.id.homepage){
                    Intent intent2 = new Intent(setting.this, login.class);
                    startActivity(intent2);
                    finish();
                }
                else if(id == R.id.plan) {//생성된 여행일정 페이지
                    Intent intent2 = new Intent(setting.this, historylist.class);
                    startActivity(intent2);
                }
                else if(id == R.id.notice){//공지사항 페이지
                }
                else if(id == R.id.setting){//환경설정 페이지
                    Intent intent3 = new Intent(setting.this, setting.class);
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
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(setting.this, MainActivity.class);
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();
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
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteid(){
        String URL = "http://211.253.26.214:8080/travolo2/post/withDrawal";//통신할 서버 url
        final String id = globallist.getInstance().getId();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(setting.this, MainActivity.class);//일정 표시화면으로 이동
                        globallist.getInstance().logout();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "error!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "서버오류", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송

        try{
            jsonObject.put("user_id",id);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(loginRequest);//전송
    }
}