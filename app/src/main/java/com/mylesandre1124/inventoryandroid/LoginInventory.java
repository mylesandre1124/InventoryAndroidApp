package com.mylesandre1124.inventoryandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mylesandre1124.inventoryandroid.client.AuthorizationClient;
import com.mylesandre1124.inventoryandroid.database.AccessDatabase;
import com.mylesandre1124.inventoryandroid.exceptions.AuthenticationExceptionHandler;
import com.mylesandre1124.inventoryandroid.models.Credentials;
import com.mylesandre1124.inventoryandroid.models.Inventory;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class LoginInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inventory);
        if(checkIfLoggedIn()) {
            Intent mainIntent = new Intent(LoginInventory.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    public boolean checkIfLoggedIn()
    {
        AccessDatabase database = new AccessDatabase(LoginInventory.this);
        return database.checkIfLoggedIn();
    }

    public void login(View view) throws IOException {
        AuthorizationClient authorizationClient = new AuthorizationClient();
        final Credentials credentials = new Credentials();
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        credentials.setUsername(usernameField.getText().toString());
        credentials.setPassword(passwordField.getText().toString());
        Call<String> loginCall = authorizationClient.getClient().authorizeUser(credentials);
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200) {
                    AccessDatabase database = new AccessDatabase(LoginInventory.this);
                    String authToken = response.body();
                    Log.i("AuthServe", "Before: " + authToken);
                    database.createRecords(credentials.getUsername(), authToken);
                    Log.i("AuthServe", "After: " + database.getToken());
                    if(checkIfLoggedIn()) {
                        Intent mainIntent = new Intent(LoginInventory.this, MainActivity.class);
                        startActivity(mainIntent);
                    }

                }
                else if (response.code() == 401)
                {
                    AuthenticationExceptionHandler exceptionHandler = new AuthenticationExceptionHandler(response);
                    exceptionHandler.createExceptionToast(LoginInventory.this);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginInventory.this, "Could not connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void logout(View view)
    {
        AccessDatabase database = new AccessDatabase(LoginInventory.this);
        database.logout();
        Toast.makeText(LoginInventory.this, checkIfLoggedIn() +"", Toast.LENGTH_LONG).show();
    }

    public AccessDatabase cr()
    {
        AccessDatabase database = new AccessDatabase(LoginInventory.this);
        return database;
    }




    public void convertToException(Response response)
    {
        try {
            int errorCode = response.code();
            String errorMessage = response.errorBody().string();
            Headers headers = response.headers();
            headers.get("");
            if(errorCode == 409)
            {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getText(View view) throws IOException {
        /*InventoryClient client = new InventoryClient();
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        EditText barcode = (EditText)findViewById(R.id.barcode);
        Call call = client.getClient().getInventory(new Long(barcode.getText().toString()).longValue());
        call.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                TextView view1 = (TextView) findViewById(R.id.textView);
                Inventory inventory = response.body();
                view1.setText(inventory.getName());
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                Toast toast = Toast.makeText(LoginInventory.this, "There was a problem connecting to the server", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        //call(call);*/
    }

    public void updateUI(Call call)
    {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Inventory inventory = (Inventory) response.body();
                //TextView view = (TextView) findViewById(R.id.textView);

                //view.setText(inventory.getName());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast toast = Toast.makeText(LoginInventory.this, "There was a problem", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
