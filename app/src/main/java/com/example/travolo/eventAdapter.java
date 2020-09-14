package com.example.travolo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.ViewHolder> {
    private ArrayList<event> items = new ArrayList<>();

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

        viewHolder.evname.setText(item.getName());
        viewHolder.evcontent.setText(item.getContent());
        viewHolder.evcontry.setText(item.getContry());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<event> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView evcontry,evname,evcontent;

        ViewHolder(View itemView){
            super(itemView);
            evcontry = itemView.findViewById(R.id.event_contry);
            evname = itemView.findViewById(R.id.event_name);
            evcontent = itemView.findViewById(R.id.event_content);
        }
    }
}