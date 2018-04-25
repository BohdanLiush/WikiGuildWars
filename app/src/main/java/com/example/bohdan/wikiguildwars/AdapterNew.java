package com.example.bohdan.wikiguildwars;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
  Created by bohdan on 14.04.2018.
 */

public class AdapterNew extends BaseAdapter {

    public ArrayList<Model> listAdapter = new ArrayList<>();

    public Context context;

    public AdapterNew(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return listAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        view = layoutInflater.inflate(R.layout.custom, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView id = view.findViewById(R.id.textView);
        //TextView type = view.findViewById(R.id.textView3);
        //TextView name = view.findViewById(R.id.textView4);
        TextView description = view.findViewById(R.id.textView2);

        for (int k = 0; k < listAdapter.size(); k++){
            id.setText("Id: " + listAdapter.get(i).getId()+ " N: " + i);
            //type.setText("Type: "+modelList.get(i).getType());
            //name.setText("Name: " + modelList.get(i).getName());
            description.setText("Description: " + listAdapter.get(i).getDescription());

            Picasso.get().load(listAdapter.get(i).getIcon()).into(imageView);
            System.out.println(listAdapter.size());
        }
        return view;
    }
}
