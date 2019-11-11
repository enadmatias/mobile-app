package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompanyRepProfile extends AppCompatActivity {
    TextView  fname, lname;
    EditText  address, contact, pass, cpass;
    Button next, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_rep_profile);

        fname = findViewById(R.id.textView7);
        lname = findViewById(R.id.textView41);
        address  = findViewById(R.id.editText15);
        contact = findViewById(R.id.editText16);
        pass = findViewById(R.id.editText17);
        cpass = findViewById(R.id.editText18);
        next = findViewById(R.id.btn_next_update);
        cancel = findViewById(R.id.btn_cancel_repprofile);

        final String temp_id = getIntent().getStringExtra("id");
        String temp_fname = getIntent().getStringExtra("name");
        String temp_lname = getIntent().getStringExtra("lname");
        String temp_address = getIntent().getStringExtra("address");
        String temp_contact = getIntent().getStringExtra("phone");
        final String temp_companyname= getIntent().getStringExtra("firmname");
        final String temp_companyaddress = getIntent().getStringExtra("firmaddress");
        final String temp_companycontact = getIntent().getStringExtra("firmcontact");

        fname.setText(temp_fname);
        lname.setText(temp_lname);
        address.setText(temp_address);
        contact.setText(temp_contact);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mfname = fname.getText().toString();
                String mlname = lname.getText().toString();
                String maddress = address.getText().toString();
                String mcontact = contact.getText().toString();
                String mpass = pass.getText().toString();
                String mcpass = cpass.getText().toString();
             Intent i = new Intent(getApplicationContext(), CompanyProfile.class);
             i.putExtra("id", temp_id);
             i.putExtra("Address", maddress);
             i.putExtra("Contact", mcontact);
             i.putExtra("Password", mpass);
             i.putExtra("ComName", temp_companyname);
             i.putExtra("ComAddress", temp_companyaddress);
             i.putExtra("ComContact", temp_companycontact);
             startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
                CompanyRepProfile.this.finish();
            }
        });
    }
}
