package com.headrun.orderitem.customer.cusotmer_item_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.headrun.orderitem.R;
import com.headrun.orderitem.base.FragmentChange;
import com.headrun.orderitem.config.Constants;
import com.headrun.orderitem.model.Order;

import java.util.HashMap;
import java.util.LinkedList;

public class CustomerItemDeatilsActivity extends Fragment implements ItemDetailsView.GetOrder, View.OnClickListener, FragmentChange {

    String TAG = CustomerItemDeatilsActivity.class.getSimpleName();
    String SEL_ITEM = "sel_item";
    ImageView item_img;
    TextView item_name, item_desc;
    Button continue_shop, add_to_cart;

    ItemDetailsPresenter mItemDetailsPresenter;


    public CustomerItemDeatilsActivity newInstance(HashMap<String, String> mitemMap) {

        CustomerItemDeatilsActivity mFragment = new CustomerItemDeatilsActivity();

        Bundle args = new Bundle();
        args.putSerializable(SEL_ITEM, mitemMap);
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemDetailsPresenter = new ItemDetailsPresenter(getActivity(), this);
        if (getArguments() != null) {
            mItemDetailsPresenter.sel_item = (HashMap<String, String>) getArguments().getSerializable(SEL_ITEM);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_customer_item_deatils, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mItemDetailsPresenter.mFragmentChange = (FragmentChange) getActivity();
        init();

    }

    public void init() {

        item_img = (ImageView) getActivity().findViewById(R.id.item_img);
        item_name = (TextView) getActivity().findViewById(R.id.item_name);
        item_desc = (TextView) getActivity().findViewById(R.id.item_desc);
        continue_shop = (Button) getActivity().findViewById(R.id.continue_shop);
        add_to_cart = (Button) getActivity().findViewById(R.id.add_to_cart);

        if (mItemDetailsPresenter.sel_item != null && mItemDetailsPresenter.sel_item.size() > 0) {
            mItemDetailsPresenter.mUtils.setIamge(item_img, mItemDetailsPresenter.sel_item.get("image_url"));
            item_name.setText(mItemDetailsPresenter.sel_item.get("name"));
            item_desc.setText(mItemDetailsPresenter.sel_item.get("desc"));

            continue_shop.setOnClickListener(this);
            add_to_cart.setOnClickListener(this);
        } else {
            Log.wtf(TAG, "sel item is null");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_shop:

                break;
            case R.id.add_to_cart:
                mItemDetailsPresenter.getUserOrerList(Constants.ADD_CART);
                break;
        }
    }

    @Override
    public void getOrders(HashMap<String, Order> orders) {

        LinkedList<String> morders = new LinkedList<>(orders.keySet());
        Log.i(TAG, "add item" + mItemDetailsPresenter.sel_item);
        if (morders.size() >= 1) {
            mItemDetailsPresenter.addOrderitems(morders.get(0));
        } else {
            mItemDetailsPresenter.addOrderitems("");
        }
    }

    @Override
    public void changeFragemnt(Fragment mfragment) {
        mItemDetailsPresenter.mFragmentChange.changeFragemnt(mfragment);
    }
}
