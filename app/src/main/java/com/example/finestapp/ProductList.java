package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ProductList extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addbtn) {
            Toast.makeText(this, "Add Product", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductList.this, AddProduct.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.scan) {
            Toast.makeText(this, "Scan Product", Toast.LENGTH_SHORT).show();
            // Handle scanbtn click action
            Intent intent = new Intent(ProductList.this, Scancamera.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.logoutbtn) {
            Login.sharedPreferences.edit().remove("state").commit();
            Intent intent = new Intent(ProductList.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}