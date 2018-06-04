package com.example.bohdan.wikiguildwars;

import java.util.List;

/** Created by bohdan on 02.05.2018. */

public class CallbackClass {

    interface Callback {

        List<Model> callingBack(int number) throws InterruptedException;

        void callingBackSecondFr(Model i) throws InterruptedException;

        Model callingBack_2_singleObject(int number) throws InterruptedException;

        void callingBackButton();
    }

    public Callback callback;

    public void registerCallBack(Callback callback){
        this.callback = callback;
    }

    List<Model> loadNumberObject(int number) throws InterruptedException {
       // вызываем метод обратного вызова
       return callback.callingBack(number);
    }

    void sendNumberObject(Model i) throws InterruptedException {
        // вызываем метод обратного вызова
        callback.callingBackSecondFr(i);
    }

    Model sendIdObject(int number) throws InterruptedException {
      return callback.callingBack_2_singleObject(number);
    }

    void buttonBack() {
        callback.callingBackButton();
    }


}
