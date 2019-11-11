package com.example.appthesis;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class BookingDetails extends AppCompatActivity {
    TextInputEditText contact_person, landmark, houseno, street, barangay, city, town, zipcode, contact_no, notes;
    String url_info_book = "http://192.168.43.189/android_register_login/booknew.php";
    Button proceed;
    SessionID sessionID;
    SessionManager sessionManager;
    String id, services, client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);



        sessionID = new SessionID(this);
        sessionManager = new SessionManager(this);
        proceed = findViewById(R.id.button17);

        HashMap<String, String> user = sessionID.getUserDetail();
        id = user.get(sessionID.USER_ID);

        HashMap<String, String> user1 = sessionManager.getUserDetail();
        client_id = user1.get(sessionManager.ID);

        Bundle b=this.getIntent().getExtras();
        if(b!=null){
            this.services= String.valueOf(b.getStringArrayList("selected"));
        }
        System.out.print("Services: " + services);

        contact_person = findViewById(R.id.edit_Contact_Person);
        landmark = findViewById(R.id.text_landmark);
        houseno = findViewById(R.id.text_house);
        street = findViewById(R.id.text_street);
        barangay = findViewById(R.id.text_barangay);
        city = findViewById(R.id.text_city);
        town = findViewById(R.id.text_town);
        zipcode = findViewById(R.id.text_zip_code);
        contact_no = findViewById(R.id.text_number);
        notes = findViewById(R.id.text_notes);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String person = contact_person.getText().toString();
                final String mark = landmark.getText().toString();
                final String house = houseno.getText().toString();
                final String strt = street.getText().toString();
                final String brgy = barangay.getText().toString();
                final String cty  = city.getText().toString();
                final String twn = town.getText().toString();
                final String code = zipcode.getText().toString();
                final String number = contact_no.getText().toString();
                final String note = notes.getText().toString();




                StringRequest request = new StringRequest(Request.Method.POST, url_info_book, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("response");
                            JSONArray jsonArray = jsonObject.getJSONArray("values");
                            if(message.equals("success")){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String book_id = object.getString("book_id");
                                Intent n = new Intent(getApplicationContext(), SchedulingPage.class);
                                n.putExtra("book_id", book_id);
                                startActivity(n);
                                Toast.makeText(getApplicationContext(), "Success  " , Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Failed" + client_id, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                    })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("client_id", client_id);
                        params.put("service_provider", id);
                        params.put("person", person);
                        params.put("landmark", mark);
                        params.put("house", house);
                        params.put("street", strt);
                        params.put("barangay", brgy);
                        params.put("municipality", cty);
                        params.put("town", twn);
                        params.put("code", code);
                        params.put("number", number);
                        params.put("note", note);
                        params.put("services", services);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);

            }
        });

    }
}
