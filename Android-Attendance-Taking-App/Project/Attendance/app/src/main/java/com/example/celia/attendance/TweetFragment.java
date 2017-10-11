package com.example.celia.attendance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.celia.attendance.Twitter.ConstantValues;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class TweetFragment extends Fragment {
    String TAG = "TWEET Service";
    View myView;
    RequestToken requestToken ;
    AccessToken accessToken;
    String oauth_url,oauth_verifier,profile_url;
    Dialog auth_dialog;
    WebView web;
    SharedPreferences pref;
    ProgressDialog progress;
    Twitter twitter;

    EditText content;
    String contentText;
    Button tweetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.tweet_fragment, container, false);

        content = (EditText)myView.findViewById(R.id.contentText);
        tweetButton = (Button)myView.findViewById(R.id.button3);
        tweetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                contentText = content.getText().toString();
                logIn();
            }
        });

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("CONSUMER_KEY", ConstantValues.TWITTER_CONSUMER_KEY);
        edit.putString("CONSUMER_SECRET", ConstantValues.TWITTER_CONSUMER_SECRET);
        edit.commit();
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(pref.getString("CONSUMER_KEY", ""), pref.getString("CONSUMER_SECRET", ""));

        return myView;

    }

    private void logIn() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Log.d(TAG, sharedPreferences.toString());
        if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
        {
            Log.d(TAG, ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN);
            new TokenGet().execute();
        }
        else
        {
            new PostTweet().execute();
        }
    }
    private class TokenGet extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                requestToken = twitter.getOAuthRequestToken();
                oauth_url = requestToken.getAuthorizationURL();
                Log.d("oauth_url", oauth_url);
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return oauth_url;
        }
        @Override
        protected void onPostExecute(String oauth_url) {
            if(oauth_url != null){
                Log.e("URL", oauth_url);
                auth_dialog = new Dialog(getActivity());
                auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                auth_dialog.setContentView(R.layout.oauth_dialog);
                web = (WebView)auth_dialog.findViewById(R.id.webv);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl(oauth_url);
                web.setWebViewClient(new WebViewClient() {
                    boolean authComplete = false;
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon){
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        if (url.contains("oauth_verifier") && authComplete == false){
                            authComplete = true;
                            Log.e("Url",url);
                            Uri uri = Uri.parse(url);
                            oauth_verifier = uri.getQueryParameter("oauth_verifier");

                            auth_dialog.dismiss();
                            new AccessTokenGet().execute();
                        }else if(url.contains("denied")){
                            auth_dialog.dismiss();
                            Toast.makeText(getActivity(), "Sorry !, Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.d(TAG, auth_dialog.toString());
                auth_dialog.show();
                auth_dialog.setCancelable(true);

            }else{
                Toast.makeText(getActivity(), "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class AccessTokenGet extends AsyncTask<String, String, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Fetching Data ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        }


        @Override
        protected Boolean doInBackground(String... args) {

            try {


                accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("ACCESS_TOKEN", accessToken.getToken());
                edit.putString("ACCESS_TOKEN_SECRET", accessToken.getTokenSecret());
                edit.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, true);
                User user = twitter.showUser(accessToken.getUserId());
                profile_url = user.getOriginalProfileImageURL();
                edit.putString("NAME", user.getName());
                edit.putString("IMAGE_URL", user.getOriginalProfileImageURL());

                edit.commit();


            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();


            }

            return true;
        }
        @Override
        protected void onPostExecute(Boolean response) {
            if(response){
                progress.hide();

                new PostTweet().execute();
                //
            }
        }


    }
    private class PostTweet extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Posting tweet ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        }
        protected String doInBackground(String... args) {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(pref.getString("CONSUMER_KEY", ""));
            builder.setOAuthConsumerSecret(pref.getString("CONSUMER_SECRET", ""));
            AccessToken accessToken = new AccessToken(pref.getString("ACCESS_TOKEN", ""), pref.getString("ACCESS_TOKEN_SECRET", ""));
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
            String status = "From Attendance:" + contentText;
            if (status.length() > 140) {
                status = status.substring(0,140);
            }
            try {
                twitter4j.StatusUpdate statusUpdate = new StatusUpdate(status);
                twitter4j.Status response = twitter.updateStatus(statusUpdate);
                return response.toString();
            } catch (TwitterException e) {
                Log.i("Error Code", String.valueOf(e.getErrorCode()));
                Log.i("Error Message", e.getErrorMessage());
            }
            return null;
        }
        protected void onPostExecute(String res) {
            if(res != null){
                progress.dismiss();
                Toast.makeText(getActivity(), "Tweet Sucessfully Posted", Toast.LENGTH_SHORT).show();

            }else{
                progress.dismiss();
                Toast.makeText(getActivity(), "You have already posted the same tweet!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
