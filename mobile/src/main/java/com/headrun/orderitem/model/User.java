package com.headrun.orderitem.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sujith on 24/5/17.
 */
@IgnoreExtraProperties
public class User {

    public String first_name;
    public String last_name;
    public String email;
    public String ph_no;
    public String role;

    public User(String first_name, String last_name, String email, String ph_no, String role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.ph_no = ph_no;
        this.role = role;
    }

    public User() {
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("email", email);
        map.put("ph_no", ph_no);
        map.put("role", role);
        return map;
    }
}
