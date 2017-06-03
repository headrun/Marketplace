package com.headrun.orderitem.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujith on 29/5/17.
 */
@IgnoreExtraProperties
public class UserItems {


    public String item_id;
    public boolean contains;

    public UserItems(String item_id, boolean contians) {
        this.contains = contians;
        this.item_id = item_id;
    }

    public UserItems() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("item_id", item_id);
        map.put("contains", contains);
        return map;
    }

}
