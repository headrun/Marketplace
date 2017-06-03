package com.headrun.orderitem.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sujith on 22/5/17.
 */

public class UserSession {

    public String TAG = UserSession.this.getClass().getSimpleName();
    public SharedPreferences pref;
    public Context _context;
    public SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "OrderItem";


    public static final String SESSION = "session";


    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public UserSession() {
    }


    public void setSession(String SESSION) {
        editor.putString(this.SESSION, SESSION);
        editor.apply();
    }

    public String getSession() {
        return pref.getString(this.SESSION, "");
    }
}
