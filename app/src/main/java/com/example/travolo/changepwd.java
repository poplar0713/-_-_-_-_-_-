package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class changepwd extends AppCompatActivity {
    EditText pass,passcheck;
    TextView passck,pasckck;
    Button btn;
    int passflag = 0, passcheckflag = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepwd);

        pass = findViewById(R.id.ppwd);
        passcheck = findViewById(R.id.pwdcheck);
        btn = findViewById(R.id.chpwd);

        passck = findViewById(R.id.textView19);
        pasckck = findViewById(R.id.textView20);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9]).{7,19}.$",pass.getText())){//숫자와 영어가 섞인 8글자이상19자이하의 글자인지 확인
                    passck.setText("영어와 숫자를 섞어서 최소 8자 이상 입력해 주세요!");//아닐경우 해당 텍스트 출력
                    passck.setTextColor(ContextCompat.getColor(changepwd.this, R.color.design_default_color_error));//붉은색
                    passflag = 0;
                }
                else{
                    passck.setText("올바른 비밀번호 입니다.");//조건에 맞을경우 해당 텍스트 출력
                    passck.setTextColor(ContextCompat.getColor(changepwd.this, R.color.colorAccent));//파란색
                    passflag = 1;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pass.getText().toString().equals(passcheck.getText().toString())){//비밀번호에 입력된 글자와 같은지 비교
                    pasckck.setText("입력한 비밀번호와 일치합니다.");
                    pasckck.setTextColor(ContextCompat.getColor(changepwd.this, R.color.colorAccent));//붉은색
                    passcheckflag = 1;
                }else{
                    pasckck.setText("입력한 비밀번호와 일치하지 않습니다.");
                    pasckck.setTextColor(ContextCompat.getColor(changepwd.this, R.color.design_default_color_error));//파란색
                    passcheckflag = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passflag == 1 && passcheckflag == 1){
                    change(pass.getText().toString());
                }else
                    Toast.makeText(getApplicationContext(), "입력하신 정보를 확인해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void change(final String pas){
        String URL = "http://211.253.26.214:8080/travolo2/post/findInfo";//통신할 서버 url
        final String id = globallist.getInstance().getId();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(changepwd.this, login.class);//일정 표시화면으로 이동
                        Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
            jsonObject.put("flag",2);
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
