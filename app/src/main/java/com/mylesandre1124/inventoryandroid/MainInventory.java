package com.mylesandre1124.inventoryandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.mylesandre1124.inventoryandroid.client.InventoryClient;
import com.mylesandre1124.inventoryandroid.models.Inventory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainInventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventory);
    }

    TextView view = (TextView)findViewById(R.id.textView);

    public void getText()
    {
        InventoryClient client = new InventoryClient("", "");
        Call<Inventory> call = client.getClient().getInventory(128L);
        call(call);
    }

    public void call(Call call)
    {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
