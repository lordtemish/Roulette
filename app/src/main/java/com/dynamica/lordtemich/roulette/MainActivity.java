package com.dynamica.lordtemich.roulette;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dynamica.lordtemich.roulette.Forms.RouletteForm;
import com.dynamica.lordtemich.roulette.Fragments.DrawerView;
import com.dynamica.lordtemich.roulette.Fragments.LoginFragment;
import com.dynamica.lordtemich.roulette.Fragments.ProgressView;
import com.dynamica.lordtemich.roulette.Fragments.RouletteFragment;
import com.dynamica.lordtemich.roulette.Fragments.SettingsFragment;
import com.dynamica.lordtemich.roulette.Fragments.StartFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String MAIN_URL="https://pepero.dynamica.kz/api/";
    public RequestQueue requestQueue;
    FrameLayout contentFrame;
    DrawerLayout drawerLayout;
    LinearLayout drawerLinearLayout;
    FrameLayout progressLayout;
    DrawerView drawerView;
    public String position=" ";
    public int win=7;
    SQLiteDatabase db;
    public String token=" ";
    public List<RouletteForm> types=new ArrayList<>();
    private void createTypes(){
        types.add(new RouletteForm(1,"Сумка Pepero", R.drawable.itemsumka,R.drawable.sumka));
        types.add(new RouletteForm(2,"Значки Pepero", R.drawable.itemznachki,R.drawable.znachok));
        types.add(new RouletteForm(3,"Футболка Pepero",R.drawable.itemtshirt,R.drawable.rabittshirt));
        types.add(new RouletteForm(4,"Бутылка Pepero", R.drawable.itembottle,R.drawable.bottle));
        types.add(new RouletteForm(5,"Бандл Пак", R.drawable.itemthreepeperio,R.drawable.threepepero));
        types.add(new RouletteForm(6,"Пак Pepero", R.drawable.itemonepepero,R.drawable.onepepero));
        types.add(new RouletteForm(7,"", R.drawable.item));
    }
    public void showToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createTypes();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(this);
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

        dataBase();
    }
    public void saveSettings(int interval, final String prizes){
        progressLayout.setVisibility(View.VISIBLE);
        String url=MAIN_URL+"user";
        try {
            JSONObject params = new JSONObject();
            params.put("interval", interval);
            params.put("prizes",prizes);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("ResponseSave",response.toString());
                    progressLayout.setVisibility(View.GONE);
                    showToast("Сохранено");
                    setFragment(R.id.content_frame,new SettingsFragment());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    showToast("Проблемы соеденения");
                    progressLayout.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> params=new HashMap<>();
                    params.put("Accept", "application/json");
                    params.put("Content-Type", "application/json");
                    params.put("Authorization","Bearer "+token);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e ){
            e.printStackTrace();
            progressLayout.setVisibility(View.GONE);
        }
    }
    private void dataBase(){
        SQLiteOpenHelper AlaportDataHelper = new RouletteDatabaseHelper(this);
        db = AlaportDataHelper.getReadableDatabase();
        Cursor cursor = db.query("position", new String[]{"PONUMBER"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        int lan=0;
        if(cursor.moveToFirst()){
            lan=cursor.getInt(0);
            Log.d("database",lan+"");
        }
        win=lan;
        String pas="";
        cursor.close();
        Cursor cur = db.query("rozygr", new String[]{"POSITIONS"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        if(cur.moveToFirst()){
            pas=cur.getString(0);
            Log.d("databas",pas);
        }
        position=pas;
        cur.close();
        Cursor ctok=db.query("token",new String[]{"ID"},"_id=?",new String[]{Integer.toString(1)},null,null,null);
        if(ctok.moveToFirst()){
            token=ctok.getString(0);
            Log.d("dataToken",token+" - "+token.length());
        }
        ctok.close();
        db.close();

        if(token.length()==1){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,1);
        }
        else{
            getUser();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            setFragment(R.id.content_frame,new StartFragment());
            closeDrawer();
            token=data.getStringExtra("token");
            updateToken(token);
            getUser();
        }
    }
    private void getUser(){
        String url=MAIN_URL+"user/";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {
                Log.d("resp",resp.toString());
                try{
                    JSONObject response=resp.getJSONObject("user");
                    win=response.getInt("interval");
                    String poss=response.getString("prizes");
                    position="";
                    for(RouletteForm type:types){
                        if(!poss.contains(type.getId()+"")){
                            position+=type.getId()+" ";
                        }
                    }
                    if(position.length()==0){
                        position=" ";
                    }
                    else{
                        position=position.substring(0,position.length()-1);
                    }
                    saveItems(win,position);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization","Bearer "+token);
                return params;
            }
        };
        requestQueue.add(objectRequest);
    }
    private void saveItems(int a, String s){
        SQLiteOpenHelper AlaportDataHelper = new RouletteDatabaseHelper(this);
        db = AlaportDataHelper.getReadableDatabase();
        ContentValues cv=new ContentValues();
        ContentValues cv2=new ContentValues();
        cv.put("POSITIONS",s);
        cv2.put("PONUMBER",a);
        db.update("position",cv2,"_id=?",new String[] {Integer.toString(1)});
        db.update("rozygr",cv,"_id=?",new String[] {Integer.toString(1)});
        db.close();
    }
    public void logOut(){
        token=" ";
        updateToken(" ");
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivityForResult(intent,1);
    }
    public void updateToken(String token){
        SQLiteOpenHelper AlaportDataHelper = new RouletteDatabaseHelper(this);
        db = AlaportDataHelper.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("ID",token);
        db.update("token",cv,"_id=?",new String[]{Integer.toString(1)});
        db.close();
    }
    public Typeface getTypeFace(String s){
        switch (s){
            case "demibold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            case "medium":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            case "light":
                return (Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
            case "black":
                return (Typeface.createFromAsset(getAssets(),"fonts/Avenir-Black.ttf"));
            case "bold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            case "regular":
                return Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Regular.ttf");
            default:
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-It.ttf"));
        }
    }
    public void setFonttypes(String s, TextView... textViews){
        for(TextView i:textViews){
            i.setTypeface(getTypeFace(s));
        }
    }
    private void createViews(){
        contentFrame=(FrameLayout) findViewById(R.id.content_frame);
        progressLayout=(FrameLayout) findViewById(R.id.progressLayout);
        drawerLinearLayout=(LinearLayout) findViewById(R.id.drawerLinearLayout);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView=(DrawerView) findViewById(R.id.drawerView);
    }
    public void setFragment(int id,Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
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
    public void onClick(View view){

    }

}
