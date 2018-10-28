package com.dynamica.lordtemich.roulette.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
FrameLayout wholeLayout;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_start, container, false);
        wholeLayout=(FrameLayout) view.findViewById(R.id.wholeLayout);
        wholeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogin();
            }
        });
        return view;
    }
    public void setLogin(){
        ((MainActivity) getActivity()).setFragment(R.id.content_frame,new LoginFragment());
    }
}
