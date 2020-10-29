package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class preferencelist extends AppCompatActivity {//해당 지역의 여행지 표시 화면

    RecyclerView recyclerView;
    private preferencAdapter adapter;
    private RequestQueue queue;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);//한줄에 4개씩 여행지를 띄움
    private ArrayList<preference> item = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencelist);
        Intent intent = getIntent();
        String area = intent.getExtras().getString("area");//선택한 지역의 이름을 전달받음
        setRecycle(area);//해당지역의 여행지를 전달받음
        recyclerView = findViewById(R.id.recycler_area);
        adapter = new preferencAdapter(this, item);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);//리사이클러뷰 표시

        final Button button = findViewById(R.id.bb);//확인버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendData(adapter);//전역변수에 선택한 여행지 저장
                    finish();
            }
        });


    }

    public void sendData(preferencAdapter adapter){
        if(globallist.getInstance().getList() == null) {//선택한 여행지 리스트가 없을경우 새로생성
            globallist.getInstance().setList(adapter.getItemselect());
        }else{//이미 존재한다면 추가
            globallist.getInstance().addList(adapter.getItemselect());
        }
        Intent intent1 = new Intent(preferencelist.this, arealist.class);//다시 지역선택 화면으로 이동
        startActivity(intent1);
        finish();
    }
    public void setRecycle(final String area){
        String URL ="http://211.253.26.214:8080/travolo2/post/randomLabel";//통신할 URL
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
                        item.add(new preference(name,img,tid));
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
        params.put("area", area);//지역이름을 전달
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
