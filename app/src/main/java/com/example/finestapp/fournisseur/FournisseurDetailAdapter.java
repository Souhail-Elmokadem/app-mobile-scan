package com.example.finestapp.fournisseur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finestapp.R;
import com.example.finestapp.fournisseur.Fournisseur;

import java.util.List;

public class FournisseurDetailAdapter extends ArrayAdapter<Fournisseur> {
    private Context context;
    private int resource;

    public FournisseurDetailAdapter(Context context, int resource, List<Fournisseur> f) {
        super(context, resource, f);
        this.context = context;
        this.resource = resource;
    }
//
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemView = inflater.inflate(resource, parent, false);
        }

        TextView textViewNom = itemView.findViewById(R.id.textViewFournisseurNom);
        TextView textViewPrenom = itemView.findViewById(R.id.textViewFournisseurPrenom);
        TextView textViewTele = itemView.findViewById(R.id.textViewFournisseurTele);
        Fournisseur currentItem = getItem(position);
        if (currentItem != null) {
            textViewNom.setText(currentItem.getNom());
            textViewPrenom.setText(currentItem.getPrenom());
            textViewTele.setText(currentItem.getTelephone());
        }

        return itemView;


        // return super.getView(position, convertView, parent);


    }
}

