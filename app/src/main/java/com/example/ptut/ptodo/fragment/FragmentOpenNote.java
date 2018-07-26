package com.example.ptut.ptodo.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.event.DataEvent;
import com.example.ptut.ptodo.fragment.base.BaseFragment;
import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.provider.ToDoContentProvider;
import com.example.ptut.ptodo.roomdb.daos.ToDoDao;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.util.TagList;
import com.example.ptut.ptodo.util.UtilGeneral;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Ptut on 2/23/2018.
 */

public class FragmentOpenNote extends LifecycleFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.date_range)
    ImageButton dateRange;
    @BindView(R.id.postEdit)
    EditText postEdit;
    @BindView(R.id.time_text)
    TextView timePick;
    @BindView(R.id.date_text)
    TextView datePick;
    @BindView(R.id.title_text)
    TextView titlePick;
    @BindView(R.id.label_text)
    TextView labelPick;



    String dateString, TimeString, labelString, TitleString,PostText = null;

    int Year, Month, Day, Hour, Minute = 0;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    ToDoItemModel toDoItemModel;
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "Jun",
            "July", "Auguest", "September", "October", "November", "December"};
    Calendar calendar;



    public static FragmentOpenNote objInstance;

    public static FragmentOpenNote newInstance() {
        if (objInstance == null) {
            objInstance = new FragmentOpenNote();
        }
        return objInstance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_eventnew, container, false);

        ButterKnife.bind(this, v);

        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        toDoItemModel= ViewModelProviders.of(this).get(ToDoItemModel.class);
        toDoItemModel.initDB(getContext());

        postEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(postEdit, InputMethodManager.SHOW_IMPLICIT);


        return v;
    }

    @OnClick(R.id.time_pick)
    public void onTimeClick() {
        timePickerDialog = TimePickerDialog.newInstance(FragmentOpenNote.this, Hour, Minute, false);
        // Show a timepicker when the timeButton is clicked

        timePickerDialog.setThemeDark(false);
        timePickerDialog.setAccentColor(Color.parseColor("#2c3e50"));
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show(getActivity().getFragmentManager(), "TimePickerDialog");

    }

    @OnClick(R.id.date_range)
    public void OnClick() {
        datePickerDialog = DatePickerDialog.newInstance(FragmentOpenNote.this, Year, Month, Day);

        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2c3e50"));
        datePickerDialog.setTitle("Select Date ");
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

    }

    @OnClick(R.id.title_pick)
    public void OnTitleClick() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_title, null);

        final EditText customText = (EditText) customView.findViewById(R.id.custom_edit_text);
        Button confirm = (Button) customView.findViewById(R.id.custom_button);

            final MaterialStyledDialog materialStyledDialog = new MaterialStyledDialog.Builder(getContext())
                    .setDescription("")
                    .setHeaderDrawable(R.drawable.nav_night)
                    .setCustomView(customView)
                    .show();
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialStyledDialog.dismiss();
                    if(!titlePick.getText().toString().equals(null)){
                        titlePick.setVisibility(View.VISIBLE);
                        TitleString=customText.getText().toString();
                        titlePick.setText(TitleString);
                    }else{
                        titlePick.setVisibility(View.GONE);
                    }
                    UtilGeneral.confirmDialog(getContext(),TitleString,titlePick);

                }
            });


    }


    @OnClick(R.id.file_upload)
    public void OnUploadClick() {
        new MaterialStyledDialog.Builder(getContext())
                .setHeaderDrawable(R.drawable.dialog_icon)
                .setDescription("File Upload is coming Soon")
                .setPositiveText("Ok")
                .show();
    }

    @OnClick(R.id.priority_label)
    public void OnLabelPick() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.dialog_priority, null);

        final RadioGroup radioGroup = (RadioGroup) customView.findViewById(R.id.radio_group);
        new MaterialStyledDialog.Builder(getContext())
                .setDescription("")
                .setHeaderDrawable(R.drawable.profile)
                .setCustomView(customView)
                // You can also show the custom view with some padding in DP (left, top, right, bottom)
                //.setCustomView(customView, 20, 20, 20, 0)
                .setPositiveText("Ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        RadioButton radioButton = (RadioButton) customView.findViewById(selectedId);
                        if(!labelPick.getText().toString().equals(null)){
                            labelPick.setVisibility(View.VISIBLE);
                            labelString = radioButton.getText().toString();
                            labelPick.setText(labelString);
                        }else{
                            labelPick.setVisibility(View.GONE);
                        }


                    }
                })
                .show();
        UtilGeneral.confirmDialog(getContext(),labelString,labelPick);
    }

    @OnClick(R.id.confirm_btn)
    public void OnConfirmClick() {
        PostText=postEdit.getText().toString();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(postEdit.getWindowToken(), 0);
        if (!PostText.equalsIgnoreCase(null)) {
            postEdit.setText("");
            ToDoItems toDoItems=new ToDoItems(TitleString,PostText,dateString,labelString,TimeString);
            if(!toDoItems.equals(null)){
                ContentValues values = new ContentValues();
                values.put(ToDoItems.TODO_TITLE, TitleString);
                values.put(ToDoItems.TODO_DESC,PostText );
                values.put(ToDoItems.TODO_DATE, dateString);
                values.put(ToDoItems.TODO_PRIORITY, labelString);
                values.put(ToDoItems.TODO_TIME, TitleString);

              getContext().getContentResolver().insert(ToDoContentProvider.CONTENT_URI,values);



                UtilGeneral.showSnapBar(getView(), "Saved successfully", 2);
                Long time=getMiliSeconds(Year,Month,Day,Hour,Minute);
//                UtilGeneral.scheduleNotification(time,TitleString,PostText,getContext());
                if (time.equals(null) ) {
                    UtilGeneral.showSnapBar(getView(), "Invalid Date/Time.Please Re-enter", 1);
                }
                else{
                    UtilGeneral.scheduleNotification(getContext(),calendar,100,TitleString,PostText);
                }
            }
        }else{
            UtilGeneral.showSnapBar(getView(), "Saved not  successfully", 1);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Year=year;
        Month=monthOfYear;
        Day=dayOfMonth;
        dateString = dayOfMonth + "-" + MONTHS[monthOfYear]+ "-" + year;
       if(!datePick.getText().toString().equals(null)){
           datePick.setVisibility(View.VISIBLE);
           datePick.setText(dateString);
       }else{
           datePick.setVisibility(View.GONE);
       }
        UtilGeneral.confirmDialog(getContext(),dateString,datePick);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Hour=hourOfDay;
        Minute=minute;
        try {
            TimeString = UtilGeneral.convert24to12hour(hourOfDay + ":" + minute);
            if(!timePick.getText().toString().equals(null)){
                timePick.setVisibility(View.VISIBLE);
                timePick.setText(TimeString);
//                timePick.setTextColor(Color.WHITE);
//                timePick.setBackgroundColor(Color.parseColor(UtilGeneral.getRandomColor()));
            }else{
                timePick.setVisibility(View.GONE);
            }
            UtilGeneral.confirmDialog(getContext(),TimeString,timePick);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public  long getMiliSeconds(int year,int month,int dayofMonth,int hour,int minute){
        Calendar current = Calendar.getInstance();
       calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayofMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        if (calendar.compareTo(current) <= 0) {
            return 0;
        }
        else{
            return calendar.getTimeInMillis();
        }
    }


}
