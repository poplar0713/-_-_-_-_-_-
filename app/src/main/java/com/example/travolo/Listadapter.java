package com.example.travolo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class Listadapter extends RecyclerView.Adapter<Listadapter.ViewHolder> implements ItemTouchHelperAdapter {//선택한 여행지 리스트 어뎁터
    Context context;
    private ArrayList<preference> items = new ArrayList<>();

    public Listadapter(Context context, ArrayList<preference> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Listadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어뎁터 생성
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select,parent,false);//아이템 디자인 설정
        Listadapter.ViewHolder viewHolder = new Listadapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Listadapter.ViewHolder holder, final int position) {//각 아이템 생성
        preference item = items.get(position);//아이템 위치 받기
        Glide.with(context).load(item.getImage()).into(holder.imageView);//여행지 이미지 표시
        holder.name.setText(item.getName());//아이템 이름 설정
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<preference> getItems(){
        return items;
    }

    @Override
    public void onItemDismiss(int position) {//아이템을 밀어서 삭제하는 경우
        items.remove(position);//리스트에서 아이템 삭제
        notifyItemRemoved(position);//리스트 새로고침
        globallist.getInstance().removeList(position);//전역변수에서 삭제
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);

        }
    }
}
