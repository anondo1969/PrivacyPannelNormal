package app.android.tanzi.com.privacypannel3;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import java.util.Calendar;

/**
 * Created by Entu on 3/24/2016.
 */
public class ProfileSettings {
    public ProfileSettings() {
    }

    public boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                //Log.d("Service already","running");
                return true;
            }
        }
        // Log.d("Service not", "running");
        return false;
    }

    public void setProfile(int id) {
        //--------------Profile Settings Code----------------------------------------------------------------------------
        int profileSettings = id; //2 strict 1 moderate 0 normal
        int status = 0;
        long REPEAT_TIMER = 0;

        ////Use this to create a Component
        ComponentName FakeGPSOnSignalServiceReceiverComponent = new ComponentName(AppContext.getContext(), FakeGPSOnSignalServiceReceiver.class);
        ComponentName LocationPermissionWarningServiceReceiverComponent = new ComponentName(AppContext.getContext(), LocationPermissionWarningServiceReceiver.class);

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------

        //disable fakegps receiver if enabled
        status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(FakeGPSOnSignalServiceReceiverComponent);
        if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.d("fakegpsreceivr disabled", "0");
            AppContext.getContext().getPackageManager().setComponentEnabledSetting
                    (FakeGPSOnSignalServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            //stop the service also
            Intent gpsService = new Intent(AppContext.getContext(), FakeGPSOnService.class);

            // if(isMyServiceRunning(FakeGPSOnService.class, AppContext.getContext())){

            AppContext.getContext().stopService(gpsService);
            // }
        }

        //disable warningReceiver if enabled
        status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(LocationPermissionWarningServiceReceiverComponent);
        if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.d("warnreceiver disabled", "0");
            AppContext.getContext().getPackageManager().setComponentEnabledSetting
                    (LocationPermissionWarningServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------


        AlarmManager service = (AlarmManager) AppContext.getContext().getSystemService(Context.ALARM_SERVICE);

        if (profileSettings == 0) {

            //disable fakegps receiver if enabled
            status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(FakeGPSOnSignalServiceReceiverComponent);
            if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                Log.d("fakegpsreceivr disabled", "0");
                AppContext.getContext().getPackageManager().setComponentEnabledSetting
                        (FakeGPSOnSignalServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                //stop the service also
                Intent gpsService = new Intent(AppContext.getContext(), FakeGPSOnService.class);

                // if(isMyServiceRunning(FakeGPSOnService.class, AppContext.getContext())){

                AppContext.getContext().stopService(gpsService);
                // }
            }

            //disable warningReceiver if enabled
            status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(LocationPermissionWarningServiceReceiverComponent);
            if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                Log.d("warnreceiver disabled", "0");
                AppContext.getContext().getPackageManager().setComponentEnabledSetting
                        (LocationPermissionWarningServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }

        }
        else if (profileSettings == 1) {

            //disable fakegps receiver if enabled
            // status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(FakeGPSOnSignalServiceReceiverComponent);
            // if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //   Log.d("fakegpsreceivr disabled", "1");
            //  AppContext.getContext().getPackageManager().setComponentEnabledSetting
            //    (FakeGPSOnSignalServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            //stop the service also
            Intent gpsService = new Intent(AppContext.getContext(), FakeGPSOnService.class);

            // if(isMyServiceRunning(FakeGPSOnService.class, AppContext.getContext())){

            AppContext.getContext().stopService(gpsService);
            //  }
            // }

            //enable warning receiver if disabled
            //status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(LocationPermissionWarningServiceReceiverComponent);
            //if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {

            AppContext.getContext().getPackageManager().setComponentEnabledSetting
                    (LocationPermissionWarningServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            //}

            // implementing moderate profile
            REPEAT_TIMER = 1000 * 60 * 10;

            Intent i = new Intent(AppContext.getContext(), LocationPermissionWarningServiceReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(AppContext.getContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

            Calendar cal = Calendar.getInstance();

            // start 10 seconds after boot completed
            cal.add(Calendar.SECOND, 10);

            // fetch every 10 minutes
            // InexactRepeating allows Android to optimize the energy consumption
            service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIMER, pending);
        }
        else if (profileSettings == 2) {

            //disable warning receiver if enabled
            // status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(LocationPermissionWarningServiceReceiverComponent);
            //  if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //     Log.d("warnreceiver disabled", "2");
            //    AppContext.getContext().getPackageManager().setComponentEnabledSetting
            //         (LocationPermissionWarningServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            // }

            //enable fakegps receiver if disabled
            // status = AppContext.getContext().getPackageManager().getComponentEnabledSetting(FakeGPSOnSignalServiceReceiverComponent);
            // if(status == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            AppContext.getContext().getPackageManager().setComponentEnabledSetting
                    (FakeGPSOnSignalServiceReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            //  }

            //implementing strict profile
            REPEAT_TIMER = 1000 * 10;

            Intent i = new Intent(AppContext.getContext(), FakeGPSOnSignalServiceReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(AppContext.getContext(), 1969, i, PendingIntent.FLAG_CANCEL_CURRENT);

            Calendar cal = Calendar.getInstance();

            // start 10 seconds after boot completed
            cal.add(Calendar.SECOND, 10);

            //check whether allow mock location is on or off
            if (Settings.Secure.getString(AppContext.getContext().getApplicationContext().getContentResolver(),
                    Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {

                // //if allow mock location is on then show warning dialogue
                // ------------------------------------------------
                //Toast.makeText(AppContext.getContext(), "Mock Location Disabled", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(AppContext.getContext());
                builder.setMessage("Please Enable \"allow mock locations\" to Enable \"Fake Location\"." +
                        "\nTo change go, Settings > More > Developer options > Allow mock locations." +
                        "\nAfter Enabling Please restart the application.")
                        .setCancelable(false)
                        .setTitle("\"allow mock locations\" is Disabled!!")
                        .setPositiveButton("OK", null);
                AlertDialog alert = builder.create();
                alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alert.show();

                //-----------------------------------------------*/
            }
            else {
                // fetch every 10 seconds
                // InexactRepeating allows Android to optimize the energy consumption
                service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIMER, pending);
            }

        }
        //---------------END---------------------------------------------------------------------------------*/
    }
}


