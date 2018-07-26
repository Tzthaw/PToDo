package com.example.ptut.ptodo.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ptut.ptodo.roomdb.daos.ToDoDao;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

/**
 * Created by Ptut on 2/20/2018.
 */

@Database(entities = {ToDoItems.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase appInstance;
    private static String DB="ToDoDB";
    public abstract ToDoDao toDoDao();

    public static AppDatabase getAppInstance(Context context){
        if(appInstance==null){
            appInstance= Room.databaseBuilder(context,AppDatabase.class,DB)
                    .allowMainThreadQueries()
                    .build();
        }
        return appInstance;
    }

    public static void switchToInMemory(Context context) {
        appInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                AppDatabase.class).build();
    }

    public  void destoryInstance(){
        appInstance=null;
    }




}
