package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
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

public class recommendInsert extends AppCompatActivity {
    RecyclerView recyclerView;
    private recommendAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<plan> item = new ArrayList<>();
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendinsert);

        String id = globallist.getInstance().getId();

        setRecycle_plan();

        recyclerView = findViewById(R.id.recycler_recommend);
        adapter = new recommendAdapter(item,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        btn = findViewById(R.id.recommend_ok);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globallist.getInstance().setTid(adapter.getPlan());//선택한 여행지들을 전역변수에 저장
                Intent intent = new Intent(recommendInsert.this, editplan.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setRecycle_plan(){
        item.addAll(globallist.getInstance().getRecommend());
    }//전역변수에 있는 여행지를 불러오기
}
