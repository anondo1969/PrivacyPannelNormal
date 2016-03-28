package app.android.tanzi.com.privacypannel3;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Entu on 3/24/2016.
 */
public class FakeGPSOnService extends Service implements LocationListener {

//    public static final String LOG_TAG = "MockGpsProviderActivity";
    private static final String MOCK_GPS_PROVIDER_INDEX = "GpsMockProviderIndex";

    private MockGpsProvider mMockGpsProviderTask = null;
    private Integer mMockGpsProviderIndex = 0;

//    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    String beforeEnable = null;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("InsideonStartCommand", "Start: ----------------------------");
        super.onStartCommand(intent, flags, startId);

        //-----------------------------------------
        mMockGpsProviderIndex = 0;
        try {
            startFakeGPS();
        }
        catch (Exception e) {

        }
        //-------------------------------------------

        return START_STICKY;
    }

    @Override
    public void onCreate() {

//        Log.d("InsideServiceOncreate", "Start: ----------------------------");

        try {
            startFakeGPS();
        }
        catch (Exception e) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //switch off mock gps provider
        TurnMockLocationSettingsOff (0);

        // stop the mock GPS provider by calling the 'cancel(true)' method
        try {
            mMockGpsProviderTask.cancel(true);
            mMockGpsProviderTask = null;
        }
        catch (Exception e) {}

        // remove it from the location manager
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeTestProvider(MockGpsProvider.GPS_MOCK_PROVIDER);
        }
        catch (Exception e) {}
    }

    private void initializeLocationManager() {
//        Log.d("Inside Service", "Start: ----------------------------");
//        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /** Define a mock GPS provider as an asynchronous task of this Activity. */
    private class MockGpsProvider extends AsyncTask<String, Integer, Void> {
//        public static final String LOG_TAG = "GpsMockProvider";
        public static final String GPS_MOCK_PROVIDER = "GpsMockProvider";

        /** Keeps track of the currently processed coordinate. */
        public Integer index = 0;

        @Override
        protected Void doInBackground(String... data) {
//            Log.d(LOG_TAG, "Inside data");
            // process data
            for (String str : data) {
                // skip data if needed (see the Activity's savedInstanceState functionality)
                if(index < mMockGpsProviderIndex) {
                    index++;
                    continue;
                }

                // let UI Thread know which coordinate we are processing
                publishProgress(index);

                // retrieve data from the current line of text
                Double latitude = null;
                Double longitude = null;
                Double altitude= null;
                try {
                    String[] parts = str.split(",");
                    latitude = Double.valueOf(parts[0]);
                    longitude = Double.valueOf(parts[1]);
                    altitude = Double.valueOf(parts[2]);
                }
                catch(NullPointerException e) { break; }        // no data available
                catch(Exception e) { continue; }                // empty or invalid line

                // translate to actual GPS location
                Location location = new Location(GPS_MOCK_PROVIDER);

                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setAltitude(altitude);
                location.setTime(System.currentTimeMillis());
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setAccuracy(16F);
                location.setAltitude(0D);
                location.setTime(System.currentTimeMillis());
                location.setBearing(0F);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setBearing(360.0f);
                location.setSpeed(0.5f); //Set the speed, in meters/second over ground.
                location.setAltitude(altitude);
                location.setTime(new Date().getTime());
                location.setProvider(LocationManager.GPS_PROVIDER);
                location.setAccuracy(1);
                location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());

                // show debug message in log
//                Log.d(LOG_TAG, location.toString());

                // provide the new location
                //  LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // locationManager.setTestProviderLocation(GPS_MOCK_PROVIDER, location);

                try {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.setTestProviderLocation(GPS_MOCK_PROVIDER, location);

                } catch(Exception e) {

                }

                // sleep for a while before providing next location
                try {
                    Thread.sleep(200);

                    // gracefully handle Thread interruption (important!)
                    if(Thread.currentThread().isInterrupted())
                        throw new InterruptedException("");
                } catch (InterruptedException e) {
                    break;
                }

                // keep track of processed locations
                index++;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            Log.d(LOG_TAG, "onProgressUpdate():" + values[0]);
            mMockGpsProviderIndex = values[0];
        }
    }

    private void startFakeGPS(){

//        Log.d("InsideStartFakeGPS", "Start: ----------------------------");

        //Check whether mock gps on or off, if off then ask user to on it
        //---------------------------------------------------------------

        if(!checkMockLocationEnabled()){

            /*
            Intent II = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            II.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(II);

            Toast.makeText(getApplicationContext(), "Please Enable Allow mock locations", Toast.LENGTH_LONG).show();
            */
//            Log.d("Inside startFakeGPS", "Value: ----------------------------");
            // normalAppMockEnableRequest();

        }

        //---------------------------------------------------------------

        //for system app, do it programmatically
        //1. require root access to your device
        // 2. move your app to /system/app or /system/priv-app

        /*---------------------------------------------
        if(checkMockOn){

            int value = TurnMockLocationSettingsOn();

        }

        //---------------------------------------------*/


        /** Setup GPS. */
        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        //LocationManager locationManager = null;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            //Tell User to stop GPS
            //(In system app, we have to do it programmatically)
            //This code works on ROOTED phones if the app is moved to /system/aps
            //turnGpsOff(getApplicationContext());

            //In normal app, simply redirect to location settings page and ask the user to do it.
            //-------------------------------------------------------------

            //  normalAppGPSDisableRequest();

            //---------------------------------------------------------------------------------


            // use real GPS provider if enabled on the device
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            //invoke mock gps provider
            try {
//                Log.d(LOG_TAG, "Inside Call1");
                locationManager.addTestProvider(MockGpsProvider.GPS_MOCK_PROVIDER, false, false,
                        false, false, true, false, false, 0, 5);
                locationManager.setTestProviderEnabled(MockGpsProvider.GPS_MOCK_PROVIDER, true);
//                Log.d(LOG_TAG, "Outside Call1");

            } catch(Exception e) {

            }


//            Log.d("Inside GPS provider", "Start: ----------------------------");
        }
        else if(!locationManager.isProviderEnabled(MockGpsProvider.GPS_MOCK_PROVIDER)) {
//            Log.d("Inside SetMock provider", "Start: ----------------------------");

            // otherwise enable the mock GPS provider
            try {
//                Log.d(LOG_TAG, "Inside Call2");
                locationManager.addTestProvider(MockGpsProvider.GPS_MOCK_PROVIDER, false, false,
                        false, false, true, false, false, 0, 5);
                locationManager.setTestProviderEnabled(MockGpsProvider.GPS_MOCK_PROVIDER, true);
//                Log.d(LOG_TAG, "Outside Call2");

            } catch(Exception e) {


            }
        }

        if(locationManager.isProviderEnabled(MockGpsProvider.GPS_MOCK_PROVIDER)) {
//            Log.d("InsideGetMockprovider", "Start: ----------------------------");

            locationManager.requestLocationUpdates(MockGpsProvider.GPS_MOCK_PROVIDER, 0, 0, this);
//            Log.d(LOG_TAG, "Outside GetMockProvider");

            /** Load mock GPS data from file and create mock GPS provider. */
            try {
                // create a list of Strings that can dynamically grow
                List<String> data = new ArrayList<String>();

                /** read a CSV file containing WGS84 coordinates from the 'assets' folder
                 * (The website http://www.gpsies.com offers downloadable tracks. Select
                 * a track and download it as a CSV file. Then add it to your assets folder.)
                 */
                InputStream is = getAssets().open("mock_gps_data.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                // add each line in the file to the list
                String line = null;
                while ((line = reader.readLine()) != null) {
                    data.add(line);
                }

                // convert to a simple array so we can pass it to the AsyncTask
                String[] coordinates = new String[data.size()];
                data.toArray(coordinates);

                // create new AsyncTask and pass the list of GPS coordinates
//                Log.d(LOG_TAG, "Inside Call4");
                mMockGpsProviderTask = new MockGpsProvider();
                mMockGpsProviderTask.execute(coordinates);
//                Log.d(LOG_TAG, "Outside Call4");
            }
            catch (Exception e) {}
        }
    }

    private void turnGpsOn (Context context) {
        beforeEnable = Settings.Secure.getString (context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        String newSet = String.format ("%s,%s",
                beforeEnable,
                LocationManager.GPS_PROVIDER);
        try {
            Settings.Secure.putString (context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                    newSet);
        } catch(Exception e) {}
    }


    private void turnGpsOff (Context context) {
        if (null == beforeEnable) {
            String str = Settings.Secure.getString (context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (null == str) {
                str = "";
            } else {
                String[] list = str.split (",");
                str = "";
                int j = 0;
                for (int i = 0; i < list.length; i++) {
                    if (!list[i].equals (LocationManager.GPS_PROVIDER)) {
                        if (j > 0) {
                            str += ",";
                        }
                        str += list[i];
                        j++;
                    }
                }
                beforeEnable = str;
            }
        }
        try {
            Settings.Secure.putString (context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                    beforeEnable);
        } catch(Exception e) {}
    }

    private boolean checkMockLocationEnabled(){

        boolean is =true;

        if(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")){
            is =false;
        }
//        Log.d("Inside CheckMock", ":Value: " + Boolean.toString(is));

        return is;
    }

    private int TurnMockLocationSettingsOn() {
        int value = 1;
        try {
            Settings.Secure.putInt(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            value = Settings.Secure.getInt(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    private void TurnMockLocationSettingsOff (int restore_value) {
        try {
            Settings.Secure.putInt(getContentResolver(),
                    Settings.Secure.ALLOW_MOCK_LOCATION, restore_value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void normalAppMockEnableRequest(){


        Intent II = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        II.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(II);




//        Toast.makeText(getApplicationContext(), "Please Enable Allow mock locations", Toast.LENGTH_LONG).show();
    }

    private void normalAppGPSDisableRequest(){

        Intent I = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(I);

//        Toast.makeText(getApplicationContext(), "Please Disable Location", Toast.LENGTH_LONG).show();
    }


}
