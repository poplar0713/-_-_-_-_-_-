package com.example.travolo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class areainfo extends AppCompatActivity {
    TextView areaname,areainfo,areaaddress,areatel,areaopen,areaclose,areahomepage,areafee;
    ImageView areaimg;
    LinearLayout layout;
    private ArrayList<String> item = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areainfo);

        Intent intent = getIntent();
        String tid = intent.getExtras().getString("tid");

        sendRequest(tid);




    }
    public void sendRequest(final String areatid) {//서버통신
        String URL = "http://211.253.26.214:8080/travolo2/post/tourInfo";//통신할 서버 url
        areaname = findViewById(R.id.areaname);
        areainfo = findViewById(R.id.areainfo);
        areaaddress = findViewById(R.id.areaaddress);
        areatel = findViewById(R.id.areatel);
        areaopen = findViewById(R.id.areaopentime);
        areaclose = findViewById(R.id.areaclosetime);
        areahomepage = findViewById(R.id.areahomepage);
        areafee = findViewById(R.id.areacost);
        areaimg = findViewById(R.id.areaimage);
        layout = findViewById(R.id.areainfolayout);

        final TMapView tMapView = new TMapView(com.example.travolo.areainfo.this);
        tMapView.setSKTMapApiKey("l7xxa3a9929fe00448dba86b5fd5e81648eb");



        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                item.clear();
                try{
                    String name = response.getString("label");//여행지 이름
                    String img = response.getString("depiction");//여행지 사진
                    String info = response.getString("description");//여행지 설명
                    String address = response.getString("address");
                    String fee = response.getString("fee");
                    String tel = response.getString("tel");
                    String open = response.getString("opentime");
                    String close = response.getString("closed");
                    String homepage = response.getString("uri");
                    double x = Double.parseDouble(response.getString("gps_lat"));
                    double y = Double.parseDouble(response.getString("gps_long"));
                    areaname.setText(name);
                    Glide.with(com.example.travolo.areainfo.this).load(img).into(areaimg);
                    areainfo.setText(info);
                    areaaddress.setText(address);
                    areafee.setText(fee);
                    areatel.setText(tel);
                    areaopen.setText(open);
                    areaclose.setText(close);
                    areahomepage.setText(homepage);
                    TMapMarkerItem markerItem = initMap(x,y);
                    tMapView.addMarkerItem("makerItem",markerItem);
                    tMapView.setCenterPoint(y,x);
                    layout.addView(tMapView);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"login error!",Toast.LENGTH_SHORT).show();
                return;
            }
        };

        Map<String, String> params = new HashMap<String, String>();//맵형태로 정보 전달
        params.put("tid",areatid);//아이디 전송

        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송

    }
    public TMapMarkerItem initMap(double x, double y){
        TMapMarkerItem markerItem = new TMapMarkerItem();
        TMapPoint tMapPoint = new TMapPoint(x,y);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.mark);
        markerItem.setIcon(bitmap);
        markerItem.setPosition(0.5f,1.0f);
        markerItem.setTMapPoint(tMapPoint);
        return markerItem;
    }
}
