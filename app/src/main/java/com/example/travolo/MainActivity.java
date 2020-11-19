package com.example.travolo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    Button btn_lg, btn, findbtn;
    String lg_id,lg_pw;
    long first;
    long second;

    @Override
    public void onBackPressed() {//뒤로가기 버튼을 누를 경우
        second = System.currentTimeMillis();
        Toast.makeText(MainActivity.this,"한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();//한번 누른경우 해당 메시지 출력
        if(second - first<2000){//2초이내에 뒤로가기 버튼을 한번더 누르는 경우
            super.onBackPressed();
            finishAffinity();
            System.exit(0);//어플을 종료
        }
        first = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.email);//아이디 입력창
        pw = findViewById(R.id.password);//비밀번호 입력창
        auto_login = findViewById(R.id.autoLogin);//자동로그인 선택창
        btn_lg = findViewById(R.id.button);//로그인 버튼
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        lg_id = auto.getString("inputid", null);
        lg_pw = auto.getString("inputpw", null);

        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(id.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });

        pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(pw.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });

        btn_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });//로그인 버튼을 누를 경우
        btn = findViewById(R.id.signup);//회원가입 버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, signup.class);
                startActivity(i);//회원가입 화면으로 이동
            }
        });

        findbtn = findViewById(R.id.findbtn);
        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, findaccount.class);
                startActivity(i);//회원가입 화면으로 이동
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//키보드가 화면을 가릴 경우 화면을 위로 밀어 올림
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
                        if(preference.equals("1")) {//선호도 조사를 끝마친 아이디의 경우
                            Toast.makeText(getApplicationContext(), "Log in Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, login.class);//메인화면으로 이동
                            globallist.getInstance().setId(user_id);
                            startActivity(intent);
                        }else{//선호도 조사를 하지않은 아이디의 경우
                            Intent intent = new Intent(MainActivity.this, arealist.class);//선호도 조사화면으로 이동
                            globallist.getInstance().setId(user_id);
                            startActivity(intent);
                        }
                    } else {//아이디 또는 비밀번호가 다른 경우
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
        params.put("user_id",user_id);//아이디 전송
        params.put("user_pw",user_pw);//비밀번호 전송

        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//키보드가 올라왔을 때 배경 화면을 누를경우 키보드를 내리는 코드
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
    }
}