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

public class CompanyRepRegister extends AppCompatActivity {
    EditText Rep_Fname, Rep_Lname, Rep_address, Rep_contact, Rep_Email, Rep_Pass, Rep_Cpass;
    Button register;
    Spinner Reg_gender;
    String gender ="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_rep_register);

        Rep_Fname = findViewById(R.id.editTxt_RepFname);
        Rep_Lname = findViewById(R.id.editTxt_RepLName);
        Rep_address = findViewById(R.id.editTxt_RepAddress);
        Rep_contact = findViewById(R.id.editTxt_RepphoneNo);
        Rep_Email = findViewById(R.id.editTxt_Repemail);
        Rep_Pass = findViewById(R.id.editTxt_Reppassword);
        Rep_Cpass = findViewById(R.id.editTxt_Repcpassword);
        Reg_gender = findViewById(R.id.spinner_gender3);
        register = findViewById(R.id.btn_submit);

        String [] list = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        Reg_gender.setAdapter(adapter);

        Reg_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        gender = "Male";
                        break;
                    case 1:
                        gender = "Female";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mFname = Rep_Fname.getText().toString();
               String mLname = Rep_Lname.getText().toString();
               String mAddress = Rep_address.getText().toString();
               String mContact = Rep_contact.getText().toString();
               String mEmail = Rep_Email.getText().toString();
               String mPass = Rep_Pass.getText().toString();
               String cPass = Rep_Cpass.getText().toString();

                if(checkEmptyFields(mFname, mLname, mAddress, mContact, mEmail) && checkPassword(mPass, cPass)) {
                    Register(mFname, mLname, mAddress, mContact, gender, mEmail, mPass);
                }


            }
        });


    }
    private boolean checkEmptyFields(String Fname, String Lname, String Address, String Contact, String Email){
        if(TextUtils.isEmpty(Fname)){
            Rep_Fname.setError("Please enter your FirstName");
            return false;

        }
        else if(TextUtils.isEmpty(Lname)){
            Rep_Lname.setError("Please enter your LastName");
            return false;
        }
        else if(TextUtils.isEmpty(Address)){
            Rep_address.setError("Please enter your Address");
            return false;
        }
        else if(TextUtils.isEmpty(Contact)){
            Rep_contact.setError("Please enter your LastName");
            return false;
        }
        else if(TextUtils.isEmpty(Email)){
            Rep_Email.setError("Please enter your Email Address");
            return false;

        }

        return true;
    }
    private boolean checkPassword(String Password, String CPassword){
        if(TextUtils.isEmpty(Password) || TextUtils.isEmpty(CPassword)){
            Rep_Pass.setError("Enter your Password");
            return false;
        }
        else if(Password.length() < 6 || Password.length() > 10){
            Rep_Cpass.setError("Password must be more 6  and least 10 characters");
            return false;
        }
        return true;
    }

    public  void Register(String fname, String lname, String gender, String address, String contact, String email, String pass){
        Intent intent = new Intent(getApplicationContext(), CompanyRegister.class);
        intent.putExtra("fname", fname);
        intent.putExtra("lname", lname);
        intent.putExtra("gender", gender);
        intent.putExtra("address", address);
        intent.putExtra("contact", contact);
        intent.putExtra("email", email);
        intent.putExtra("pass", pass);
        startActivity(intent);

    }
}
