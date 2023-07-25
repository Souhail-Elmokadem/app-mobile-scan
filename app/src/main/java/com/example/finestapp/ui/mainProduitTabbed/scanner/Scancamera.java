package com.example.finestapp.ui.mainProduitTabbed.scanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finestapp.R;
import com.example.finestapp.product.Item;
import com.example.finestapp.product.ProductDetail;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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

public class Scancamera extends AppCompatActivity {

    private boolean isProcessingBarcode = false;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private static final String Url_PHP_Product = "https://ftapp.finesttechnology.ma/Loginregister/getProductByBarcode.php";
    private TextView barcodeText;
    private String barcodeData;
    private Button backbtn;
    private ProgressBar progressBar;
    private CountDownTimer timer;
    private static final int TIMER_DURATION = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancamera);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        barcodeText = findViewById(R.id.barcodeText);
        surfaceView = findViewById(R.id.surface_view);
        backbtn = findViewById(R.id.backbtn);
        progressBar = findViewById(R.id.progressBar);
        timer = createTimer(TIMER_DURATION);

        initialiseDetectorsAndSources();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1080, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Scancamera.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(Scancamera.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0 && !isProcessingBarcode) {
                    isProcessingBarcode = true;

                    Barcode barcode = barcodes.valueAt(0);
                    if (barcode.email != null) {
                        barcodeData = barcode.email.address;
                    } else {
                        barcodeData = barcode.displayValue;
                        //  barcodeText.setText(barcodeData);
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        verife_CodeBarre_Produit(barcodeData);

                        // Start the timer and show the progress bar
                        timer.start();

                    }
                }
            }
        });
    }

    private CountDownTimer createTimer(long duration) {
        return new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the progress bar based on the remaining time
                int progress = (int) (millisUntilFinished / 1000);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                // Timer finished, reset the progress bar and allow scanning again
                progressBar.setProgress(0);
                isProcessingBarcode = false;

                // Reset the timer
                timer.cancel();
                timer = createTimer(TIMER_DURATION);
            }
        };
    }

    private boolean verife_CodeBarre_Produit(String barecode) {

        ArrayList<Item> resultList = new ArrayList<>();
        boolean status = false;
        try {
            URL url = new URL(Url_PHP_Product);
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

                if(jsonObject.getString("Lebelle").equals(barecode)){
                    String productId = jsonObject.getString("idProd");
                    String productName = jsonObject.getString("NomProd");
                    String productPrice = jsonObject.getString("PrixAchat");
                    String productdate = jsonObject.getString("dateProd");
                    String productMarge = jsonObject.getString("MargeProd");
                    String productFourn = jsonObject.getString("idFour");
                    String productFourName = jsonObject.getString("FournisseurName");
                    // Item item = new Item(productName, productPrice);
                    Item item = new Item(productId, productName, productPrice,productdate,productMarge,productFourName,productFourn);
                    resultList.add(item);

                    Intent intent = new Intent(Scancamera.this, ProductDetail.class);
                    intent.putExtra("productId",productId);
                    intent.putExtra("productfourn",productFourn);
                    intent.putExtra("productMarge",productMarge);
                    intent.putExtra("productDate",productdate);
                    intent.putExtra("productName", productName);
                    intent.putExtra("productPrice", productPrice);
                    intent.putExtra("FournisseurName",productFourName);

                    startActivity(intent);
                    finish();
                } else if (jsonObject.getString("Libelle").equals(barecode)) {
                    String productId = jsonObject.getString("idProd");
                    String productName = jsonObject.getString("NomProd");
                    String productPrice = jsonObject.getString("PrixAchat");
                    String productdate = jsonObject.getString("dateProd");
                    String productMarge = jsonObject.getString("MargeProd");
                    String productFourn = jsonObject.getString("idFour");
                    String productFourName = jsonObject.getString("FournisseurName");
                    // Item item = new Item(productName, productPrice);
                    Item item = new Item(productId, productName, productPrice,productdate,productMarge,productFourName,productFourn);
                    resultList.add(item);

                    Intent intent = new Intent(Scancamera.this, ProductDetail.class);
                    intent.putExtra("productId",productId);
                    intent.putExtra("productfourn",productFourn);
                    intent.putExtra("productMarge",productMarge);
                    intent.putExtra("productDate",productdate);
                    intent.putExtra("productName", productName);
                    intent.putExtra("productPrice", productPrice);
                    intent.putExtra("FournisseurName",productFourName);

                    startActivity(intent);
                    finish();

                } else {
                    barcodeText.setText("Codebar Not Found");
                }
            }

        } catch (IOException | JSONException e) {
            Log.e(resultList.toString(), "Error retrieving data: " + e.getMessage());
        }
        return true;
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}