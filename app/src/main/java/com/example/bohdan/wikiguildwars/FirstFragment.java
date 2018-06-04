package com.example.bohdan.wikiguildwars;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Created by bohdan on 27.04.2018. */

public class FirstFragment extends Fragment implements Serializable {

    SeekBar seekBar;
    Spinner spinner;
    TextView textSeekbar;
    public int numberObj;
    public Button button;

    ArrayList<String> spinnerList = new ArrayList<>();
    public ArrayList<Model> modelList = new ArrayList<>();
    public ArrayList<Model> templist = new ArrayList<>();

    public  List<Model> model;
    public Model newModel;

    BaseAdapter baseAdapter;
    GridView gridView;
    SquareImageView squareImageView;
    AdapterNew adapterNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        container = (ViewGroup) inflater.inflate(R.layout.fragmentone, container, false);

        button = container.findViewById(R.id.button);
        seekBar = container.findViewById(R.id.seekBar);
        textSeekbar = container.findViewById(R.id.textView3);

        textSeekbar.setText("Progress: " + seekBar.getProgress() + " / " + seekBar.getMax());

        gridView = container.findViewById(R.id.liste23);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MainActivity activityHome = (MainActivity) view.getContext();
                CallbackClass callbacks = new CallbackClass();
                callbacks.registerCallBack(activityHome);

                try {
                    model = callbacks.loadNumberObject(numberObj);
                    //model = callbacks.sendIdObject(numberObj);
                    //newModel = callbacks.sendIdObject(numberObj);
                    modelList.addAll(model);
                    //templist.addAll(modelList);

                    if (adapterNew == null){
                        adapterNew = new AdapterNew(modelList);
                        gridView.setAdapter(adapterNew);
                        adapterNew.notifyDataSetChanged(); // метод обновлення listview
                        setRetainInstance(true);
                    }
                    /*adapterNew = new AdapterNew(model);

                    gridView.setAdapter(adapterNew);*/
                    //adapterNew.notifyDataSetChanged(); // метод обновлення listview
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
        spinnerList.clear();
        spinnerList.add("    Choice");
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

                /* if (position==0){
                    TemplistReturn();
                }*/
                if (position==1){
                    adapterNew = new AdapterNew(changeToAllElement());
                    gridView.setAdapter(adapterNew);
                    adapterNew.notifyDataSetChanged(); // метод обновлення listview
                }

                if (position==2){
                    adapterNew = new AdapterNew(changeToEvenElement());
                    gridView.setAdapter(adapterNew);
                    adapterNew.notifyDataSetChanged(); // метод обновлення listview
                }
                 if (position==3){
                     adapterNew = new AdapterNew(changeToOddElement());
                     gridView.setAdapter(adapterNew);
                     adapterNew.notifyDataSetChanged(); // метод обновлення listview
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        /*baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listModel.size();
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

                for (int k = 0; k < listModel.size(); k++){
                    id.setText("Id: " + listModel.get(i).getId()+ " N: " + i);
                    //type.setText("Type: "+modelList.get(i).getType());
                    //name.setText("Name: " + modelList.get(i).getName());
                    description.setText("Description: " + listModel.get(i).getDescription());
                    Picasso.get().load(listModel.get(i).getIcon()).into(imageView);
                    System.out.println(listModel.size());
                }
                return view;
            }
        };*/
        return container;
    }

        /*public List<Model> changeToEvenElement(){

        ArrayList<Model> templist = new ArrayList<>();

        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2==0){
                templist.add(modelList.get(i));
            }
        }
        // modelList.removeAll(templist);
        // baseAdapter.notifyDataSetChanged();
        return templist;
    }*/

    public List<Model> changeToEvenElement(){
        //ArrayList<Model> templist = new ArrayList<>();
        templist.clear();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2==0){
                templist.add(modelList.get(i));
            }
        }
        return templist;
    }

    public List<Model> changeToOddElement(){
       // ArrayList<Model> templist = new ArrayList<>();
        templist.clear();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2!=0){
                templist.add(modelList.get(i));
            }
        }
        return templist;
    }

    public List<Model> changeToAllElement(){
        templist.clear();
        //ArrayList<Model> templist = new ArrayList<>(modelList);
        /*for (int i = 0; i < modelList.size(); i++) {
            templist.add(modelList.get(i));
        }*/
        templist.addAll(modelList);
        return templist;
    }
}
