package com.zhilyn.app.wumpusworld.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zhilyn.app.wumpusworld.ListAdapter;
import com.zhilyn.app.wumpusworld.R;

/**
 * this activity represents a page that has the solution to the game
 * */
public class AIActivity extends BaseNavActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    FloatingActionButton fabStart;
    RecyclerView recyclerview;
    Toolbar toolbar;

    private ListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabClear;
    private TextView solutionText;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        solutionText = (TextView) findViewById(R.id.solution);

        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new ListAdapter();
        final RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
        RecyclerView.LayoutManager lm = new GridLayoutManager(AIActivity.this, 4, LinearLayoutManager.VERTICAL, false);

        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(lm);
        recyclerview.setItemAnimator(animator);

        fabStart = (FloatingActionButton) findViewById(R.id.fab_start);
        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solveGame(v);
            }
        });

        fabClear = (FloatingActionButton) findViewById(R.id.fab_stop);
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void clear() {
        solutionText.setText(null);
    }

    private void solveGame(View v) {
        solutionText.setText(null);
        adapter.solve(solutionText);
        //Snackbar.make(fabStart, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        adapter = new ListAdapter();
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        solutionText.setText("");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int getSelfInNav() {
        return R.id.nav_ai_bot;
    }
}
