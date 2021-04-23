package com.example.appcartjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcartjava.OnClickChangeAmount;
import com.example.appcartjava.R;
import com.example.appcartjava.adapters.CartAdapter;
import com.example.appcartjava.models.Product;
import com.example.appcartjava.singleton.CartSingleTon;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRcvCart;
    List<Product> mProducts;
    CartAdapter mCartAdapter;
    TextView mTvEmpty;
    Button mBtnBuy,mBtnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mToolbar = findViewById(R.id.toolBarCart);
        mRcvCart = findViewById(R.id.recyclerViewCart);
        mTvEmpty = findViewById(R.id.textViewEmptyCart);
        mBtnBuy = findViewById(R.id.buttonBuy);
        mBtnCancel = findViewById(R.id.buttonCancel);

        mProducts = new ArrayList<>();
        mProducts.addAll(CartSingleTon.getInstance().getCart());
        mCartAdapter = new CartAdapter(this);
        mRcvCart.setAdapter(mCartAdapter);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        checkRenderRecyclerView();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mCartAdapter.setProductList(mProducts, new OnClickChangeAmount() {
            @Override
            public void onChangedAmount(int productId, int amount) {
                for (int i = 0; i < CartSingleTon.getInstance().getCart().size() ; i++) {
                    if (amount == 0){
                        if (productId == CartSingleTon.getInstance().getCart().get(i).getProductId()){
                            CartSingleTon.getInstance().getCart().remove(i);
                            mProducts.remove(i);
                            mCartAdapter.notifyItemRemoved(i);
                            break;
                        }
                    }else {
                        if (productId == CartSingleTon.getInstance().getCart().get(i).getProductId()){
                            CartSingleTon.getInstance().getCart().get(i).setProductAmount(amount);
                            mProducts.get(i).setProductAmount(amount);
                            mCartAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                }
                checkRenderRecyclerView();
            }
        });
        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CartSingleTon.getInstance().getCart().size() == 0){
                    Toast.makeText(CartActivity.this, "Giỏ hàng bạn đang trống!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartSingleTon.getInstance().clearCart();
                mProducts.clear();
                mCartAdapter.notifyDataSetChanged();
                checkRenderRecyclerView();
                finish();
            }
        });

    }
    private void hideView(View v){
        v.setVisibility(View.GONE);
    }
    private void showView(View v){
        v.setVisibility(View.VISIBLE);
    }
    private void checkRenderRecyclerView(){
        if (CartSingleTon.getInstance().getCart().size() == 0){
            hideView(mRcvCart);
            showView(mTvEmpty);
        }else{
            showView(mRcvCart);
            hideView(mTvEmpty);
        }
    }

}