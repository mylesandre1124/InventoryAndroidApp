package com.mylesandre1124.inventoryandroid.client;

import com.mylesandre1124.inventoryandroid.models.Inventory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Myles on 10/19/17.
 */
public class InventoryClient extends Client{

    private EndpointInventoryInterface client;

    public InventoryClient() {
        this.client = retrofit.create(EndpointInventoryInterface.class);
    }

    public EndpointInventoryInterface getClient() {
        return client;
    }

    public ArrayList<Inventory> getAllInventory() {
        /*try {
            Response inventoryCall;
            Inventory inventory;
            client.getAllInventory().enqueue(new Callback<ArrayList<Inventory>>() {
                @Override
                public void onResponse(Call<ArrayList<Inventory>> call, Response<ArrayList<Inventory>> response) {
                    //inventory = response.body().get(0);
                }

                @Override
                public void onFailure(Call<ArrayList<Inventory>> call, Throwable t) {

                }
            });
            if(inventoryCall.code() != 200)
            {
                //throw new
            }
            System.out.println(inventoryCall.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client.getAllInventory().execute().body();*/
        return new ArrayList<>();
    }

    public Call<Inventory> getInventory(long barcode) {
        return client.getInventory(barcode);
    }

    public Call<Inventory> addInventory(String authToken, long barcode)
    {
        return client.addToInventory(authToken, barcode);
    }

    public Call<Inventory> subInventory(String authToken, long barcode)
    {
        return client.subFromInventory(authToken, barcode);
    }



    public interface EndpointInventoryInterface
    {
        @GET("Inventory")
        Call<ArrayList<Inventory>> getAllInventory();

        @GET("Inventory/{barcode}")
        Call<Inventory> getInventory(@Path("barcode") long barcode);

        @GET("Inventory/{barcode}/add")
        Call<Inventory> addToInventory(@Header("Authorization") String token, @Path("barcode") long barcode);

        @GET("Inventory/{barcode}/sub")
        Call<Inventory> subFromInventory(@Header("Authorization") String token, @Path("barcode") long barcode);

        @POST("Inventory")
        Call<Inventory> createInventory(@Body Inventory inventory);

        @PUT("Inventory/{barcode}")
        Call<Inventory> updateInventory(@Path("barcode") long barcode, @Body Inventory inventory);
    }

    public static void main(String[] args) throws IOException {
        InventoryClient client = new InventoryClient();
        Response<Inventory> response = client.getClient().getInventory(11241996).execute();
        System.out.println(response.errorBody().string());
    }

}
