package com.example.finestapp.user;

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
import android.widget.Toast;

import com.example.finestapp.R;
import com.example.finestapp.product.ProductDetail;

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

    private static final String TAG = "UserList";
    private static final String PHP_SCRIPT_URL = "http://ftapp.finesttechnology.ma/Loginregister/ListUser.php";
    private ListView listView;
    private UserListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView = findViewById(R.id.subListView);
        adapter = new UserListAdapter(this,R.layout.list_user_layout, new ArrayList<>());
        listView.setAdapter(adapter);


        UserListAsyncTask UserListAsyncTask = new UserListAsyncTask();
        UserListAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedItem = adapter.getItem(position);
                String nomUser = selectedItem.getNom();
                String prenomUser = selectedItem.getPrenom();
                String emailUser = selectedItem.getEmail();
                Intent intent = new Intent(UserList.this, ProductDetail.class);
                intent.putExtra("nomUser", nomUser);
                intent.putExtra("prenomUser", prenomUser);
                intent.putExtra("emailUser",emailUser);
                startActivity(intent);
            }
        });
    }

    private class UserListAsyncTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> resultList = new ArrayList<>();

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

                Log.d(TAG, "Server Response: " + response.toString()); // Add this line to log the response

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String nomUser = jsonObject.getString("NomUser");
                    String prenomUser = jsonObject.getString("PrenomUser");
                    String email = jsonObject.getString("EmailUser");

                    User users = new User(nomUser, prenomUser,email);
                    resultList.add(users);
                }

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return resultList;
        }
        @Override
        protected void onPostExecute(List<User> resultList) {
            // Process the retrieved data (resultList)
            Toast.makeText(UserList.this, "Received data: " + resultList.size() + " items", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + resultList.size() + " items");

            adapter.clear();
            adapter.addAll(resultList);
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navfour,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addfourn){
            startActivity(new Intent(getApplicationContext(),Adduser.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}