package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class select_place_search extends AppCompatActivity {
    RecyclerView recyclerView;
    private select_place_searchAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<plan> item = new ArrayList<>();
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_place_search);

        Intent intent = getIntent();
        String area = intent.getExtras().getString("area");


        setRecycle(area);

        recyclerView = findViewById(R.id.recycler_search_result);
        adapter = new select_place_searchAdapter(item,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);//리사이클러뷰 표시

        button = findViewById(R.id.add_place);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globallist.getInstance().getList() == null) {//선택한 여행지 리스트가 없을경우 새로생성
                    globallist.getInstance().setList(adapter.getSearch());
                }else{//이미 존재한다면 추가
                    globallist.getInstance().addList(adapter.getSearch());
                }
                Intent intent = new Intent(select_place_search.this, select_place.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void setRecycle(final String area){
        String URL ="http://211.253.26.214:8080/travolo2/post/searchTour";//통신할 URL
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                adapter.notifyDataSetChanged();
                try{
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String name = jsonObject.getString("name");//여행지의 이름, 사진, tid를 전달받음
                            String img = jsonObject.getString("img");
                            String tid = jsonObject.getString("tid");
                            String info = jsonObject.getString("info");
                            item.add(new plan(tid, name, img, info, null, null));
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
        params.put("key", area);//여행지 이름을 전달
        params.put("base_point",globallist.getInstance().getAddress());//선택한 여행지 전달
        JSONObject jsonObject = new JSONObject(params);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
