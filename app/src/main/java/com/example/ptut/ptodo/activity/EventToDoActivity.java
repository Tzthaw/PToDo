package com.example.ptut.ptodo.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.example.ptut.ptodo.R;
import com.example.ptut.ptodo.controller.TodoListItemController;
import com.example.ptut.ptodo.fragment.EventFragment;
import com.example.ptut.ptodo.fragment.FragmentOpenNote;
import com.example.ptut.ptodo.fragment.Fragment_ToDoDetail;
import com.example.ptut.ptodo.model.ToDoItemModel;
import com.example.ptut.ptodo.provider.ToDoContentProvider;
import com.example.ptut.ptodo.roomdb.entity.ToDoItems;
import com.example.ptut.ptodo.util.DetailsTransition;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventToDoActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,TodoListItemController {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ToDoItems toDoItems;

    private static final int LOADER_TODO = 1;
    private static String TAG = EventToDoActivity.class.getSimpleName();
    public static final String NEWNOTE = "newnote";
    public static final String ITEM = "item";
    public static final String TODO_ITEM="todoitems";
    public static boolean ishome=true;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ToDoItemModel toDoItemModel;
    Fragment fragment;
    String fragName;

    public static void getNewNote(Context context,String Data){
        Intent intent=new Intent(context,EventToDoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("KEY",Data);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void getItems(Context context,String Data,ToDoItems toDoItems){
        Intent intent=new Intent(context,EventToDoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("KEY",Data);
        bundle.putSerializable(TODO_ITEM,toDoItems);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void newInstance(Context context){
        context.startActivity(new Intent(context,EventToDoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        ButterKnife.bind(this,this);


        toDoItemModel= ViewModelProviders.of(this).get(ToDoItemModel.class);
        toDoItemModel.initDB(getApplicationContext());

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(TAG);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fragName = extras.getString("KEY");
            if(fragName.equalsIgnoreCase(NEWNOTE)){
                fragment=FragmentOpenNote.newInstance();
            }else if(fragName.equalsIgnoreCase(ITEM)){
                 toDoItems= (ToDoItems) extras.get(TODO_ITEM);
              fragment=Fragment_ToDoDetail.getInstanceFragment(toDoItems);
                Bundle bundle = new Bundle();
                bundle.putSerializable("todoobj",toDoItems);
                fragment.setArguments(bundle);
            }

            if (fragment!=null){
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_frame, fragment);
                fragmentTransaction.commit();
            }

        } else {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_frame, EventFragment.newInstance());
            fragmentTransaction.commit();

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(ishome)
            {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
                startActivity(intent);
                finish();
            }else {
                ishome=true;
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_frame, EventFragment.newInstance()).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (id) {
            case R.id.create_note:
                ishome=false;
                fragment = FragmentOpenNote.newInstance();
                title = getString(R.string.nav_new_note);
                break;
            case R.id.open_note:
                ishome=true;
                fragment = EventFragment.newInstance();
                title = getString(R.string.nav_item_home);
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_frame, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onToDoItemControl(ToDoItems toDoItems, ImageView imageView) {

        Fragment_ToDoDetail fragment=Fragment_ToDoDetail.getInstanceFragment(toDoItems);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());

        }
        ishome=false;
        Bundle bundle = new Bundle();
        bundle.putSerializable("todoobj",toDoItems);
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addSharedElement(imageView,"sharedImage");
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.commit();

    }

}