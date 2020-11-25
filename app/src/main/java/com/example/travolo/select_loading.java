package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class select_loading extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_loading);
        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);

        send();

    }
    public void send(){
        String URL = "http://211.253.26.214:15000/schedulePlusTour";//통신할 서버 url
        String id = globallist.getInstance().getId();
        String a = globallist.getInstance().getAddress();
        String f = globallist.getInstance().getStartdate();
        String t = globallist.getInstance().getEnddate();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        String group_no = response.getString("group_no");
                        globallist.getInstance().setGroup_no(group_no);
                        Intent intent = new Intent(select_loading.this, planlist.class);//일정 표시화면으로 이동
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "networkerror!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(select_loading.this, select_place.class);//이전화면으로 돌아감
                        startActivity(intent);
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
                Toast.makeText(getApplicationContext(), "서버오류", Toast.LENGTH_SHORT).show();
                finish();
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
            jsonObject.put("base_address",a);//선택 지역
            jsonObject.put("user_id",id);//유저 id
            jsonObject.put("start_date",f);//시작일
            jsonObject.put("end_date",t);//종료일
            jsonObject.put("tour_list",jsonArray);//여행지 리스트
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//일정생성이 기간이 길수록 오래 걸리기 때문에 억지로 통신 시간을 늘려준다
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(loginRequest);//전송
    }
}
