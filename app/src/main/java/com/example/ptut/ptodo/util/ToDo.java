package com.example.ptut.ptodo.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ptut on 3/21/2018.
 */

public class ToDo extends Application {
    public static final String TAG = "ToDoApp";

    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
