package com.mylesandre1124.inventoryandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.mylesandre1124.inventoryandroid.client.AuthorizationClient;
import com.mylesandre1124.inventoryandroid.database.AccessDatabase;
import com.mylesandre1124.inventoryandroid.exceptions.AuthenticationExceptionHandler;
import com.mylesandre1124.inventoryandroid.models.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class LoginInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inventory);
        if(checkIfTokenExists()) {
            Intent mainIntent = new Intent(LoginInventory.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    public boolean checkIfTokenExists()
    {
        AccessDatabase database = new AccessDatabase(LoginInventory.this);
        return database.checkIfLoggedIn();
    }

    public boolean checkIfLoggedIn()
    {
        if (checkIfTokenExists())
        {

        }
    }

    public void login(View view) throws IOException {
        AuthorizationClient authorizationClient = new AuthorizationClient();
        final Credentials credentials = getCredentials();
        Call<String> loginCall = authorizationClient.getClient().authorizeUser(credentials);
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200) {
                    AccessDatabase database = new AccessDatabase(LoginInventory.this);
                    String authToken = response.body();
                    database.createRecords(credentials.getUsername(), authToken);
                    if(checkIfTokenExists()) {
                        startMainActivityOnSuccess();
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

    public Credentials getCredentials() {
        Credentials credentials = new Credentials();
        EditText usernameField = (EditText) findViewById(R.id.usernameField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        credentials.setUsername(usernameField.getText().toString());
        credentials.setPassword(passwordField.getText().toString());
        return credentials;
    }


    public void startMainActivityOnSuccess()
    {
        Intent mainIntent = new Intent(LoginInventory.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    public void logout(View view)
    {
        AccessDatabase database = new AccessDatabase(LoginInventory.this);
        database.logout();
        Toast.makeText(LoginInventory.this, checkIfTokenExists() +"", Toast.LENGTH_LONG).show();
    }



    public void showPassword(View view)
    {
        RadioButton showPasswordRadioButton = (RadioButton) view;
        if(showPasswordRadioButton.isChecked())
        {
            EditText passwordField = (EditText)findViewById(R.id.passwordField);
            //passwordField.set
        }
    }



}
