package com.zhilyn.app.wumpusworld.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhilyn.app.wumpusworld.AIListAdapter;
import com.zhilyn.app.wumpusworld.R;

/**
 * this activity represents a page that has the solution to the game
 */
public class AIActivity extends BaseNavActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String PREF_VISITED = "pref-visited";
    private static final String PREF = "pref";
    private static final int WELCOME_REQUEST = 100;

    FloatingActionButton fabStart;
    RecyclerView recyclerview;
    Toolbar toolbar;

    private AIListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabClear;
    private TextView solutionText;
    private RelativeLayout solutionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        solutionText = (TextView) findViewById(R.id.solution);
        solutionContainer = (RelativeLayout) findViewById(R.id.container_solution);

        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AIListAdapter();
        final RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
        RecyclerView.LayoutManager lm = new GridLayoutManager(AIActivity.this, 4, LinearLayoutManager.VERTICAL, false);

        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(lm);
        recyclerview.setItemAnimator(animator);

        fabStart = (FloatingActionButton) findViewById(R.id.fab_start);
        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCard();
                solveGame();
            }
        });

        fabClear = (FloatingActionButton) findViewById(R.id.fab_stop);
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCard();
                clear();
            }
        });

        SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
        if (!prefs.getBoolean(PREF_VISITED, false)) {
            startActivityForResult(new Intent(this, WelcomeActivity.class), WELCOME_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == WELCOME_REQUEST){
                SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean(PREF_VISITED, true);
                edit.apply();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void showCard() {
        if(solutionContainer.getVisibility() == View.VISIBLE){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator animator = ViewAnimationUtils.createCircularReveal(solutionContainer,
                    solutionContainer.getWidth() / 2,
                    solutionContainer.getHeight(),
                    0,
                    (float) Math.hypot(solutionContainer.getWidth(), solutionContainer.getHeight()));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    solutionContainer.setVisibility(View.VISIBLE);
                }
            });
            animator.setDuration(250);
            animator.start();
        } else {
            solutionContainer.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    solutionContainer.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void hideCard() {
        if(solutionContainer.getVisibility() == View.INVISIBLE){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator animatorHide = ViewAnimationUtils.createCircularReveal(solutionContainer,
                    solutionContainer.getWidth() / 2,
                    solutionContainer.getHeight(),
                    (float) Math.hypot(solutionContainer.getWidth(), solutionContainer.getHeight()),
                    0);
            animatorHide.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    solutionContainer.setVisibility(View.INVISIBLE);
                }
            });
            animatorHide.setDuration(250);
            animatorHide.start();
        } else {
            solutionContainer.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    solutionContainer.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private void clear() {
        solutionText.setText(null);
    }

    private void solveGame() {
        solutionText.setText(null);
        adapter.solve(solutionText);
        //Snackbar.make(fabStart, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rules) {
            startActivity(new Intent(AIActivity.this, RulesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        hideCard();
        clear();
        adapter = new AIListAdapter();
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int getSelfInNav() {
        return R.id.nav_ai_bot;
    }
}
