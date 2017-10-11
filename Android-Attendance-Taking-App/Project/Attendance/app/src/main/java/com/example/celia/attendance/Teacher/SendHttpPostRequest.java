package com.example.celia.attendance.Teacher;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


class SendHttpPostRequest extends AsyncTask<String, Void, String> {

    private String url;
    private JSONObject json;

    public SendHttpPostRequest(JSONObject json, String url) {
        this.json = json;
        this.url = url;
    }

    protected String doInBackground(String... urls) {
        HttpClient httpclient = new DefaultHttpClient();


//                url = new URI("http://www.google.com/");
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
//                nameValuePair.add(new BasicNameValuePair("userName", "kiwi@gmail.com"));


        //Encoding POST data
        try {
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            System.out.println(json.toString());
            StringEntity se = new StringEntity(json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);

        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpclient.execute(httpPost);
            // write response to log
            String responseStr = EntityUtils.toString(response.getEntity());

            Log.d("Http Post Response:", responseStr);
            return responseStr;
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}