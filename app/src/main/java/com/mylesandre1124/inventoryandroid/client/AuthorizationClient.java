package com.mylesandre1124.inventoryandroid.client;

import com.mylesandre1124.inventoryandroid.models.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.io.IOException;

public class AuthorizationClient extends Client{

    private EndpointAuthorizationInterface client;

    public AuthorizationClient() {
        this.client = retrofit.create(AuthorizationClient.EndpointAuthorizationInterface.class);
    }

    public EndpointAuthorizationInterface getClient() {
        return client;
    }

    public interface EndpointAuthorizationInterface
    {
        @POST("Authorization")
        Call<String> authorizeUser(@Body Credentials credentials);

    }


    public static void main(String[] args) throws IOException {
        AuthorizationClient authorizationClient = new AuthorizationClient();
        Credentials credentials = new Credentials();
        credentials.setUsername("mylesandre1124");
        credentials.setPassword("megamacman1");
        Call<String> authorize = authorizationClient.getClient().authorizeUser(credentials);
        //System.out.println(authorize.execute().errorBody().string());
    }

}
