package com.example.bohdan.wikiguildwars;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;


import com.example.bohdan.wikiguildwars.databinding.FragmentOneBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Created by bohdan on 27.04.2018. */

public class FirstFragment extends Fragment implements Serializable {

    public int numberObj;
    FirstFragment firstFragment;

    ArrayList<String> spinnerList = new ArrayList<>();
    public ArrayList <Model> modelList = new ArrayList<>();
    public ArrayList<Model> templist = new ArrayList<>();

    public List <Model> model;
    public Model newModel;
    FragmentOneBinding fragmentOneBinding;
    ArrayListModels arrayListModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         fragmentOneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_one,
                container, false);

         fragmentOneBinding.textSeekbarTextview.setText(String.valueOf("Progress: " + fragmentOneBinding.seekBar.getProgress()
                 + " / " + fragmentOneBinding.seekBar.getMax()));

         arrayListModels = new ArrayListModels(modelList);
         fragmentOneBinding.setModelRol(arrayListModels);


         fragmentOneBinding.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //arrayListModels = null;
                methodOnClick(view);
                fragmentOneBinding.setModelRol(arrayListModels);

            /*MainActivity activityHome = (MainActivity) view.getContext();
            CallbackClass callbacks = new CallbackClass();
            callbacks.registerCallBack(activityHome);

                try {
                    model = callbacks.loadNumberObject(numberObj);
                    //model = callbacks.sendIdObject(numberObj);
                    //newModel = callbacks.sendIdObject(numberObj);
                    modelList.addAll(model);
                    fragmentOneBinding.setFirstFragment((FirstFragment) model);
                    //fragmentOneBinding.setModel((Model) model);
                    *//*if (adapterNew == null){
                        adapterNew = new AdapterNew(modelList);
                        fragmentOneBinding.gridviewliste23.setAdapter(adapterNew);
                        adapterNew.notifyDataSetChanged();
                        setRetainInstance(true);
                    }*//*
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
        });

       fragmentOneBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                fragmentOneBinding.textSeekbarTextview.setText(String.valueOf("Progress: " + progress
                      + " / " + fragmentOneBinding.seekBar.getMax()));
                numberObj = progress;
                //System.out.println("numberobj: " + numberObj);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //!!!...spinner
        spinnerList.clear();
        spinnerList.add("    Choice");
        spinnerList.add("    All");
        spinnerList.add("    Even");
        spinnerList.add("    Odd");

       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(container.getContext(),
                                         R.layout.support_simple_spinner_dropdown_item,spinnerList);
       arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

       fragmentOneBinding.spinner.setAdapter(arrayAdapter);
       fragmentOneBinding.spinner.setPromptId(R.string.country);

       fragmentOneBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position==1){
                    //adapterNew = new AdapterNew(changeToAllElement());
                    arrayListModels = new ArrayListModels(changeToAllElement());
                    fragmentOneBinding.setModelRol(arrayListModels);

                   // fragmentOneBinding.gridviewliste23.setAdapter(adapterNew);
                   // adapterNew.notifyDataSetChanged();
                }
                if (position==2){
                    //adapterNew = new AdapterNew(changeToEvenElement());
                    arrayListModels = new ArrayListModels(changeToEvenElement());
                    fragmentOneBinding.setModelRol(arrayListModels);

                    // fragmentOneBinding.gridviewliste23.setAdapter(adapterNew);
                    //adapterNew.notifyDataSetChanged();
                }
                 if (position==3){
                     //adapterNew = new AdapterNew(changeToOddElement());
                     arrayListModels = new ArrayListModels(changeToOddElement());
                     fragmentOneBinding.setModelRol(arrayListModels);

                     //fragmentOneBinding.gridviewliste23.setAdapter(adapterNew);
                     //adapterNew.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        fragmentOneBinding.gridviewliste23.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity activityHome = (MainActivity) view.getContext();
                CallbackClass callbacks = new CallbackClass();
                callbacks.registerCallBack(activityHome);
                try {
                    callbacks.sendIdObject(templist.get(i).getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return fragmentOneBinding.getRoot();
    }

    public void methodOnClick(View view){

        MainActivity activityHome = (MainActivity) view.getContext();
        CallbackClass callbacks = new CallbackClass();
        callbacks.registerCallBack(activityHome);

        try {
            modelList.clear();  /** тут друге місце де ми поклали clear */
            model = callbacks.loadNumberObject(numberObj);
            //model = callbacks.sendIdObject(numberObj);
            //newModel = callbacks.sendIdObject(numberObj);
            modelList.addAll(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public ArrayList <Model> changeToEvenElement(){
        templist.clear();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2==0){
                templist.add(modelList.get(i));
            }
        }
        return templist;
    }

    public ArrayList <Model> changeToOddElement(){
        templist.clear();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2!=0){
                templist.add(modelList.get(i));
            }
        }
        return templist;
    }

    public ArrayList <Model> changeToAllElement(){
        templist.clear();
        templist.addAll(modelList);
        return templist;
    }


}
