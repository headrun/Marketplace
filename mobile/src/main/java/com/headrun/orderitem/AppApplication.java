package com.headrun.orderitem;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.headrun.orderitem.model.User;


/**
 * Created by HP-HP on 27-03-2016.
 */
public class AppApplication extends Application {

    public static final String TAG = AppApplication.class.getSimpleName();

    private static AppApplication _instance;
    //  private RequestQueue mRequestQueue;
    public static FirebaseAuth mAuth;
    public static FirebaseUser mFirebaseUser;
    public static FirebaseDatabase mFirebaseDatabase;
    public static User mUserData;


    FirebaseStorage mFirebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static AppApplication getInstance() {
        return _instance;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public static FirebaseDatabase getFirebaseDataBase() {
        if (mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        return mFirebaseDatabase;
    }

    public static void setmFirebaseUser(FirebaseUser mfirebaseUser) {
        mFirebaseUser = mfirebaseUser;
    }

    public static FirebaseUser getmFirebaseUser() {
        return mFirebaseUser;
    }

    public static FirebaseStorage getmFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    /*public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        }
        return mRequestQueue;
    }*/

    public static void setUserData(User UserData) {
        mUserData = UserData;
    }

    public static User getmUserData() {
        return mUserData;
    }

    /*public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }*/
}
