package com.zhilyn.app.wumpusworld.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zhilyn.app.wumpusworld.GameListAdapter;
import com.zhilyn.app.wumpusworld.R;

public class GameActivity extends BaseNavActivity {

    private RecyclerView recyclerView;
    private GameListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new GameListAdapter();

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getSelfInNav() {
        return R.id.nav_game;
    }
}
