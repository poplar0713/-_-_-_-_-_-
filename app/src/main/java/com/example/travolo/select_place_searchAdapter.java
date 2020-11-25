package com.example.travolo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class select_place_searchAdapter extends RecyclerView.Adapter<select_place_searchAdapter.ViewHolder> {
    private ArrayList<plan> items = new ArrayList<>();
    private ArrayList<preference> search = new ArrayList<>();
    Context context;

    public select_place_searchAdapter(ArrayList<plan> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public select_place_searchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_place,parent,false);
        select_place_searchAdapter.ViewHolder viewHolder = new select_place_searchAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull select_place_searchAdapter.ViewHolder holder, int position) {
        plan item = items.get(position);
        holder.name.setText("이름 : "+ item.getName());
        holder.info.setText("설명 : "+ item.getInfo());
        Glide.with(context).load(item.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<preference> getSearch() {
        return this.search;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, info;
        ImageView image;
        Button btn;

        ViewHolder(final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.search_name);
            info = itemView.findViewById(R.id.search_info);
            image = itemView.findViewById(R.id.search_image);
            btn = itemView.findViewById(R.id.search_insert);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("여행지 추가").setMessage("여행지를 추가 하시겠습니까?").setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            preference p = new preference(items.get(position).getName(),items.get(position).getImage(),items.get(position).getTid());
                            search.add(p);
                            items.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, items.size());
                            Toast.makeText(context,"여행지가 추가 되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            });
        }
    }
}