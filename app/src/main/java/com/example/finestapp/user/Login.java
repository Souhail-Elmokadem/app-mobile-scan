package com.example.finestapp.user;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.Dashboard;
import com.example.finestapp.R;
import com.example.finestapp.Server;
import com.example.finestapp.SessionActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    EditText Username, Password;
    Button Login;
    //    ProgressBar progressBar;
    CheckBox CheckBox;
    private Button leavebtn;
    public static boolean checkboxStatus=false;

    @Override
    protected void onStart() {
        super.onStart();
            checkSession();
    }

    private void checkSession() {
        SessionActivity sessionActivity = new SessionActivity(Login.this);
        String userEmail = sessionActivity.getSession();
        if (userEmail!="null"){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
            Toast.makeText(Login.this, "Login Successful !", Toast.LENGTH_SHORT).show();
        }else {
                // do somethings
        }
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.loginbtn);
        CheckBox = findViewById(R.id.checkBox);
        SessionActivity sessionActivity = new SessionActivity(com.example.finestapp.user.Login.this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CheckBox.isChecked()==true) {

                    sessionActivity.setIscheckBox(true);

                }
                String username, password;

                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());


                if(!username.equals("") && !password.equals("")) {
//                    progressBar.setVisibility(View.VISIBLE);
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
//
                            //The IP-Adress means that devices needs to be connected to the same WIFI network
                            PutData putData = new PutData(Server.Url +"/Loginregister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Login Success")){
                                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                        sessionActivity.saveSession(username,password);
                                       // Toast.makeText(Login.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                                        //session start
                                        getUser(username);
                                            //sessionActivity.saveSession(username,password);


                                        //moveToDashboard();
                                        //session end
                                       // Toast.makeText(getApplicationContext(),n,Toast.LENGTH_SHORT).show();


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

    private  void getUser(String Email){
        List<User> resultList = new ArrayList<>();
        //SessionActivity sessionActivity = new SessionActivity(getApplicationContext());

        try {
            URL url = new URL(Server.Url+"/Loginregister/ListUser.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            //Log.d(TAG, "Server Response: " + response.toString()); // Add this line to log the response

            bufferedReader.close();
            inputStream.close();
            connection.disconnect();

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("EmailUser").equals(Email)){
                    String idUser = jsonObject.getString("idUser");
                    String nomUser = jsonObject.getString("NomUser");
                    String prenomUser = jsonObject.getString("PrenomUser");
                    String email = jsonObject.getString("EmailUser");
                    String telephone =jsonObject.getString("telUser");
                    User users = new User(idUser,nomUser, prenomUser,email,telephone);
                    resultList.add(users);
                }




            }

        } catch (IOException | JSONException e) {
            Log.e("user_login", "Error retrieving data: " + resultList);
        }
        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public boolean IsLoggedIn() {
//        // Verify that the default value of isLoggedIn is false
//        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//        return isLoggedIn;
//    }

}
