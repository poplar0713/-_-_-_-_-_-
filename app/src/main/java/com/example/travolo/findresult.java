package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class findresult extends AppCompatActivity {
    TextView result;
    String text;
    int flag;
    Button btn1, btn2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findresult);

        Intent intent = getIntent();
        text = intent.getExtras().getString("result");
        flag = intent.getExtras().getInt("flag");

        result = findViewById(R.id.fnresult);
        result.setText(text);

        btn1 = findViewById(R.id.tologin);
        btn2 = findViewById(R.id.tochangepwd);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findresult.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if(flag == 1){
            btn2.setVisibility(View.VISIBLE);
        }else
            btn2.setVisibility(View.GONE);
    }
}
