package com.example.finestapp.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finestapp.R;

import java.util.List;

public class ProductListAdapterPhysique extends ArrayAdapter<Item> {
    private Context context;
    private int resource;

    public ProductListAdapterPhysique(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemView = inflater.inflate(resource, parent, false);
        }

        TextView textViewName = itemView.findViewById(R.id.productNameTextView);
        TextView textViewPrice = itemView.findViewById(R.id.productPriceTextView);

        Item currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
            String priceText = currentItem.getPrice() + " DHS";
            textViewPrice.setText(priceText);
        }

        return itemView;
    }

}
