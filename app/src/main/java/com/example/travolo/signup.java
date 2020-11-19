package com.example.travolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    Toolbar toolbar;
    Button button, button2;
    EditText id,name,password,passwordcheck,birthdate;
    ImageView imageView;
    RadioGroup radio;
    TextView check,passcheck;
    int idcheckflag = 0, passflag = 0, passcheckflag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//화면을 키보드가 가릴경우 화면을 올림

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("회원가입");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = findViewById(R.id.id);
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

        button2 = findViewById(R.id.check);//아이디 중복체크 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID();
            }
        });//아이디 중복 체크
        
        password = findViewById(R.id.password);//비밀번호
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(password.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        passwordcheck = findViewById(R.id.passwordcheck);//비밀번호 확인
        passwordcheck.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(passwordcheck.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        imageView = findViewById(R.id.image);

        passcheck = findViewById(R.id.passcheck);
        password.addTextChangedListener(new TextWatcher() {//비밀번호를 입력하는 경우
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//비밀번호에 글자가 바뀔때 마다
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9]).{7,19}.$",password.getText())){//숫자와 영어가 섞인 8글자이상19자이하의 글자인지 확인
                    passcheck.setText("영어와 숫자를 섞어서 최소 8자 이상 입력해 주세요!");//아닐경우 해당 텍스트 출력
                    passcheck.setTextColor(ContextCompat.getColor(signup.this, R.color.design_default_color_error));//붉은색
                    passflag = 0;
                }
                else{
                    passcheck.setText("올바른 비밀번호 입니다.");//조건에 맞을경우 해당 텍스트 출력
                    passcheck.setTextColor(ContextCompat.getColor(signup.this, R.color.colorAccent));//파란색
                    passflag = 1;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordcheck.addTextChangedListener(new TextWatcher() {//비밀번호 확인칸에 입력 하는 경우
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//해당 칸에 글자가 바뀔 때 마다
                if(password.getText().toString().equals(passwordcheck.getText().toString())){//비밀번호에 입력된 글자와 같은지 비교
                    imageView.setImageResource(R.drawable.check);//같으면 초록색 체크표시의 그림 출력
                    passcheckflag = 1;
                }else{
                    imageView.setImageResource(R.drawable.images);//다르면 붉은색 x표시의 그림 출력
                    passcheckflag = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        birthdate = findViewById(R.id.birthdate);
        birthdate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(birthdate.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });

        button = findViewById(R.id.signup);//회원가입 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkID() {//아이디 확인
        check = findViewById(R.id.idcheck);

        final String user_id = id.getText().toString();
        String URL = "http://211.253.26.214:8080/travolo2/post/checkId";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String success = response.getString("success");//응답이 success일 경우
                    if(success != null && success.equals("1")) {
                        check.setText("생성 가능한 아이디 입니다.");
                        check.setTextColor(ContextCompat.getColor(signup.this, R.color.colorAccent));
                        idcheckflag = 1;
                    } else {
                        check.setText("중복되는 아이디 입니다.");
                        check.setTextColor(ContextCompat.getColor(signup.this, R.color.design_default_color_error));
                        idcheckflag = 0;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"network error!",Toast.LENGTH_SHORT).show();
                return;
            }
        };

        Map<String, String> params = new HashMap<String, String>();//맵형태로 정보 전달
        params.put("user_id",user_id);

        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        if(!Patterns.EMAIL_ADDRESS.matcher(id.getText()).matches()){//아이디가 이메일 형식인지 확인
            check.setText("이메일 형식이 아닙니다!");//아닐경우 해당 텍스트 출력
            check.setTextColor(ContextCompat.getColor(signup.this, R.color.design_default_color_error));
        }
        else
            queue.add(loginRequest);//전송

    }

    public void Signup(){//회원가입
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);

        radio = findViewById(R.id.radio);
        RadioButton rd = findViewById(radio.getCheckedRadioButtonId());

        final String user_id = id.getText().toString();
        final String user_pwd = password.getText().toString();
        final String user_name = name.getText().toString();
        final String user_birthdate = birthdate.getText().toString();
        final String user_gender = rd.getText().toString();


        String URL = "http://211.253.26.214:8080/travolo2/post/signUp";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String success = response.getString("success");//응답이 success일 경우
                    if(success != null && success.equals("1")) {
                        Intent intent = new Intent(signup.this, arealist.class);//선호도 조사 화면으로 이동
                        globallist.getInstance().setId(user_id);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"이미 가입된 회원입니다!",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"network error!",Toast.LENGTH_SHORT).show();
                return;
            }
        };

        Map<String, String> params = new HashMap<String, String>();//맵형태로 정보 전달
        params.put("user_id",user_id);//아이디, 비밀번호, 이름, 생일,성별의 회원정보를 서버에 전달
        params.put("user_pwd",user_pwd);
        params.put("user_name",user_name);
        params.put("user_birthdate",user_birthdate);
        params.put("user_gender",user_gender);

        JSONObject jsonObject = new JSONObject(params);//맵형태의 정보를 json으로 전송

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        if(idcheckflag == 1 && passcheckflag ==1 && passflag == 1){
            queue.add(loginRequest);//전송
        }else if (idcheckflag == 0){//아이디 중복체크를 했는지 확인
            Toast.makeText(getApplicationContext(),"아이디 중복체크를 해주세요!",Toast.LENGTH_SHORT).show();
        }else if (passcheckflag == 0 || passflag == 0){//비밀번호가 같은지 확인
            Toast.makeText(getApplicationContext(),"패스워드 확인을 해주세요!",Toast.LENGTH_SHORT).show();
        }

    }

}
