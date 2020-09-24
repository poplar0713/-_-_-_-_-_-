package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class progress_loading extends AppCompatActivity {

    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_loading);

        Intent intent = getIntent();

        id = intent.getExtras().getString("id");

        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.dot).into(image);

        loading load = new loading();
        load.start();

    }
    class loading extends Thread{
        public void run(){
            try{
                sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent1 = new Intent(progress_loading.this, planlist.class);
            intent1.putExtra("id",id);
            startActivity(intent1);
        }
    }
}
