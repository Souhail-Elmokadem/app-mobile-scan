package com.example.finestapp.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finestapp.MainActivity;
import com.example.finestapp.R;
import com.example.finestapp.Scancamera;
import com.example.finestapp.user.Login;

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
            String productMarge = extras.getString("productMarge");
            String fournisseurName = extras.getString("productfourn");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdetail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editbtn) {
            Toast.makeText(this, "Add Product", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductDetail.this, AddProduct.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.deletebtn) {
            Toast.makeText(this, "Scan Product", Toast.LENGTH_SHORT).show();
            // Handle scanbtn click action
            Intent intent = new Intent(ProductDetail.this, Scancamera.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}