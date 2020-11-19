package com.example.travolo;

import android.content.Context;
import android.content.DialogInterface;
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

public class recommendAdapter extends RecyclerView.Adapter<recommendAdapter.ViewHolder> {
    private ArrayList<plan> items = new ArrayList<>();
    private ArrayList<String> plans = new ArrayList<>();
    Context context;

    public recommendAdapter(ArrayList<plan> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public recommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        recommendAdapter.ViewHolder viewHolder = new recommendAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recommendAdapter.ViewHolder holder, int position) {
        plan item = items.get(position);
        holder.name.setText("이름 : "+ item.getName());
        holder.info.setText("설명 : "+ item.getInfo());
        Glide.with(context).load(item.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getPlan(){
        return plans;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, info;
        ImageView image;
        Button btn;

        ViewHolder(final View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.recommend_name);
            info = itemView.findViewById(R.id.recommend_info);
            image = itemView.findViewById(R.id.recommend_image);
            btn = itemView.findViewById(R.id.recommend_insert);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("여행지 추가").setMessage("여행지를 추가 하시겠습니까?").setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            plan p =items.get(position);
                            items.remove(position);
                            globallist.getInstance().addplans(p);
                            plans.add(p.getTid());
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
