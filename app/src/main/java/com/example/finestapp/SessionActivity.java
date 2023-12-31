package com.example.finestapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String shared_pref_name = "session";

    private boolean ischeckBox=false;
    private String Email;
    private String nom;
    private String idUser;
    private  String idrole;


    public SessionActivity(Context context){
        this.sharedPreferences=context.getSharedPreferences(shared_pref_name,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(String Email,String password){
        editor.putString("session_email",Email);
        editor.putString("session_password",password);
        editor.commit();
    }

    public void saveSessionDetail(String idUser,String Nom,String idrole){
        editor.putString("session_id",idUser);
        editor.putString("session_nom",Nom);
        editor.putString("session_role",idrole);
        editor.commit();
    }
//    public String getSession(){
//        return sharedPreferences.getString("session_email","null");
//    }
    public boolean isIscheckBox() {
        return ischeckBox;
    }

    public void setIscheckBox(boolean check) {

        ischeckBox = check;
    }

    public String getEmailSession() {
        return sharedPreferences.getString("session_email","null");
    }
    public void setEmailSession(String email){
        editor.putString("session_email",email);
        editor.commit();
    }
    public String getPassword(){
        return  sharedPreferences.getString("session_email","null");
    }
    public void setPassword(String pass){
        editor.putString("session_password",pass);
        editor.commit();
    }

    public String getIdrole() {
         return sharedPreferences.getString("session_role","null");
    }



    public String getIdUser() {
        return sharedPreferences.getString("session_id","null");
    }

    public String getNom(){
        return  sharedPreferences.getString("session_nom","null");
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void removeSession(){

        editor.putString("session_email","null").commit();
        editor.clear();
        editor.remove("session_email");
        editor.remove("session_password");
        editor.remove("session_role");
        editor.commit();
        editor.apply();
    }

}
