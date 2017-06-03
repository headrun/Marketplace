package com.headrun.orderitem.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by sujith on 1/6/17.
 */
@IgnoreExtraProperties
public class Order {

    public String user_id;
    public String status;
    public long time_stamp;
    public HashMap<String, OrderItem> items;
    public int no_items;
    public String total_amount;

    public static class OrderItem {
        public String item_id;
        public int qty;
        public String order_id;

        public OrderItem() {
        }
    }

}
