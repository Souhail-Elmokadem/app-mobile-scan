package com.example.finestapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.R;
import com.example.finestapp.product.frag_products.fragment_ProductMain;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import io.github.muddz.styleabletoast.StyleableToast;

public class AddProductDigital extends AppCompatActivity {
    Button cancel, addbtn;
    EditText code,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_digital);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cancel = findViewById(R.id.cancel);
        addbtn = findViewById(R.id.addbtn);
        code = findViewById(R.id.editcodeprod);
        name = findViewById(R.id.editnomprod);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Code,Name;

                Name = String.valueOf(name.getText());
                Code = String.valueOf(code.getText());

                if(!Name.equals("") && !Code.equals("") ){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "NomProd";
                            field[1] = "CodeProd";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = Name;
                            data[1] = Code;
                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addProductDigital.php", "POST", field, data);

                            if(putData.startPut()){
                                if(putData.onComplete()){
                                    String res = putData.getResult();
                                    if(res.equals("Add Success")){
                                        fragment_ProductMain.fa.finish();
                                        startActivity(new Intent(getApplicationContext(), fragment_ProductMain.class));
                                        finish();
                                        StyleableToast.makeText(getApplicationContext(),"Produit Digital Ajouté !",R.style.successToast,Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Ajout de produit digital échoué",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Remplire tout les champs vides !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}