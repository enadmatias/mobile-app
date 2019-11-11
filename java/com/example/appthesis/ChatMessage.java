package com.example.appthesis;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ChatMessage extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MessageListAdapter adapter;
    Context context;
    EditText message;
    ImageView send;
    SessionManager sessionManager;
    private ArrayList<Message> messageList = new ArrayList<>();
    String url_message = "http://192.168.43.189/android_register_login/messaging.php";
    String url_sent_message = "http://192.168.43.189/android_register_login/sent_message.php";
    String message_code = "receiver", mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        mRecyclerView = findViewById(R.id.recyclerView5);
        mRecyclerView.setHasFixedSize(true);
        message = findViewById(R.id.editText21);
        send = findViewById(R.id.imageView38);
        sessionManager = new SessionManager(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MessageListAdapter(messageList, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        HashMap<String, String> user = sessionManager.getUserDetail();
        mid = user.get(sessionManager.ID);


        StringRequest request = new StringRequest(Request.Method.POST, url_message, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("notification");
                    if(message.trim().equals("success")){
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String dataBody = object.getString("message").trim();
                            String dataCode = object.getString("code").trim();
                            Message m = new Message( dataBody,dataCode);
                            messageList.add(m);

                        }
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        //do nothing
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                params.put("id", mid);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String data = message.getText().toString().trim();
            sendMessage(data,mid);


            }
        });

    }
    public void sendMessage(final String message, final String userid){
        StringRequest request = new StringRequest(Request.Method.POST, url_sent_message, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("success")){
                    System.out.print(response);

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
                params.put("userid", userid);
                params.put("code", message_code);
                params.put("text", message);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    }





