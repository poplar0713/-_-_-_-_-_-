package com.example.travolo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class findaccount extends AppCompatActivity {
    EditText name, birthdate;
    Button button,btn1,btn2;
    String data1, data2;
    int dataflag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findaccount);
        name = findViewById(R.id.findname);
        birthdate = findViewById(R.id.findbirthdate);
        btn1 = findViewById(R.id.fnid);
        btn2 = findViewById(R.id.fnpw);

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                birthdate.setHint("생년월일");
                btn2.setBackgroundResource(R.color.white);
                btn2.setTextColor(Color.parseColor("#000000"));
                btn1.setBackgroundResource(R.color.colorAccent);
                btn1.setTextColor(Color.parseColor("#ffffff"));
                dataflag = 0;
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                birthdate.setHint("아이디");
                btn1.setBackgroundResource(R.color.white);
                btn1.setTextColor(Color.parseColor("#000000"));
                btn2.setBackgroundResource(R.color.colorAccent);
                btn2.setTextColor(Color.parseColor("#ffffff"));
                dataflag = 1;
            }
        });



        button = findViewById(R.id.findaccbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(dataflag);
            }
        });
    }
    public void send(final int flag){
        String URL = "http://211.253.26.214:8080/travolo2/post/findInfo";//통신할 서버 url
        data1 = name.getText().toString();
        data2 = birthdate.getText().toString();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    String result = response.getString("result");
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(findaccount.this, findresult.class);//일정 표시화면으로 이동
                        intent.putExtra("result",result);
                        intent.putExtra("flag",dataflag);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "서버오류", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송

        try{
            jsonObject.put("flag",flag);
            jsonObject.put("data1",data1);
            jsonObject.put("data2",data2);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(loginRequest);//전송
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
