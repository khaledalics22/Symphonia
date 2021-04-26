package com.example.symphonia.Service;

import com.example.symphonia.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static RetrofitSingleton instance = null;
    private RetrofitApi retrofitApi;

    private RetrofitSingleton() {
        buildRetrofit();
    }

    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            instance = new RetrofitSingleton();
        }

        return instance;
    }

    private void buildRetrofit() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.retrofitApi = retrofit.create(RetrofitApi.class);
    }

    public RetrofitApi getRetrofitApi() {
        return this.retrofitApi;
    }
}
