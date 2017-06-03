package com.headrun.orderitem.suplier.itemView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.R;
import com.headrun.orderitem.model.Item;
import com.headrun.orderitem.utils.DividerItemDecoration;

import java.util.Map;

/**
 * Created by sujith on 27/5/17.
 */

public class ItemFragment extends Fragment implements ItemView, ItemAdapter.ItemCallback {

    public String TAG = ItemFragment.class.getSimpleName();
    public RecyclerView item_listview;
    public ProgressBar mProgress_bar;
    public GridLayoutManager gridLayoutManager;
    public ItemAdapter mItemAdapter;
    public ItemPresenter mItemPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suplier_itemview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        mItemPresenter.getItems();

    }

    public void init() {

        mItemPresenter = new ItemPresenter(getActivity(), this);

        item_listview = (RecyclerView) getActivity().findViewById(R.id.item_listview);
        mProgress_bar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        int columnCount = getResources().getInteger(R.integer.column_count);

        gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        item_listview.setLayoutManager(gridLayoutManager);
        item_listview.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recyclerview_divider));
        mItemAdapter = new ItemAdapter(getActivity(), mItemPresenter.mItemLinkedList);
        mItemAdapter.setCallbacks(this);
        item_listview.setAdapter(mItemAdapter);

    }

    @Override
    public void clickItem(int pos) {

        Log.i(TAG, "sel pos is key is " + mItemPresenter.mItemkeyList.get(pos) +
                "\n values are " + mItemPresenter.mItemMap.get(mItemPresenter.mItemkeyList.get(pos)));

    }

    @Override
    public void updateItemList() {

        mItemAdapter.notifyData();

    }

    @Override
    public void clearItemList() {

    }
}
