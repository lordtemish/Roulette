package com.dynamica.lordtemich.roulette.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.Forms.RouletteForm;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import java.util.List;

public class RouletteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ImageView wholeLayout;
        TextView nameTextView;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            wholeLayout=(ImageView ) view.findViewById(R.id.wholeLayout);
            wholeLayout.setAlpha(1f);
            ((MainActivity)context).setFonttypes("bold",nameTextView);
        }
        private void setAlpha(){
            wholeLayout.setAlpha(1f);
            nameTextView.setVisibility(View.VISIBLE);
        }
        private void notAlpha(){
            wholeLayout.setAlpha(0.7f);
            nameTextView.setVisibility(View.GONE);
        }
        private void setInfo(RouletteForm form){
            nameTextView.setText(form.getName());
            if(form.isAlpha()){
                setAlpha();
            }
            else
                notAlpha();
            wholeLayout.setImageResource(form.getDrawable());

        }
    }
    private List<RouletteForm> list;
    private Context context;


    public RouletteAdapter(List<RouletteForm> strings){
        list=strings;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        myHolder holder=(myHolder) viewHolder;
        holder.setInfo(list.get(i));

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
