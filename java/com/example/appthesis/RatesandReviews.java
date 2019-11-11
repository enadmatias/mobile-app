package com.example.appthesis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RatesandReviews extends AppCompatActivity{
    ImageView add, comments, update;
    RatingBar ratingBar, ratingBar2, ratingBar3;
    EditText comment;
    RecyclerView recyclerView;
    Context context;
    String url_edit_detail= "http://192.168.43.189/android_register_login/ratesandreviews.php";
    String url_retrieve_detail= "http://192.168.43.189/android_register_login/retrievedata.php";
    String url_retrieve_previews= "http://192.168.43.189/android_register_login/show_previews.php";
    String dateWithoutTime, rates_id, rates_value, rates_comment, rates_date, client_id;
    private List<Previews> previews = new ArrayList<>();

    SessionManager sessionManager;
    String miD, emp_id;
    TextView dates, reviews, names, edit_preview, rates_avg;
    String  firstname, lastname, rateavg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratesand_reviews);

        ratingBar2 = findViewById(R.id.ratingBar2);
        dates = findViewById(R.id.textView_date);
        reviews = findViewById(R.id.textView_rate_message);
        names = findViewById(R.id.textView_rate_names);
        edit_preview = findViewById(R.id.textView_edit_preview);
        rates_avg = findViewById(R.id.textView34);

        sessionManager = new SessionManager(this);



        recyclerView = findViewById(R.id.recyclerView_previews);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        PreviewsAdapter adapter = new  PreviewsAdapter(previews);


        HashMap<String, String> user = sessionManager.getUserDetail();
          miD = user.get(sessionManager.ID);
        String fname = user.get(sessionManager.NAME);
        String lname = user.get(sessionManager.LNAME);



        names.setText(" " + fname + " " + lname);
        edit_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateReviews.class);
               intent.putExtra("rates_id", rates_id);
               intent.putExtra("client_id", miD);
               Toast.makeText(getApplicationContext(), "Id" +miD + "rates_id" + rates_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        StringRequest request1  = new StringRequest(Request.Method.POST, url_retrieve_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject  jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            rates_id = object.getString("rates_id").trim();
                            client_id = object.getString("client_id").trim();
                            firstname = object.getString("client_fname").trim();
                            lastname = object.getString("client_lname").trim();
                            rates_value = object.getString("rates_value").trim();
                            rates_comment = object.getString("rates_comment").trim();
                            rates_date = object.getString("rates_date").trim();
                            rateavg = object.getString("rate_avg").trim();

                            rates_avg.setText(rateavg);
                            String fullname = "" + firstname + " " + lastname;
                            final float f = Float.parseFloat(rates_value);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                            try {
                                Date d1 = sdf1.parse(rates_date);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                dateWithoutTime = sdf.format(d1);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(miD.equals(client_id)){
                            ratingBar2.setRating(f);
                            reviews.setText(rates_comment);
                            dates.setText(dateWithoutTime);
                            }
                            else{
                                previews.add(new Previews(fullname,f,rates_comment,dateWithoutTime));
                                initializeAdapter();
                            }
                        }
                        if(!miD.equals(client_id)){
                            rates_id = "null";
                        }
                    }
                    else{
                        rates_id = "null";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", miD);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request1);
    }



    private void initializeAdapter() {
        PreviewsAdapter adapter = new PreviewsAdapter(previews);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
