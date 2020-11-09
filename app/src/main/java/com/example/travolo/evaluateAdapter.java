package com.example.travolo;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class evaluateAdapter extends RecyclerView.Adapter<evaluateAdapter.ViewHolder> {
    private ArrayList<evaluate> items = new ArrayList<>();
    private SparseBooleanArray goodselected = new SparseBooleanArray(0);
    private SparseBooleanArray bads = new SparseBooleanArray(0);
    Context context;

    public evaluateAdapter(ArrayList<evaluate> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public evaluateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate,parent,false);
        evaluateAdapter.ViewHolder viewHolder = new evaluateAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final evaluateAdapter.ViewHolder holder, int position) {
        evaluate item = items.get(position);
        holder.evname.setText(item.getName());
        Glide.with(context).load(item.getImage()).into(holder.evimg);
        int pos = holder.getAdapterPosition();
        if(isItemgoodSelected(position) && !isbads(position)){
            item.setGrade("5");
            items.set(pos,item);
            holder.good.setBackgroundResource(R.drawable.icon_like_color);
            holder.bad.setBackgroundResource(R.drawable.icon_unlike_gray);
        }
        else if(isbads(position) && !isItemgoodSelected(position)){
            item.setGrade("0");
            items.set(pos,item);
            holder.bad.setBackgroundResource(R.drawable.icon_unlike_color);
            holder.good.setBackgroundResource(R.drawable.icon_like_gray);
        }else{
            item.setGrade("3");
            items.set(pos,item);
            holder.bad.setBackgroundResource(R.drawable.icon_unlike_gray);
            holder.good.setBackgroundResource(R.drawable.icon_like_gray);
        }
    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    public void setItems(ArrayList<evaluate> items){
        this.items = items;
    }

    public ArrayList<evaluate> getItems() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView evimg;
        TextView evname;
        Button good, bad;

        ViewHolder(View itemView){
            super(itemView);
            evimg = itemView.findViewById(R.id.evimage);
            evname = itemView.findViewById(R.id.evname);
            good = itemView.findViewById(R.id.selectgood);
            bad = itemView.findViewById(R.id.selectbad);

            good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    toggleItemgoodSelected(position);
                }
            });
            bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    togglebad(position);
                }
            });
        }
    }
    private void toggleItemgoodSelected(int position){//리사이클러뷰의 재사용을 방지하기위한 선택한 리스트 확인
        if(goodselected.get(position,false)){
            goodselected.delete(position);//선택리스트에서 삭제
            notifyItemChanged(position);//리스트 갱신
        }else{
            goodselected.put(position,true);//선택리스트에 추가
            bads.delete(position);
            notifyItemChanged(position);//리스트 갱신
        }
    }
    private Boolean isItemgoodSelected(int position){
        return goodselected.get(position, false);
    }
    private void togglebad(int position){//리사이클러뷰의 재사용을 방지하기위한 선택한 리스트 확인
        if(bads.get(position,false)){
            bads.delete(position);//선택리스트에서 삭제
            notifyItemChanged(position);//리스트 갱신
        }else{
            bads.put(position,true);//선택리스트에 추가
            goodselected.delete(position);
            notifyItemChanged(position);//리스트 갱신
        }
    }
    private Boolean isbads(int position){
        return bads.get(position, false);
    }
}
