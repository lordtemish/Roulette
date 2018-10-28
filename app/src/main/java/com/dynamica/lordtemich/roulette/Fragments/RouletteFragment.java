package com.dynamica.lordtemich.roulette.Fragments;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.Adapters.RouletteAdapter;
import com.dynamica.lordtemich.roulette.Forms.RouletteForm;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouletteFragment extends Fragment {
    RecyclerView recyclerView;
    FrameLayout  pressLayout;
    ImageView pressTextView, wonItemImageView;
    ConstraintLayout recyclerLayout, buttsLayout;
    TextView nextTextView, cancelTextView, wonItemTextView;
    View.OnClickListener cancel, next;
    LinearLayout wonItemLayout;
    List<RouletteForm> ss;
    List<RouletteForm> strings;
    int pa=4;
    String name;
    public RouletteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        name=getArguments().getString("name","");
        pa=getArguments().getInt("prize", 2)-1;
        View view=inflater.inflate(R.layout.fragment_roulette, container, false);

        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.HORIZONTAL );
        strings=new ArrayList<>();
        ss=((MainActivity)getActivity()).types;
        String positions=((MainActivity) getActivity()).position;
        for(int i=0;i<10;i++){
            for(RouletteForm j:ss){
                strings.add(new RouletteForm(j));
            }
        }
        Random random=new Random();
        String s=((MainActivity)getActivity()).position;
        String[] a=s.split(" ");
        strings.add(new RouletteForm(ss.get(random.nextInt(6))));
        strings.add(new RouletteForm(ss.get(random.nextInt(6))));
        strings.add(new RouletteForm(ss.get(pa)));
        strings.add(new RouletteForm(ss.get(random.nextInt(6))));
        strings.get(strings.size()-2).setAlpha(true);

        RouletteAdapter adapter=new RouletteAdapter(strings);
        recyclerView.setAdapter(adapter);

        recyclerLayout.setVisibility(View.INVISIBLE);
        buttsLayout.setVisibility(View.GONE);


        pressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressLayout.setVisibility(View.GONE);
                recyclerLayout.setVisibility(View.VISIBLE);
                recViewMove(strings.size()-1);
            }
        });

       recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        cancel=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,new StartFragment());
            }
        };
        next=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,new WinFragment());
            }
        };

        cancelTextView.setOnClickListener(cancel);
        nextTextView.setOnClickListener(cancel);

        ((MainActivity)getActivity()).setFonttypes("bold",cancelTextView,nextTextView, wonItemTextView);
        return view;
    }
    private void recViewMove(int a){
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(a);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==0){
                   showButtons();
                }
            }
        });
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerVIew);
        pressLayout=(FrameLayout)view.findViewById(R.id.pressLayout);
        recyclerLayout=(ConstraintLayout) view.findViewById(R.id.recyclerLayout);
        buttsLayout =(ConstraintLayout) view.findViewById(R.id.buttsLayout);
        pressTextView=(ImageView) view.findViewById(R.id.pressTextView);
        wonItemImageView=(ImageView) view.findViewById(R.id.wonItemImageView);
        wonItemLayout=(LinearLayout) view.findViewById(R.id.wonItemLayout);
        wonItemTextView=(TextView) view.findViewById(R.id.wonItemTextView);
        cancelTextView=(TextView) view.findViewById(R.id.cancelTextView);
        nextTextView=(TextView) view.findViewById(R.id.nextTextView);
    }
    private void showButtons(){
            double l=0;
            wonItemLayout.setVisibility(View.VISIBLE);
            buttsLayout.setVisibility(View.VISIBLE);
            hideAnimation(recyclerView);SystemClock.sleep(200);
            wonItemImageView.setImageResource(ss.get(pa).getDrawable());
            wonItemTextView.setText(ss.get(pa).getName());
            makeAnimation(wonItemLayout);
            makeAnimation(buttsLayout);
        if(ss.get(pa).getId()<7) {
            next = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WinFragment fragment = new WinFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("item", ss.get(pa).getName());
                    bundle.putInt("img", ss.get(pa).getExtraDrawable());
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).setFragment(R.id.content_frame, fragment);
                }
            };
            nextTextView.setOnClickListener(next);
        }
    }
    private void makeAnimation(final View view){
        Animation fadeOut = new AlphaAnimation(0, 1);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                view.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        view.startAnimation(fadeOut);
    }
    private void hideAnimation(final View view){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                view.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        view.startAnimation(fadeOut);
    }
}
