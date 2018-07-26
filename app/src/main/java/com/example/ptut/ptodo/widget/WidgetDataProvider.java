package com.example.ptut.ptodo.widget;

/**
 * Created by Ptut on 3/16/2018.
 */

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleService;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.activity.EventToDoActivity;
import com.example.ptut.ptodo.fragment.EventFragment;
import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.provider.ToDoContentProvider;
import com.example.ptut.ptodo.roomdb.AppDatabase;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory, Loader.OnLoadCompleteListener<Cursor>{

    List<ToDoItems> mCollections = new ArrayList();
    public static List<ToDoItems> mDatasFromDB = new ArrayList();
    ToDoItemModel toDoItemModel;
    private static final int LOADER_TODO = 1;


    Context mContext = null;
    Intent intent;
    AppDatabase appDatabase;

    CursorLoader mCursorLoader;


    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        this.intent = intent;

    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }


    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_item_2);

        String title = mCollections.get(position).getTodo_title();
        String desc = mCollections.get(position).getTodo_description();
        String date =mCollections.get(position).getTodo_date();
        String priority =mCollections.get(position).getTodo_priority();
        String time =mCollections.get(position).getTodo_time();

        mView.setTextViewText(R.id.todo_title, title);
        mView.setTextViewText(R.id.todo_desc, desc);
        mView.setImageViewResource(R.id.profile, R.drawable.ic_date);
        mView.setTextViewText(R.id.date_card, date);
        switch (priority) {
            case "Very Important":
                mView.setImageViewResource(R.id.priority_viewLeft, R.color.alizarin);
                break;
            case "Important":
                mView.setImageViewResource(R.id.priority_viewLeft, R.color.carrot);
                ;
                break;
            case "Normal":
                mView.setImageViewResource(R.id.priority_viewLeft, R.color.sunflower);
                ;
                break;
            default:
                mView.setImageViewResource(R.id.priority_viewLeft, R.color.sunflower);
                ;
                break;
        }
        mView.setImageViewResource(R.id.todolist_task, R.drawable.ic_done);

        mView.setTextColor(R.id.todo_desc, Color.BLACK);
        mView.setTextColor(R.id.date_card, Color.BLACK);

        ToDoItems toDoItems = new ToDoItems(title, desc, date, priority, time);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(WidgetProvider.EXTRA_STRING,
                mCollections.get(position).toString());
        bundle.putSerializable(EventToDoActivity.TODO_ITEM, toDoItems);
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.view_layout, fillInIntent);

        return mView;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        mCursorLoader = new CursorLoader(mContext, ToDoContentProvider.URI_TODOITEMS,
                null,
                null,
                null,
                null);
        mCursorLoader.registerListener(11,this);
        mCursorLoader.startLoading();
        init();
    }

    @Override
    public void onDataSetChanged() {
      init();
    }

    public void init(){
        mCollections=new ArrayList<>();
       /* for (int i = 1; i <= 11; i++) {
            mCollections.add(new ToDoItems("ListView item"+i,"","","",""));
        }*/
        for (int i = 0; i < mDatasFromDB.size(); i++) {
            ToDoItems items=mDatasFromDB.get(i);
            mCollections.add(mDatasFromDB.get(i));
        }

    }

    @Override
    public void onDestroy() {
        if (mCursorLoader != null) {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
    }

    @Override
    public void onLoadComplete(@NonNull Loader<Cursor> loader, @Nullable Cursor mCursor) {
        mDatasFromDB=new ArrayList<>();
        if(mCursor !=null && mCursor.moveToFirst()){
            do{
                String title = mCursor.getString(1);
                String desc = mCursor.getString(2);
                String date = mCursor.getString(3);
                String priority = mCursor.getString(4);
                String time = mCursor.getString(5);
                ToDoItems item = new ToDoItems(title, desc, date, priority, time);

                mDatasFromDB.add(item);
            }while(mCursor.moveToNext());
            onDataSetChanged();
            WidgetProvider.sendRefreshBroadcast(mContext);
        }
    }
}
