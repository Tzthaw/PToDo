package com.example.ptut.ptodo.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.activity.EventToDoActivity;

public class AlarmReceiver extends BroadcastReceiver {
    int notifyId=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"Alarm has been set",Toast.LENGTH_SHORT).show();
    /*Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Ringtone r = RingtoneManager.getRingtone(context, notification);
    r.play();*/
        NotificationCompat.Builder mNotify=new NotificationCompat.Builder(context);
        mNotify.setSmallIcon(R.drawable.ptodologo);
        mNotify.setContentTitle("Coding");
        mNotify.setContentText("INVENTO: Coding competition is going to be conducted today.");
        Intent resultIntent=new Intent(context,EventToDoActivity.class);
        TaskStackBuilder stackBuilder= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(EventToDoActivity.class); //add the to-be-displayed activity to the top of stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            mNotify.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notifyId,mNotify.build());
        }

    }
}