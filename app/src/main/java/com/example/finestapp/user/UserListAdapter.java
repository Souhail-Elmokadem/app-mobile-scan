package com.example.finestapp.user;

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

public class UserListAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;

    public UserListAdapter(Context context, int resource, List<User> users){
        super(context, resource, users);
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

        TextView textViewName = itemView.findViewById(R.id.userNom);
        TextView textViewRole = itemView.findViewById(R.id.userRole);
        User currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getNom() + " " + currentItem.getPrenom());
            textViewRole.setText(currentItem.getroleName());
        }

        return itemView;
    }

}
