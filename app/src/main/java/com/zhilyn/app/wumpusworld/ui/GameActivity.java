package com.zhilyn.app.wumpusworld.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.zhilyn.app.wumpusworld.GameListAdapter;
import com.zhilyn.app.wumpusworld.R;

public class GameActivity extends BaseNavActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GameListAdapter adapter;
    private ImageButton up, down, right, left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupButtons();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new GameListAdapter();

        recyclerView.setAdapter(adapter);
    }

    private void setupButtons() {
        up = (ImageButton) findViewById(R.id.up);
        down = (ImageButton) findViewById(R.id.down);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    protected int getSelfInNav() {
        return R.id.nav_game;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up:

                break;
            case R.id.down:

                break;
            case R.id.right:

                break;
            case R.id.left:

                break;
        }
    }
}
