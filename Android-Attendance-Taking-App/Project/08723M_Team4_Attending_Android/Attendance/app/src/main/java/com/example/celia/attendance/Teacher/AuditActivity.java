package com.example.celia.attendance.Teacher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.celia.attendance.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiwei on 21/7/16.
 */
public class AuditActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audit_list);
        Intent intent = getIntent();
        final String courseId =  (String)intent.getSerializableExtra("courseId");
        final String courseName =  (String)intent.getSerializableExtra("courseName");
        final String courseNumber =  (String)intent.getSerializableExtra("courseNumber");
        final String teacherUserName =  (String)intent.getSerializableExtra("teacherUserName");

        new GetAttendenceRateByTeacher(courseId, courseName, courseNumber, teacherUserName).execute();
        new GetStudentNotAttend(courseId, courseName, courseNumber, teacherUserName).execute();



//        new GetAttendenceRateByTeacher(courseName, courseNumber, teacherUserName).execute();

    }



    private class GetAttendenceRateByTeacher extends AsyncTask<String, String, String> {

        private String courseName;
        private String courseNumber;
        private String courseId;
        private String teacherUserName;

        public GetAttendenceRateByTeacher (String courseId, String courseName, String courseNumber, String teacherUserName) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseNumber = courseNumber;
            this.teacherUserName = teacherUserName;
        }

        @Override
        protected String doInBackground(String... data) {
            // Create a new HttpClient and Post Header


            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getAttendantRateByTeacher");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = sdf.format(date);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("date", strDate)
                        .appendQueryParameter("courseId", courseId);

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
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
                conn.disconnect();
                return result;
            } catch (Exception e) {
                return e.toString();
            }

        }

        @Override
        protected void onPostExecute(String result){

            TextView auditRateView = (TextView)findViewById(R.id.audit_rate);
            auditRateView.setText("Attendence Rate: " + result+"%");


        }
    }

    private class GetStudentNotAttend extends AsyncTask<String, String, String> {

        private String courseName;
        private String courseNumber;
        private String courseId;
        private String teacherUserName;

        public GetStudentNotAttend (String courseId, String courseName, String courseNumber, String teacherUserName) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.courseNumber = courseNumber;
            this.teacherUserName = teacherUserName;
        }

        @Override
        protected String doInBackground(String... data) {
            // Create a new HttpClient and Post Header


            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getStudentNotAttend");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = sdf.format(date);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("date", strDate)
                        .appendQueryParameter("courseId", courseId);

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
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
                conn.disconnect();
                return result;
            } catch (Exception e) {
                return e.toString();
            }

        }

        @Override
        protected void onPostExecute(String result){

            try {
                TextView absenceView = (TextView)findViewById(R.id.absence_list);
//                absenceView.setText(result);
                String[] array = result.split(",");
                for (String a: array) {
                    System.out.println(a);
                }
                JSONArray jsonarray = new JSONArray(result);
                System.out.println(jsonarray.toString());

                for (int i = 0; i < jsonarray.length(); i++) {
                    Object jsonobject = jsonarray.get(i);

                    absenceView.append(jsonobject.toString() + "  ");
                }
            } catch (Exception e) {

            }

        }
    }
}
