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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class afterplan extends AppCompatActivity {
    RecyclerView recyclerView;
    private planAdapter adapter;
    private RequestQueue queue;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<plan> item = new ArrayList<>();
    Button save,reset;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterplan);

        setRecycle_plan();

        recyclerView = findViewById(R.id.recycler_after_plan);
        adapter = new planAdapter(this,item);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//세로로 리스트를 띄움
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        final String id = globallist.getInstance().getId();
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_edit_plan(id);
            }
        });
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globallist.getInstance().removeplans();
                globallist.getInstance().removeTid();
                globallist.getInstance().removerecommend();
                Intent intent = new Intent(afterplan.this, planlist.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void setRecycle_plan(){
        item.addAll(globallist.getInstance().getPlans());
    }
    public void save_edit_plan(final String id){
        String URL = "http://211.253.26.214:8080/travolo2/post/editSchedule";//통신할 서버 url
        ArrayList<plan> editlist = new ArrayList<>();
        editlist.addAll(globallist.getInstance().getPlans());

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        globallist.getInstance().removerecommend();
                        globallist.getInstance().removeTid();
                        globallist.getInstance().removeplans();
                        Intent intent = new Intent(afterplan.this, planlist.class);
                        startActivity(intent);
                        finish();
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
            for (int i = 0; i<editlist.size();i++){
                JSONObject object = new JSONObject();
                object.put("tid",editlist.get(i).getTid());//선택한 여행지의 tid를 서버에 전송하여 저장
                object.put("date",editlist.get(i).getDate());
                jsonArray.put(object);
            }
            jsonObject.put("group_no",globallist.getInstance().getGroup_no());
            jsonObject.put("user_id",id);
            jsonObject.put("item",jsonArray);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송
    }
}
