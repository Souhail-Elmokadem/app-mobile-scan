package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finestapp.fournisseur.FournisseurList;
import com.example.finestapp.product.ProductList;
import com.example.finestapp.user.UserList;

public class Dashboard extends AppCompatActivity {


    ImageView fournisseur,product,scanbtn,userbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userbtn = findViewById(R.id.userbtn);
        scanbtn = findViewById(R.id.scanbtn);
        fournisseur = findViewById(R.id.fournisseurbtn);
        product = findViewById(R.id.productbtn);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductList.class);
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
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserList.class));
            }
        });
    }
}