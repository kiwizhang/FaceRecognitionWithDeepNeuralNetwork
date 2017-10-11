package com.example.celia.attendance.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.celia.attendance.R;


public class ViewCourseActivity extends FragmentActivity {
    String msg = "Android : ";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcourse);
        Log.d(msg, "The onCreate() event");
        Intent intent = getIntent();

        final String courseName =  (String)intent.getSerializableExtra("courseName");
        final String courseNumber =  (String)intent.getSerializableExtra("courseNumber");
        final String teacherUserName =  (String)intent.getSerializableExtra("teacherUserName");
        final String courseId =  (String)intent.getSerializableExtra("courseId");
        final String startDate = (String)intent.getSerializableExtra("startDate");
        final String endDate = (String)intent.getSerializableExtra("endDate");
        final String startTime = (String)intent.getSerializableExtra("startTime");
        final String endTime = (String)intent.getSerializableExtra("endTime");
        final String description = (String)intent.getSerializableExtra("description");





        TextView textView = (TextView)findViewById(R.id.course_name);
        textView.setText(Html.fromHtml("<b>" + courseNumber + " " + courseName + "</b>" + "<br><br>" + "Start Date: " + startDate + "<br>" + "End Date: " + endDate+"<br>" + "Start Time: "+startTime + "<br>" + "End Time: " + endTime + "<br>Description: " + description + "<br><br>"));


        Button audit = (Button)findViewById(R.id.audit_button);
        audit.setOnClickListener(new View.OnClickListener() {


            public void onClick (View view) {

                Intent intent = new Intent(ViewCourseActivity.this, AuditActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("courseNumber", courseNumber);
                intent.putExtra("courseName", courseName);
                intent.putExtra("teacherUserName", teacherUserName);
                startActivity(intent);
            }

        });

        Button viewquiz = (Button)findViewById(R.id.quizlink_button);
        viewquiz.setOnClickListener(new View.OnClickListener() {


            public void onClick (View view) {

                Intent intent = new Intent(ViewCourseActivity.this, ViewQuizActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("courseNumber", courseNumber);
                intent.putExtra("courseName", courseName);
                intent.putExtra("teacherUserName", teacherUserName);
                startActivity(intent);
            }

        });


    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }
}