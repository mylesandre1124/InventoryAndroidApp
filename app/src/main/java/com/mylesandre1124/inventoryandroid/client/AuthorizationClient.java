package com.mylesandre1124.inventoryandroid.client;

import retrofit2.Call;
import retrofit2.http.GET;

public class AuthorizationClient extends Client{

    public AuthorizationClient(String username, String password) {
        super(username, password);
    }



}
