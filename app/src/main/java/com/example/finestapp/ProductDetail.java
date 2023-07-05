package com.example.finestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class ProductDetail extends AppCompatActivity {

    private static final String TAG = "ProductList";
    private static final String PHP_SCRIPT_URL = "http://192.168.11.63/Loginregister/ItemDetail.php";

    private ListView listView;
    private ProductListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Button backbtn;
        backbtn = findViewById(R.id.backbtn);


        listView = findViewById(R.id.subListView);
        adapter = new ProductListAdapter(this, R.layout.list_detail_layout, new ArrayList<>());
        listView.setAdapter(adapter);

        ProductDetailAsyncTask productDetailAsyncTask = new ProductDetailAsyncTask();
        productDetailAsyncTask.execute();

        // Retrieve extras


        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


}
    private class ProductDetailAsyncTask extends AsyncTask<Void, Void, List<Item>> {

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
                    String productDate = jsonObject.getString("Date");
                    int productMarge = jsonObject.getInt("Marge");
                    String fournisseurName = jsonObject.getString("Fournisseur");
                    Item item = new Item(productName, productPrice,productDate,productMarge,fournisseurName);
                    resultList.add(item);
                }


            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
