package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.regex.Pattern;

public class HouseholdRegister extends AppCompatActivity {

    EditText fname, lname,address,phoneno,emailadd,pass,cpass;
    Button submit;
    Spinner gender;
    String Gender = "";
    String url_login = "http://192.168.43.189/android_register_login/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_register);

        gender = findViewById(R.id.spinner_gender2);
        fname = findViewById(R.id.editTxt_fname);
        lname = findViewById(R.id.editTxt_LName);
        address = findViewById(R.id.editTxt_Address);
        emailadd = findViewById(R.id.editTxt_email);
        phoneno = findViewById(R.id.editTxt_phoneNo);
        pass = findViewById(R.id.editTxt_password);
        cpass = findViewById(R.id.editTxt_cpassword);
        submit = findViewById(R.id.btn_submit);

        String [] list = {"Male", "Female"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        gender.setAdapter(adapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Gender = "Male";
                        break;
                    case 1:
                        Gender = "Female";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mFname = fname.getText().toString().trim();
               String mLname  = lname.getText().toString().trim();
               String mPhone = phoneno.getText().toString().trim();
               String mAddress = address.getText().toString().trim();
               String mEmail = emailadd.getText().toString().trim();
               String mPass = pass.getText().toString().trim();
               String mCpass = cpass.getText().toString().trim();
                if(checkEmptyFields(mFname, mLname, mAddress, mPhone, mEmail) && checkPassword(mPass, mCpass)) {
                    Register(mFname, mLname, mAddress, mPhone, Gender, mEmail, mPass);

                }
            }
        });
    }
    private boolean checkEmptyFields(String Fname, String Lname, String Address, String Contact, String Email){
        if(TextUtils.isEmpty(Fname)){
            fname.setError("Please enter your FirstName");
            return false;

        }
        else if(TextUtils.isEmpty(Lname)){
            lname.setError("Please enter your LastName");
            return false;
        }
        else if(TextUtils.isEmpty(Address)){
            address.setError("Please enter your Address");
            return false;
        }
        else if(TextUtils.isEmpty(Contact)){
            phoneno.setError("Please enter your LastName");
            return false;
        }
        else if(TextUtils.isEmpty(Email)){
            emailadd.setError("Please enter your Email Address");
            return false;

        }

        return true;
    }
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private boolean checkPassword(String Password, String CPassword){
        if(TextUtils.isEmpty(Password) || TextUtils.isEmpty(CPassword)){
            pass.setError("Enter your Password");
            return false;
        }
        else if(Password.length() < 6 || Password.length() > 10){
            pass.setError("Password must be more 6  and least 10 characters");
            return false;
        }
        return true;
    }

    public void Register(final  String Fname, final String Lname, final String Address, final String Phone, final String Identity, final String Email, final String Password){
        final String usertype = "household";
        StringRequest request  = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if (response.trim().equals("success")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
                params.put("Fname", Fname);
                params.put("Lname", Lname);
                params.put("Address", Address);
                params.put("Phone", Phone);
                params.put("Identity", Identity);
                params.put("Email", Email);
                params.put("Password", Password);
                params.put("Usertype", usertype);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
