package com.headrun.orderitem.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.headrun.orderitem.R;
import com.headrun.orderitem.base.HomePresenter;
import com.headrun.orderitem.base.FragmentChange;
import com.headrun.orderitem.customer.CustomerOrdersFragment;
import com.headrun.orderitem.customer.customer_item.CustomerHomeFragment;
import com.headrun.orderitem.profile.ProfileFragment;
import com.headrun.orderitem.suplier.SupplierHomeFragment;
import com.headrun.orderitem.suplier.addItemView.AddItemFragment;
import com.headrun.orderitem.suplier.itemView.ItemFragment;
import com.headrun.orderitem.utils.Utils;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentChange {

    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    Menu nav_menu;
    boolean is_custoemr;
    HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mHomePresenter = new HomePresenter(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeMenu();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_cart) {
            // mHomePresenter.mUtils.logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_home) {
            if (is_custoemr)
                changeFragemnt(new CustomerHomeFragment());
            else
                changeFragemnt(new SupplierHomeFragment());
        } else if (id == R.id.navigation_ordes) {
            if (is_custoemr)
                changeFragemnt(new CustomerOrdersFragment());
            else
                changeFragemnt(new AddItemFragment());

        } else if (id == R.id.navigation_profile) {
            changeFragemnt(new ProfileFragment());
        } else if (id == R.id.navigation_items) {
            changeFragemnt(new ItemFragment());
        } else if (id == R.id.log_out) {
            new Utils().logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeMenu() {

        nav_menu = navigationView.getMenu();
        if (mHomePresenter.mUserdata.role.equalsIgnoreCase(getString(R.string.customer))) {
            is_custoemr = true;
            nav_menu.findItem(R.id.navigation_ordes).setTitle(getString(R.string.title_orders));
            nav_menu.findItem(R.id.navigation_items).setVisible(false);

        } else {
            is_custoemr = false;
            nav_menu.findItem(R.id.navigation_items).setVisible(true);
            nav_menu.findItem(R.id.navigation_ordes).setTitle(getString(R.string.title_additem));

        }
        invalidateOptionsMenu();

        onNavigationItemSelected(
                navigationView.getMenu().getItem(0).setChecked(true));

    }

    @Override
    public void changeFragemnt(Fragment mfragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, mfragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeMenu();
    }
}
