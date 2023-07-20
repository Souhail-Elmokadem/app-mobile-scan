package com.example.finestapp.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finestapp.R;
import com.example.finestapp.product.frag_products.fragment_ProductMain;
import com.example.finestapp.product.frag_products.fragment_digital;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddProductDigital extends AppCompatActivity {


    Button cancel, addbtn;
    EditText code,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_digital);

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
                            field[0] = "CodeProd";
                            field[1] = "NomProd";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = Code;
                            data[1] = Name;
                            PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addProductDigital.php", "POST", field, data);

                            if(putData.startPut()){
                                if(putData.onComplete()){
                                    String res = putData.getResult();
                                    if(res.equals("Add Success")){
                                        Intent intent = new Intent(getApplicationContext(), fragment_ProductMain.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"Digital Product Added !",Toast.LENGTH_SHORT).show();
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), fragment_digital.class);
                startActivity(intent);
                finish();
            }
        });
    }
}