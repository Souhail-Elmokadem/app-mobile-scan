package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    private static final String KEY_USER_ID = "userId";
    public static SharedPreferences sharedPreferences;
    EditText Username, Password;
    Button Login;
    ProgressBar progressBar;
    CheckBox CheckBox;
    private Button leavebtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.loginbtn);
        CheckBox = findViewById(R.id.checkBox);
        progressBar = findViewById(R.id.progress);
        sharedPreferences = getPreferences(MODE_PRIVATE);



        int state = sharedPreferences.getInt("state",-1);
        if(state!=-1)
        {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
            Toast.makeText(Login.this, "Login Successful !", Toast.LENGTH_SHORT).show();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CheckBox.isChecked()==true)
                {
                    sharedPreferences.edit().putInt("state",1).apply();

                }


                String username, password;

                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());


                if(!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "EmailUser";
                            field[1] = "PasswordUser";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            //The IP-Adress means that devices needs to be connected to the same WIFI network
                            PutData putData = new PutData("http://192.168.11.63/Loginregister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Login Success")){
                                        Intent intent = new Intent(getApplicationContext(), ProductList.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(Login.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Login.this, "Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


        leavebtn = findViewById(R.id.leavebtn);

        leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
