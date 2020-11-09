package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    LinearLayout linearLayout,liner;
    ArrayList<route> item;

    Button bt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_map);


        String group_no = globallist.getInstance().getGroup_no();
        String id = globallist.getInstance().getId();

        linearLayout = findViewById(R.id.Tmapview);
        final TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xxa3a9929fe00448dba86b5fd5e81648eb");
        linearLayout.addView(tMapView);

        setRoute(id, group_no);
        liner = findViewById(R.id.datebtn);
        DisplayMetrics dm = getResources().getDisplayMetrics();//dp치수를 설정하기위한 메소드
        int size = Math.round(10 * dm.density);
        for(int i = 0; i<globallist.getInstance().getDate(); i++) {
            Button btn = new Button(this);//새로운 버튼을 생성
            btn.setText(i + 1 + "일");//날짜 표시
            final int position = i * 4;
            btn.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.btn));//버튼 이미지 설정
            btn.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(size * 4, size * 4);//버튼 사이즈 조절
            param.bottomMargin = size;//버튼 마진 설정
            param.leftMargin = size;//버튼 마진 설정
            param.rightMargin = size;//버튼 마진 설정
            btn.setLayoutParams(param);
            final int j = i*4;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TMapPoint tMapPointStart = new TMapPoint(item.get(j).getX(),item.get(j).getY()); // SKT타워(출발지)
                    TMapPoint tMapPointEnd = new TMapPoint(item.get(j+3).getX(),item.get(j+3).getY());
                    ArrayList<TMapPoint> pathlist = new ArrayList<>();
                    ArrayList<TMapPoint> point = new ArrayList<>();
                    pathlist.add(new TMapPoint(item.get(j+1).getX(),item.get(j+1).getY()));
                    pathlist.add(new TMapPoint(item.get(j+2).getX(),item.get(j+2).getY()));// N서울타워(목적지)
                    try {
                        searchRoute(tMapPointStart,tMapPointEnd,tMapView, pathlist);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    point.add(new TMapPoint(item.get(j).getX(),item.get(j).getY()));
                    point.add(new TMapPoint(item.get(j+1).getX(),item.get(j+1).getY()));
                    point.add(new TMapPoint(item.get(j+2).getX(),item.get(j+2).getY()));
                    point.add(new TMapPoint(item.get(j+3).getX(),item.get(j+3).getY()));

                    TMapInfo info = tMapView.getDisplayTMapInfo(point);
                    TMapPoint tMapPoint = info.getTMapPoint();
                    tMapView.setCenterPoint(tMapPoint.getLongitude(),tMapPoint.getLatitude());
                    tMapView.setZoomLevel(info.getTMapZoomLevel());
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
                        Intent i = new Intent(plan_map.this, MainActivity.class);
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

    public void setRoute(final String id, final String group){
        String URL ="http://211.253.26.214:8080/travolo2/post/optimalRoad";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item = new ArrayList<>();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String x = jsonObject.getString("gps_lat");//여행지 이름
                        String y = jsonObject.getString("gps_long");//여행지 사진
                        item.add(new route(Double.parseDouble(x),Double.parseDouble(y)));
                    }
                }catch (JSONException e){
                    Toast.makeText(context,"서버오류",Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"서버오류",Toast.LENGTH_SHORT).show();
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("user_id", id);
        params.put("group_no", group);
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void searchRoute(TMapPoint start, TMapPoint end, final TMapView mapView, ArrayList<TMapPoint> passlist) throws ParserConfigurationException, SAXException, IOException {
        TMapData data = new TMapData();
        data.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, start, end, passlist, 0, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(R.color.colorAccent);
                tMapPolyLine.setLineWidth(10);
                mapView.addTMapPath(tMapPolyLine);
            }
        });
        Bitmap st = BitmapFactory.decodeResource(context.getResources(),R.drawable.mark);
        mapView.setTMapPathIcon(st,st,st);
    }
}
