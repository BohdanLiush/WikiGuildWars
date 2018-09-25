package com.example.bohdan.wikiguildwars;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/** Created by bohdan on 11.06.2018. */

public class BindingAdapters {


    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, String string) {
        Glide.with(view.getContext()).load(string).into(view);
    }

    @BindingAdapter("bind:items")
    public static void listBindGridview (GridView view, ArrayList<Model> model){
        AdapterNew adapterNew = new AdapterNew(model);

        view.setAdapter(adapterNew);
        //adapterNew.notifyDataSetChanged();
    }
}
