package com.example.appthesis;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SchedulingPage extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> list =new ArrayList<>();
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText firstdate, seconddate, first_time_range, second_time_range, amount;
    String url_update_book = "http://192.168.43.189/android_register_login/update_book.php";
    Button book;
    String book_id, amount_payed, first_date, second_date, first_time, second_time;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalConfig.PAYPAL_CLIENT_ID);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_page);

        book_id = getIntent().getStringExtra("book_id");

        firstdate = findViewById(R.id.editText3);
        seconddate = findViewById(R.id.editText8);
        first_time_range = findViewById(R.id.editText22);
        second_time_range = findViewById(R.id.editText23);
        book = findViewById(R.id.button18);
        amount = findViewById(R.id.editText24);

        firstdate.setOnClickListener(this);
        seconddate.setOnClickListener(this);
        first_time_range.setOnClickListener(this);
        second_time_range.setOnClickListener(this);
        book.setOnClickListener(this);

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.editText3:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        firstdate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.editText8:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        seconddate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog1.show();
                break;
            case R.id.editText22:
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        first_time_range.setText(hourOfDay + ":" + minute);
                    }
                },mHour, mMinute, false);

                timePickerDialog.show();
                break;
            case R.id.editText23:
                final Calendar c3 = Calendar.getInstance();
                mHour = c3.get(Calendar.HOUR_OF_DAY);
                mMinute = c3.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        second_time_range.setText(hourOfDay + ":" + minute);
                    }
                },mHour, mMinute, false);

                timePickerDialog1.show();
                break;

            case R.id.button18:
                amount_payed = amount.getText().toString();
                first_date = firstdate.getText().toString();
                second_date = seconddate.getText().toString();
                first_time = first_time_range.getText().toString();
                second_time = second_time_range.getText().toString();
                getPayment();
                break;
        }

    }


    private void getPayment() {


        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount_payed)), "USD", "Service Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        JSONObject jsonDetails = new JSONObject(paymentDetails);
                        addPaymentDetails(jsonDetails.getJSONObject("response"));

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        //  startActivity(new Intent(this, PaymentDetails.class)
                        //        .putExtra("PaymentDetails", paymentDetails)
                        //      .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
    public void addPaymentDetails(JSONObject jsonDetails) throws JSONException{
        String id = jsonDetails.getString("id");
        final String status = jsonDetails.getString("state");

        StringRequest request  = new StringRequest(Request.Method.POST, url_update_book, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("response");
                    if(message.equals("success")){
                        Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Intent intent = new Intent(getApplicationContext(), SchedulingPage.class);
                    startActivity(intent);
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
                params.put("book_id", book_id);
                params.put("firstdate", first_date);
                params.put("second_date", second_date);
                params.put("first_time", first_time);
                params.put("second_time", second_time);
                params.put("amount_payed", amount_payed);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}
