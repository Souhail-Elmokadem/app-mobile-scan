package com.example.finestapp.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finestapp.R;
import com.example.finestapp.Server;
import com.example.finestapp.fournisseur.Fournisseur;
import com.example.finestapp.product.frag_products.ProductMain;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class ProductDetail extends AppCompatActivity {



    private ArrayList<Fournisseur> fournisseurList;
    private ArrayAdapter<Fournisseur> fournisseurAdapter;
    Button savebtn, cancelbtn, backbtn;
    EditText name,price,date,marge;
    RequestQueue requestQueue;
    Spinner fournisseurSpinner;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        // textbox product details pour edit btn

        name = findViewById(R.id.editname);
        price = findViewById(R.id.editprice);
        date = findViewById(R.id.editdate);
        marge = findViewById(R.id.editmarge);

        requestQueue = Volley.newRequestQueue(this);
        fournisseurSpinner = findViewById(R.id.editfournisseur);
        fournisseurList = new ArrayList<>();

        backbtn = findViewById(R.id.backbtn);
        savebtn = findViewById(R.id.savebtn);
        cancelbtn = findViewById(R.id.cancelbtn);


        String URL = Server.Url+"/Loginregister/SpinnerFetcher.php";

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
                    }

                    fournisseurAdapter = new ArrayAdapter<>(ProductDetail.this, android.R.layout.simple_spinner_item, fournisseurList);
                    fournisseurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fournisseurSpinner.setAdapter(fournisseurAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                Log.e("ProductDetail", "Error: " + errorMessage);
                Toast.makeText(getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });


        // Retrieve extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("productName")!=null && extras.getString("productMarge")!=null){
                String productName = extras.getString("productName");
                String productPrice = extras.getString("productPrice");
                String productDate = extras.getString("productDate");
                String productMarge = extras.getString("productMarge");
                String fournisseurid = extras.getString("productfourn");
                String fournisseurName = extras.getString("FournisseurName");
                // Set values to TextView elements
                TextView textViewProductName = findViewById(R.id.textViewProductName);
                textViewProductName.setText("Product Name: " + productName);

                TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
                textViewProductPrice.setText("Product Price: " + productPrice + "DHS");

                TextView textViewProductDate = findViewById(R.id.textViewProductDate);
                textViewProductDate.setText("Product Date: " + productDate);

                TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
                textViewProductMarge.setText("Product Marge: " + productMarge + "DHS");

                TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
                textViewFournisseurName.setText("Fournisseur Name: " + fournisseurName);
            } else if (extras.getString("productCode")!=null) {
                TextView textViewProductDate = findViewById(R.id.textViewProductDate);
                TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
                textViewProductMarge.setVisibility(View.GONE);
                TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
                textViewFournisseurName.setVisibility(View.GONE);
                textViewProductDate.setVisibility(View.GONE);
                String productName = extras.getString("productName");
                String productCode = extras.getString("productCode");
                TextView textViewProductName = findViewById(R.id.textViewProductName);
                textViewProductName.setText("Product Name: " + productName);

                TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
                textViewProductPrice.setText("Product Code: " + productCode );
            }
        }

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = findViewById(R.id.linear);
                linearLayout.setVisibility(View.GONE);

                TextView textViewProductName = findViewById(R.id.textViewProductName);
                TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
                TextView textViewProductDate = findViewById(R.id.textViewProductDate);
                TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
                TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);

                textViewProductName.setVisibility(View.VISIBLE);
                textViewProductPrice.setVisibility(View.VISIBLE);
                textViewProductDate.setVisibility(View.VISIBLE);
                textViewProductMarge.setVisibility(View.VISIBLE);
                textViewFournisseurName.setVisibility(View.VISIBLE);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String productID = extras.getString("productId");
                if (name.getText().toString().trim().length() > 0 && price.getText().toString().trim().length() > 0 &&
                       date. getText().toString().trim().length() > 0 && marge.getText().toString().trim().length() > 0
                ){

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[6];
                            field[0] = "idProd";
                            field[1] = "NomProd";
                            field[2] = "PrixAchat";
                            field[3] = "dateProd";
                            field[4] = "MargeProd";
                            field[5] = "idFour";

                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = productID;
                            data[1] = String.valueOf(name.getText());
                            data[2] = String.valueOf(price.getText());
                            data[3] = String.valueOf(date.getText());
                            data[4] = String.valueOf(marge.getText());

                            Fournisseur selectedFournisseur = (Fournisseur) fournisseurSpinner.getSelectedItem();
                            String selectedFournisseurId = selectedFournisseur.getId();
                            data[5] = selectedFournisseurId;



                            PutData putData = new PutData(Server.Url+"/Loginregister/updateProd.php", "POST", field, data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String res = putData.getResult();
                                    if (res.equals("Updated Success")){
                                        startActivity(new Intent(getApplicationContext(), ProductMain.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.editbtn){
            Bundle extras = getIntent().getExtras();
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productDate = extras.getString("productDate");
            String productMarge = extras.getString("productMarge");
            String fournisseurid = extras.getString("productfourn");
            backbtn = findViewById(R.id.backbtn);
            backbtn.setText("Cancel");

            LinearLayout linear = findViewById(R.id.linear);


        name.setText(productName);
        price.setText(productPrice);
        date.setVisibility(View.GONE);
        date.setText(productDate);
        marge.setText(productMarge);

        linear.setVisibility(View.VISIBLE);

            // Find the position of the selected fournisseur in the fournisseurList
            int selectedFournisseurPosition = -1;
            for (int i = 0; i < fournisseurList.size(); i++) {
                Fournisseur fournisseur = fournisseurList.get(i);
                if (fournisseur.getId().equals(fournisseurid)) {
                    selectedFournisseurPosition = i;
                    break;
                }
            }

            // Set the selected fournisseur in the spinner
            if (selectedFournisseurPosition != -1) {
                fournisseurSpinner.setSelection(selectedFournisseurPosition);
            }


        TextView textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductName.setVisibility(View.GONE);
        TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
        textViewProductPrice.setVisibility(View.GONE);
        TextView textViewProductDate = findViewById(R.id.textViewProductDate);
        textViewProductDate.setVisibility(View.GONE);
        TextView textViewProductMarge = findViewById(R.id.textViewProductMarge);
        textViewProductMarge.setVisibility(View.GONE);
        TextView textViewFournisseurName = findViewById(R.id.textViewFournisseurName);
        textViewFournisseurName.setVisibility(View.GONE);
        savebtn.setVisibility(View.VISIBLE);
        }
        else if (item.getItemId() == R.id.deletebtn) {
            alertDialog = new AlertDialog.Builder(ProductDetail.this);
            alertDialog.setTitle("Suppression");
            alertDialog.setMessage("Are you sure ?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[1];
                            field[0] = "idProd";
                            String[] data = new String[1];
                            data[0] = getIntent().getExtras().getString("productId");
                            PutData putData = new PutData(Server.Url+"/Loginregister/deleteProd.php", "POST", field, data);
                            if (putData.startPut()){
                                if (putData.onComplete()){
                                    String res = putData.getResult();
                                    if (res.equals("Product deleted successfully.")){
                                        startActivity(new Intent(getApplicationContext(), ProductMain.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Suppression with success", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    }
                    });

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Any Message for inform user cancel
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navdetail, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}