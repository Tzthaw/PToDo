package com.example.ptut.ptodo.viewholder;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.controller.TodoListItemController;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.util.UtilGeneral;
import com.example.ptut.ptodo.viewholder.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ptut on 2/20/2018.
 */

public class EventToDoViewHolder extends BaseViewHolder<ToDoItems> {
    @BindView(R.id.priority_view)
    ImageView priorityView;
    @BindView(R.id.priority_viewLeft)
    ImageView priorityViewLeft;
    @BindView(R.id.todo_title)
    TextView todoTitle;
    @BindView(R.id.todo_desc)
    TextView todoDesc;
    @BindView(R.id.date_card)
    TextView todoDate;
    @BindView(R.id.todolist_task)
    ImageView task;


    Context context;
    TodoListItemController controller;
    ToDoItems toDoItems;

    public EventToDoViewHolder(View itemView, Context context, TodoListItemController controller) {
        super(itemView);
        this.context = context;
        this.controller = controller;
    }

    @Override
    public void bind(ToDoItems data, int totalSize, int position) {
        toDoItems = data;
        if (data.getTodo_priority() == null) {
            priorityView.setBackgroundResource(R.color.silver);
            priorityViewLeft.setBackgroundResource(R.color.silver);
        } else {
            UtilGeneral.checkPriorityLabel(data.getTodo_priority(), priorityView);
            UtilGeneral.checkPriorityLabel(data.getTodo_priority(), priorityViewLeft);
        }
        todoTitle.setText(data.getTodo_title());
        todoDesc.setText(data.getTodo_description());
        todoDate.setText(data.getTodo_date());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                ViewCompat.setTransitionName(task, "sharedImage");
                controller.onToDoItemControl(toDoItems,task);
                break;
        }
    }
}
