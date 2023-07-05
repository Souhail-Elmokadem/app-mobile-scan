
package com.example.finestapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Listcon extends AppCompatActivity {

    private static final String TAG = "ListconActivity";
    private static final String PHP_SCRIPT_URL = "http://192.168.11.104/Loginregister/ListItem.php"; // Replace with your PHP script URL
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcon);
        txt = findViewById(R.id.datatext);
        // Execute the AsyncTask
        ListconAsyncTask listconAsyncTask = new ListconAsyncTask();
        listconAsyncTask.execute();
    }

    private class ListconAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(PHP_SCRIPT_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

            } catch (IOException e) {
                Log.e(TAG, "Error retrieving data: " + e.getMessage());
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // Process the retrieved data (result)
            Toast.makeText(Listcon.this, "Received data: " + result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received data: " + result);
            txt.setText(result);
            // Here, you can parse the result as JSON or any other desired format
            // and extract the values from the array
            // Example: if the array contains JSON data, you can use the JSON library (e.g., JSONObject) to parse it
        }
    }


}
