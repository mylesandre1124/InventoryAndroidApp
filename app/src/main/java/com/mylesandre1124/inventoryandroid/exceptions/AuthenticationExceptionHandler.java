package com.mylesandre1124.inventoryandroid.exceptions;

import android.content.Context;
import android.widget.Toast;
import okhttp3.Headers;
import retrofit2.Response;

import java.io.IOException;

public class AuthenticationExceptionHandler {

    private AuthenticationException authenticationException;

    public AuthenticationExceptionHandler(Response response) {
        try {
            this.authenticationException = new AuthenticationException(response.errorBody().string() + "Please try again.");
        }
        catch (IOException ex)
        {

        }
    }


    public void createExceptionToast(Context applicationContext)
    {
        Toast exceptionToast = Toast.makeText(applicationContext, authenticationException.getMessage(),Toast.LENGTH_LONG);
        exceptionToast.show();
    }

}
