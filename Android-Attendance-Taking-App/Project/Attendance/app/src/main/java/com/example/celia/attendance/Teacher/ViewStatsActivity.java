package com.example.celia.attendance.Teacher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.celia.attendance.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jiwei on 25/7/16.
 */
public class ViewStatsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        Intent intent = getIntent();
        final String courseId =  (String)intent.getSerializableExtra("courseId");
        final String courseName =  (String)intent.getSerializableExtra("courseName");
        final String courseNumber =  (String)intent.getSerializableExtra("courseNumber");
        final String teacherUserName =  (String)intent.getSerializableExtra("teacherUserName");

        try {
//                    json.put("createDate", new Date());
            new GetAttendenceRateByTeacher(courseId, courseName, courseNumber, teacherUserName).execute();

        } catch (Exception e) {

        }

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

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getQuizByCourseId");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
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

            TextView responseView = (TextView)findViewById(R.id.responseView);
            try {
                TextView quizLinkView = (TextView)findViewById(R.id.responseView);
                JSONArray jsonarray = new JSONArray(result);

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    final String quizName = jsonobject.getString("quizName");
                    final String url = jsonobject.getString("url") + "#responses";


                    quizLinkView.append("\n");
                    quizLinkView.append("\n");
                    quizLinkView.append( Html.fromHtml("<a href="+url + ">" + quizName));
                    quizLinkView.setMovementMethod(LinkMovementMethod.getInstance());

//                    quizLinkView.append(quizName + "\n" + url + "\n");
                }


            } catch (Exception e) {

            }


        }
    }
}