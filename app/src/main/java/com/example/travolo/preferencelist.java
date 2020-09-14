package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Map;


public class preferencelist extends AppCompatActivity {

    RecyclerView recyclerView;
    private preferencAdapter adapter;
    private RequestQueue queue;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
    int dataflag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencelist);
        Intent intent = getIntent();
        ArrayList<preference> item = (ArrayList<preference>)intent.getSerializableExtra("item");
        recyclerView = findViewById(R.id.recycler_area);
        final Button button = findViewById(R.id.bb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendData(adapter);
            }
        });
        adapter = new preferencAdapter(this,item);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }
//    public void setRecyclerView(View view) {
//
//        String URL = "http://211.253.26.214:8080/travolo2/post/randomLabel";
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                item.clear();
//                adapter.notifyDataSetChanged();
//                try {
//                    for(int i = 0; i<response.length();i++){
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        String name = jsonObject.getString("name");
//                        String img = jsonObject.getString("img");
//                        String tid = jsonObject.getString("tid");
//                        item.add(new preference(name,img,tid));
//                        adapter.notifyItemInserted(0);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue = Volley.newRequestQueue(this);
//        queue.add(jsonArrayRequest);
//    }

//    public void sendData(preferencAdapter adapter, final String id) {
//        String URL = "http://211.253.26.214:8080/travolo2/post/preference";//통신할 서버 url
//        final Map<String, String> params = adapter.getItemselect();
//
//        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String success = response.getString("success");//응답이 success일 경우
//                    if (success != null && success.equals("1")) {
//                        Intent intent = new Intent(preferencelist.this, login.class);
//                        intent.putExtra("id", id);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "networkerror!", Toast.LENGTH_SHORT).show();//아이디 비밀번호가 다른경우
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "login error!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        };
//
//        //맵형태로 정보 전달
//        params.put("user_id", id);
//        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송
//
//        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
//
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        if (params.size() > 19)
//            queue.add(loginRequest);//전송
//        else
//            Toast.makeText(getApplicationContext(),"최소 20개를 선택해 주세요",Toast.LENGTH_SHORT).show();
//    }

    public void sendData(preferencAdapter adapter){
        final Map<String, String> params = adapter.getItemselect();



    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
