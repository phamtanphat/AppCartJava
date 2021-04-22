package com.example.appcartjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appcartjava.IClickBuyListener;
import com.example.appcartjava.R;
import com.example.appcartjava.adapters.ProductAdapter;
import com.example.appcartjava.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    // ui
    private Toolbar mToolbar;

    private RecyclerView mRvProductList;
    private ProductAdapter mProductAdapter;
    private List<Product> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        mProductAdapter = new ProductAdapter(getApplicationContext());
        mProductList = new ArrayList<>();
        mProductList.add(new Product(R.drawable.mac_air, "Mac Air", "$2000"));
        mProductList.add(new Product(R.drawable.iphone12, "Iphone 12", "$800"));
        mProductList.add(new Product(R.drawable.samsung, "Samsung", "$400"));
        mProductList.add(new Product(R.drawable.pixel, "Google Pixel", "$600"));

        mProductAdapter.setProductList(mProductList, product -> {

        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRvProductList.setLayoutManager(gridLayoutManager);
        mRvProductList.setHasFixedSize(true);
        mRvProductList.setItemViewCacheSize(20);
        mRvProductList.setAdapter(mProductAdapter);

    }

    private void initView() {
        mToolbar = findViewById(R.id.topAppBar_list);
        mRvProductList = findViewById(R.id.rv_product_list);
    }
}