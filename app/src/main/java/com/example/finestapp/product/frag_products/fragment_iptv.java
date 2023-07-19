package com.example.finestapp.product.frag_products;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finestapp.R;
import com.example.finestapp.product.Item;
import com.example.finestapp.product.ProductDetail;
import com.example.finestapp.product.ProductListAdapterIptv;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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


public class fragment_iptv extends Fragment {

    public fragment_iptv() {
        // Required empty public constructor
    }

    private SearchView searchView;
    private List<Item> originalItemList;
    private static final String TAG = "ProductList";
    private static final String PHP_SCRIPT_URL = "https://ftapp.finesttechnology.ma/Loginregister/ListProduitsDigital.php";

    private ListView listView;
    private ProductListAdapterIptv adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_iptv, container, false);
        searchView = v.findViewById(R.id.searchView);
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

        listView = v.findViewById(R.id.subListView);
        adapter = new ProductListAdapterIptv(getContext(),R.layout.list_item_layout, new ArrayList<>());
        listView.setAdapter(adapter);

        ProductListAsyncTaskiptv ProductListAsyncTask = new ProductListAsyncTaskiptv();
        ProductListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = adapter.getItem(position);
                String productId = selectedItem.getId();
                String productName = selectedItem.getName();
                String productCode = selectedItem.getCode();
                Intent intent = new Intent(getContext(), ProductDetail.class);
                intent.putExtra("productId",productId);
                intent.putExtra("productCode",productCode);
                intent.putExtra("productName", productName);
                startActivity(intent);

            }
        });
        FloatingActionButton fab = v.findViewById(R.id.addbtnIptv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), AddProduct.class));
                Snackbar.make(v,"en cours",Snackbar.LENGTH_LONG).show();
            }
        });
        // end oncreate
        return v;
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

    public class ProductListAsyncTaskiptv extends AsyncTask<Void, Void, List<Item>> {


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
                    String productCode = jsonObject.getString("CodeProd");
                    // Item item = new Item(productName, productPrice);
                    Item item = new Item(productId,productName,productCode);
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
            Toast.makeText(getContext(), "Received data: " + resultList.size() + " items", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + resultList.size() + " items");

            adapter.clear();
            adapter.addAll(resultList);
            adapter.notifyDataSetChanged();
        }
    }

}