package com.example.ptut.ptodo.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.ptut.ptodo.event.DataEvent;
import com.example.ptut.ptodo.roomdb.AppDatabase;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Ptut on 3/1/2018.
 */

public class ToDoItemModel extends ViewModel {

    public static ToDoItemModel toDoItemModel;

    AppDatabase appDatabase;
    public ToDoItemModel() {

    }
    public static ToDoItemModel getInstance(){
        if(toDoItemModel==null){
            toDoItemModel=new ToDoItemModel();
        }
        return toDoItemModel;
    }
    public void initDB(Context context){
        appDatabase=AppDatabase.getAppInstance(context);
    }

    @Override
    protected void onCleared() {
        appDatabase.destoryInstance();
    }

    public void addToDoItems(ToDoItems toDoItems){
        appDatabase.toDoDao().addToDoItems(toDoItems);
    }

    public  LiveData<List<ToDoItems>> getToDoList(){
        return appDatabase.toDoDao().getToDoList();
    }

    public void deleteToDoItem(ToDoItems toDoItems){
        appDatabase.toDoDao().deleteItem(toDoItems);
    }
    public void updateItem(ToDoItems toDoItems){
        appDatabase.toDoDao().updateItem(toDoItems);
    }


}
