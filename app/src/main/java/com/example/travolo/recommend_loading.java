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
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class recommend_loading extends AppCompatActivity {
    private ArrayList<plan> item = new ArrayList<>();
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_loading);
        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);

        setRecycle_plan(globallist.getInstance().getId());
    }
    public void setRecycle_plan(final String id){
        String URL ="http://211.253.26.214:15000/recommandTourListPlus";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");//여행지 이름
                        String img = jsonObject.getString("img");//여행지 사진
                        String info = jsonObject.getString("info");//여행지 설명
                        String tid = jsonObject.getString("tid");//여행지 tid
                        item.add(new plan(tid,name,img,info,null,null));
                    }
                    globallist.getInstance().setRecommend(item);//전역변수에 저장
                    Intent intent = new Intent(recommend_loading.this, recommendInsert.class);
                    startActivity(intent);
                    finish();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "서버오류", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "net error", Toast.LENGTH_SHORT).show();
            }
        };

        JSONObject jsonObject = new JSONObject();
        JSONArray jarray = new JSONArray();
        try{
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i<globallist.getInstance().gettidsize();i++){
                JSONObject object = new JSONObject();
                object.put("tid",globallist.getInstance().gettidpos(i));//선택한 여행지의 tid를 서버에 전송하여 저장
                jsonArray.put(object);
            }
            jsonObject.put("user_id",id);//사용자 id
            jsonObject.put("address",globallist.getInstance().getAddress());//여행지 주소
            jsonObject.put("group_no",globallist.getInstance().getGroup_no());//여행일정 번호
            jsonObject.put("item",jsonArray);//여행일정들
            jarray.put(jsonObject);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jarray, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
