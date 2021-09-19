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
import androidx.recyclerview.widget.RecyclerView;

import backend.*;

public class OrderActivity extends AppCompatActivity {

    ListView cartDisplay;
    TextView subtotal,tax,total;
    Order cart;
    Button placeOrderButton;
    ArrayAdapter<MenuItem> adapter ;
    StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        cartDisplay = (ListView)findViewById(R.id.cartDisplay);
        subtotal = (TextView)findViewById(R.id.subtotal);
        tax = (TextView)findViewById(R.id.tax);
        total = (TextView)findViewById(R.id.total);
        placeOrderButton = (Button)findViewById(R.id.placeOrderButton);

        cart = (Order)getIntent().getSerializableExtra("currentCart");
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("storeOrders");


        adapter = new ArrayAdapter<MenuItem>(this,android.R.layout.simple_list_item_1,cart.getList());
        cartDisplay.setAdapter(adapter);
        calculate();

       cartDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MenuItem item = (MenuItem) parent.getItemAtPosition(position);
               removeItem(item);
           }
        });

    }

    /**
     * This method adds the order to storeOrders,and clear current order
     *
     * @param v View
     */
    public void placeOrder(View v){

        if(cart.isEmpty()){
            Toast.makeText(this,R.string.toast_empty_cart,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("currentStoreOrders");
        storeOrders.add(cart);
        cart = new Order();
        intent.putExtra("orderToMain_cart",new Order());
        intent.putExtra("orderToMain_storeOrders",storeOrders);
        setResult(RESULT_OK, intent);
        Toast.makeText(this,R.string.toast_place_order,Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * This method is to remove item from the list and show the alert message
     *
     * @param item the item to be removed
     */
    private void removeItem(MenuItem item){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.remove_alert_title).setMessage(R.string.remove_alert_msg);
        alert.setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cart.remove(item);
                adapter.remove(item);
                calculate();
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
     * This method shows the toast
     */
    private void showToast(){
        Toast.makeText(this,R.string.toast_delete_item,Toast.LENGTH_SHORT).show();
    }

    /**
     * This method calculates the subtotal and tax and total
     */
    private void calculate(){
        double temp = cart.getSubTotal();
        subtotal.setText(String.format("$%.2f",temp));
        tax.setText(String.format("$%.2f",temp * Order.taxRate));
        total.setText(String.format("$%.2f",temp * (1+Order.taxRate)));
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
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("currentStoreOrders");
        intent.putExtra("orderToMain_cart",cart);
        intent.putExtra("orderToMain_storeOrders",storeOrders);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }
}