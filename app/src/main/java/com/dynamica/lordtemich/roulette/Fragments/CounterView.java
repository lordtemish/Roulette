package com.dynamica.lordtemich.roulette.Fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

public class CounterView extends FrameLayout {
    private int max=9;
    TextView number;
    ImageView minus, plus;
    int page=0;
    Context context;
    public CounterView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        this.context=context;
        initView();
    }
    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    public CounterView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public int getPage() {
        return page;
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.counter_view, null);
        addView(view);
        createViews(view);

        checkPage();
        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(-1);
            }
        });
        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(+1);
            }
        });
        ((MainActivity) context).setFonttypes("bold",number);
    }
    private void createViews(View view){
        minus=(ImageView) view.findViewById(R.id.minusTextView);
        plus=(ImageView) view.findViewById(R.id.plusTextView);
        number=(TextView) view.findViewById(R.id.numberTextView);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }
    public void changePage(int a){
        page+=a;
        if(page>max)
            page=1;
        if(page<1)
            page=max;
        checkPage();
    }
    private void checkPage(){
        number.setText(page+"");
    }
}