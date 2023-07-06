package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductDetail extends AppCompatActivity {

    private TextView productNameTextView;
    private TextView productPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Button backbtn;
        backbtn = findViewById(R.id.backbtn);

        // Retrieve extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productDate = extras.getString("productDate");
            int productMarge = extras.getInt("productMarge");
            String fournisseurName = extras.getString("fournisseurName");

            // Set values to TextView elements
            TextView textViewProductName = findViewById(R.id.textViewProductName);
            textViewProductName.setText("Product Name: " + productName);

            TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
            textViewProductPrice.setText("Product Price: " + productPrice + "DHS");

            TextView textViewProductDate = findViewById(R.id.textViewProductDate);
            textViewProductDate.setText("Product Date: " + productDate);

            TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
            textViewProductMarge.setText("Product Marge: " + productMarge + "DHS");

            TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
            textViewFournisseurName.setText("Fournisseur Name: " + fournisseurName);
        }
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
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