package com.headrun.orderitem.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.headrun.orderitem.AppApplication;

import com.headrun.orderitem.R;
import com.headrun.orderitem.base.HomeActivity;
import com.headrun.orderitem.config.Constants;
import com.headrun.orderitem.database.FirebaseReferencePath;
import com.headrun.orderitem.model.User;
import com.headrun.orderitem.utils.Utils;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by sujith on 7/3/17.
 */

public class AuthPresenter {

    static AuthView mAuthView;
    static Context mContext;
    static Utils mUtils;
    static String REDIRECT_TO = "";
    static String demand_id = "";

    String[] user_roles;


    public AuthPresenter(Context mContext, String REDIRECT_TO, String demand_id) {
        this.REDIRECT_TO = REDIRECT_TO;
        this.demand_id = demand_id;
        this.mContext = mContext;
        user_roles = mContext.getResources().getStringArray(R.array.roels);
        mUtils = new Utils(mContext);
    }

    public AuthPresenter() {
    }

    public AuthPresenter(AuthView mAuthView, Context mContext) {
        this.mAuthView = mAuthView;
        this.mContext = mContext;
        user_roles = mContext.getResources().getStringArray(R.array.roels);
        mUtils = new Utils(mContext);
    }


    public void loginValidation(String email, String pwd, String role) {

        if (TextUtils.isEmpty(role) && user_roles.length > 0) {
            role = user_roles[0];
        }

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
            mAuthView.setError(mContext.getString(R.string.missing_fields));
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", email);
            params.put("password", pwd);
            new UserCredetilsCheck(mContext, email, pwd, role, Constants.LOGIN);
        }
    }

    public void signupValidation(String fname, String lname, String email, String pwd, String ph_no, String role) {

        if (TextUtils.isEmpty(role) && user_roles.length > 0) {
            role = user_roles[0];
        }

        if (TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(ph_no)) {
            mAuthView.setError(mContext.getString(R.string.missing_fields));
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("first_name", fname);
            params.put("last_name", lname);
            params.put("email", email);
            params.put("password", pwd);
            params.put("username", email);
            params.put("phone_number", ph_no);
            new UserCredetilsCheck(mContext, email, pwd, fname, lname, ph_no, email, role, Constants.SIGNUP);
        }

    }


    public class UserCredetilsCheck implements OnCompleteListener<AuthResult> {

        int type;
        String email, f_name, l_name, ph_no, user_name, role;

        public UserCredetilsCheck(Context mContext, String email, String pwd, String role, int type) {
            this.type = type;
            AppApplication.getFirebaseAuth().
                    signInWithEmailAndPassword(email, pwd).
                    addOnCompleteListener(this);
        }

        public UserCredetilsCheck(Context mContext, String email, String pwd, String f_name, String l_name, String ph_no, String user_name, String role, int type) {
            this.type = type;
            this.email = email;
            this.f_name = f_name;
            this.l_name = l_name;
            this.ph_no = ph_no;
            this.role = role;
            this.user_name = user_name;
            AppApplication.getFirebaseAuth().
                    createUserWithEmailAndPassword(email, pwd).
                    addOnCompleteListener(this);
        }

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()) {
                if (Constants.LOGIN == type) {
                    Log.i(TAG, "login task info is " + task.toString());
                    AppApplication.setmFirebaseUser(task.getResult().getUser());
                    getUserData();

                    mAuthView.redirectActivity(AppApplication.getmFirebaseUser());
                } else if (Constants.SIGNUP == type) {
                    Log.i(TAG, "sign up task info is " + task.toString());
                    mAuthView.setData(new User(f_name, l_name, email, ph_no, role));
                }

            } else {

                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(mContext, "Authentication failed." +
                                task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        }

        public void setUserData(User mUser) {


        }
    }

    protected static void redirctToAtivty(FirebaseUser mFirebaseUser) {

        mContext.startActivity(new Intent(mContext, HomeActivity.class));
        ((Activity) mContext).finish();


    }

    public void setFireBaseUserData(final User mUser) {

        final FirebaseUser loginUser = mUtils.loginCheck();
        if (loginUser != null) {

            FirebaseReferencePath.getUserReference().child(loginUser.getUid()).setValue(mUser)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    mAuthView.setError(e.getLocalizedMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    AppApplication.setUserData(mUser);

                    mAuthView.redirectActivity(loginUser);
                }
            });

        }
    }

    public void getUserData() {

        AppApplication.getFirebaseDataBase().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User mUser = dataSnapshot.getValue(User.class);

                if (mUser != null) {
                    AppApplication.setUserData(mUser);
                } else {
                    Log.i(TAG, "user is not valid");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

}

