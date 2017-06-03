package com.headrun.orderitem.customer.cart;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.headrun.orderitem.R;

public class CartActivity extends Fragment implements CartView {

    CartPresenter mCartPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCartPresenter = new CartPresenter(getActivity(), this);

        super.onViewCreated(view, savedInstanceState);
    }

}
