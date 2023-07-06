package com.example.finestapp.product.productDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finestapp.R;
import com.example.finestapp.product.Item;

import java.util.List;

public class ProductDetailAdapter extends ArrayAdapter<Item>{
    private Context context;
    private int resource;

    public ProductDetailAdapter(Context context, int resource, List<Item> items) {
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

        TextView textViewName = itemView.findViewById(R.id.textViewProductName);
        TextView textViewPrice = itemView.findViewById(R.id.textViewProductPrice);
        TextView textViewDate = itemView.findViewById(R.id.textViewProductDate);
        TextView textViewMarge = itemView.findViewById(R.id.textViewProductMarge);
        TextView textviewFourn = itemView.findViewById(R.id.textViewFournisseurName);
        Item currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
            String priceText = currentItem.getPrice() + " DHS";
            textViewPrice.setText(priceText);
            textViewDate.setText(currentItem.getDate());
            textViewMarge.setText(currentItem.getMarge());
            textviewFourn.setText(currentItem.getFournisseurName());
        }

        return itemView;


       // return super.getView(position, convertView, parent);


    }
}
