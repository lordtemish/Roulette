package com.dynamica.lordtemich.roulette.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.Forms.RouletteForm;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import java.util.ArrayList;
import java.util.List;

public class RozygrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView name, num;
        ConstraintLayout wholeLayout;
        private myHolder(View view){
            super(view);
            num=(TextView) view.findViewById(R.id.numberTextView);
            name=(TextView) view.findViewById(R.id.nameTextView);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);
            ((MainActivity) context).setFonttypes("bold",num,name);
        }
        private void setInfo(RouletteForm form){
            num.setText(form.getId()+"");
            name.setText(form.getName());
            if (form.isClicked()){
                wholeLayout.setBackgroundResource(R.drawable.redsettings);
                num.setTextColor(context.getResources().getColor(R.color.white));
                name.setTextColor(context.getResources().getColor(R.color.white));
            }
            else {
                wholeLayout.setBackgroundResource(R.drawable.lightgrey_page);
                num.setTextColor(context.getResources().getColor(R.color.darkgrey));
                name.setTextColor(context.getResources().getColor(R.color.darkgrey));
            }
        }
    }
    List<RouletteForm> list;
    Context context;
    View.OnClickListener listener;
    public RozygrAdapter(List<RouletteForm> rouletteForms){
        list=rouletteForms;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.rozygr_row,viewGroup,false);
        return new myHolder(view);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    public List<Integer> getIntegers(){
        List<Integer> integer=new ArrayList<>();
        for(RouletteForm i:list){
            if(!i.isClicked()){
                integer.add(i.getId());
            }
        }
        return integer;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final  int i) {
        myHolder holder=(myHolder) viewHolder;
        holder.setInfo(list.get(i));
        holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setClicked(!list.get(i).isClicked());
                notifyItemChanged(i);
                listener.onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
