package com.mylesandre1124.inventoryandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mylesandre1124.inventoryandroid.client.AuthorizationClient;
import com.mylesandre1124.inventoryandroid.client.InventoryClient;
import com.mylesandre1124.inventoryandroid.models.Credentials;
import com.mylesandre1124.inventoryandroid.models.Inventory;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class MainInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventory);
    }

    public void login(View view) throws IOException {
        /*AuthorizationClient authorizationClient = new AuthorizationClient();
        Credentials credentials = new Credentials();
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        credentials.setUsername(usernameField.getText().toString());
        credentials.setPassword(passwordField.getText().toString());
        Call<String> loginCall = authorizationClient.getClient().authorizeUser(credentials);*/
        InventoryClient inventoryClient = new InventoryClient();
        Call<Inventory> loginCall = inventoryClient.getClient().getInventory(1L);
        loginCall.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                if(response.code() == 200) {
                    //String authToken = response.body();
                    //Toast.makeText(MainInventory.this, authToken, Toast.LENGTH_LONG).show();
                }
                else if (response.code() == 409)
                {
                    convertToException(response);
                    /*String errorMessage = response.errorBody().string();
                    Toast unauthorized = Toast.makeText(MainInventory.this, errorMessage, Toast.LENGTH_LONG);
                    unauthorized.show();*/

                }
            }
            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                Toast.makeText(MainInventory.this, "Could not connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }




    public void convertToException(Response response)
    {
        try {
            int errorCode = response.code();
            String errorMessage = response.errorBody().string();
            Headers headers = response.headers();
            headers.get()
            if(errorCode == 409)
            {
                AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(MainInventory.this);
                errorDialogBuilder.setMessage(errorMessage + "Are you sure you want to scan this item?");
                errorDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainInventory.this, "Yes", Toast.LENGTH_LONG).show();

                    }
                });
                errorDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainInventory.this, "No", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog errorDialog = errorDialogBuilder.create();
                errorDialog.show();
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
                Toast toast = Toast.makeText(MainInventory.this, "There was a problem connecting to the server", Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(MainInventory.this, "There was a problem", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
