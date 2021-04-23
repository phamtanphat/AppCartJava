package com.example.appcartjava.singleton;

import com.example.appcartjava.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartSingleTon {
    private static CartSingleTon instance = null;
    private List<Product> mListProduct = null;

    private CartSingleTon() {
        mListProduct = new ArrayList<>();
    }

    public static CartSingleTon getInstance(){
        if(instance == null){
            instance = new CartSingleTon();
        }
        return instance;
    }

    public void pushCart(Product product){
        boolean instance = false;
        if (mListProduct.size() <= 0){
            mListProduct.add(product);
        }else{
            for (int i = 0; i < mListProduct.size() ; i++) {
                if (product.getProductId() == mListProduct.get(i).getProductId()){
                    mListProduct.get(i).setProductAmount(mListProduct.get(i).getProductAmount() +1);
                    instance = true;
                    break;
                }else {
                    instance = false;
                }
            }

            if (!instance){
                mListProduct.add(product);
            }
        }

    }
    public void clearCart(){
        mListProduct.clear();
    }

    public List<Product> getCart(){
        return mListProduct;
    }


}
