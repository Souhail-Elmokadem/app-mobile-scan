package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {

    private static final String TAG = "ProductList";
    private static final String PHP_SCRIPT_URL = "http://192.168.11.63/Loginregister/ListItem.php";

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.subListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        ProductListAsyncTask ProductListAsyncTask = new ProductListAsyncTask();
        ProductListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String productName = adapter.getItem(position);
                // Handle click event for the product
                Toast.makeText(ProductList.this, "Clicked on: " + productName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ProductListAsyncTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> resultList = new ArrayList<>();

            try {
                URL url = new URL(PHP_SCRIPT_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    resultList.add(line);
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

            } catch (IOException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;

        }


        @Override
        protected void onPostExecute(List<String> resultList) {
            // Process the retrieved data (resultList)
            Toast.makeText(ProductList.this, "Received data: " + resultList.size() + " items", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + resultList.size() + " items");

            adapter.clear();
            for (String item : resultList) {
                adapter.add(item);
            }
            adapter.notifyDataSetChanged();
        }
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