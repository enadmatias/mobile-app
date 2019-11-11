package com.example.appthesis;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class BookPage extends AppCompatActivity {
    EditText code;
    Button proceed;
    String id = "", type="";
    String url_read_code = "http://192.168.43.189/android_register_login/read_code.php";
    String url_update_code = "http://192.168.43.189/android_register_login/update_code.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);


         id = getIntent().getStringExtra("Id");
         type = getIntent().getStringExtra("type");

         code = findViewById(R.id.editText);
         proceed= findViewById(R.id.btn_next);
         proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String matchCode = code.getText().toString();
                if(!matchCode.isEmpty()){
                StringRequest request  = new StringRequest(Request.Method.POST, url_read_code, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("response");
                            JSONArray jsonArray = jsonObject.getJSONArray("value");
                            if(message.equals("success")){
                                for(int i=0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String code = object.getString("code");
                                    if(type.equals("household")){
                                        //Intent intent =   new Intent(getApplicationContext(), BookingOption.class);
                                        //intent.putExtra("Id", id);
                                        //intent.putExtra("ClientType", type);
                                        //startActivity(intent);
                                        emptyCode(id,type);
                                    }
                                    else if(type.equals("company")){
                                        //Intent intent =   new Intent(getApplicationContext(), BookingSchedule.class);
                                        //intent.putExtra("Id", id);
                                        //intent.putExtra("ClientType", type);
                                        //startActivity(intent);
                                        emptyCode(id, type);
                                    }

                                }
                            }
                            else{
                                code.setError("Code do not Match");
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
                        params.put("id", id);
                        params.put("key_code", matchCode);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
                }
                else {
                    code.setError("Field is Empty");
                }

            }
        });


    }

public  void emptyCode(final String userid, final String usertype){
    StringRequest request  = new StringRequest(Request.Method.POST, url_update_code, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("response");
                if(message.equals("success")){
                   System.out.print("message " + message);
                    if(usertype.equals("household")){
                        Intent intent =   new Intent(getApplicationContext(), BookingOption.class);
                        intent.putExtra("Id", userid);
                        intent.putExtra("ClientType", usertype);
                        startActivity(intent);
                    }
                    else if(usertype.equals("company")){
                        Intent intent =   new Intent(getApplicationContext(), BookingSchedule.class);
                        intent.putExtra("Id", userid);
                        intent.putExtra("ClientType", usertype);
                        startActivity(intent);
                    }

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
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("id", userid);
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(request);
}

}
