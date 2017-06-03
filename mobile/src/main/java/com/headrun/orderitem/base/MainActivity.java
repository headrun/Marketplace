package com.headrun.orderitem.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.headrun.orderitem.AppApplication;
import com.headrun.orderitem.R;
import com.headrun.orderitem.auth.AuthActivity;
import com.headrun.orderitem.auth.AuthPresenter;
import com.headrun.orderitem.config.Constants;
import com.headrun.orderitem.database.FirebaseReferencePath;
import com.headrun.orderitem.model.User;
import com.headrun.orderitem.ui.Main2Activity;
import com.headrun.orderitem.utils.Utils;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    Utils mUtils;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUtils = new Utils(this);
        mFirebaseUser = mUtils.loginCheck();
        if (mFirebaseUser != null) {

            FirebaseReferencePath.getUserReference().child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User mUser = dataSnapshot.getValue(User.class);
                    if (mUser != null) {
                        AppApplication.setUserData(mUser);
                        startActivity(new Intent(MainActivity.this, Main2Activity.class));
                        finish();
                    } else {
                        startActivity(new Intent(MainActivity.this, AuthActivity.class));
                        finish();
                        Log.i(TAG, "user is not valid");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AuthActivity.class));
                    finish();

                }
            });

        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


}
