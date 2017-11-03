package com.mylesandre1124.inventoryandroid;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.mylesandre1124.inventoryandroid.client.InventoryClient;
import com.mylesandre1124.inventoryandroid.database.AccessDatabase;
import com.mylesandre1124.inventoryandroid.database.Database;
import com.mylesandre1124.inventoryandroid.exceptions.AuthenticationExceptionHandler;
import com.mylesandre1124.inventoryandroid.exceptions.ItemAlreadyScannedException;
import com.mylesandre1124.inventoryandroid.exceptions.ItemAlreadyScannedExceptionHandler;
import com.mylesandre1124.inventoryandroid.models.Inventory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSpinnerItems();
        EditText barcode = (EditText) findViewById(R.id.barcode);
        barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.ACTION_DOWN)
                {
                    Log.i("AuthServe", ":" +textView.getText().toString() +":");
                    Long barcodeLong = Long.parseLong(textView.getText().toString());
                    editInventory(barcodeLong);
                }
                return true;
            }
        });
    }


    public void editInventory(Long barcode)
    {
        Spinner spinner = (Spinner) findViewById(R.id.changeSpinner);
        int spinnerPosition = spinner.getSelectedItemPosition();
        Log.i("AuthServe", spinnerPosition +"");
        switch (spinnerPosition)
        {
            case 0:
                addInventory(barcode);
                break;
            case 1:
                subInventory(barcode);
                break;

        }
    }

    public void setUIText(Inventory inventory)
    {

        EditText name = (EditText) findViewById(R.id.inventoryName);
        EditText vendor = (EditText) findViewById(R.id.inventoryVendor);
        EditText price = (EditText) findViewById(R.id.inventoryPrice);
        EditText count = (EditText) findViewById(R.id.inventoryCount);
        name.setText(inventory.getName());
        vendor.setText(inventory.getVendor());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String priceString = "$" + decimalFormat.format(inventory.getPrice());
        price.setText(priceString);
        count.setText(String.valueOf(inventory.getCount()));
    }


    public String getAuthorizationToken()
    {
        AccessDatabase accessDatabase = new AccessDatabase(MainActivity.this);
        return accessDatabase.getToken();
    }

    public void addInventory(Long barcode)
    {
        InventoryClient inventoryClient = new InventoryClient();
        Call<Inventory> addInventoryCall = inventoryClient.addInventory(getAuthorizationToken(), barcode);
        addInventoryCall.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                if(response.code() == 200)
                {
                    Inventory inventory = response.body();
                    setUIText(inventory);
                }
                else if(response.code() == 409)
                {
                    ItemAlreadyScannedExceptionHandler itemAlreadyScannedExceptionHandler = new ItemAlreadyScannedExceptionHandler(response);
                    AlertDialog.Builder builder = itemAlreadyScannedExceptionHandler.getBuilderDialog(MainActivity.this);
                    Log.i("AuthServe", "Broken1");
                    //builder.setPositiveButton()
                }
                else {
                    Log.i("AuthServe", "Broken " + response.code() + " Token: " + getAuthorizationToken());
                }
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {

            }
        });
    }

    public void subInventory(Long barcode)
    {
        InventoryClient inventoryClient = new InventoryClient();
        Call<Inventory> subInventoryCall = inventoryClient.subInventory(getAuthorizationToken(), barcode);
        subInventoryCall.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                InventoryResponseHandler inventoryResponseHandler = new InventoryResponseHandler(response, MainActivity.this);
                Inventory inventory = inventoryResponseHandler.handle();
                if(inventory != null)
                {
                    setUIText(inventory);
                }
            }
            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {

            }
        });
    }

    public void createSpinnerItems()
    {
        Spinner changeSpinner = (Spinner) findViewById(R.id.changeSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.inventoryChangeList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeSpinner.setAdapter(adapter);
    }

    public void enterButton(View view)
    {
        EditText barcode = (EditText) findViewById(R.id.barcode);
        Long barcodeLong = Long.parseLong(barcode.getText().toString());
        editInventory(barcodeLong);
    }

}
