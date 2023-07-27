package com.example.finestapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.user.Login;

public class MainActivity extends AppCompatActivity {
    private static final long CHECK_INTERVAL = 5000; // Check every 5 seconds
    private Handler handler = new Handler();
    private InternetConnectivityChecker connectivityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        connectivityChecker = new InternetConnectivityChecker(this);


        getSupportActionBar().hide();

    }


    // chackInternet


    private Runnable connectivityRunnable = new Runnable() {
        @Override
        public void run() {
            checkInternetConnection();
            handler.postDelayed(this, CHECK_INTERVAL);
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        handler.post(connectivityRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(connectivityRunnable);
    }

    public void checkInternetConnection() {
        if (connectivityChecker.isInternetAvailable()) {


                Handler handler =  new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(MainActivity.this,Login.class);
                        MainActivity.this.startActivity(mainIntent);
                        MainActivity.this.finish();
                    }
                }, 3000);

        } else {
            startActivity(new Intent(MainActivity.this, NoInternetConnection.class));
            finish();
            showToast("No internet connection!");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

