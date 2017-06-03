package com.headrun.orderitem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.auth.AuthActivity;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by sujith on 22/5/17.
 */

public class Utils {

    public String TAG = Utils.class.getSimpleName();

    Context mContext;
    UserSession mUserSession;

    public Utils(Context mContext) {
        this.mContext = mContext;
        mUserSession = new UserSession(mContext);

    }

    public Utils() {
        mUserSession = new UserSession();
    }

    /*public boolean loginCheck() {
        return mUserSession.getSession().trim().isEmpty() ? false : true;
    }*/

    public FirebaseUser loginCheck() {
        FirebaseUser mFirebaseUser = AppApplication.getFirebaseAuth().getCurrentUser();
        if (mFirebaseUser != null && !mFirebaseUser.isAnonymous()) {
            AppApplication.setmFirebaseUser(mFirebaseUser);
            return mFirebaseUser;
        } else {
            mContext.startActivity(new Intent(mContext, AuthActivity.class));
            ((Activity) mContext).finish();
        }
        return null;
    }


    public void firebaseException(Exception mException) {
        String errorMessage = mException.getMessage();
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();

    }

    public void logOut() {
        AppApplication.getFirebaseAuth().signOut();
        mContext.startActivity(new Intent(mContext, AuthActivity.class));
        ((Activity) mContext).finish();
    }

    public void setIamge(ImageView mImageView, String url) {
        Picasso.with(mContext).load(url).into(mImageView);
    }

    public long getUTCTimeMills() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
    }
}
