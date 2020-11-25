package com.example.travolo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class preferencAdapter extends RecyclerView.Adapter<preferencAdapter.ViewHolder> {//여행지 리스트 어뎁터
    Context context;
    private ArrayList<preference> items = new ArrayList<>();
    private SparseBooleanArray selected = new SparseBooleanArray(0);
    private ArrayList<preference> itemselect = new ArrayList<>();


    public preferencAdapter(Context context, ArrayList<preference> item){
        this.context = context;
        items = item;
    }

    @NonNull
    @Override
    public preferencAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어뎁터 생성

        View itemVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference,parent,false);
        preferencAdapter.ViewHolder viewHolder = new preferencAdapter.ViewHolder(itemVIew);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull preferencAdapter.ViewHolder viewHolder, int position) {//각각의 아이템 설정
        preference item = items.get(position);//아이템의 위치를 받아옴
        Glide.with(context).load(item.getImage()).into(viewHolder.image);
        viewHolder.name.setText(item.getName());
        int itempos = viewHolder.getAdapterPosition();
        if(isItemSelected(itempos)){//선택한 아이템이 선택한 아이템리스트에 존재하지 않는 경우
            viewHolder.itemView.findViewById(R.id.card_linear).setBackgroundColor(R.color.colorAccent);//색을 바꿈
            viewHolder.name.setTextColor(Color.WHITE);
            itemselect.add(item);//선택한 아이템 리스트에 추가
        }else {
            viewHolder.itemView.findViewById(R.id.card_linear).setBackgroundColor(Color.WHITE);//색을 원래의 흰색으로 되돌림
            viewHolder.name.setTextColor(Color.BLACK);
            itemselect.remove(item);//선택한 아이템 리스트에서 삭제
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<preference> getItemselect(){
        return itemselect;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//아이템 선택시 행동
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                }
            });
        }

    }
    private void toggleItemSelected(int position){//리사이클러뷰의 재사용을 방지하기위한 선택한 리스트 확인
        if(selected.get(position,false)){
            selected.delete(position);//선택리스트에서 삭제
            notifyItemChanged(position);//리스트 갱신
        }else{
            selected.put(position,true);//선택리스트에 추가
            notifyItemChanged(position);//리스트 갱신
        }
    }
    private Boolean isItemSelected(int position){
        return selected.get(position, false);
    }
}
