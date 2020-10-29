package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class preference_loading extends AppCompatActivity {//선호도 조사를 등록하기 위한 로딩화면

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_loading);
        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);
        String id = globallist.getInstance().getId();
        sendData(id);
    }
    public void sendData(final String id) {
        String URL = "http://211.253.26.214:8080/travolo2/post/preference";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        updateMtrx();//메트릭스 업데이트
                    } else {
                        Toast.makeText(getApplicationContext(), "sever error!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
                return;
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송

        try{
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i<globallist.getInstance().getsize();i++){
                JSONObject object = new JSONObject();
                object.put("tid",globallist.getInstance().getItem(i));//선택한 여행지의 tid를 서버에 전송하여 저장
                jsonArray.put(object);
            }
            jsonObject.put("user_id",id);
            jsonObject.put("item",jsonArray);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        if (globallist.getInstance().getsize() > 19)//선택한 여행지 개수가 20개 이상인 경우 전송
            queue.add(loginRequest);//전송
        else
            Toast.makeText(getApplicationContext(),"최소 20개를 선택해 주세요",Toast.LENGTH_SHORT).show();
    }
    public void updateMtrx(){
        String URL = "http://211.253.26.214:15000/pyPreference";//통신할 서버 url
        final String user_id = globallist.getInstance().getId();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(preference_loading.this, login.class);//여행지 검색화면으로 이동
                        globallist.getInstance().deleteList();//선호도 선택 리스트를 초기화
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//서버와의 통신시간을 강제로 늘림
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송
    }
}
