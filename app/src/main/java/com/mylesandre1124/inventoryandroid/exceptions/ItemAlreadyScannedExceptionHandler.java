package com.mylesandre1124.inventoryandroid.exceptions;

import android.app.AlertDialog;
import android.content.Context;
import okhttp3.Headers;
import retrofit2.Response;

public class ItemAlreadyScannedExceptionHandler {

    private ItemAlreadyScannedException itemAlreadyScannedException;

    public ItemAlreadyScannedExceptionHandler(Response response) {
        createException(response);
    }

    public ItemAlreadyScannedException createException(Response response){
        Headers exceptionHeaders = response.headers();
        int count = Integer.parseInt(exceptionHeaders.get("count"));
        String name = exceptionHeaders.get("name");
        itemAlreadyScannedException = new ItemAlreadyScannedException(name, count);
        return this.itemAlreadyScannedException;
    }


    public AlertDialog.Builder getBuilderDialog(final Context applicationContext)
    {
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(applicationContext);
        errorDialogBuilder.setMessage(itemAlreadyScannedException.getMessage() + "Are you sure you want to scan this item?");
        return errorDialogBuilder;
    }
}
