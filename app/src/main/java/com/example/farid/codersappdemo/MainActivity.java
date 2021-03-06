package com.example.farid.codersappdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorLong;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.example.farid.codersappdemo.contest.main_contest;
import com.example.farid.codersappdemo.friends.friend_list;
import com.example.farid.codersappdemo.login.signin_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    private int prev_position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("DashBoard",R.drawable.material_background));
        menuItems.add(new MenuItem("Contests",R.drawable.material_background));
        menuItems.add(new MenuItem("Friends",R.drawable.material_background));
        menuItems.add(new MenuItem("Ranking",R.drawable.material_background));
        menuItems.add(new MenuItem("Calculator",R.drawable.material_background));
        menuItems.add(new MenuItem("My Profile",R.drawable.material_background));
        menuItems.add(new MenuItem("About",R.drawable.material_background));
        menuItems.add(new MenuItem("Logout",R.drawable.material_background));
        menuItems.add(new MenuItem("Exit",R.drawable.material_background));


        sNavigationDrawer.setMenuItemList(menuItems);


        String type = getIntent().getStringExtra("type");
        sNavigationDrawer.setAppbarColor(Color.parseColor("#FF1A1A1A"));
        sNavigationDrawer.setAppbarTitleTextColor(Color.parseColor("#FFFFFFFF"));
        sNavigationDrawer.setMenuiconTintColor(Color.parseColor("#FFFFFFFF"));

        if(type != null) {
            switch (type) {
                case "contests": {
                    prev_position=1;
                    sNavigationDrawer.setAppbarTitleTV("Contests");
                    fragmentClass =  loading_activity.class;
                    break;
                }
                case "friends" : {
                    prev_position=2;
                    sNavigationDrawer.setAppbarTitleTV("Friends");
                    fragmentClass =  friend_list.class;
                    break;
                }
                case "ranking" : {
                    prev_position=3;
                    sNavigationDrawer.setAppbarTitleTV("Ranking");
                    fragmentClass =  UniversityListFragment.class;
                    break;
                }
                case "calculator" : {
                    prev_position=4;
                    sNavigationDrawer.setAppbarTitleTV("Calculator");
                    fragmentClass =  calculator_activity.class;
                    break;
                }
                case "myprofile" : {
                    prev_position=5;
                    sNavigationDrawer.setAppbarTitleTV("My Profile");
                    fragmentClass =  friend_list.class;
                    break;
                }
            }
        } else {
            prev_position= 0;
            sNavigationDrawer.setAppbarTitleTV("Dashboard");
            fragmentClass =  dashboard_activity.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        prev_position= 0;
                        fragmentClass = dashboard_activity.class;
                        break;
                    }
                    case 1:{
                        prev_position= 1;
                        fragmentClass = loading_activity.class;
                        break;
                    }
                    case 2:{
                        prev_position= 2;
                        fragmentClass = friend_list.class;
                        break;
                    }
                    case 3:{
                        prev_position= 3;
                        fragmentClass = UniversityListFragment.class;
                        break;
                    }
                    case 4:{
                        prev_position= 4;
                        fragmentClass = calculator_activity.class;
                        break;
                    }
                    case 5:{
                        prev_position= 5;
                        Intent intent2 = new Intent(MainActivity.this, user_profile_loading.class);
                        intent2.putExtra("type", 3);
                        intent2.putExtra("USER_KEY", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(intent2);
                        break;
                    }
                    case 6:{
                        prev_position = 6;
                        fragmentClass = about_activity.class;
                        break;
                    }
                    case 7:{
                        final AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(MainActivity.this);
                        }
                        builder.setTitle("Close app")
                                .setMessage("Are you sure you want to Log out from this app?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), signin_activity.class));
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sNavigationDrawer.openDrawer();
                                sNavigationDrawer.setAppbarTitleTV("Dashboard");
                                fragmentClass = dashboard_activity.class;
                            }
                        });
                        builder.show();
                        break;
                    }
                    case 8:{
                        onBackPressed();
                        break;
                    }

                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(sNavigationDrawer.isDrawerOpen()) {
            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setTitle("Close app")
                    .setMessage("Are you sure you want to close this app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finishAffinity();
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    if (fragment != null) {
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if(prev_position == 0) sNavigationDrawer.setAppbarTitleTV("Dashboard");
                        else if(prev_position == 1) sNavigationDrawer.setAppbarTitleTV("Contests");
                        else if(prev_position == 2) sNavigationDrawer.setAppbarTitleTV("Friends");
                        else if(prev_position == 3) sNavigationDrawer.setAppbarTitleTV("Ranking");
                        else if(prev_position == 4) sNavigationDrawer.setAppbarTitleTV("Calculator");
                        else if(prev_position == 5) sNavigationDrawer.setAppbarTitleTV("My Profile");
                        else if(prev_position == 6) sNavigationDrawer.setAppbarTitleTV("About");
                        else if(prev_position == 7) sNavigationDrawer.setAppbarTitleTV("Logout");
                        else if(prev_position == 8) sNavigationDrawer.setAppbarTitleTV("Exit");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                    }
                }
            });
            builder.show();
        } else {
            if(prev_position == 0) {
                sNavigationDrawer.openDrawer();
            } else {
                fragmentClass = dashboard_activity.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
            }
        }
    }


}
