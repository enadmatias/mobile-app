package com.example.appthesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserOption extends AppCompatActivity {
    RadioGroup radioGroup;
    String choose= "";
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_option);

        radioGroup = findViewById(R.id.radioGroup);
        proceed = findViewById(R.id.btn_allow);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton_household:
                        choose = "household";
                        break;
                    case R.id.radioButton_company:
                        choose = "company";
                        break;
                }
            }
        });

      proceed.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(choose.equals("household")){
                  Intent intent = new Intent(getApplicationContext(), HouseholdRegister.class);
                  startActivity(intent);
              }
              else if(choose.equals("company"))
              {
                  Intent intent = new Intent(getApplicationContext(), CompanyRepRegister.class);
                  startActivity(intent);
              }
              else{
                  Toast.makeText(getApplicationContext(), "Please choose the category", Toast.LENGTH_SHORT).show();
              }

          }
      });
    }
}
