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

public class FournisseurListAdapter extends ArrayAdapter<Fournisseur> {

    private Context context;
    private int resource;

    public FournisseurListAdapter(Context context, int resource, List<Fournisseur> items) {
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

        TextView textViewName = itemView.findViewById(R.id.fournisseurNom);
        TextView textViewPrice = itemView.findViewById(R.id.fournisseurTelephone);

        Fournisseur currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getNom() + " " + currentItem.getPrenom());
            String priceText = currentItem.getTelephone();
            textViewPrice.setText(priceText);
        }

        return itemView;
    }

}
