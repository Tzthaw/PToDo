package com.example.ptut.ptodo.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.adapter.EventToDoListAdapter;
import com.example.ptut.ptodo.controller.TodoListItemController;
import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.provider.ToDoContentProvider;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.widget.WidgetDataProvider;
import com.example.ptut.ptodo.widget.WidgetProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ptut on 2/23/2018.
 */

public class EventFragment extends LifecycleFragment {
    @BindView(R.id.note_recycler)
    RecyclerView noteRecycler;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    ToDoItemModel toDoItemModel;
    EventToDoListAdapter adapter;
    List<ToDoItems> toDoItemsList=new ArrayList<>();
    private Paint p = new Paint();


    public static EventFragment objInstance;
    TodoListItemController tcontroller;
    private static final int LOADER_TODO = 1;



    public static EventFragment newInstance(){
        if(objInstance==null){
            objInstance=new EventFragment();
        }
        return objInstance;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tcontroller=(TodoListItemController)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this,v);

        emptyLayout.setVisibility(View.VISIBLE);

        toDoItemModel= ViewModelProviders.of(this).get(ToDoItemModel.class);
        toDoItemModel.initDB(getContext());


        noteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new EventToDoListAdapter(getContext(),tcontroller);
        noteRecycler.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    p.setColor(Color.parseColor("#F44336"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.deleteicon);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure delete this?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final int position = viewHolder.getAdapterPosition(); //swiped position
                            adapter.removeItem(position);
                            adapter.clearData();
//                            getContext().getContentResolver().delete(ToDoContentProvider.CONTENT_URI.buildUpon().appendPath(String.valueOf(ToDoItems.COLUMN_ID)).build(),null,null);
                            toDoItemModel.deleteToDoItem(toDoItemsList.get(position));
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             int pos = viewHolder.getAdapterPosition();
                            adapter.notifyItemChanged(pos);
                        }
                    });
                    builder.show();

                }
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(noteRecycler);


        toDoItemModel.getToDoList().observe(this, new Observer<List<ToDoItems>>() {
            @Override
            public void onChanged(@Nullable List<ToDoItems> toDoItems) {
                if(toDoItems.size()==0){
                    noteRecycler.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                }else if(toDoItems.size()>0){
                    noteRecycler.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);

                }
                toDoItemsList=toDoItems;
                adapter.appendNewDatas(toDoItems);

            }
        });

       // getActivity().getSupportLoaderManager().initLoader(LOADER_TODO, null, mLoaderCallbacks);

        return v;
    }

   /* private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_TODO:
                            return new CursorLoader(getContext(),
                                    ToDoContentProvider.URI_TODOITEMS,
                                    new String[]{ToDoItems.TODO_TITLE,ToDoItems.TODO_DESC,ToDoItems.TODO_DATE,
                                            ToDoItems.TODO_PRIORITY,ToDoItems.TODO_TIME},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case LOADER_TODO:

                            WidgetProvider.sendRefreshBroadcast(getContext());
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_TODO:

                            break;
                    }
                }

            };
*/


}
