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
import java.util.Map;


public class Listadapter extends RecyclerView.Adapter<Listadapter.ViewHolder> implements ItemTouchHelperAdapter {
    Context context;
    private ArrayList<preference> items = new ArrayList<>();

    public Listadapter(Context context, ArrayList<preference> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Listadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select,parent,false);
        Listadapter.ViewHolder viewHolder = new Listadapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Listadapter.ViewHolder holder, final int position) {
        preference item = items.get(position);
        Glide.with(context).load(item.getImage()).into(holder.imageView);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<preference> getItems(){
        return items;
    }

    @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        globallist.getInstance().removeList(position);
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
