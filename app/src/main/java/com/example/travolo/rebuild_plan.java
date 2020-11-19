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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class rebuild_plan extends AppCompatActivity {
    private ArrayList<plan> item = new ArrayList<>();
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rebuild_plan);
        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);

        sendData(globallist.getInstance().getId());
    }
    public void sendData(final String id) {
        String URL = "http://211.253.26.214:15000/newRoute";//통신할 서버 url
        ArrayList<plan> editlist = new ArrayList<>();
        editlist.addAll(globallist.getInstance().getPlans());

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                globallist.getInstance().removeplans();
                item.clear();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");//여행지 이름
                        String img = jsonObject.getString("img");//여행지 사진
                        String info = jsonObject.getString("info");//여행지 설명
                        String tid = jsonObject.getString("tid");//여행지 tid
                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        item.add(new plan(tid,name,img,info,date,time));
                    }
                    globallist.getInstance().setPlans(item);
                    Intent intent = new Intent(rebuild_plan.this, afterplan.class);
                    startActivity(intent);
                    finish();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "서버오류", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송
        JSONArray jarray = new JSONArray();
        try{
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i<editlist.size();i++){
                JSONObject object = new JSONObject();
                object.put("tid",editlist.get(i).getTid());//선택한 여행지의 tid를 서버에 전송하여 저장
                jsonArray.put(object);
            }
            jsonObject.put("start_date",globallist.getInstance().getStartdate());
            jsonObject.put("end_date",globallist.getInstance().getEnddate());
            jsonObject.put("user_id",id);
            jsonObject.put("tour_list",jsonArray);
            jarray.put(jsonObject);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jarray, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);//전송
    }
}
