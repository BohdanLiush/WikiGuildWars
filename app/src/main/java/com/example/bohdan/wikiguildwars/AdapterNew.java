package com.example.bohdan.wikiguildwars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bohdan.wikiguildwars.databinding.CustomBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/** Created by bohdan on 14.04.2018. */

public class AdapterNew extends BaseAdapter {

    public ArrayList <Model> model = new ArrayList<>();
    private LayoutInflater inflater;

    public AdapterNew(List <Model> model)
    {
        this.model = (ArrayList<Model>) model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        CustomBinding customBinding = DataBindingUtil.inflate(inflater, R.layout.custom, viewGroup, false);

        customBinding.setModel(model.get(i));
        return customBinding.getRoot();
    }
}

