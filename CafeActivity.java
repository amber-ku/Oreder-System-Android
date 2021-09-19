package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import backend.*;


public class CafeActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    Button addButton;
    RadioButton shortSize,tallSize,grandeSize,ventiSize;
    RadioGroup size;
    Spinner spinnerQty;
    ArrayList<Spinner> spinnerArr = new ArrayList<Spinner>();
    Order order;
    StoreOrders storeOrders;
    TextView subtotal;
    Coffee current;
    int[] qtyArr = new int[Toppings.numOfTopping];
    public static final int DEFAULT_QTY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_layout);

        addButton = (Button)findViewById(R.id.addCafeButton);
        spinnerQty = (Spinner)findViewById(R.id.spinnerQty);
        subtotal = (TextView)findViewById(R.id.cafeSubtotal);
        shortSize = (RadioButton)findViewById(R.id.shortSize);
        tallSize = (RadioButton)findViewById(R.id.tallSize);
        grandeSize = (RadioButton)findViewById(R.id.grandeSize);
        ventiSize = (RadioButton)findViewById(R.id.ventiSize);
        size = (RadioGroup)findViewById(R.id.size);

        order = (Order)getIntent().getSerializableExtra("currentCart");
        storeOrders = (StoreOrders)getIntent().getSerializableExtra("currentStoreOrders");

        current = new Coffee("Short",DEFAULT_QTY);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.quantity));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.quantity2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subtotal.setText(R.string.cafe_initial_price);

        spinnerArr.add((Spinner) findViewById(R.id.cream_qty));
        spinnerArr.add((Spinner) findViewById(R.id.whipcream_qty));
        spinnerArr.add((Spinner) findViewById(R.id.syrup_qty));
        spinnerArr.add((Spinner) findViewById(R.id.milk_qty));
        spinnerArr.add((Spinner) findViewById(R.id.caramel_qty));

        for(int i = 0; i < Toppings.numOfTopping;i++){
            setSpinner(spinnerArr.get(i),i);
        }

        setRadioButton(shortSize,"Short");
        setRadioButton(tallSize,"Tall");
        setRadioButton(grandeSize,"Grande");
        setRadioButton(ventiSize,"Venti");

        spinnerQty.setAdapter(adapter2);
        spinnerQty.setSelection(DEFAULT_QTY);
        spinnerQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                int qty = Integer.parseInt(spinnerQty.getSelectedItem().toString());
                current.setAmount(qty);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



    }

    /**
     * This method set the attributes of spinner
     *
     * @param sp The spinner to be set
     * @param index the Toppings index
     */
    private void setSpinner(Spinner sp,int index){
        sp.setAdapter(adapter);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                int qty = Integer.parseInt(sp.getSelectedItem().toString());
                current.removeToppings(Toppings.getToppingsList().get(index));
                current.addToppingWithQty(Toppings.getToppingsList().get(index),qty);
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /**
     * This method sets the attributes of radio button
     *
     * @param button The  to be set
     * @param sizeStr the size of current coffee
     */
    private void setRadioButton(RadioButton button,String sizeStr){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setSize(sizeStr);
                calculate();
            }
        });
    }

    /**
     * This method calculates the current subtotal and set the subtotal display
     */
    private void calculate(){
        current.itemPrice();
        subtotal.setText(String.format("$%.2f",current.getPrice()));
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
        intent.putExtra("cafeToMain_cart",order);
        intent.putExtra("cafeToMain_storeOrders",storeOrders);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    /**
     * This method add the coffee to order and back to main activity.
     *
     * @param v the View
     */
    public void addToOrder(View v){
        Intent intent = new Intent();
        order.add(current);
        intent.putExtra("cafeToMain_cart",order);
        intent.putExtra("cafeToMain_storeOrders",storeOrders);
        Toast.makeText(this,R.string.toast_add_to_order,Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intent);
        finish();
    }

}