package com.zhilyn.app.wumpusworld.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.zhilyn.app.wumpusworld.R;

/**
 * Created by Deep on 5/4/16.
 * welcome screen
 */
public class WelcomeActivity  extends Activity {
    private static final String TAG = "Splash";
    private static final String PREF_VISITED = "visited-welcome-page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        SharedPreferences prefs = getSharedPreferences(this.getClass().getSimpleName(), MODE_PRIVATE);
        if(prefs.getBoolean(PREF_VISITED, false)){
            Intent i = new Intent(this, AIActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_welcome);
    }
}