package com.example.appthesis;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class TransactionPage extends AppCompatActivity {
    TextView name, employee, book_date, date_service, status, amount_paid, date_paid;
    String url_show_data = "http://192.168.43.189/android_register_login/showdata.php";
    String userid, emp_fname, emp_lname, bookdate, Status, paid_amount;
    SessionManager sessionManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);

        name = findViewById(R.id.textView35);
        employee = findViewById(R.id.textView39);
        status = findViewById(R.id.textView43);
        amount_paid = findViewById(R.id.textView58);
        date_paid = findViewById(R.id.textView47);
        context = getApplicationContext();

       userid = getIntent().getStringExtra("ID");

        StringRequest request  = new StringRequest(Request.Method.POST, url_show_data, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            emp_fname = object.getString("fname").trim();
                            emp_lname = object.getString("lname").trim();
                            bookdate = object.getString("date_paid").trim();
                            Status = object.getString("status").trim();
                            paid_amount = object.optString("payment_amount").trim();
                            String fullname = "" + emp_fname + " "+emp_lname;
                            employee.setText(fullname);
                            date_paid.setText(bookdate);
                            amount_paid.setText(paid_amount);
                            status.setText(Status);


                            String stat = status.getText().toString();
                            if(stat.equals("pending")){
                                status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
                            }
                            else if(stat.equals("scheduled")){
                                status.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            }
                            else{
                                status.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                            }
                        }
                    }
                    else{
                          employee.setText("No Data");
                          book_date.setText("No Data");
                          status.setText("No Data");
                          amount_paid.setText("No Data");
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
                params.put("Id", userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
