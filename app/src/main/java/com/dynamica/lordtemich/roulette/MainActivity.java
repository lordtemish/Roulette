package com.dynamica.lordtemich.roulette;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dynamica.lordtemich.roulette.Fragments.DrawerView;
import com.dynamica.lordtemich.roulette.Fragments.LoginFragment;
import com.dynamica.lordtemich.roulette.Fragments.RouletteFragment;
import com.dynamica.lordtemich.roulette.Fragments.SettingsFragment;
import com.dynamica.lordtemich.roulette.Fragments.StartFragment;

public class MainActivity extends AppCompatActivity {
    FrameLayout contentFrame;
    DrawerLayout drawerLayout;
    LinearLayout drawerLinearLayout;
    DrawerView drawerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViews();

        setFragment(R.id.content_frame,new StartFragment());
        drawerView.setGameListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.content_frame,new LoginFragment());
                closeDrawer();
            }
        });
        drawerView.setSettingsListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(R.id.content_frame,new SettingsFragment());
                closeDrawer();
            }
        });
    }

    private void createViews(){
        contentFrame=(FrameLayout) findViewById(R.id.content_frame);
        drawerLinearLayout=(LinearLayout) findViewById(R.id.drawerLinearLayout);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView=(DrawerView) findViewById(R.id.drawerView);
    }
    public void setFragment(int id,Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(id,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    public void setRecyclerViewOrientation(RecyclerView view, int orientation){
        view.setItemAnimator(new DefaultItemAnimator());
        view.setLayoutManager(new LinearLayoutManager(this,orientation,false));
    }

    private void closeDrawer(){
        drawerLayout.closeDrawer(drawerLinearLayout);
    }
    private void openDrawer(){
        drawerLayout.openDrawer(drawerLinearLayout);
    }
    private void onDrawer(){
        if(drawerLayout.isDrawerOpen(drawerLinearLayout)){
            closeDrawer();
        }
        else{
            openDrawer();
        }
    }
}
