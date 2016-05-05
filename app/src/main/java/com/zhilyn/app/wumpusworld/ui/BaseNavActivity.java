package com.zhilyn.app.wumpusworld.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhilyn.app.wumpusworld.R;

/**
 * Created by Deep on 5/4/16.
 *
 */
abstract class BaseNavActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "BaseNavActivity";

    protected Toolbar mToolbar;
    protected DrawerLayout mDrawer;
    protected NavigationView navigationView;

    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(getSelfInNav());

        setupHeader(navigationView.getHeaderView(0));
    }

    private void setupHeader(View headerView) {
        ImageView profile = (ImageView) headerView.findViewById(R.id.header_profile_pic);
        ImageView profileBg = (ImageView) headerView.findViewById(R.id.header_profile_bg);
        TextView profileText = (TextView) headerView.findViewById(R.id.header_profile_text);

    }

    protected abstract int getSelfInNav();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent i = null;
        switch(item.getItemId()){
            case R.id.nav_game:

                break;
            case R.id.nav_ai_bot:

                break;
            case R.id.nav_about:
                //if(!(this instanceof AboutActivity)) {i = new Intent(this, AboutActivity.class);}
                break;
            case R.id.nav_feedback:
                //if(!(this instanceof FeedbackActivity)) {i = new Intent(this, FeedbackActivity.class);}
                break;
        }

        if (i != null) {
            startActivity(i);
            finish();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}