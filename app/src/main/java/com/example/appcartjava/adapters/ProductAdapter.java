package com.example.appcartjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcartjava.IClickBuyListener;
import com.example.appcartjava.R;
import com.example.appcartjava.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    private List<Product> mListProduct;
    private List<Product> mListProductOld;
    private Context context;
    private IClickBuyListener mIClickBuyListener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<Product> productList, IClickBuyListener listener) {
        mListProduct = productList;
        mListProductOld = productList;
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
        holder.tvProductPrice.setText("$"+ currentProduct.getProductPrice() );

        holder.tvBuy.setOnClickListener(v -> {
            mIClickBuyListener.onClickAddToCart(currentProduct);
        });

    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListProduct = mListProductOld;
                } else {
                    List<Product> products = new ArrayList<>();
                    for (Product product : mListProductOld) {
                        if (product.getProductTitle().toLowerCase().contains(strSearch.toLowerCase())) {
                            products.add(product);
                        }
                    }
                    mListProduct = products;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListProduct = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvProductTitle, tvBuy;
        private TextView tvProductPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_placeholder);
            tvProductTitle = itemView.findViewById(R.id.tv_product_title_item);
            tvProductPrice = itemView.findViewById(R.id.tv_price_item);
            tvBuy = itemView.findViewById(R.id.tv_buy_item);
        }
    }

}
