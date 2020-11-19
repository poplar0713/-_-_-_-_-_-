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

public class planAdapter extends RecyclerView.Adapter<planAdapter.ViewHolder> implements onPlanItemClickListener {
    private ArrayList<plan> items = new ArrayList<>();
    Context context;
    onPlanItemClickListener listener;

    public planAdapter(Context context, ArrayList<plan> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public planAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어뎁터 생성
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan,parent,false);
        planAdapter.ViewHolder viewholder = new planAdapter.ViewHolder(itemView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull planAdapter.ViewHolder holder, int position) {//각 아이템별 설
        plan item = items.get(position);
        String []day = item.getDate().split("-");
        holder.name.setText("이름 : "+item.getName());//이름 표시
        holder.info.setText("설명 : "+item.getInfo());//설명 표시
        Glide.with(context).load(item.getImage()).into(holder.image);//여행지 사진 등록
        if(item.getTime().equals("0")){
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(day[2]+"일 일정");
        }else
            holder.date.setVisibility(View.GONE);
    }
    public void setOnItemClicklistener(onPlanItemClickListener listener){//아이템을 선택한경우
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder viewHolder, View view, int position) {
        if(listener != null){
            listener.onItemClick(viewHolder,view,position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public plan getItems(int position) {
        return items.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,info,date;
        ImageView image;

        ViewHolder(View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            info = itemView.findViewById(R.id.info);
            image = itemView.findViewById(R.id.image);

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
