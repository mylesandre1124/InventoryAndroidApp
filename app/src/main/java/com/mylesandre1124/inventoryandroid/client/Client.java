package com.mylesandre1124.inventoryandroid.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Myles on 10/19/17.
 */
public class Client {

    private static final String BASE_URL = "http://localhost:8080/Inventory/";
    Retrofit retrofit;

    public Client(String username, String password) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        OkHttpClient authorizationClient = new OkHttpClient.Builder()
                .addInterceptor(new Authorization(username, password))
                .build();
        this.retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }
}
