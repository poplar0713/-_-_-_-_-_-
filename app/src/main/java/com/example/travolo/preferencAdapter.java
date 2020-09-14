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
import java.util.HashMap;
import java.util.Map;

public class preferencAdapter extends RecyclerView.Adapter<preferencAdapter.ViewHolder> {
    Context context;
    private ArrayList<preference> items = new ArrayList<>();
    private SparseBooleanArray selected = new SparseBooleanArray(0);
    private Map<String, String> itemselect = new HashMap<>();


    public preferencAdapter(Context context, ArrayList<preference> item){
        this.context = context;
        items = item;
    }

    @NonNull
    @Override
    public preferencAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preference,parent,false);
        preferencAdapter.ViewHolder viewHolder = new preferencAdapter.ViewHolder(itemVIew);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull preferencAdapter.ViewHolder viewHolder, int position) {
        preference item = items.get(position);
        Glide.with(context).load(item.getImage()).into(viewHolder.image);
        viewHolder.name.setText(item.getName());
        if(isItemSelected(position)){
            viewHolder.itemView.findViewById(R.id.card_linear).setBackgroundColor(R.color.colorAccent);
            viewHolder.name.setTextColor(Color.WHITE);
            itemselect.put(item.getTid(), item.getTid());
        }else {
            viewHolder.itemView.findViewById(R.id.card_linear).setBackgroundColor(Color.WHITE);
            viewHolder.name.setTextColor(Color.BLACK);
            itemselect.remove(viewHolder.name.getText().toString());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<preference> items){
        this.items = items;
    }

    public Map<String, String> getItemselect(){
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
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                }
            });
        }

    }
    private void toggleItemSelected(int position){
        if(selected.get(position,false)){
            selected.delete(position);
            notifyItemChanged(position);
        }else{
            selected.put(position,true);
            notifyItemChanged(position);
        }
    }
    private Boolean isItemSelected(int position){
        return selected.get(position, false);
    }
}
