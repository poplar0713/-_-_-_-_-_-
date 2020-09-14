package com.example.travolo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {//리스트의 내용을 담기위한 어뎁터
    private ArrayList<history> items = new ArrayList<>();//리스트
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);//레이아웃 인플레이터
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;//레이아웃 리턴
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {//입력받은 아이템의 정보를 토대로 정보 수정
        history item = items.get(position);
        viewHolder.name.setText(item.getName());//아이템이름 재설정
        viewHolder.num.setText(item.getNum());//아이템번호 재설정
    }

    @Override
    public int getItemCount() {//리스트에 있는 아이템의 개수
        return items.size();
    }

    public void setItems(ArrayList<history> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView num,name;

        ViewHolder(View itemView) {//각 아이템에서 수정할 부분을 지정
            super(itemView);
            num = itemView.findViewById(R.id.history_num);
            name = itemView.findViewById(R.id.history_name);
        }
    }
}
