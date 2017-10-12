package com.example.celia.attendance.Teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.celia.attendance.ProfileUtil;
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
 * Created by celia on 7/31/16.
 */
public class TeacherMyCourseFragment extends Fragment {

    View myView;
    String content = "";
    LinearLayout linearLayout;
    ProfileUtil profile = ProfileUtil.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.teacher_course_fragment, container, false);

        linearLayout = new LinearLayout(getActivity());
        // Set the layout full width, full height
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL); //or VERTICAL
        new PostTask().execute();


        return myView;


    }

    private class PostTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getCourseByTeacherUserName");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("teacherUserName", profile.getUserName());

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
                    System.out.println("response: " + result);
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

                JSONArray jsonarray = new JSONArray(result);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    final String name = jsonobject.getString("courseNumber");
                    final String number = jsonobject.getString("courseName");
                    final String courseId = jsonobject.getString("courseId");
                    final String startDate = jsonobject.getString("startDate");
                    final String endDate = jsonobject.getString("endDate");
                    final String startTime = jsonobject.getString("startTime");
                    final String endTime = jsonobject.getString("endTime");
                    final String description = jsonobject.getString("description");




                    System.out.println(number + " " + name);
                    Button button = new Button(getActivity());
                    button.setId(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, 200);
                    params.setMargins(100,50,200,0);
                    button.setLayoutParams(params);
                    button.setText(name + " " + number);
                    button.setOnClickListener(new View.OnClickListener()
                    {   public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), ViewCourseActivity.class);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("courseNumber", number);
                        intent.putExtra("courseName", name);
                        intent.putExtra("teacherUserName", profile.getUserName());
                        intent.putExtra("startDate", startDate);
                        intent.putExtra("endDate", endDate);
                        intent.putExtra("startTime", startTime);
                        intent.putExtra("endTime", endTime);
                        intent.putExtra("description", description);
                        startActivity(intent);
                    }
                    });

                    linearLayout.addView(button);
                }
                ViewGroup viewGroup = (ViewGroup) myView;

                viewGroup.addView(linearLayout);

            } catch (Exception e) {
                System.out.println("exception" + e);
            }

        }
    }

}
