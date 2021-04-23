package com.example.appcartjava.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcartjava.R;
import com.example.appcartjava.adapters.ProductAdapter;
import com.example.appcartjava.models.Product;
import com.example.appcartjava.singleton.CartSingleTon;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    // ui
    private Toolbar mToolbar;

    private RecyclerView mRvProductList;
    private ProductAdapter mProductAdapter;
    private List<Product> mProductList;
    private SearchView mSearchView;
    private TextView mTvCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        mProductAdapter = new ProductAdapter(getApplicationContext());
        mProductList = new ArrayList<>();
        mProductList.add(new Product(1,R.drawable.mac_air, "Mac Air", 2000,1));
        mProductList.add(new Product(2,R.drawable.iphone12, "Iphone 12", 800,1));
        mProductList.add(new Product(3,R.drawable.samsung, "Samsung",400,1));
        mProductList.add(new Product(5,R.drawable.iphone10, "Iphone 10", 500,1));
        mProductList.add(new Product(6,R.drawable.oppo, "Oppo Reno", 300,1));
        mProductList.add(new Product(7,R.drawable.iphonese, "Iphone SE", 700,1));
        mProductList.add(new Product(8,R.drawable.xiaomi, "Xiaomin", 240,1));

        mProductAdapter.setProductList(mProductList, product -> {
            CartSingleTon.getInstance().pushCart(product);
            setupBadge();
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRvProductList.setLayoutManager(gridLayoutManager);
        mRvProductList.setHasFixedSize(true);
        mRvProductList.setItemViewCacheSize(20);
        mRvProductList.setAdapter(mProductAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProductAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProductAdapter.getFilter().filter(newText);
                return true;
            }
        });

        final MenuItem menuItem = menu.findItem(R.id.itemCart);

        View actionView = menuItem.getActionView();
        mTvCartItemCount = actionView.findViewById(R.id.textCartbage);
        setupBadge();
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemCart :
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mToolbar = findViewById(R.id.topAppBar_list);
        mRvProductList = findViewById(R.id.rv_product_list);
    }

    @Override
    public void onBackPressed() {
        if (!mSearchView.isIconified()){
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    private void setupBadge() {
        if (mTvCartItemCount != null) {
            if (CartSingleTon.getInstance().getCart().size() == 0) {
                if (mTvCartItemCount.getVisibility() != View.GONE) {
                    mTvCartItemCount.setVisibility(View.GONE);
                }
            } else {
                mTvCartItemCount.setText(String.valueOf(Math.min(CartSingleTon.getInstance().getCart().size(), 99)));
                if (mTvCartItemCount.getVisibility() != View.VISIBLE) {
                    mTvCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        setupBadge();
        super.onRestart();
    }
}