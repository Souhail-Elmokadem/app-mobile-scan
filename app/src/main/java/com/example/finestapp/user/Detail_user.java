package com.example.finestapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finestapp.R;

public class Detail_user extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        EditText NomUser,PrenomUser,EmailUser;

        NomUser=findViewById(R.id.EditNomUser);
        PrenomUser=findViewById(R.id.EditPrenomUser);
        EmailUser=findViewById(R.id.EditEmailUser);
          Button Backbtnuser;
//        Backbtnuser=findViewById(R.id.backbtnUser);

        Bundle extras = getIntent().getExtras();
        if (extras !=null) {
            String UserNom = extras.getString("nomUser");
            String UserPrenom = extras.getString("prenomUser");
            String UserEmail = extras.getString("emailUser");

            TextView textViewUserNom = findViewById(R.id.textViewUserNom);
            textViewUserNom.setText("First Name: " + UserNom);

            TextView textViewUserPrenom = findViewById(R.id.textViewUserPrenom);
            textViewUserPrenom.setText("Last Name: " + UserPrenom );

            TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
            textViewUserEmail.setText("Email: " + UserEmail);
        }
        Backbtnuser = findViewById(R.id.backbtnUser);

        Backbtnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

    }
}