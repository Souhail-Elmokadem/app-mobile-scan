package com.example.finestapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.user.Adduser;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Setting extends AppCompatActivity {

    EditText editOld,editnew,editconfnew;
    Button savebtn,logoutbtn;
    TextView MainActivityPasswordError,MainActivityConfirmPassError;
    boolean PasswordVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editOld = findViewById(R.id.EditOldPassword);
        editnew = findViewById(R.id.EditNewPassword);
        editconfnew = findViewById(R.id.EditConfirmNewPassword);
        savebtn = findViewById(R.id.savebtn);
        logoutbtn=findViewById(R.id.logoutbtn);

        MainActivityPasswordError=findViewById(R.id.NewPaswordAlert);
        MainActivityConfirmPassError=findViewById(R.id.ConfirmPassAlert);

       SessionActivity sessionActivity = new SessionActivity(Setting.this);

        editOld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=editOld.getRight()-editOld.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection=editOld.getSelectionEnd();
                        if(PasswordVisible)
                        {
                            editOld.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_passwordhidden_24,0);

                            editOld.setTransformationMethod(PasswordTransformationMethod.getInstance());

                            PasswordVisible=false;
                        }
                        else {

                            editOld.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_passwordvisible_24,0);

                            editOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            PasswordVisible=true;
                        }
                        editOld.setSelection(selection);
                        return  true;

                    }
                }


                return false;
          }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validatePassword()) {

                    if (editnew.getText().toString().equals(editconfnew.getText().toString())){
                        String email_user = sessionActivity.getEmailSession();
                        //Toast.makeText(getApplicationContext(),email_user,Toast.LENGTH_SHORT).show();



                        // start update

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[3]; // Increase the array size to accommodate roleId
                                field[0] = "EmailUser";
                                field[1] = "OldPassword";
                                field[2] = "NewPassword";

                                //Creating array for data
                                String[] data = new String[3]; // Increase the array size to accommodate roleId
                                data[0] = email_user;
                                data[1] = String.valueOf(editOld.getText());
                                data[2] = String.valueOf(editnew.getText());


                                PutData putData = new PutData(Server.Url + "/Loginregister/updatePassword.php", "POST", field, data);
                                if (putData.startPut() && putData.onComplete()) {
                                    String res = putData.getResult();
                                    if (res.equals("Password updated successfully.")) {
                                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Password Updated Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });

                        // end update

                    }else{
                        Toast.makeText(getApplicationContext(),"Password does not confirm",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionActivity.removeSession();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Runtime.getRuntime().exit(0);

            }
        });
    }

    private boolean validatePassword(){
        String passwordInput = editnew.getText().toString().trim();
        String ConfitmpasswordInput = editconfnew.getText().toString().trim();
        if (passwordInput.isEmpty() || ConfitmpasswordInput.isEmpty()) {
            MainActivityPasswordError.setText("Field can't be empty");
            return false;
        } else if (editnew.getText().toString().trim().length()<5 && editconfnew.getText().toString().trim().length()<5) {
            MainActivityPasswordError.setText("Password must be at least 5 characters");
            return false;
        }
        else if (!passwordInput.equals(ConfitmpasswordInput)) {
            MainActivityConfirmPassError.setText("Password Would Not be matched");
            return false;
        }else {
            MainActivityConfirmPassError.setText("Password Matched");
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addfourn){
            startActivity(new Intent(getApplicationContext(), Adduser.class));
            finish();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}