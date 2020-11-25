package com.example.travolo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

public class plan_map extends AppCompatActivity {
    private RequestQueue queue;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    LinearLayout linearLayout, liner;
    Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_map);

        final ArrayList<route> item = globallist.getInstance().getRoutes();

        String id = globallist.getInstance().getId();

        linearLayout = findViewById(R.id.Tmapview);//Tmap을 띄우기 위한 레이아웃
        final TMapView tMapView = new TMapView(this);//Tmap뷰 생성

        tMapView.setSKTMapApiKey("l7xxa3a9929fe00448dba86b5fd5e81648eb");//보안코드
        linearLayout.addView(tMapView);//티맵뷰 레이아웃에 추가

        liner = findViewById(R.id.datebtn);//일자 버튼 추가를 위한 레이아웃

        DisplayMetrics dm = getResources().getDisplayMetrics();//dp치수를 설정하기위한 메소드
        int size = Math.round(10 * dm.density);
        for (int i = 0; i < globallist.getInstance().getDate(); i++) {
            Button btn = new Button(this);//새로운 버튼을 생성
            btn.setText(i + 1 + "일");//날짜 표시
            btn.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.btn));//버튼 이미지 설정
            btn.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(size * 4, size * 4);//버튼 사이즈 조절
            param.bottomMargin = size;//버튼 마진 설정
            param.leftMargin = size;//버튼 마진 설정
            param.rightMargin = size;//버튼 마진 설정
            btn.setLayoutParams(param);
            final int j = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//버튼을 눌렀을 때 경로를 설정하기 위한 코드
                    int start = 0, end = 0;
                    int tit = 0;
                    while (true) {
                        if (item.get(tit).getFlag() == j) {
                            start = tit;
                            end = start;
                            break;
                        }
                        tit++;
                    }
                    for (int k = start; k < item.size(); k++) {
                        if (item.get(k).getFlag() == j) {
                            end++;
                        }
                    }
                    Log.d("start-end_check", "ClickListener:" + start + " " + end);
                    TMapPoint tMapPointStart = new TMapPoint(item.get(start).getX(), item.get(start).getY()); //출발지
                    TMapPoint tMapPointEnd = new TMapPoint(item.get(end - 1).getX(), item.get(end - 1).getY());//도착지
                    ArrayList<TMapPoint> pathlist = new ArrayList<>();
                    ArrayList<TMapPoint> point = new ArrayList<>();
                    for (int t = start; t < end; t++) {//경유지 추가코드
                        pathlist.add(new TMapPoint(item.get(t).getX(), item.get(t).getY()));
                    }
                    if (end != start + 1)
                        pathlist.remove((end - start) - 1);
                    pathlist.remove(0);

                    try {
                        searchRoute(tMapPointStart, tMapPointEnd, tMapView, pathlist);//경로 설정 및 경로 추가
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int k = start; k < end; k++) {
                        point.add(new TMapPoint(item.get(k).getX(), item.get(k).getY()));
                    }//해당 경로의 최적 크기와 중간지점을 계산하기 위한 좌표 설정
                    TMapInfo info = tMapView.getDisplayTMapInfo(point);
                    TMapPoint tMapPoint = info.getTMapPoint();
                    tMapView.setCenterPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());//경로의 중앙으로 화면 설정
                    tMapView.setZoomLevel(info.getTMapZoomLevel());//경로에 알맞은 크기로 화면 설정
                }
            });
            liner.addView(btn);//레이아웃에 버튼 추가
        }

        bt = findViewById(R.id.planlistbtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

                if (id == R.id.homepage) {
                    Intent intent2 = new Intent(plan_map.this, login.class);
                    startActivity(intent2);
                    finish();
                } else if (id == R.id.plan) {//생성된 여행일정 페이지
                    Intent intent2 = new Intent(plan_map.this, historylist.class);
                    startActivity(intent2);
                } else if (id == R.id.notice) {//공지사항 페이지
                } else if (id == R.id.setting) {//환경설정 페이지
                    Intent intent3 = new Intent(plan_map.this, setting.class);
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
            public void onClick(View v) {//로그아웃 버튼 선택시
                new AlertDialog.Builder(context).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(plan_map.this, MainActivity.class);
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();
                        globallist.getInstance().logout();
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);//화면에 햄버거 메뉴를 띄움
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchRoute(TMapPoint start, TMapPoint end, final TMapView mapView, ArrayList<TMapPoint> passlist) throws ParserConfigurationException, SAXException, IOException {
        TMapData data = new TMapData();
        data.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, start, end, passlist, 0, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(R.color.colorAccent);//라인 색
                tMapPolyLine.setLineWidth(10);//라인 크기
                mapView.addTMapPath(tMapPolyLine);//라인 추가
            }
        });
        Bitmap st = BitmapFactory.decodeResource(context.getResources(), R.drawable.mark);//목적지 위치 아이콘
        mapView.setTMapPathIcon(st, st, st);
    }
}
