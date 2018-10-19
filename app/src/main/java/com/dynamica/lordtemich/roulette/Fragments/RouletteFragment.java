package com.dynamica.lordtemich.roulette.Fragments;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dynamica.lordtemich.roulette.Adapters.RouletteAdapter;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouletteFragment extends Fragment {
    RecyclerView recyclerView;
    FrameLayout butFrame;
    int pa=0;
    public RouletteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_roulette, container, false);

        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.HORIZONTAL );
        final List<String > strings=new ArrayList<>();
        strings.add("daasfaf");
        strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");
        strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");
        strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");
        strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");
        strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");strings.add("daasfaf");
        final RouletteAdapter adapter=new RouletteAdapter(strings);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(strings.size()-3);
        pa=strings.size()-3;
        butFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pa==0) {
                    recyclerView.smoothScrollToPosition(strings.size()-3);
                    pa=strings.size()-3;
                }
                else{
                    recyclerView.smoothScrollToPosition(0);
                    pa=0;
                }
            }
        });
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerVIew);
        butFrame=(FrameLayout) view.findViewById(R.id.butFrame);
    }

}
