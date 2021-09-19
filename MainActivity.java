package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import backend.*;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<MenuItem> cart = new ArrayList<>();
    Order currentCart = new Order();
    StoreOrders storeOrders = new StoreOrders();
    public static final String EXTRA_CART = "cart";
    public static final int donutViewCode = 1;
    public static final int cafeViewCode = 2;
    public static final int orderViewCode = 3;
    public static final int storeViewCode = 4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * Navigates to order donut view
     *
     * @param view View
     */
    public void shopDonut(View view){
        Intent intent = new Intent(this,DonutActivity.class);
        intent.putExtra("currentCart",currentCart);
        intent.putExtra("currentStoreOrders",storeOrders);
        startActivityForResult(intent,donutViewCode);
    }

    /**
     * Navigates to order cafe view
     *
     * @param view View
     */
    public void shopCafe(View view){
        Intent intent = new Intent(this,CafeActivity.class);
        intent.putExtra("currentCart",currentCart);
        intent.putExtra("currentStoreOrders",storeOrders);
        startActivityForResult(intent,cafeViewCode);
    }

    /**
     * Navigates to order view
     *
     * @param view View
     */
    public void orderView(View view){

        Intent intent = new Intent(this,OrderActivity.class);
        intent.putExtra("currentCart",currentCart);
        intent.putExtra("currentStoreOrders",storeOrders);
        startActivityForResult(intent,orderViewCode);
    }

    /**
     * Navigates to store orders view
     *
     * @param view View
     */
    public void storeView(View view){
        Intent intent = new Intent(this,StoreOrderActivity.class);
        intent.putExtra("currentCart",currentCart);
        intent.putExtra("currentStoreOrders",storeOrders);
        startActivityForResult(intent,storeViewCode);
    }


    /**
     * This method retrieve the values back from previous activity.
     *
     * @param requestCode integer code be requested
     * @param resultCode integer code indicating if data is sent back successfully or not
     * @param data Intent for retrieve data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == donutViewCode) {
            if(resultCode == RESULT_OK) {
                currentCart = (Order)data.getSerializableExtra("order");
                storeOrders = (StoreOrders)data.getSerializableExtra("returnStoreOrders");
            }
        }
        if (requestCode == cafeViewCode) {
            if(resultCode == RESULT_OK) {
                currentCart = (Order)data.getSerializableExtra("cafeToMain_cart");
                storeOrders = (StoreOrders)data.getSerializableExtra("cafeToMain_storeOrders");
            }
        }
        if (requestCode ==  orderViewCode) {
            if(resultCode == RESULT_OK) {
                currentCart = (Order)data.getSerializableExtra("orderToMain_cart");
                storeOrders = (StoreOrders)data.getSerializableExtra("orderToMain_storeOrders");
            }
        }
        if (requestCode == storeViewCode) {
            if(resultCode == RESULT_OK) {
                currentCart = (Order)data.getSerializableExtra("storeToMain_cart");
                storeOrders = (StoreOrders)data.getSerializableExtra("storeToMain_storeOrders");
            }
        }
    }


}