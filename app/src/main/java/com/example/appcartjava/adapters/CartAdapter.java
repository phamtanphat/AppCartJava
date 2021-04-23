package com.example.appcartjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.appcartjava.OnClickChangeAmount;
import com.example.appcartjava.R;
import com.example.appcartjava.models.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> mListProduct;
    private Context context;
    private OnClickChangeAmount mOnClickChangeAmount;
    private ViewBinderHelper mViewBinderHelper;

    public CartAdapter(Context context) {
        this.context = context;
        mViewBinderHelper = new ViewBinderHelper();
    }

    public void setProductList(List<Product> productList, OnClickChangeAmount mOnClickChangeAmount) {
        mListProduct = productList;
        this.mOnClickChangeAmount = mOnClickChangeAmount;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product currentProduct = mListProduct.get(position);
        if (currentProduct == null) {
            return;
        }
        Glide.with(context)
                .load(currentProduct.getProductImage())
                .into(holder.imgCart);
        holder.tvName.setText(currentProduct.getProductTitle());
        holder.tvPrice.setText("$" + currentProduct.getProductPrice());
        holder.tvAmount.setText(currentProduct.getProductAmount()+"");
        mViewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(currentProduct.getProductId()));
        holder.imgInCrease.setOnClickListener(v -> {
            if (mOnClickChangeAmount != null){
                mOnClickChangeAmount.onChangedAmount(currentProduct.getProductId(),currentProduct.getProductAmount() + 1);
            }

        });
        holder.imgDeCrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickChangeAmount != null){
                    if (currentProduct.getProductAmount() == 0){
                        currentProduct.setProductAmount(1);
                    }
                    mOnClickChangeAmount.onChangedAmount(currentProduct.getProductId(),currentProduct.getProductAmount() - 1);
                }
            }
        });
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickChangeAmount != null){
                    mOnClickChangeAmount.onChangedAmount(currentProduct.getProductId(),0);
                }
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



    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCart,imgInCrease,imgDeCrease;
        private TextView tvName,tvPrice,tvAmount;
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layoutDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.img_cart);
            tvName = itemView.findViewById(R.id.tv_name_cart);
            tvPrice = itemView.findViewById(R.id.tv_price_cart);
            tvAmount = itemView.findViewById(R.id.editTextAmount);
            imgInCrease = itemView.findViewById(R.id.imageViewInCrease);
            imgDeCrease = itemView.findViewById(R.id.imageViewDeCrease);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            layoutDelete = itemView.findViewById(R.id.layoutDelete);
        }
    }

}
