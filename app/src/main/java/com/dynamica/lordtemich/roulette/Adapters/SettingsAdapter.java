package com.dynamica.lordtemich.roulette.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        private myHolder(View view){
            super(view);
            textView=(TextView) view.findViewById(R.id.text);
            ((MainActivity) context).setFonttypes("bold",textView);
        }
    }
    List<Integer> list;
    Context context;
    public SettingsAdapter(List<Integer> integers){
        list=integers;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.settings_row,viewGroup,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            myHolder holder=(myHolder) viewHolder;
            holder.textView.setText(list.get(i)+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
