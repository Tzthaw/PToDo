package com.example.ptut.ptodo.fragment;

import android.app.ActionBar;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.fragment.base.BaseFragment;
import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.util.UtilGeneral;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ptut on 3/5/2018.
 */

public class Fragment_ToDoDetail extends BaseFragment {

    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_date)
    TextView detailDate;
    @BindView(R.id.detail_priority)
    TextView detailPriority;
    @BindView(R.id.detail_time)
    TextView detailTime;
    @BindView(R.id.detail_desc)
    TextView detailDesc;
    @BindView(R.id.edit_fab)
    FloatingActionButton floatingActionButton;


    public static Fragment_ToDoDetail fragment_toDoDetail;
    public ToDoItemModel toDoItemModel;
    public static String INTENTSTRING="todoobj";

    public static Fragment_ToDoDetail getInstanceFragment(ToDoItems toDoItems){
        if(fragment_toDoDetail==null){
            fragment_toDoDetail=new Fragment_ToDoDetail();

        }
        return fragment_toDoDetail;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_event_profile,container,false);
        ButterKnife.bind(this,v);


//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
           ToDoItems toDoItems = (ToDoItems) bundle.get(INTENTSTRING);
            addData(toDoItems);
        }

        return v;
    }

    public void addData(ToDoItems toDoItems){
        UtilGeneral.checkNullTextView(detailTitle,toDoItems.getTodo_title());
        UtilGeneral.checkNullTextView(detailDate,toDoItems.getTodo_date());
        UtilGeneral.checkNullTextView(detailPriority,toDoItems.getTodo_priority());
        UtilGeneral.checkNullTextView(detailTime,toDoItems.getTodo_time());
        UtilGeneral.checkNullTextView(detailDesc,toDoItems.getTodo_description());
    }


}
