package com.example.finestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.fournisseur.FournisseurList;
import com.example.finestapp.product.frag_products.fragment_ProductMain;
import com.example.finestapp.scanner.Scancamera;
import com.example.finestapp.user.UserList;

public class Dashboard extends AppCompatActivity {
    ImageView fournisseur,product,scanbtn,userbtn,profilebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userbtn = findViewById(R.id.userbtn);
        scanbtn = findViewById(R.id.scanbtn);
        fournisseur = findViewById(R.id.fournisseurbtn);
        product = findViewById(R.id.productbtn);
        profilebtn = findViewById(R.id.ProfileButton);
//        dashboard=this;
        SessionActivity sessionActivity = new SessionActivity(Dashboard.this);
        String role =  sessionActivity.getIdrole();
        if (role=="null"){
            role="3";
        }
        if (Integer.parseInt(role)==2){
            userbtn.setVisibility(View.VISIBLE);
            userbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), UserList.class));
                }
            });
        }else if(Integer.parseInt(role)==3){
            userbtn.setVisibility(View.GONE);
        }



        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
            }
        });
        getSupportActionBar().hide();
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), fragment_ProductMain.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "product", Toast.LENGTH_SHORT).show();
            }
        });


        fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FournisseurList.class);
                startActivity(intent);
            }
        });
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Scancamera.class));
            }
        });

    }

    public static Dashboard dashboard;



}