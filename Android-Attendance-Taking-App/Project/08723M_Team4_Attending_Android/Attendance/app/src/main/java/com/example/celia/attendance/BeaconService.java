package com.example.celia.attendance;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {

    private BeaconManager mBeaconManager;
    private String beaconUrl;
    private String userName  = ProfileUtil.getInstance().getUserName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-URL frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v"));
        mBeaconManager.bind(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    public void onBeaconServiceConnect() {
        Region region = new Region("all-beacons-region", null, null, null);

        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> beacons, Region region) {
        for (Beacon beacon: beacons) {
            if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x10) {
                // This is a Eddystone-URL frame
                beaconUrl = UrlBeaconUrlCompressor.uncompress(beacon.getId1().toByteArray());
                beaconUrl = beaconUrl.substring(0, beaconUrl.length() - 5) + "/" + beaconUrl.substring(beaconUrl.length() - 5);
                Log.d("Beacon", "I see a beacon transmitting a url: " + beaconUrl +
                        " approximately " + beacon.getDistance() + " meters away.");
                new CheckIn().execute();
            }
        }
    }

    private class CheckIn extends AsyncTask<String, Void, String> {

        public CheckIn() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... urls) {
            Log.v("checkIn", "doInBackground method call");
            try {

                URL url = new URL("http://50.19.186.200:8080/mobilefinalbackend/rest/createCheckin");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("date", getDateTime())
                        .appendQueryParameter("userName", userName)
                        .appendQueryParameter("beaconUrl", beaconUrl);

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
                    Log.i("username", userName);
                    Log.i("url", beaconUrl);
                    Log.i("date", getDateTime());
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

    private static String getDateTime() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return date;
    }
}

