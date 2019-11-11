package com.example.appthesis;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LandingPage extends AppCompatActivity  implements ModalDialog.BottomSheetListener{
    TextView message;
    ImageView bottunopendialog;
    Button inquire, book, services, tips, transactions, ratesandreviews;
    String id, miD, mName, mLname, mType;
    String url_read_detail = "http://192.168.43.189/android_register_login/read_detail.php";
    String url_show_notification = "http://192.168.43.189/android_register_login/notification.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        message = findViewById(R.id.textView2_message);
        book = findViewById(R.id.button5);
        inquire = findViewById(R.id.button4);
        services = findViewById(R.id.button3);
        tips = findViewById(R.id.button8);
        transactions = findViewById(R.id.button6);
        ratesandreviews = findViewById(R.id.button7);
        bottunopendialog = findViewById(R.id.imageView2);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        miD = user.get(sessionManager.ID);
        mName = user.get(sessionManager.NAME);
        mLname = user.get(sessionManager.LNAME);
        mType = user.get(sessionManager.TYPE);


        StringRequest request  = new StringRequest(Request.Method.POST, url_show_notification, new Response.Listener<String>() {

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
                            String status = object.getString("status").trim();
                            if(status.equals("scheduled")){
                                int notificationId = 1;
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"M_CH_ID");
                                builder.setSmallIcon(R.drawable.wd_logo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.wd_logo))
                                        .setContentTitle("WireDoctor")
                                        .setContentText("Your booking were scheduled already.")
                                        .setAutoCancel(true)
                                        .setDefaults(NotificationCompat.DEFAULT_ALL);
                                Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                builder.setSound(path);

                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                    String channelId= "YOUR CHANNEL_ID";
                                    NotificationChannel  channel = new NotificationChannel(channelId, "Channel something", NotificationManager.IMPORTANCE_DEFAULT);
                                    notificationManager.createNotificationChannel(channel);
                                    builder.setChannelId(channelId);
                                }
                                notificationManager.notify(notificationId, builder.build());
                            }


                        }
                    }
                    else if(message.equals("failed")){

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
                params.put("Id", miD);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

        id = getIntent().getStringExtra("id");
        message.setText("Welcome " +mName+ "!");



        bottunopendialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalDialog modalDialog = new ModalDialog();
                modalDialog.show(getSupportFragmentManager(), "modalDialog");
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FreelanceProfile.class);
                startActivity(intent);

            }
        });
        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatMessage.class);
                intent.putExtra("Id", miD);
                startActivity(intent);
            }
        });
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionPage.class);
                intent.putExtra("ID", miD);
                startActivity(intent);
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ServiceTypeDesign.class);
                startActivity(intent);
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TipsList.class);
                startActivity(intent);
            }
        });

        ratesandreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RatesandReviews.class);
                intent.putExtra("ID", miD);
                intent.putExtra("Fname", mName);
                intent.putExtra("Lname", mLname);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onButtonClicked(String text) {

     if(text.equals("Edit")){
         StringRequest request  = new StringRequest(Request.Method.POST, url_read_detail, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
              try
              {
                  JSONObject jsonObject = new JSONObject(response);
                  String message = jsonObject.getString("response");
                  JSONArray jsonArray = jsonObject.getJSONArray("read");
                  if(message.equals("household")){
                      for(int i=0; i<jsonArray.length(); i++){
                          JSONObject object = jsonArray.getJSONObject(i);
                          String Id = object.getString("id").trim();
                          String name = object.getString("name").trim();
                          String lname = object.getString("lname").trim();
                          String address = object.getString("address").trim();
                          String phone = object.getString("phone").trim();
                              Intent intent = new Intent(getApplicationContext(), Profile.class);
                              intent.putExtra("id", Id);
                              intent.putExtra("name", name);
                              intent.putExtra("lname", lname);
                              intent.putExtra("address", address);
                              intent.putExtra("phone", phone);
                              startActivity(intent);

                      }
                  }
                  else if(message.equals("company")){
                      for(int i=0; i<jsonArray.length(); i++){
                          JSONObject object = jsonArray.getJSONObject(i);
                          String Id = object.getString("id").trim();
                          String name = object.getString("name").trim();
                          String lname = object.getString("lname").trim();
                          String address = object.getString("address").trim();
                          String phone = object.getString("phone").trim();
                         String firm_name = object.getString("firmname").trim();
                         String firm_address = object.getString("firmaddress").trim();
                         String firm_contact = object.getString("firmcontact").trim();
                          Intent intent = new Intent(getApplicationContext(), CompanyRepProfile.class);
                          intent.putExtra("id", Id);
                          intent.putExtra("name", name);
                          intent.putExtra("lname", lname);
                          intent.putExtra("address", address);
                          intent.putExtra("phone", phone);
                          intent.putExtra("firmname", firm_name);
                          intent.putExtra("firmaddress", firm_address);
                          intent.putExtra("firmcontact", firm_contact);

                          startActivity(intent);

                      }
                  }


              } catch (JSONException e) {
                  e.printStackTrace();
                  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
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
                 params.put("id", miD);
                 params.put("type", mType);
                 return params;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(request);

        }
     else
         if(text.equals("Contact")){
             Intent intent = new Intent(getApplicationContext(),ContactDetails.class);
             this.startActivity(intent);
         }
      else if(text.equals("Logout")){
          sessionManager.logOut();
         }
    }

    @Override
    public void onBackPressed() {
       showAlertDialog();
    }

    public void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LandingPage.this.finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
