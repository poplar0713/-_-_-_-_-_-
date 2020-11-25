package com.example.travolo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class period extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    TextView textView,textView2;
    Button button, btn;
    SimpleDateFormat format;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private DatePickerDialog.OnDateSetListener callbackMethod2;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat yearf = new SimpleDateFormat("yyyy", Locale.getDefault());//년도
    SimpleDateFormat monthf = new SimpleDateFormat("MM",Locale.getDefault());//월
    SimpleDateFormat datef = new SimpleDateFormat("dd",Locale.getDefault());//일

    int year = Integer.parseInt(yearf.format(currentTime));
    int month = Integer.parseInt(monthf.format(currentTime));
    int day = Integer.parseInt(datef.format(currentTime));

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.period);

        final String id = globallist.getInstance().getId();//사용자의 id를 받음
        Intent intent = getIntent();
        final String area = intent.getExtras().getString("area");//사용자가 선택한 여행지를 받음



        textView = findViewById(R.id.from);
        textView2 = findViewById(R.id.to);
        this.InitializeListener();
        this.InitializeListener2();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//기간설정을 위한 달력을 띄움
                DatePickerDialog dialog = new DatePickerDialog(context, callbackMethod, year, month-1, day);//달력의 시작일자를 현재 날짜로 시작하게 설정
                dialog.show();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//기간설정을 위한 달력을 띄움
                DatePickerDialog dialog2 = new DatePickerDialog(context, callbackMethod2, year, month-1, day);
                dialog2.show();
            }
        });
        button = findViewById(R.id.auto_make);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//선택한 기간과 선택지를 서버로 전달하기위해 로딩페이지로 전달
                Intent intent = new Intent(period.this, progress_loading.class);
                final String from = textView.getText().toString();
                final String to = textView2.getText().toString();
                try{
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    Date first = format.parse(from);
                    Date second = format.parse(to);

                    long caldate = first.getTime() - second.getTime();
                    long caldateday = caldate/(24*60*60*1000);

                    caldateday = Math.abs(caldateday);//선택한 날짜 사이의 기간을 계산
                    globallist.getInstance().setDate((int)caldateday+1);//전역변수에 날짜 저장
                }catch (ParseException e){

                }
                globallist.getInstance().setAddress(area);
                globallist.getInstance().setStartdate(from);
                globallist.getInstance().setEnddate(to);
                startActivity(intent);
            }
        });
        btn = findViewById(R.id.select_place);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String from = textView.getText().toString();
                final String to = textView2.getText().toString();
                try{
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    Date first = format.parse(from);
                    Date second = format.parse(to);

                    long caldate = first.getTime() - second.getTime();
                    long caldateday = caldate/(24*60*60*1000);

                    caldateday = Math.abs(caldateday);//선택한 날짜 사이의 기간을 계산
                    globallist.getInstance().setDate((int)caldateday+1);//전역변수에 날짜 저장
                }catch (ParseException e){

                }
                globallist.getInstance().setAddress(area);
                globallist.getInstance().setStartdate(from);
                globallist.getInstance().setEnddate(to);
                Intent intent = new Intent(period.this, select_place.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 햄버거 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.hambergermenu); //햄버거 버튼 이미지 지정
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.homepage){
                    Intent intent2 = new Intent(period.this, login.class);
                    startActivity(intent2);
                    finish();
                }
                else if(id == R.id.plan) {//생성된 여행일정 페이지
                    Intent intent2 = new Intent(period.this, historylist.class);
                    startActivity(intent2);
                }
                else if(id == R.id.notice){//공지사항 페이지
                }
                else if(id == R.id.setting){//환경설정 페이지
                    Intent intent3 = new Intent(period.this, setting.class);
                    startActivity(intent3);
                }

                return true;
            }
        });

        View nav_haeder = navigationView.getHeaderView(0);
        TextView id_text = nav_haeder.findViewById(R.id.nav_id);
        id_text.setText(id);
        Button button = nav_haeder.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//로그아웃 버튼 선택시 동작
                new AlertDialog.Builder(context).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(period.this, MainActivity.class);
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = auto.edit();
                        editor.clear();
                        editor.commit();
                        globallist.getInstance().logout();//저장된 아이디 제거
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);//햄버거 버튼 화면에 띄우기
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                textView.setText(year + "-" + monthOfYear + "-" + dayOfMonth);//선택한 날짜를 텍스트(yyyy-mm-dd)로 띄움
            }
        };
    }
    public void InitializeListener2()
    {
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                textView2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);//선택한 날짜를 텍스트(yyyy-mm-dd)로 띄움
            }
        };
    }

}