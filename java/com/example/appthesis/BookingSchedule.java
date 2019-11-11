package com.example.appthesis;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingSchedule extends AppCompatActivity implements View.OnClickListener {
    EditText datepicker1, datepicker2;
    EditText timepicker1, timepicker2;
    EditText address, mobile;
    TextView tv1, tv2;
    Spinner noofloors, noRooms;
    Button proceed;
    SessionManager sessionManager;
    String id = "", Servicetype ="", client_type, client_type1, infra_type="house", infra_type1 = "building";
    private int mYear, mMonth, mDay, mHour, mMinute;
    String dateFirst = "", dateSecond = "", TimeFirst = "", TimeSecond ="";

    String [] list = {"1", "2", "3", "4", "5"};
    String [] rooms = {"1", "2", "3", "4", "5"};
    String noFloors="", no_Rooms="", mType;

    String url_book_service = "http://192.168.43.189/android_register_login/book.php";
    String url_book_company = "http://192.168.43.189/android_register_login/book_company.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_schedule);

        datepicker1 = findViewById(R.id.editTxt_date_picker_start);
        datepicker2 = findViewById(R.id.editTxt_date_picker_end);
        timepicker1 = findViewById(R.id.editTxt_time_picker_start);
        timepicker2 = findViewById(R.id.editTxt_time_picker_end);
        noofloors = findViewById(R.id.spinner_type);
        noRooms = findViewById(R.id.spinner_noRoom);
        proceed = findViewById(R.id.button_Proceed);
        tv1 = findViewById(R.id.textView19);
        tv2 = findViewById(R.id.textView20);
        address = findViewById(R.id.editText2);
        mobile = findViewById(R.id.editText5);

        datepicker1.setOnClickListener(this);
        datepicker2.setOnClickListener(this);
        timepicker1.setOnClickListener(this);
        timepicker2.setOnClickListener(this);
        proceed.setOnClickListener(this);

        id = getIntent().getStringExtra("Id");
        Servicetype = getIntent().getStringExtra("type");
        client_type = getIntent().getStringExtra("ClientType");




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        noofloors.setAdapter(adapter);

        noofloors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noFloors = noofloors.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rooms);
     noRooms.setAdapter(madapter);

        noRooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             no_Rooms = noRooms.getItemAtPosition(position).toString();
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.editTxt_date_picker_start:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datepicker1.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
               break;
            case R.id.editTxt_date_picker_end:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datepicker2.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog1.show();
                break;

        case R.id.editTxt_time_picker_start:
            final Calendar c2 = Calendar.getInstance();
            mHour = c2.get(Calendar.HOUR_OF_DAY);
            mMinute = c2.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timepicker1.setText(hourOfDay + ":" + minute);
                }
            },mHour, mMinute, false);

            timePickerDialog.show();
            break;
            case R.id.editTxt_time_picker_end:
                final Calendar c3 = Calendar.getInstance();
                mHour = c3.get(Calendar.HOUR_OF_DAY);
                mMinute = c3.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timepicker2.setText(hourOfDay + ":" + minute);
                    }
                },mHour, mMinute, false);

                timePickerDialog1.show();
                break;

            case R.id.button_Proceed:
                dateFirst = datepicker1.getText().toString().trim();
                dateSecond = datepicker2.getText().toString().trim();
                TimeFirst = timepicker1.getText().toString().trim();
                TimeSecond = timepicker2.getText().toString().trim();
                 String mAddress = address.getText().toString();
                 String mNumber = mobile.getText().toString();
                 if(checkEmptyField(dateFirst, dateSecond, TimeFirst, TimeSecond, mAddress, mNumber)){
                   BookDetails(id, dateFirst, dateSecond, TimeFirst, TimeSecond, noFloors, mAddress, mNumber);
                 }
                break;
        }

    }

    public void BookDetails(String book_id, final String book_date_start, final String book_date_end, final String time_first,
                            final String time_second, final String Infratype, final String Address, final String Contact) {

        StringRequest request = new StringRequest(Request.Method.POST, url_book_service, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    if (message.equals("success")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String book_id = object.getString("book_id").trim();
                            Intent intent = new Intent(BookingSchedule.this, PaypalPage.class);
                            intent.putExtra("Book_ID", book_id);
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    Intent intent = new Intent(BookingSchedule.this, BookingSchedule.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (client_type.equals("household")) {
                    params.put("Id", id);
                    params.put("type", infra_type);
                    params.put("date_start", book_date_start);
                    params.put("date_end", book_date_end);
                    params.put("time_start", time_first);
                    params.put("time_end", time_second);
                    params.put("service", Servicetype);
                    params.put("floors", noFloors);
                    params.put("rooms", no_Rooms);
                    params.put("address", Address);
                    params.put("number", Contact);
                }
                else {
                  if(checkEmptyField(book_date_start, book_date_end, time_first, time_second, Address, Contact)){
                    Update(id, infra_type1, book_date_start, book_date_end, time_first, time_second, Address, Contact);
                  }

                }

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    private void Update(final String id, String type, final String datefirst, final String datesecond, final String timefirst, final String timesecond, final String address, final String mobile_num) {


        StringRequest request = new StringRequest(Request.Method.POST, url_book_company, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("response");

                 if (message.equals("success")) {
                    Intent intent = new Intent(BookingSchedule.this, LandingPage.class);
                    BookingSchedule.this.finish();
                    startActivity(intent);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                    params.put("Id", id);
                    params.put("type", infra_type1);
                    params.put("date_start", datefirst);
                    params.put("date_end", datesecond);
                    params.put("time_start", timefirst);
                    params.put("time_end", timesecond);
                    params.put("address", address);
                    params.put("mobile", mobile_num);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public boolean checkEmptyField(String dateFirst, String dateSecond, String TimeFirst, String TimeSecond,  String mAddress, String mNumber){


        if(TextUtils.isEmpty(mAddress)){
            address.setError("Empty fields");
            return false;
        }
        else   if(TextUtils.isEmpty(mNumber)){
            mobile.setError("Empty fields");
            return false;
        }
        else if(TextUtils.isEmpty(dateFirst)){
            datepicker1.setError("Empty fields");
            return false;
        }
        else if(TextUtils.isEmpty(dateSecond)){
            datepicker2.setError("Empty fields");
            return false;
        }
        else if(TextUtils.isEmpty(TimeFirst)){
            timepicker1.setError("Empty fields");
            return false;
        }
        else  if(TextUtils.isEmpty(TimeSecond)){
            timepicker2.setError("Empty fields");
            return false;
        }
        return true;
    }

   public boolean checkUserType(String type){
    if(type.equals("company")){
        return true;
    }
    else if (type.equals("household")){
        return true;
    }
   return false;
   }
}
