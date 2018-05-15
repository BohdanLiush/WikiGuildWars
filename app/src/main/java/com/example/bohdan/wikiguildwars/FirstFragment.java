package com.example.bohdan.wikiguildwars;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/** Created by bohdan on 27.04.2018. */

public class FirstFragment extends Fragment implements Serializable {

    SeekBar seekBar;
    Spinner spinner;
    TextView textSeekbar;
    public int numberObj;
    ArrayList<String> spinnerList = new ArrayList<>();
    public Button button;
    //public ArrayList<Model> modelList = new ArrayList<>();
    public  List<Model> model;
    BaseAdapter baseAdapter;
    GridView gridView;
    SquareImageView squareImageView;
    AdapterNew adapterNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        container = (ViewGroup) inflater.inflate(R.layout.fragmentone, container,false);
        button = container.findViewById(R.id.button);
        seekBar = container.findViewById(R.id.seekBar);
        textSeekbar = container.findViewById(R.id.textView3);

        textSeekbar.setText("Progress: " + seekBar.getProgress() + " / " + seekBar.getMax());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activityHome = (MainActivity) view.getContext();
                CallbackClass callbacks = new CallbackClass();
                callbacks.registerCallBack(activityHome);
                try {
                    model = callbacks.loadNumberObject(numberObj);
                    adapterNew = new AdapterNew(model);
                    gridView.setAdapter(adapterNew);
                    adapterNew.notifyDataSetChanged(); // метод обновлення listview
                    System.out.println("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textSeekbar.setText(String.valueOf("Progress: " + progress + " / " + seekBar.getMax()));
                numberObj = progress;
                System.out.println("numberobj: " + numberObj);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //!!!...spinner
        spinnerList.add("    John");
        spinnerList.add("    All");
        spinnerList.add("    Even");
        spinnerList.add("    Odd");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(container.getContext(),R.layout.support_simple_spinner_dropdown_item,spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner = container.findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setPromptId(R.string.country);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                /*if (position==0){
                    TemplistReturn();
                }
                if (position==1){
                    deleteAllElement(container);
                }
                if (position==2){
                    deleteEvenElement();
                }
                if (position==3){
                    deleteOddElement(container);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        gridView = container.findViewById(R.id.liste23);
        //squareImageView = new SquareImageView(container.getContext());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity activityHome = (MainActivity) view.getContext();
                CallbackClass callbacks = new CallbackClass();
                callbacks.registerCallBack(activityHome);
                try {
                    callbacks.sendNumberObject(model.get(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*Intent intent = new Intent(view.getContext(), TwoActivity.class);
                intent.putExtra("item", (Serializable) model.get(i));
                startActivity(intent);*/
            }
        });

        /*baseAdapter = new BaseAdapter() {
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                view = getLayoutInflater().inflate(R.layout.custom, viewGroup, false);
                ImageView imageView = view.findViewById(R.id.imageView);
                TextView id = view.findViewById(R.id.textView);
                //TextView type = view.findViewById(R.id.textView3);
                //TextView name = view.findViewById(R.id.textView4);
                TextView description = view.findViewById(R.id.textView2);

                for (int k = 0; k < model.size(); k++){
                    id.setText("Id: " + model.get(i).getId()+ " N: " + i);
                    //type.setText("Type: "+modelList.get(i).getType());
                    //name.setText("Name: " + modelList.get(i).getName());
                    description.setText("Description: " + model.get(i).getDescription());
                    Picasso.get().load(model.get(i).getIcon()).into(imageView);
                    System.out.println(model.size());
                }

                return view;
            }
        };*/
        return container;
    }
}
