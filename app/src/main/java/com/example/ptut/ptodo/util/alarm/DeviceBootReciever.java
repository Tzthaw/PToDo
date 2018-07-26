package com.example.ptut.ptodo.util.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ptut on 3/8/2018.
 */
public class DeviceBootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                new SetAlarm().alarmSetter(context);
            }
        }
    }
}