package com.example.appthesis;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineerDetails extends AppCompatActivity {
    TextView fname, lname, address, email, contactNo, yrs_exp;
    String url_details_engineer = "http://192.168.43.189/android_register_login/show_engineer_details.php";
    String url_engineer_rates = "http://192.168.43.189/android_register_login/show_engineer_rates.php";
    ArrayList<String> list =new ArrayList<>();
    private List<Previews> previews = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_details);


        id = getIntent().getStringExtra("id");
        fname = findViewById(R.id.textView37);
        lname = findViewById(R.id.textView44);
        address = findViewById(R.id.textView55);
        email = findViewById(R.id.textView68);
        contactNo = findViewById(R.id.textView69);
        yrs_exp = findViewById(R.id.textView70);

        recyclerView = findViewById(R.id.recyclerView10);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        showDetails();
        showRatesandReviews();

    }


    private void showDetails() {
        StringRequest request = new StringRequest(Request.Method.POST, url_details_engineer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("response");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if(message.equals("success")){
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Fname = object.getString("fname");
                        String Lname = object.getString("lname");
                        String Address = object.getString("address");
                        String Exp = object.getString("yrs_exp");
                        String Contact = object.getString("contact");
                        String Email = object.getString("email");

                        fname.setText(Fname);
                        lname.setText(Lname);
                        address.setText(Address);
                        yrs_exp.setText(Exp);
                        contactNo.setText(Contact);
                        email.setText(Email);
                    }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
      }

    private void showRatesandReviews() {
        StringRequest request = new StringRequest(Request.Method.POST, url_details_engineer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("response");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String Fname = object.getString("fname");
                            String Lname = object.getString("lname");
                            String rate = object.getString("rate");
                            String comment = object.getString("comment");
                            String date = object.getString("date");

                          String name = "" +Fname + " " + Lname;
                           float f = Float.parseFloat(rate);
                           previews.add(new Previews(name, f, comment, date));
                            initializeAdapter();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void initializeAdapter() {
        EngineerRatings adapter = new EngineerRatings(previews, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}

