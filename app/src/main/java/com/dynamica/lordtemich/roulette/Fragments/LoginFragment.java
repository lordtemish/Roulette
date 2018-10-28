package com.dynamica.lordtemich.roulette.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dynamica.lordtemich.roulette.MainActivity;
import com.dynamica.lordtemich.roulette.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    TextView gameTextView;
    EditText name, phone, mail;
    ProgressView progressView;
    int prize=1;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        createViews(view);

        gameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrize();
            }
        });

        View.OnFocusChangeListener changeListener=new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                EditText editText=(EditText) view;
                if(b){
                    editText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
                }
                else{
                    editText.setHintTextColor(getActivity().getResources().getColor(R.color.darkgrey));
                }
            }
        };
        name.setOnFocusChangeListener(changeListener);
        phone.setOnFocusChangeListener(changeListener);
        mail.setOnFocusChangeListener(changeListener);

        ((MainActivity)getActivity()).setFonttypes("bold",name,phone,mail, gameTextView);
        progressView.setVisibility(View.GONE);
        return view;
    }
    private void setFr(){
        RouletteFragment fragment=new RouletteFragment();
        Bundle bundle=new Bundle();
        bundle.putString("name",name.getText()+"");
        bundle.putInt("prize",prize);
        fragment.setArguments(bundle);
        ((MainActivity) getActivity()).setFragment(R.id.content_frame, fragment);
    }
    private void getPrize(){
        progressView.setVisibility(View.VISIBLE);
        String url=((MainActivity) getActivity()).MAIN_URL+"client";
        if(name.getText().length()>0 && mail.getText().length()>0 && phone.getText().length()>0) {
            JSONObject object = new JSONObject();
            try {
                object.put("fullname", name.getText() + "");
                object.put("email", mail.getText() + "");
                object.put("phone", phone.getText() + "");
                JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressView.setVisibility(View.GONE);
                        Log.d("resp",response.toString());
                        try {
                            JSONObject obj=response.getJSONObject("win");
                            prize=obj.getInt("prize_id");
                            setFr();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressView.setVisibility(View.GONE);
                        error.printStackTrace();
                        ((MainActivity) getActivity()).showToast("Такой человек уже создан или Проблемы соеденения");
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("Accept", "application/json");
                        params.put("Content-Type", "application/json");
                        params.put("Authorization","Bearer "+((MainActivity)getActivity()).token);
                        return params;
                    }
                };
                ((MainActivity)getActivity()).requestQueue.add(objectRequest);
            } catch (Exception e) {
                progressView.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
        else{
            progressView.setVisibility(View.GONE);
            ((MainActivity)getActivity()).showToast("Введите все данные");
        }
    }
    private void createViews(View view){
        gameTextView=(TextView) view.findViewById(R.id.gameTextView);
        name=(EditText) view.findViewById(R.id.nameEditText);
        phone=(EditText) view.findViewById(R.id.phoneEditText);
        mail=(EditText) view.findViewById(R.id.mailEditText);
        progressView=(ProgressView) view.findViewById(R.id.progressView);
    }

}
