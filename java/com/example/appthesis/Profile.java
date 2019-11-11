package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Profile extends AppCompatActivity {
    EditText FirstName, LastName, UserAddress, UserPhone, UserPass, UserCpass;
    Button submit, cancel;
    String Uid, mtype;
    String temp_fname, temp_lname, temp_address, temp_contact;
    String url_edit_detail= "http://192.168.43.189/android_register_login/edit_detail.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirstName = findViewById(R.id.editText9);
        LastName = findViewById(R.id.editText10);
        UserAddress = findViewById(R.id.editText11);
        UserPhone = findViewById(R.id.editText12);
        UserPass = findViewById(R.id.editText13);
        UserCpass = findViewById(R.id.editText14);
        submit = findViewById(R.id.btn_save_profile);
        cancel = findViewById(R.id.btn_cancel_profile);
        sessionManager = new SessionManager(this);

        Uid = getIntent().getStringExtra("id");
        temp_fname = getIntent().getStringExtra("name");
        temp_lname = getIntent().getStringExtra("lname");
        temp_address = getIntent().getStringExtra("address");
        temp_contact = getIntent().getStringExtra("phone");

        FirstName.setText(temp_fname);
        LastName.setText(temp_lname);
        UserAddress.setText(temp_address);
        UserPhone.setText(temp_contact);

        HashMap<String, String> user = sessionManager.getUserDetail();
        mtype = user.get(sessionManager.TYPE);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAddress = UserAddress.getText().toString().trim();
                String mContact = UserPhone.getText().toString().trim();
                String mPass = UserPass.getText().toString().trim();
                String mCpass = UserCpass.getText().toString().trim();

             if(!mAddress.isEmpty() || !mContact.isEmpty() && !mPass.isEmpty() || !mCpass.isEmpty()){
                    if(mPass.equals(mCpass)){
                     Update(Uid, mAddress, mContact, mPass);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
             else {
                 Toast.makeText(getApplicationContext(), "Field is Empty", Toast.LENGTH_SHORT).show();
             }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
                Profile.this.finish();
            }
        });

    }

    public void Update(final String Id,final String Postal, final String Number, final String Password){
        StringRequest request  = new StringRequest(Request.Method.POST, url_edit_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                JSONObject jsonObject = new JSONObject(response);
                String message = jsonObject.getString("success");
                    if(message.equals("success")) {
                        Intent intent = new Intent(Profile.this, LandingPage.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Failed to Update  Id ", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", Id);
                params.put("Postal", Postal);
                params.put("Number", Number);
                params.put("Password", Password);
                params.put("Usertype", mtype);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
