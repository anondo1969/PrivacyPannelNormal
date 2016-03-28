package app.android.tanzi.com.privacypannel3;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by Entu on 3/24/2016.
 */
public class FakeGPSOnSignalServiceReceiver extends BroadcastReceiver {

    boolean GPSProtectionEnable = false;
    final Context context1 = AppContext.getContext();
    final DatabaseHandler db = new DatabaseHandler(context1);

    @Override
    public void onReceive(Context context, Intent intent) {

        //check whether allow mock location is on or off

        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {

            //if allow mock location is on then show warning dialogue
            //------------------------------------------------

            AlertDialog.Builder builder = new AlertDialog.Builder(AppContext.getContext());
            builder.setMessage("Please Enable \"allow mock locations\" to Enable \"Fake Location\"." +
                    "\nTo change go, Settings > More > Developer options > Allow mock locations."+
                    "\nAfter Enabling Please restart the application.")
                    .setCancelable(false)
                    .setTitle("Attention:")
                    .setPositiveButton("OK",null);
            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();

            //-----------------------------------------------*/

            //also cancel the alarm manager
            //----------------------------------------------------------------------------------------------------------------
            AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent notificationIntent = new Intent(context, FakeGPSOnSignalServiceReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 1969,   notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mgr.cancel(pendingIntent);
            //-------------------------------------------------------------------------------------------------------------------
        }

        //detecting forground apps
        new AsyncTask<Void, Void, List<ProcessManager.Process>>() {

            long startTime;

            @Override
            protected List<ProcessManager.Process> doInBackground(Void... params) {
                startTime = System.currentTimeMillis();
                return ProcessManager.getRunningApps();
            }

            @Override
            protected void onPostExecute(List<ProcessManager.Process> processes) {
                StringBuilder sb = new StringBuilder();
                sb.append("Execution time: ").append(System.currentTimeMillis() - startTime).append("ms\n");
                sb.append("Running apps:\n");

                for (ProcessManager.Process process : processes) {

                    //check whether it is a system app or not
                    //-------------------------------START---------------------------------------------------------------------

//                    Log.d("Foreground Process: ", "starts");

                    ApplicationInfoController applicationInfo = db.getApplicationInfo(process.uid);


                    //check whether it is a system app or not
                    //if system process id is of a system app then it will be null or 0
                    if(applicationInfo.getUID()!=0) {
                        //sb.append('\n').append(process.name);
                        //sb.append('\n').append(process.uid);

//                        String log = "Id: "+applicationInfo.getId()+" ,UID: " + applicationInfo.getUID()
//                                +" ,Name: " + applicationInfo.getAppName() + " ,Package Name: " + applicationInfo.getPackageName()
//                                + " ,Permission Checked: " + applicationInfo.getPermissionChecked();
//                        // Writing Contacts to log
//                        Log.d("Foreground Process: ", log);

                        //Check gps permissions
                        List<PermissionInfoController> packageSpecificPermissionInfo = db.
                                getPackageSpecificPermissionInfo(applicationInfo.getUID());

                        for (PermissionInfoController apps : packageSpecificPermissionInfo) {
                            if(apps.getPermissions().equals("android.permission.ACCESS_COARSE_LOCATION")
                                    ||apps.getPermissions().equals("android.permission.ACCESS_FINE_LOCATION") ){

                                if(applicationInfo.getPermissionChecked().equals("1")) {// 0 is for testing only, it will be 1
                                    GPSProtectionEnable = true;
                                }

                                sb.append('\n').append(applicationInfo.getAppName());
                                sb.append('\n').append(applicationInfo.getPackageName());

                                break;
                                //later we will save the app names to show
                            }

                        }

                    }
                }
                //  Toast.makeText(AppContext.getContext(), "Active Apps: " + sb.toString(), Toast.LENGTH_LONG).show();
//                Log.d("GPSProtectionEnable: ", Boolean.toString(GPSProtectionEnable));

                Intent service = new Intent(AppContext.getContext(), FakeGPSOnService.class);


                if(GPSProtectionEnable){

                    //call the service
//                    Log.d("FakeGPSOnSignal: ", "GPS Permission");
                    //Toast.makeText(AppContext.getContext(), "GPS Permission", Toast.LENGTH_LONG).show();
                    //Toast.makeText(AppContext.getContext(), "Active Apps: " + sb.toString(), Toast.LENGTH_LONG).show();

                    AppContext.getContext().startService(service);

                }
                else{
                    //stop the service
//                    Log.d("FakeGPSOnSignal: ", "No GPS Permission");
//                    Toast.makeText(AppContext.getContext(), "No GPS Permission", Toast.LENGTH_LONG).show();
                    //ProfileSettings profileSettings = new ProfileSettings();
                    //if(profileSettings.isMyServiceRunning(FakeGPSOnService.class, AppContext.getContext())) {

                        AppContext.getContext().stopService(service);
                    //}
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //------------------------------------------------------------------------------------------------
        db.close();



        //-------------------------------------------------------------------------

    }
}