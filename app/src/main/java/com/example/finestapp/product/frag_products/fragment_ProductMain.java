package com.example.finestapp.product.frag_products;

import android.app.Activity;
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

    private int currentTabIndex = 0;
    private ActivityFragmentProduitMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fa = this;

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
        // Set the currently selected tab based on the currentTabIndex variable
        TabLayout tabs = binding.tabs;
        TabLayout.Tab tab = tabs.getTabAt(currentTabIndex);
        if (tab != null) {
            tab.select();
        }
    }
    public static Activity fa;
}