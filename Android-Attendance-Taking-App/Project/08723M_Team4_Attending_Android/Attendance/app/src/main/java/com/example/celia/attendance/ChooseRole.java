package com.example.celia.attendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.celia.attendance.Constants.Role;
import com.example.celia.attendance.Model.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChooseRole extends AppCompatActivity {
    private static Role role;
    private EditText andrewId;
    private static String andrewIdText;
    private EditText name;
    private static String nameText;
    private ProfileUtil profile = ProfileUtil.getInstance();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        andrewId = (EditText) findViewById(R.id.andrewIdText);
        name = (EditText) findViewById(R.id.nameText);

        Log.i("check profile, username", profile.getUserName());

        if (profile.getAndrewId() != null && !"null".equals(profile.getAndrewId())) {
            Intent intent = new Intent(this, Navigation.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("choose role", "on resume!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (profile.getAndrewId() != null && !"null".equals(profile.getAndrewId())) {
            Intent intent = new Intent(this, Navigation.class);
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.teacher:
                if (checked)
                    Log.i("On choose role page:", "Teacher role is chosen");
                    role = Role.TEACHER;
                    break;
            case R.id.student:
                if (checked)
                    Log.i("On choose role page:", "Student role is chosen");
                    role = Role.STUDENT;
                    break;
        }

    }
    public void onButtonClicked(View view) {
        andrewIdText = andrewId.getText().toString();
        nameText = name.getText().toString();
        createUser();
        try{
            Thread.sleep(2000);
        } catch (Exception e) {
            Log.i("choose role ", "thread sleeping exception");
        }

        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);

    }
    public void createUser() {
        user  = new User();
        user.setUserName(profile.getUserName());
        user.setName(nameText);
        user.setAndrewId(andrewIdText);
        user.setRole(role);

        new CreateUser().execute();

    }

    private class CreateUser extends AsyncTask<String, Void, String> {

        public CreateUser() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            Log.v("CreateUser-chooseRole", "doInBackground method call");
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/updateUser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(new Gson().toJson(user));
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
            profile.setAndewId(andrewIdText);
            profile.setRole(role.toString());
            profile.setName(nameText);
            Log.v("role.onPostExecute", "onPostExecute method call");
            Log.v("role.onPostExecute", "json response is:" + result);
        }
    }
}
