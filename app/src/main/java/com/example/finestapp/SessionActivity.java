package com.example.finestapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String shared_pref_name = "session";

    private boolean isLoggedIn;
    private String Email;
    private String idUser;


    public SessionActivity(Context context){
        this.sharedPreferences=context.getSharedPreferences(shared_pref_name,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(String Email,String password){
        editor.putString("session_email",Email);
        editor.putString("session_password",password);
        editor.commit();
    }
    public String getSession(){
        return sharedPreferences.getString("session_email","null");
    }
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getEmail() {
        return Email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void removeSession(){

        editor.putString("session_email","null").commit();
        editor.clear();
        editor.remove("session_email");
        editor.commit();
    }
}
