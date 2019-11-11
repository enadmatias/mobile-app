package com.example.appthesis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;

    private  final String PREF_NAME = "LOGIN";
    private  final String LOGIN = "IS_LOGIN";
    public  final String  ID = "ID";
    public  final String EMAIL = "EMAIL";
    public  final String NAME = "NAME";
    public   final String LNAME = "LNAME";
    public  final String TYPE = "TYPE";
    public final String SERVICE_TYPE = "SERVICETYPE";



    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }



    public void createSession(String id, String name, String lname, String type, String service){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NAME, name);
        editor.putString(LNAME, lname);
        editor.putString(TYPE, type);
        editor.putString(SERVICE_TYPE, service);
        editor.apply();

    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);

    }

    public void checkLogin(){
        if(!this.isLogin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((LandingPage)context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(LNAME, sharedPreferences.getString(LNAME, null));
        user.put(TYPE, sharedPreferences.getString(TYPE, null));
        user.put(SERVICE_TYPE, sharedPreferences.getString(SERVICE_TYPE, null));
        return user;
    }

    public void logOut(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((LandingPage)context).finish();
    }

    public void exit(){

    }


}
