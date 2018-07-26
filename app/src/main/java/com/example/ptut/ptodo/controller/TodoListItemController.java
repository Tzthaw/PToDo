package com.example.ptut.ptodo.controller;

import android.widget.ImageView;

import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

/**
 * Created by Ptut on 3/5/2018.
 */

public interface TodoListItemController {
    void onToDoItemControl(ToDoItems toDoItems, ImageView imageView);
}
