package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class checkpwd extends AppCompatActivity {
    EditText pwd;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpassword);

        pwd = findViewById(R.id.cpd);
        button = findViewById(R.id.pwdcheck1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(pwd.getText().toString());
            }
        });
    }
    public void check(final String pas){
        String URL = "http://211.253.26.214:8080/travolo2/post/login";//통신할 서버 url
        final String id = globallist.getInstance().getId();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(checkpwd.this, changepwd.class);//일정 표시화면으로 이동
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요!", Toast.LENGTH_SHORT).show();
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
            jsonObject.put("user_id",id);
            jsonObject.put("user_pw",pas);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(loginRequest);//전송
    }
}
