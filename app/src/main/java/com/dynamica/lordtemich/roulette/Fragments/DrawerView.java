package com.dynamica.lordtemich.roulette.Fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.R;

public class DrawerView extends FrameLayout {
    TextView gameTextView, settingsTextView;
    public DrawerView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public DrawerView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public DrawerView(Context context){
        super(context);
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.drawer_view, null);
        addView(view);
        createViews(view);
    }
    private void createViews(View view){
        settingsTextView=(TextView) view.findViewById(R.id.settingsTextView);
        gameTextView=(TextView)view.findViewById(R.id.gameTextView);
    }

    public void setGameListener(OnClickListener on){
        gameTextView.setOnClickListener(on);
    }
    public void setSettingsListener(OnClickListener on){
        settingsTextView.setOnClickListener(on);
    }
}
