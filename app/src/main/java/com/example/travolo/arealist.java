package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class arealist extends AppCompatActivity {
    private areaAdapter adapter = new areaAdapter();
    private Listadapter listadapter;
    private preferencAdapter preferencAdapter;
    private ArrayList<preference> data = null;
    private ArrayList<preference> item = new ArrayList<preference>();
    private RequestQueue queue;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arealist);

        final String id = globallist.getInstance().getId();

        final RecyclerView recyclerView = findViewById(R.id.recycler_area);
        final RecyclerView recyclerView1 = findViewById(R.id.select_recycle);

        adapter.setItems(new Samplearea().getItems());
        if(globallist.getInstance().getList() != null)
            item.addAll(globallist.getInstance().getList());

        listadapter = new Listadapter(this,item);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(listadapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView1);

        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(listadapter);

        button = findViewById(R.id.bb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(id);
            }
        });

        adapter.setOnItemClicklistener(new onAreaItemClickListener() {
            @Override
            public void onItemClick(areaAdapter.ViewHolder viewHolder, View view, int position) {
                area areaitem = adapter.getItems(position);
                final String area = areaitem.getName();
                Intent intent = new Intent(arealist.this, preferencelist.class);
                intent.putExtra("area", area);
                startActivity(intent);
                finish();
            }
        });

    }
        public void sendData(final String id) {
        String URL = "http://211.253.26.214:8080/travolo2/post/preference";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(arealist.this, login.class);
                        globallist.getInstance().deleteList();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "networkerror!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "login error!", Toast.LENGTH_SHORT).show();
                return;
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송

        try{
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i<globallist.getInstance().getsize();i++){
                JSONObject object = new JSONObject();
                object.put("tid",globallist.getInstance().getItem(i));
                jsonArray.put(object);
            }
            jsonObject.put("user_id",id);
            jsonObject.put("item",jsonArray);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        if (globallist.getInstance().getsize() > 19)
            queue.add(loginRequest);//전송
        else
            Toast.makeText(getApplicationContext(),"최소 20개를 선택해 주세요",Toast.LENGTH_SHORT).show();
    }

}
