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

public class planAdapter extends RecyclerView.Adapter<planAdapter.Viewholder> {
    private ArrayList<plan> items = new ArrayList<>();
    Context context;

    public planAdapter(Context context, ArrayList<plan> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public planAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan,parent,false);
        planAdapter.Viewholder viewholder = new planAdapter.Viewholder(itemView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull planAdapter.Viewholder holder, int position) {
        plan item = items.get(position);
        holder.name.setText(item.getName());
        holder.grade.setText(item.getGrade());
        Glide.with(context).load(item.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        TextView name,grade;
        ImageView image;

        Viewholder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            grade = itemView.findViewById(R.id.grade);
            image = itemView.findViewById(R.id.image);
        }
    }
}
