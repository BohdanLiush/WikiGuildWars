package com.example.bohdan.wikiguildwars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
  Created by bohdan on 14.04.2018.
 */

public class AdapterNew extends BaseAdapter {

    //public ArrayList<Model> arrayList = new ArrayList<>();
    public List<Model> model;

    public Context context;
    private LayoutInflater inflater;

    public AdapterNew(List<Model> model) {
        //this.context = context;
        this.model = model;

    }
    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ServiceCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        /*if (inflater == null) {
            inflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }*/

        /*    inflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null){
            view = inflater.inflate(R.layout.custom, viewGroup, false);
        }*/

        View v;
        if (view == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate(R.layout.custom, viewGroup, false);
        } else {
            v = view;
        }

        // view = inflater.inflate(R.layout.custom, viewGroup, false);
        ImageView imageView = v.findViewById(R.id.imageView);
        TextView id = v.findViewById(R.id.textView);
        //TextView type = view.findViewById(R.id.textView3);
        //TextView name = view.findViewById(R.id.textView4);
        TextView description = v.findViewById(R.id.textView2);

        for (int k = 0; k < model.size(); k++){
            id.setText("Id: " + model.get(i).getId()+ " N: " + i);
            //type.setText("Type: "+modelList.get(i).getType());
            //name.setText("Name: " + modelList.get(i).getName());
            description.setText("Description: " + model.get(i).getDescription());
            Picasso.get().load(model.get(i).getIcon()).into(imageView);
            notifyDataSetChanged();
            System.out.println(model.size());
        }

        return v;
    }
}

