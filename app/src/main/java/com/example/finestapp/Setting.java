package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finestapp.user.Login;
import com.example.finestapp.user.UserList;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Setting extends AppCompatActivity {

    EditText editOld,editnew,editconfnew;
    Button savebtn,logoutbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editOld = findViewById(R.id.EditOldPassword);
        editnew = findViewById(R.id.EditNewPassword);
        editconfnew = findViewById(R.id.EditConfirmNewPassword);
        savebtn = findViewById(R.id.savebtnpass);
        logoutbtn=findViewById(R.id.logoutbtn);
        SessionActivity sessionActivity = new SessionActivity(Setting.this);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionActivity.removeSession();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }
}