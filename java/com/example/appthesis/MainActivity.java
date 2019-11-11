package com.example.appthesis;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText email, password;
    Button login;
    DrawerLayout drawer;
    ProgressBar loading;
    String url_login = "http://192.168.43.189/android_register_login/login.php";
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        loading = findViewById(R.id.loading);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sessionManager = new SessionManager(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if(!mEmail.isEmpty()|| !mPass.isEmpty()){
                   Login(mEmail, mPass);
                }
                else
                 {
                 email.setError("Please enter Email");
                 password.setError("Please enter Password");
                }
            }
        });

    }

    public void Login(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        StringRequest request  = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


              try{
                  JSONObject jsonObject = new JSONObject(response);
                  String message = jsonObject.getString("success");
                  JSONArray jsonArray = jsonObject.getJSONArray("login");
                  if(message.equals("success")){
                      loading.setVisibility(View.GONE);
                      for(int i=0; i<jsonArray.length(); i++){
                         JSONObject object = jsonArray.getJSONObject(i);
                         String id = object.getString("id").trim();
                          String name = object.getString("name").trim();
                          String lname = object.getString("lname").trim();
                          String type = object.getString("type").trim();
                          Intent intent = new Intent(MainActivity.this, LandingPage.class);
                                  intent.putExtra("id", id);
                                  sessionManager.createSession(id,name,lname,type, null);
                                 startActivity(intent);
                      }
                  }


              } catch (JSONException e) {
                  loading.setVisibility(View.GONE);
                  Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
              }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_sign_up:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Register()).commit();
               Intent reg = new Intent(MainActivity.this, UserOption.class);
                startActivity(reg);
                break;
            case R.id.nav_about_us:
             //   Intent team = new Intent(MainActivity.this, about_us.class);
             //   break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
