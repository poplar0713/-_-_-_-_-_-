package com.example.travolo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends ArrayAdapter<searchitem> {
    private List<searchitem> searchlist;

    public searchAdapter(Context context, List<searchitem> list){
        super(context,0,list);
        searchlist = new ArrayList<>(list);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search, parent,false);
        }
        TextView areaname = convertView.findViewById(R.id.areaname);

        searchitem searchitem = getItem(position);
        if(searchitem != null){
            areaname.setText(searchitem.getAreaName());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return areaFilter;
    }

    private Filter areaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<searchitem> searchitems = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                searchitems.addAll(searchlist);
            }else {
                for(searchitem item : searchlist){
                        searchitems.add(item);
                }
            }
            results.values = searchitems;
            results.count = searchitems.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((searchitem) resultValue).getAreaName();
        }
    };
}
