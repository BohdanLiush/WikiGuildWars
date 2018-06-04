package com.example.bohdan.wikiguildwars;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

public class MainActivity extends AppCompatActivity implements Serializable, CallbackClass.Callback {

    // private final String URL = "https://api.guildwars2.com/v2/";
    // public List<Model> listModel;
    // BaseAdapter baseAdapter;
    // public ArrayList<Model> modelList = new ArrayList<>();
    // public ArrayList<Model> templist = new ArrayList<>();
    //Thread s, p, t;
    //String number = "";
    public int numberObj;
    //    int count = 0;
    /* Call<List<Model>> tanks;
    GridView gridView;
    SquareImageView squareImageView;
    AdapterNew adapterNew;*/

    FirstFragment firstFragment = new FirstFragment();
    NetworkManager networkManager = new NetworkManager();
    SecondFragment secondFragment = new SecondFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();
    }

    private void loadFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, firstFragment);
        fragmentTransaction.commit();
    }

    @Override
    public ArrayList<Model> callingBack(int numbers) throws InterruptedException {
        System.out.println("number " + numberObj);
        networkManager.loadNumberFromMain(numbers);
        System.out.println("network number:  " + networkManager.numberObj);
        networkManager.loadObjectThread.join();
        return networkManager.modelList;
    }

    @Override
    public void callingBackSecondFr(Model i) throws InterruptedException {

        Bundle arguments = new Bundle();
        arguments.putSerializable("listModel", i);
        secondFragment.setArguments(arguments);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(firstFragment)
                .add(R.id.frameLayout, secondFragment,"home").addToBackStack("home").commit();

        //fragmentTransaction.replace(R.id.frameLayout, secondFragment);
        //fragmentTransaction.addToBackStack("home");
        //fragmentTransaction.commit();
        //return (List<Model>) i;
    }

    @Override
    public Model callingBack_2_singleObject(int number) throws InterruptedException {

        networkManager.loadIdObjectFromMain(number);

        Bundle arguments = new Bundle();
        arguments.putSerializable("model", networkManager.oneModel);
        secondFragment.setArguments(arguments);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        /*fragmentTransaction.remove(firstFragment)
                .add(R.id.frameLayout, secondFragment,"home").addToBackStack("home").commit();*/

        fragmentTransaction.replace(R.id.frameLayout, secondFragment).addToBackStack("home").commit();

        System.out.println("network number:  " + networkManager.numberObj);
        networkManager.getIdOneObjectThread.join();
        return networkManager.oneModel;
    }

    @Override
    public void callingBackButton() {

       /* Fragment home = getFragmentManager().findFragmentByTag("home");
        if (home instanceof SecondFragment && home.isVisible()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, firstFragment, "home");
            ft.commit();
        }*/
       // this.getFragmentManager().popBackStackImmediate();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
           // finish();
        //this.getFragmentManager().popBackStack();
    }

    /*public void getIDSFromWeb(View view) throws InterruptedException {

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
            tanks = tankApi.idsInfo(getIdsLoop(numberObj-modelList.size()));  // перший варіант
            s = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        listModel = tanks.execute().body();
                        modelList.addAll(listModel);
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
    }*/

    /*public void changeToEvenElement(){
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

    public void changeToOddElement(View view){
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

    public void changeToAllElement(View view){
        //ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++) {
            templist.add(modelList.get(i));
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();
        // return templist;
    }

    public List<Model> TemplistReturn() {
        for (int i = 0; i < modelList.size(); i++) {
            templist.add(modelList.get(i));
        }
        return templist;
    }*/
    }








