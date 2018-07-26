package com.example.ptut.ptodo.activity;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.ptut.ptodo.R;

/**
 * Created by Ptut on 3/6/2018.
 */

public class ToDoSplash  extends AppCompatActivity{

    Handler handler;
    Runnable callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        handler = new Handler();
        callback = new Runnable() {
            @Override
            public void run() {

                goMainActivity();
            }
        };
        handler.postDelayed(callback, 2000);

    }
    public void goMainActivity() {
          EventToDoActivity.newInstance(this);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(callback);
    }
}
