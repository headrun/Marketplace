package com.headrun.orderitem.config;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sujith on 21/2/17.
 */

public class Constants {


    public static final int LOGIN = 1;
    public static final int SIGNUP = 2;
    public static final String USER = "user";

    public static String TYPE = "redirect_type";

    public static String ADD_CART = "add_cart";
    public static String OPEN_ORDER = "open";
    public static String CLOSE_ORDER = "close";


    public static class FirebaseNames {

        public static String IMAGES = "images";
        public static String USERS = "users";
        public static String ITEMS = "items";
        public static String USER_ITEMS = "UserItems";
        public static String ORDERS = "orders";
        public static String ORDERS_ITEMS = "order_items";

    }

}
