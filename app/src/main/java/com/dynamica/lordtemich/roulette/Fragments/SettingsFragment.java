package com.dynamica.lordtemich.roulette.Fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dynamica.lordtemich.roulette.Adapters.RozygrAdapter;
import com.dynamica.lordtemich.roulette.Adapters.SettingsAdapter;
import com.dynamica.lordtemich.roulette.Forms.RouletteForm;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;
import com.dynamica.lordtemich.roulette.RouletteDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    List<Integer> list;
    RecyclerView recyclerView, rozygrRecyclerView;
    FrameLayout extraLayout;
    ConstraintLayout rozygrLayout;
    CounterView counterView;
    RozygrAdapter rozygrAdapter;
    SettingsAdapter settingsAdapter;
    List<RouletteForm> rouletteForms;
    TextView save, saveSettings, settings, posT, rozNe;
    SQLiteDatabase db;
    View.OnClickListener saveListener;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.HORIZONTAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(rozygrRecyclerView, LinearLayoutManager.VERTICAL);
        list=new ArrayList<>();list.add(1);list.add(2);list.add(5);
        settingsAdapter=new SettingsAdapter(list);
        rouletteForms=((MainActivity) getActivity()).types;
        rozygrAdapter=new RozygrAdapter(rouletteForms);
        rozygrRecyclerView.setAdapter(rozygrAdapter);
        recyclerView.setAdapter(settingsAdapter);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRozygr();
            }
        };
        rozygrLayout.setOnClickListener(listener);
        extraLayout.setOnClickListener(listener);

        rozygrAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNumbers(rozygrAdapter.getIntegers());
            }
        });

        datad();
        saveListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                for(int i=0;i<list.size();i++){
                    if(i==list.size()-1){
                        s=s+list.get(i);
                    }
                    else{
                        s=s+list.get(i)+" ";
                    }
                }
                if(s.length()==0){
                    s=" ";
                }
                saveItems(counterView.getPage(),s);
            }
        };
        save.setOnClickListener(saveListener);
        saveSettings.setOnClickListener(saveListener);

        ((MainActivity)getActivity()).setFonttypes("bold",save,saveSettings,settings,posT,rozNe);
        return view;
    }
    private void datad(){
        SQLiteOpenHelper AlaportDataHelper = new RouletteDatabaseHelper(getContext());
        db = AlaportDataHelper.getReadableDatabase();
        Cursor cursor = db.query("position", new String[]{"PONUMBER"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        int lan=0;
        if(cursor.moveToFirst()){
           lan=cursor.getInt(0);
            Log.d("database",lan+"");
        }
        counterView.changePage(lan);
        String pas="";
        cursor.close();
        Cursor cur = db.query("rozygr", new String[]{"POSITIONS"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        if(cur.moveToFirst()){
            pas=cur.getString(0);
            Log.d("databas",pas);
        }
        setItems(pas);
        cur.close();
        db.close();
    }
    private void setItems(String s){
        String[] ar=s.split(" ");
        list.clear();
        for(RouletteForm j:rouletteForms){
            j.setClicked(true);
        }
        for(int i=0;i<ar.length;i++){
            list.add(Integer.parseInt(ar[i]));
            for(RouletteForm j:rouletteForms){
                if(j.getId()==Integer.parseInt(ar[i])){
                    j.setClicked(false);
                    break;
                }
            }
        }
        settingsAdapter.notifyDataSetChanged();
        rozygrAdapter.notifyDataSetChanged();
    }
    private void saveItems(int a, String s){
        SQLiteOpenHelper AlaportDataHelper = new RouletteDatabaseHelper(getContext());
        db = AlaportDataHelper.getReadableDatabase();
        ContentValues cv=new ContentValues();
        ContentValues cv2=new ContentValues();
        cv.put("POSITIONS",s);
        cv2.put("PONUMBER",a);
        db.update("position",cv2,"_id=?",new String[] {Integer.toString(1)});
        db.update("rozygr",cv,"_id=?",new String[] {Integer.toString(1)});
        db.close();


        String prizes="[";
        for(int i=1;i<7;i++){
            if(!s.contains(i+"")){
                prizes+=i+",";
            }
        }
        prizes=prizes.substring(0,prizes.length()-1)+"]";

        ((MainActivity)getActivity()).saveSettings(a, prizes);
    }
    private void setNumbers(List<Integer> inte){
        list.clear();list.addAll(inte);
        settingsAdapter.notifyDataSetChanged();
    }
    private void setRozygr(){
        if(extraLayout.getVisibility()==View.VISIBLE){
            extraLayout.setVisibility(View.GONE);
        }
        else{
            extraLayout.setVisibility(View.VISIBLE);
        }
    }

    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        rozygrRecyclerView=(RecyclerView) view.findViewById(R.id.rozygrRecyclerView);
        extraLayout=(FrameLayout) view.findViewById(R.id.extraLayout);
        rozygrLayout=(ConstraintLayout) view.findViewById(R.id.rozygrLayout);
        counterView=(CounterView) view.findViewById(R.id.counterView);
        save=(TextView) view.findViewById(R.id.saveTextView);
        saveSettings=(TextView) view.findViewById(R.id.saveSettings);
        settings=(TextView) view.findViewById(R.id.settings);
        posT=(TextView) view.findViewById(R.id.posT);
        rozNe=(TextView) view.findViewById(R.id.rozNe);
    }

}
