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

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.ViewHolder> {
    private ArrayList<event> items = new ArrayList<>();
    Context context;

    public eventAdapter(ArrayList<event> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public eventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemVIew);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventAdapter.ViewHolder viewHolder, int position) {
        event item = items.get(position);
        Glide.with(context).load(item.getImg()).into(viewHolder.image);
        viewHolder.evname.setText(item.getName());
        viewHolder.evaddress.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<event> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView evname,evaddress;
        ImageView image;

        ViewHolder(View itemView){
            super(itemView);
            evname = itemView.findViewById(R.id.event_name);
            evaddress = itemView.findViewById(R.id.event_address);
            image = itemView.findViewById(R.id.event_image);
        }
    }
}