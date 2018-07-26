package com.example.ptut.ptodo.roomdb.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by Ptut on 3/1/2018.
 */

@Entity(tableName = ToDoItems.TABLE_NAME)
public class ToDoItems implements Serializable{

    public static final String TABLE_NAME="todoitems";
    public static final String COLUMN_ID ="id";
    public static final String TODO_TITLE="todo_title";
    public static final String TODO_DESC="todo_description";
    public static final String TODO_DATE="todo_date";
    public static final String TODO_PRIORITY="todo_priority";
    public static final String TODO_TIME="todo_time";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = TODO_TITLE)
    public String todo_title;

    @ColumnInfo(name = TODO_DESC)
    public String todo_description;

    @ColumnInfo(name = TODO_DATE)
    public String todo_date;

    @ColumnInfo(name = TODO_PRIORITY)
    public String todo_priority;

    @ColumnInfo(name = TODO_TIME)
    public  String todo_time;

    public ToDoItems() {
    }

    public ToDoItems(String todo_title, String todo_description, String todo_date, String todo_priority, String todo_time) {
        this.todo_title = todo_title;
        this.todo_description = todo_description;
        this.todo_date = todo_date;
        this.todo_priority = todo_priority;
        this.todo_time = todo_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodo_title() {
        return todo_title;
    }

    public void setTodo_title(String todo_title) {
        this.todo_title = todo_title;
    }

    public String getTodo_description() {
        return todo_description;
    }

    public void setTodo_description(String todo_description) {
        this.todo_description = todo_description;
    }

    public String getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(String todo_date) {
        this.todo_date = todo_date;
    }

    public String getTodo_priority() {
        return todo_priority;
    }

    public void setTodo_priority(String todo_priority) {
        this.todo_priority = todo_priority;
    }

    public String getTodo_time() {
        return todo_time;
    }

    public void setTodo_time(String todo_time) {
        this.todo_time = todo_time;
    }

    /**
     * Create a new {@link ToDoItems} from the specified {@link ContentValues}.
     *
     * @return A newly created {@link ToDoItems} instance.
     */
    public static ToDoItems fromContentValues(ContentValues values) {
        final ToDoItems toDoItems = new ToDoItems();
        if (values.containsKey(COLUMN_ID)) {
            toDoItems.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(TODO_TITLE)) {
            toDoItems.todo_title = values.getAsString(TODO_TITLE);
        }
        if (values.containsKey(TODO_DESC)) {
            toDoItems.todo_description = values.getAsString(TODO_DESC);
        }
        if (values.containsKey(TODO_DATE)) {
            toDoItems.todo_date= values.getAsString(TODO_DATE);
        }
        if (values.containsKey(TODO_PRIORITY)) {
            toDoItems.todo_priority = values.getAsString(TODO_PRIORITY);
        }
        if (values.containsKey(TODO_TIME)) {
            toDoItems.todo_time = values.getAsString(TODO_TIME);
        }
        return toDoItems;
    }
}
