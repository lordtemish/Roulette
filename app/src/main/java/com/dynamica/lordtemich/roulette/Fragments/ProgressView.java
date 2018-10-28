package com.dynamica.lordtemich.roulette.Fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.dynamica.lordtemich.roulette.R;

public class ProgressView extends FrameLayout {
    Context context;
    public ProgressView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        this.context=context;
        initView();
    }
    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    public ProgressView(Context context) {
        super(context);
        this.context=context;
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.progress_view, null);
        addView(view);
        createViews(view);
    }
    private void createViews(View view){

    }
}
