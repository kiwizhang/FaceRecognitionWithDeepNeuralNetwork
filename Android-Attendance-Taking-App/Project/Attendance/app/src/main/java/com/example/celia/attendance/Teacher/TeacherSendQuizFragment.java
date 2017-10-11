package com.example.celia.attendance.Teacher;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.celia.attendance.ProfileUtil;
import com.example.celia.attendance.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Jiwei on 26/7/16.
 */
public class TeacherSendQuizFragment extends Fragment  {

    View view;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    List<String> links = new ArrayList<>();
    TextView outputText;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    GoogleAccountCredential mCredential;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private String content = "";

    private static final String[] SCOPES = { DriveScopes.DRIVE_METADATA_READONLY };
    private Map<String, String> map = new HashMap<>();
    List<String> courseList = new ArrayList<>();
    private Map<String, String> courseMap = new HashMap<>();
    ProfileUtil profile = ProfileUtil.getInstance();








    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.teacher_sendquiz_fragment, container, false);
        outputText = (TextView)view.findViewById(R.id.outputText);
        mProgress = new ProgressDialog(getActivity());
        mProgress.setMessage("Calling Drive API ...");
        mCredential = GoogleAccountCredential.usingOAuth2(
                getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        new PostTask().execute();
        getResultsFromApi();
        addListenerOnButton();


//        addListenerOnSpinnerItemSelection();


        return view;

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    // add items into spinner dynamically

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
//        list.add("Select Course Number");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, courseList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) view.findViewById(R.id.spinner2);

//        links.add("https://docs.google.com/forms/d/e/1e79dLWI9Eap1_0jdaJ-9C7BbzUSQCrwAclRK1kPkA7I/viewform");
        System.out.println("links" + links.toString());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, links);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);




        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String courseNumber = spinner1.getSelectedItem().toString();
                String quizLink = map.get(spinner2.getSelectedItem().toString());
                System.out.println("quizlink from map"+ quizLink);
                String url = "http://50.19.186.200:8080/mobilefinalbackend/rest/createCourseQuiz";
                JSONObject json = new JSONObject();
                try {
                    json.put("courseId", courseMap.get(courseNumber));
                    json.put("url", quizLink);
                    json.put("quizName", spinner2.getSelectedItem().toString());
//                    json.put("createDate", new Date());
                    System.out.println("json: "+json.toString());

                } catch (Exception e) {

                }

                new  SendHttpPostRequest(json, url).execute();


            }

        });
    }
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            System.out.println("******before acquireGoogle");

            acquireGooglePlayServices();

        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            System.out.println("******in !online");

            outputText.setText("No network connection available.");
        } else {
            System.out.println("******before makerequest");

            new MakeRequestTask(mCredential).execute();
        }
    }

    private void acquireGooglePlayServices() {
        System.out.println("******int acquireGoogle");

        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    private boolean isDeviceOnline() {
        System.out.println("******in isonline");

        ConnectivityManager connMgr =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != getActivity().RESULT_OK) {
                    outputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == getActivity().RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == getActivity().RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }


    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        System.out.println("******in chooseaccount");

        if (EasyPermissions.hasPermissions(
                getActivity(), Manifest.permission.GET_ACCOUNTS)) {

            String accountName = getActivity().getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                System.out.println("******in chooseaccount acountName!= null");
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                System.out.println("******before startactivityforresult= null");

                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
                System.out.println("******after startactivity for result");

            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.drive.Drive mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            System.out.println("in makerequesttask");
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Drive API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Drive API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                System.out.println("******before getdatafromapi");

                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of up to 10 file names and IDs.
         * @return List of Strings describing files, or an empty list if no files
         *         found.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> fileInfo = new ArrayList<String>();
            System.out.println("******in datafromapi");

            FileList result = mService.files().list()
                    .setQ("mimeType='application/vnd.google-apps.form'")
                    .setPageSize(5)
                    .setFields("nextPageToken, files(id, name, mimeType)")
                    .execute();
            List<File> files = result.getFiles();
            System.out.println("getfiles:" + files.get(0));

            if (files != null) {
                for (File file : files) {
                    if (file.getMimeType().endsWith(".form")) {
                        String filename = file.getName();
                        String link = "https://docs.google.com/forms/d/" + file.getId()+ "/edit";
                        map.put(filename, link);
                        fileInfo.add(filename);
                    }
//                    fileInfo.add(String.format("%s %s %S (%s)\n", file.getKind(), file.getMimeType(),
//                            file.getName(), file.getId()));
                }
            }
            links = fileInfo;
            System.out.println("links: " + links.get(0));

            return fileInfo;
        }


        @Override
        protected void onPreExecute() {
            outputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                outputText.setText("No results returned.");
            } else {
//                for (String s: output) {
//                    map.put(s, getTitle(s));
//                    System.out.println("linktitle: " + map.get(s));
//                    links.add(s);
//                }


//                links.add("Select Quiz Link");
                addItemsOnSpinner2();

                System.out.println("*********links: " + links);
//                outputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            REQUEST_AUTHORIZATION);
                } else {
                    outputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                outputText.setText("Request cancelled.");
            }
        }

        public String sendGetRequest (String url) {
            HttpResponse response = null;
            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                response = client.execute(request);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response.toString();
        }
        public String getTitle (String url) {
            new SendHTTPGetRequest(url).execute();
            String title = "";
            String[] strs1 = content.split("<title>");
            if (strs1.length >= 2) {
                title = strs1[1].split("</title>")[0];

            }
            System.out.println("title: "+title);

//            String title = content.split("<title>")[1].split("</title>")[0];
            return title;
        }
    }
    class SendHTTPGetRequest extends AsyncTask<String, Void, String> {

        private Exception exception;
        private String urlString;
        private String responseString;

        public SendHTTPGetRequest(String url) {
            this.urlString = url;
        }

        protected String doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();

            try {
                URI url = new URI(urlString);
                HttpResponse response = httpclient.execute(new HttpGet(url));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    response.getEntity().writeTo(out);
                    responseString = out.toString();
//                    System.out.println(responseString);
                    out.close();
                    content = responseString;
                    return responseString;



                    //..more logic
                } else{
                    //Closes the connection.

                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());


                }

            }catch (IOException e) {
                return null;

            }catch (URISyntaxException e) {
                return null;

            }
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }

    private class PostTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://50.19.186.200:8080/mobilefinalbackend/rest/getCourseByTeacherUserName");

            try {
                //add data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("teacherUserName", profile.getUserName()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //execute http post
                HttpResponse response = httpclient.execute(httppost);
                content = response.toString();
                String responseStr = EntityUtils.toString(response.getEntity());

                content = responseStr;

                System.out.println("response: "+responseStr);
                return responseStr;

            } catch (ClientProtocolException e) {
                return null;

            } catch (IOException e) {
                return null;

            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("courseLis response: " + result);
                JSONArray jsonarray = new JSONArray(result);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    final String courseNumber = jsonobject.getString("courseNumber");
                    final String courseId = jsonobject.getString("courseId");
                    courseList.add(courseNumber);
                    courseMap.put(courseNumber, courseId);
                    addItemsOnSpinner1();




                }


            } catch (Exception e) {
                System.out.println("exception" + e);
            }


        }
    }

}
