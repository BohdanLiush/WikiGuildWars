package com.example.bohdan.wikiguildwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Created by bohdan on 02.05.2018. */

public class NetworkManager  {

    public final String URL = "https://api.guildwars2.com/v2/";
    public List<Model> model = new ArrayList<>();
    public ArrayList<Model> modelList = new ArrayList<>();
    public ArrayList<Model> templist = new ArrayList<>();
    public Thread loadObjectThread, p, t;
    public String number = "";
    public Call<List<Model>> tanks;
    public int numberObj;
    public int count = 0;

    public void loadNumberFromMain(int number) throws InterruptedException {
        numberObj = number;
        getIDSFromWeb();
       /* NetworkManager networkManager = new NetworkManager();
        networkManager.execute();*/
    }

    public void getIDSFromWeb() throws InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModelApi tankApi = retrofit.create(ModelApi.class);
        //ExecutorService service = Executors.newCachedThreadPool();

        while (modelList.size() < numberObj) {
            tanks = tankApi.tanksInfo(getIdsLoop(numberObj - modelList.size()));  // перший варіант
            //tanks = tankApi.tanksInfo(String.valueOf(numberObj));  // перший варіант

            /*service.submit(new Runnable() {
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
            //service.shutdown();
            service.awaitTermination(4, TimeUnit.SECONDS);*/

            loadObjectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        model = tanks.execute().body();
                        if (model!=null) {
                            modelList.addAll(model);
                        }
                        System.out.println("Smotrim: ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            loadObjectThread.start();
            loadObjectThread.join();

        /*while (modelList.size() < numberObj) {
            tanks = tankApi.tanksInfo(getIdsLoop(numberObj - modelList.size()));

            tanks.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    model = response.body();
                    modelList.addAll(model);
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                }
            });


            // перший варіант

            @Override
            public void run() {
                try {
                    model = tanks.execute().body();
                    modelList.addAll(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/


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


   /* @Override
    protected Object doInBackground(Object[] objects) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ModelApi tankApi = retrofit.create(ModelApi.class);
            //ExecutorService service = Executors.newCachedThreadPool();

            while (modelList.size() < numberObj) {
                tanks = tankApi.tanksInfo(getIdsLoop(numberObj - modelList.size()));  // перший варіант
           *//* service.submit(new Runnable() {
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
            //service.shutdown();
            service.awaitTermination(4, TimeUnit.SECONDS);
*//*
                loadObjectThread = new Thread(new Runnable() {
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

        *//*while (modelList.size() < numberObj) {
            tanks = tankApi.tanksInfo(getIdsLoop(numberObj - modelList.size()));

            tanks.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    model = response.body();
                    modelList.addAll(model);
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                }
            });*//*


                // перший варіант

          *//*  @Override
            public void run() {
                try {
                    model = tanks.execute().body();
                    modelList.addAll(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*//*



            }
        return null;
        }*/

    }

