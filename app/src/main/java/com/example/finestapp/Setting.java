package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    EditText editOld,editnew,editconfnew;
    Button savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editOld = findViewById(R.id.EditOldPassword);
        editnew = findViewById(R.id.EditNewPassword);
        editconfnew = findViewById(R.id.EditConfirmNewPassword);
        savebtn = findViewById(R.id.savebtnpass);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editnew.getText().toString().equals(editconfnew.getText().toString())){


                }else{
                    Toast.makeText(getApplicationContext(),"Password does not confirm",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}