package com.example.appcartjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcartjava.R;
import com.example.appcartjava.singleton.CartSingleTon;

public class PaymentActivity extends AppCompatActivity {

    TextView mTvTotal,mTvTotalPayment;
    Button mBtnPay;
    int mTotal = 0;
    float mTotalPay = 0;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mTvTotal = findViewById(R.id.textViewTotal);
        mTvTotalPayment = findViewById(R.id.textViewTotalPayment);
        mBtnPay = findViewById(R.id.buttonPay);
        mToolbar = findViewById(R.id.toolBarPayment);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        for (int i = 0; i < CartSingleTon.getInstance().getCart().size(); i++) {
            mTotal += CartSingleTon.getInstance().getCart().get(i).getProductPrice() * CartSingleTon.getInstance().getCart().get(i).getProductAmount();
        }
        mTotalPay = (float) (mTotal - (mTotal * 0.12));
        mTvTotal.setText(mTotal + " $");
        mTvTotalPayment.setText(mTotalPay + "$");

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setTitle("Message");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CartSingleTon.getInstance().clearCart();
                        Toast.makeText(PaymentActivity.this, "Create order success!! ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,ListActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }
}