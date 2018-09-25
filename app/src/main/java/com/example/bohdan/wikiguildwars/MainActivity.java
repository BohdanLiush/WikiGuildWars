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

    public int numberObj;

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
        // System.out.println("number " + numberObj);

        networkManager.loadNumberFromMain(numbers);
        System.out.println("network number:  " + networkManager.numberObj);
        //networkManager.loadObjectThread.join();   // лишній код, робить і без нього
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

        //System.out.println("network number:  " + networkManager.numberObj);
        //networkManager.getIdOneObjectThread.join();
        return networkManager.oneModel;
    }

    @Override
    public void callingBackButton() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}








