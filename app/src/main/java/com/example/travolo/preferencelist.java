package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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


public class preferencelist extends AppCompatActivity {

    RecyclerView recyclerView;
    private preferencAdapter adapter;
    private RequestQueue queue;
    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
    private ArrayList<preference> item = new ArrayList<>();
    int dataflag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencelist);
        Intent intent = getIntent();
        String area = intent.getExtras().getString("area");
        setRecycle(area);
        recyclerView = findViewById(R.id.recycler_area);
        adapter = new preferencAdapter(this, item);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        final Button button = findViewById(R.id.bb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendData(adapter);
            }
        });


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
        if(globallist.getInstance().getList() == null) {
            globallist.getInstance().setList(adapter.getItemselect());
        }else{
            globallist.getInstance().addList(adapter.getItemselect());
        }
        Intent intent1 = new Intent(preferencelist.this, arealist.class);
        startActivity(intent1);
        finish();
    }
    public void setRecycle(final String area){
        String URL ="http://211.253.26.214:8080/travolo2/post/randomLabel";
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                item.clear();
                adapter.notifyDataSetChanged();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
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
        params.put("area", area);
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
