package com.example.celia.attendance.Student;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.celia.attendance.ProfileUtil;
import com.example.celia.attendance.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class StudentRegisterCourseFragment extends Fragment {

    View myView;
    Button regiter;
    EditText courseNumber;
    String courseNumText;
    String userName = ProfileUtil.getInstance().getUserName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.student_register_fragment, container, false);
        regiter = (Button) myView.findViewById(R.id.button2);
        courseNumber = (EditText) myView.findViewById(R.id.editText2);
        regiter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                courseNumText = courseNumber.getText().toString();
                register();
            }
        });
        return myView;

    }

    private void register() { // coursenumber, gmailAddress
        RegisterCourse registerCourse = new RegisterCourse();
        registerCourse.execute();
    }

    private class RegisterCourse extends AsyncTask<String, Void, String> {

        public RegisterCourse() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            Log.v("RegisterCourse", "doInBackground method call");
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/registerCourse");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userName", userName)
                        .appendQueryParameter("courseNumber", courseNumText);

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
        protected void onPostExecute(String result) {

            Log.v(".onPostExecute", "onPostExecute method call");
            Log.v(".onPostExecute", "json response is:" + result);
        }
    }
}
