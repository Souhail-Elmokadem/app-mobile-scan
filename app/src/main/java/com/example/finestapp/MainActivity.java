package com.example.finestapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.user.Login;

public class MainActivity extends AppCompatActivity {

//    private SharedPreferences sharedPreferences;
//    private static final String SHARED_PREF_NAME = "your_shared_pref";
//    private static final String KEY_USERNAME = "username";
//    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        NextActivity();
    }
    public void NextActivity()
    {
        Handler handler =  new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,Login.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 3000);
    }
}