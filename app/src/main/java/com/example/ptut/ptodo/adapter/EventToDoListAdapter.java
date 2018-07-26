package com.example.ptut.ptodo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.adapter.base.BaseRecyclerAdapter;
import com.example.ptut.ptodo.controller.TodoListItemController;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.viewholder.EventToDoViewHolder;

/**
 * Created by Ptut on 2/20/2018.
 */

public class EventToDoListAdapter  extends BaseRecyclerAdapter<EventToDoViewHolder,ToDoItems> {

    Context context;
    TodoListItemController controller;

    public EventToDoListAdapter(Context context,TodoListItemController controller) {
        super(context);
        mLayoutInflater= LayoutInflater.from(context);
        this.controller=controller;
    }

    @Override
    public EventToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mLayoutInflater.inflate(R.layout.activity_event_cards,parent,false);
        return new EventToDoViewHolder(v,context,controller);
    }

    @Override
    public void onBindViewHolder(EventToDoViewHolder holder, int position) {
        holder.bind(mData.get(position),mData.size(),position);
    }

    public void removeItem(int position){
        mData.remove(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position,mData.size());
    }
}
