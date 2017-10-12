package com.example.celia.attendance.Teacher;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.example.celia.attendance.ProfileUtil;
import com.example.celia.attendance.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by celia on 7/31/16.
 */
public class TeacherCreateCourseFragment extends Fragment {
    private ProfileUtil profile = ProfileUtil.getInstance();
    private View myView;
    Button saveButton;
    EditText courseNumberEdit;
    EditText courseNameEdit;
    EditText description;
    EditText startDateEdit;
    EditText endDateEdit;
    EditText startTimeEdit;
    EditText endTimeEdit;
    EditText numClassEdit;
    EditText beaconUrlEdit;
    EditText editText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.teacher_create_fragment, container, false);
        saveButton = (Button) myView.findViewById(R.id.savebutton);
        courseNumberEdit = (EditText) myView.findViewById(R.id.CourseNumberEdit);
        courseNameEdit = (EditText) myView.findViewById(R.id.CourseNameEdit);
        description = (EditText) myView.findViewById(R.id.DescriptionEdit);
        startDateEdit = (EditText) myView.findViewById(R.id.StartDateEdit);
        endDateEdit = (EditText) myView.findViewById(R.id.EndDateEdit);
        startTimeEdit = (EditText) myView.findViewById(R.id.StartTimeEdit);
        endTimeEdit = (EditText) myView.findViewById(R.id.EndTimeEdit);
        numClassEdit = (EditText) myView.findViewById(R.id.NumCourseEdit);
        beaconUrlEdit = (EditText) myView.findViewById(R.id.BeaconUrlEdit);


        startDateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("in onclick");
                editText = startDateEdit;

                showPopup(getActivity());
            }
        });

        endDateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("in onclick");
                editText = endDateEdit;

                showPopup(getActivity());
            }
        });

        startTimeEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeEdit.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        endTimeEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeEdit.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view) {

                String number = courseNumberEdit.getText().toString();
                String name = courseNameEdit.getText().toString();
                String desc = description.getText().toString();
                String startDate = startDateEdit.getText().toString();
                String endDate = endDateEdit.getText().toString();
                String startTime = startTimeEdit.getText().toString();
                String endTime = endTimeEdit.getText().toString();
                String numClass = numClassEdit.getText().toString();
                String beaconUrl = beaconUrlEdit.getText().toString();

                JSONObject json = new JSONObject();

                try {
                    json.put("courseName", name);
                    json.put("courseNumber", number);
                    json.put("description", desc);
                    json.put("startDate", getDate(startDate).getTime()+"");
                    json.put("endDate", getDate(endDate).getTime()+"");
                    json.put("startTime", getTime(startTime).getTime() + "");
                    json.put("endTime", getTime(endTime).getTime() + "");
                    json.put("numOfCourse", numClass);
                    json.put("beaconUrl", beaconUrl);
                    json.put("teacherUserName", profile.getUserName());
                    json.put("teacherUserName", profile.getUserName());


                } catch (Exception e) {

                }

                new RetrieveFeedTask(json).execute();
            }

        });

        return myView;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private JSONObject json;
        public RetrieveFeedTask(JSONObject json) {
            this.json = json;
        }

        protected String doInBackground(String... urls) {
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/createCourse");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(json.toString());
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                if (conn.getResponseCode() != 200) {
                    return conn.getResponseMessage() + conn.getResponseCode();
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                String result = "";
                while ((output = br.readLine()) != null) {
                    result = output.toString();
                }
                System.out.println("result: " +result);

                conn.disconnect();
                return result;
            } catch (Exception e) {
                return e.toString();
            }
        }

        protected void onPostExecute(String result) {
            // TODO: check this.exception
            // TODO: do something with the feed
            Log.i("result", result);
        }
    }

    private static Date getDate(String str) {
        SimpleDateFormat sdf = new  SimpleDateFormat("MM-dd-yyyy");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {

        }
        return date;
    }
    private static Date getTime(String str) {
        SimpleDateFormat sdf = new  SimpleDateFormat("MM-dd-yyyy HH:mm");
        SimpleDateFormat sdfToday = new  SimpleDateFormat("MM-dd-yyyy");
        String today = sdfToday.format(new Date());
        Date date = null;
        try {
            date = sdf.parse(today + " " + str);
        } catch (Exception e) {

        }
        return date;
    }

    private void showPopup(Activity context) {

        // Inflate the popup_layout.xml
        System.out.println("in show up");
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.datepopup, null,false);
        // Creating the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                layout,800,500);

        popupWindow.setContentView(layout);
        popupWindow.setHeight(900);
        popupWindow.setOutsideTouchable(false);
        // Clear the default translucent background
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        CalendarView cv = (CalendarView) layout.findViewById(R.id.calendarView);
        cv.setBackgroundColor(Color.WHITE);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                Log.d("date selected", "date selected " + year + " " + month + " " + dayOfMonth);
                editText.setText(month+1 + "-" + dayOfMonth + "-" + year);

            }
        });
        popupWindow.showAtLocation(layout, Gravity.TOP,5,170);
    }
}
