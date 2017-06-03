package com.headrun.orderitem.auth;

import com.google.firebase.auth.FirebaseUser;
import com.headrun.orderitem.model.User;

/**
 * Created by sujith on 7/3/17.
 */

public interface AuthView {

    public void setError(String msg);

    public void redirectActivity(FirebaseUser mFirebaseUser);

    public void showProgress();

    public void hideProgress();

    public void setData(User muser);

}
