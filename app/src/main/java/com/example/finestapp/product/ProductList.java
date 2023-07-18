package com.example.finestapp.product;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.example.finestapp.scanner.Scancamera;
import com.example.finestapp.SessionActivity;
import com.example.finestapp.user.Login;

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

    private SearchView searchView;
    private List<Item> originalItemList;
    private static final String TAG = "ProductList";
    private static final String PHP_SCRIPT_URL = "http://ftapp.finesttechnology.ma/Loginregister/ItemDetail.php";

    private ListView listView;
    private ProductListAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //nav bar



        // end nav bar

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

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
                String productDate = selectedItem.getDate();
                String productMarge = selectedItem.getMarge();
                String productfourn = selectedItem.getIdFour();
                String productId = selectedItem.getId();
                String productfourname = selectedItem.getFournisseurName();
                Intent intent = new Intent(ProductList.this, ProductDetail.class);
                intent.putExtra("productId",productId);
                intent.putExtra("productfourn",productfourn);
                intent.putExtra("productMarge",productMarge);
                intent.putExtra("productDate",productDate);
                intent.putExtra("productName", productName);
                intent.putExtra("productPrice", productPrice);
                intent.putExtra("FournisseurName",productfourname);
                startActivity(intent);
                finish();
            }
        });
    }

    private void filterItems(String query) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : originalItemList) {
            if (item.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
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
                    String productId = jsonObject.getString("idProd");
                    String productName = jsonObject.getString("NomProd");
                    String productPrice = jsonObject.getString("PrixAchat");
                    String productdate = jsonObject.getString("dateProd");
                    String productMarge = jsonObject.getString("MargeProd");
                    String productFourn = jsonObject.getString("idFour");
                    String productFourName = jsonObject.getString("FournisseurName");
                    // Item item = new Item(productName, productPrice);
                    Item item = new Item(productId, productName, productPrice,productdate,productMarge,productFourName,productFourn);
                    resultList.add(item);
                }


            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;

        }

        @Override
        protected void onPostExecute(List<Item> resultList) {
            originalItemList = resultList;
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
            finish();
            return true;
        }else if (id == R.id.scan) {
            Toast.makeText(this, "Scan Product", Toast.LENGTH_SHORT).show();
            // Handle scanbtn click action
            Intent intent = new Intent(ProductList.this, Scancamera.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.logoutbtn) {
            SessionActivity sessionActivity = new SessionActivity(ProductList.this);
            sessionActivity.removeSession();
            startActivity(new Intent(ProductList.this, Login.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}