package com.headrun.orderitem.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.headrun.orderitem.R;


/**
 * Created by sujith on 7/3/17.
 */

public class AuthActivity extends AppCompatActivity implements FragmentChanged {

    private static final int REQUEST_PERRMISSONS = 1;
    private String TAG = AuthActivity.class.getSimpleName();
    private boolean Skip_enable = true;
    private String REDIRECT_TO = "";
    private String demand_id = "";
    private AuthPresenter mAuthPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        getData();

        mAuthPresenter = new AuthPresenter(this, REDIRECT_TO, demand_id);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_content, new LoginFragment())
                .commit();

    }


    @Override
    public void fragmentChanged(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_content, fragment)
                .commit();

    }


    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle data = intent.getExtras();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void checkPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED},
                REQUEST_PERRMISSONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_PERRMISSONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        checkPermissions();
                        Toast.makeText(this, " permissions are denied", Toast.LENGTH_LONG).show();
                    } else {

                    }
                    return;
                }
        }
    }
}
