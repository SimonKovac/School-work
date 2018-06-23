package com.example.simon.uvdn5;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    float price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spDayArr = (Spinner) findViewById(R.id.spDayArr);
        Spinner spMonthArr = (Spinner) findViewById(R.id.spMonthArr);
        Spinner spYearArr = (Spinner) findViewById(R.id.spYearArr);
        spDayArr.setEnabled(false);
        spMonthArr.setEnabled(false);
        spYearArr.setEnabled(false);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgTrip);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbOw = (RadioButton) findViewById(R.id.rbOw);
                RadioButton rbR = (RadioButton) findViewById(R.id.rbR);
                Spinner spDayArr = (Spinner) findViewById(R.id.spDayArr);
                Spinner spMonthArr = (Spinner) findViewById(R.id.spMonthArr);
                Spinner spYearArr = (Spinner) findViewById(R.id.spYearArr);
                if(checkedId == rbOw.getId()){
                    spDayArr.setEnabled(false);
                    spMonthArr.setEnabled(false);
                    spYearArr.setEnabled(false);
                }else if(checkedId == rbR.getId()){
                    spDayArr.setEnabled(true);
                    spMonthArr.setEnabled(true);
                    spYearArr.setEnabled(true);
                }
            }
        });
    }
    public void addPassengers(View view){
        Intent intent = new Intent(this, AddPassenger.class);
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    String priceValue = data.getStringExtra("price");
                    if(priceValue.equals("1")) price += 0.5;
                    else if(priceValue.equals("2")) price += 1;
                    String returnValue = data.getStringExtra("result");
                    listItems.add(returnValue);
                    adapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }
    public void payment(View view){
        Intent intent = new Intent(this, Payment.class);
        intent.putExtra("price", (price * 100) + "");
        startActivityForResult(intent, 2);
    }

}
