package com.example.finestapp.user;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.example.finestapp.SessionActivity;

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

public class UserList extends AppCompatActivity {
    private SearchView searchView;
    private List<User> originalItemList;
    private static final String TAG = "UserList";
    private static String Server= com.example.finestapp.Server.Url;
    static String PHP_SCRIPT_URL = Server+"/Loginregister/ListUser.php";
    private ListView listView;
    private UserListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        userlist = this;


        listView = findViewById(R.id.subListView);
        adapter = new UserListAdapter(this,R.layout.list_user_layout, new ArrayList<>());
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


        UserListAsyncTask UserListAsyncTask = new UserListAsyncTask();
        UserListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedItem = adapter.getItem(position);
                String nomUser = selectedItem.getNom();
                String prenomUser = selectedItem.getPrenom();
                String emailUser = selectedItem.getEmail();
                String telUser = selectedItem.getTelephone();
                String idUser = selectedItem.getIdUser();
                String idrole = selectedItem.getrole();
                Intent intent = new Intent(UserList.this, UserDetail.class);
                intent.putExtra("telUser",telUser);
                intent.putExtra("nomUser", nomUser);
                intent.putExtra("prenomUser", prenomUser);
                intent.putExtra("emailUser",emailUser);
                intent.putExtra("idUser",idUser);
                intent.putExtra("idrole",idrole);
                startActivity(intent);
            }
        });
    }

    private void filterItems(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User user : originalItemList) {
            String nom = user.getNom().toLowerCase();
            String prenom = user.getPrenom().toLowerCase();
            query = query.toLowerCase();
            if (nom.startsWith(query) || prenom.startsWith(query)) {
                filteredList.add(user);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    public class UserListAsyncTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> resultList = new ArrayList<>();
            SessionActivity sessionActivity = new SessionActivity(UserList.this);
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

                Log.d("TAG", "Server Response: " + response.toString()); // Add this line to log the response

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String idUser = jsonObject.getString("idUser");
                    String nomUser = jsonObject.getString("NomUser");
                    String prenomUser = jsonObject.getString("PrenomUser");
                    String email = jsonObject.getString("EmailUser");
                    String telephone =jsonObject.getString("TelUser");
                    String idrole =  jsonObject.getString("idrole");
                    User users = new User(idUser,nomUser, prenomUser,email,telephone,idrole);
                    resultList.add(users);
                }

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;
        }
        @Override
        protected void onPostExecute(List<User> resultList) {
            originalItemList = resultList;
            // Process the retrieved data (resultList)
            Log.d("TAG", "Received data: " + resultList.size() + " items");

            adapter.clear();
            adapter.addAll(resultList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SessionActivity sessionActivity = new SessionActivity(UserList.this);
        if (Integer.parseInt(sessionActivity.getIdrole())==2){
            getMenuInflater().inflate(R.menu.navfour, menu);
        }else{

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addfourn){
            startActivity(new Intent(getApplicationContext(),Adduser.class));
            finish();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static Activity userlist;
}