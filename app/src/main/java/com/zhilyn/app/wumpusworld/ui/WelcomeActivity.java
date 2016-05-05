package com.zhilyn.app.wumpusworld.ui;

import android.app.Activity;
import android.os.Bundle;

import com.zhilyn.app.wumpusworld.R;

/**
 * Created by Deep on 5/4/16.
 * welcome screen
 */
public class WelcomeActivity  extends Activity {
    private static final String TAG = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_welcome);

    }
}