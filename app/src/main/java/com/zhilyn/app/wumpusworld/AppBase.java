package com.zhilyn.app.wumpusworld;

import android.app.Application;

/**
 * Created by Deep on 5/4/16.
 * application base
 */
public class AppBase extends Application {

    public static final boolean DEBUG = true;

    public static AppBase instance;

    // uncaught exception handler variable
    private Thread.UncaughtExceptionHandler defaultUEH;
    // handler listener
    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            defaultUEH.uncaughtException(thread, ex);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        AppBase.instance = this;

        // setup handler for uncaught exception
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }
}