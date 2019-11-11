package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateReviews extends AppCompatActivity {
  RatingBar  ratingBar;
  EditText comment;
  Button Update;
  String url_update_detail= "http://192.168.43.189/android_register_login/UpdateData.php";
  String url_edit_detail= "http://192.168.43.189/android_register_login/ratesandreviews.php";
  String bookid, clientId, emp_id;
  SessionID sessionID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reviews);

        sessionID = new SessionID(this);
        ratingBar = findViewById(R.id.ratingBar1);
        comment = findViewById(R.id.editText4);
        Update = findViewById(R.id.button9);
        bookid = getIntent().getStringExtra("rates_id");
        clientId = getIntent().getStringExtra("client_id");

        HashMap<String, String> user = sessionID.getUserDetail();
        emp_id = user.get(sessionID.USER_ID);


        Update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               float ratingValue = ratingBar.getRating();
               String Comment = comment.getText().toString().trim();
               final String reviews = Float.toString(ratingValue);
               if(bookid.equals("null")){
                 insertData(reviews, Comment);
               }
               else {
                   Update(reviews, Comment);
               }
           }
       });

    }

    private void insertData(final String rates, final String comments) {
        StringRequest request  = new StringRequest(Request.Method.POST, url_edit_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if(message.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), RatesandReviews.class);
                        startActivity(intent);
                        UpdateReviews.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", clientId);
                params.put("empId", emp_id);
                params.put("reviews", rates);
                params.put("comment", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    public void Update(final String rates, final String comments){

        StringRequest request  = new StringRequest(Request.Method.POST, url_update_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if(message.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), RatesandReviews.class);
                        startActivity(intent);
                        UpdateReviews.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rates_id", bookid);
                params.put("User_ID", clientId);
                params.put("reviews", rates);
                params.put("comment", comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
