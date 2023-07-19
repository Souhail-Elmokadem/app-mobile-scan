package com.example.finestapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finestapp.R;
import com.example.finestapp.fournisseur.Fournisseur;
import com.example.finestapp.product.frag_products.ProductMain;
import com.example.finestapp.scanner.ScannerQr;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {


     Button cancel, addbtn, qrbtn;
    EditText name,price,marge;

    Spinner spinnerFournisseur;

    //ArrayList to hold the list of Fournisseur objects
    ArrayList<Fournisseur> fournisseurList = new ArrayList<>();

    //Adapter for Fournisseur objects, set to null by default bc it will be populated later
    ArrayAdapter<Fournisseur> fournisseurAdapter;

    //used by Volley library to manage the network requests
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        qrbtn = findViewById(R.id.qrbtn);
        qrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fournisseur
                Fournisseur selectedFournisseur = (Fournisseur) spinnerFournisseur.getSelectedItem();
                String selectedFournisseurId = selectedFournisseur.getId();

                Intent intent = new Intent(getApplicationContext(), ScannerQr.class);
                intent.putExtra("productMarge",marge.getText().toString());
                intent.putExtra("productName", name.getText().toString());
                intent.putExtra("productPrice",price.getText().toString() );
                intent.putExtra("fournisseurid",selectedFournisseurId);
                startActivity(intent);

            }
        });


        requestQueue = Volley.newRequestQueue(this);
        spinnerFournisseur = findViewById(R.id.spinnerFournisseur);
        fournisseurList = new ArrayList<>();


        String URL = "http://ftapp.finesttechnology.ma/Loginregister/SpinnerFetcher.php";


        //fetch data from the specified URL, It makes a POST request with no request body (null)
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("fournisseur");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String fournisseurID = jsonObject.optString("idFour");
                        String fournisseurNom = jsonObject.optString("nomFour");
                        String fournisseurPrenom = jsonObject.optString("prenomFour");

                        Fournisseur fournisseur = new Fournisseur(fournisseurID, fournisseurNom, fournisseurPrenom, "");
                        fournisseurList.add(fournisseur);

                    }Log.d("Spinner Items",fournisseurList.toString());


                    //ArrayAdapter is created with the fournisseurList as the data source
                    //the layout in this line is for each item in the spinner.
                    fournisseurAdapter = new ArrayAdapter<>(AddProduct.this, android.R.layout.simple_spinner_item, fournisseurList);

                    //the layout for the dropdown items in the spinner.
                    fournisseurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFournisseur.setAdapter(fournisseurAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {

            //error responses from the network request and provides appropriate feedback
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                Log.e("AddProduct", "Error: " + errorMessage);
                Toast.makeText(getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        cancel = findViewById(R.id.cancel);
        addbtn = findViewById(R.id.addbtn);
        name = findViewById(R.id.editname);
        price = findViewById(R.id.editprice);
        marge = findViewById(R.id.editmarge);


        //Cancel Action
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductMain.class);
                startActivity(intent);
                finish();
            }
        });

        //Add New Product
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name,Price,Marge;

                Fournisseur selectedFournisseur = (Fournisseur) spinnerFournisseur.getSelectedItem();
                String selectedFournisseurId = selectedFournisseur.getId();


                Name = String.valueOf(name.getText());
                Price = String.valueOf(price.getText());
                Marge = String.valueOf(marge.getText());

                if(!Name.equals("") && !Price.equals("") && !Marge.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "NomProd";
                            field[1] = "PrixAchat";
                            field[2] = "MargeProd";
                            field[3] = "idFour";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = Name;
                            data[1] = Price;
                            data[2] = Marge;
                            data[3] = selectedFournisseurId;
                            //Adresse IP Local
    //                        PutData putData = new PutData("http://192.168.11.66/Loginregister/addproduct.php", "POST", field, data);
//                            //Adresse IP Cloud
                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addProduct.php", "POST", field, data);


                            if(putData.startPut()){
                                if(putData.onComplete()){
                                    String res = putData.getResult();
                                    if(res.equals("Add Success")){
                                        Intent intent = new Intent(getApplicationContext(), ProductMain.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Product Added !",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Remplire tout les champs vide",Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
