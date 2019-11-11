package com.example.appthesis;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionID {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;

    private  final String PREF_NAME = "LOGIN";
    private  final String LOGIN = "IS_LOGIN";
    public  final String  USER_ID = "USER_ID";

    public SessionID(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }
    public void createSession(String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(USER_ID, id);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);

    }
    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        return user;
    }
}
