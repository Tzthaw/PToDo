package com.example.ptut.ptodo.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.activity.EventToDoActivity;
import com.example.ptut.ptodo.util.alarm.NotificationPublisher;
import com.github.mrengineer13.snackbar.SnackBar;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Ptut on 2/26/2018.
 */

public class UtilGeneral {

    public class NotifyService extends Service {
        public NotifyService() {
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static Bitmap convertBitmapImage(Resources res, int id) {
        return BitmapFactory.decodeResource(res, id);

    }

    public static String convert24to12hour(String time) throws ParseException {


            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(time);
             return  _12HourSDF.format(_24HourDt);


    }

    public static String getRandomColor() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#ED7D31");
        colors.add("#00B0F0");
        colors.add("#FF0000");
        colors.add("#D0CECE");
        colors.add("#00B050");
        colors.add("#9999FF");
        colors.add("#FF5FC6");
        colors.add("#FFC000");
        colors.add("#7F7F7F");
        colors.add("#4800FF");

        return colors.get(new Random().nextInt(colors.size()));
    }


    public static void confirmDialog(final Context context, final String s, final TextView textView){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("\"" + s + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(null);
                        textView.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

    }

    public static void randomBackgroundTint(TextView textView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color=Color.parseColor(getRandomColor());
            textView.setBackgroundColor(color);
        }
    }

    public static void showSnapBar(View view,String message,int color){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            customToast(view.getContext(),message);
        } if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (color==1) {
                //red
                new SnackBar.Builder(getActivity(view))
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.pumpkin)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            } else if (color==2) {
                //yellow
                new SnackBar.Builder(getActivity(view))
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.turquoise)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            }
        }if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N && Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)  {
            if ( color==1) {
                //red
                new SnackBar.Builder((Activity)view.getContext())
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.pumpkin)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            } else if (color==2) {
                //yellow
                new SnackBar.Builder((Activity)view.getContext())
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.turquoise)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            }
        }else{
            if ( color==1) {
                //red
                new SnackBar.Builder((Activity)view.getContext())
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.pumpkin)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            } else if (color==2) {
                //yellow
                new SnackBar.Builder((Activity)view.getContext())
                        .withMessage(message)
                        .withTextColorId(R.color.white)
                        .withBackgroundColorId(R.color.turquoise)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            }

        }
    }

    public static void customToast(Context context,String message){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View customToastroot =inflater.inflate(R.layout.toast_layout_root, null);
        TextView text = (TextView) customToastroot.findViewById(R.id.toast_message);
        Toast customtoast=new Toast(context);
        text.setTextColor(context.getResources().getColor(R.color.white));
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,0, 10);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.show();
    }

//    public static Typeface converTypeface(View view){
//        Typeface font=null;
//        if(isZawgyi(view.getContext())){
//            font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/zawgyi.ttf");
//        }else if(isUni(view.getContext())){
//            font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/mm3.ttf");
//        }
//        return  font;
//    }
public static Activity getActivity(View view) {
    Activity activity = null;
    if (view.getContext().getClass().getName().contains("com.android.internal.policy.DecorContext")) {
        try {
            Field field = view.getContext().getClass().getDeclaredField("mPhoneWindow");
            field.setAccessible(true);
            Object obj = field.get(view.getContext());
            java.lang.reflect.Method m1 = obj.getClass().getMethod("getContext");
            activity = (Activity) (m1.invoke(obj));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        activity = (Activity) view.getContext();
    }
    return activity;
}

public static void checkUrl(String s){
    String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    Pattern p = Pattern.compile(URL_REGEX);
    Matcher m = p.matcher(s);//replace with string to compare
    if(m.find()) {
        System.out.println("String contains URL");
    }
}
public void LoadDialogUrl(Context context){
    AlertDialog.Builder alert = new AlertDialog.Builder(context);
    alert.setTitle("Preview");

    WebView wv = new WebView(context);
    wv.loadUrl("http:\\www.google.com");
    wv.setWebViewClient(new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    });

    alert.setView(wv);
    alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    });
    alert.show();
}
public static  void checkPriorityLabel(String s, ImageView imageView){
    switch (s){
        case "Very Important": imageView.setBackgroundResource(R.color.alizarin);break;
        case "Important":imageView.setBackgroundResource(R.color.carrot);break;
        case  "Normal": imageView.setBackgroundResource(R.color.sunflower);break;
        default:imageView.setBackgroundResource(R.color.sunflower);break;
    }
}
    public String getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }
    public static void checkNullTextView(TextView textView,String s){
        if(!(s==null)){
            textView.setVisibility(View.VISIBLE);
            textView.setText(s);
        }else{
            textView.setText("empty");
            textView.setTextColor(Color.GRAY);
            textView.setAlpha(0.5f);
        }
    }

    public static  void shareView(Context context,String title,String desc){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, desc);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public static void scheduleNotification(Context context, Calendar calendar, int notificationId,String title,String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ptodologo)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.splash5)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, EventToDoActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }



}

