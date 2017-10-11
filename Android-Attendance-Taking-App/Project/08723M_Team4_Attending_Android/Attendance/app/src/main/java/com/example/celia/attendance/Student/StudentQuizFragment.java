package com.example.celia.attendance.Student;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentQuizFragment extends Fragment {

    View myView;
    private ListView mListView;
    private SimpleAdapter mAdapter;
    public static List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    String userName = ProfileUtil.getInstance().getUserName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.student_course_fragment, container, false);


        mListView = (ListView) myView.findViewById(R.id.listView);

        mAdapter = new SimpleAdapter(getActivity(), list, R.layout.my_listview_layout,
                new String[]{"quizName", "url"},new int[]{R.id.courseNumber, R.id.courseName});

        mListView.setAdapter(mAdapter);

        new GetAllQuiz().execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
            {
                Uri uri = (Uri) list.get((int)arg3).get("url");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return myView;

    }

    private class GetAllQuiz extends AsyncTask<String, Void, String> {

        public GetAllQuiz() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            Log.v("GetCourses", "doInBackground method call");
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/getAllQuizByStudentUserName");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userName", userName);
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
                list.clear();
                while ((output = br.readLine()) != null) {
                    JSONArray array = new JSONArray(output);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        HashMap<String, Object> map = new HashMap<>();
                        //map.put("date", o.getString("createDate"));
                        map.put("quizName", o.getString("quizName"));
                        map.put("url", Uri.parse(o.getString("url")));
                        list.add(map);
                    }
                }
                conn.disconnect();
                return output;
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mAdapter.notifyDataSetChanged();
            Log.v(".onPostExecute", "onPostExecute method call");
            //dialog.dismiss();
            Log.v(".onPostExecute", "json response is:" + result);
        }
    }

}
