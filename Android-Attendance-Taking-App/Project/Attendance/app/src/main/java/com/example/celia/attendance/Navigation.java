package com.example.celia.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.celia.attendance.Student.StudentMyCourseFragment;
import com.example.celia.attendance.Student.StudentQuizFragment;
import com.example.celia.attendance.Student.StudentRegisterCourseFragment;
import com.example.celia.attendance.Teacher.TeacherCreateCourseFragment;
import com.example.celia.attendance.Teacher.TeacherMyCourseFragment;
import com.example.celia.attendance.Teacher.TeacherSendQuizFragment;


public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProfileUtil profile = ProfileUtil.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (profile.getRole().equals(Constants.Role.STUDENT.toString())) {
            setContentView(R.layout.student_navigation);
            Intent startIntent = new Intent(this, BeaconService.class);
            startService(startIntent);
        } else {
            setContentView(R.layout.teacher_navigation);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer;
        if (profile.getRole().equals(Constants.Role.STUDENT.toString())) {
             drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        } else {
             drawer = (DrawerLayout) findViewById(R.id.teacher_drawer_layout);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        TextView profileUserName = (TextView) headerView.findViewById(R.id.profileUserName);
        TextView profileName = (TextView) headerView.findViewById(R.id.profileName);
        TextView profileAndrewId = (TextView) headerView.findViewById(R.id.profileAndrewId);
        TextView profileRole = (TextView) headerView.findViewById(R.id.profileRole);
        profileUserName.setText(ProfileUtil.getInstance().getUserName());
        profileName.setText(ProfileUtil.getInstance().getName());
        profileAndrewId.setText(ProfileUtil.getInstance().getAndrewId());
        profileRole.setText(ProfileUtil.getInstance().getRole());

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer;
        if (profile.getRole().equals(Constants.Role.STUDENT.toString())) {
            drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        } else {
            drawer = (DrawerLayout) findViewById(R.id.teacher_drawer_layout);
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (profile.getRole().equals(Constants.Role.STUDENT.toString())) {
            if (id == R.id.student_nav_first) {
                // Handle the camera action
                fragmentManager.beginTransaction().replace(R.id.content_frame, new StudentRegisterCourseFragment()).commit();

            } else if (id == R.id.student_nav_second) {

                fragmentManager.beginTransaction().replace(R.id.content_frame, new StudentMyCourseFragment()).commit();
            } else if (id == R.id.student_nav_third) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new StudentQuizFragment()).commit();

            } else if (id == R.id.student_nav_forth) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TweetFragment()).commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (id == R.id.teacher_nav_first) {
                // Handle the camera action
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TeacherCreateCourseFragment()).commit();

            } else if (id == R.id.teacher_nav_second) {

                fragmentManager.beginTransaction().replace(R.id.content_frame, new TeacherMyCourseFragment()).commit();
            } else if (id == R.id.teacher_nav_third) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TeacherSendQuizFragment()).commit();

            } else if (id == R.id.teacher_nav_forth) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TweetFragment()).commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.teacher_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }

        return true;
    }
}
