package com.example.appcartjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcartjava.IClickBuyListener;
import com.example.appcartjava.R;
import com.example.appcartjava.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> mListProduct;
    private Context context;
    private IClickBuyListener mIClickBuyListener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<Product> productList, IClickBuyListener listener) {
        mListProduct = productList;
        mIClickBuyListener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_recycler, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = mListProduct.get(position);
        if (currentProduct == null) {
            return;
        }
        Glide.with(context)
                .load(currentProduct.getProductImage())
                .into(holder.imgProduct);
        holder.tvProductTitle.setText(currentProduct.getProductTitle());
        holder.tvProductPrice.setText(currentProduct.getProductPrice());

        holder.btnBuy.setOnClickListener(v -> {
            if (!currentProduct.getAddToCart()) {
                mIClickBuyListener.onClickAddToCart(currentProduct);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductTitle;
        private TextView tvProductPrice;
        private Button btnBuy;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_placeholder);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title_item);
            tvProductPrice = itemView.findViewById(R.id.tv_price_item);
            btnBuy = itemView.findViewById(R.id.btn_buy_item);
        }
    }

}
