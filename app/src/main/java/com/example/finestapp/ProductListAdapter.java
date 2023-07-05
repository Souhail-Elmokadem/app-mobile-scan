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

public class ProductListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater inflater;
    public ProductListAdapter(Context context, List<Item> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);

            holder = new ViewHolder();
            holder.productNameTextView = convertView.findViewById(R.id.productNameTextView);
            holder.productPriceTextView = convertView.findViewById(R.id.productPriceTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);

        holder.productNameTextView.setText(item.getName());
        holder.productPriceTextView.setText(item.getPrice());

        return convertView;
    }

    private static class ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
    }

}
