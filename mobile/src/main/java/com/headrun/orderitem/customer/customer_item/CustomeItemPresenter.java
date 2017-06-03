package com.headrun.orderitem.customer.customer_item;

import android.content.Context;

import com.headrun.orderitem.base.FragmentChange;

/**
 * Created by sujith on 31/5/17.
 */

public class CustomeItemPresenter {

    CustomerItemView mCustomerItemView;
    Context mContext;
    FragmentChange mFragmentChange;

    public CustomeItemPresenter(Context mContext, CustomerItemView mCustomerItemView) {
        this.mCustomerItemView = mCustomerItemView;
        this.mContext = mContext;
    }
}
