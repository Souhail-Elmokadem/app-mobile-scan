package com.example.finestapp.fournisseur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.finestapp.product.Item;
import com.example.finestapp.product.ProductDetail;
import com.example.finestapp.R;

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

public class FournisseurList extends AppCompatActivity {

    private SearchView searchView;
    private List<Fournisseur> originalItemList;


    private static final String TAG = "FournisseurList";

    private static final String PHP_SCRIPT_URL = "http://ftapp.finesttechnology.ma/Loginregister/ListFournisseur.php";

    private ListView listView;
    private FournisseurListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur_list);

        listView = findViewById(R.id.subListView);
        adapter = new FournisseurListAdapter(this,R.layout.list_fournisseur_layout, new ArrayList<>());
        listView.setAdapter(adapter);

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

        FournisseurListAsyncTask FournisseurListAsyncTask = new FournisseurListAsyncTask();
        FournisseurListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fournisseur selectedItem = adapter.getItem(position);
                String FournisseurNom = selectedItem.getNom();
                String FournisseurTelephone = selectedItem.getTelephone();
                String FournusseurPrenom = selectedItem.getPrenom();
                String fournisseurid = selectedItem.getId();
                Intent intent = new Intent(FournisseurList.this, FournisseurDetail.class);
                intent.putExtra("FournisseurNom", FournisseurNom);
                intent.putExtra("FournisseurTelephone", FournisseurTelephone);
                intent.putExtra("FournisseurPrenom", FournusseurPrenom);
                intent.putExtra("fournisseurid",fournisseurid);
                startActivity(intent);
            }
        });

    }

    private void filterItems(String query) {
        List<Fournisseur> filteredList = new ArrayList<>();
        for (Fournisseur fournisseur : originalItemList) {
            String nom = fournisseur.getNom().toLowerCase();
            String prenom = fournisseur.getPrenom().toLowerCase();
            query = query.toLowerCase();
            if (nom.startsWith(query) || prenom.startsWith(query)) {
                filteredList.add(fournisseur);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    private class FournisseurListAsyncTask extends AsyncTask<Void, Void, List<Fournisseur>> {
        @Override
        protected List<Fournisseur> doInBackground(Void... voids) {
            List<Fournisseur> resultList = new ArrayList<>();

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

                    String fournisseurId = jsonObject.getString("idFour");
                    String fournisseurNom = jsonObject.getString("NomFour");
                    String fournisseurPrenom = jsonObject.getString("PrenomFour");
                    String fournisseurTelephone = jsonObject.getString("TelFour");

                    Fournisseur fournisseur = new Fournisseur(fournisseurId, fournisseurNom,fournisseurPrenom, fournisseurTelephone);
                    resultList.add(fournisseur);
                }

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;
        }

        @Override
        protected void onPostExecute(List<Fournisseur> resultList) {
            originalItemList = resultList;
            // Process the retrieved data (resultList)
            Toast.makeText(FournisseurList.this, "Received data: " + resultList.size() + " items", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + resultList.size() + " items");

            adapter.clear();
            adapter.addAll(resultList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addfourn){
            startActivity(new Intent(getApplicationContext(),addFournisseur.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navfour,menu);
        return super.onCreateOptionsMenu(menu);
    }
}