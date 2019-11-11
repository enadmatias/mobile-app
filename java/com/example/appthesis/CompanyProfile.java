package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class CompanyProfile extends AppCompatActivity {
    TextView companyname;
    EditText address, contactNo;
    Button update, cancel;
    String mid, maddress, mcontact, mPass, comname, companyaddress, companycontact, mtype;
    String url_edit_detail= "http://192.168.43.189/android_register_login/edit_detail.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        companyname = findViewById(R.id.textView9);
        address = findViewById(R.id.editText19);
        contactNo = findViewById(R.id.editText20);
        update = findViewById(R.id.btn_save_update);
        cancel = findViewById(R.id.btn_cancel_comprofile);

        sessionManager = new SessionManager(this);

         mid = getIntent().getStringExtra("id");
         maddress = getIntent().getStringExtra("Address");
         mcontact = getIntent().getStringExtra("Contact");
         mPass = getIntent().getStringExtra("Password");
         comname = getIntent().getStringExtra("ComName");
         companyaddress = getIntent().getStringExtra("ComAddress");
         companycontact = getIntent().getStringExtra("ComContact");

         companyname.setText(comname);
         address.setText(companyaddress);
         contactNo.setText(companycontact);

        HashMap<String, String> user = sessionManager.getUserDetail();
        mtype = user.get(sessionManager.TYPE);

         update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String temp_address = address.getText().toString();
                 String temp_contact = contactNo.getText().toString();
                 Update(mid,maddress,mcontact,mPass,temp_address,temp_contact);
             }
         });

         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), CompanyRepProfile.class);
                 startActivity(intent);
                 CompanyProfile.this.finish();
             }
         });
    }

    public void Update(final String Id,final String Address, final String ContactNo, final String Password, final String CompanyAdd, final String CompanyNum){
        StringRequest request  = new StringRequest(Request.Method.POST, url_edit_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if(message.equals("success")) {
                        Intent intent = new Intent(CompanyProfile.this, LandingPage.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Failed to Update ", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", Id);
                params.put("Address", Address);
                params.put("ContactNo", ContactNo);
                params.put("Password", Password);
                params.put("CompanyAdd", CompanyAdd);
                params.put("CompanyNum", CompanyNum);
                params.put("Usertype", mtype);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
