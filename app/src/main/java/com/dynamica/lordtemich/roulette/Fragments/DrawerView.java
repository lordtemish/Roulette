package com.dynamica.lordtemich.roulette.Fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

public class DrawerView extends FrameLayout {
    TextView gameTextView, settingsTextView, logout;
    Context context;
    public DrawerView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        this.context=context;
        initView();
    }
    public DrawerView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context=context;
        initView();
    }
    public DrawerView(Context context){
        super(context);
        this.context=context;
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.drawer_view, null);
        addView(view);
        createViews(view);

        ((MainActivity)context).setFonttypes("bold",settingsTextView,gameTextView, logout);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).logOut();
            }
        });
    }
    private void createViews(View view){
        settingsTextView=(TextView) view.findViewById(R.id.settingsTextView);
        gameTextView=(TextView)view.findViewById(R.id.gameTextView);
        logout=(TextView)view.findViewById(R.id.logout);
    }

    public void setGameListener(OnClickListener on){
        gameTextView.setOnClickListener(on);
    }
    public void setSettingsListener(OnClickListener on){
        settingsTextView.setOnClickListener(on);
    }
}
