package com.example.appthesis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingOption extends AppCompatActivity {
  RecyclerView recyclerView;
  Context context;
  Button book;
  String option="";
  String id = "";
  String mtype="";
  ServiceAdapter serviceAdapter;
  SessionManager sessionManager;
  ServiceType serviceType;
  List<ServiceType> serviceTypeList = new ArrayList<>();
  String url_booking_option = "http://192.168.43.189/android_register_login/retrieve_service.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_option);
        context = this;


        //radioGroup = findViewById(R.id.radiogroup_option);
        book = findViewById(R.id.button2);
        id  = getIntent().getStringExtra("Id");
        mtype = getIntent().getStringExtra("ClientType");


        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        StringRequest request = new StringRequest(Request.Method.POST, url_booking_option, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if(message.equals("success")) {
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String service = object.getString("service_type").trim();
                            System.out.println("service " + service);
                            //Intent intent = new Intent(BookingOption.this, MainActivity.class);
                           // startActivity(intent);
                            //Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();

                            serviceTypeList.add(new ServiceType(service));
                            ServiceAdapter adapter = new ServiceAdapter(serviceTypeList, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    }
                    else
                        Toast.makeText(getApplicationContext(), "Unsuccessful!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


     book.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(mtype.equals("household")) {
                Intent intent = new Intent(getApplicationContext(), BookingSchedule.class);
                intent.putExtra("Id", id);
                intent.putExtra("type", option);
                intent.putExtra("ClientType", mtype);
                startActivity(intent);
            }

         }
     });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            option = intent.getStringExtra("type");
        }
    };
}
