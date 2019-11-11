package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompanyRegister extends AppCompatActivity {
    EditText company, company_address, company_contact, company_email;
    Button  submit;

    String url_register_company = "http://192.168.43.189/android_register_login/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        final String fname = getIntent().getStringExtra("fname");
        final String lname = getIntent().getStringExtra("lname");
        final String gender = getIntent().getStringExtra("gender");
        final String address = getIntent().getStringExtra("address");
        final String contact = getIntent().getStringExtra("contact");
        final String email = getIntent().getStringExtra("email");
        final String pass = getIntent().getStringExtra("pass");
        final String usertype = "company";

        company = findViewById(R.id.edittxt_company_name);
        company_address = findViewById(R.id.edittxt_company_address);
        company_contact = findViewById(R.id.editTxt_phone_no);
        company_email = findViewById(R.id.editTxt_email_address);
        submit = findViewById(R.id.button14);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mCompany = company.getText().toString();
                final String mCompanyAddress = company_address.getText().toString();
                final String mCompanyContact = company_contact.getText().toString();
                final String mCompanyEmail = company_email.getText().toString();
              if(checkEmptyFields(mCompany, mCompanyAddress, mCompanyContact, mCompanyEmail)){
                  StringRequest request = new StringRequest(Request.Method.POST, url_register_company, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                              if(response.trim().equals("success")) {
                                  Intent intent = new Intent(CompanyRegister.this, MainActivity.class);
                                  startActivity(intent);
                                  Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                              }
                              else
                                  Toast.makeText(getApplicationContext(), "Unsuccessful!", Toast.LENGTH_SHORT).show();

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
                          params.put("fname", fname);
                          params.put("lname", lname);
                          params.put("gender", gender);
                          params.put("address", address);
                          params.put("contact", contact);
                          params.put("pass", pass);
                          params.put("email", email);
                          params.put("Usertype", usertype);
                          params.put("companyname", mCompany);
                          params.put("companyaddress", mCompanyAddress);
                          params.put("companycontact", mCompanyContact);
                          params.put("companyemail", mCompanyEmail);
                          return params;
                      }
                  };
                  RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                  requestQueue.add(request);
              }
            }
        });

    }

    private boolean checkEmptyFields(String mCompany, String mCompanyAddress, String mCompanyContact,String mCompanyEmail){
        if(TextUtils.isEmpty(mCompany)){
            company.setError("Please enter Field");
            return false;

        }
        else if(TextUtils.isEmpty(mCompanyAddress)){
            company_address.setError("Please enter Field");
            return false;
        }
        else if(TextUtils.isEmpty(mCompanyContact)){
            company_contact.setError("Please enter Field");
            return false;
        }
        else if(TextUtils.isEmpty(mCompanyEmail)){
            company_email.setError("Please enter Field");
            return false;
        }

        return true;
    }
}
