package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {

    EditText Prenom, Nom, Username, Password, Telephone;
    Button Register;
    ProgressBar progressBar;

    private Button leavebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Prenom = findViewById(R.id.prenom);
        Nom = findViewById(R.id.nom);
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Telephone = findViewById(R.id.telephone);

        Register = findViewById(R.id.registerbtn);

        progressBar = findViewById(R.id.progress);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String prenom, nom, username, password, telephone;
                prenom = String.valueOf(Prenom.getText());
                nom = String.valueOf(Nom.getText());
                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());
                telephone = String.valueOf(Telephone.getText());

                if(!prenom.equals("") && !nom.equals("") && !username.equals("") && !password.equals("") && !telephone.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "NomUser";
                            field[1] = "PrenomUser";
                            field[2] = "EmailUser";
                            field[3] = "PasswordUser";
                            field[4] = "TelUser";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = prenom;
                            data[1] = nom;
                            data[2] = username;
                            data[3] = password;
                            data[4] = telephone;

                            //The IP-Adress means that devices needs to be connected to the same WIFI network
                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Sign Up Success")){
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(Register.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Register.this, "Error Creating Account !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
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