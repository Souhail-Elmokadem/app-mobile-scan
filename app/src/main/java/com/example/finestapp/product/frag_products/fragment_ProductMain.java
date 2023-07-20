package com.example.finestapp.product.frag_products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.finestapp.R;
import com.example.finestapp.SessionActivity;
import com.example.finestapp.databinding.ActivityFragmentProduitMainBinding;
import com.example.finestapp.product.AddProduct;
import com.example.finestapp.product.AddProductDigital;
import com.example.finestapp.scanner.Scancamera;
import com.example.finestapp.ui.mainProduitTabbed.SectionsPagerAdapter;
import com.example.finestapp.user.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class fragment_ProductMain extends AppCompatActivity {

    private ActivityFragmentProduitMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFragmentProduitMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        buttonAdd(0);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

    public void buttonAdd(int postion) {
        FloatingActionButton fab = binding.fab; //this button temporaire here before ask reda
        if (postion == 0) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragment_ProductMain.this, AddProduct.class));
                    finish();
                }
            });
        } else if (postion == 1) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragment_ProductMain.this, AddProductDigital.class));
                    finish();
                }
            });
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(fragment_ProductMain.this, AddProduct.class));
                    finish();
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addbtn) {
            Intent intent = new Intent(fragment_ProductMain.this, AddProduct.class);
            startActivity(intent);
            finish();
            return true;
        }else if (id == R.id.scan) {
            // Handle scanbtn click action
            Intent intent = new Intent(fragment_ProductMain.this, Scancamera.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.logoutbtn) {
            SessionActivity sessionActivity = new SessionActivity(fragment_ProductMain.this);
            sessionActivity.removeSession();
            startActivity(new Intent(fragment_ProductMain.this, Login.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}