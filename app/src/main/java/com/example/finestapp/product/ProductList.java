package com.example.finestapp.product;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.finestapp.user.Login;
import com.example.finestapp.MainActivity;
import com.example.finestapp.R;
import com.example.finestapp.Scancamera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String PHP_SCRIPT_URL = "http://ftapp.finesttechnology.ma/Loginregister/ListItem.php";

    private ListView listView;
    private ProductListAdapter adapter;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.subListView);
        adapter = new ProductListAdapter(this,R.layout.list_item_layout, new ArrayList<>());
        listView.setAdapter(adapter);

        ProductListAsyncTask ProductListAsyncTask = new ProductListAsyncTask();
        ProductListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = adapter.getItem(position);
                String productName = selectedItem.getName();
                String productPrice = selectedItem.getPrice();

                Intent intent = new Intent(ProductList.this, ProductDetail.class);
                intent.putExtra("productName", productName);
                intent.putExtra("productPrice", productPrice);
                startActivity(intent);
            }
        });
    }



    private class ProductListAsyncTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... voids) {
            List<Item> resultList = new ArrayList<>();

            try {
                URL url = new URL(PHP_SCRIPT_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String productName = jsonObject.getString("NomProd");
                    String productPrice = jsonObject.getString("PrixAchat");
                    //String productdate = jsonObject.getString("dateProd");
                   // String productMarge = jsonObject.getString("MargeProd");
                 //   String productFourn = jsonObject.getString("idFour ");
                   // String productId = jsonObject.getString("idProd ");
                    Item item = new Item(productName, productPrice);
                   // Item item = new Item(productName, productPrice,productdate,productMarge,productFourn);
                    resultList.add(item);
                }


            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;

        }

        @Override
        protected void onPostExecute(List<Item> resultList) {
            // Process the retrieved data (resultList)
            Toast.makeText(ProductList.this, "Received data: " + resultList.size() + " items", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + resultList.size() + " items");

            adapter.clear();
            adapter.addAll(resultList);
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