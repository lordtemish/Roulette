package com.dynamica.lordtemich.roulette.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dynamica.lordtemich.roulette.R;

import java.util.List;

public class RouletteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        private myHolder(View view){
            super(view);
        }
    }
    List<String> list;
    Context context;
    public RouletteAdapter(List<String> strings){
        list=strings;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.roulette_row,viewGroup,false);
        return new myHolder(view);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
