package com.headrun.orderitem.suplier.itemView;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.R;
import com.headrun.orderitem.database.FirebaseReferencePath;
import com.headrun.orderitem.model.Item;
import com.headrun.orderitem.model.UserItems;
import com.headrun.orderitem.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by sujith on 27/5/17.
 */

public class ItemPresenter {

    String TAG = ItemPresenter.class.getSimpleName();

    public ItemView mItemView;
    public Context mContext;
    public LinkedList<HashMap<String, String>> mItemLinkedList;
    public LinkedList<String> mItemkeyList;
    public LinkedHashMap<String, Item> mItemMap;
    public Utils mUtils;

    public ItemPresenter(Context mContext, ItemView mItemView) {
        this.mItemView = mItemView;
        this.mContext = mContext;
        this.mUtils = mUtils;
        mItemLinkedList = new LinkedList<>();
        mItemMap = new LinkedHashMap<>();
        mItemkeyList = new LinkedList<>();
    }

    public ItemPresenter() {
    }


    public void getItems() {

        FirebaseUser mFirebaseUser = AppApplication.getmFirebaseUser();
        Query firebase_item_query = null;
        if (AppApplication.getmUserData().role.equalsIgnoreCase(mContext.getString(R.string.merchant)))
            firebase_item_query = FirebaseReferencePath.getItems().
                    orderByChild("user_id").equalTo(mFirebaseUser.getUid());
        else
            firebase_item_query = FirebaseReferencePath.getItems();

        if (firebase_item_query != null) {
            firebase_item_query.
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.wtf(TAG, "dataSnapshot is " + dataSnapshot);

                            if (dataSnapshot.getValue() != null) {
                                mItemMap.putAll((Map<String, Item>) dataSnapshot.getValue());


                                Collection<Item> matches = mItemMap.values();

                                mItemLinkedList.clear();
                                mItemkeyList.clear();

                                mItemkeyList.addAll(mItemMap.keySet());
                                mItemLinkedList.addAll(matches);
                                mItemView.updateItemList();

                                Log.i(TAG, "items are " + matches.size());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            try {
                                mUtils.firebaseException(databaseError.toException());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } else {
            Log.wtf(TAG, "query is nulll");
        }
    }

}
