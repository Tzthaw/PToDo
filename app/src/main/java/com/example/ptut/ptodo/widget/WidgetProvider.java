package com.example.ptut.ptodo.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.activity.EventToDoActivity;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "com.dharmangsoni.widgets.ACTION_TOAST";
    public static final String EXTRA_STRING = "com.dharmangsoni.widgets.EXTRA_STRING";
    private static final String MyOnClick = "myOnClickTag";



    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, WidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetCollectionList);
        }
        if (intent.getAction().equals(ACTION_TOAST)) {
            String item = intent.getExtras().getString(EXTRA_STRING);
            ToDoItems toDoItems = (ToDoItems) intent.getExtras().get(EventToDoActivity.TODO_ITEM);
            EventToDoActivity.getItems(context,EventToDoActivity.ITEM,toDoItems);
        }else if(intent.getAction().equals(MyOnClick)){
            Toast.makeText(context,"Create new note", Toast.LENGTH_LONG).show();
            EventToDoActivity.getNewNote(context,EventToDoActivity.NEWNOTE);
        }
        super.onReceive(context, intent);
    }


    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, WidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

            // Adding collection list item handler
            final Intent onItemClick = new Intent(context, WidgetProvider.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.widgetCollectionList,
                    onClickPendingIntent);


            appWidgetManager.updateAppWidget(widgetId, mView);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_provider_layout);

        Intent intent= new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);

        mView.setOnClickPendingIntent(R.id.note_new,
                getPendingSelfIntent(context, MyOnClick));


        return mView;
    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


}