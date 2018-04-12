package com.example.bohdan.wikiguildwars;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
  Created by bohdan on 03.04.2018.
 */

public interface ModelApi {

    //@GET("items?ids=1,2,3,4,5,6,7,8,9,10")
    @GET("items")
    Call<List<Model>> tanksInfo(@Query("ids") String number);

}
