package com.example.project5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import backend.MenuItem;
import backend.Order;
import backend.StoreOrders;

public class StoreOrderActivity extends AppCompatActivity {


    ListView ordersDisplay;
    Order cart;
    ArrayAdapter<Order> adapter ;
    StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_layout);

        ordersDisplay = (ListView)findViewById(R.id.ordersDisplay);
        cart = (Order)getIntent().getSerializableExtra("currentCart");
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("currentStoreOrders");

        adapter = new ArrayAdapter<Order>(this,android.R.layout.simple_list_item_1,storeOrders.getOrders());
        ordersDisplay.setAdapter(adapter);

        ordersDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getItemAtPosition(position);
                cancelOrder(order);
            }
        });

    }

    /**
     * This method will try to cancel the selected order and show the alert message
     *
     * @param order the order to be canceled
     */
    private void cancelOrder(Order order){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.cancel_alert_title).setMessage(R.string.cancel_alert_msg);
        alert.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.remove(order);
                storeOrders.remove(order);
                showToast();
            }
        });

        alert.setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * This methods show the toast
     */
    private void showToast(){
        Toast.makeText(this,R.string.toast_cancel_order,Toast.LENGTH_SHORT).show();
    }

    /**
     * This method overrides the up-button in action bar,
     * it will navigate to original main activity
     *
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        intent.putExtra("storeToMain_cart",cart);
        intent.putExtra("storeToMain_storeOrders",this.storeOrders);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }
}