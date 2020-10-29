package com.example.travolo;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapView;

public class plan_map extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_map);

        linearLayout = findViewById(R.id.Tmapview);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xxa3a9929fe00448dba86b5fd5e81648eb");
        linearLayout.addView(tMapView);
    }
}
