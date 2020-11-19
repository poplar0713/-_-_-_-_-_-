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

import org.json.JSONException;
import org.json.JSONObject;


public class progress_loading extends AppCompatActivity {

    String area,from,to;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_loading);

        Intent intent = getIntent();

        area = globallist.getInstance().getAddress();
        from = globallist.getInstance().getStartdate();
        to = globallist.getInstance().getEnddate();

        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);//로딩화면을 화면에 띄움
        send(area,from,to);//일정 생성을 위한 서버에 정보 전송
    }
    public void send(final String a,final String f, final String t){
        String URL = "http://211.253.26.214:15000/executeAnalysis";//통신할 서버 url
        String id = globallist.getInstance().getId();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        String group_no = response.getString("group_no");
                        globallist.getInstance().setGroup_no(group_no);
                        Intent intent = new Intent(progress_loading.this, planlist.class);//일정 표시화면으로 이동
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "networkerror!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(progress_loading.this, period.class);//이전화면으로 돌아감
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
            jsonObject.put("area",a);
            jsonObject.put("user_id",id);
            jsonObject.put("from",f);
            jsonObject.put("to",t);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//일정생성이 기간이 길수록 오래 걸리기 때문에 억지로 통신 시간을 늘려준다
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(loginRequest);//전송
    }
}
