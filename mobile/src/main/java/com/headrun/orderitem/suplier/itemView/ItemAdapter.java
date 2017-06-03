package com.headrun.orderitem.suplier.itemView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.headrun.orderitem.R;
import com.headrun.orderitem.model.Item;
import com.headrun.orderitem.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by sujith on 27/5/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context mContext;
    LinkedList<HashMap<String, String>> mItemLinkedList;
    private ItemAdapter.ItemCallback mCallbacks;

    interface ItemCallback {
        public void clickItem(int pos);
    }

    public ItemAdapter(Context mContext, LinkedList<HashMap<String, String>> mItemLinkedList) {

        this.mContext = mContext;
        this.mItemLinkedList = mItemLinkedList;

    }

    public void setCallbacks(ItemAdapter.ItemCallback mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.suplier_item_view_adpter, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Item mItem = (Item) mItemLinkedList.get(position);

        try {
            // Item mItem = mItemLinkedList.get(position);
            HashMap<String, String> pos_item = mItemLinkedList.get(position);
            // Item mItem = new Gson().fromJson(pos_item.toString(), Item.class);


            if (pos_item.get("image_url") != null && !pos_item.get("image_url").isEmpty())
                new Utils(mContext).setIamge(holder.mImageView, pos_item.get("image_url"));

            holder.txt_item_name.setText("" + pos_item.get("name"));
            holder.txt_item_price.setText(mContext.getString(R.string.price, pos_item.get("actual_price").toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mItemLinkedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView txt_item_name, txt_item_price;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.img_item);
            txt_item_name = (TextView) itemView.findViewById(R.id.txt_item_name);
            txt_item_price = (TextView) itemView.findViewById(R.id.txt_item_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallbacks != null) {
                mCallbacks.clickItem(getAdapterPosition());
            }
        }
    }

    public void notifyData() {
        notifyDataSetChanged();
    }
}
