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
    public List<Model> listModel = new ArrayList<>();
    public Model oneModel;

    public ArrayList<Model> modelList = new ArrayList<>();
    public ArrayList<Model> templist = new ArrayList<>();

    public Thread loadObjectThread, p, getIdOneObjectThread;

    public String number = "";
    public int numberObj;
    public int count = 0;

    public Call<List<Model>> idsCall;
    public Call<Model> idSingleCall;

    public void loadNumberFromMain(int number) throws InterruptedException {
        numberObj = number;
        getIDSFromWeb();
    }

    public void loadIdObjectFromMain(int number) throws InterruptedException {
        numberObj = number;
        getIdOneObject();
    }

    public void getIdOneObject() throws InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ModelApi idApi = retrofit.create(ModelApi.class);

        getIdOneObjectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                idSingleCall = idApi.idInfoSingleObject(String.valueOf(numberObj));
                try {
                    oneModel = idSingleCall.execute().body();
                   /* if (listModel != null) {
                        modelList.addAll(listModel);
                    }*/
                    System.out.println("Smotrim: ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        getIdOneObjectThread.start();
        getIdOneObjectThread.join();

    }

    public void getIDSFromWeb() throws InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModelApi idsApi = retrofit.create(ModelApi.class);
        //ExecutorService service = Executors.newCachedThreadPool();

        while (modelList.size() < numberObj) {

            idsCall = idsApi.idsInfo(getIdsLoop(numberObj - modelList.size()));  // перший варіант

            /*service.submit(new Runnable() {
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
            //service.shutdown();
            service.awaitTermination(4, TimeUnit.SECONDS);*/

            loadObjectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        listModel = idsCall.execute().body();
                        if (listModel != null) {
                            modelList.addAll(listModel);
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
            tanks = idsApi.idsInfo(getIdsLoop(numberObj - modelList.size()));

            tanks.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    listModel = response.body();
                    modelList.addAll(listModel);
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                }
            });
            // перший варіант
            @Override
            public void run() {
                try {
                    listModel = tanks.execute().body();
                    modelList.addAll(listModel);
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
                tanks = tankApi.idsInfo(getIdsLoop(numberObj - modelList.size()));  // перший варіант
           *//* service.submit(new Runnable() {
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
            //service.shutdown();
            service.awaitTermination(4, TimeUnit.SECONDS);
*//*
                loadObjectThread = new Thread(new Runnable() {
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

        *//*while (modelList.size() < numberObj) {
            tanks = tankApi.idsInfo(getIdsLoop(numberObj - modelList.size()));

            tanks.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    listModel = response.body();
                    modelList.addAll(listModel);
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                }
            });*//*


                // перший варіант

          *//*  @Override
            public void run() {
                try {
                    listModel = tanks.execute().body();
                    modelList.addAll(listModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*//*



            }
        return null;
        }*/

    }

