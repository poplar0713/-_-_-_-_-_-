package com.example.travolo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {//리스트의 내용을 담기위한 어뎁터
    private ArrayList<history> items = new ArrayList<>();//리스트
    Context context;

    public historyAdapter(ArrayList<history> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);//레이아웃 인플레이터
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;//레이아웃 리턴
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {//입력받은 아이템의 정보를 토대로 정보 수정
        final history item = items.get(position);
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
        Button button,btn;

        ViewHolder(View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.history_num);
            name = itemView.findViewById(R.id.history_name);
            button = itemView.findViewById(R.id.history_grade);
            btn = itemView.findViewById(R.id.seeplan);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), evaluateplan.class);
                    String number = items.get(position).getHistorynum();
                    globallist.getInstance().setGroup_no(number);
                    v.getContext().startActivity(intent);
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), planlist.class);
                    String number = items.get(position).getHistorynum();
                    int date = items.get(position).getDate();
                    globallist.getInstance().setDate(date);
                    globallist.getInstance().setGroup_no(number);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
