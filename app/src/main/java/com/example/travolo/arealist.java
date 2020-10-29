package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class arealist extends AppCompatActivity {
    private areaAdapter adapter = new areaAdapter();
    private Listadapter listadapter;
    private ArrayList<preference> item = new ArrayList<preference>();
    long first;
    long second;
    TextView textView;
    Button button;

    @Override
    public void onBackPressed() {//뒤로가기 버튼을 누른 경우
        second = System.currentTimeMillis();
        Toast.makeText(arealist.this,"한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
        if(second - first<2000){
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
        }
        first = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arealist);

        final String id = globallist.getInstance().getId();

        final RecyclerView recyclerView = findViewById(R.id.recycler_area);//지명 리스트
        final RecyclerView recyclerView1 = findViewById(R.id.select_recycle);//사용자가 선택한 여행지들의 리스트

        adapter.setItems(new Samplearea().getItems());
        if(globallist.getInstance().getList() != null)
            item.addAll(globallist.getInstance().getList());//전역변수에서 사용자가 선택한 여행지 리스트를 가져온다.

        listadapter = new Listadapter(this,item);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);//3개씩 한줄로 아이템을 띄운다
        recyclerView.setLayoutManager(layoutManager);//layoutmanager 설정

        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//가로 방향으로 아이템들을 쭉 띄움

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(listadapter);//선택한 여행지에서 아이템을 위 또는 아래로 밀었을 경우 아이템이 리스트에서 삭제되도록 설정
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView1);//리사이클러뷰에 적용

        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(listadapter);

        button = findViewById(R.id.bb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(arealist.this, preference_loading.class);//로딩화면으로 이동
                startActivity(intent);
            }
        });
        textView = findViewById(R.id.areacount);
        if(item.size() > 0){
            textView.setText("현재 선택된 여행지 수 : " + item.size());//사용자가 선택한 여행지의 개수를 표시
        }

        adapter.setOnItemClicklistener(new onAreaItemClickListener() {
            @Override
            public void onItemClick(areaAdapter.ViewHolder viewHolder, View view, int position) {//지역을 선택한 경우
                area areaitem = adapter.getItems(position);
                final String area = areaitem.getName();
                Intent intent = new Intent(arealist.this, preferencelist.class);//해당 지역의 여행지를 표시해주는 화면으로 이동
                intent.putExtra("area", area);
                startActivity(intent);
                finish();
            }
        });
    }
}
