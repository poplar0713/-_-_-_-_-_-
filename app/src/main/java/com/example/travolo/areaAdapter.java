package com.example.travolo;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class areaAdapter extends RecyclerView.Adapter<areaAdapter.ViewHolder> implements onAreaItemClickListener{
    private ArrayList<area> items = new ArrayList<>();
    private SparseBooleanArray selected = new SparseBooleanArray(0);
    onAreaItemClickListener listener;

    public areaAdapter() {
    }

    @NonNull
    @Override
    public areaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area,parent,false);
        areaAdapter.ViewHolder viewHolder = new areaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull areaAdapter.ViewHolder holder, int position) {
        area item = items.get(position);
        holder.name.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClicklistener(onAreaItemClickListener listener){
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
    }

    public area getItems(int position){
        return items.get(position);
    }

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
