package com.example.simon.uvdn5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPrice.setText(intent.getStringExtra("price"));
    }

    public void finish(View view){
        finish();
    }
}
