package com.headrun.orderitem.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.headrun.orderitem.R;
import com.headrun.orderitem.customer.customer_item.CustomerHomeFragment;
import com.headrun.orderitem.customer.CustomerOrdersFragment;
import com.headrun.orderitem.profile.ProfileFragment;
import com.headrun.orderitem.suplier.addItemView.AddItemFragment;
import com.headrun.orderitem.suplier.SupplierHomeFragment;
import com.headrun.orderitem.suplier.itemView.ItemFragment;
import com.headrun.orderitem.utils.Utils;

public class HomeActivity extends AppCompatActivity implements FragmentChange {

    private TextView mTextMessage;
    HomePresenter mHomePresenter;
    Menu mBottom_nav_menu;
    BottomNavigationView navigation;
    boolean is_custoemr;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    if (is_custoemr)
                        changeFragemnt(new CustomerHomeFragment());
                    else
                        changeFragemnt(new SupplierHomeFragment());
                    return true;
                case R.id.navigation_ordes:
                    mTextMessage.setText(R.string.title_orders);
                    if (is_custoemr)
                        changeFragemnt(new CustomerOrdersFragment());
                    else
                        changeFragemnt(new AddItemFragment());
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    changeFragemnt(new ProfileFragment());
                    return true;

                case R.id.navigation_items:
                    mTextMessage.setText(R.string.title_items);
                    changeFragemnt(new ItemFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mHomePresenter = new HomePresenter(this, this);
        getIntentData();
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeMenu();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    public void getIntentData() {

        Intent intent = getIntent();
        if (intent != null) {
            Bundle data = intent.getExtras();
        }
    }

    public void changeMenu() {

        mBottom_nav_menu = navigation.getMenu();
        if (mHomePresenter.mUserdata.role.equalsIgnoreCase(getString(R.string.customer))) {
            is_custoemr = true;
            mBottom_nav_menu.findItem(R.id.navigation_ordes).setTitle(getString(R.string.title_orders));
            mBottom_nav_menu.findItem(R.id.navigation_items).setVisible(false);

        } else {
            is_custoemr = false;
            mBottom_nav_menu.findItem(R.id.navigation_items).setVisible(true);
            mBottom_nav_menu.findItem(R.id.navigation_ordes).setTitle(getString(R.string.title_additem));

        }
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_cart:
                new Utils().logOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeMenu();
    }

    @Override
    public void changeFragemnt(Fragment mfragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, mfragment)
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
