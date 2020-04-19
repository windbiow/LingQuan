package com.example.tablehosttest.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private Retrofit retrofit;

    public static final RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance(){
        return ourInstance;
    }

    private RetrofitManager(){
        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
