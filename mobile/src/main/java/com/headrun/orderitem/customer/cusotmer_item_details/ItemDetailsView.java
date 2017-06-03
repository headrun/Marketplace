package com.headrun.orderitem.customer.cusotmer_item_details;

import com.headrun.orderitem.model.Order;

import java.util.HashMap;

/**
 * Created by sujith on 31/5/17.
 */

public class ItemDetailsView {

    public interface GetOrder {
        public void getOrders(HashMap<String, Order> orders);
    }
}
