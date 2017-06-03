package com.headrun.orderitem.customer.cart;

import android.content.Context;

/**
 * Created by sujith on 31/5/17.
 */

public class CartPresenter {

    Context mContext;
    CartView mCartView;

    public CartPresenter(Context mContext, CartView mCartView) {
        this.mContext = mContext;
        this.mCartView = mCartView;
    }
}
