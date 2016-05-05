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
    private static final String PREF_VISITED = "visited-welcome-page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setResult(RESULT_OK);

    }
}