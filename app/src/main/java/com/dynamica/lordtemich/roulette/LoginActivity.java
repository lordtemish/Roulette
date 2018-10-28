package com.dynamica.lordtemich.roulette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dynamica.lordtemich.roulette.Fragments.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText login, password;
    TextView gameTextView;
    ProgressView progressView;
    public RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue= Volley.newRequestQueue(this);
        createViews();

        gameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest("login");
            }
        });
    }
    private void createViews(){
        login=(EditText) findViewById(R.id.login);
        password=(EditText) findViewById(R.id.password);
        gameTextView=(TextView) findViewById(R.id.gameTextView);
        progressView=(ProgressView) findViewById(R.id.progressView);
    }
    private void sendRequest(String s){
        try {
            progressView.setVisibility(View.VISIBLE);
            String url = "https://pepero.dynamica.kz/api/" + s;
            JSONObject params = new JSONObject();
            if(login.getText().length()>0 && password.getText().length()>0) {
                params.put("login", login.getText() + "");
                params.put("password", password.getText() + "");
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressView.setVisibility(View.GONE);
                        try {
                            Log.d("resp",response.toString());
                            if(response.has("message")){
                                String message = response.getString("message");
                                Log.d("message",message);
                            }
                            if(response.has("token")) {
                                String tok = response.getString("token");
                                Log.d("token", tok);
                                setInfo(tok);
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressView.setVisibility(View.GONE);
                        error.printStackTrace();
                        makeToast("Проблемы соеденения");
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> params=new HashMap<>();
                        params.put("Accept", "application/json");
                        params.put("Content-Type", "application/json");
                        return params;
                    }
                };
                requestQueue.add(objectRequest);
            }
            else {
                Toast.makeText(this,"Введите данные",Toast.LENGTH_LONG).show();
                progressView.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void makeToast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
    private void setInfo(String token){
        Intent output=getIntent();
        output.putExtra("token",token);
        setResult(RESULT_OK,output);
        Log.d("LOGINFr",output.getBooleanExtra("client",false)+"");
        finish();
    }
    @Override
    public void onBackPressed(){
    }
}
