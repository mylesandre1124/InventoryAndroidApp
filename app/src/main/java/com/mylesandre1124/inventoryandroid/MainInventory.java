package com.mylesandre1124.inventoryandroid;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mylesandre1124.inventoryandroid.client.InventoryClient;
import com.mylesandre1124.inventoryandroid.models.Inventory;
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


    public void getText(View view) throws IOException {
        InventoryClient client = new InventoryClient("", "");
        EditText barcode = (EditText)findViewById(R.id.barcode);
        Call call = client.getClient().getInventory(new Long(barcode.getText().toString()).longValue());
        call.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                if(response.code() == 200) {
                    TextView view1 = (TextView) findViewById(R.id.textView);
                    Inventory inventory = response.body();
                    view1.setText(inventory.getName());
                }
                else if(response.code() == 409)
                {
                    try {
                        CharSequence message = response.errorBody().string();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main_inventory), message, Snackbar.LENGTH_INDEFINITE);
                        snackbar.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                Toast toast = Toast.makeText(MainInventory.this, "There was a problem connecting to the server", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        //call(call);
    }

    public void updateUI(Call call)
    {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Inventory inventory = (Inventory) response.body();
                TextView view = (TextView) findViewById(R.id.textView);

                view.setText(inventory.getName());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast toast = Toast.makeText(MainInventory.this, "There was a problem", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
