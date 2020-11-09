package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class evaluateplan extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private evaluateAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<evaluate> item = new ArrayList<>();
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluateplan);

        String num = globallist.getInstance().getGroup_no();
        setRecycler(num);

        adapter = new evaluateAdapter(item,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_evaluate);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn = findViewById(R.id.evb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendgrade(adapter);
            }
        });

        String id = globallist.getInstance().getId();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.hambergermenu); //뒤로가기 버튼 이미지 지정
        mDrawerLayout = findViewById(R.id.drawer_layout1);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.plan){
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.event){
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.notice){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.setting){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
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
                        Intent i = new Intent(evaluateplan.this, MainActivity.class);
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
                mDrawerLayout.openDrawer(GravityCompat.START);//화면에 햄버거 메뉴를 띄움
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRecycler(final String hisnum){
        String uid= globallist.getInstance().getId();
        String URL ="http://211.253.26.214:8080/travolo2/post/printSchedule";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                adapter.notifyDataSetChanged();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");//여행지의 이름, 사진, tid를 전달받음
                        String img = jsonObject.getString("img");
                        String tid = jsonObject.getString("tid");
                        item.add(new evaluate(name,img,tid,"3"));
                        adapter.notifyItemInserted(0);
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

        Map<String, String> params = new HashMap<>();
        params.put("group_no", hisnum);//지역이름을 전달
        params.put("user_id",uid);
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void sendgrade(final evaluateAdapter adapter){
        String URL ="http://211.253.26.214:8080/travolo2/post/evaluateTour";//통신할 URL
        ArrayList<evaluate> items = adapter.getItems();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String success = jsonObject.getString("success");//여행지의 이름, 사진, tid를 전달받음
                        if(success.equals("1")){
                            updateMtrx();
                        }else{
                            Toast.makeText(context,"서버오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(context,"네트워크 에러",Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i<items.size(); i++){
            Map<String, String> params = new HashMap<>();
            evaluate ev = items.get(i);
            params.put("tid",ev.getTid());
            params.put("grade",ev.getGrade());
            JSONObject jsonObject = new JSONObject(params);
            jsonArray.put(jsonObject);
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void updateMtrx(){
        String URL = "http://211.253.26.214:15000/ratingMatrix";//통신할 서버 url
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(evaluateplan.this, historylist.class);//여행지 검색화면으로 이동
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "IBCF error!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "networkerror!", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, null, responseListener, errorListener);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//서버와의 통신시간을 강제로 늘림
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송
    }
}
