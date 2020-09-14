package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class arealist extends AppCompatActivity {
    private areaAdapter adapter = new areaAdapter();
    private RequestQueue queue;
    private ArrayList<preference> item = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arealist);

        RecyclerView recyclerView = findViewById(R.id.recycler_area);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setItems(new Samplearea().getItems());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClicklistener(new onAreaItemClickListener() {
            @Override
            public void onItemClick(areaAdapter.ViewHolder viewHolder, View view, int position) {
                area areaitem = adapter.getItems(position);
                final String area = areaitem.getName();
                sendarea(area);
                Intent intent = new Intent(arealist.this, preferencelist.class);
                intent.putExtra("itme",item);
                startActivity(intent);
            }
        });

    }
    public void sendarea(final String area){
        String URL ="";
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String img = jsonObject.getString("img");
                        String tid = jsonObject.getString("tid");
                        item.add(new preference(name,img,tid));
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

        JSONArray jsonArray = new JSONArray();

        jsonArray.put(params);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,URL,jsonArray, listener, errorListener);
        queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
