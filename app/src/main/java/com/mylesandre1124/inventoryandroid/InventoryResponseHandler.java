package com.mylesandre1124.inventoryandroid;

import android.app.AlertDialog;
import android.content.Context;
import com.mylesandre1124.inventoryandroid.exceptions.ItemAlreadyScannedExceptionHandler;
import com.mylesandre1124.inventoryandroid.models.Inventory;
import retrofit2.Response;

public class InventoryResponseHandler {

    private Response response;
    private Context context;

    public InventoryResponseHandler(Response response, Context context) {
        this.response = response;
        this.context = context;
    }

    public Inventory handle()
    {
        if(response.code() == 200)
        {
            Inventory inventory = (Inventory) response.body();
            return inventory;
        }
        else if (response.code() == 409)
        {
            ItemAlreadyScannedExceptionHandler itemAlreadyScannedExceptionHandler = new ItemAlreadyScannedExceptionHandler(response);
            AlertDialog.Builder builder = itemAlreadyScannedExceptionHandler.getBuilderDialog(context);
            builder.create();
            return null;
        }
        else {
            return null;
        }
    }
}
