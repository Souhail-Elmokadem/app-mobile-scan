package com.example.finestapp.product.frag_products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.finestapp.InternetConnectivityChecker;
import com.example.finestapp.NoInternetConnection;
import com.example.finestapp.R;
import com.example.finestapp.databinding.ActivityFragmentProduitMainBinding;
import com.example.finestapp.product.AddProduct;
import com.example.finestapp.product.AddProductDigital;
import com.example.finestapp.ui.mainProduitTabbed.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class fragment_ProductMain extends AppCompatActivity {

    private int currentTabIndex = 0;
    ImageButton backbtn;
    private ActivityFragmentProduitMainBinding binding;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityChecker = new InternetConnectivityChecker(this);
        fa = this;

        binding = ActivityFragmentProduitMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        buttonAdd(0);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTabIndex = tab.getPosition();
                buttonAdd(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    private Runnable connectivityRunnable = new Runnable() {
        @Override
        public void run() {
            checkInternetConnection();
            handler.postDelayed(this, CHECK_INTERVAL);
        }
    };
    private static final long CHECK_INTERVAL = 5000; // Check every 5 seconds
    private Handler handler = new Handler();
    private InternetConnectivityChecker connectivityChecker;




    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(connectivityRunnable);
    }

    public void checkInternetConnection() {
        if (connectivityChecker.isInternetAvailable()) {



        } else {
            startActivity(new Intent(getApplicationContext(), NoInternetConnection.class));
            finish();
            showToast("No internet connection!");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void buttonAdd(int position) {
        FloatingActionButton fab = binding.fab;
        if (position == 0) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragment_ProductMain.this, AddProduct.class));
                }
            });
        } else if (position == 1) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragment_ProductMain.this, AddProductDigital.class));
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(connectivityRunnable);
        // Set the currently selected tab based on the currentTabIndex variable
        TabLayout tabs = binding.tabs;
        TabLayout.Tab tab = tabs.getTabAt(currentTabIndex);
        if (tab != null) {
            tab.select();
        }
    }

    public static Activity fa;
}