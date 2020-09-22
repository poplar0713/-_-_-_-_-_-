package com.example.travolo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText id, pw;
    CheckBox auto_login;
    Button btn_lg, btn;
    String lg_id,lg_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.email);
        pw = findViewById(R.id.password);
        auto_login = findViewById(R.id.autoLogin);
        btn_lg = findViewById(R.id.button);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        lg_id = auto.getString("inputid", null);
        lg_pw = auto.getString("inputpw", null);

        btn_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
        btn = findViewById(R.id.signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, signup.class);
                startActivity(i);
            }
        });
    }

    public void sendRequest() {//서버통신
        final String user_id = id.getText().toString();
        String user_pw = pw.getText().toString();
        String URL = "http://211.253.26.214:8080/travolo2/post/login";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String success = response.getString("success");//응답이 success일 경우
                    String preference = response.getString("preference");
                    if(success != null && success.equals("1")) {
                        if(preference.equals("1")) {
                            Toast.makeText(getApplicationContext(), "Log in Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, login.class);//메인화면으로 이동
                            globallist.getInstance().setId(user_id);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MainActivity.this, arealist.class);//메인화면으로 이동
                            globallist.getInstance().setId(user_id);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호를 확인해 주세요",Toast.LENGTH_SHORT).show();//아이디 비밀번호가 다른경우
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"login error!",Toast.LENGTH_SHORT).show();
                return;
            }
        };

        Map<String, String> params = new HashMap<String, String>();//맵형태로 정보 전달
        params.put("user_id",user_id);
        params.put("user_pw",user_pw);

        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송

    }
}