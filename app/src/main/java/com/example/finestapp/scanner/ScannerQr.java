package com.example.finestapp.scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.finestapp.R;
import com.example.finestapp.product.frag_products.fragment_ProductMain;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;

public class ScannerQr extends AppCompatActivity {
    private Button backbtn,addbtn;

    private boolean isProcessingBarcode = false;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    private ProgressBar progressBar;
    private CountDownTimer timer;
    private static final int TIMER_DURATION = 5000; // 5 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qr);

        backbtn = findViewById(R.id.backbtn);
        barcodeText = findViewById(R.id.barcodeText);
        surfaceView = findViewById(R.id.surface_view);
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
                    if (ActivityCompat.checkSelfPermission(ScannerQr.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannerQr.this, new
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
            public void release() {
            }

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
                        barcodeText.setText(barcodeData);
                        analysedate(barcodeData);
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                        // Start the timer and show the progress bar
                        timer.start();
                    }
                }
            }

            private void analysedate(String barcodeData) {
                addbtn = findViewById(R.id.addbtn);
                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle extras = getIntent().getExtras();


                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[5];
                                field[0] = "NomProd";
                                field[1] = "PrixAchat";
                                field[2] = "MargeProd";
                                field[3] = "idFour";
                                field[4] = "Libelle";
                                //Creating array for data
                                String[] data = new String[5];
                                data[0] = extras.getString("productName");
                                data[1] = extras.getString("productPrice");
                                data[2] = extras.getString("productMarge");
                                data[3] = extras.getString("fournisseurid");
                                data[4] = barcodeData;
                                //                        PutData putData = new PutData("http://192.168.11.66/Loginregister/addproduct.php", "POST", field, data);
                                PutData putData = new PutData("http://ftapp.finesttechnology.ma/Loginregister/addProductWithQr.php", "POST", field, data);


                                if(putData.startPut()){
                                    if(putData.onComplete()){
                                        String res = putData.getResult();
                                        if(res.equals("Add Success")){
                                            Intent intent = new Intent(getApplicationContext(), fragment_ProductMain.class);
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

                    }
                });

            }
        });
    }
//timer
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}