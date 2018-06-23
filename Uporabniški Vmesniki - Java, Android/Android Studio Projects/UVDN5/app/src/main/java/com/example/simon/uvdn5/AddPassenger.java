package com.example.simon.uvdn5;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class AddPassenger extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);
    }

    public void submit(View view){
        String result = "";
        RadioButton rbMale = (RadioButton) findViewById(R.id.rbMale);
        RadioButton rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        if(rbMale.isChecked()){
            result += "Mr. ";
        }else if(rbFemale.isChecked()){
            result += "Mrs. ";
        }
        EditText etSurname = (EditText) findViewById(R.id.etSurname);
        result += etSurname.getText().toString() + ", ";
        Spinner spYearPass = (Spinner) findViewById(R.id.spYearPass);
        int age = 2017 -Integer.parseInt(spYearPass.getSelectedItem().toString());
        result += age + "";
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", result);
        String price = "";
        if(age <= 2) price += "0";
        else if(age >2 && age <= 12) price += "1";
        else price += "2";
        resultIntent.putExtra("price", price);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void cancel(View view){
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }
}
