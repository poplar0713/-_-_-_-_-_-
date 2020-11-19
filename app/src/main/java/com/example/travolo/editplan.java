package com.example.travolo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class editplan extends AppCompatActivity {
    RecyclerView recyclerView;
    private editplanAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<plan> item = new ArrayList<>();
    Button btn,cancle,insert;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editplan);

        final String id = globallist.getInstance().getId();

        setRecycle_plan();

        recyclerView = findViewById(R.id.recycler_editplan);
        adapter = new editplanAdapter(item,this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn = findViewById(R.id.editsave);
        cancle = findViewById(R.id.editcancle);
        insert = findViewById(R.id.editinsert);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editplan.this, rebuild_plan.class);
                startActivity(intent);
                finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globallist.getInstance().removeplans();
                Intent intent = new Intent(editplan.this, planlist.class);
                startActivity(intent);
                finish();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editplan.this, recommend_loading.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setRecycle_plan(){
        item.addAll(globallist.getInstance().getPlans());
    }
}
