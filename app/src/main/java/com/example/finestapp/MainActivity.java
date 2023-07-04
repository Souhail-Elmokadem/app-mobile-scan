package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button listbutton;
    private Button login;
    private Button register;

//    private SharedPreferences sharedPreferences;
//    private static final String SHARED_PREF_NAME = "your_shared_pref";
//    private static final String KEY_USERNAME = "username";
//    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//        if (isLoggedIn()) {
//            // User is logged in, proceed with the app flow
//            // For example, you can redirect to the product list activity or perform other actions.
//            // You may consider displaying a welcome message based on the retrieved username.
//        } else {
//            // User is not logged in, show the login form or perform any necessary actions for a guest user.
//        }

//        listbutton = findViewById(R.id.listbutton);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


//        listbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ProductList.class);
//                startActivity(intent);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

 /*   private void saveLoginStatus(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    private String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        // Perform any other actions necessary for logging out the user.
    }
*/
}