package com.example.bohdan.wikiguildwars;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Serializable {

    private final String URL = "https://api.guildwars2.com/v2/";
    public List<Model> model;
    BaseAdapter baseAdapter;
    ArrayList<Model> modelList = new ArrayList<>();
    Thread s;
    //String number = "11,15,23,24,1,2,6,56,57,58,59,60,61,62,63,64,68,69,70";
    String number = "";
    int numberObj = 100;
    int count = 0;
    Call<List<Model>> tanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getTanksFromWeb();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.liste23);

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
                ImageView imageView = view.findViewById(R.id.imageView3);
                TextView name = view.findViewById(R.id.textView);
                TextView type = view.findViewById(R.id.textView3);
                TextView id = view.findViewById(R.id.textView4);
                TextView description = view.findViewById(R.id.textView2);

                for (int k = 0; k < modelList.size(); k++){

                    name.setText("Name: "+i+" "+modelList.get(i).getName());
                    type.setText("Type: "+modelList.get(i).getType());
                    id.setText("Id: " + modelList.get(i).getId());
                    description.setText("Description: "+modelList.get(i).getDescription());

                    Picasso.get().load(modelList.get(i).getIcon()).into(imageView);
                    System.out.println(modelList.size());
                }
                return view;
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                intent.putExtra("item", (Serializable) modelList.get(i));
                startActivity(intent);
            }
        });

        listView.setAdapter(baseAdapter);

        baseAdapter.notifyDataSetChanged(); // метод обновлення listview
        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    public void getTanksFromWeb() throws InterruptedException {

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

    public void deleteEvenElement(View view){
        ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2==0){
                templist.add(modelList.get(i));
            }
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();
    }

    public void deleteOddElement(View view){
        ArrayList<Model> templist = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++){
            if (modelList.get(i).getId()%2!=0){
                templist.add(modelList.get(i));
            }
        }
        modelList.removeAll(templist);
        baseAdapter.notifyDataSetChanged();
    }
}






