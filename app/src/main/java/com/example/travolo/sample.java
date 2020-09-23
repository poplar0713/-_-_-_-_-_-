package com.example.travolo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class sample extends AppCompatActivity {
    TextView textView;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        textView = findViewById(R.id.sampletext);
        button = findViewById(R.id.samplebutton);

        textView.setText(globallist.getInstance().getId()+"님 설문을 완료하셨습니다. 감사합니다.");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(sample.this);
                System.exit(0);
            }
        });

    }
}
