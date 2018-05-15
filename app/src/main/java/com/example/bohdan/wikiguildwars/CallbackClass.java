package com.example.bohdan.wikiguildwars;

import java.util.List;

/**
  Created by bohdan on 02.05.2018.
 */

public class CallbackClass {

    interface Callback {
        List<Model> callingBack(int number) throws InterruptedException;

        List<Model> callingBackSecondFr(Model i) throws InterruptedException;
    }

    public Callback callback;

    public void registerCallBack(Callback callback){
        this.callback = callback;
    }

    List<Model> loadNumberObject(int number) throws InterruptedException {
       // вызываем метод обратного вызова
       return callback.callingBack(number);
    }

    List<Model> sendNumberObject(Model i) throws InterruptedException {
        // вызываем метод обратного вызова
        return callback.callingBackSecondFr(i);
    }


}
