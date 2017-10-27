package com.mylesandre1124.inventoryandroid.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Myles on 10/21/17.
 */
public class Credentials implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
