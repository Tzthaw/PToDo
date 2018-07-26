package com.example.ptut.ptodo.roomdb.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ptut on 3/1/2018.
 */

@Dao
public interface ToDoDao {

    @Query("SELECT COUNT(*) FROM " + ToDoItems.TABLE_NAME)
    int count();


    @Query("SELECT * FROM  "+ToDoItems.TABLE_NAME+" ORDER BY "+ ToDoItems.COLUMN_ID+" DESC")
    LiveData<List<ToDoItems>> getToDoList();

    @Query("DELETE  FROM "+ToDoItems.TABLE_NAME)
    void deleteAllItems();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addToDoItem(ToDoItems toDoItems);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] addToDoItems(ToDoItems... toDoItems);

    @Delete
    void deleteItem(ToDoItems toDoItems);

    @Update
    int updateItem(ToDoItems toDoItems);


    @Query("SELECT * FROM " + ToDoItems.TABLE_NAME+" ORDER BY "+ ToDoItems.COLUMN_ID+" DESC")
    Cursor selectAllCursor();


    @Query("SELECT * FROM " + ToDoItems.TABLE_NAME + " WHERE " + ToDoItems.COLUMN_ID + " = :id")
    Cursor selectByIdCursor(long id);


    @Query("DELETE FROM " + ToDoItems.TABLE_NAME + " WHERE " + ToDoItems.COLUMN_ID + " = :id")
    int deleteById(long id);





}
