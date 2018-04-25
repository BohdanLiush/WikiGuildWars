package com.example.bohdan.wikiguildwars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Serializable {

    private Context context;
    private final String URL = "https://api.guildwars2.com/v2/";
    public List<Model> model;
    BaseAdapter baseAdapter;
    public ArrayList<Model> modelList = new ArrayList<>();
    ArrayList<String> spinnerList = new ArrayList<>();
    ArrayList<Model> spinnerModelList = new ArrayList<>();
    public ArrayList<Model> templist = new ArrayList<>();

    Thread s, p, t;
    //String number = "11,15,23,24,1,2,6,56,57,58,59,60,61,62,63,64,68,69,70";
    String number = "";
    int  numberObj;

    int count = 0;
    Call<List<Model>> tanks;
    TextView textSeekbar;
    SeekBar seekBar;
    GridView gridView;
    Spinner spinner;
    SquareImageView squareImageView;
    AdapterNew adapterNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textSeekbar = findViewById(R.id.textView3);
        textSeekbar.setText("Progress: " + seekBar.getProgress() + " / " + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textSeekbar.setText(String.valueOf("Progress: " + progress + " / " + seekBar.getMax()));
                numberObj = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

       /* spinnerModelList.addAll(EvenElement());
        spinnerModelList.addAll(OddElement());*/

        //!!!...spinner
        spinnerList.add("    John");
        spinnerList.add("    All");
        spinnerList.add("    Even");
        spinnerList.add("    Odd");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setPromptId(R.string.country);
        // spinner.setSelection(0,false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position==0){
                  TemplistReturn();

                }
                if (position==1){
                    deleteAllElement(view);
                }
                if (position==2){
                   deleteEvenElement();

                }
                if (position==3){
                    deleteOddElement(view);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // listView.setAdapter(baseAdapter);
        // listView.invalidateViews();
        // listView.refreshDrawableState();
    }

    public void getTanksFromWeb(View view) throws InterruptedException {

        gridView = findViewById(R.id.liste23);
        adapterNew = new AdapterNew(MainActivity.this);
        squareImageView = new SquareImageView(MainActivity.this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                intent.putExtra("item", (Serializable) modelList.get(i));
                startActivity(intent);
            }
        });

        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return modelList.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                view = getLayoutInflater().inflate(R.layout.custom, viewGroup, false);
                ImageView imageView = view.findViewById(R.id.imageView);
                TextView id = view.findViewById(R.id.textView);
                //TextView type = view.findViewById(R.id.textView3);
                //TextView name = view.findViewById(R.id.textView4);
                TextView description = view.findViewById(R.id.textView2);

                for (int k = 0; k < modelList.size(); k++){
                    id.setText("Id: " + modelList.get(i).getId()+ " N: " + i);
                    //type.setText("Type: "+modelList.get(i).getType());
                    //name.setText("Name: " + modelList.get(i).getName());
                    description.setText("Description: " + modelList.get(i).getDescription());

                    Picasso.get().load(modelList.get(i).getIcon()).into(imageView);
                    System.out.println(modelList.size());
                }

                return view;
            }
        };

        gridView.setAdapter(baseAdapter);
        baseAdapter.notifyDataSetChanged(); // метод обновлення listview

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModelApi tankApi = retrofit.create(ModelApi.class);

        while (modelList.size()<numberObj){
            tanks = tankApi.tanksInfo(getIdsLoop(numberObj-modelList.size()));  // перший варіант

            s = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        model = tanks.execute().body();
                        modelList.addAll(model);
                        //templist.addAll(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            s.start();
            s.join();
        }
    }

    public String getIdsLoop(int n) { // якшо різниця була 20 об.
        number = "";  // обнулення строки після 100 або іншого числа, тобто строка буде іти від 100, 101 як нам і треба
        for (int i = 0; i < n; i++) {
                number = number + count + ","; //101 do 120 // другий прохід
                count++;  //100
            }
        System.out.println("count " + count);
        return number;
    }

    public void deleteEvenElement(){
        //ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2==0){
                templist.add(modelList.get(i));
            }
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();
        //return templist;
    }

    public void deleteOddElement(View view){
        //ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2!=0){
                templist.add(modelList.get(i));
            }
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();
        //return templist;
    }

    public void deleteAllElement(View view){
        //ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++) {
            templist.add(modelList.get(i));
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();

//        return templist;
    }

   /* public List<Model> EvenElement() throws InterruptedException {
      final   ArrayList<Model> templist = new ArrayList<>();
        p = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < modelList.size(); i++){
                    if (modelList.get(i).getId()%2==0){
                        templist.add(modelList.get(i));
                    }
                }
                modelList.removeAll(templist);
            }
        });
        p.start();
        p.join();
        //baseAdapter.notifyDataSetChanged();
        return modelList;
    }

    public List<Model> OddElement(){
        ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2!=0){
                templist.add(modelList.get(i));
            }

        }
        //modelList.removeAll(templist);
        //baseAdapter.notifyDataSetChanged();
        return templist;
    }
*/
    public List<Model> TemplistReturn() {
        for (int i = 0; i < modelList.size(); i++) {
            templist.add(modelList.get(i));
        }
        return templist;

    }
}







