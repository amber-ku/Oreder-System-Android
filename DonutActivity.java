package com.example.project5;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import backend.*;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DonutActivity extends AppCompatActivity{


    ArrayAdapter<String> adapter;
    Button addButton;
    int[] allFlavorsQty = new int[DonutType.numOfFlavors];
    String[] allFlavorArr;
    ArrayList<Spinner> spinnerArr = new ArrayList<Spinner>();
    Order order;
    StoreOrders storeOrders;
    TextView subtotal;
    public static final double PRICE = 1.39;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donut_layout);

        order = (Order)getIntent().getSerializableExtra("currentCart");
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("currentStoreOrders");
        addButton = (Button)findViewById(R.id.addButton);
        subtotal = (TextView)findViewById(R.id.subtotal);

        allFlavorArr = getResources().getStringArray(R.array.flavorsArr);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.quantity));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerArr.add((Spinner) findViewById(R.id.chocolate_qty));
        spinnerArr.add((Spinner) findViewById(R.id.mint_qty));
        spinnerArr.add((Spinner) findViewById(R.id.strawberry_qty));
        spinnerArr.add((Spinner) findViewById(R.id.vanilla_qty));
        spinnerArr.add((Spinner) findViewById(R.id.classic_qty));
        spinnerArr.add((Spinner) findViewById(R.id.coffee_qty));
        spinnerArr.add((Spinner) findViewById(R.id.berry_qty));
        spinnerArr.add((Spinner) findViewById(R.id.cinnamon_qty));
        spinnerArr.add((Spinner) findViewById(R.id.lemon_qty));
        spinnerArr.add((Spinner) findViewById(R.id.glazed_qty));
        spinnerArr.add((Spinner) findViewById(R.id.peanut_qty));
        spinnerArr.add((Spinner) findViewById(R.id.blueberry_qty));


        for(int i = 0; i < allFlavorsQty.length;i++){
            setSpinner(spinnerArr.get(i),i);
        }


    }

    /**
     * This method add the donuts to order and back to main activity.
     *
     * @param v the View
     */
    public void addToOrder(View v){
        ArrayList<String> tempFlavorsName = new ArrayList<>();
        ArrayList<Integer> tempFlavorsQty = new ArrayList<>();
        Order tempOrder = new Order();
        for(int i = 0; i < allFlavorsQty.length;i++){
            if(allFlavorsQty[i] != 0) {
                tempOrder.add(new Donut(allFlavorArr[i],allFlavorsQty[i]));
            }
        }

        if(tempOrder.isEmpty()){
            Toast.makeText(this,R.string.toast_empty_cart,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        order.addAll(tempOrder);
        intent.putExtra("order",order);
        intent.putExtra("returnStoreOrders",storeOrders);
        setResult(RESULT_OK, intent);
        Toast.makeText(this,R.string.toast_add_to_order,Toast.LENGTH_SHORT).show();
        finish();
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
        intent.putExtra("order",order);
        intent.putExtra("returnStoreOrders",storeOrders);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    /**
     * This method set the attributes of spinner
     *
     * @param sp The spinner to be set
     * @param flavorIndex the flavors index
     */
    private void setSpinner(Spinner sp,int flavorIndex){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.quantity));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subtotal.setText(R.string.initial_price);
        sp.setAdapter(adapter);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                int qty = Integer.parseInt(sp.getSelectedItem().toString());
                allFlavorsQty[flavorIndex] = qty;
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    /**
     * This method calculates the current subtotal and set the subtotal display
     */
    private void calculate(){
        double temp = 0.0;
        for(Integer i : allFlavorsQty){
               temp += PRICE * i;
        }
        subtotal.setText(String.format("$%.2f",temp));
    }

}