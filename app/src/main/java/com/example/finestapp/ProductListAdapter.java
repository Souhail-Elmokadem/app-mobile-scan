package com.example.finestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private List<Product> mProductList;

    public ProductListAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
        mContext = context;
        mProductList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_product, parent, false);
        }

        Product currentProduct = mProductList.get(position);

        TextView nameTextView = listItemView.findViewById(R.id.text_name);
        nameTextView.setText(currentProduct.getNomProd());

        TextView priceTextView = listItemView.findViewById(R.id.text_price);
        priceTextView.setText(String.valueOf(currentProduct.getPrixAchat()));

        TextView dateTextView = listItemView.findViewById(R.id.text_date);
        dateTextView.setText(currentProduct.getDateProd());

        return listItemView;
    }
}
