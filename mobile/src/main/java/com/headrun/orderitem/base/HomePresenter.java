package com.headrun.orderitem.base;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.model.User;
import com.headrun.orderitem.utils.Utils;

/**
 * Created by sujith on 25/5/17.
 */

public class HomePresenter {

    public FragmentChange mHomeView;
    public Context mContext;
    public FirebaseUser mFirebaseuser;
    public User mUserdata;
    public Utils mUtils;

    public HomePresenter(FragmentChange mHomeView, Context mContext) {
        this.mHomeView = mHomeView;
        this.mContext = mContext;
        mUtils = new Utils(mContext);
        getFirebaseuser();
    }

    public void getFirebaseuser() {
        mFirebaseuser = mUtils.loginCheck();
        mUserdata = AppApplication.getmUserData();
    }


}
