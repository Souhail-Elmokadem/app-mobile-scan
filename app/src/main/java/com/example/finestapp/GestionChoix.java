package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class GestionChoix extends AppCompatActivity {

    ImageView fournisseur,product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_choix);
        this.setTitle("Choix");
        fournisseur = findViewById(R.id.imageView4);
        product = findViewById(R.id.imageView5);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductList.class);
                startActivity(intent);
                Toast.makeText(GestionChoix.this, "product", Toast.LENGTH_SHORT).show();
            }
        });
        fournisseur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),fournisseurlist.class);
                startActivity(intent);
            }
        });
    }
}