package com.example.celia.attendance.Student;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.celia.attendance.ProfileUtil;
import com.example.celia.attendance.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentCourseDetail extends AppCompatActivity {

    private TextView datetime;
    private String courseId;
    private String userName = ProfileUtil.getInstance().getUserName();
    private double attendance;
    private double absence;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Bundle extras = getIntent().getExtras();

        courseId = extras.getString("id");

        new GetAttendanceRate().execute();

        datetime = (TextView) findViewById(R.id.dateTime);
        datetime.setText(getDateTime());

        webview = (WebView) findViewById(R.id.webView1);



    }

    private static String getDateTime() {
        //06-02-2016 EST
        String date = new SimpleDateFormat("MM-dd-yyyy ").format(new Date());
        return date;
    }

    private class GetAttendanceRate extends AsyncTask<String, Void, String> {

        public GetAttendanceRate() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            Log.v("GetAttendanceRate", "doInBackground method call");
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getAttendantRate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("studentUserName", userName)
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
                while ((output = br.readLine()) != null) {

                    attendance = Double.parseDouble(output.toString());
                    absence = 100 - attendance;
                    //result = "attendance:" + attendance + " absence:" + absence;
                }

                conn.disconnect();
                return output.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String GraphURL = "https://chart.googleapis.com/chart?cht=p3&chs=250x100&chd=t:"+attendance+"," + absence + "&chl=Attendance"+attendance+"|Absence"+ absence +"&chtt=Attendence Percentage";
            webview.loadUrl(GraphURL);
            Log.v(".onPostExecute", "onPostExecute method call");
            Log.v(".onPostExecute", "json response is:" + result);
        }
    }
}
