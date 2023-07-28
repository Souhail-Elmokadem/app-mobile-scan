package com.example.finestapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finestapp.user.Adduser;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Setting extends AppCompatActivity {

    EditText editOld,editnew,editconfnew;
    Button savebtn,logoutbtn;
    TextView MainActivityPasswordError,MainActivityConfirmPassError,MainActivityOldPasswordError;
    boolean PasswordVisible;

    String actuelOldPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editOld = findViewById(R.id.EditOldPassword);
        editnew = findViewById(R.id.EditNewPassword);
        editconfnew = findViewById(R.id.EditConfirmNewPassword);
        savebtn = findViewById(R.id.savebtn);
        logoutbtn=findViewById(R.id.logoutbtn);

        MainActivityOldPasswordError= findViewById(R.id.OldPasswordAlert);
        MainActivityPasswordError=findViewById(R.id.NewPaswordAlert);
        MainActivityConfirmPassError=findViewById(R.id.ConfirmPassAlert);

       SessionActivity sessionActivity = new SessionActivity(Setting.this);
        actuelOldPassword=new SessionActivity(Setting.this).getPassword();

        editOld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=editOld.getRight()-editOld.getCompoundDrawables()[Right].getBounds().width())
                    {
                        int selection=editOld.getSelectionEnd();
                        if(PasswordVisible)
                        {
                            editOld.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_passwordhidden_24,0);

                            editOld.setTransformationMethod(PasswordTransformationMethod.getInstance());

                            PasswordVisible=false;
                        }
                        else {

                            editOld.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_passwordvisible_24,0);

                            editOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                            PasswordVisible=true;
                        }
                        editOld.setSelection(selection);
                        return  true;

                    }
                }


                return false;
          }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validatePassword()) {

                    if (editnew.getText().toString().equals(editconfnew.getText().toString())){
                        String email_user = sessionActivity.getEmailSession();
                        //Toast.makeText(getApplicationContext(),email_user,Toast.LENGTH_SHORT).show();



                        // start update

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[3]; // Increase the array size to accommodate roleId
                                field[0] = "EmailUser";
                                field[1] = "OldPassword";
                                field[2] = "NewPassword";

                                //Creating array for data
                                String[] data = new String[3]; // Increase the array size to accommodate roleId
                                data[0] = email_user;
                                data[1] = String.valueOf(editOld.getText());
                                data[2] = String.valueOf(editnew.getText());


                                PutData putData = new PutData(Server.Url + "/Loginregister/updatePassword.php", "POST", field, data);
                                if (putData.startPut() && putData.onComplete()) {
                                    String res = putData.getResult();
                                    if (res.equals("Password updated successfully.")) {
                                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                        finish();
                                        Toast.makeText(getApplicationContext(), "votre mot de passe à été changé avec succès", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });

                        // end update

                    }else{
                        Toast.makeText(getApplicationContext(),"Mot de passe est invalid",Toast.LENGTH_SHORT).show();
                    }

                }





            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionActivity.removeSession();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Runtime.getRuntime().exit(0);
            }
        });

        // Ajoutez ces lignes dans votre onCreate après avoir initialisé les EditTexts

// TextWatcher pour le champ d'ancien mot de passe
        editOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Rien à faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Effacez le message d'erreur de l'ancien mot de passe lorsque l'utilisateur commence à saisir
                MainActivityOldPasswordError.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Rien à faire après que le texte a changé
            }
        });

// TextWatcher pour le champ de nouveau mot de passe
        editnew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Rien à faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Effacez le message d'erreur du nouveau mot de passe lorsque l'utilisateur commence à saisir
                MainActivityPasswordError.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Rien à faire après que le texte a changé
            }
        });

// TextWatcher pour le champ de confirmation du nouveau mot de passe
        editconfnew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Rien à faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Effacez le message d'erreur de la confirmation du nouveau mot de passe lorsque l'utilisateur commence à saisir

                MainActivityConfirmPassError.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Rien à faire après que le texte a changé
            }
        });

    }

    private boolean validatePassword(){
        String enterOldPassword=editOld.getText().toString().trim();
        String passwordInput = editnew.getText().toString().trim();
        String ConfitmpasswordInput = editconfnew.getText().toString().trim();
        if (!actuelOldPassword.equals(enterOldPassword)){
            MainActivityOldPasswordError.setVisibility(View.VISIBLE);
            MainActivityOldPasswordError.setText("Mot de passe est Incorrect");
           return false;
        }
        else if (passwordInput.isEmpty() || ConfitmpasswordInput.isEmpty()) {
            MainActivityPasswordError.setText("Remplire Les champs vide");
            MainActivityPasswordError.setVisibility(View.VISIBLE);
            return false;
        } else if (editnew.getText().toString().trim().length()<5 && editconfnew.getText().toString().trim().length()<5) {
            MainActivityPasswordError.setText("mot de passe doit contenir au minimum 5 caractères");
            MainActivityPasswordError.setVisibility(View.VISIBLE);
            return false;
        }
        else if (!passwordInput.equals(ConfitmpasswordInput)) {
            MainActivityConfirmPassError.setText("Le mot de passe ne correspond pas");
            MainActivityConfirmPassError.setVisibility(View.VISIBLE);
            return false;
        }else {

            MainActivityConfirmPassError.setVisibility(View.VISIBLE);
            return true;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addfourn){
            startActivity(new Intent(getApplicationContext(), Adduser.class));
            finish();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}