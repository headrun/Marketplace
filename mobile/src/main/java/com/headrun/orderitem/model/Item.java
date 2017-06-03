package com.headrun.orderitem.model;

import com.google.firebase.database.IgnoreExtraProperties;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujith on 26/5/17.
 */
@IgnoreExtraProperties
public class Item extends HashMap<String, String> implements Serializable {


    public String name;

    public String actual_price;

    public String offer_price;

    public String desc;

    public String image_url;

    public String user_id;

    public String item_id;

    public Item() {

    }


    public Item(String name, String actual_price, String offer_price, String desc) {
        this.name = name;
        this.actual_price = actual_price;
        this.offer_price = offer_price;
        this.desc = desc;
    }

    public Item(String name, String actual_price, String offer_price, String desc, String image_url) {
        this.name = name;
        this.actual_price = actual_price;
        this.offer_price = offer_price;
        this.desc = desc;
        this.image_url = image_url;
    }

    public Item(String name, String actual_price, String offer_price, String desc, String image_url, String user_id) {
        this.name = name;
        this.actual_price = actual_price;
        this.offer_price = offer_price;
        this.desc = desc;
        this.image_url = image_url;
        this.user_id = user_id;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("actual_price", actual_price);
        map.put("offer_price", offer_price);
        map.put("desc", desc);
        map.put("image_url", image_url);
        map.put("user_id", user_id);
        return map;
    }

    public Item mapToItem(HashMap<String, String> map) {

        Item mItem = new Item(map.get("name"),
                map.get("actual_price"),
                map.get("offer_price"),
                map.get("desc"),
                map.get("image_url"),
                map.get("user_id"));

        return mItem;

    }

}
