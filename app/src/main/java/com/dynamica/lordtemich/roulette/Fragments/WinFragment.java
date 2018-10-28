package com.dynamica.lordtemich.roulette.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {
    TextView nameTextView, itemTextView, pozdr;
    ImageView image;
    String name, item;
    LinearLayout imageLinear;
    int img;
    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle ar=getArguments();
        name=ar.getString("name","");
        item=ar.getString("item","");
        img=ar.getInt("img",R.drawable.sumka);
        View view= inflater.inflate(R.layout.fragment_win, container, false);
        createView(view);

        nameTextView.setText(name);
        image.setImageResource(img);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,400);
        int top=0,right=30;
        switch (img){
            case R.drawable.znachok:
                top=30;
                right=50;
                break;
            case R.drawable.onepepero:
                top=60;
                right=110;
                break;
            case R.drawable.threepepero:
                top=60;
                right=50;
                break;
            case R.drawable.rabittshirt:
                top=60;
                right=50;
                break;
                case R.drawable.bottle:
                    top=50;
                    right=50;
                    break;
            default:

                break;
        }
        params.setMargins(right, top, right, 0);
        image.setLayoutParams(params);
        itemTextView.setText(item);

        ((MainActivity)getActivity()).setFonttypes("bold",nameTextView, itemTextView, pozdr);
        return view;
    }
    private void createView(View view){
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        itemTextView=(TextView) view.findViewById(R.id.itemTextView);
        pozdr=(TextView) view.findViewById(R.id.pozdr);
        image=(ImageView) view.findViewById(R.id.image);
        imageLinear=(LinearLayout) view.findViewById(R.id.imageLinear);
    }
}
