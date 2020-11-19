package com.example.travolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class select_place extends AppCompatActivity {
    private Listadapter listadapter;
    private ArrayList<preference> item = new ArrayList<preference>();
    RecyclerView recyclerView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_place);

        recyclerView = findViewById(R.id.select_place_recycle);
        if(globallist.getInstance().getList() != null)
            item.addAll(globallist.getInstance().getList());//전역변수에서 사용자가 선택한 여행지 리스트를 가져온다.

        listadapter = new Listadapter(this,item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//가로 방향으로 아이템들을 쭉 띄움

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(listadapter);//선택한 여행지에서 아이템을 위 또는 아래로 밀었을 경우 아이템이 리스트에서 삭제되도록 설정
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);//리사이클러뷰에 적용

        recyclerView.setAdapter(listadapter);

        editText = findViewById(R.id.search_place);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });

        button = findViewById(R.id.search_place_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = editText.getText().toString();
                Intent intent = new Intent(select_place.this, select_place.class );
                startActivity(intent);
                finish();
            }
        });
    }
}
