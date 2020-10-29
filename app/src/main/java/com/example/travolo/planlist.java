package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Map;

public class planlist extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    RecyclerView recyclerView;
    private planAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<plan> item = new ArrayList<>();
    LinearLayout layout;
    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist);
        DisplayMetrics dm = getResources().getDisplayMetrics();//dp치수를 설정하기위한 메소드
        int size = Math.round(10 * dm.density);//size를 10dp로 설정
        layout = findViewById(R.id.btnline);//버튼을 생성할 레이아웃
        for(int i = 0; i<globallist.getInstance().getDate(); i++){
            Button btn = new Button(this);//새로운 버튼을 생성
            btn.setText(i+1+"일");//날짜 표시
            final int position = i * 4;
            btn.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.btn));//버튼 이미지 설정
            btn.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(size * 4,size * 4);//버튼 사이즈 조절
            param.bottomMargin = size;//버튼 마진 설정
            param.leftMargin = size;//버튼 마진 설정
            param.rightMargin = size;//버튼 마진 설정
            btn.setLayoutParams(param);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()){//버튼을 누를시 해당 일차의 여행일정으로 이동
                        @Override
                        protected int getVerticalSnapPreference() {
                            return LinearSmoothScroller.SNAP_TO_START;//해당 일차를 화면의 제일 위로 표시
                        }
                    };
                    smoothScroller.setTargetPosition(position);
                    recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);//리스트에 적용
                }
            });
            layout.addView(btn);//레이아웃에 버튼 추가
        }

        final String id = globallist.getInstance().getId();

        setRecycle_plan(id);
        recyclerView = findViewById(R.id.recycler_plan);
        adapter = new planAdapter(this, item);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClicklistener(new onPlanItemClickListener() {
            @Override
            public void onItemClick(planAdapter.ViewHolder viewHolder, View view, int position) {
                plan planitem = adapter.getItems(position);
                final String tid = planitem.getTid();
                Intent intent = new Intent(planlist.this, areainfo.class);
                intent.putExtra("tid",tid);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.mapbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(planlist.this, plan_map.class);//일정 표시화면으로 이동
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

                if(id == R.id.plan){
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.event){
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {//로그아웃 버튼 선택시
                new AlertDialog.Builder(context).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(planlist.this, MainActivity.class);
                        globallist.getInstance().logout();
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
    public void setRecycle_plan(final String id){
        String URL ="http://211.253.26.214:8080/travolo2/post/schedule";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                adapter.notifyDataSetChanged();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");//여행지 이름
                        String img = jsonObject.getString("img");//여행지 사진
                        String info = jsonObject.getString("info");//여행지 설명
                        String tid = jsonObject.getString("tid");//여행지 tid
                        item.add(new plan(tid,name,img,info));
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
        params.put("user_id", id);
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {//뒤로가기 버튼을 누를시
        super.onBackPressed();
        Intent i = new Intent(planlist.this, login.class);//메인화면으로 이동
        startActivity(i);
        finish();
    }
}