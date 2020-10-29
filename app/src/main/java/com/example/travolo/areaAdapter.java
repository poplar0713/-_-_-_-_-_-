package com.example.travolo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class areaAdapter extends RecyclerView.Adapter<areaAdapter.ViewHolder> implements onAreaItemClickListener{//지역 선택화면을 띄우기 위한 어뎁터
    private ArrayList<area> items = new ArrayList<>();
    onAreaItemClickListener listener;//지역을 선택했을 경우 반응

    public areaAdapter() {
    }

    @NonNull
    @Override
    public areaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어뎁터 생성
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area,parent,false);
        areaAdapter.ViewHolder viewHolder = new areaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull areaAdapter.ViewHolder holder, int position) {//각 아이템 생성
        area item = items.get(position);
        holder.name.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClicklistener(onAreaItemClickListener listener){//아이템을 선택한경우
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder viewHolder, View view, int position) {
        if(listener != null){
            listener.onItemClick(viewHolder,view,position);
        }
    }

    public void setItems(ArrayList<area> items){
        this.items = items;
    }//리스트를 설정

    public area getItems(int position){
        return items.get(position);
    }//해당 아이템을 반환

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ViewHolder(View itemView){
            super(itemView);
            name= itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });
        }
    }
}
