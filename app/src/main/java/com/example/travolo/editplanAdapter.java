package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class editplanAdapter extends RecyclerView.Adapter<editplanAdapter.ViewHolder> {
    private ArrayList<plan> items = new ArrayList<>();
    Context context;

    public editplanAdapter(ArrayList<plan> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public editplanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editplan,parent,false);
        editplanAdapter.ViewHolder viewHolder = new editplanAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull editplanAdapter.ViewHolder holder, int position) {
        plan item = items.get(position);
        holder.name.setText("이름 : "+ item.getName());
        holder.info.setText("설명 : "+ item.getInfo());
        Glide.with(context).load(item.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public ArrayList<plan> getList(){
        return items;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,info;
        ImageView image;
        Button btn;

        ViewHolder(final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.edit_name);
            info = itemView.findViewById(R.id.edit_info);
            image = itemView.findViewById(R.id.edit_image);
            btn = itemView.findViewById(R.id.edit_delete);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("일정삭제").setMessage("일정을 삭제 하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            items.remove(position);
                            globallist.getInstance().deleteplans(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, items.size());
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
