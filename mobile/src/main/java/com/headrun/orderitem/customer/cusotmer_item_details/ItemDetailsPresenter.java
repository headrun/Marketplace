package com.headrun.orderitem.customer.cusotmer_item_details;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.R;
import com.headrun.orderitem.base.FragmentChange;
import com.headrun.orderitem.config.Constants;
import com.headrun.orderitem.database.FirebaseReferencePath;
import com.headrun.orderitem.model.Item;
import com.headrun.orderitem.model.Order;
import com.headrun.orderitem.utils.Utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.headrun.orderitem.database.FirebaseReferencePath.getOrders;

/**
 * Created by sujith on 31/5/17.
 */

public class ItemDetailsPresenter {

    String TAG = ItemDetailsPresenter.class.getSimpleName();
    public ItemDetailsView.GetOrder mItemDetailsView;
    public Context mContext;
    //public Item sel_item;
    public Utils mUtils;
    public Order.OrderItem mOrderItem;
    public HashMap<String, String> sel_item;
    public LinkedHashMap<String, Order> mUser_ordersMap;
    FragmentChange mFragmentChange;

    public ItemDetailsPresenter(Context mContext, ItemDetailsView.GetOrder mItemDetailsView) {
        this.mItemDetailsView = mItemDetailsView;
        this.mContext = mContext;
        this.mUtils = new Utils(mContext);
        mUser_ordersMap = new LinkedHashMap<>();
    }

    public void getUserOrerList(String type) {
        String user_id = AppApplication.getmFirebaseUser().getUid();
        String id = user_id + "_" + type;
        Query query = FirebaseReferencePath.getOrders().orderByChild("user_id").equalTo(id);

        mUser_ordersMap.clear();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null)
                    mUser_ordersMap.putAll((Map<String, Order>) dataSnapshot.getValue());

                mItemDetailsView.getOrders(mUser_ordersMap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf(TAG, "get an error" + databaseError.getMessage());
            }
        });

    }

    public void addOrderitems(String order_id) {

        Map<String, Object> childUpdates = new HashMap<>();

        mOrderItem = new Order.OrderItem();

        mOrderItem.item_id = sel_item.get("item_id");
        mOrderItem.qty = 1;
        Order mOrder = null;
        String Order_id = order_id;

        if (order_id.isEmpty()) {
            Order_id = FirebaseReferencePath.getOrders().push().getKey();
            mOrder = getOrder();
            childUpdates.put("/" + Constants.FirebaseNames.ORDERS + "/" + Order_id, mOrder);
        } else {
            mOrder = getOrder();
            childUpdates.put("/" + Constants.FirebaseNames.ORDERS + "/" + Order_id, mOrder);
        }
        mOrderItem.order_id = Order_id;

        final String order_item_key = FirebaseReferencePath.getOderItems().push().getKey();
        childUpdates.put("/" + Constants.FirebaseNames.ORDERS_ITEMS + "/" + order_item_key, mOrderItem);

        final Order finalMOrder = mOrder;

        FirebaseReferencePath.getReference().updateChildren(childUpdates).
                addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(mContext, "" + mContext.getString(R.string.add_cart), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mUtils.firebaseException(e);
            }
        }).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        });
    }

    public Order getOrder() {
        Order order = new Order();
        order.no_items = 1;
        order.status = Constants.ADD_CART;
        order.time_stamp = mUtils.getUTCTimeMills();
        order.user_id = AppApplication.getmFirebaseUser().getUid() + "_" + Constants.ADD_CART;
        return order;
    }
}
