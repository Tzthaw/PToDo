package com.example.ptut.ptodo.fragment.base;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.ptut.ptodo.event.DataEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;



public abstract class BaseFragment extends LifecycleFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            readArguments(bundle);
        }

        if (savedInstanceState != null) {
            readArguments(savedInstanceState);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void readArguments(Bundle bundle) {

    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onEvent(DataEvent.DataLoadedEvent event) {

    }
}
