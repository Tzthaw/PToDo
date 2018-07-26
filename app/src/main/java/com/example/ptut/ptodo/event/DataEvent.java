package com.example.ptut.ptodo.event;

import com.example.ptut.ptodo.roomdb.entity.ToDoItems;

import java.util.ArrayList;

/**
 * Created by Ptut on 2/20/2018.
 */

public class DataEvent {
    public static class DataLoadedEvent{
        ArrayList<ToDoItems> toDoItems;

        public DataLoadedEvent(ArrayList<ToDoItems> toDoItems) {
            this.toDoItems = toDoItems;
        }

        public ArrayList<ToDoItems> getToDoItems() {
            return toDoItems;
        }

        public void setToDoItems(ArrayList<ToDoItems> toDoItems) {
            this.toDoItems = toDoItems;
        }
    }

    public static class DataLoadedErrorEvent{
        String msg;

        public DataLoadedErrorEvent(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
