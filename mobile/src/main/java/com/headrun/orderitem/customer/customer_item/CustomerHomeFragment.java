package com.headrun.orderitem.customer.customer_item;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.headrun.orderitem.base.FragmentChange;
import com.headrun.orderitem.customer.cusotmer_item_details.CustomerItemDeatilsActivity;
import com.headrun.orderitem.suplier.itemView.ItemFragment;

import java.util.HashMap;

/**
 * Created by sujith on 24/5/17.
 */

public class CustomerHomeFragment extends ItemFragment implements CustomerItemView, FragmentChange {

    String TAG = CustomerHomeFragment.class.getSimpleName();

    CustomeItemPresenter mCustomeItemPresenter = new CustomeItemPresenter(getActivity(), this);


    @Override
    public void clickItem(int pos) {

        mCustomeItemPresenter.mFragmentChange = (FragmentChange) getActivity();

        Log.i(TAG, "sel pos is key is " + mItemPresenter.mItemkeyList.get(pos) +
                "\n values are " + mItemPresenter.mItemMap.get(mItemPresenter.mItemkeyList.get(pos)));

        HashMap<String, String> sel_item = mItemPresenter.mItemMap.get(mItemPresenter.mItemkeyList.get(pos));
        sel_item.put("item_id", mItemPresenter.mItemkeyList.get(pos));

       /* Intent i = new Intent(getActivity(), CustomerItemDeatilsActivity.class);
        i.putExtra("sel_item", sel_item);
        startActivity(i);*/

        changeFragemnt(new CustomerItemDeatilsActivity().newInstance(sel_item));
    }

    @Override
    public void changeFragemnt(Fragment mfragment) {
        mCustomeItemPresenter.mFragmentChange.changeFragemnt(mfragment);
    }
}
